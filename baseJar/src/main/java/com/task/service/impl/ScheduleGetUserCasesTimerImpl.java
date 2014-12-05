package com.task.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.database.sitemessage.model.PublicSitemessage;
import com.base.database.task.model.TaskGetUserCases;
import com.base.database.trading.model.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.*;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageService;
import com.sitemessage.service.SiteMessageStatic;
import com.task.service.IScheduleGetUserCasesTimer;
import com.task.service.ITaskGetUserCases;
import com.trading.service.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/10/17.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ScheduleGetUserCasesTimerImpl implements IScheduleGetUserCasesTimer {
    static Logger logger = Logger.getLogger(ScheduleGetUserCasesTimerImpl.class);
    @Value("${EBAY.API.URL}")
    private String apiUrl;
    @Autowired
    private ITradingGetUserCases iTradingGetUserCases;
    @Autowired
    private ITradingGetEBPCaseDetail iTradingGetEBPCaseDetail;
    @Autowired
    private ITradingCasePaymentDetail iTradingCasePaymentDetail;
    @Autowired
    private ITradingCaseResponseHistory iTradingCaseResponseHistory;
    @Autowired
    private ITradingGetDispute iTradingGetDispute;
    @Autowired
    private ITradingGetDisputeMessage iTradingGetDisputeMessage;
    @Autowired
    private SiteMessageService siteMessageService;
    @Autowired
    private ITaskGetUserCases iTaskGetUserCases;
    @Override
    public void synchronizeUserCases(List<TaskGetUserCases> taskGetUserCaseses) {
        /*CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);*/



        try{
            for(TaskGetUserCases taskGetUserCases:taskGetUserCaseses){
                Integer flag=taskGetUserCases.getTokenflag();
                flag=flag+1;
                taskGetUserCases.setTokenflag(flag);
                iTaskGetUserCases.saveListTaskGetUserCases(taskGetUserCases);
                UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
                d.setSoaOperationName("getUserCases");
                String token=taskGetUserCases.getToken();
                d.setSoaSecurityToken(token);
                d.setHeaderType("DisputeApiHeader");
                Map map=new HashMap();
                map.put("fromTime", taskGetUserCases.getFromtime());
                map.put("toTime", taskGetUserCases.getEndtime());
                map.put("page","1");
                String xml = BindAccountAPI.getUserCases(map);
                AddApiTask addApiTask = new AddApiTask();
                Map<String, String> resMap = addApiTask.exec2(d, xml, "https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1?REST-PAYLOAD");
                String r1 = resMap.get("stat");
                String res = resMap.get("message");
                if ("fail".equalsIgnoreCase(r1)) {
                    List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_get_user_cases_timer_FAIL", "CASE定时任务:" + taskGetUserCases.getId());
                    if(list1!=null&&list1.size()>0){
                        return;
                    }
                    TaskMessageVO taskMessageVO = new TaskMessageVO();
                    taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_GET_USER_CASES_TIMER + "_FAIL");
                    taskMessageVO.setMessageTitle("定时同步CASE失败!");
                    taskMessageVO.setMessageContext("CASE调用API失败:" + res);
                    taskMessageVO.setMessageTo(taskGetUserCases.getUserid());
                    taskMessageVO.setMessageFrom("system");
                    taskMessageVO.setOrderAndSeller("CASE定时任务:"+taskGetUserCases.getId());
                    siteMessageService.addSiteMessage(taskMessageVO);
                    return;
                }
                String ack = SamplePaseXml.getVFromXmlString(res, "ack");
                if ("Success".equalsIgnoreCase(ack)) {
                    Map<String,Object> map1= UserCasesAPI.parseXMLAndSave(res);
                    String totalPages= (String) map1.get("totalPages");
                    int totalPage= Integer.parseInt(totalPages);
                    for(int i=1;i<=totalPage;i++){
                        if(i!=1){
                            d=new UsercontrollerDevAccountExtend();
                            d.setSoaOperationName("getUserCases");
                            d.setSoaSecurityToken(token);
                            d.setHeaderType("DisputeApiHeader");
                            map.put("fromTime", taskGetUserCases.getFromtime());
                            map.put("toTime", taskGetUserCases.getEndtime());
                            map.put("page",i+"");
                            xml = BindAccountAPI.getUserCases(map);
                            resMap = addApiTask.exec2(d, xml, "https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1?REST-PAYLOAD");
                            r1 = resMap.get("stat");
                            res = resMap.get("message");
                            if ("fail".equalsIgnoreCase(r1)) {
                                List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_get_user_cases_timer_FAIL","CASE定时任务:"+taskGetUserCases.getId());
                                if(list1!=null&&list1.size()>0){
                                    return;
                                }
                                TaskMessageVO taskMessageVO = new TaskMessageVO();
                                taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_GET_USER_CASES_TIMER + "_FAIL");
                                taskMessageVO.setMessageTitle("定时同步CASE失败!");
                                taskMessageVO.setMessageContext("CASE调用API失败:" + res);
                                taskMessageVO.setMessageTo(taskGetUserCases.getUserid());
                                taskMessageVO.setMessageFrom("system");
                                taskMessageVO.setOrderAndSeller("CASE定时任务:"+taskGetUserCases.getId());
                                siteMessageService.addSiteMessage(taskMessageVO);
                                return;
                            }
                            if (!"Success".equalsIgnoreCase(ack)){
                                List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_get_user_cases_timer_FAIL","CASE定时任务:"+taskGetUserCases.getId());
                                if(list1!=null&&list1.size()>0){
                                    return;
                                }
                                TaskMessageVO taskMessageVO = new TaskMessageVO();
                                taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_GET_USER_CASES_TIMER + "_FAIL");
                                taskMessageVO.setMessageTitle("定时同步CASE失败!");
                                taskMessageVO.setMessageContext("CASE调用API失败:" + res);
                                taskMessageVO.setMessageTo(taskGetUserCases.getUserid());
                                taskMessageVO.setMessageFrom("system");
                                taskMessageVO.setOrderAndSeller("CASE定时任务:"+taskGetUserCases.getId());
                                siteMessageService.addSiteMessage(taskMessageVO);
                                return;
                            }
                            map1= UserCasesAPI.parseXMLAndSave(res);
                        }
                        List<TradingGetUserCases> userCasesList = (List<TradingGetUserCases>) map1.get("cases");
                        for (TradingGetUserCases userCases : userCasesList) {
                            List<TradingGetUserCases> casesList = iTradingGetUserCases.selectGetUserCasesByTransactionId(userCases.getTransactionid(),userCases.getSellerid());
                            if (casesList != null && casesList.size() > 0) {
                                userCases.setId(casesList.get(0).getId());
                                userCases.setHandled(casesList.get(0).getHandled());
                            } else {
                                userCases.setHandled(0);
                            }
                            userCases.setCreateUser(taskGetUserCases.getUserid());
                            iTradingGetUserCases.saveGetUserCases(userCases);
                            String caseType=userCases.getCasetype();
                            String caseId=userCases.getCaseid();
                            if(caseType.contains("EBP")){
                                d.setSoaOperationName("getEBPCaseDetail");
                                d.setSoaSecurityToken(token);
                                d.setHeaderType("DisputeApiHeader");
                                Map ebpMap=new HashMap();
                                ebpMap.put("token",token);
                                ebpMap.put("caseId", caseId);
                                ebpMap.put("caseType",caseType);
                                String ebpXml = BindAccountAPI.getEBPCase(ebpMap);
                                Map<String, String> resEbpMap = addApiTask.exec2(d, ebpXml, "https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1");
                                String ebpR1 = resEbpMap.get("stat");
                                String ebpRes = resEbpMap.get("message");

                                if ("fail".equalsIgnoreCase(ebpR1)) {
                                    List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_get_user_cases_ebp_timer_FAIL","CASE_EBP定时任务:"+taskGetUserCases.getId());
                                    if(list1!=null&&list1.size()>0){
                                        return;
                                    }
                                    TaskMessageVO taskMessageVO = new TaskMessageVO();
                                    taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_GET_USER_CASES_EBP_TIMER + "_FAIL");
                                    taskMessageVO.setMessageTitle("定时同步CASE_EBP失败!");
                                    taskMessageVO.setMessageContext("CASE_EBP调用API失败:" + res);
                                    taskMessageVO.setMessageTo(taskGetUserCases.getUserid());
                                    taskMessageVO.setMessageFrom("system");
                                    taskMessageVO.setOrderAndSeller("CASE_EBP定时任务:"+taskGetUserCases.getId());
                                    siteMessageService.addSiteMessage(taskMessageVO);
                                    return;
                                }
                                String ebpAck = SamplePaseXml.getVFromXmlString(ebpRes, "ack");
                                if ("Success".equalsIgnoreCase(ebpAck)) {
                                    Map<String,Object> ebpmap= GetEBPCaseDetailAPI.parseXMLAndSave(ebpRes);
                                    TradingGetEBPCaseDetail caseDetail= (TradingGetEBPCaseDetail) ebpmap.get("ebpCaseDetail");
                                    List<TradingGetEBPCaseDetail> lists=iTradingGetEBPCaseDetail.selectGetEBPCaseDetailByTransactionId(caseDetail.getTransactionid());
                                    if(lists!=null&&lists.size()>0){
                                        caseDetail.setId(lists.get(0).getId());
                                        List<TradingCasePaymentDetail> list1=iTradingCasePaymentDetail.selectCasePaymentDetailByEBPId(lists.get(0).getId());
                                        List<TradingCaseResponseHistory> list2=iTradingCaseResponseHistory.selectCaseResponseHistoryByEBPId(lists.get(0).getId());
                                        for(TradingCaseResponseHistory history:list2){
                                            iTradingCaseResponseHistory.deleteCaseResponseHistory(history);
                                        }
                                        for(TradingCasePaymentDetail detail:list1){
                                            iTradingCasePaymentDetail.deleteCasePaymentDetail(detail);
                                        }
                                    }
                                    caseDetail.setCreateUser(taskGetUserCases.getUserid());
                                    iTradingGetEBPCaseDetail.saveGetEBPCaseDetail(caseDetail);
                                    List<TradingCaseResponseHistory> historyList= (List<TradingCaseResponseHistory>) ebpmap.get("historyList");
                                    List<TradingCasePaymentDetail> paymentDetailList= (List<TradingCasePaymentDetail>) ebpmap.get("paymentDetailList");
                                    for(TradingCaseResponseHistory history:historyList){
                                        history.setCreateUser(taskGetUserCases.getUserid());
                                        history.setEbpcasedetailId(caseDetail.getId());
                                        iTradingCaseResponseHistory.saveCaseResponseHistory(history);
                                    }
                                    for(TradingCasePaymentDetail detail:paymentDetailList){
                                        detail.setCreateUser(taskGetUserCases.getUserid());
                                        detail.setEbpcasedetailId(caseDetail.getId());
                                        iTradingCasePaymentDetail.saveCasePaymentDetail(detail);
                                    }
                                }else {
                                    List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_get_user_cases_ebp_timer_FAIL", "CASE_EBP定时任务:" + taskGetUserCases.getId());
                                    if(list1!=null&&list1.size()>0){
                                        return;
                                    }
                                    TaskMessageVO taskMessageVO = new TaskMessageVO();
                                    taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_GET_USER_CASES_EBP_TIMER + "_FAIL");
                                    taskMessageVO.setMessageTitle("定时同步CASE_EBP失败!");
                                    taskMessageVO.setMessageContext("CASE_EBP调用API失败:" + res);
                                    taskMessageVO.setMessageTo(taskGetUserCases.getUserid());
                                    taskMessageVO.setMessageFrom("system");
                                    taskMessageVO.setOrderAndSeller("CASE_EBP定时任务:"+taskGetUserCases.getId());
                                    siteMessageService.addSiteMessage(taskMessageVO);
                                    return;
                                }
                            }else{
                                d=new UsercontrollerDevAccountExtend();
                                //真实环境
                               /* d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
                                d.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
                                d.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
                                d.setApiCompatibilityLevel("883");*/
                                //------------------------------------------
                                d.setApiSiteid("0");
                                d.setHeaderType("");
                                d.setApiCallName(APINameStatic.GetDispute);
                                xml = BindAccountAPI.getGetDispute(token, caseId);
                                //真实环境
                               /* resMap = addApiTask.exec2(d, xml, "https://api.ebay.com/ws/api.dll");*/
                                //测试环境
                                resMap = addApiTask.exec2(d, xml, apiUrl);
                                r1 = resMap.get("stat");
                                res = resMap.get("message");
                                if ("fail".equalsIgnoreCase(r1)) {
                                    List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_get_user_cases_dispute_timer_FAIL","CASE_dispute定时任务:"+taskGetUserCases.getId());
                                    if(list1!=null&&list1.size()>0){
                                        return;
                                    }
                                    TaskMessageVO taskMessageVO = new TaskMessageVO();
                                    taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_GET_USER_CASES_DISPUTE_TIMER + "_FAIL");
                                    taskMessageVO.setMessageTitle("定时同步CASE_dispute失败!");
                                    taskMessageVO.setMessageContext("CASE_dispute调用API失败:" + res);
                                    taskMessageVO.setMessageTo(taskGetUserCases.getUserid());
                                    taskMessageVO.setMessageFrom("system");
                                    taskMessageVO.setOrderAndSeller("CASE_dispute定时任务:"+taskGetUserCases.getId());
                                    siteMessageService.addSiteMessage(taskMessageVO);
                                    return;
                                }
                                String Ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                                if ("Success".equalsIgnoreCase(Ack)) {
                                    Map<String,Object> disputeMap= GetDisputeAPI.parseXMLAndSave(res);
                                    TradingGetDispute dispute= (TradingGetDispute) disputeMap.get("dispute");
                                    List<TradingGetDispute> disputeList=iTradingGetDispute.selectGetDisputeByTransactionId(dispute.getTransactionid());
                                    if(disputeList!=null&&disputeList.size()>0){
                                        dispute.setId(disputeList.get(0).getId());
                                    }
                                    dispute.setCreateUser(taskGetUserCases.getUserid());
                                    iTradingGetDispute.saveGetDispute(dispute);
                                    List<TradingGetDisputeMessage> messageList= (List<TradingGetDisputeMessage>) disputeMap.get("disputeMessage");
                                    List<TradingGetDisputeMessage> list=iTradingGetDisputeMessage.selectGetDisputeMessageByDisputeId(dispute.getId());
                                    if(list!=null&&list.size()>0){
                                        for(TradingGetDisputeMessage message:list){
                                            iTradingGetDisputeMessage.deleteGetDisputeMessage(message);
                                        }
                                    }
                                    for(TradingGetDisputeMessage message:messageList){
                                        message.setDisputeId(dispute.getId());
                                        message.setCreateUser(taskGetUserCases.getUserid());
                                        iTradingGetDisputeMessage.saveGetDisputeMessage(message);
                                    }
                                }else{
                                    List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_get_user_cases_dispute_timer_FAIL", "CASE_dispute定时任务:" + taskGetUserCases.getId());
                                    if(list1!=null&&list1.size()>0){
                                        return;
                                    }
                                    TaskMessageVO taskMessageVO = new TaskMessageVO();
                                    taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_GET_USER_CASES_DISPUTE_TIMER + "_FAIL");
                                    taskMessageVO.setMessageTitle("定时同步CASE_dispute失败!");
                                    taskMessageVO.setMessageContext("CASE_dispute调用API失败:" + res);
                                    taskMessageVO.setMessageTo(taskGetUserCases.getUserid());
                                    taskMessageVO.setMessageFrom("system");
                                    taskMessageVO.setOrderAndSeller("CASE_dispute定时任务:"+taskGetUserCases.getId());
                                    siteMessageService.addSiteMessage(taskMessageVO);
                                    return;
                                }
                            }
                        }
                    }
                }else{
                    List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_get_user_cases_timer_FAIL","CASE定时任务:"+taskGetUserCases.getId());
                    if(list1!=null&&list1.size()>0){
                        return;
                    }
                    TaskMessageVO taskMessageVO = new TaskMessageVO();
                    taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_GET_USER_CASES_TIMER + "_FAIL");
                    taskMessageVO.setMessageTitle("定时同步CASE失败!");
                    taskMessageVO.setMessageContext("CASE调用API失败:" + res);
                    taskMessageVO.setMessageTo(taskGetUserCases.getUserid());
                    taskMessageVO.setMessageFrom("system");
                    taskMessageVO.setOrderAndSeller("CASE定时任务:"+taskGetUserCases.getId());
                    siteMessageService.addSiteMessage(taskMessageVO);
                    return;
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String queryTrack(TradingOrderGetOrders order) throws Exception {
        BufferedReader in = null;
        String content = null;
        String trackNum=order.getShipmenttrackingnumber();
        String token=(URLEncoder.encode("RXYaxblwfBeNY+2zFVDbCYTz91r+VNWmyMTgXE4v16gCffJam2FcsPUpiau6F8Yk"));
        String url="http://api.91track.com/track?culture=zh-CN&numbers="+trackNum+"&token="+token;
        /*String url="http://api.91track.com/track?culture=en&numbers="+"RD275816257CN"+"&token="+token;*/
        HttpClient client=new DefaultHttpClient();
        HttpGet get=new HttpGet();
        get.setURI(new URI(url));
        HttpResponse response = client.execute(get);

        in = new BufferedReader(new InputStreamReader(response.getEntity()
                .getContent()));
        StringBuffer sb = new StringBuffer("");
        String line ="";
        String NL = System.getProperty("line.separator");
        while ((line = in.readLine()) != null) {
            sb.append(line + NL);
        }
        in.close();
        content = sb.toString();
        String[] arr=content.split(",");
        String content1="{"+arr[1]+"}";
        JSONObject json = JSON.parseObject(content1);
        String status=json.getString("Status");
        return status;
    }
}
