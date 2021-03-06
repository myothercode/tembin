package com.task.service.impl;

import com.base.aboutpaypal.service.PayPalService;
import com.base.database.sitemessage.model.PublicSitemessage;
import com.base.database.task.model.TaskGetOrders;
import com.base.database.task.model.TaskGetOrdersSellerTransaction;
import com.base.database.trading.model.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.*;
import com.base.utils.common.DateUtils;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.threadpool.TaskPool;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageService;
import com.sitemessage.service.SiteMessageStatic;
import com.task.service.IScheduleGetTimerOrders;
import com.task.service.ITaskGetOrders;
import com.task.service.ITaskGetOrdersSellerTransaction;
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
    @Autowired
    private ITaskGetOrdersSellerTransaction iTaskGetOrdersSellerTransaction;
    @Autowired
    private ITradingAutoMessageAndOrder iTradingAutoMessageAndOrder;
    @Override
    public void synchronizeOrders(List<TaskGetOrders> taskGetOrders) throws Exception {
        /*CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);*/
            for(TaskGetOrders taskGetOrder:taskGetOrders){
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
                String xml ="";
                if(taskGetOrder.getNewuserflag()==null){
                    xml=BindAccountAPI.getGetOrders(map);
                }else{
                    xml=BindAccountAPI.getGetOrdersNewUser(map);
                }
                AddApiTask addApiTask = new AddApiTask();
                Map<String, String> resMap = addApiTask.exec2(d, xml,apiUrl);
                //Map<String, String> resMap = addApiTask.exec2(d, xml,"https://api.ebay.com/ws/api.dll");
                String r1 = resMap.get("stat");
                String res = resMap.get("message");

                if ("fail".equalsIgnoreCase(r1)) {
                    String LongError=SamplePaseXml.getWarningInformation(res);
                    logger.error("定时同步订单调用API失败:"+LongError);
                }
                String ack="";
                try{
                    ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                }catch (Exception e){
                    logger.error("ScheduleGetTimerOrdersImpl第149",e);
                    ack="";
                }
                if ("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)) {
                    if("Warning".equalsIgnoreCase(ack)){
                        String errors = "";
                        try{
                            errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                        }catch (Exception e){
                            logger.error("ScheduleGetTimerOrdersImpl第158",e);
                            errors="";
                        }
                        if(!StringUtils.isNotBlank(errors)){
                            try{
                                errors =  SamplePaseXml.getWarningInformation(res);
                            }catch (Exception e){
                                logger.error("ScheduleGetTimerOrdersImpl第165",e);
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
                            UsercontrollerDevAccountExtend ds=new UsercontrollerDevAccountExtend();
                           /* ds.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
                            ds.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
                            ds.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
                            ds.setApiCompatibilityLevel("883");*/

                            ds.setApiSiteid("0");
                            ds.setApiCallName(APINameStatic.GetOrders);
                            map.put("page", i + "");
                            if(taskGetOrder.getNewuserflag()==null){
                                xml = BindAccountAPI.getGetOrders(map);
                            }else{
                                xml = BindAccountAPI.getGetOrdersNewUser(map);
                            }
                            resMap = addApiTask.exec2(ds, xml,apiUrl);
                            //resMap = addApiTask.exec2(ds, xml, "https://api.ebay.com/ws/api.dll");
                   /* resMap = addApiTask.exec2(d, xml, "https://api.ebay.com/ws/api.dll");*/
                            r1 = resMap.get("stat");
                            res = resMap.get("message");
                            if ("fail".equalsIgnoreCase(r1)) {
                                String LongError=SamplePaseXml.getWarningInformation(res);
                                logger.error("循环中的定时同步订单调用API失败:"+LongError);
                            }
                            ack="";
                            try{
                                ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                            }catch (Exception e){
                                logger.error("ScheduleGetTimerOrdersImpl第215",e);
                                ack="";
                            }
                            if (!"Success".equalsIgnoreCase(ack)&&!"Warning".equalsIgnoreCase(ack)) {
                                String errors = "";
                                try{
                                    errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                                }catch (Exception e){
                                    logger.error("ScheduleGetTimerOrdersImpl第223",e);
                                    errors="";
                                }
                                if(!StringUtils.isNotBlank(errors)){
                                    try{
                                        errors =  SamplePaseXml.getWarningInformation(res);
                                    }catch (Exception e){
                                        logger.error("ScheduleGetTimerOrdersImpl第211",e);
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
                                    logger.error("ScheduleGetTimerOrdersImpl第253",e);
                                    errors="";
                                }
                                if(!StringUtils.isNotBlank(errors)){
                                    try{
                                        errors =  SamplePaseXml.getWarningInformation(res);
                                    }catch (Exception e){
                                        logger.error("ScheduleGetTimerOrdersImpl第260",e);
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
                                List<TradingOrderGetOrders> ls = iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(order.getTransactionid(),order.getSelleruserid());
                                if(ls!=null&&ls.size()>0){
                                    order.setId(ls.get(0).getId());
                                    order.setCreateTime(ls.get(0).getCreateTime());
                                    order.setUuid(ls.get(0).getUuid());
                                    if(ls.get(0).getAccountflag()!=null){
                                        order.setAccountflag(ls.get(0).getAccountflag());
                                    }else{
                                        order.setAccountflag(0);
                                    }
                                    if(ls.get(0).getItemflag()!=null){
                                        order.setItemflag(ls.get(0).getItemflag());
                                    }else{
                                        order.setItemflag(0);
                                    }
                                    if(ls.get(0).getSellertrasactionflag()!=null){
                                        order.setSellertrasactionflag(ls.get(0).getSellertrasactionflag());
                                    }else{
                                        order.setSellertrasactionflag(0);
                                    }
                                }else{
                                    order.setAccountflag(0);
                                    order.setSellertrasactionflag(0);
                                    order.setItemflag(0);
                                    order.setAccountflag(0);
                                    order.setItemflag(0);
                                    order.setSellertrasactionflag(0);
                                }
                                List<TradingOrderAddMemberMessageAAQToPartner> addmessages = iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByTransactionId(order.getTransactionid(), 2, order.getSelleruserid());
                                Date date1=DateUtils.buildDateTime(2015,0,29,0,0,0);
                                Date date2=DateUtils.buildDateTime(2015,0,24,0,0,0);
                                if (addmessages != null && addmessages.size() > 0) {
                                    //order=autoMessageSet("标记已发货",taskGetOrder.getUserid(),order);
                                    boolean flag = false;
                                    String trackingnumber = order.getShipmenttrackingnumber();
                                    for (TradingOrderAddMemberMessageAAQToPartner addmessage : addmessages) {
                                        if (StringUtils.isNotBlank(trackingnumber) && addmessage.getMessageflag() == 2) {
                                            flag = true;
                                        }
                                    }
                                    Date modifyDate=order.getLastmodifiedtime();
                                    if (!flag && StringUtils.isNotBlank(trackingnumber)&&modifyDate.after(date2)) {
                                        order.setPaypalflag(0);
                                        order.setShippedflag(1);
                                        order.setAutomessageId(1L);
                                    } else {
                                        order.setPaypalflag(1);
                                        order.setShippedflag(1);
                                        order.setAutomessageId(0L);
                                    }
                                } else {
                                    String trackingnumber = order.getShipmenttrackingnumber();
                                    if ("Complete".equals(order.getStatus()) && !StringUtils.isNotBlank(trackingnumber) && "Completed".equals(order.getOrderstatus())) {
                                        //付款后发送消息
                                        //order=autoMessageSet("收到买家付款",taskGetOrder.getUserid(),order);
                                        Date creatDate=order.getCreatedtime();
                                        if(creatDate.after(date1)){
                                            order.setPaypalflag(1);
                                            order.setShippedflag(0);
                                            order.setAutomessageId(1L);
                                        }else{
                                            order.setPaypalflag(0);
                                            order.setShippedflag(0);
                                            order.setAutomessageId(0L);
                                        }
                                    }else if ("Complete".equals(order.getStatus())&&StringUtils.isNotBlank(trackingnumber)&& "Completed".equals(order.getOrderstatus())) {
                                        //order=autoMessageSet("标记已发货",taskGetOrder.getUserid(),order);
                                        Date modifyDate=order.getLastmodifiedtime();
                                        if(modifyDate.after(date2)){
                                            order.setPaypalflag(0);
                                            order.setShippedflag(1);
                                            order.setAutomessageId(1L);
                                        }else{
                                            order.setPaypalflag(0);
                                            order.setShippedflag(0);
                                            order.setAutomessageId(0L);
                                        }
                                    }else{
                                        order.setPaypalflag(0);
                                        order.setShippedflag(0);
                                        order.setAutomessageId(0L);
                                    }
                                }
                                order.setTrackstatus("0");
                                order.setCreateUser(taskGetOrder.getUserid());
                             /*   if(!StringUtils.isNotBlank(order.getShipmenttrackingnumber())){
                                    order.setShipmenttrackingnumber("");
                                }
                                if(!StringUtils.isNotBlank(order.getShippingcarrierused())){
                                    order.setShippingcarrierused("");
                                }
                                if(order.getShippedtime()==null){
                                    order.setShippedtime(null);
                                }*/
                                TaskPool.togos.put(order);
                                //iTradingOrderGetOrdersNoTransaction.saveOrderGetOrders(order);
                            }
                            if("0".equals(TaskPool.togosBS[0])){
                                iTradingOrderGetOrdersNoTransaction.saveOrderGetOrders(null);
                            }
                        }
                    }
                }else {
                    String LongError=SamplePaseXml.getWarningInformation(res);
                    logger.error("Order获取apisessionid失败!"+LongError);
                }
                if(taskGetOrder.getNewuserflag()==null){
                    Integer flag1=taskGetOrder.getTokenflag();
                    flag1=flag1+1;
                    taskGetOrder.setTokenflag(flag1);
                    iTaskGetOrders.saveListTaskGetOrders(taskGetOrder);
                }else{
                    taskGetOrder.setNewuserflag(1);
                    iTaskGetOrders.saveListTaskGetOrders(taskGetOrder);
                }

            }
    }
   /* private Date sendAutoMessageTime(TradingAutoMessage partner,TradingOrderGetOrders order){
        int day = partner.getDay();
        int hour = partner.getHour();
        Date date = order.getLastmodifiedtime();
        Date date1 = org.apache.commons.lang.time.DateUtils.addDays(date, day);
        Date date2 = org.apache.commons.lang.time.DateUtils.addHours(date1, hour);
        return date2;
    }
    private TradingOrderGetOrders autoMessageSet(String type,Long userId,TradingOrderGetOrders order) throws Exception {
        List<TradingAutoMessage> partners = iTradingAutoMessage.selectAutoMessageByType(type, userId);
        if(partners!=null&&partners.size()>0) {
            Boolean automessageFlag=false;
            List<TradingAutoMessageAndOrder> autoMessageAndOrders= iTradingAutoMessageAndOrder.selectAutoMessageAndOrderByAutoOrderId(order.getId());
            if(autoMessageAndOrders!=null&&autoMessageAndOrders.size()>0){
                for(TradingAutoMessageAndOrder autoMessageAndOrder:autoMessageAndOrders){
                    iTradingAutoMessageAndOrder.deleteAutoMessageAndOrder(autoMessageAndOrder);
                }
            }
            for (TradingAutoMessage partner : partners) {
                if (partner!=null&&partner.getStartuse() == 1) {
                    Boolean autoFlag = false;
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
                                TradingDataDictionary dataDict=DataDictionarySupport.getTradingDataDictionaryByID(serviceAttr.getDictionaryId());
                                String service = order.getSelectedshippingservice();
                                String dicService=dataDict.getValue();
                                if (dicService.equals(service)) {
                                    serviceFlag = true;
                                }
                            }
                            if (internationalServiceAttrs != null && internationalServiceAttrs.size() > 0) {
                                for (TradingAutoMessageAttr internationalServiceAttr : internationalServiceAttrs) {
                                    String internationalService = order.getSelectedshippingservice();
                                    TradingDataDictionary dataDict=DataDictionarySupport.getTradingDataDictionaryByID(internationalServiceAttr.getDictionaryId());
                                    String dicInternationalService=dataDict.getValue();
                                    if (dicInternationalService.equals(internationalService)) {
                                        internationalServiceFlag = true;
                                    }
                                }
                            } else {
                                internationalServiceFlag = true;
                            }
                        } else if (internationalServiceAttrs != null && internationalServiceAttrs.size() > 0) {
                            for (TradingAutoMessageAttr internationalServiceAttr : internationalServiceAttrs) {
                                TradingDataDictionary dataDict=DataDictionarySupport.getTradingDataDictionaryByID(internationalServiceAttr.getDictionaryId());
                                String dicInternationalService=dataDict.getValue();
                                String internationalService = order.getSelectedshippingservice();
                                if (dicInternationalService.equals(internationalService)) {
                                    internationalServiceFlag = true;
                                }
                            }
                            if (serviceAttrs != null && serviceAttrs.size() > 0) {
                                for (TradingAutoMessageAttr serviceAttr : serviceAttrs) {
                                    String service = order.getSelectedshippingservice();
                                    TradingDataDictionary dataDict=DataDictionarySupport.getTradingDataDictionaryByID(serviceAttr.getDictionaryId());
                                    String dicService=dataDict.getValue();
                                    if (dicService.equals(service)) {
                                        serviceFlag = true;
                                    }
                                }
                            }else{
                                serviceFlag = true;
                            }
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
                        *//*if (!orderItemFlag){
                            logger.error("订单号("+order.getOrderid()+")不符合自动消息id("+partner.getId()+")订单商品的设置");
                        }
                        if (!countryFlag){
                            logger.error("订单号("+order.getOrderid()+")不符合自动消息id("+partner.getId()+")订单目的地的设置");
                        }
                        if (!exceptCountryFlag){
                            logger.error("订单号("+order.getOrderid()+")不符合自动消息id("+partner.getId()+")订单不包括目的地的设置");
                        }
                        if (!amountFlag){
                            logger.error("订单号("+order.getOrderid()+")不符合自动消息id("+partner.getId()+")订单卖家账号的设置");
                        }
                        if (!serviceFlag){
                            logger.error("订单号("+order.getOrderid()+")不符合自动消息id("+partner.getId()+")订单国内运输选项的设置");
                        }
                        if (!internationalServiceFlag){
                            logger.error("订单号("+order.getOrderid()+")不符合自动消息id("+partner.getId()+")订单国际运输选项的设置");
                        }*//*
                        if (exceptCountryFlag && internationalServiceFlag && serviceFlag && amountFlag && countryFlag && orderItemFlag) {
                            autoFlag = true;
                        }
                    if (autoFlag) {
                        Date date2 = sendAutoMessageTime(partner, order);
                        order.setSendmessagetime(date2);
                        order.setAutomessageId(partner.getId());
                        automessageFlag=true;
                        TradingAutoMessageAndOrder messageAndOrder=new TradingAutoMessageAndOrder();
                        messageAndOrder.setOrderId(order.getId());
                        messageAndOrder.setAutomessageId(partner.getId());
                        messageAndOrder.setSendtime(date2);
                        iTradingAutoMessageAndOrder.saveAutoMessageAndOrder(messageAndOrder);
                    }
                    *//*logger.error("----------------------------判断符合自动消息条件的订单结束---------------------------------");*//*
                }
            }
            if(automessageFlag){
                order.setAutomessageId(1L);
            }else{
                order.setAutomessageId(0L);
            }
        }
        return order;
    }*/
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
                    String LongError=SamplePaseXml.getWarningInformation(itemres);
                    logger.error("定时同步订单商品调用API失败:" +LongError);
                }
                String itemack = "";
                try {
                    itemack = SamplePaseXml.getVFromXmlString(itemres, "Ack");
                } catch (Exception e) {
                    logger.error("ScheduleGetTimerOrdersImpl第769", e);
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
                    item.setUpdatetime(new Date());
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

                } else {
                    String LongError=SamplePaseXml.getWarningInformation(itemres);
                    logger.error("Order定时同步商品获取apisessionid失败!" +LongError);
                }
            }
            order.setItemflag(1);
            TaskPool.togos.put(order);
        }
        if("0".equals(TaskPool.togosBS[0])){
            iTradingOrderGetOrdersNoTransaction.saveOrderGetOrders(null);
        }
    }

    @Override
    public void synchronizeOrderAccount(List<TradingOrderGetOrders> orders) throws Exception {
        //测试环境
        UsercontrollerDevAccountExtend ds = new UsercontrollerDevAccountExtend();//开发者帐号id
        ds.setApiSiteid("0");
        //真实环境
       /* UsercontrollerDevAccountExtend ds=new UsercontrollerDevAccountExtend();
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
                    String LongError=SamplePaseXml.getWarningInformation(accountres);
                    logger.error("定时同步订单Account调用API失败:"+LongError);
                }
                String accountack="";
                try{
                    accountack = SamplePaseXml.getVFromXmlString(accountres, "Ack");
                }catch (Exception e){
                    logger.error("ScheduleGetTimerOrdersImpl第981",e);
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
                        acc.setUpdatetime(new Date());
                        iTradingOrderGetAccount.saveOrderGetAccount(acc);
                        //iTradingOrderGetOrdersNoTransaction.saveOrderGetOrders(order);
                    }
                } else {
                    String LongError=SamplePaseXml.getWarningInformation(accountres);
                    logger.error("Order定时同步account获取apisessionid失败!" +LongError);
                }
            }
            order.setAccountflag(1);
            TaskPool.togos.put(order);
        }
        if("0".equals(TaskPool.togosBS[0])){
            iTradingOrderGetOrdersNoTransaction.saveOrderGetOrders(null);
        }
    }

    @Override
    public void synchronizeOrderSellerTrasaction(TaskGetOrdersSellerTransaction sellerTransaction) throws Exception {
        //测试环境
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();//开发者帐号id
        d.setApiSiteid("0");
        //真实环境
     /*   UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
        d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
        d.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
        d.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
        d.setApiCompatibilityLevel("883");
        d.setApiSiteid("0");*/
        //-----------修改后-----------------
        d.setApiCallName("GetSellerTransactions");
        AddApiTask addApiTask = new AddApiTask();
        String token=sellerTransaction.getToken();
        Date date=new Date();
        Date date2= DateUtils.addDays(date, 1);
        Date date1=DateUtils.subDays(date2, 7);
        Date end1= com.base.utils.common.DateUtils.turnToDateEnd(date2);
        Date start1=com.base.utils.common.DateUtils.turnToDateStart(date1);
        String start= DateUtils.DateToString(start1);
        String end=DateUtils.DateToString(end1);
        String sellerxml = BindAccountAPI.GetSellerTransactions(token,"1",start,end);//获取接受消息
        Map<String, String> resSellerMap = addApiTask.exec2(d, sellerxml, apiUrl);
        //Map<String, String> resSellerMap = addApiTask.exec2(d, sellerxml, "https://api.ebay.com/ws/api.dll");
        //------------------------
        String sellerR1 = resSellerMap.get("stat");
        String sellerRes = resSellerMap.get("message");
        if ("fail".equalsIgnoreCase(sellerR1)) {
            String LongError=SamplePaseXml.getWarningInformation(sellerRes);
            logger.error("定时同步订单外部交易的API失败:"+LongError);
        }
        String sellerAck="";
        try{
            sellerAck = SamplePaseXml.getVFromXmlString(sellerRes, "Ack");
        }catch (Exception e){
            logger.error("ScheduleGetTimerOrdersImpl第1062",e);
            sellerAck="";
        }
        if ("Success".equalsIgnoreCase(sellerAck)) {
            Integer total=GetSellerTransactionsAPI.parseTotalPage(sellerRes);
            for(int i=0;i<total;i++){
                sellerxml= BindAccountAPI.GetSellerTransactions(token,(i+1)+"",start,end);//获取接受消息
                resSellerMap = addApiTask.exec2(d, sellerxml, apiUrl);
                //resSellerMap = addApiTask.exec2(d, sellerxml, "https://api.ebay.com/ws/api.dll");
                sellerR1 = resSellerMap.get("stat");
                sellerRes = resSellerMap.get("message");
                if ("fail".equalsIgnoreCase(sellerR1)) {
                    String LongError=SamplePaseXml.getWarningInformation(sellerRes);
                    logger.error("定时同步订单外部交易的API失败799:"+LongError);
                }
                sellerAck="";
                try{
                    sellerAck = SamplePaseXml.getVFromXmlString(sellerRes, "Ack");
                }catch (Exception e){
                    logger.error("ScheduleGetTimerOrdersImpl第805",e);
                    sellerAck="";
                }
                if ("Success".equalsIgnoreCase(sellerAck)) {
                    List<TradingOrderGetSellerTransactions> lists = GetSellerTransactionsAPI.parseXMLAndSave(sellerRes);
                    for (TradingOrderGetSellerTransactions list : lists) {
                        List<TradingOrderGetSellerTransactions> transactionseList = iTradingOrderGetSellerTransactions.selectTradingOrderGetSellerTransactionsByTransactionId(list.getTransactionid());
                        if (transactionseList != null && transactionseList.size() > 0) {
                            list.setId(transactionseList.get(0).getId());
                        }
                        list.setCreateUser(sellerTransaction.getUserid());
                        list.setUpdatetime(new Date());
                        iTradingOrderGetSellerTransactions.saveOrderGetSellerTransactions(list);

                    }
                }
            }
            Integer flag=sellerTransaction.getTokenflag();
            flag=flag+1;
            sellerTransaction.setTokenflag(flag);
            iTaskGetOrdersSellerTransaction.saveTaskGetOrdersSellerTransaction(sellerTransaction);
        } else {
            String LongError=SamplePaseXml.getWarningInformation(sellerRes);
            logger.error("Order定时同步外部交易获取apisessionid失败!" +LongError);
        }
        //--------修改前---------------
        /*Map<String,String> ackFlagMap=new HashMap<String, String>();
        for(TradingOrderGetOrders order:orders) {
            if(ackFlagMap.get(order.getSelleruserid())==null||(ackFlagMap.get(order.getSelleruserid())!=null&&!"Success".equals(ackFlagMap.get(order.getSelleruserid())))){
                d.setApiCallName("GetSellerTransactions");
                UsercontrollerEbayAccount ebay = iUsercontrollerEbayAccount.selectByEbayAccount(order.getSelleruserid());
                if (ebay != null && StringUtils.isNotBlank(ebay.getEbayToken())) {
                    AddApiTask addApiTask = new AddApiTask();
                    String token=ebay.getEbayToken();
                    Date date=new Date();
                    Date date2= DateUtils.addDays(date, 1);
                    Date date1=DateUtils.subDays(date2, 7);
                    Date end1= com.base.utils.common.DateUtils.turnToDateEnd(date2);
                    Date start1=com.base.utils.common.DateUtils.turnToDateStart(date1);
                    String start= DateUtils.DateToString(start1);
                    String end=DateUtils.DateToString(end1);
                    String sellerxml = BindAccountAPI.GetSellerTransactions(token,"1",start,end);//获取接受消息
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
                    ackFlagMap.put(order.getSelleruserid(),sellerAck);
                    if ("Success".equalsIgnoreCase(sellerAck)) {
                        Integer total=GetSellerTransactionsAPI.parseTotalPage(sellerRes);
                        for(int i=0;i<total;i++){
                            sellerxml= BindAccountAPI.GetSellerTransactions(token,(i+1)+"",start,end);//获取接受消息
                            resSellerMap = addApiTask.exec2(d, sellerxml, apiUrl);
                            //resSellerMap = addApiTask.exec2(d, sellerxml, "https://api.ebay.com/ws/api.dll");
                            sellerR1 = resSellerMap.get("stat");
                            sellerRes = resSellerMap.get("message");
                            if ("fail".equalsIgnoreCase(sellerR1)) {
                                logger.error("定时同步订单外部交易的API失败799:"+sellerRes+"\n\nXML"+sellerxml);
                            }
                            sellerAck="";
                            try{
                                sellerAck = SamplePaseXml.getVFromXmlString(sellerRes, "Ack");
                            }catch (Exception e){
                                logger.error("ScheduleGetTimerOrdersImpl第805"+sellerRes,e);
                                sellerAck="";
                            }
                            ackFlagMap.put(order.getSelleruserid(),sellerAck);
                            if ("Success".equalsIgnoreCase(sellerAck)) {
                                List<TradingOrderGetSellerTransactions> lists = GetSellerTransactionsAPI.parseXMLAndSave(sellerRes);
                                for (TradingOrderGetSellerTransactions list : lists) {
                                    List<TradingOrderGetSellerTransactions> transactionseList = iTradingOrderGetSellerTransactions.selectTradingOrderGetSellerTransactionsByTransactionId(list.getTransactionid());
                                    if (transactionseList != null && transactionseList.size() > 0) {
                                        list.setId(transactionseList.get(0).getId());
                                    }
                                    list.setCreateUser(order.getCreateUser());
                                    list.setUpdatetime(new Date());
                                    iTradingOrderGetSellerTransactions.saveOrderGetSellerTransactions(list);
                                    order.setSellertrasactionflag(1);
                                    TaskPool.togos.put(order);
                                }
                            }
                        }
                    } else {
                        logger.error("Order定时同步外部交易获取apisessionid失败!" + sellerRes+"\n\nXML"+sellerxml);
                    }
                }
            }else{
                order.setSellertrasactionflag(1);
                TaskPool.togos.put(order);
            }
        }
        if("0".equals(TaskPool.togosBS[0])){
            iTradingOrderGetOrdersNoTransaction.saveOrderGetOrders(null);
        }*/
    }

}

