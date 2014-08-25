package com.order.controller;

import com.base.database.trading.model.*;
import com.base.domains.CommonParmVO;
import com.base.domains.querypojos.OrderGetOrdersQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.sampleapixml.*;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.common.DateUtils;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
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
    @Autowired
    private  ITradingMessageGetmymessage iTradingMessageGetmymessage;
    @Autowired
    private  ITradingMessageAddmembermessage iTradingMessageAddmembermessage;
    @Autowired
    private ITradingOrderSenderAddress iTradingOrderSenderAddress;
    @Autowired
    private  ITradingOrderAddMemberMessageAAQToPartner iTradingOrderAddMemberMessageAAQToPartner;
    @Autowired
    private  ITradingOrderGetAccount iTradingOrderGetAccount;
    @Autowired
    private  ITradingOrderGetSellerTransactions iTradingOrderGetSellerTransactions;
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
        String status=request.getParameter("status");
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<UsercontrollerEbayAccountExtend> ebays=userInfoService.getEbayAccountForCurrUser();
        Map map=new HashMap();
        if(orderStatus==null||"All".equals(orderStatus)){
            orderStatus=null;
        }
        if(status==null||"All".equals(status)){
            status=null;
        }
        if(!StringUtils.isNotBlank(starttime)){
            starttime=null;
        }
        if(!StringUtils.isNotBlank(endtime)){
            endtime=null;
        }
        map.put("ebays",ebays);
        map.put("orderStatus",orderStatus);
        map.put("starttime",starttime);
        map.put("endtime",endtime);
        map.put("status",status);
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
     * 发货时修改订单(运输)
     */
    @RequestMapping("/ajax/updateOrder.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void updateOrder( HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String orderid=request.getParameter("id");
        String freight=request.getParameter("freight");
        String shippedtime=request.getParameter("shippedtime");
        Date shipptime=null;
        if(StringUtils.isNotBlank(shippedtime)){
            int year= Integer.parseInt(shippedtime.substring(0,4));
            int month= Integer.parseInt(shippedtime.substring(5,7));
            int day= Integer.parseInt(shippedtime.substring(8,10));
            int hour= Integer.parseInt(shippedtime.substring(11,13));
            int minite= Integer.parseInt(shippedtime.substring(14, 16));
            int ss=0;
            shipptime=DateUtils.buildDateTime(year,month,day,hour,minite,0);
        }
        List<TradingOrderGetOrders> orderList= iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(orderid);
        for(TradingOrderGetOrders order:orderList){
            order.setShippedtime(shipptime);
            if(StringUtils.isNotBlank(freight)){
                order.setActualshippingcost(Double.valueOf(freight));
            }
            order.setOrderstatus("Shipped");
            iTradingOrderGetOrders.saveOrderGetOrders(order);
        }
        AjaxSupport.sendSuccessText("","操作成功!");
    }
    /**
     * 查看订单详情
     */
    @RequestMapping("/viewOrderGetOrders.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewTemplateInitTable(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String orderid=request.getParameter("orderid");
        List<TradingOrderGetOrders> lists=iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(orderid);
        modelMap.put("order",lists.get(0));
        modelMap.put("orderId",orderid);
        List<TradingMessageGetmymessage> messages=new ArrayList<TradingMessageGetmymessage>();
        List<TradingOrderAddMemberMessageAAQToPartner> addMessages=new ArrayList<TradingOrderAddMemberMessageAAQToPartner>();
        for(TradingOrderGetOrders order:lists){
            String itemid=order.getItemid();
            List<TradingMessageGetmymessage> messageList=iTradingMessageGetmymessage.selectMessageGetmymessageByItemId(itemid);
            List<TradingOrderAddMemberMessageAAQToPartner> addmessageList=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByItemId(itemid);
            messages.addAll(messageList);
            addMessages.addAll(addmessageList);
        }
        List<TradingOrderSenderAddress> senderAddresses=iTradingOrderSenderAddress.selectOrderSenderAddressByOrderId(orderid);
        TradingOrderSenderAddress type1=new TradingOrderSenderAddress();
        TradingOrderSenderAddress type2=new TradingOrderSenderAddress();
        for(TradingOrderSenderAddress senderAddresse:senderAddresses){
            if("1".equals(senderAddresse.getType())){
                type1=senderAddresse;
            }
            if("2".equals(senderAddresse.getType())){
                type2=senderAddresse;
            }
        }
        modelMap.put("messages1", messages);
        modelMap.put("addMessage1",addMessages);
        modelMap.put("addresstype1",type1);
        modelMap.put("addresstype2",type2);
        /*Map<String,String> map=new HashMap<String, String>();
        map.put("orderStatus","Completed");
        map.put("selleraccount",lists.get(0).getSelleruserid());
        map.put("buyaccount",lists.get(0).getBuyeruserid());
        List<OrderGetOrdersQuery> querys= this.iTradingOrderGetOrders.selectOrderGetOrdersByGroupList(map,page);*/
        return forword("orders/order/viewOrderGetOrders",modelMap);
    }
    /*
     *保存寄件人地址/退货地址
     */
    @RequestMapping("/ajax/saveReturnAddress.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveReturnAddress( HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String returnAddress=request.getParameter("returnAddress");
        String returnContacts=request.getParameter("returnContacts");
        String returnCompany=request.getParameter("returnCompany");
        String returnCountry=request.getParameter("returnCountry");
        String returnProvince=request.getParameter("returnProvince");
        String returnCity=request.getParameter("returnCity");
        String returnArea=request.getParameter("returnArea");
        String returnStreet=request.getParameter("returnStreet");
        String returnPostCode=request.getParameter("returnPostCode");
        String returnPhone=request.getParameter("returnPhone");
        String returnEmail=request.getParameter("returnEmail");
        String orderid=request.getParameter("orderId");
        TradingOrderSenderAddress senderAddress=new TradingOrderSenderAddress();
        senderAddress.setType(returnAddress);
        senderAddress.setContacts(returnContacts);
        senderAddress.setCompany(returnCompany);
        senderAddress.setCountry(returnCountry);
        senderAddress.setProvince(returnProvince);
        senderAddress.setCity(returnCity);
        senderAddress.setArea(returnArea);
        senderAddress.setStreet(returnStreet);
        senderAddress.setPostcode(returnPostCode);
        senderAddress.setPhone(returnPhone);
        senderAddress.setEmail(returnEmail);
        senderAddress.setOrderid(orderid);
        List<TradingOrderSenderAddress> senderAddressesList=iTradingOrderSenderAddress.selectOrderSenderAddressByOrderId(orderid);
        if(senderAddressesList!=null&&senderAddressesList.size()>0) {
            for (TradingOrderSenderAddress senderAddr : senderAddressesList) {
                if (senderAddr.getType().equals(senderAddress.getType())) {
                    senderAddress.setId(senderAddr.getId());
                }
            }
        }
        iTradingOrderSenderAddress.saveOrderSenderAddress(senderAddress);
        AjaxSupport.sendSuccessText("","操作成功!");
    }
    /*
     *订单详情摘要交易信息,付款信息等
     */
    @RequestMapping("/viewOrderAbstractLeft.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewOrderAbstractLeft(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String orderId=request.getParameter("orderId");
        List<TradingOrderGetOrders> lists=iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(orderId);
        TradingOrderGetOrders order=lists.get(0);
        /*List<TradingOrderShippingDetails> detailsList=iTradingOrderShippingDetails.selectOrderGetItemById(order.getShippingdetailsId());*/
/*
        List<TradingOrderShippingServiceOptions> serviceOptionList=iTradingOrderShippingServiceOptions.selectOrderGetItemByShippingDetailsId(order.getShippingdetailsId());
*/
        List<TradingOrderGetSellerTransactions> sellerTransactions=iTradingOrderGetSellerTransactions.selectTradingOrderGetSellerTransactionsByTransactionId(lists.get(0).getTransactionid());
        String transactionid="";
        if(sellerTransactions!=null&&sellerTransactions.size()>0){
            transactionid=sellerTransactions.get(0).getExternaltransactionid();
        }
        List<TradingOrderGetAccount> accountlist=iTradingOrderGetAccount.selectTradingOrderGetAccountByTransactionId(lists.get(0).getTransactionid());
        String grossdetailamount="";
        if(accountlist!=null&&accountlist.size()>0){
            grossdetailamount=accountlist.get(0).getGrossdetailamount();
        }
        modelMap.put("grossdetailamount",grossdetailamount);
        modelMap.put("paypal",transactionid);
        modelMap.put("order",order);
        /*modelMap.put("options",serviceOptionList);*/
        return forword("orders/order/viewOrderAbstractLeft",modelMap);
    }
    /*
     *摘要里的订单地址
     */
    @RequestMapping("/viewOrderAbstractRight.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewOrderAbstractRight(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String orderId=request.getParameter("orderId");
        List<TradingOrderGetOrders> lists=iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(orderId);
        TradingOrderGetOrders order=lists.get(0);
        modelMap.put("order",order);
        return forword("orders/order/viewOrderAbstractRight",modelMap);
    }
    /*
     *摘要里的最近的发送消息
     */
    @RequestMapping("/viewOrderAbstractDown.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewOrderShipmentsHistory(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String orderId=request.getParameter("orderId");
        List<TradingOrderGetOrders> lists=iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(orderId);
        List<TradingOrderAddMemberMessageAAQToPartner> addMes=new ArrayList<TradingOrderAddMemberMessageAAQToPartner>();
        for(TradingOrderGetOrders order:lists){
            String itemid=order.getItemid();
            List<TradingOrderAddMemberMessageAAQToPartner> addmessageList1=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByItemId(itemid);
            addMes.addAll(addmessageList1);
        }
        TradingOrderAddMemberMessageAAQToPartner partner=new TradingOrderAddMemberMessageAAQToPartner();
        if(addMes!=null&&addMes.size()>0){
            partner= addMes.get(addMes.size()-1);
        }
        modelMap.put("addQMessage",partner);
        return forword("orders/order/viewOrderAbstractDown",modelMap);
    }
    /*@RequestMapping("/viewOrderEbayMessage.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewOrderEbayMessage(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String orderId=request.getParameter("orderId");
        List<TradingOrderGetOrders> lists=iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(orderId);
        TradingOrderGetOrders order=lists.get(0);
        modelMap.put("order",order);
        return forword("orders/order/viewOrderAbstractDown",modelMap);

    }*/
    /*
     *购买历史初始化
     */
    @RequestMapping("/viewOrderBuyHistory.do")
    public ModelAndView viewOrderBuyHistory(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String orderId=request.getParameter("orderId");
        modelMap.put("orderId",orderId);
        return forword("/orders/order/viewOrderBuyHistory",modelMap);
    }
    /*
     *购买历史信息
     */
    @RequestMapping("/ajax/loadBuyHistory.do")
    @ResponseBody
    public void loadBuyHistory(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String orderId=request.getParameter("orderId");
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        Map map=new HashMap();
        List<TradingOrderGetOrders> orderList=iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(orderId);
        String selleraccount=null;
        String buyaccount=null;
        if(orderList!=null&&orderList.size()>0){
            selleraccount=orderList.get(0).getSelleruserid();
            buyaccount=orderList.get(0).getBuyeruserid();
        }
        List<String> statusList=new ArrayList<String>();
        statusList.add("shipped");
        statusList.add("Completed");
        map.put("StatusList",statusList);
        map.put("selleraccount",selleraccount);
        map.put("buyaccount",buyaccount);
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
    /*
     *发送消息初始化
     */
    @RequestMapping("/initOrdersSendMessage.do")
    public ModelAndView initOrdersSendMessage(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String orderid=request.getParameter("orderid");
        List<TradingOrderGetOrders> list=iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(orderid);
        modelMap.put("itemid",list.get(0).getItemid());
        modelMap.put("order",list.get(0));
        return forword("/orders/order/orderSendMessage",modelMap);
    }
    /*
     *订单中卖家发消息
     */
    @RequestMapping("/apiGetOrdersSendMessage.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void apiGetOrdersSendMessage(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String body=request.getParameter("body");
        String subject=request.getParameter("subject");
        String itemid= request.getParameter("itemid");
        String buyeruserid=request.getParameter("buyeruserid");
        String sender=request.getParameter("selleruserid");
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);//开发者帐号id
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.AddMemberMessageAAQToPartner);
        Map map=new HashMap();
        String ebayName=request.getParameter("selleruserid");
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
        map.put("itemid",itemid);
        map.put("buyeruserid",buyeruserid);
        String xml = BindAccountAPI.getAddMemberMessageAAQToPartner(map);//获取接受消息
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
            TradingOrderAddMemberMessageAAQToPartner message1=new TradingOrderAddMemberMessageAAQToPartner();
            message1.setBody(body);
            message1.setItemid(itemid);
            message1.setRecipientid(buyeruserid);
            message1.setSubject(subject);
            message1.setSender(sender);
            iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message1);
            AjaxSupport.sendSuccessText("success", "发送成功");
        }else{
            AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
        }
    }
    /*
     *同步订单
     */
    @RequestMapping("/apiGetOrdersRequest.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void apiGetOrdersRequest(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
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
                //------------同步订单商品-----------
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
                    String errors = SamplePaseXml.getVFromXmlString(itemres, "Errors");
                    logger.error("获取apisessionid失败!" + errors);
                    AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
                }
                //同步account-----
                 /*  UsercontrollerDevAccountExtend ds=new UsercontrollerDevAccountExtend();
                   ds.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
                   ds.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
                   ds.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
                   ds.setApiCompatibilityLevel("881");
                   ds.setApiSiteid("0");*/
                /* Map accountmap=new HashMap();
                accountmap.put("token",token);
                accountmap.put("Itemid",order.getItemid());
                accountmap.put("fromTime", start);
                accountmap.put("toTime", end);*/
                d.setApiCallName(APINameStatic.GetAccount);
                Map accountmap=new HashMap();
                accountmap.put("token",token);
                accountmap.put("Itemid",order.getItemid());
                 accountmap.put("fromTime", start);
                accountmap.put("toTime", end);
                String accountxml = BindAccountAPI.getGetAccount(accountmap);
                Map<String, String> accountresmap = addApiTask.exec(d, accountxml, apiUrl);
                String accountr1 = accountresmap.get("stat");
                String accountres = accountresmap.get("message");
                if ("fail".equalsIgnoreCase(accountr1)) {
                    AjaxSupport.sendFailText("fail", itemres);
                    return;
                }
                String accountack = SamplePaseXml.getVFromXmlString(accountres, "Ack");
                if ("Success".equalsIgnoreCase(accountack)) {
                    List<TradingOrderGetAccount> accountList= GetAccountAPI.parseXMLAndSave(accountres);
                    for(TradingOrderGetAccount acc:accountList){
                        List<TradingOrderGetAccount> accountList1=iTradingOrderGetAccount.selectTradingOrderGetAccountByTransactionId(order.getTransactionid());
                        if(accountList1!=null&&accountList1.size()>0){
                            acc.setId(accountList1.get(0).getId());
                        }
                        iTradingOrderGetAccount.saveOrderGetAccount(acc);
                    }

                }else{
                    String errors = SamplePaseXml.getVFromXmlString(accountres, "Errors");
                    logger.error("获取apisessionid失败!" + errors);
                    AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
                }
                //----------------

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
