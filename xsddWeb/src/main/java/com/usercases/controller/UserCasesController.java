package com.usercases.controller;

import com.base.database.trading.model.TradingCasePaymentDetail;
import com.base.database.trading.model.TradingCaseResponseHistory;
import com.base.database.trading.model.TradingGetEBPCaseDetail;
import com.base.database.trading.model.TradingGetUserCases;
import com.base.domains.CommonParmVO;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.sampleapixml.BindAccountAPI;
import com.base.sampleapixml.GetEBPCaseDetailAPI;
import com.base.sampleapixml.UserCasesAPI;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.common.DateUtils;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.*;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    /*
     *同步纠纷
     */
    @RequestMapping("/apiGetuserCasessRequest.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void apiGetuserCasessRequest(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String ebayId=request.getParameter("ebayId");
        Long ebay=Long.valueOf(ebayId);
        /*UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);//开发者帐号id*/
        UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
        d.setSoaGlobalId("EBAY-US");
        d.setSoaServiceVersion("1.3.0");
        d.setSoaServiceName("ResolutionCaseManagementService");
        d.setSoaOperationName("getUserCases");
        /*d.setSoaSecurityToken("AgAAAA**AQAAAA**aAAAAA**CLSRUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlYCjDJGCqA+dj6x9nY+seQ**FdYBAA**AAMAAA**w2sMbwlQ7TBHWxj9EsVedHQRI3+lonY9MDfiyayQbnFkjEanjL/yMCpS/D2B9xHRzRx+ppxWZkRPgeAKJvNotPLLrVTuEzOl5M7pi6Tw8+pzcmIEsOh7HQO78JlyFlvLc/ruE6/hG0E/HO1UX76YBwxp00N9f1NNUpo5u36D/TYsx5O2jXFTKkCOHwz6RW9vtN6TU39aLm+JQme2+NfFFXnbX8MHzoUiX7Sty0R88ZpX5wLp8ZdgXCEc5zZDQziYB1MSXF9hsmby5wKbxFF+OvW/zKADThk1gprgAgnEOucyoao+cUMHopLlYgMbjnLzdCXP5F9z+fkYTnKF6AEl5eHBpcKQGbPzswnKebRoBVw+bI2I1C/iq+PvBUyndFAexjrvlDQbEKr6qb6AWRVTTfkW2ce6a0ixRuCTq35zEpWpfAqkSKo+X23d/Q4V8R30rDXotOWDZL6o408cMO+UQ17uVA2arA1JNkYfc/AZ0T0z7ze5o/yp93jJPlDgi05Ut4fpCAMZw3X85GxrTlbEtawWgoyUbmMuv4f6QHZLZAerOaJA8DRJkzkzjJJ025bp1HvAECOc4ggdv0cofu4q96shssgNYYZJUPM+q4+0fnGK0pxQTNY9SV6vSaVCVoTZJo6vefW7OiHX2/eLoPKFuUfsKXXEv9OY71gD1xzYg/rpCMAqCTq1dKqqyT1R5fxANnoRX7vwkq+7jkCj2fAfKTnHi9mSuBFsilKLmnsqqWy3IGShMgdxiQwBEk6IWi9C");*/
        String token="AgAAAA**AQAAAA**aAAAAA**CLSRUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlYCjDJGCqA+dj6x9nY+seQ**FdYBAA**AAMAAA**w2sMbwlQ7TBHWxj9EsVedHQRI3+lonY9MDfiyayQbnFkjEanjL/yMCpS/D2B9xHRzRx+ppxWZkRPgeAKJvNotPLLrVTuEzOl5M7pi6Tw8+pzcmIEsOh7HQO78JlyFlvLc/ruE6/hG0E/HO1UX76YBwxp00N9f1NNUpo5u36D/TYsx5O2jXFTKkCOHwz6RW9vtN6TU39aLm+JQme2+NfFFXnbX8MHzoUiX7Sty0R88ZpX5wLp8ZdgXCEc5zZDQziYB1MSXF9hsmby5wKbxFF+OvW/zKADThk1gprgAgnEOucyoao+cUMHopLlYgMbjnLzdCXP5F9z+fkYTnKF6AEl5eHBpcKQGbPzswnKebRoBVw+bI2I1C/iq+PvBUyndFAexjrvlDQbEKr6qb6AWRVTTfkW2ce6a0ixRuCTq35zEpWpfAqkSKo+X23d/Q4V8R30rDXotOWDZL6o408cMO+UQ17uVA2arA1JNkYfc/AZ0T0z7ze5o/yp93jJPlDgi05Ut4fpCAMZw3X85GxrTlbEtawWgoyUbmMuv4f6QHZLZAerOaJA8DRJkzkzjJJ025bp1HvAECOc4ggdv0cofu4q96shssgNYYZJUPM+q4+0fnGK0pxQTNY9SV6vSaVCVoTZJo6vefW7OiHX2/eLoPKFuUfsKXXEv9OY71gD1xzYg/rpCMAqCTq1dKqqyT1R5fxANnoRX7vwkq+7jkCj2fAfKTnHi9mSuBFsilKLmnsqqWy3IGShMgdxiQwBEk6IWi9C";
        d.setSoaSecurityToken(token);
        d.setSoaRequestDataFormat("XML");
        d.setHeaderType("DisputeApiHeader");
        d.setSoaResponseDataformat("XML");
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
        Map<String, String> resMap = addApiTask.exec(d, xml, "https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1?REST-PAYLOAD");
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
           /* for(int i=1;i<=totalPage;i++){*/
              /*  if(i!=1){
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
                List<TradingGetUserCases> userCasesList= (List<TradingGetUserCases>) map1.get("cases");
                for(TradingGetUserCases userCases:userCasesList){
                    List<TradingGetUserCases> casesList=iTradingGetUserCases.selectOrderGetItemByTransactionId(userCases.getTransactionid());
                    if(casesList!=null&&casesList.size()>0){
                        userCases.setId(casesList.get(0).getId());
                    }
                    if(userCases.getCasetype().contains("EBP")){
                        d.setSoaOperationName("getEBPCaseDetail");
                        Map ebpMap=new HashMap();
                        ebpMap.put("token", token);
                        ebpMap.put("caseId", userCases.getCaseid());
                        ebpMap.put("caseType",userCases.getCasetype());
                        String ebpXml = BindAccountAPI.getEBPCase(ebpMap);
                        Map<String, String> resEbpMap = addApiTask.exec(d, ebpXml, "https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1?REST-PAYLOAD");
                        String ebpR1 = resEbpMap.get("stat");
                        String ebpRes = resEbpMap.get("message");
                        if ("fail".equalsIgnoreCase(ebpR1)) {
                            AjaxSupport.sendFailText("fail", ebpRes);
                            return;
                        }
                        String ebpAck = SamplePaseXml.getVFromXmlString(ebpRes, "ack");
                        if ("Success".equalsIgnoreCase(ebpAck)) {
                            Map<String,Object> ebpmap=GetEBPCaseDetailAPI.parseXMLAndSave(ebpRes);
                            TradingGetEBPCaseDetail caseDetail= (TradingGetEBPCaseDetail) ebpmap.get("ebpCaseDetail");


                            List<TradingCaseResponseHistory> historyList= (List<TradingCaseResponseHistory>) ebpmap.get("historyList");
                            List<TradingCasePaymentDetail> paymentDetailList= (List<TradingCasePaymentDetail>) ebpmap.get("paymentDetailList");
                           /* for(TradingCaseResponseHistory history:historyList){
                                history.setEbpcasedetailId();
                            }*/
                        }else{
                            String errors = SamplePaseXml.getVFromXmlString(ebpRes, "Errors");
                            logger.error("获取EBP纠纷失败!" + errors);
                            AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
                            return;
                        }
                    }else{
                        continue;
                    }

                    iTradingGetUserCases.saveGetUserCases(userCases);
                }
            /*}*/

            AjaxSupport.sendSuccessText("success", "同步成功！");
        }else{
            String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
            logger.error("获取纠纷总数失败!" + errors);
            AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");

        }
    }
}
