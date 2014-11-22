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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/29.
 * 在线商品每晚执行，定时任务
 */
public class AutoMessageTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(AutoMessageTaskRun.class);

    //自动发送付款消息
    public void sendAutoMessage(List<TradingOrderGetOrders> orders) throws Exception{
        //--------------自动发送消息-------------------------
        for(TradingOrderGetOrders order:orders){
            ITradingOrderAddMemberMessageAAQToPartner iTradingOrderAddMemberMessageAAQToPartner = (ITradingOrderAddMemberMessageAAQToPartner) ApplicationContextUtil.getBean(ITradingOrderAddMemberMessageAAQToPartner.class);
            ITradingOrderGetOrders iTradingOrderGetOrders= (ITradingOrderGetOrders) ApplicationContextUtil.getBean(ITradingOrderGetOrders.class);
            IUsercontrollerEbayAccount iUsercontrollerEbayAccount = (IUsercontrollerEbayAccount) ApplicationContextUtil.getBean(IUsercontrollerEbayAccount.class);
            UsercontrollerEbayAccount ebay=iUsercontrollerEbayAccount.selectByEbayAccount(order.getSelleruserid());
            String token=ebay.getEbayToken();
            if(StringUtils.isNotBlank(token)){
                UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();//开发者帐号id
                d.setApiSiteid("0");
                d.setApiCallName(APINameStatic.AddMemberMessageAAQToPartner);
                Map<String,String> messageMap=autoSendMessage(order,token,d,"收到买家付款");
                if("false".equals(messageMap.get("flag"))){
                    if("自动消息设置的时间没到到".equals(messageMap.get("message"))){
                        TempStoreDataSupport.removeData("task_"+getScheduledType());
                        return;
                    }else {
                        SiteMessageService siteMessageService = (SiteMessageService) ApplicationContextUtil.getBean(SiteMessageService.class);
                        List<PublicSitemessage> list=siteMessageService.selectPublicSitemessageByMessage("auto_message_task_run_FAIL",order.getOrderid()+order.getSelleruserid());
                        if(list!=null&&list.size()>0){
                            continue;
                        }else{
                            TaskMessageVO taskMessageVO = new TaskMessageVO();
                            taskMessageVO.setMessageType(SiteMessageStatic.AUTO_MESSAGE_TASK_RUN + "_FAIL");
                            taskMessageVO.setMessageTitle("定时发送付款或发货后的自动消息失败!");
                            taskMessageVO.setMessageContext(messageMap.get("message"));
                            taskMessageVO.setMessageTo(ebay.getUserId());
                            taskMessageVO.setMessageFrom("system");
                            taskMessageVO.setOrderAndSeller(order.getOrderid()+order.getSelleruserid());
                            siteMessageService.addSiteMessage(taskMessageVO);
                        }
                        TempStoreDataSupport.removeData("task_"+getScheduledType());
                        return;
                    }
                }
                if("true".equals(messageMap.get("flag"))){
                    TradingOrderAddMemberMessageAAQToPartner message=new TradingOrderAddMemberMessageAAQToPartner();
                    message.setBody(messageMap.get("body"));
                    message.setSender(order.getSelleruserid());
                    message.setItemid(order.getItemid());
                    message.setRecipientid(order.getBuyeruserid());
                    message.setSubject(messageMap.get("subject"));
                    message.setTransactionid(order.getTransactionid());
                    message.setCreateUser(ebay.getUserId());
                    message.setMessagetype(2);
                    message.setMessageflag(1);
                    message.setReplied("true");
                    order.setPaypalflag(null);
                    order.setShippedflag(null);
                    iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message);
                    iTradingOrderGetOrders.saveOrderGetOrders(order);
                }
            }
        }
    }
    //自动发送发货消息
    public void sendAutoMessage1(List<TradingOrderGetOrders> orders) throws Exception{
        //--------------自动发送消息-------------------------
        for(TradingOrderGetOrders order:orders){
            ITradingOrderAddMemberMessageAAQToPartner iTradingOrderAddMemberMessageAAQToPartner = (ITradingOrderAddMemberMessageAAQToPartner) ApplicationContextUtil.getBean(ITradingOrderAddMemberMessageAAQToPartner.class);
            ITradingOrderGetOrders iTradingOrderGetOrders= (ITradingOrderGetOrders) ApplicationContextUtil.getBean(ITradingOrderGetOrders.class);
            IUsercontrollerEbayAccount iUsercontrollerEbayAccount = (IUsercontrollerEbayAccount) ApplicationContextUtil.getBean(IUsercontrollerEbayAccount.class);
            UsercontrollerEbayAccount ebay=iUsercontrollerEbayAccount.selectByEbayAccount(order.getSelleruserid());
            String token=ebay.getEbayToken();
            if(StringUtils.isNotBlank(token)){
                List<TradingOrderAddMemberMessageAAQToPartner> addmessages=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByTransactionId(order.getTransactionid(),2,order.getSelleruserid());
                UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();//开发者帐号id
                d.setApiSiteid("0");
                d.setApiCallName(APINameStatic.AddMemberMessageAAQToPartner);
                Map<String,String> messageMap=autoSendMessage(order,token,d,"标记已发货");
                if("false".equals(messageMap.get("flag"))){
                    if("自动消息设置的时间没到到".equals(messageMap.get("message"))){
                        TempStoreDataSupport.removeData("task_"+getScheduledType());
                        return;
                    }else {
                        SiteMessageService siteMessageService = (SiteMessageService) ApplicationContextUtil.getBean(SiteMessageService.class);
                        List<PublicSitemessage> list=siteMessageService.selectPublicSitemessageByMessage("auto_message_task_run_FAIL",order.getOrderid()+order.getSelleruserid());
                        if(list!=null&&list.size()>0){
                            continue;
                        }else{
                            TaskMessageVO taskMessageVO = new TaskMessageVO();
                            taskMessageVO.setMessageType(SiteMessageStatic.AUTO_MESSAGE_TASK_RUN + "_FAIL");
                            taskMessageVO.setMessageTitle("定时发送付款或发货后的自动消息失败!");
                            taskMessageVO.setMessageContext(messageMap.get("message"));
                            taskMessageVO.setMessageTo(ebay.getUserId());
                            taskMessageVO.setMessageFrom("system");
                            taskMessageVO.setOrderAndSeller(order.getOrderid()+order.getSelleruserid());
                            siteMessageService.addSiteMessage(taskMessageVO);
                        }
                        TempStoreDataSupport.removeData("task_"+getScheduledType());
                        return;
                    }
                }
                if("true".equals(messageMap.get("flag"))){
                    TradingOrderAddMemberMessageAAQToPartner message=new TradingOrderAddMemberMessageAAQToPartner();
                    message.setBody(messageMap.get("body"));
                    message.setSender(order.getSelleruserid());
                    message.setItemid(order.getItemid());
                    message.setRecipientid(order.getBuyeruserid());
                    message.setSubject(messageMap.get("subject"));
                    message.setTransactionid(order.getTransactionid());
                    message.setCreateUser(order.getCreateUser());
                    message.setMessagetype(2);
                    message.setReplied("true");
                    message.setMessageflag(2);
                    order.setPaypalflag(null);
                    order.setShippedflag(null);
                    iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message);
                    iTradingOrderGetOrders.saveOrderGetOrders(order);
                }
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
                                        messageMap.put("message","该订单("+order.getOrderid()+")SKU("+order.getSku()+")不包含在自动消息中指定商品SKU,"+order.getSelleruserid()+"请重新设置自动消息"+type);
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
                                        messageMap.put("message","该订单("+order.getOrderid()+")目的地国家("+order.getCountry()+")不包含在自动消息订单目的地中,"+order.getSelleruserid()+"请重新设置自动消息"+type);
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
                                        messageMap.put("message","订单("+order.getOrderid()+")买家账号("+order.getSelleruserid()+")不包含在自动消息指定的账号中,"+order.getSelleruserid()+"请重新设置自动消息"+type);
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
                                    messageMap.put("message","订单("+order.getOrderid()+")物流方式("+order.getSelectedshippingservice()+")不包含在自动消息指定物流方式中,"+order.getSelleruserid()+"请重新设置自动消息"+type);
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
                                        messageMap.put("message","该订单("+order.getOrderid()+")目的地国家之外("+order.getCountry()+")不包含在自动消息订单目的地之外中,"+order.getSelleruserid()+"请重新设置自动消息"+type);
                                        return messageMap;
                                    }
                                }
                            }
                            String body=templates.get(0).getContent();
                            body=body.replace("{Buyer_eBay_ID}",order.getBuyeruserid());
                            body=body.replace("{Carrier}",order.getShippingcarrierused());
                            body=body.replace("{eBay_Item#}",order.getItemid());
                            body=body.replace("{eBay_Item_Title}",order.getTitle());
                            body=body.replace("{Payment_Date}",order.getPaidtime()+"");
                            body=body.replace("{Purchase_Quantity}",order.getQuantitypurchased());
                            body=body.replace("{Received_Amount}",order.getAmountpaid());
                            body=body.replace("{Seller_eBay_ID}",order.getSelleruserid());
                            body=body.replace("{Seller_Email}",order.getSelleremail());
                            body=body.replace("{Today}",new Date()+"");
                            body=body.replace("{Track_Code}",order.getShipmenttrackingnumber());
                            String subject=templates.get(0).getName();
                            //--测试环境
                            d.setApiCallName(APINameStatic.AddMemberMessageAAQToPartner);
                            //--真实环境
                          /*  d=new UsercontrollerDevAccountExtend();
                            d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
                            d.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
                            d.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
                            d.setApiCompatibilityLevel("883");
                            d.setApiSiteid("0");
                            d.setApiCallName(APINameStatic.AddMemberMessageAAQToPartner);*/
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
                            //--测试环境
                                Map<String, String> resMap = addApiTask.exec2(d, xml, commPars.apiUrl);
                            //--真实环境
                            /*Map<String, String> resMap = addApiTask.exec2(d, xml,"https://api.ebay.com/ws/api.dll");*/
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
                                messageMap.put("message","自动消息发送失败！请稍后重试,"+order.getOrderid()+order.getSelleruserid());
                            }
                        }else{
                            messageMap.put("flag","false");
                            messageMap.put("message","自动消息设置的时间没到");
                        }
                    }
                }else{
                    messageMap.put("flag","false");
                    messageMap.put("message","无对应的自动消息,"+order.getSelleruserid()+"请先创建"+type+"的自动消息");
                }
            }
        }else{
            messageMap.put("flag","false");
            messageMap.put("message","无对应的自动消息,"+order.getSelleruserid()+"请先创建"+type+"的自动消息");
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
        ITradingOrderGetOrders iTradingOrderGetOrders=(ITradingOrderGetOrders) ApplicationContextUtil.getBean(ITradingOrderGetOrders.class);
        List<TradingOrderGetOrders> paids= iTradingOrderGetOrders.selectOrderGetOrdersBySendPaidMessage();
        List<TradingOrderGetOrders> ships= iTradingOrderGetOrders.selectOrderGetOrdersBySendShipMessage();
        if(paids.size()>10){
            paids=filterLimitList(paids);
        }
        if(ships.size()>10){
            ships=filterLimitList(ships);
        }
        try{
            sendAutoMessage(paids);
            sendAutoMessage1(ships);
            TempStoreDataSupport.removeData("task_"+getScheduledType());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**只从集合记录取多少条*/
    private List<TradingOrderGetOrders> filterLimitList(List<TradingOrderGetOrders> tlist){
        return filterLimitListFinal(tlist,10);
        /*List<TradingOrderGetOrders> x=new ArrayList<TradingOrderGetOrders>();
        for (int i = 0;i<10;i++){
            x.add(tlist.get(i));
        }
        return x;*/
    }

    public AutoMessageTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.AUTO_MESSAGE;
    }

    @Override
    public Integer crTimeMinu() {
        return null;
    }
}
