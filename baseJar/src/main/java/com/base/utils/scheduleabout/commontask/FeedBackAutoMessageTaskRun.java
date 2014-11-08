package com.base.utils.scheduleabout.commontask;

import com.base.database.sitemessage.model.PublicSitemessage;
import com.base.database.trading.model.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.BindAccountAPI;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.threadpool.TaskPool;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageService;
import com.sitemessage.service.SiteMessageStatic;
import com.trading.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/29.
 * 在线商品每晚执行，定时任务
 */
public class FeedBackAutoMessageTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(FeedBackAutoMessageTaskRun.class);

    //自动发送付款消息
    public void sendAutoMessage(List<TradingFeedBackDetail> details) throws Exception{
        //--------------自动发送消息-------------------------
        ITradingFeedBackDetail iTradingFeedBackDetail=(ITradingFeedBackDetail) ApplicationContextUtil.getBean(ITradingFeedBackDetail.class);
        for(TradingFeedBackDetail detail:details) {
            ITradingOrderAddMemberMessageAAQToPartner iTradingOrderAddMemberMessageAAQToPartner = (ITradingOrderAddMemberMessageAAQToPartner) ApplicationContextUtil.getBean(ITradingOrderAddMemberMessageAAQToPartner.class);
            ITradingOrderGetOrders iTradingOrderGetOrders = (ITradingOrderGetOrders) ApplicationContextUtil.getBean(ITradingOrderGetOrders.class);
            ITradingListingData iTradingListingData = (ITradingListingData) ApplicationContextUtil.getBean(ITradingListingData.class);
            List<TradingOrderGetOrders> orders = iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(detail.getOrderlineitemid());
            IUsercontrollerEbayAccount iUsercontrollerEbayAccount = (IUsercontrollerEbayAccount) ApplicationContextUtil.getBean(IUsercontrollerEbayAccount.class);
            UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();//开发者帐号id
            d.setApiSiteid("0");
            d.setApiCallName(APINameStatic.AddMemberMessageAAQToPartner);
            Map<String, String> messageMap = new HashMap<String, String>();
            Date sendTime = detail.getSenttime();
            if (orders != null && orders.size() > 0) {
                UsercontrollerEbayAccount ebay = iUsercontrollerEbayAccount.selectByEbayAccount(orders.get(0).getSelleruserid());
                String token = ebay.getEbayToken();
                if ("Negative".equals(detail.getCommenttype())) {
                    messageMap = autoSendMessage(orders.get(0), token, d, "收到买家的负评");
                } else if ("Neutral".equals(detail.getCommenttype())) {
                    messageMap = autoSendMessage(orders.get(0), token, d, "收到买家的中评");
                } else if ("Positive".equals(detail.getCommenttype())) {
                    messageMap = autoSendMessage(orders.get(0), token, d, "收到买家的正评");
                }
                if ("false".equals(messageMap.get("flag"))) {
                    if("自动消息设置的时间没到到".equals(messageMap.get("message"))){
                        TempStoreDataSupport.removeData("task_"+getScheduledType());
                        return;
                    }else {
                        SiteMessageService siteMessageService = (SiteMessageService) ApplicationContextUtil.getBean(SiteMessageService.class);
                        List<PublicSitemessage> list=siteMessageService.selectPublicSitemessageByMessage("auto_message_task_run_FAIL",detail.getCommentinguser()+detail.getItemid());
                        if(list!=null&&list.size()>0){
                            continue;
                        }else{
                            TaskMessageVO taskMessageVO = new TaskMessageVO();
                            taskMessageVO.setMessageType(SiteMessageStatic.FEED_BACK_AUTO_MESSAGE_TASK_RUN + "_FAIL");
                            taskMessageVO.setMessageTitle("定时发送获取评价后的自动消息失败!");
                            taskMessageVO.setMessageContext(messageMap.get("message"));
                            taskMessageVO.setMessageTo(ebay.getUserId());
                            taskMessageVO.setMessageFrom("system");
                            taskMessageVO.setOrderAndSeller(detail.getCommentinguser()+detail.getItemid());
                            siteMessageService.addSiteMessage(taskMessageVO);
                        }
                        TempStoreDataSupport.removeData("task_"+getScheduledType());
                        return;
                    }
                }
                if ("true".equals(messageMap.get("flag"))) {
                    TradingOrderAddMemberMessageAAQToPartner message = new TradingOrderAddMemberMessageAAQToPartner();
                    message.setBody(messageMap.get("body"));
                    message.setSender(orders.get(0).getSelleruserid());
                    message.setItemid(orders.get(0).getItemid());
                    message.setRecipientid(detail.getCommentinguser());
                    message.setSubject(messageMap.get("subject"));
                    message.setTransactionid(detail.getTransactionid());
                    message.setCreateUser(orders.get(0).getCreateUser());
                    message.setMessagetype(3);
                    message.setReplied("true");
                    detail.setAutomessageflag(1);
                    iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message);
                }
                iTradingFeedBackDetail.saveFeedBackDetail(details);
            }else{
                SiteMessageService siteMessageService = (SiteMessageService) ApplicationContextUtil.getBean(SiteMessageService.class);
                List<PublicSitemessage> list=siteMessageService.selectPublicSitemessageByMessage("auto_message_task_run_FAIL",detail.getCommentinguser()+detail.getItemid());
                if(list!=null&&list.size()>0){
                    continue;
                }else{
                    TaskMessageVO taskMessageVO = new TaskMessageVO();
                    taskMessageVO.setMessageType(SiteMessageStatic.FEED_BACK_AUTO_MESSAGE_TASK_RUN + "_FAIL");
                    taskMessageVO.setMessageTitle("定时发送获取评价后的自动消息失败!");
                    taskMessageVO.setMessageContext("订单不存在,请先同步订单");
                    taskMessageVO.setMessageFrom("system");
                    taskMessageVO.setOrderAndSeller(detail.getCommentinguser()+detail.getItemid());
                    siteMessageService.addSiteMessage(taskMessageVO);
                }
                TempStoreDataSupport.removeData("task_"+getScheduledType());
                return;
            }
        }
    }
    private Map<String,String> autoSendMessage(TradingOrderGetOrders order,String token,UsercontrollerDevAccountExtend d,String type) throws Exception{
        CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
        Map<String,String> messageMap=new HashMap<String, String>();
        ITradingAutoMessage iTradingAutoMessage = (ITradingAutoMessage) ApplicationContextUtil.getBean(ITradingAutoMessage.class);
        ITradingMessageTemplate iTradingMessageTemplate = (ITradingMessageTemplate) ApplicationContextUtil.getBean(ITradingMessageTemplate.class);
        List<TradingAutoMessage> partners=iTradingAutoMessage.selectAutoMessageByType(type);
        IUsercontrollerEbayAccount iUsercontrollerEbayAccount = (IUsercontrollerEbayAccount) ApplicationContextUtil.getBean(IUsercontrollerEbayAccount.class);
        UsercontrollerEbayAccount ebay=iUsercontrollerEbayAccount.selectByEbayAccount(order.getSelleruserid());
        if(partners!=null&&partners.size()>0){
            for(TradingAutoMessage partner:partners){
                if(partner.getCreateUser()==ebay.getUserId()){
                    List<TradingMessageTemplate> templates=iTradingMessageTemplate.selectMessageTemplatebyId(partner.getMessagetemplateId());
                    Date date=new Date();
                    if(templates!=null&&templates.size()>0){
                        if(date.after(order.getSendmessagetime())){
                            ITradingAutoMessageAttr iTradingAutoMessageAttr=(ITradingAutoMessageAttr)ApplicationContextUtil.getBean(ITradingAutoMessageAttr.class);
                            List<TradingAutoMessageAttr> allOrders=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(partner.getId(),"allOrder");
                            List<TradingAutoMessageAttr> orderItems=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(partner.getId(),"orderItem");
                            List<TradingAutoMessageAttr> countrys=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(partner.getId(),"country");
                            List<TradingAutoMessageAttr> amounts=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(partner.getId(),"amount");
                            List<TradingAutoMessageAttr> services=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(partner.getId(),"service");
                            List<TradingAutoMessageAttr> internationalServices=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(partner.getId(),"internationalService");
                            List<TradingAutoMessageAttr> exceptCountrys=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(partner.getId(),"exceptCountry");
                            if(allOrders==null&&allOrders.size()==0){
                                if(orderItems.size()>0){
                                    Boolean orderItemFlag=false;
                                    for(TradingAutoMessageAttr orderItem:orderItems){
                                        if(order.getSku().equals(orderItem.getValue())){
                                            orderItemFlag=true;
                                        }
                                    }
                                    if(!orderItemFlag){
                                        messageMap.put("flag","false");
                                        messageMap.put("message","该订单("+order.getOrderid()+")SKU("+order.getSku()+")不包含在自动消息中指定商品SKU,请重新设置自动消息"+type);
                                        return messageMap;
                                    }
                                }
                                if(countrys.size()>0){
                                    Boolean countryFlag=false;
                                    for(TradingAutoMessageAttr country:countrys){
                                        TradingDataDictionary cs=DataDictionarySupport.getTradingDataDictionaryByID(country.getDictionaryId());
                                        if(DataDictionarySupport.DATA_DICT_DELTA.equals(cs.getType())){
                                            List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_COUNTRY,cs.getParentId());
                                            for(TradingDataDictionary li:lidata){
                                                if(order.getCountry().equals(li.getValue())){
                                                    countryFlag=true;
                                                }
                                            }
                                        }else if(DataDictionarySupport.DATA_DICT_COUNTRY.equals(cs.getType())){
                                            if(order.getCountry().equals(cs.getValue())){
                                                countryFlag=true;
                                            }
                                        }
                                    }
                                    if(!countryFlag){
                                        messageMap.put("flag","false");
                                        messageMap.put("message","该订单("+order.getOrderid()+")目的地国家("+order.getCountry()+")不包含在自动消息订单目的地中,请重新设置自动消息"+type);
                                        return messageMap;
                                    }
                                }
                                if(amounts.size()>0){
                                    Boolean amountFlag=false;
                                    for(TradingAutoMessageAttr amout:amounts){
                                        if(order.getSelleruserid().equals(amout.getValue())){
                                            amountFlag=true;
                                        }
                                    }
                                    if(!amountFlag){
                                        messageMap.put("flag","false");
                                        messageMap.put("message","订单("+order.getOrderid()+")买家账号("+order.getSelleruserid()+")不包含在自动消息指定的账号中,请重新设置自动消息"+type);
                                        return messageMap;
                                    }
                                }
                                Boolean serviceFlag=false;
                                Boolean internationalServiceFlag=false;
                                if(services.size()>0){
                                    for(TradingAutoMessageAttr service:services){
                                        if(order.getSelectedshippingservice().equals(service.getType())){
                                            serviceFlag=true;
                                        }
                                    }
                                }
                                if(internationalServices.size()>0){
                                    for(TradingAutoMessageAttr internationalService:internationalServices){
                                        if(order.getSelectedshippingservice().equals(internationalService.getType())){
                                            internationalServiceFlag=true;
                                        }
                                    }
                                }
                                if(!serviceFlag&&!internationalServiceFlag){
                                    messageMap.put("flag","false");
                                    messageMap.put("message","订单("+order.getOrderid()+")物流方式("+order.getSelectedshippingservice()+")不包含在自动消息指定物流方式中,请重新设置自动消息"+type);
                                    return messageMap;
                                }
                                if(exceptCountrys.size()>0){
                                    Boolean exceptCountryFlag=false;
                                    for(TradingAutoMessageAttr exceptCountry:exceptCountrys){
                                        TradingDataDictionary cs=DataDictionarySupport.getTradingDataDictionaryByID(exceptCountry.getDictionaryId());
                                        if(DataDictionarySupport.DATA_DICT_DELTA.equals(cs.getType())){
                                            List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_COUNTRY,cs.getParentId());
                                            for(TradingDataDictionary li:lidata){
                                                if(order.getCountry().equals(li.getValue())){
                                                    exceptCountryFlag=true;
                                                }
                                            }
                                        }else if(DataDictionarySupport.DATA_DICT_COUNTRY.equals(cs.getType())){
                                            if(order.getCountry().equals(cs.getValue())){
                                                exceptCountryFlag=true;
                                            }
                                        }
                                    }
                                    if(!exceptCountryFlag){
                                        messageMap.put("flag","false");
                                        messageMap.put("message","该订单("+order.getOrderid()+")目的地国家之外("+order.getCountry()+")不包含在自动消息订单目的地之外中,请重新设置自动消息"+type);
                                        return messageMap;
                                    }
                                }
                            }
                            String body=templates.get(0).getContent();
                            String subject=templates.get(0).getName();
                            d.setApiCallName(APINameStatic.AddMemberMessageAAQToPartner);
                            Map map=new HashMap();
                            messageMap.put("body",body);
                            messageMap.put("subject",subject);
                            map.put("token", token);
                            map.put("subject",subject);
                            map.put("body",body);
                            map.put("itemid",order.getItemid());
                            map.put("buyeruserid",order.getBuyeruserid());
                            String xml = BindAccountAPI.getAddMemberMessageAAQToPartner(map);
                            AddApiTask addApiTask = new AddApiTask();
                            Map<String, String> resMap = addApiTask.exec2(d, xml, commPars.apiUrl);
                            String r1 = resMap.get("stat");
                            String res = resMap.get("message");
                            if ("fail".equalsIgnoreCase(r1)) {
                                messageMap.put("flag","false");
                                messageMap.put("message",res);
                            }
                            String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                            if ("Success".equalsIgnoreCase(ack)) {
                                messageMap.put("flag","true");
                                messageMap.put("message","发送成功");
                            }else{
                                messageMap.put("flag","false");
                                messageMap.put("message","自动消息发送失败！请稍后重试"+order.getOrderid()+order.getSelleruserid());
                            }
                        }else{
                            messageMap.put("flag","false");
                            messageMap.put("message","自动消息设置的时间没到到");
                        }
                    }
                }else{
                    messageMap.put("flag","false");
                    messageMap.put("message","无对应的自动消息,请先创建"+type+"的自动消息");
                }
            }
        }else{
            messageMap.put("flag","false");
            messageMap.put("message","无对应的自动消息,请先创建"+type+"的自动消息");
        }
        return messageMap;
    }
    @Override
    public void run(){
        int i= TaskPool.scheduledThreadPoolTaskExecutor.getActiveCount();
        if(i>30){
            return;
        }
        String isRunging = TempStoreDataSupport.pullData("task_" + getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_" + getScheduledType(), "x");
        ITradingFeedBackDetail iTradingFeedBackDetail=(ITradingFeedBackDetail) ApplicationContextUtil.getBean(ITradingFeedBackDetail.class);
        List<String> types=new ArrayList<String>();
        types.add("Negative");
        types.add("Neutral");
        types.add("Positive");
        List<TradingFeedBackDetail> details=iTradingFeedBackDetail.selectFeedBackDetailByAutoMessageFlag(types);
        if(details.size()>20){
            details=filterLimitList(details);
        }
        try{
            sendAutoMessage(details);
            TempStoreDataSupport.removeData("task_"+getScheduledType());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**只从集合记录取多少条*/
    private List<TradingFeedBackDetail> filterLimitList(List<TradingFeedBackDetail> tlist){
        return filterLimitListFinal(tlist,20);
        /*List<TradingFeedBackDetail> x=new ArrayList<TradingFeedBackDetail>();
        for (int i = 0;i<20;i++){
            x.add(tlist.get(i));
        }
        return x;*/
    }

    public FeedBackAutoMessageTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.FEEDBACK_AUTOM_ESSAGE;
    }
}
