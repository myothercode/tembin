package com.task.service.impl;

import com.base.aboutpaypal.service.PayPalService;
import com.base.database.sitemessage.model.PublicSitemessage;
import com.base.database.task.model.TaskGetOrders;
import com.base.database.trading.model.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.*;
import com.base.utils.common.DateUtils;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageService;
import com.sitemessage.service.SiteMessageStatic;
import com.task.service.IScheduleGetTimerOrders;
import com.task.service.ITaskGetOrders;
import com.trading.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/10/17.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ScheduleGetTimerOrdersImpl implements IScheduleGetTimerOrders {
    static Logger logger = Logger.getLogger(ScheduleGetTimerOrdersImpl.class);
    @Value("${EBAY.API.URL}")
    private String apiUrl;
    @Autowired
    private SiteMessageService siteMessageService;
    @Autowired
    private ITradingOrderOrderVariationSpecifics iTradingOrderOrderVariationSpecifics;
    @Autowired
    private ITradingOrderGetOrders iTradingOrderGetOrders;
    @Autowired
    private ITradingOrderAddMemberMessageAAQToPartner iTradingOrderAddMemberMessageAAQToPartner;
    @Autowired
    private ITradingAutoMessage iTradingAutoMessage;
    @Autowired
    private IUsercontrollerEbayAccount iUsercontrollerEbayAccount;
    @Autowired
    private ITradingOrderGetItem iTradingOrderGetItem;
    @Autowired
    private ITradingOrderSeller iTradingOrderSeller;
    @Autowired
    private ITradingOrderShippingDetails iTradingOrderShippingDetails;
    @Autowired
    private ITradingOrderCalculatedShippingRate iTradingOrderCalculatedShippingRate;
    @Autowired
    private ITradingOrderSellerInformation iTradingOrderSellerInformation;
    @Autowired
    private ITradingOrderSellingStatus iTradingOrderSellingStatus;
    @Autowired
    private ITradingOrderReturnpolicy iTradingOrderReturnpolicy;
    @Autowired
    private ITradingOrderShippingServiceOptions iTradingOrderShippingServiceOptions;
    @Autowired
    private ITradingOrderItemSpecifics iTradingOrderItemSpecifics;
    @Autowired
    private ITradingOrderVariation iTradingOrderVariation;
    @Autowired
    private ITradingOrderVariationSpecifics iTradingOrderVariationSpecifics;
    @Autowired
    private ITradingOrderPictures iTradingOrderPictures;
    @Autowired
    private ITradingOrderListingDetails iTradingOrderListingDetails;
    @Autowired
    private ITradingOrderPictureDetails iTradingOrderPictureDetails;
    @Autowired
    private ITradingOrderGetAccount iTradingOrderGetAccount;
    @Autowired
    private ITradingOrderGetSellerTransactions iTradingOrderGetSellerTransactions;
    @Autowired
    private ITaskGetOrders iTaskGetOrders;
    @Autowired
    private PayPalService payPalService;
    @Autowired
    private ITradingAutoMessageAttr iTradingAutoMessageAttr;
    @Autowired
    private ITradingOrderGetOrdersNoTransaction iTradingOrderGetOrdersNoTransaction;

    @Override
    public void synchronizeOrders(List<TaskGetOrders> taskGetOrders) throws Exception {
        /*CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);*/
            for(TaskGetOrders taskGetOrder:taskGetOrders){
                Integer flag1=taskGetOrder.getTokenflag();
                flag1=flag1+1;
                taskGetOrder.setTokenflag(flag1);
                iTaskGetOrders.saveListTaskGetOrders(taskGetOrder);
                //测试
                UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();//开发者帐号id
                d.setApiSiteid("0");
                d.setApiCallName(APINameStatic.GetOrders);
                //真实
               /* UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
                d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
                d.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
                d.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
                d.setApiCompatibilityLevel("883");
                d.setApiSiteid("0");
                d.setApiCallName(APINameStatic.GetOrders);*/
                //--------------
                Map map=new HashMap();
                map.put("token", taskGetOrder.getToken());
                map.put("fromTime", taskGetOrder.getFromtime());
                map.put("toTime", taskGetOrder.getTotime());
                map.put("page","1");
                String xml = BindAccountAPI.getGetOrders(map);
                AddApiTask addApiTask = new AddApiTask();
                Map<String, String> resMap = addApiTask.exec2(d, xml,apiUrl);
                //Map<String, String> resMap = addApiTask.exec2(d, xml,"https://api.ebay.com/ws/api.dll");
                String r1 = resMap.get("stat");
                String res = resMap.get("message");
                if ("fail".equalsIgnoreCase(r1)) {
                    logger.error("定时同步订单调用API失败:"+res+"\n\nXML:"+xml);
                }
                String ack="";
                try{
                    ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                }catch (Exception e){
                    logger.error("ScheduleGetTimerOrdersImpl第149"+res,e);
                    ack="";
                }
                if ("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)) {
                    if("Warning".equalsIgnoreCase(ack)){
                        String errors = "";
                        try{
                            errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                        }catch (Exception e){
                            logger.error("ScheduleGetTimerOrdersImpl第158"+res,e);
                            errors="";
                        }
                        if(!StringUtils.isNotBlank(errors)){
                            try{
                                errors =  SamplePaseXml.getWarningInformation(res);
                            }catch (Exception e){
                                logger.error("ScheduleGetTimerOrdersImpl第165"+res,e);
                                errors="解析res出错2";
                            }
                        }
                        List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_get_order_timer_FAIL","订单定时任务有警告:"+taskGetOrder.getId());
                        if(list1==null||list1.size()==0){
                            TaskMessageVO taskMessageVO = new TaskMessageVO();
                            taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_GET_ORDER_TIMER + "_FAIL");
                            taskMessageVO.setMessageTitle("定时同步订单有警告!");
                            taskMessageVO.setMessageContext("订单调用API有警告:" + errors);
                            taskMessageVO.setMessageTo(taskGetOrder.getUserid());
                            taskMessageVO.setMessageFrom("system");
                            taskMessageVO.setOrderAndSeller("订单定时任务有警告:"+taskGetOrder.getId());
                            siteMessageService.addSiteMessage(taskMessageVO);
                        }
                        logger.error("获取定时订单有警告!" + errors);
                    }
                    Map<String,Object> mapOrder= GetOrdersAPI.parseXMLAndSave(res);
                    String totalPage1= (String) mapOrder.get("totalPage");
                    String page1= (String) mapOrder.get("page");
                    int totalPage= Integer.parseInt(totalPage1);
                    for(int i=1;i<=totalPage;i++) {
                        if (i != 1) {
                            d.setApiSiteid("0");
                            d.setApiCallName(APINameStatic.GetOrders);
                            map.put("page", i + "");
                            xml = BindAccountAPI.getGetOrders(map);
                            resMap = addApiTask.exec2(d, xml,apiUrl);
                            //resMap = addApiTask.exec2(d, xml, "https://api.ebay.com/ws/api.dll");
                   /* resMap = addApiTask.exec2(d, xml, "https://api.ebay.com/ws/api.dll");*/
                            r1 = resMap.get("stat");
                            res = resMap.get("message");
                            if ("fail".equalsIgnoreCase(r1)) {
                                logger.error("循环中的定时同步订单调用API失败:"+res+"\n\nXML:"+xml);
                            }
                            ack="";
                            try{
                                ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                            }catch (Exception e){
                                logger.error("ScheduleGetTimerOrdersImpl第215"+res,e);
                                ack="";
                            }
                            if (!"Success".equalsIgnoreCase(ack)&&!"Warning".equalsIgnoreCase(ack)) {
                                String errors = "";
                                try{
                                    errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                                }catch (Exception e){
                                    logger.error("ScheduleGetTimerOrdersImpl第223"+res,e);
                                    errors="";
                                }
                                if(!StringUtils.isNotBlank(errors)){
                                    try{
                                        errors =  SamplePaseXml.getWarningInformation(res);
                                    }catch (Exception e){
                                        logger.error(""+res,e);
                                        errors="解析res出错";
                                    }
                                }
                                logger.error("循环中的Order参数错误!" + errors+"\n\nXML:"+xml);
                            }
                            if("Warning".equalsIgnoreCase(ack)){
                                String errors = "";
                                try{
                                    errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                                }catch (Exception e){
                                    logger.error("ScheduleGetTimerOrdersImpl第253"+res,e);
                                    errors="";
                                }
                                if(!StringUtils.isNotBlank(errors)){
                                    try{
                                        errors =  SamplePaseXml.getWarningInformation(res);
                                    }catch (Exception e){
                                        logger.error("ScheduleGetTimerOrdersImpl第260"+res,e);
                                        errors="解析res出错1";
                                    }
                                }
                                List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_get_order_timer_FAIL","订单定时任务有警告:"+taskGetOrder.getId());
                                if(list1==null||list1.size()==0){
                                    TaskMessageVO taskMessageVO = new TaskMessageVO();
                                    taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_GET_ORDER_TIMER + "_FAIL");
                                    taskMessageVO.setMessageTitle("定时同步订单有警告!");
                                    taskMessageVO.setMessageContext("订单调用API有警告:" + errors);
                                    taskMessageVO.setMessageTo(taskGetOrder.getUserid());
                                    taskMessageVO.setMessageFrom("system");
                                    taskMessageVO.setOrderAndSeller("订单定时任务有警告:"+taskGetOrder.getId());
                                    siteMessageService.addSiteMessage(taskMessageVO);

                                }
                                logger.error("循环中的获取定时订单有警告!" + errors);
                            }
                            mapOrder = GetOrdersAPI.parseXMLAndSave(res);
                        }
                        if ("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)) {
                            List<TradingOrderGetOrders> orders = (List<TradingOrderGetOrders>) mapOrder.get("OrderList");
                            List<List<TradingOrderOrderVariationSpecifics>> OrderVariationSpecificses = (List<List<TradingOrderOrderVariationSpecifics>>) mapOrder.get("OrderVariation");
                            for (List<TradingOrderOrderVariationSpecifics> orderVariationSpecificses : OrderVariationSpecificses) {
                                for (TradingOrderOrderVariationSpecifics orderOrderVariationSpecifics : orderVariationSpecificses) {
                                    List<TradingOrderOrderVariationSpecifics> list = iTradingOrderOrderVariationSpecifics.selectOrderOrderVariationSpecificsByAll(orderOrderVariationSpecifics.getSku(), orderOrderVariationSpecifics.getName(), orderOrderVariationSpecifics.getValue());
                                    if (list == null || list.size() == 0) {
                                        orderOrderVariationSpecifics.setCreateUser(taskGetOrder.getUserid());
                                        iTradingOrderOrderVariationSpecifics.saveOrderOrderVariationSpecifics(orderOrderVariationSpecifics);
                                    }
                                }
                            }
                            for (TradingOrderGetOrders order : orders) {
                                List<TradingOrderGetOrders> ls = iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(order.getOrderid());
                                for (TradingOrderGetOrders l : ls) {
                                    if (l.getTransactionid().equals(order.getTransactionid())) {
                                        order.setId(l.getId());
                                    }
                                }
                                //-----获取跟踪号状态-----------
                               /* if (order.getShipmenttrackingnumber() != null) {
                                    String trackStatus = null;
                                    try {
                                        trackStatus = OrderQueryTrack.queryTrack(order);
                                        if("0".equalsIgnoreCase(trackStatus)){
                                            order.setTrackstatus("0");
                                        }
                                        else{
                                            order.setTrackstatus(trackStatus);
                                        }
                                    } catch (Exception e) {
                                        MainTask.taskRunTime.put("91trackTask_ERROR",new Date());
                                        order.setTrackstatus("0");
                                        logger.error("",e);
                                    }
                                }*/
                                //--------------自动发送消息-------------------------
                                List<TradingOrderAddMemberMessageAAQToPartner> addmessages = iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByTransactionId(order.getTransactionid(), 2, order.getSelleruserid());
                                if (addmessages != null && addmessages.size() > 0) {
                                    order=autoMessageSet("标记已发货",taskGetOrder.getUserid(),order);
                                  /*  List<TradingAutoMessage> partners = iTradingAutoMessage.selectAutoMessageByType("标记已发货", taskGetOrder.getUserid());
                                    if(partners!=null&&partners.size()>0) {
                                        for (TradingAutoMessage partner : partners) {
                                            if(partner==null){
                                                continue;
                                            }
                                            if (partner.getStartuse() == 1) {
                                                int day = partner.getDay();
                                                int hour = partner.getHour();
                                                Date date = order.getLastmodifiedtime();
                                                Date date1 = org.apache.commons.lang.time.DateUtils.addDays(date, day);
                                                Date date2 = org.apache.commons.lang.time.DateUtils.addHours(date1, hour);
                                                order.setSendmessagetime(date2);
                                            }
                                        }
                                    }*/
                                    boolean flag = false;
                                    String trackingnumber = order.getShipmenttrackingnumber();
                                    for (TradingOrderAddMemberMessageAAQToPartner addmessage : addmessages) {
                                        if (StringUtils.isNotBlank(trackingnumber) && addmessage.getMessageflag() == 2) {
                                            flag = true;
                                        }
                                    }
                                    if (flag && StringUtils.isNotBlank(trackingnumber)) {
                                        order.setPaypalflag(null);
                                        order.setShippedflag(1);
                                    } else {
                                        order.setPaypalflag(1);
                                        order.setShippedflag(1);
                                    }
                                } else {
                                    String trackingnumber = order.getShipmenttrackingnumber();
                                    if ("Complete".equals(order.getStatus()) && !StringUtils.isNotBlank(trackingnumber) && "Completed".equals(order.getOrderstatus())) {
                                        //付款后发送消息
                                        order=autoMessageSet("收到买家付款",taskGetOrder.getUserid(),order);
                                       /* List<TradingAutoMessage> partners = iTradingAutoMessage.selectAutoMessageByType("收到买家付款", taskGetOrder.getUserid());
                                       *//* TradingAutoMessage autoMessage = new TradingAutoMessage();*//*
                                        if(partners!=null&&partners.size()>0){
                                            for (TradingAutoMessage partner : partners) {
                                                if(partner==null){
                                                    continue;
                                                }
                                                if (partner.getStartuse() == 1) {
                                                    int day = partner.getDay();
                                                    int hour = partner.getHour();
                                                    Date date = order.getLastmodifiedtime();
                                                    Date date1 = org.apache.commons.lang.time.DateUtils.addDays(date, day);
                                                    Date date2 = org.apache.commons.lang.time.DateUtils.addHours(date1, hour);
                                                    order.setSendmessagetime(date2);
                                                }
                                            }
                                        }*/
                                        order.setPaypalflag(1);
                                        order.setShippedflag(null);
                                    }
                                    if (StringUtils.isNotBlank(trackingnumber)) {
                                        order=autoMessageSet("标记已发货",taskGetOrder.getUserid(),order);
                                       /* List<TradingAutoMessage> partners = iTradingAutoMessage.selectAutoMessageByType("标记已发货", taskGetOrder.getUserid());
                                        TradingAutoMessage autoMessage = new TradingAutoMessage();
                                        if(partners!=null&&partners.size()>0) {
                                            for (TradingAutoMessage partner : partners) {
                                                if (partner == null) {
                                                    continue;
                                                }
                                                if (partner.getStartuse() == 1) {
                                                    int day = partner.getDay();
                                                    int hour = partner.getHour();
                                                    Date date = order.getLastmodifiedtime();
                                                    Date date1 = org.apache.commons.lang.time.DateUtils.addDays(date, day);
                                                    Date date2 = org.apache.commons.lang.time.DateUtils.addHours(date1, hour);
                                                    order.setSendmessagetime(date2);
                                                }
                                            }
                                        }*/
                                        order.setPaypalflag(null);
                                        order.setShippedflag(1);
                                    }
                                }
                                order.setCreateUser(taskGetOrder.getUserid());
                                order.setAccountflag(0);
                                order.setSellertrasactionflag(0);
                                order.setItemflag(0);
                                iTradingOrderGetOrdersNoTransaction.saveOrderGetOrders(order);
                            }
                        }
                    }
                }else {
                    logger.error("Order获取apisessionid失败!" + res);
                }
            }
    }
    private Date sendAutoMessageTime(TradingAutoMessage partner,TradingOrderGetOrders order){
        int day = partner.getDay();
        int hour = partner.getHour();
        Date date = order.getLastmodifiedtime();
        Date date1 = org.apache.commons.lang.time.DateUtils.addDays(date, day);
        Date date2 = org.apache.commons.lang.time.DateUtils.addHours(date1, hour);
        return date2;
    }
    private TradingOrderGetOrders autoMessageSet(String type,Long userId,TradingOrderGetOrders order){
        List<TradingAutoMessage> partners = iTradingAutoMessage.selectAutoMessageByType(type, userId);
        if(partners!=null&&partners.size()>0) {
            for (TradingAutoMessage partner : partners) {
                if(partner==null){
                    order.setAutomessageId(0L);
                }
                if (partner.getStartuse() == 1) {
                    Boolean autoFlag = false;
                    List<TradingAutoMessageAttr> allOrders = iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(partner.getId(), "allOrder");
                    if (allOrders != null && allOrders.size() > 0) {
                        autoFlag = true;
                    } else {
                        Boolean orderItemFlag = false;
                        Boolean countryFlag = false;
                        Boolean amountFlag = false;
                        Boolean serviceFlag = false;
                        Boolean internationalServiceFlag = false;
                        Boolean exceptCountryFlag = false;
                        List<TradingAutoMessageAttr> orderItemAttrs = iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(partner.getId(), "orderItem");
                        List<TradingAutoMessageAttr> countryAttrs = iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(partner.getId(), "country");
                        List<TradingAutoMessageAttr> amountAttrs = iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(partner.getId(), "amount");
                        List<TradingAutoMessageAttr> serviceAttrs = iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(partner.getId(), "service");
                        List<TradingAutoMessageAttr> internationalServiceAttrs = iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(partner.getId(), "internationalService");
                        List<TradingAutoMessageAttr> exceptCountryAttrs = iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(partner.getId(), "exceptCountry");
                        if (orderItemAttrs != null && orderItemAttrs.size() > 0) {
                            for (TradingAutoMessageAttr orderItemAttr : orderItemAttrs) {
                                String sku = order.getSku();
                                if (orderItemAttr.getValue().contains(sku)) {
                                    orderItemFlag = true;
                                }
                            }
                        } else {
                            orderItemFlag = true;
                        }
                        if (countryAttrs != null && countryAttrs.size() > 0) {
                            for (TradingAutoMessageAttr countryAttr : countryAttrs) {
                                String country = order.getCountry();
                                if (countryAttr.getValue().contains(country)) {
                                    countryFlag = true;
                                }
                            }
                        } else {
                            countryFlag = true;
                        }
                        if (amountAttrs != null && amountAttrs.size() > 0) {
                            for (TradingAutoMessageAttr amountAttr : amountAttrs) {
                                String amount = order.getSelleruserid();
                                if (amountAttr.getValue().contains(amount)) {
                                    amountFlag = true;
                                }
                            }
                        } else {
                            amountFlag = true;
                        }
                        if (serviceAttrs != null && serviceAttrs.size() > 0) {
                            for (TradingAutoMessageAttr serviceAttr : serviceAttrs) {
                                String service = order.getSelectedshippingservice();
                                if (serviceAttr.getValue().contains(service)) {
                                    serviceFlag = true;
                                }
                            }
                            if (internationalServiceAttrs != null && internationalServiceAttrs.size() > 0) {
                                for (TradingAutoMessageAttr internationalServiceAttr : internationalServiceAttrs) {
                                    String internationalService = order.getSelectedshippingservice();
                                    if (internationalServiceAttr.getValue().contains(internationalService)) {
                                        internationalServiceFlag = true;
                                    }
                                }
                            } else {
                                internationalServiceFlag = true;
                            }
                        } else if (internationalServiceAttrs != null && internationalServiceAttrs.size() > 0) {
                            for (TradingAutoMessageAttr internationalServiceAttr : internationalServiceAttrs) {
                                String internationalService = order.getSelectedshippingservice();
                                if (internationalServiceAttr.getValue().contains(internationalService)) {
                                    internationalServiceFlag = true;
                                }
                            }
                            serviceFlag = true;
                        } else {
                            serviceFlag = true;
                            internationalServiceFlag = true;
                        }
                        if (exceptCountryAttrs != null && exceptCountryAttrs.size() > 0) {
                            for (TradingAutoMessageAttr exceptCountryAttr : exceptCountryAttrs) {
                                String country = order.getCountry();
                                if (!exceptCountryAttr.getValue().contains(country)) {
                                    exceptCountryFlag = true;
                                }
                            }
                        } else {
                            exceptCountryFlag = true;
                        }
                        if (exceptCountryFlag && internationalServiceFlag && serviceFlag && amountFlag && countryFlag && orderItemFlag) {
                            autoFlag = true;
                        }
                    }
                    if (autoFlag) {
                        Date date2 = sendAutoMessageTime(partner, order);
                        order.setSendmessagetime(date2);
                        order.setAutomessageId(partner.getId());
                        continue;
                    }else{
                        order.setAutomessageId(0L);
                    }
                }else{
                    order.setAutomessageId(0L);
                }
            }
        }
        return order;
    }
    @Override
    public void synchronizeOrderItems(List<TradingOrderGetOrders> orders) throws Exception {
        //测试环境
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();//开发者帐号id
        d.setApiSiteid("0");
        //真实环境
       /* UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
        d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
        d.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
        d.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
        d.setApiCompatibilityLevel("883");
        d.setApiSiteid("0");*/
        for(TradingOrderGetOrders order:orders){
            UsercontrollerEbayAccount ebay=iUsercontrollerEbayAccount.selectByEbayAccount(order.getSelleruserid());
            if(ebay!=null&&StringUtils.isNotBlank(ebay.getEbayToken())) {
                String token=ebay.getEbayToken();
                d.setApiCallName(APINameStatic.GetItem);
                //测试环境
                Map<String, String> itemresmap = GetOrderItemAPI.apiGetOrderItem(d, token, apiUrl, order.getItemid());
                //真实环境
                //Map<String, String> itemresmap = GetOrderItemAPI.apiGetOrderItem(d, token, "https://api.ebay.com/ws/api.dll", order.getItemid());
                String itemr1 = itemresmap.get("stat");
                String itemres = itemresmap.get("message");
                if ("fail".equalsIgnoreCase(itemr1)) {
                    logger.error("定时同步订单商品调用API失败:" + itemres + "\n\norderId:" + order.getOrderid());
                }
                String itemack = "";
                try {
                    itemack = SamplePaseXml.getVFromXmlString(itemres, "Ack");
                } catch (Exception e) {
                    logger.error("ScheduleGetTimerOrdersImpl第769" + itemres, e);
                    itemack = "";
                }
                if ("Success".equalsIgnoreCase(itemack)) {
                    Map<String, Object> items = GetOrderItemAPI.parseXMLAndSave(itemres);
                    TradingOrderGetItem item = (TradingOrderGetItem) items.get(GetOrderItemAPI.ORDER_ITEM);
                    String ItemId = order.getItemid();
                    List<TradingOrderGetItem> itemList = iTradingOrderGetItem.selectOrderGetItemByItemId(ItemId);
                    TradingOrderListingDetails listingDetails = (TradingOrderListingDetails) items.get(GetOrderItemAPI.LISTING_DETAILS);
                    TradingOrderSeller orderSeller = (TradingOrderSeller) items.get(GetOrderItemAPI.ORDER_SELLER);
                    TradingOrderSellingStatus sellingStatus = (TradingOrderSellingStatus) items.get(GetOrderItemAPI.SELLING_STATUS);
                    TradingOrderShippingDetails shippingDetails = (TradingOrderShippingDetails) items.get(GetOrderItemAPI.SHIPPING_DETAILS);
                    TradingOrderPictureDetails pictureDetails = (TradingOrderPictureDetails) items.get(GetOrderItemAPI.PICTURE_DETAILS);
                    TradingOrderReturnpolicy returnpolicy = (TradingOrderReturnpolicy) items.get(GetOrderItemAPI.RETURN_POLICY);
                    TradingOrderSellerInformation sellerInformation = (TradingOrderSellerInformation) items.get(GetOrderItemAPI.SELLER_INFORMATION);
                    TradingOrderCalculatedShippingRate shippingRate = (TradingOrderCalculatedShippingRate) items.get(GetOrderItemAPI.SHIPPING_RATE);
                    List<TradingOrderShippingServiceOptions> serviceOptionses = (List<TradingOrderShippingServiceOptions>) items.get(GetOrderItemAPI.SERVICE_OPTIONS);
                    shippingRate.setCreateUser(order.getCreateUser());
                    shippingDetails.setCreateUser(order.getCreateUser());
                    sellerInformation.setCreateUser(order.getCreateUser());
                    listingDetails.setCreateUser(order.getCreateUser());
                    orderSeller.setCreateUser(order.getCreateUser());
                    sellingStatus.setCreateUser(order.getCreateUser());
                    pictureDetails.setCreateUser(order.getCreateUser());
                    returnpolicy.setCreateUser(order.getCreateUser());
                    if (itemList != null && itemList.size() > 0) {
                        item.setId(itemList.get(0).getId());
                        listingDetails.setId(itemList.get(0).getListingdetailsId());
                        orderSeller.setId(itemList.get(0).getSellerId());
                        sellingStatus.setId(itemList.get(0).getSellingstatusId());
                        shippingDetails.setId(itemList.get(0).getShippingdetailsId());
                        pictureDetails.setId(itemList.get(0).getPicturedetailsId());
                        returnpolicy.setId(itemList.get(0).getOrderreturnpolicyId());
                        List<TradingOrderSeller> sellerList = iTradingOrderSeller.selectOrderGetItemById(orderSeller.getId());
                        if (sellerList != null && sellerList.size() > 0) {
                            sellerInformation.setId(sellerList.get(0).getSellerinfoId());
                        }
                        List<TradingOrderShippingDetails> shippingDetailsList = iTradingOrderShippingDetails.selectOrderGetItemById(shippingDetails.getId());
                        if (shippingDetailsList != null && shippingDetailsList.size() > 0) {
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

                    } else {
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
                    List<TradingOrderShippingServiceOptions> shippingServiceOptionses = iTradingOrderShippingServiceOptions.selectOrderGetItemByShippingDetailsId(shippingDetails.getId());
                    for (TradingOrderShippingServiceOptions shippingServiceOptionse : shippingServiceOptionses) {
                        iTradingOrderShippingServiceOptions.deleteOrderShippingServiceOptions(shippingServiceOptionse);
                    }
                    for (TradingOrderShippingServiceOptions serviceOptionse : serviceOptionses) {
                        serviceOptionse.setShippingdetailsId(shippingDetails.getId());
                        serviceOptionse.setCreateUser(order.getCreateUser());
                        iTradingOrderShippingServiceOptions.saveOrderShippingServiceOptions(serviceOptionse);
                    }
                    order.setShippingdetailsId(shippingDetails.getId());
                    item.setCreateUser(order.getCreateUser());
                    item.setSku(order.getSku());
                    iTradingOrderGetItem.saveOrderGetItem(item);
                    List<TradingOrderItemSpecifics> specificItemList = (List<TradingOrderItemSpecifics>) items.get(GetOrderItemAPI.ITEM_SPECIFICS);
                    List<TradingOrderItemSpecifics> Itemspecifics = iTradingOrderItemSpecifics.selectOrderItemSpecificsByItemId(item.getId());
                    if (Itemspecifics != null && Itemspecifics.size() > 0) {
                        for (TradingOrderItemSpecifics specifics : Itemspecifics) {
                            iTradingOrderItemSpecifics.deleteOrderItemSpecifics(specifics);
                        }
                    }
                    for (TradingOrderItemSpecifics specifics : specificItemList) {
                        specifics.setOrderitemId(item.getId());
                        specifics.setCreateUser(order.getCreateUser());
                        iTradingOrderItemSpecifics.saveOrderItemSpecifics(specifics);
                    }
                    List<TradingOrderVariation> variationList = (List<TradingOrderVariation>) items.get(GetOrderItemAPI.VARIATION);
                    List<TradingOrderVariation> variationLists = iTradingOrderVariation.selectOrderVariationByItemId(item.getId());
                    if (variationLists != null && variationLists.size() > 0) {
                        for (TradingOrderVariation specifics : variationLists) {
                            List<TradingOrderVariationSpecifics> specificsVariations = iTradingOrderVariationSpecifics.selectOrderVariationSpecificsByVariationId(specifics.getId());
                            if (specificsVariations != null && specificsVariations.size() > 0) {
                                for (TradingOrderVariationSpecifics specificsVariation : specificsVariations) {
                                    iTradingOrderVariationSpecifics.deleteOrderVariationSpecifics(specificsVariation);
                                }
                            }
                            iTradingOrderVariation.deleteOrderVariation(specifics);
                        }
                    }
                    for (TradingOrderVariation specifics : variationList) {
                        specifics.setOrderitemId(item.getId());
                        specifics.setCreateUser(order.getCreateUser());
                        iTradingOrderVariation.saveOrderVariation(specifics);
                        List<TradingOrderVariationSpecifics> specificsList = (List<TradingOrderVariationSpecifics>) items.get(GetOrderItemAPI.VARIATION_SPECIFICS);
                        for (TradingOrderVariationSpecifics specificsVariation : specificsList) {
                            specificsVariation.setOrdervariationId(specifics.getId());
                            specificsVariation.setCreateUser(order.getCreateUser());
                            iTradingOrderVariationSpecifics.saveOrderVariationSpecifics(specificsVariation);
                        }
                    }
                    List<TradingOrderPictures> pictrueList = (List<TradingOrderPictures>) items.get(GetOrderItemAPI.PICTURES);
                    List<TradingOrderPictures> pictrueLists = iTradingOrderPictures.selectOrderPicturesByItemId(item.getId());
                    if (pictrueLists != null && pictrueLists.size() > 0) {
                        for (TradingOrderPictures specifics : pictrueLists) {
                            iTradingOrderPictures.deleteOrderPictures(specifics);
                        }
                    }
                    for (TradingOrderPictures specifics : pictrueList) {
                        specifics.setOrderitemId(item.getId());
                        specifics.setCreateUser(order.getCreateUser());
                        iTradingOrderPictures.saveOrderPictures(specifics);
                    }
                    order.setItemflag(1);
                    iTradingOrderGetOrdersNoTransaction.saveOrderGetOrders(order);
                } else {
                    logger.error("Order定时同步商品获取apisessionid失败!" + itemres + "\n\norderId:" + order.getOrderid());
                }
            }
        }
    }

    @Override
    public void synchronizeOrderAccount(List<TradingOrderGetOrders> orders) throws Exception {
        //测试环境
        UsercontrollerDevAccountExtend ds = new UsercontrollerDevAccountExtend();//开发者帐号id
        ds.setApiSiteid("0");
        //真实环境
        /*UsercontrollerDevAccountExtend ds=new UsercontrollerDevAccountExtend();
        ds.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
        ds.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
        ds.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
        ds.setApiCompatibilityLevel("883");
        ds.setApiSiteid("0");*/
        for(TradingOrderGetOrders order:orders){
            ds.setApiCallName(APINameStatic.GetAccount);
            UsercontrollerEbayAccount ebay=iUsercontrollerEbayAccount.selectByEbayAccount(order.getSelleruserid());
            if(ebay!=null&&StringUtils.isNotBlank(ebay.getEbayToken())) {
                String token=ebay.getEbayToken();
                Map accountmap = new HashMap();
                //真实环境
                //accountmap.put("token","AgAAAA**AQAAAA**aAAAAA**CLSRUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlYCjDJGCqA+dj6x9nY+seQ**FdYBAA**AAMAAA**w2sMbwlQ7TBHWxj9EsVedHQRI3+lonY9MDfiyayQbnFkjEanjL/yMCpS/D2B9xHRzRx+ppxWZkRPgeAKJvNotPLLrVTuEzOl5M7pi6Tw8+pzcmIEsOh7HQO78JlyFlvLc/ruE6/hG0E/HO1UX76YBwxp00N9f1NNUpo5u36D/TYsx5O2jXFTKkCOHwz6RW9vtN6TU39aLm+JQme2+NfFFXnbX8MHzoUiX7Sty0R88ZpX5wLp8ZdgXCEc5zZDQziYB1MSXF9hsmby5wKbxFF+OvW/zKADThk1gprgAgnEOucyoao+cUMHopLlYgMbjnLzdCXP5F9z+fkYTnKF6AEl5eHBpcKQGbPzswnKebRoBVw+bI2I1C/iq+PvBUyndFAexjrvlDQbEKr6qb6AWRVTTfkW2ce6a0ixRuCTq35zEpWpfAqkSKo+X23d/Q4V8R30rDXotOWDZL6o408cMO+UQ17uVA2arA1JNkYfc/AZ0T0z7ze5o/yp93jJPlDgi05Ut4fpCAMZw3X85GxrTlbEtawWgoyUbmMuv4f6QHZLZAerOaJA8DRJkzkzjJJ025bp1HvAECOc4ggdv0cofu4q96shssgNYYZJUPM+q4+0fnGK0pxQTNY9SV6vSaVCVoTZJo6vefW7OiHX2/eLoPKFuUfsKXXEv9OY71gD1xzYg/rpCMAqCTq1dKqqyT1R5fxANnoRX7vwkq+7jkCj2fAfKTnHi9mSuBFsilKLmnsqqWy3IGShMgdxiQwBEk6IWi9C");
                //accountmap.put("Itemid",order.getItemid());
                //accountmap.put("fromTime",start);
                //accountmap.put("toTime",end);
                //测试环境
                Date date=new Date();
                Date date2= DateUtils.addDays(date, 1);
                Date date1=DateUtils.subDays(date2, 7);
                Date end1= com.base.utils.common.DateUtils.turnToDateEnd(date2);
                Date start1=com.base.utils.common.DateUtils.turnToDateStart(date1);
                String start= DateUtils.DateToString(start1);
                String end=DateUtils.DateToString(end1);
                accountmap.put("token", token);
                accountmap.put("Itemid", order.getItemid());
                accountmap.put("fromTime", start);
                accountmap.put("toTime", end);
                String accountxml = BindAccountAPI.getGetAccount(accountmap);
                AddApiTask addApiTask = new AddApiTask();
                //真实环境
                //Map<String, String> accountresmap = addApiTask.exec2(ds, accountxml, "https://api.ebay.com/ws/api.dll");
                //测试环境
                Map<String, String> accountresmap = addApiTask.exec2(ds, accountxml, apiUrl);
                String accountr1 = accountresmap.get("stat");
                String accountres = accountresmap.get("message");
                if ("fail".equalsIgnoreCase(accountr1)) {
                    logger.error("定时同步订单Account调用API失败:"+accountres+"\n\nXML:"+accountxml);
                }
                String accountack="";
                try{
                    accountack = SamplePaseXml.getVFromXmlString(accountres, "Ack");
                }catch (Exception e){
                    logger.error("ScheduleGetTimerOrdersImpl第981"+accountres,e);
                    accountack="";
                }
                if ("Success".equalsIgnoreCase(accountack)) {
                    List<TradingOrderGetAccount> accountList = GetAccountAPI.parseXMLAndSave(accountres);
                    for (TradingOrderGetAccount acc : accountList) {
                        List<TradingOrderGetAccount> accountList1 = iTradingOrderGetAccount.selectTradingOrderGetAccountByTransactionId(order.getTransactionid());
                        if (accountList1 != null && accountList1.size() > 0) {
                            for (TradingOrderGetAccount acc1 : accountList1) {
                                if (acc.getDescription().equals(acc1.getDescription()) && acc.getTransactionid().equals(acc1.getTransactionid())) {
                                    acc.setId(acc1.getId());
                                }
                            }
                        }
                        acc.setCreateUser(order.getCreateUser());
                        iTradingOrderGetAccount.saveOrderGetAccount(acc);
                        order.setAccountflag(1);
                        iTradingOrderGetOrdersNoTransaction.saveOrderGetOrders(order);

                    }
                } else {
                    logger.error("Order定时同步account获取apisessionid失败!" + accountres+"\n\nXML"+accountxml);
                }
            }
        }

    }

    @Override
    public void synchronizeOrderSellerTrasaction(List<TradingOrderGetOrders> orders) throws Exception {
        //测试环境
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();//开发者帐号id
        d.setApiSiteid("0");
        //真实环境
      /*  UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
        d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
        d.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
        d.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
        d.setApiCompatibilityLevel("883");
        d.setApiSiteid("0");*/
        for(TradingOrderGetOrders order:orders) {
            d.setApiCallName("GetSellerTransactions");
            UsercontrollerEbayAccount ebay = iUsercontrollerEbayAccount.selectByEbayAccount(order.getSelleruserid());
            if (ebay != null && StringUtils.isNotBlank(ebay.getEbayToken())) {
                AddApiTask addApiTask = new AddApiTask();
                String token=ebay.getEbayToken();
                String sellerxml = BindAccountAPI.GetSellerTransactions(token);//获取接受消息
                Map<String, String> resSellerMap = addApiTask.exec2(d, sellerxml, apiUrl);
                //Map<String, String> resSellerMap = addApiTask.exec2(d, sellerxml, "https://api.ebay.com/ws/api.dll");
                //------------------------
                String sellerR1 = resSellerMap.get("stat");
                String sellerRes = resSellerMap.get("message");
                if ("fail".equalsIgnoreCase(sellerR1)) {
                    logger.error("定时同步订单外部交易的API失败:"+sellerRes+"\n\nXML"+sellerxml);
                }
                String sellerAck="";
                try{
                    sellerAck = SamplePaseXml.getVFromXmlString(sellerRes, "Ack");
                }catch (Exception e){
                    logger.error("ScheduleGetTimerOrdersImpl第1062"+sellerRes,e);
                    sellerAck="";
                }
                if ("Success".equalsIgnoreCase(sellerAck)) {
                    List<TradingOrderGetSellerTransactions> lists = GetSellerTransactionsAPI.parseXMLAndSave(sellerRes);
                    for (TradingOrderGetSellerTransactions list : lists) {
                        List<TradingOrderGetSellerTransactions> transactionseList = iTradingOrderGetSellerTransactions.selectTradingOrderGetSellerTransactionsByTransactionId(list.getTransactionid());
                        if (transactionseList != null && transactionseList.size() > 0) {
                            list.setId(transactionseList.get(0).getId());
                        }
                        list.setCreateUser(order.getCreateUser());
                        iTradingOrderGetSellerTransactions.saveOrderGetSellerTransactions(list);
                        order.setSellertrasactionflag(1);
                        iTradingOrderGetOrdersNoTransaction.saveOrderGetOrders(order);
                    }
                } else {
                    logger.error("Order定时同步外部交易获取apisessionid失败!" + sellerRes+"\n\nXML"+sellerxml);
                }
            }
        }
    }

}

