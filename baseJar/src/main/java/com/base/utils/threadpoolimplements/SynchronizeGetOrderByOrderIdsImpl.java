package com.base.utils.threadpoolimplements;

import com.base.database.trading.model.*;
import com.base.sampleapixml.GetOrdersAPI;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.threadpool.TaskPool;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageStatic;
import com.trading.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/9/13.
 */
@Service
public class SynchronizeGetOrderByOrderIdsImpl implements ThreadPoolBaseInterFace {
    static Logger logger = Logger.getLogger(SynchronizeGetOrderByOrderIdsImpl.class);
    @Autowired
    private ITradingOrderOrderVariationSpecifics iTradingOrderOrderVariationSpecifics;
    @Autowired
    private ITradingOrderGetOrders iTradingOrderGetOrders;
    @Autowired
    private ITradingOrderGetOrdersNoTransaction iTradingOrderGetOrdersNoTransaction;
    @Autowired
    private ITradingOrderAddMemberMessageAAQToPartner iTradingOrderAddMemberMessageAAQToPartner;
    @Autowired
    private ITradingAutoMessage iTradingAutoMessage;
    @Autowired
    private ITradingAutoMessageAttr iTradingAutoMessageAttr;
    @Override
    public <T> void doWork(String res, T... t) {
        if(StringUtils.isEmpty(res)){return;}
        TaskMessageVO taskMessageVO = (TaskMessageVO)t[0];
        try {
            String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
            if ("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)) {
                if("Warning".equalsIgnoreCase(ack)){
                    String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                    if(!StringUtils.isNotBlank(errors)){
                        errors = SamplePaseXml.getWarningInformation(res);
                    }
                    logger.error("获取订单有警告!" + errors);
                }
                Map<String,Object> mapOrder= GetOrdersAPI.parseXMLAndSave(res);
                List<TradingOrderGetOrders> orders = (List<TradingOrderGetOrders>) mapOrder.get("OrderList");
                List<List<TradingOrderOrderVariationSpecifics>> OrderVariationSpecificses = (List<List<TradingOrderOrderVariationSpecifics>>) mapOrder.get("OrderVariation");
                for (List<TradingOrderOrderVariationSpecifics> orderVariationSpecificses : OrderVariationSpecificses) {
                    for (TradingOrderOrderVariationSpecifics orderOrderVariationSpecifics : orderVariationSpecificses) {
                        List<TradingOrderOrderVariationSpecifics> list = iTradingOrderOrderVariationSpecifics.selectOrderOrderVariationSpecificsByAll(orderOrderVariationSpecifics.getSku(), orderOrderVariationSpecifics.getName(), orderOrderVariationSpecifics.getValue());
                        if (list == null || list.size() == 0) {
                            orderOrderVariationSpecifics.setCreateUser(taskMessageVO.getMessageTo());
                            iTradingOrderOrderVariationSpecifics.saveOrderOrderVariationSpecifics(orderOrderVariationSpecifics);
                        }
                    }
                }
                for (TradingOrderGetOrders order : orders) {
                    List<TradingOrderGetOrders> ls = iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(order.getTransactionid(),order.getSelleruserid());
                    if(ls!=null&&ls.size()>0){
                        order.setId(ls.get(0).getId());
                    }else{
                        order.setAccountflag(0);
                        order.setSellertrasactionflag(0);
                        order.setItemflag(0);
                    }
                    //--------------自动发送消息-------------------------
                    List<TradingOrderAddMemberMessageAAQToPartner> addmessages = iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByTransactionId(order.getTransactionid(), 2, order.getSelleruserid());
                    if (addmessages != null && addmessages.size() > 0) {
                        boolean flag = false;
                        String trackingnumber = order.getShipmenttrackingnumber();
                        for (TradingOrderAddMemberMessageAAQToPartner addmessage : addmessages) {
                            if (StringUtils.isNotBlank(trackingnumber) && addmessage.getMessageflag() == 2) {
                                flag = true;
                            }
                        }
                        if (flag && StringUtils.isNotBlank(trackingnumber)) {
                            order.setPaypalflag(0);
                            order.setShippedflag(1);
                        } else {
                            order.setPaypalflag(0);
                            order.setShippedflag(0);
                        }
                    } else {
                        String trackingnumber = order.getShipmenttrackingnumber();
                        if ("Complete".equals(order.getStatus()) && !StringUtils.isNotBlank(trackingnumber) && "Completed".equals(order.getOrderstatus())) {
                            //付款后发送消息
                            order.setPaypalflag(1);
                            order.setShippedflag(0);
                        }else if (StringUtils.isNotBlank(trackingnumber)) {
                            order.setPaypalflag(0);
                            order.setShippedflag(1);
                        }else{
                            order.setPaypalflag(0);
                            order.setShippedflag(0);
                        }
                    }
                    order.setTrackstatus("0");
                    order.setCreateUser(taskMessageVO.getMessageTo());
                    TaskPool.togos.put(order);
                }
                if("0".equals(TaskPool.togosBS[0])){
                    iTradingOrderGetOrdersNoTransaction.saveOrderGetOrders(null);
                }
            }else{
                logger.error("订单API参数错误!" + SamplePaseXml.getWarningInformation(res));
            }
        } catch (Exception e) {
            logger.error("根据订单号同步订单解析XML出错28",e);
        }
    }
    private TradingOrderGetOrders autoMessageSet(String type,Long userId,TradingOrderGetOrders order){
        List<TradingAutoMessage> partners = iTradingAutoMessage.selectAutoMessageByType(type, userId);
        if(partners!=null&&partners.size()>0) {
            for (TradingAutoMessage partner : partners) {
                if(partner==null){
                    order.setAutomessageId(0L);
                }
                if (partner.getStartuse() == 1) {
                   /* logger.error("----------------------------判断符合自动消息条件的订单开始---------------------------------");*/
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
                            TradingDataDictionary dataDict= DataDictionarySupport.getTradingDataDictionaryByID(serviceAttr.getDictionaryId());
                            String service = order.getSelectedshippingservice();
                            String dicService=dataDict.getName();
                            if (dicService.equals(service)) {
                                serviceFlag = true;
                            }
                        }
                        if (internationalServiceAttrs != null && internationalServiceAttrs.size() > 0) {
                            for (TradingAutoMessageAttr internationalServiceAttr : internationalServiceAttrs) {
                                String internationalService = order.getSelectedshippingservice();
                                TradingDataDictionary dataDict=DataDictionarySupport.getTradingDataDictionaryByID(internationalServiceAttr.getDictionaryId());
                                String dicInternationalService=dataDict.getName();
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
                            String dicInternationalService=dataDict.getName();
                            String internationalService = order.getSelectedshippingservice();
                            if (dicInternationalService.equals(internationalService)) {
                                internationalServiceFlag = true;
                            }
                        }
                        if (serviceAttrs != null && serviceAttrs.size() > 0) {
                            for (TradingAutoMessageAttr serviceAttr : serviceAttrs) {
                                String service = order.getSelectedshippingservice();
                                TradingDataDictionary dataDict=DataDictionarySupport.getTradingDataDictionaryByID(serviceAttr.getDictionaryId());
                                String dicService=dataDict.getName();
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
                        /*if (!orderItemFlag){
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
                        }*/
                    if (exceptCountryFlag && internationalServiceFlag && serviceFlag && amountFlag && countryFlag && orderItemFlag) {
                        autoFlag = true;
                    }
                    if (autoFlag) {
                        Date date2 = sendAutoMessageTime(partner, order);
                        order.setSendmessagetime(date2);
                        order.setAutomessageId(partner.getId());
                        continue;
                    }else{
                        order.setAutomessageId(0L);
                    }
                    /*logger.error("----------------------------判断符合自动消息条件的订单结束---------------------------------");*/
                }else{
                    order.setAutomessageId(0L);
                }
            }
        }
        return order;
    }
    private Date sendAutoMessageTime(TradingAutoMessage partner,TradingOrderGetOrders order){
        int day = partner.getDay();
        int hour = partner.getHour();
        Date date = order.getLastmodifiedtime();
        Date date1 = org.apache.commons.lang.time.DateUtils.addDays(date, day);
        Date date2 = org.apache.commons.lang.time.DateUtils.addHours(date1, hour);
        return date2;
    }
    @Override
    public String getType() {
        return SiteMessageStatic.SYNCHRONIZE_GET_ORDER_BY_ORDERIDS_BEAN;
    }
}
