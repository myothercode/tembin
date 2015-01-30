package com.base.utils.scheduleabout.commontask;

import com.base.database.trading.model.*;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.common.MyStringUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.TaskPool;
import com.trading.service.ITradingAutoMessage;
import com.trading.service.ITradingAutoMessageAndOrder;
import com.trading.service.ITradingAutoMessageAttr;
import com.trading.service.ITradingOrderGetOrders;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/29.
 * 通过订单表选取自动消息
 */
public class SynchronizeSelectAutoMessageTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SynchronizeSelectAutoMessageTaskRun.class);
    @Override
    public void run(){
        int i= TaskPool.scheduledThreadPoolTaskExecutor.getActiveCount();
        if(i>30){
            return;
        }
        Boolean b= TaskPool.threadIsAliveByName("thread_" + getScheduledType());
        if(b){
            return;
        }
        Thread.currentThread().setName("thread_" + getScheduledType());
        ITradingOrderGetOrders iTradingOrderGetOrders = (ITradingOrderGetOrders) ApplicationContextUtil.getBean(ITradingOrderGetOrders.class);
        List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByAutoMessageId();
        if(orders!=null&&orders.size()>0){
            for(TradingOrderGetOrders order:orders){
                Integer shipflag=order.getShippedflag();
                Integer paypalflag=order.getPaypalflag();
                if(shipflag!=null&&paypalflag!=null){
                    if(paypalflag==1&&shipflag==0){
                        try {
                            autoMessageSet("收到买家付款",order.getCreateUser(),order);
                        } catch (Exception e) {
                            logger.error("SynchronizeSelectAutoMessageTaskRun第36:"+e);
                        }
                    }
                    if(paypalflag==0&&shipflag==1){
                        try {
                            autoMessageSet("标记已发货",order.getCreateUser(),order);
                        } catch (Exception e) {
                            logger.error("SynchronizeSelectAutoMessageTaskRun第43:" + e);
                        }
                    }
                }
            }
        }
        TaskPool.threadRunTime.remove("thread_" + getScheduledType());
        Thread.currentThread().setName("thread_" + getScheduledType()+ MyStringUtil.getRandomStringAndNum(5));
        //saveTaskFeedBack(liue,date,"Negative");
    }
    private void autoMessageSet(String type,Long userId,TradingOrderGetOrders order) throws Exception {
        ITradingAutoMessage iTradingAutoMessage = (ITradingAutoMessage) ApplicationContextUtil.getBean(ITradingAutoMessage.class);
        ITradingAutoMessageAndOrder iTradingAutoMessageAndOrder = (ITradingAutoMessageAndOrder) ApplicationContextUtil.getBean(ITradingAutoMessageAndOrder.class);
        ITradingAutoMessageAttr iTradingAutoMessageAttr=(ITradingAutoMessageAttr) ApplicationContextUtil.getBean(ITradingAutoMessageAttr.class);
        List<TradingAutoMessage> partners = iTradingAutoMessage.selectAutoMessageByType(type, userId);
        if(partners!=null&&partners.size()>0) {
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
                            TradingDataDictionary dataDict= DataDictionarySupport.getTradingDataDictionaryByID(serviceAttr.getDictionaryId());
                            String service = order.getSelectedshippingservice();
                            String dicService=dataDict.getValue();
                            if (dicService.equals(service)) {
                                serviceFlag = true;
                                internationalServiceFlag=true;
                            }
                        }
                        if (internationalServiceAttrs != null && internationalServiceAttrs.size() > 0) {
                            for (TradingAutoMessageAttr internationalServiceAttr : internationalServiceAttrs) {
                                String internationalService = order.getSelectedshippingservice();
                                TradingDataDictionary dataDict=DataDictionarySupport.getTradingDataDictionaryByID(internationalServiceAttr.getDictionaryId());
                                String dicInternationalService=dataDict.getValue();
                                if (dicInternationalService.equals(internationalService)) {
                                    internationalServiceFlag = true;
                                    serviceFlag = true;
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
                                serviceFlag = true;
                            }
                        }
                        if (serviceAttrs != null && serviceAttrs.size() > 0) {
                            for (TradingAutoMessageAttr serviceAttr : serviceAttrs) {
                                String service = order.getSelectedshippingservice();
                                TradingDataDictionary dataDict=DataDictionarySupport.getTradingDataDictionaryByID(serviceAttr.getDictionaryId());
                                String dicService=dataDict.getValue();
                                if (dicService.equals(service)) {
                                    serviceFlag = true;
                                    internationalServiceFlag = true;
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
                        TradingAutoMessageAndOrder messageAndOrder=new TradingAutoMessageAndOrder();
                        messageAndOrder.setOrderId(order.getId());
                        messageAndOrder.setAutomessageId(partner.getId());
                        messageAndOrder.setSendtime(date2);
                        iTradingAutoMessageAndOrder.saveAutoMessageAndOrder(messageAndOrder);
                    }
                    /*logger.error("----------------------------判断符合自动消息条件的订单结束---------------------------------");*/
                }
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
    public SynchronizeSelectAutoMessageTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.SYNCHRONIZE_SELECT_AUTO_MESSAGE_TASK_RUN;
    }

    @Override
    public Integer crTimeMinu() {
        return 30;
    }

    @Override
    public void setMark(String x) {

    }

    @Override
    public String getMark() {
        return null;
    }
}
