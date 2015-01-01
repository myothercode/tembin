package com.base.utils.scheduleabout.commontask;

import com.base.database.sitemessage.model.PublicSitemessage;
import com.base.database.trading.model.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.BindAccountAPI;
import com.base.utils.applicationcontext.ApplicationContextUtil;
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
 * 评价发送自动消息，定时任务
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
                    messageMap = autoSendMessage(detail, token, d);
                } else if ("Neutral".equals(detail.getCommenttype())) {
                    messageMap = autoSendMessage(detail, token, d);
                } else if ("Positive".equals(detail.getCommenttype())) {
                    messageMap = autoSendMessage(detail, token, d);
                }
                if ("false".equals(messageMap.get("flag"))) {
                    if("自动消息设置的时间没到".equals(messageMap.get("message"))||"没有匹配的自动消息".equals(messageMap.get("message"))){
                        logger.error("AutoMessageTaskRun第110:"+messageMap.get("message"));
                        SiteMessageService siteMessageService = (SiteMessageService) ApplicationContextUtil.getBean(SiteMessageService.class);
                        List<PublicSitemessage> list=siteMessageService.selectPublicSitemessageByMessage("auto_message_task_run_FAIL",detail.getOrderlineitemid()+detail.getCommentinguser());
                        if(list==null||list.size()==0){
                            TaskMessageVO taskMessageVO = new TaskMessageVO();
                            taskMessageVO.setMessageType(SiteMessageStatic.AUTO_MESSAGE_TASK_RUN + "_FAIL");
                            taskMessageVO.setMessageTitle(messageMap.get("message"));
                            taskMessageVO.setMessageContext(messageMap.get("message"));
                            taskMessageVO.setMessageTo(ebay.getUserId());
                            taskMessageVO.setMessageFrom("system");
                            taskMessageVO.setOrderAndSeller(detail.getOrderlineitemid()+detail.getCommentinguser());
                            siteMessageService.addSiteMessage(taskMessageVO);
                        }
                    }else {
                        logger.error(messageMap.get("FeedBackAutoMessageTaskRun第75:"+messageMap.get("message")));
                    }
                    TradingOrderAddMemberMessageAAQToPartner message = new TradingOrderAddMemberMessageAAQToPartner();
                    message.setBody(messageMap.get("body"));
                    message.setItemid(detail.getItemid());
                    message.setRecipientid(detail.getCommentinguser());
                    message.setSubject(messageMap.get("subject"));
                    message.setTransactionid(detail.getTransactionid());
                    if(orders.size()>0&&orders!=null){
                        message.setSender(orders.get(0).getSelleruserid());
                        message.setCreateUser(orders.get(0).getCreateUser());
                    }
                    message.setMessagetype(3);
                    message.setReplied("false");
                    message.setFailereason(messageMap.get("message"));
                    detail.setAutomessageflag(1);
                    iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message);
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
                logger.error("FeedBackAutoMessageTaskRun第109:该评价没有订单,请先同步订单");
            }
        }
    }
    private Map<String,String> autoSendMessage(TradingFeedBackDetail feedBackDetail,String token,UsercontrollerDevAccountExtend d) throws Exception{
        Map<String,String> messageMap=new HashMap<String, String>();
        Long automessageId=feedBackDetail.getAutomessageId();
        if(automessageId!=null&&automessageId!=0L){
            Date date=new Date();
            Date date1=feedBackDetail.getSenttime();
            if(date1!=null&&date.after(date1)){
                ITradingAutoMessage iTradingAutoMessage = (ITradingAutoMessage) ApplicationContextUtil.getBean(ITradingAutoMessage.class);
                ITradingMessageTemplate iTradingMessageTemplate = (ITradingMessageTemplate) ApplicationContextUtil.getBean(ITradingMessageTemplate.class);
                List<TradingAutoMessage> autoMessage=iTradingAutoMessage.selectAutoMessageById(automessageId);
                List<TradingMessageTemplate> templates=iTradingMessageTemplate.selectMessageTemplatebyId(autoMessage.get(0).getMessagetemplateId());
                CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
                ITradingOrderGetOrders iTradingOrderGetOrders=(ITradingOrderGetOrders)ApplicationContextUtil.getBean(ITradingOrderGetOrders.class);
                List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByBuyerAndItemid(feedBackDetail.getItemid(), feedBackDetail.getCommentinguser());
                String body=templates.get(0).getContent();
                body=body.replace("{Buyer_eBay_ID}",feedBackDetail.getCommentinguser());
                body=body.replace("{eBay_Item#}",feedBackDetail.getItemid());
                body=body.replace("{eBay_Item_Title}",feedBackDetail.getItemtitle());
                body=body.replace("{Received_Amount}",feedBackDetail.getItemprice()+"");
                body=body.replace("{Today}",new Date()+"");
                if(orders!=null&&orders.size()>0){
                    TradingOrderGetOrders order=orders.get(0);
                    body=body.replace("{Payment_Date}",order.getPaidtime()+"");
                    body=body.replace("{Purchase_Quantity}",order.getQuantitypurchased());
                    body=body.replace("{Seller_eBay_ID}",order.getSelleruserid());
                    body=body.replace("{Carrier}",order.getShippingcarrierused());
                    body=body.replace("{Seller_Email}",order.getSelleremail());
                    body=body.replace("{Track_Code}",order.getShipmenttrackingnumber());
                }
                String subject=templates.get(0).getName();
                //--测试环境
                d.setApiCallName(APINameStatic.AddMemberMessageAAQToPartner);
                //--真实环境
                /*d=new UsercontrollerDevAccountExtend();
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
                map.put("itemid",feedBackDetail.getItemid());
                map.put("buyeruserid",feedBackDetail.getCommentinguser());
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
                    messageMap.put("message","发送失败第169:"+res);
                }
                String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                if ("Success".equalsIgnoreCase(ack)) {
                    messageMap.put("flag","true");
                    messageMap.put("message","发送成功");
                }else{
                    messageMap.put("flag","false");
                    messageMap.put("message","自动消息发送失败！请稍后重试第177,"+feedBackDetail.getOrderlineitemid()+feedBackDetail.getCommentinguser());
                }
            }else{
                messageMap.put("flag","false");
                messageMap.put("message","自动消息设置的时间没到");
            }
        }else{
            messageMap.put("flag","false");
            messageMap.put("message","没有匹配的自动消息");
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
            logger.error("评价自动消息发送出错:"+e.getMessage(),e);
            TempStoreDataSupport.removeData("task_"+getScheduledType());
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

    @Override
    public Integer crTimeMinu() {
        return null;
    }
}
