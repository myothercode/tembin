package com.base.utils.scheduleabout.commontask;

import com.base.database.task.model.ListingDataTask;
import com.base.database.trading.model.TradingAutoMessage;
import com.base.database.trading.model.TradingMessageTemplate;
import com.base.database.trading.model.TradingOrderAddMemberMessageAAQToPartner;
import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.BindAccountAPI;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.trading.service.ITradingAutoMessage;
import com.trading.service.ITradingMessageTemplate;
import com.trading.service.ITradingOrderAddMemberMessageAAQToPartner;
import com.trading.service.ITradingOrderGetOrders;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/29.
 * 在线商品每晚执行，定时任务
 */
public class AutoMessageTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(AutoMessageTaskRun.class);

    //自动发送消息
    public void sendAutoMessage(List<TradingOrderGetOrders> orders) throws Exception {
        //--------------自动发送消息-------------------------
        for(TradingOrderGetOrders order:orders){
            ITradingOrderAddMemberMessageAAQToPartner iTradingOrderAddMemberMessageAAQToPartner = (ITradingOrderAddMemberMessageAAQToPartner) ApplicationContextUtil.getBean(ITradingOrderAddMemberMessageAAQToPartner.class);
            List<TradingOrderAddMemberMessageAAQToPartner> addmessages=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByTransactionId(order.getTransactionid(),2);
            SystemUserManagerService systemUserManagerService=(SystemUserManagerService) ApplicationContextUtil.getBean(SystemUserManagerService.class);
            String token="";
            Map map=new HashMap();
            List<UsercontrollerEbayAccountExtend> ebays=systemUserManagerService.queryCurrAllEbay(map);
            for(UsercontrollerEbayAccountExtend ebay:ebays){
                if(order.getSelleruserid().equals(ebay.getEbayName())){
                    token=ebay.getEbayToken();
                }
            }
            if(StringUtils.isNotBlank(token)){
                UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();//开发者帐号id
                d.setApiSiteid("0");
                d.setApiCallName(APINameStatic.AddMemberMessageAAQToPartner);
                if(addmessages!=null&&addmessages.size()>0){
                    for(TradingOrderAddMemberMessageAAQToPartner addmessage:addmessages){
                        TradingOrderAddMemberMessageAAQToPartner message=new TradingOrderAddMemberMessageAAQToPartner();
                        String trackingnumber=order.getShipmenttrackingnumber();
                        if(StringUtils.isNotBlank(trackingnumber)){
                            if(addmessage.getMessageflag()<=1){
                                //自动发送发货消息
                                Map<String,String> messageMap=autoSendMessage(order,token,d,"标记已发货");
                                message.setBody(messageMap.get("body"));
                                message.setSender(order.getSelleruserid());
                                message.setItemid(order.getItemid());
                                message.setRecipientid(order.getBuyeruserid());
                                message.setSubject(messageMap.get("subject"));
                                message.setTransactionid(order.getTransactionid());
                                message.setCreateUser(order.getCreateUser());
                                message.setMessagetype(addmessage.getMessagetype());
                                if("false".equals(messageMap.get("flag"))){
                                    message.setMessageflag(addmessage.getMessagetype());
                                    message.setReplied("false");
                                    iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message);
                                    return;
                                }
                                if("true".equals(messageMap.get("flag"))){
                                    message.setMessageflag(2);
                                    message.setReplied("true");
                                    iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message);
                                }
                            }
                        }
                    }
                }else{
                    TradingOrderAddMemberMessageAAQToPartner message=new TradingOrderAddMemberMessageAAQToPartner();
                    String trackingnumber=order.getShipmenttrackingnumber();
                    if("Complete".equals(order.getStatus())&&!StringUtils.isNotBlank(trackingnumber)&&"Completed".equals(order.getOrderstatus())){
                        //付款后发送消息
                        Map<String,String> messageMap=autoSendMessage(order,token,d,"收到买家付款");
                        message.setMessageflag(1);
                        message.setBody(messageMap.get("body"));
                        message.setSender(order.getSelleruserid());
                        message.setItemid(order.getItemid());
                        message.setMessagetype(2);
                        message.setRecipientid(order.getBuyeruserid());
                        message.setSubject(messageMap.get("subject"));
                        message.setTransactionid(order.getTransactionid());
                        message.setCreateUser(order.getCreateUser());
                        if("false".equals(messageMap.get("flag"))){
                            message.setReplied("false");
                            iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message);
                            return;
                        }
                        if("true".equals(messageMap.get("flag"))){
                            message.setReplied("true");
                            iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message);
                        }
                    }
                    if(StringUtils.isNotBlank(trackingnumber)){
                        //发送发货消息
                        Map<String,String> messageMap=autoSendMessage(order,token,d,"标记已发货");
                        message.setMessageflag(2);
                        message.setBody(messageMap.get("body"));
                        message.setSender(order.getSelleruserid());
                        message.setItemid(order.getItemid());
                        message.setMessagetype(2);
                        message.setRecipientid(order.getBuyeruserid());
                        message.setSubject(messageMap.get("subject"));
                        message.setTransactionid(order.getTransactionid());
                        message.setCreateUser(order.getCreateUser());
                        if("false".equals(messageMap.get("flag"))){
                            message.setReplied("false");
                            iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message);
                            return;
                        }
                        if("true".equals(messageMap.get("flag"))){
                            message.setReplied("true");
                            iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message);
                        }
                        //}
                    }
                }
            }
        }

    }
    private Map<String,String> autoSendMessage(TradingOrderGetOrders order,String token,UsercontrollerDevAccountExtend d,String type) throws Exception {
        CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
        Map<String,String> messageMap=new HashMap<String, String>();
        ITradingAutoMessage iTradingAutoMessage = (ITradingAutoMessage) ApplicationContextUtil.getBean(ITradingAutoMessage.class);
        ITradingMessageTemplate iTradingMessageTemplate = (ITradingMessageTemplate) ApplicationContextUtil.getBean(ITradingMessageTemplate.class);
        List<TradingAutoMessage> partners=iTradingAutoMessage.selectAutoMessageByType(type);
        if(partners!=null&&partners.size()>0){
            List<TradingMessageTemplate> templates=iTradingMessageTemplate.selectMessageTemplatebyId(partners.get(0).getMessagetemplateId());
            if(templates!=null&&templates.size()>0){
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
                Map<String, String> resMap = addApiTask.exec(d, xml, commPars.apiUrl);
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
                    messageMap.put("message","获取必要的参数失败！请稍后重试");
                }

            }
        }else{
            messageMap.put("flag","false");
            messageMap.put("message","无对应的自动消息,请先创建");
        }
        return messageMap;
    }
    @Override
    public void run() {
        ITradingOrderGetOrders iTradingOrderGetOrders=(ITradingOrderGetOrders) ApplicationContextUtil.getBean(ITradingOrderGetOrders.class);

       /* IListingDataTask iListingDataTask = (IListingDataTask) ApplicationContextUtil.getBean(IListingDataTask.class);
        List<ListingDataTask> lildt = iListingDataTask.selectByTimerTaskflag();
        if(lildt.size()>2){
            lildt =filterLimitList(lildt);
        }
        String taskFlag="";
        for(ListingDataTask ldt:lildt){
            taskFlag = this.saveListingData(ldt.getEbayaccount(),ldt.getUserid(),ldt.getToken(),ldt.getSite());
            ldt.setTaskFlag(taskFlag);
            ldt.setCreateDate(new Date());
            iListingDataTask.saveListDataTask(ldt);
        }*/
    }

    /**只从集合记录取多少条*/
    private List<ListingDataTask> filterLimitList(List<ListingDataTask> tlist){
        List<ListingDataTask> x=new ArrayList<ListingDataTask>();
        for (int i = 0;i<2;i++){
            x.add(tlist.get(i));
        }
        return x;
    }

    public AutoMessageTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.AUTO_MESSAGE;
    }
}
