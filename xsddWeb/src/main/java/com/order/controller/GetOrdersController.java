package com.order.controller;

import com.base.database.trading.model.*;
import com.base.domains.CommonParmVO;
import com.base.domains.querypojos.OrderGetOrdersQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.BindAccountAPI;
import com.base.sampleapixml.GetOrderItemAPI;
import com.base.sampleapixml.GetOrdersAPI;
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
 * Created by Administrtor on 2014/8/13.
 */
@Controller
@RequestMapping("order")
public class GetOrdersController extends BaseAction {

    static Logger logger = Logger.getLogger(GetOrdersController.class);
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ITradingOrderGetOrders iTradingOrderGetOrders;
    @Autowired
    private ITradingOrderShippingServiceOptions iTradingOrderShippingServiceOptions;
    @Autowired
    private ITradingOrderGetItem iTradingOrderGetItem;
    @Autowired
    private ITradingOrderListingDetails iTradingOrderListingDetails;
    @Autowired
    private ITradingOrderSeller iTradingOrderSeller;
    @Autowired
    private ITradingOrderSellingStatus iTradingOrderSellingStatus;
    @Autowired
    private ITradingOrderShippingDetails iTradingOrderShippingDetails;
    @Autowired
    private ITradingOrderPictureDetails iTradingOrderPictureDetails;
    @Autowired
    private ITradingOrderReturnpolicy iTradingOrderReturnpolicy;
    @Autowired
    private ITradingOrderSellerInformation iTradingOrderSellerInformation;
    @Autowired
    private  ITradingOrderCalculatedShippingRate iTradingOrderCalculatedShippingRate;
    @Value("${EBAY.API.URL}")
    private String apiUrl;

