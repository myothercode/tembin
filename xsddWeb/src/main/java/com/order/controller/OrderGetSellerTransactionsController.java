package com.order.controller;

import com.base.database.trading.model.TradingOrderGetSellerTransactions;
import com.base.domains.CommonParmVO;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.sampleapixml.BindAccountAPI;
import com.base.sampleapixml.GetSellerTransactionsAPI;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingOrderGetSellerTransactions;
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
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/15.
 */
@Controller
@RequestMapping("sellertransactions")
public class OrderGetSellerTransactionsController extends BaseAction {
    static Logger logger = Logger.getLogger(GetOrdersController.class);
    @Autowired
    private UserInfoService userInfoService;
    @Value("${EBAY.API.URL}")
    private String apiUrl;
    @Autowired
    private ITradingOrderGetSellerTransactions iTradingOrderGetSellerTransactions;

    @RequestMapping("/orderSellerTransactionsList.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    @ResponseBody
    public ModelAndView orderSellerTransactionsList(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        List<UsercontrollerEbayAccountExtend> ebays = userInfoService.getEbayAccountForCurrUser();
        modelMap.put("ebays",ebays);
        return forword("/orders/orderSellerTransactions/getordersellertransaction",modelMap);
    }

    @RequestMapping("/apiOrderSellerTransactions.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    /*获取接受信息总数*/
    public void apiOrderSellerTransactions(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String ebayId=request.getParameter("ebayId");
        Long ebay=Long.valueOf(ebayId);
        /*UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);//开发者帐号id
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.GetSellerTransactions);
        String token=userInfoService.getTokenByEbayID(ebay);
        String xml = BindAccountAPI.GetSellerTransactions(token);//获取接受消息
        AddApiTask addApiTask = new AddApiTask();
           *//* Map<String, String> resMap = addApiTask.exec(d, xml, "https://api.ebay.com/ws/api.dll");*//*
        Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);*/
        UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
        d.setApiCallName("GetSellerTransactions");
        d.setApiCompatibilityLevel("883");
        d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
        d.setApiAppName("sandpoin-1f58-4e64-a45b-56507b02bbeb");
        d.setApiCertName("936fc911-c05c-455c-8838-3a698f2da43a");
        d.setApiSiteid("0");
        String token="AgAAAA**AQAAAA**aAAAAA**/axvUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlIWnC5iEpAidj6x9nY+seQ**I9ABAA**AAMAAA**fxldhpfo5xKyb9gLGSZgZtGZ35LrWfrf6xXxYJN/x1dazCBJKQIPEKf8Jf1YhWtmekAMZAJh4XkfsCuJfo+ROf/KEATGPEJEFI/bK+5DdZRndlY4AY9V3SM+iNPq0kXNAEy7hfWNqpb1PeA1TRWU21O1z07Vcnq+8Rr6XpwUWCL16T+KKsYpeM7CIudbtNY6+jsl0Vp65tOgYLfBmqgz6Q3XwVZXWQ914BetqWQsGudhvxsrwItxQruXZgOgiUZjJl8fjL1YWIYj+sa3CJPPDJy1l6+/NRMfD7cbfMbaF4m5tCXgcziyq/IQVnJHHxonUvNJj6zOQD/j0baLBdqOz+bGEWOSaPjEyRLvpwPfXVahl41OjJmKTqQTT1otJMw0LtDIWuhE6VhsQqFD79zw4GzEUrgWdjf9GUaHiirsC3U+1XFPiuA29djDJS9Rk8flXxZJstCuPCX9V0L8fcTiqrKr6k5c+yE2doyBV5FkM5JfJ+SJLk6r3qiIBQFwpvPQc/+R6UYhvY8msN1TiIVEQiBNAH1VfjRAq+SAmKhWW2UP/fOgy8aop2W/HnpG89WQ3UDIFRkOWijU/sepdwGVVYvGvtPEd9xHX1CZO6ZIJ6pMdPTaHxisPSQxJGvZ1GJW/daKgeNbSDDwoKYkzrG5CGJysOj3MOWeJNXKDbu9scB5nfiOX7JLVKurr1p0zxWXF4Mhhk3MWfqjP2SYYL3RIiTDKXHZiju4LluIxUcx9VDo85wXGV6JdO5xT4VrLgEA";
        String xml = BindAccountAPI.GetSellerTransactions(token);//获取接受消息
        AddApiTask addApiTask = new AddApiTask();
        /* Map<String, String> resMap = addApiTask.exec(d, xml, "https://api.ebay.com/ws/api.dll");*/
        Map<String, String> resMap = addApiTask.exec(d, xml, "https://api.sandbox.ebay.com/ws/api.dll");

        //------------------------
        String r1 = resMap.get("stat");
        String res = resMap.get("message");
        if ("fail".equalsIgnoreCase(r1)) {
            AjaxSupport.sendFailText("fail", res);
            return;
        }
        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
        if ("Success".equalsIgnoreCase(ack)) {
            List<TradingOrderGetSellerTransactions> lists= GetSellerTransactionsAPI.parseXMLAndSave(res);
            for(TradingOrderGetSellerTransactions list:lists){
                List<TradingOrderGetSellerTransactions>  transactionseList=iTradingOrderGetSellerTransactions.selectTradingOrderGetSellerTransactionsByTransactionId(list.getTransactionid());
                if(transactionseList!=null&&transactionseList.size()>0){
                    list.setId(transactionseList.get(0).getId());
                }
                iTradingOrderGetSellerTransactions.saveOrderGetSellerTransactions(list);
            }
            AjaxSupport.sendSuccessText("success", "同步成功！");
        } else {
            String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
            logger.error("获取apisessionid失败!" + errors);
            AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
        }
        AjaxSupport.sendSuccessText("success", "同步成功！");
    }
}
