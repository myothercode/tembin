package com.order.controller;

import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.OrderItemQuery;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.SessionCacheSupport;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingOrderGetItem;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/15.
 */
@Controller
@RequestMapping("orderItem")
public class GetOrderItemController extends BaseAction {
    static Logger logger = Logger.getLogger(GetOrderItemController.class);
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ITradingOrderGetItem iTradingOrderGetItem;
    @Value("${EBAY.API.URL}")
    private String apiUrl;

    @RequestMapping("/orderItemList.do")
     public ModelAndView OrdersList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("/orders/orderItem/orderItemList",modelMap);
    }
  /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadOrdersList.do")
    @ResponseBody
    public void loadOrdersList(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String content=request.getParameter("content");
        if(!StringUtils.isNotBlank(content)){
            content=null;
        }
        Map m = new HashMap();
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<UsercontrollerEbayAccountExtend> ebays=userInfoService.getEbayAccountForCurrUser(new HashMap(),Page.newAOnePage());
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        Map map=new HashMap();
        map.put("ebays",ebays);
        map.put("content",content);
        m.put("userId",sessionVO.getId());
        List<OrderItemQuery> lists= this.iTradingOrderGetItem.selectOrderItemList(map,page);
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

   /**
     * 初始化同步订单商品界面
     */
    @RequestMapping("/GetOrder.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    @ResponseBody
    public ModelAndView GetOrder(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        List<UsercontrollerEbayAccountExtend> ebays = userInfoService.getEbayAccountForCurrUser(new HashMap(),Page.newAOnePage());
        modelMap.put("ebays",ebays);
        return forword("orders/orderItem/synOrderItem",modelMap);
    }

   /* @RequestMapping("/apiOrderItemRequest.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    *//**获取接受信息总数*//*
    public void apiGetMyMessagesRequest(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String ebayId=request.getParameter("ebayId");
        Long ebay=Long.valueOf(ebayId);
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);//开发者帐号id
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.GetOrders);
        request.getSession().setAttribute("dveId", d);
        Map map=new HashMap();
        Date startTime2= DateUtils.subDays(new Date(), 8);
        Date endTime= DateUtils.addDays(startTime2, 9);
        Date end1= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
        String start= DateUtils.DateToString(startTime2);
        String end=DateUtils.DateToString(end1);
        String token=userInfoService.getTokenByEbayID(ebay);
        map.put("token", token);
        map.put("fromTime", start);
        map.put("toTime", end);
        String xml = BindAccountAPI.getGetOrders(map);//获取接受消息
        AddApiTask addApiTask = new AddApiTask();
          *//*  Map<String, String> resMap = addApiTask.exec(d, xml, "https://api.ebay.com/ws/api.dll");*//*
        Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
        String r1 = resMap.get("stat");
        String res = resMap.get("message");
        if ("fail".equalsIgnoreCase(r1)) {
            AjaxSupport.sendFailText("fail", res);
            return;
        }
        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
        if ("Success".equalsIgnoreCase(ack)) {
            List<TradingOrderGetOrders> orders= GetOrdersAPI.parseXMLAndSave(res);
            for(TradingOrderGetOrders order:orders){
                List<TradingOrderGetOrders> ls=iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(order.getOrderid());
                if(ls.size()>0){
                    order.setId(ls.get(0).getId());
                }
                iTradingOrderGetOrders.saveOrderGetOrders(order);
            }
            AjaxSupport.sendSuccessText("success", "同步成功！");
        } else {
            String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
            AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
        }
    }*/
}