    @RequestMapping("/getOrdersList.do")
    public ModelAndView OrdersList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("/orders/order/getOrdersList",modelMap);
    }
    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadOrdersList.do")
    @ResponseBody
    public void loadOrdersList(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String orderStatus=request.getParameter("orderStatus");
        String starttime=request.getParameter("starttime");
        String endtime=request.getParameter("endtime");
        Map m = new HashMap();
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<UsercontrollerEbayAccountExtend> ebays=userInfoService.getEbayAccountForCurrUser();
        Map map=new HashMap();
        if(orderStatus==null||"All".equals(orderStatus)){
            orderStatus=null;
        }
        map.put("ebays",ebays);
       /* map.put("orderStatus",orderStatus);*/
        map.put("starttime",starttime);
        map.put("endtime",endtime);
        List<OrderGetOrdersQuery> lists= this.iTradingOrderGetOrders.selectOrderGetOrdersByGroupList(map,page);
        for(OrderGetOrdersQuery list:lists){
            String itemid=list.getItemid();
            List<TradingOrderGetItem> itemList= iTradingOrderGetItem.selectOrderGetItemByItemId(itemid);
            if(itemList!=null&&itemList.size()>0){
                Long pictureid=itemList.get(0).getPicturedetailsId();
                List<TradingOrderPictureDetails> pictureDetailses=iTradingOrderPictureDetails.selectOrderGetItemById(pictureid);
                if(pictureDetailses!=null&&pictureDetailses.size()>0){
                    list.setPictrue(pictureDetailses.get(0).getPictureurl());
                }
            }
            String url="http://www.sandbox.ebay.com/itm/"+list.getItemid();
            list.setItemUrl(url);
        }
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    /**
     * 初始化同步订单界面
     */
    @RequestMapping("/GetOrder.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    @ResponseBody
    public ModelAndView GetOrder(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        List<UsercontrollerEbayAccountExtend> ebays = userInfoService.getEbayAccountForCurrUser();
        modelMap.put("ebays",ebays);
        return forword("orders/order/synOrders",modelMap);
    }

    /**
     * 查看订单详情
     */
    @RequestMapping("/viewOrderGetOrders.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewTemplateInitTable(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String orderid=request.getParameter("orderid");
        List<TradingOrderGetOrders> lists=iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(orderid);
        modelMap.put("lists",lists);
        return forword("orders/order/viewOrderGetOrders",modelMap);
    }
    @RequestMapping("/apiGetOrdersRequest.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    /**获取接受信息总数*/
    public void apiGetMyMessagesRequest(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String ebayId=request.getParameter("ebayId");
        Long ebay=Long.valueOf(ebayId);
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);//开发者帐号id
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.GetOrders);
        request.getSession().setAttribute("dveId", d);
        Map map=new HashMap();
        Date startTime2=DateUtils.subDays(new Date(),8);
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
          /*  Map<String, String> resMap = addApiTask.exec(d, xml, "https://api.ebay.com/ws/api.dll");*/
        Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
        String r1 = resMap.get("stat");
        String res = resMap.get("message");
        if ("fail".equalsIgnoreCase(r1)) {
            AjaxSupport.sendFailText("fail", res);
            return;
        }
        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
        if ("Success".equalsIgnoreCase(ack)) {
            List<TradingOrderGetOrders> orders=GetOrdersAPI.parseXMLAndSave(res);
            for(TradingOrderGetOrders order:orders){
                List<TradingOrderGetOrders> ls=iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(order.getOrderid());
                if(ls!=null&&ls.size()>0){
                    order.setId(ls.get(0).getId());
                }
                /* order.setShippingdetailsId(sd.getId());*/
                d.setApiCallName(APINameStatic.GetItem);
                Map<String,String> itemresmap=GetOrderItemAPI.apiGetOrderItem(d,token,apiUrl,order.getItemid());
                String itemr1 = itemresmap.get("stat");
                String itemres = itemresmap.get("message");
                if ("fail".equalsIgnoreCase(itemr1)) {
                    AjaxSupport.sendFailText("fail", itemres);
                    return;
                }
                String itemack = SamplePaseXml.getVFromXmlString(itemres, "Ack");
                if ("Success".equalsIgnoreCase(itemack)) {
                    Map<String,Object> items=GetOrderItemAPI.parseXMLAndSave(itemres);
                    TradingOrderGetItem item= (TradingOrderGetItem) items.get(GetOrderItemAPI.ORDER_ITEM);
                    String ItemId=order.getItemid();
                    List<TradingOrderGetItem> itemList=iTradingOrderGetItem.selectOrderGetItemByItemId(ItemId);
                    TradingOrderListingDetails listingDetails= (TradingOrderListingDetails) items.get(GetOrderItemAPI.LISTING_DETAILS);
                    TradingOrderSeller orderSeller= (TradingOrderSeller) items.get(GetOrderItemAPI.ORDER_SELLER);
                    TradingOrderSellingStatus sellingStatus= (TradingOrderSellingStatus) items.get(GetOrderItemAPI.SELLING_STATUS);
                    TradingOrderShippingDetails shippingDetails= (TradingOrderShippingDetails) items.get(GetOrderItemAPI.SHIPPING_DETAILS);
                    TradingOrderPictureDetails pictureDetails= (TradingOrderPictureDetails) items.get(GetOrderItemAPI.PICTURE_DETAILS);
                    TradingOrderReturnpolicy returnpolicy= (TradingOrderReturnpolicy) items.get(GetOrderItemAPI.RETURN_POLICY);
                    TradingOrderSellerInformation sellerInformation= (TradingOrderSellerInformation) items.get(GetOrderItemAPI.SELLER_INFORMATION);
                    TradingOrderCalculatedShippingRate shippingRate= (TradingOrderCalculatedShippingRate) items.get(GetOrderItemAPI.SHIPPING_RATE);
                    List<TradingOrderShippingServiceOptions> serviceOptionses= (List<TradingOrderShippingServiceOptions>) items.get(GetOrderItemAPI.SERVICE_OPTIONS);
                    if(itemList!=null&&itemList.size()>0){
                        item.setId(itemList.get(0).getId());
                        listingDetails.setId(itemList.get(0).getListingdetailsId());
                        orderSeller.setId(itemList.get(0).getSellerId());
                        sellingStatus.setId(itemList.get(0).getSellingstatusId());
                        shippingDetails.setId(itemList.get(0).getShippingdetailsId());
                        pictureDetails.setId(itemList.get(0).getPicturedetailsId());
                        returnpolicy.setId(itemList.get(0).getOrderreturnpolicyId());
                        List<TradingOrderSeller> sellerList=iTradingOrderSeller.selectOrderGetItemById(orderSeller.getId());
                        if(sellerList!=null&&sellerList.size()>0){
                            sellerInformation.setId(sellerList.get(0).getSellerinfoId());
                        }
                        List<TradingOrderShippingDetails> shippingDetailsList=iTradingOrderShippingDetails.selectOrderGetItemById(shippingDetails.getId());
                        if(shippingDetailsList!=null&&shippingDetailsList.size()>0){
                            shippingRate.setId(shippingDetailsList.get(0).getCalculatedshippingrateId());
                        }
                        iTradingOrderCalculatedShippingRate.saveOrderCalculatedShippingRate(shippingRate);
                        shippingDetails.setCalculatedshippingrateId(shippingRate.getId());
                        iTradingOrderShippingDetails.saveOrderShippingDetails(shippingDetails);
                        iTradingOrderSellerInformation.saveOrderSellerInformation(sellerInformation);
                        iTradingOrderListingDetails.saveOrderListingDetails(listingDetails);
                        orderSeller.setSellerinfoId(sellerInformation.getId());
                        iTradingOrderSeller.saveOrderSeller(orderSeller);
                        iTradingOrderSellingStatus.saveOrderSellingStatus(sellingStatus);
                        iTradingOrderPictureDetails.saveOrderPictureDetails(pictureDetails);
                        iTradingOrderReturnpolicy.saveOrderReturnpolicy(returnpolicy);

                    }else{
                        iTradingOrderCalculatedShippingRate.saveOrderCalculatedShippingRate(shippingRate);
                        iTradingOrderSellerInformation.saveOrderSellerInformation(sellerInformation);
                        iTradingOrderListingDetails.saveOrderListingDetails(listingDetails);
                        orderSeller.setSellerinfoId(sellerInformation.getId());
                        iTradingOrderSeller.saveOrderSeller(orderSeller);
                        iTradingOrderSellingStatus.saveOrderSellingStatus(sellingStatus);
                        shippingDetails.setCalculatedshippingrateId(shippingRate.getId());
                        iTradingOrderShippingDetails.saveOrderShippingDetails(shippingDetails);
                        iTradingOrderPictureDetails.saveOrderPictureDetails(pictureDetails);
                        iTradingOrderReturnpolicy.saveOrderReturnpolicy(returnpolicy);
                        item.setListingdetailsId(listingDetails.getId());
                        item.setSellerId(orderSeller.getId());
                        item.setSellingstatusId(sellingStatus.getId());
                        item.setShippingdetailsId(shippingDetails.getId());
                        item.setPicturedetailsId(pictureDetails.getId());
                        item.setOrderreturnpolicyId(returnpolicy.getId());
                    }
                    List<TradingOrderShippingServiceOptions> shippingServiceOptionses=iTradingOrderShippingServiceOptions.selectOrderGetItemByShippingDetailsId(shippingDetails.getId());
                    for(TradingOrderShippingServiceOptions shippingServiceOptionse:shippingServiceOptionses){
                        iTradingOrderShippingServiceOptions.deleteOrderShippingServiceOptions(shippingServiceOptionse);
                    }
                    for(TradingOrderShippingServiceOptions serviceOptionse:serviceOptionses){
                        serviceOptionse.setShippingdetailsId(shippingDetails.getId());
                        iTradingOrderShippingServiceOptions.saveOrderShippingServiceOptions(serviceOptionse);
                    }
                    order.setShippingdetailsId(shippingDetails.getId());
                    iTradingOrderGetItem.saveOrderGetItem(item);
                }else {
                    String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                    logger.error("获取apisessionid失败!" + errors);
                    AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
                }

                iTradingOrderGetOrders.saveOrderGetOrders(order);
            }
            AjaxSupport.sendSuccessText("success", "同步成功！");
        } else {
            String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
            logger.error("获取apisessionid失败!" + errors);
            AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
        }
    }
}
