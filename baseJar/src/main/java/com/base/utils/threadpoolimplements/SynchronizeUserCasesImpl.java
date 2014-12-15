package com.base.utils.threadpoolimplements;

import com.base.database.trading.model.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.*;
import com.base.utils.common.DateUtils;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageStatic;
import com.trading.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/9/13.
 */
@Service
public class SynchronizeUserCasesImpl implements ThreadPoolBaseInterFace {
    static Logger logger = Logger.getLogger(SynchronizeUserCasesImpl.class);
    @Autowired
    private ITradingGetUserCases iTradingGetUserCases;
    @Autowired
    private ITradingGetEBPCaseDetail iTradingGetEBPCaseDetail;
    @Autowired
    private ITradingCaseResponseHistory iTradingCaseResponseHistory;
    @Autowired
    private ITradingCasePaymentDetail iTradingCasePaymentDetail;
    @Autowired
    private ITradingGetDispute iTradingGetDispute;
    @Autowired
    private ITradingGetDisputeMessage iTradingGetDisputeMessage;
    @Value("${EBAY.API.URL}")
    private String apiUrl;
    @Override
    public <T> void doWork(String res, T... t) {
          if(StringUtils.isEmpty(res)){return;}
        TaskMessageVO taskMessageVO = (TaskMessageVO)t[0];
        Map map2= (Map) taskMessageVO.getObjClass();
        String token= (String) map2.get("token");
        UsercontrollerDevAccountExtend d= (UsercontrollerDevAccountExtend) map2.get("d");
        AddApiTask addApiTask = new AddApiTask();
        String ack = null;
        try {
            ack = SamplePaseXml.getVFromXmlString(res, "ack");
            if ("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)) {
                if("Warning".equalsIgnoreCase(ack)){
                    String errors = SamplePaseXml.getWarningInformation(res);
                    logger.error("获取CASE有警告!" + errors);
                }
                Map<String,Object> map1= UserCasesAPI.parseXMLAndSave(res);
                String totalPages= (String) map1.get("totalPages");
                int totalPage= Integer.parseInt(totalPages);
                for(int i=1;i<=totalPage;i++) {
                    if (i > 1) {
                        Map map=new HashMap();
                     /*   Date startTime2= DateUtils.subDays(new Date(), 60);
                        Date endTime= DateUtils.addDays(startTime2, 60);*/
                        Date startTime2= DateUtils.subDays(new Date(), 6);
                        Date endTime= DateUtils.addDays(startTime2,6);
                        Date end1= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
                        String start= DateUtils.DateToString(startTime2);
                        String end=DateUtils.DateToString(end1);
                        map.put("fromTime", start);
                        map.put("toTime", end);
                        map.put("page", i + "");
                        String xml = BindAccountAPI.getUserCases(map);
                        d.setSoaSecurityToken(token);
                        d.setSoaOperationName("getUserCases");
                        d.setHeaderType("DisputeApiHeader");
                        Map<String, String> resMap = addApiTask.exec(d, xml, "https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1?REST-PAYLOAD");
                        String r1 = resMap.get("stat");
                        res = resMap.get("message");
                        if ("fail".equalsIgnoreCase(r1)) {
                            logger.error("纠纷同步失败!"+res+"\n\nXML:"+xml);
                        }
                        if (!"Success".equalsIgnoreCase(ack)&&!"Warning".equalsIgnoreCase(ack)) {
                            logger.error("纠纷同步失败!" +res +"\n\nXML:"+xml);
                        }
                        if("Warning".equalsIgnoreCase(ack)){
                            String errors = SamplePaseXml.getWarningInformation(res);
                            logger.error("获取CASE有警告!" + errors);
                        }
                        map1 = UserCasesAPI.parseXMLAndSave(res);
                    }
                    if ("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)) {
                        List<TradingGetUserCases> userCasesList = (List<TradingGetUserCases>) map1.get("cases");
                        for (TradingGetUserCases userCases : userCasesList) {
                            List<TradingGetUserCases> casesList = iTradingGetUserCases.selectGetUserCasesByTransactionId(userCases.getTransactionid(), userCases.getSellerid());
                            if (casesList != null && casesList.size() > 0) {
                                userCases.setId(casesList.get(0).getId());
                                userCases.setHandled(casesList.get(0).getHandled());
                            } else {
                                userCases.setHandled(0);
                            }
                            userCases.setCreateUser(taskMessageVO.getMessageTo());
                            iTradingGetUserCases.saveGetUserCases(userCases);
                            String caseType = userCases.getCasetype();
                            String caseId = userCases.getCaseid();
                            if (caseType.contains("EBP")) {
                                d.setSoaOperationName("getEBPCaseDetail");
                                d.setSoaSecurityToken(token);
                                d.setHeaderType("DisputeApiHeader");
                                Map ebpMap = new HashMap();
                                ebpMap.put("token", token);
                                ebpMap.put("caseId", caseId);
                                ebpMap.put("caseType", caseType);
                                String ebpXml = BindAccountAPI.getEBPCase(ebpMap);
                                Map<String, String> resEbpMap = addApiTask.exec(d, ebpXml, "https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1");
                                String ebpR1 = resEbpMap.get("stat");
                                String ebpRes = resEbpMap.get("message");

                                if ("fail".equalsIgnoreCase(ebpR1)) {
                                    logger.error("调用EBP API失败" + ebpRes+"\n\nXML:"+ebpXml);
                                }
                                String ebpAck = SamplePaseXml.getVFromXmlString(ebpRes, "ack");
                                if ("Success".equalsIgnoreCase(ebpAck)) {
                                    Map<String, Object> ebpmap = GetEBPCaseDetailAPI.parseXMLAndSave(ebpRes);
                                    TradingGetEBPCaseDetail caseDetail = (TradingGetEBPCaseDetail) ebpmap.get("ebpCaseDetail");
                                    List<TradingGetEBPCaseDetail> lists = iTradingGetEBPCaseDetail.selectGetEBPCaseDetailByTransactionId(caseDetail.getTransactionid());
                                    if (lists != null && lists.size() > 0) {
                                        caseDetail.setId(lists.get(0).getId());
                                        List<TradingCasePaymentDetail> list1 = iTradingCasePaymentDetail.selectCasePaymentDetailByEBPId(lists.get(0).getId());
                                        List<TradingCaseResponseHistory> list2 = iTradingCaseResponseHistory.selectCaseResponseHistoryByEBPId(lists.get(0).getId());
                                        for (TradingCaseResponseHistory history : list2) {
                                            iTradingCaseResponseHistory.deleteCaseResponseHistory(history);
                                        }
                                        for (TradingCasePaymentDetail detail : list1) {
                                            iTradingCasePaymentDetail.deleteCasePaymentDetail(detail);
                                        }
                                    }
                                    caseDetail.setCreateUser(taskMessageVO.getMessageTo());
                                    iTradingGetEBPCaseDetail.saveGetEBPCaseDetail(caseDetail);
                                    List<TradingCaseResponseHistory> historyList = (List<TradingCaseResponseHistory>) ebpmap.get("historyList");
                                    List<TradingCasePaymentDetail> paymentDetailList = (List<TradingCasePaymentDetail>) ebpmap.get("paymentDetailList");
                                    for (TradingCaseResponseHistory history : historyList) {
                                        history.setCreateUser(taskMessageVO.getMessageTo());
                                        history.setEbpcasedetailId(caseDetail.getId());
                                        iTradingCaseResponseHistory.saveCaseResponseHistory(history);
                                    }
                                    for (TradingCasePaymentDetail detail : paymentDetailList) {
                                        detail.setCreateUser(taskMessageVO.getMessageTo());
                                        detail.setEbpcasedetailId(caseDetail.getId());
                                        iTradingCasePaymentDetail.saveCasePaymentDetail(detail);
                                    }
                                } else {
                                    logger.error("获取EBP纠纷参数出错!" + res+"\n\nXML:"+ebpXml);
                                }
                            } else {
                            /*d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
                            d.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
                            d.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
                            d.setApiCompatibilityLevel("883");
                            d.setApiSiteid("0");*/
                                d.setHeaderType("");
                                d.setApiCallName(APINameStatic.GetDispute);
                                String xml = BindAccountAPI.getGetDispute(token, caseId);
                                //真实环境
                            /*Map<String, String> resMap = addApiTask.exec(d, xml, "https://api.ebay.com/ws/api.dll");*/
                                //测试环境
                                Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
                                String r1 = resMap.get("stat");
                                res = resMap.get("message");
                                if ("fail".equalsIgnoreCase(r1)) {
                                    logger.error("调用一般纠纷API失败!" + res+"\n\nXML:"+xml);
                                }
                                String Ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                                if ("Success".equalsIgnoreCase(Ack)) {
                                    Map<String, Object> disputeMap = GetDisputeAPI.parseXMLAndSave(res);
                                    TradingGetDispute dispute = (TradingGetDispute) disputeMap.get("dispute");
                                    List<TradingGetDispute> disputeList = iTradingGetDispute.selectGetDisputeByTransactionId(dispute.getTransactionid());
                                    if (disputeList != null && disputeList.size() > 0) {
                                        dispute.setId(disputeList.get(0).getId());
                                    }
                                    dispute.setCreateUser(taskMessageVO.getMessageTo());
                                    iTradingGetDispute.saveGetDispute(dispute);
                                    List<TradingGetDisputeMessage> messageList = (List<TradingGetDisputeMessage>) disputeMap.get("disputeMessage");
                                    List<TradingGetDisputeMessage> list = iTradingGetDisputeMessage.selectGetDisputeMessageByDisputeId(dispute.getId());
                                    if (list != null && list.size() > 0) {
                                        for (TradingGetDisputeMessage message : list) {
                                            iTradingGetDisputeMessage.deleteGetDisputeMessage(message);
                                        }
                                    }
                                    for (TradingGetDisputeMessage message : messageList) {
                                        message.setDisputeId(dispute.getId());
                                        message.setCreateUser(taskMessageVO.getMessageTo());
                                        iTradingGetDisputeMessage.saveGetDisputeMessage(message);
                                    }
                                } else {
                                    logger.error("获取一般纠纷参数出错!" + res+"\n\nXML:"+xml);
                                }
                            }
                        }
                    }
                }
            }else {
                String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                logger.error("CASE API调用失败!" + errors);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("解析纠纷xml出错,请稍后到ebay网站确认结果"+res);
            return;
        }
    }

    @Override
    public String getType() {
        return SiteMessageStatic.SYNCHRONIZE_USER_CASE_BEAN;
    }
}
