package com.usercases.controller;

import com.base.database.trading.model.*;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.UserCasesQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.BindAccountAPI;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DateUtils;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.SamplePaseXml;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.sitemessage.service.SiteMessageStatic;
import com.trading.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrtor on 2014/8/28.
 */
@Controller
@RequestMapping("userCases")
public class UserCasesController extends BaseAction{
    static Logger logger = Logger.getLogger(UserCasesController.class);
    @Value("${EBAY.API.URL}")
    private String apiUrl;
    @Autowired
    private UserInfoService userInfoService;
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
    private ITradingOrderGetOrders iTradingOrderGetOrders;
    @Autowired
    private ITradingOrderGetSellerTransactions iTradingOrderGetSellerTransactions;
    @Autowired
    private ITradingOrderGetItem iTradingOrderGetItem;
    @Autowired
    private ITradingOrderPictureDetails iTradingOrderPictureDetails ;
    @Autowired
    private ITradingOrderAddMemberMessageAAQToPartner iTradingOrderAddMemberMessageAAQToPartner;
    /*
    *纠纷列表
    */
    @RequestMapping("/userCasesList.do")
    public ModelAndView userCasesList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("/usercases/userCasesList",modelMap);
    }
    /*
    *同步纠纷初始化
    */
    @RequestMapping("/getUserCases.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    @ResponseBody
    public ModelAndView getUserCases(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        List<UsercontrollerEbayAccountExtend> ebays = userInfoService.getEbayAccountForCurrUser();
        modelMap.put("ebays",ebays);
        return forword("usercases/synUserCases",modelMap);
    }
    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadUserCasesList.do")
    @ResponseBody
    public void loadUserCasesList(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        /**分页组装*/
        String type=request.getParameter("type");
        if("all".equals(type)){
            type=null;
        }
        Map m = new HashMap();
        m.put("caseType",type);
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<UserCasesQuery> list=iTradingGetUserCases.selectGetUserCases(m, page);
        jsonBean.setList(list);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
    /**获取list数据的ajax方法*/
    @RequestMapping("/sendMessage.do")
    @ResponseBody
    public void sendMessage(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String number=request.getParameter("number");
        String carrier=request.getParameter("carrier");
        String sendmessage=request.getParameter("sendmessage");
        String transactionid=request.getParameter("transactionid");
        String status=request.getParameter("status");
        List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionid);
        if((number==null&&carrier==null&&sendmessage==null)||(sendmessage!=null&&number!=null)||(sendmessage!=null&&carrier!=null)){
            AjaxSupport.sendFailText("fail","请选择其中一个进行提交");
            return;
        }
        if(StringUtils.isNotBlank(status)){
            Map<String,String> map=sendMessage1(orders.get(0),"Tracking status","Tracking number:"+number+",carrier:"+carrier+",status:"+status);
            if("false".equals(map.get("flag"))){
                AjaxSupport.sendFailText("fail",map.get("message"));
                return;
            }else{
                AjaxSupport.sendSuccessText("", map.get("message"));
                return;
            }
        }else if(sendmessage==null&&!StringUtils.isNotBlank(status)){
            AjaxSupport.sendFailText("fail","status is empty");
        }
        if(sendmessage!=null){
            Map<String,String> map=sendMessage1(orders.get(0),"纠纷消息",sendmessage);
            if("false".equals(map.get("flag"))){
                AjaxSupport.sendFailText("fail",map.get("message"));
            }else{
                AjaxSupport.sendSuccessText("", map.get("message"));
            }
        }
    }
    /**获取list数据的ajax方法*/
    @RequestMapping("/sendMessage2.do")
    @ResponseBody
    public void sendMessage2(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String transactionid=request.getParameter("transactionid");
        List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionid);
        Map<String,String> map=sendMessage1(orders.get(0),"Tracking status","I shipped the item without tracking information");
        if("false".equals(map.get("flag"))){
            AjaxSupport.sendFailText("fail",map.get("message"));
        }else{
            AjaxSupport.sendSuccessText("", map.get("message"));
        }
    }
    /*
     * 同步纠纷详情初始化
     */
    @RequestMapping("/synDetails.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView synDetails(HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id1=request.getParameter("id");
        Long id= Long.valueOf(id1);
        TradingGetUserCases userCases=iTradingGetUserCases.selectUserCasesById(id);
        modelMap.put("userCases",userCases);
        return forword("usercases/synEBPCases",modelMap);
    }
    /*
    * 查看纠纷详情
    */
    @RequestMapping("/viewDetails.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewDetails(HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String transactionid=request.getParameter("transactionid");
        List<TradingGetDispute> disputeList=iTradingGetDispute.selectGetDisputeByTransactionId(transactionid);
        TradingGetDispute dispute=new TradingGetDispute();
        TradingOrderGetOrders order=new TradingOrderGetOrders();
        TradingOrderGetSellerTransactions sellerTransaction=new TradingOrderGetSellerTransactions();
        if(disputeList!=null&&disputeList.size()>0){
            dispute=disputeList.get(0);
        }
        String url="http://www.sandbox.ebay.com/itm/"+dispute.getItemid();
        List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionid);
        if(orders!=null&&orders.size()>0){
            order=orders.get(0);
        }
        List<TradingOrderGetSellerTransactions> sellerTransactionses= iTradingOrderGetSellerTransactions.selectTradingOrderGetSellerTransactionsByTransactionId(transactionid);
        if(sellerTransactionses!=null&&sellerTransactionses.size()>0){
            sellerTransaction=sellerTransactionses.get(0);
        }
        String pic="";
        if(order!=null){
            List<TradingOrderGetItem> items=iTradingOrderGetItem.selectOrderGetItemByItemId(order.getItemid());
            List<TradingOrderPictureDetails> detailses=iTradingOrderPictureDetails.selectOrderGetItemById(items.get(0).getPicturedetailsId());
            pic=detailses.get(0).getPictureurl();
        }
        modelMap.put("url",url);
        modelMap.put("order",order);
        modelMap.put("dispute",dispute);
        modelMap.put("transaction",sellerTransaction);
        modelMap.put("pic",pic);
        return forword("usercases/viewCases",modelMap);
    }
    /*
   * 处理纠纷
   */
    @RequestMapping("/handleDispute.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView handleDispute(HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String transactionid=request.getParameter("transactionid");
        List<TradingGetEBPCaseDetail> EBPlist=iTradingGetEBPCaseDetail.selectGetEBPCaseDetailByTransactionId(transactionid);
        List<TradingGetUserCases> casesList=iTradingGetUserCases.selectGetUserCasesByTransactionId(transactionid);
        TradingGetEBPCaseDetail ebpCaseDetail=new TradingGetEBPCaseDetail();
        TradingGetUserCases cases=new TradingGetUserCases();
        List<TradingCaseResponseHistory> responses=new ArrayList<TradingCaseResponseHistory>();
        if(EBPlist!=null&&EBPlist.size()>0){
            ebpCaseDetail=EBPlist.get(0);
            responses=iTradingCaseResponseHistory.selectCaseResponseHistoryById(ebpCaseDetail.getId());
        }
        if(casesList!=null&&casesList.size()>0){
            cases=casesList.get(0);
        }
        TradingOrderGetOrders order=new TradingOrderGetOrders();
        List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionid);
        if(orders!=null&&orders.size()>0){
            order=orders.get(0);
        }
        modelMap.put("ebpCaseDetail",ebpCaseDetail);
        modelMap.put("cases",cases);
        modelMap.put("responses",responses);
        modelMap.put("order",order);
        return forword("usercases/handleDispute",modelMap);
    }
    /*
  * 响应纠纷
  */
    @RequestMapping("/responseDispute.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView responseDispute(HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String transactionid=request.getParameter("transactionid");
        TradingOrderGetOrders order=new TradingOrderGetOrders();
        List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionid);
        if(orders!=null&&orders.size()>0){
            order=orders.get(0);
        }
        modelMap.put("transactionid",transactionid);
        return forword("usercases/responseDispute",modelMap);
    }
    /*
    *同步纠纷详情
    */
    @RequestMapping("/apiEBPCasessRequest.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void apiEBPCasessRequest(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String sellerId=request.getParameter("sellerId");
        String caseId=request.getParameter("caseId");
        String caseType=request.getParameter("caseType");
        List<UsercontrollerEbayAccountExtend> ebayList=userInfoService.getEbayAccountForCurrUser();
        String token="";
        for(UsercontrollerEbayAccountExtend ebay:ebayList){
            if(sellerId.equals(ebay.getEbayName())){
                token=userInfoService.getTokenByEbayID(ebay.getId());
            }
        }
        token="AgAAAA**AQAAAA**aAAAAA**CLSRUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlYCjDJGCqA+dj6x9nY+seQ**FdYBAA**AAMAAA**w2sMbwlQ7TBHWxj9EsVedHQRI3+lonY9MDfiyayQbnFkjEanjL/yMCpS/D2B9xHRzRx+ppxWZkRPgeAKJvNotPLLrVTuEzOl5M7pi6Tw8+pzcmIEsOh7HQO78JlyFlvLc/ruE6/hG0E/HO1UX76YBwxp00N9f1NNUpo5u36D/TYsx5O2jXFTKkCOHwz6RW9vtN6TU39aLm+JQme2+NfFFXnbX8MHzoUiX7Sty0R88ZpX5wLp8ZdgXCEc5zZDQziYB1MSXF9hsmby5wKbxFF+OvW/zKADThk1gprgAgnEOucyoao+cUMHopLlYgMbjnLzdCXP5F9z+fkYTnKF6AEl5eHBpcKQGbPzswnKebRoBVw+bI2I1C/iq+PvBUyndFAexjrvlDQbEKr6qb6AWRVTTfkW2ce6a0ixRuCTq35zEpWpfAqkSKo+X23d/Q4V8R30rDXotOWDZL6o408cMO+UQ17uVA2arA1JNkYfc/AZ0T0z7ze5o/yp93jJPlDgi05Ut4fpCAMZw3X85GxrTlbEtawWgoyUbmMuv4f6QHZLZAerOaJA8DRJkzkzjJJ025bp1HvAECOc4ggdv0cofu4q96shssgNYYZJUPM+q4+0fnGK0pxQTNY9SV6vSaVCVoTZJo6vefW7OiHX2/eLoPKFuUfsKXXEv9OY71gD1xzYg/rpCMAqCTq1dKqqyT1R5fxANnoRX7vwkq+7jkCj2fAfKTnHi9mSuBFsilKLmnsqqWy3IGShMgdxiQwBEk6IWi9C";
        UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
        AddApiTask addApiTask = new AddApiTask();
        if(caseType.contains("EBP")){
           /* d.setSoaGlobalId("EBAY-US");
            d.setSoaServiceVersion("1.0.0");*/
           /* d.setSoaServiceName("ResolutionCaseManagementService");*/
            d.setSoaOperationName("getEBPCaseDetail");
            d.setSoaSecurityToken(token);
           /* d.setSoaRequestDataFormat("XML");*/
            d.setHeaderType("DisputeApiHeader");
/*            d.setSoaResponseDataformat("XML");*/
            Map ebpMap=new HashMap();
            ebpMap.put("token",token);
            ebpMap.put("caseId", caseId);
            ebpMap.put("caseType",sellerId);
            String ebpXml = BindAccountAPI.getEBPCase(ebpMap);
            //---修改前---
           /* Map<String, String> resEbpMap = addApiTask.exec(d, ebpXml, "https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1");
            String ebpR1 = resEbpMap.get("stat");
            String ebpRes = resEbpMap.get("message");

            if ("fail".equalsIgnoreCase(ebpR1)) {
                AjaxSupport.sendFailText("fail", ebpRes);
                return;
            }
            String ebpAck = SamplePaseXml.getVFromXmlString(ebpRes, "ack");
            if ("Success".equalsIgnoreCase(ebpAck)) {
                Map<String,Object> ebpmap= GetEBPCaseDetailAPI.parseXMLAndSave(ebpRes);
                TradingGetEBPCaseDetail caseDetail= (TradingGetEBPCaseDetail) ebpmap.get("ebpCaseDetail");
                List<TradingCaseResponseHistory> historyList= (List<TradingCaseResponseHistory>) ebpmap.get("historyList");
                List<TradingCasePaymentDetail> paymentDetailList= (List<TradingCasePaymentDetail>) ebpmap.get("paymentDetailList");
                AjaxSupport.sendSuccessText("success", "同步成功！");
            }else{
                String errors = SamplePaseXml.getVFromXmlString(ebpRes, "Errors");
                logger.error("获取EBP纠纷失败!" + errors);
                AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
            }*/
            TaskMessageVO taskMessageVO=new TaskMessageVO();
            taskMessageVO.setMessageContext("EBP纠纷");
            taskMessageVO.setMessageTitle("同步EBP纠纷");
            taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_USER_CASE_EBP_TYPE);
            taskMessageVO.setBeanNameType(SiteMessageStatic.SYNCHRONIZE_USER_CASE_EBP_BEAN);
            taskMessageVO.setMessageFrom("system");
            SessionVO sessionVO= SessionCacheSupport.getSessionVO();
            taskMessageVO.setMessageTo(sessionVO.getId());
            addApiTask.execDelayReturn(d, ebpXml,"https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1", taskMessageVO);
            AjaxSupport.sendSuccessText("message", "操作成功！结果请稍后查看消息！");
            //---修改后---
        }else{
           /* UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);//开发者帐号id
            d.setApiSiteid("0");
            d.setApiCallName(APINameStatic.GetOrders);
            request.getSession().setAttribute("dveId", d);*/
            d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
            d.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
            d.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
            d.setApiCompatibilityLevel("883");
            d.setApiSiteid("0");
            d.setHeaderType("");
            d.setApiCallName(APINameStatic.GetDispute);
            String xml = BindAccountAPI.getGetDispute(token, caseId);
            //---修改前---
            /*Map<String, String> resMap = addApiTask.exec(d, xml, "https://api.ebay.com/ws/api.dll");
            String r1 = resMap.get("stat");
            String res = resMap.get("message");
            if ("fail".equalsIgnoreCase(r1)) {
                AjaxSupport.sendFailText("fail", res);
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
                    iTradingGetDisputeMessage.saveGetDisputeMessage(message);
                }
                AjaxSupport.sendSuccessText("success", "同步成功！");
            }else{
                String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                logger.error("获取一般纠纷失败!" + errors);
                AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
            }*/
            //---修改后---
            TaskMessageVO taskMessageVO=new TaskMessageVO();
            taskMessageVO.setMessageContext("一般纠纷");
            taskMessageVO.setMessageTitle("同步一般纠纷");
            taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_USER_CASE_DISPUTE_TYPE);
            taskMessageVO.setBeanNameType(SiteMessageStatic.SYNCHRONIZE_USER_CASE_DISPUTE_BEAN);
            taskMessageVO.setMessageFrom("system");
            SessionVO sessionVO= SessionCacheSupport.getSessionVO();
            taskMessageVO.setMessageTo(sessionVO.getId());
            addApiTask.execDelayReturn(d, xml,"https://api.ebay.com/ws/api.dll", taskMessageVO);
            AjaxSupport.sendSuccessText("message", "操作成功！结果请稍后查看消息！");
        }
    }
    /*
     *同步纠纷目录
     */
    @RequestMapping("/apiGetuserCasessRequest.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void apiGetuserCasessRequest(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String ebayId=request.getParameter("ebayId");
        Long ebay=Long.valueOf(ebayId);
        /*UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);//开发者帐号id*/
        UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
      /*  d.setSoaGlobalId("EBAY-US");
        d.setSoaServiceVersion("1.0.0");*/
        /*d.setSoaServiceName("ResolutionCaseManagementService");*/
        d.setSoaOperationName("getUserCases");
        /*d.setSoaSecurityToken("AgAAAA**AQAAAA**aAAAAA**CLSRUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlYCjDJGCqA+dj6x9nY+seQ**FdYBAA**AAMAAA**w2sMbwlQ7TBHWxj9EsVedHQRI3+lonY9MDfiyayQbnFkjEanjL/yMCpS/D2B9xHRzRx+ppxWZkRPgeAKJvNotPLLrVTuEzOl5M7pi6Tw8+pzcmIEsOh7HQO78JlyFlvLc/ruE6/hG0E/HO1UX76YBwxp00N9f1NNUpo5u36D/TYsx5O2jXFTKkCOHwz6RW9vtN6TU39aLm+JQme2+NfFFXnbX8MHzoUiX7Sty0R88ZpX5wLp8ZdgXCEc5zZDQziYB1MSXF9hsmby5wKbxFF+OvW/zKADThk1gprgAgnEOucyoao+cUMHopLlYgMbjnLzdCXP5F9z+fkYTnKF6AEl5eHBpcKQGbPzswnKebRoBVw+bI2I1C/iq+PvBUyndFAexjrvlDQbEKr6qb6AWRVTTfkW2ce6a0ixRuCTq35zEpWpfAqkSKo+X23d/Q4V8R30rDXotOWDZL6o408cMO+UQ17uVA2arA1JNkYfc/AZ0T0z7ze5o/yp93jJPlDgi05Ut4fpCAMZw3X85GxrTlbEtawWgoyUbmMuv4f6QHZLZAerOaJA8DRJkzkzjJJ025bp1HvAECOc4ggdv0cofu4q96shssgNYYZJUPM+q4+0fnGK0pxQTNY9SV6vSaVCVoTZJo6vefW7OiHX2/eLoPKFuUfsKXXEv9OY71gD1xzYg/rpCMAqCTq1dKqqyT1R5fxANnoRX7vwkq+7jkCj2fAfKTnHi9mSuBFsilKLmnsqqWy3IGShMgdxiQwBEk6IWi9C");*/
        String token="AgAAAA**AQAAAA**aAAAAA**CLSRUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlYCjDJGCqA+dj6x9nY+seQ**FdYBAA**AAMAAA**w2sMbwlQ7TBHWxj9EsVedHQRI3+lonY9MDfiyayQbnFkjEanjL/yMCpS/D2B9xHRzRx+ppxWZkRPgeAKJvNotPLLrVTuEzOl5M7pi6Tw8+pzcmIEsOh7HQO78JlyFlvLc/ruE6/hG0E/HO1UX76YBwxp00N9f1NNUpo5u36D/TYsx5O2jXFTKkCOHwz6RW9vtN6TU39aLm+JQme2+NfFFXnbX8MHzoUiX7Sty0R88ZpX5wLp8ZdgXCEc5zZDQziYB1MSXF9hsmby5wKbxFF+OvW/zKADThk1gprgAgnEOucyoao+cUMHopLlYgMbjnLzdCXP5F9z+fkYTnKF6AEl5eHBpcKQGbPzswnKebRoBVw+bI2I1C/iq+PvBUyndFAexjrvlDQbEKr6qb6AWRVTTfkW2ce6a0ixRuCTq35zEpWpfAqkSKo+X23d/Q4V8R30rDXotOWDZL6o408cMO+UQ17uVA2arA1JNkYfc/AZ0T0z7ze5o/yp93jJPlDgi05Ut4fpCAMZw3X85GxrTlbEtawWgoyUbmMuv4f6QHZLZAerOaJA8DRJkzkzjJJ025bp1HvAECOc4ggdv0cofu4q96shssgNYYZJUPM+q4+0fnGK0pxQTNY9SV6vSaVCVoTZJo6vefW7OiHX2/eLoPKFuUfsKXXEv9OY71gD1xzYg/rpCMAqCTq1dKqqyT1R5fxANnoRX7vwkq+7jkCj2fAfKTnHi9mSuBFsilKLmnsqqWy3IGShMgdxiQwBEk6IWi9C";
        d.setSoaSecurityToken(token);
       /* d.setSoaRequestDataFormat("XML");*/
        d.setHeaderType("DisputeApiHeader");
     /*   d.setSoaResponseDataformat("XML");*/
        Map map=new HashMap();
        Date startTime2= DateUtils.subDays(new Date(), 9);
        Date endTime= DateUtils.addDays(startTime2, 9);
        Date end1= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
        String start= DateUtils.DateToString(startTime2);
        String end=DateUtils.DateToString(end1);
        map.put("fromTime", start);
        map.put("toTime", end);
        map.put("page","1");
        String xml = BindAccountAPI.getUserCases(map);
        AddApiTask addApiTask = new AddApiTask();
        //---修改前---
        /*Map<String, String> resMap = addApiTask.exec(d, xml, "https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1?REST-PAYLOAD");
        String r1 = resMap.get("stat");
        String res = resMap.get("message");
        if ("fail".equalsIgnoreCase(r1)) {
            AjaxSupport.sendFailText("fail", res);
            return;
        }
        String ack = SamplePaseXml.getVFromXmlString(res, "ack");
        if ("Success".equalsIgnoreCase(ack)) {
            Map<String,Object> map1= UserCasesAPI.parseXMLAndSave(res);
            String totalPages= (String) map1.get("totalPages");
            int totalPage= Integer.parseInt(totalPages);
            for(int i=1;i<=totalPage;i++){*/
                /*if(i!=1){
                    map.put("page",i+"");
                    xml = BindAccountAPI.getUserCases(map);
                    resMap = addApiTask.exec(d, xml, "https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1?REST-PAYLOAD");
                    r1 = resMap.get("stat");
                    res = resMap.get("message");
                    if ("fail".equalsIgnoreCase(r1)) {
                        AjaxSupport.sendFailText("fail", res);
                        return;
                    }
                    if (!"Success".equalsIgnoreCase(ack)){
                        String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                        logger.error("Order获取apisessionid失败!" + errors);
                        AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
                        return;
                    }
                    map1= UserCasesAPI.parseXMLAndSave(res);
                }*/
             /*   List<TradingGetUserCases> userCasesList= (List<TradingGetUserCases>) map1.get("cases");
                for(TradingGetUserCases userCases:userCasesList){
                    List<TradingGetUserCases> casesList=iTradingGetUserCases.selectGetUserCasesByTransactionId(userCases.getTransactionid());
                    if(casesList!=null&&casesList.size()>0){
                        userCases.setId(casesList.get(0).getId());
                    }
                    iTradingGetUserCases.saveGetUserCases(userCases);
                }
            }

            AjaxSupport.sendSuccessText("success", "同步成功！");
        }else{
            String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
            logger.error("获取纠纷总数失败!" + errors);
            AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
        }*/
        //---修改后--

        TaskMessageVO taskMessageVO=new TaskMessageVO();
        taskMessageVO.setMessageContext("纠纷");
        taskMessageVO.setMessageTitle("同步纠纷");
        taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_USER_CASE_TYPE);
        taskMessageVO.setBeanNameType(SiteMessageStatic.SYNCHRONIZE_USER_CASE_BEAN);
        taskMessageVO.setMessageFrom("system");
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        taskMessageVO.setMessageTo(sessionVO.getId());
        addApiTask.execDelayReturn(d, xml,"https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1?REST-PAYLOAD", taskMessageVO);
        AjaxSupport.sendSuccessText("message", "操作成功！结果请稍后查看消息！");
    }
    private Map<String,String> sendMessage1(TradingOrderGetOrders order,String subject,String body) throws Exception {
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);//开发者帐号id
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.AddMemberMessageAAQToPartner);
        Map map=new HashMap();
        Map<String,String> returnMap=new HashMap<String, String>();
        String ebayName=order.getSelleruserid();
        List<UsercontrollerEbayAccountExtend> dList= userInfoService.getEbayAccountForCurrUser();
        String token=null;
        for(UsercontrollerEbayAccountExtend list:dList){
            if(StringUtils.isNotBlank(ebayName)&&ebayName.equals(list.getEbayName())){
                token=list.getEbayToken();
            }
        }
        map.put("token", token);
        map.put("subject",subject);
        map.put("body",body);
        map.put("itemid",order.getItemid());
        map.put("buyeruserid",order.getBuyeruserid());
        String xml = BindAccountAPI.getAddMemberMessageAAQToPartner(map);//获取接受消息
        AddApiTask addApiTask = new AddApiTask();
          /*  Map<String, String> resMap = addApiTask.exec(d, xml, "https://api.ebay.com/ws/api.dll");*/
        Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
        String r1 = resMap.get("stat");
        String res = resMap.get("message");
        if ("fail".equalsIgnoreCase(r1)) {
            AjaxSupport.sendFailText("fail", res);
            returnMap.put("flag","false");
            returnMap.put("message",res);
        }
        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
        if ("Success".equalsIgnoreCase(ack)) {
            TradingOrderAddMemberMessageAAQToPartner message1=new TradingOrderAddMemberMessageAAQToPartner();
            message1.setBody(body);
            message1.setItemid(order.getItemid());
            message1.setRecipientid(order.getBuyeruserid());
            message1.setSubject(subject);
            message1.setSender(order.getSelleruserid());
            message1.setMessagetype(1);
            message1.setTransactionid(order.getTransactionid());
            iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message1);
            returnMap.put("flag","true");
            returnMap.put("message", "发送成功");
        }else{
            returnMap.put("flag","false");
            returnMap.put("message","获取必要的参数失败！请稍后重试");
        }
        return returnMap;
    }
}
