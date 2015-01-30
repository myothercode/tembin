package com.base.utils.scheduleabout.commontask;

import com.base.database.trading.model.*;
import com.base.domains.querypojos.OrderGetOrdersQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.BindAccountAPI;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.MyStringUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.MainTaskStaticParam;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskPool;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageService;
import com.trading.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/29.
 * 订单定时发送自动消息，定时任务
 */
public class AutoMessageTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(AutoMessageTaskRun.class);
    String mark;
    //自动发送付款消息
    public void sendAutoMessage(List<TradingOrderGetOrders> orders) throws Exception{
        //--------------自动发送消息-------------------------
        ITradingOrderGetOrdersNoTransaction iTradingOrderGetOrdersNoTransaction=(ITradingOrderGetOrdersNoTransaction)ApplicationContextUtil.getBean(ITradingOrderGetOrdersNoTransaction.class);
        for(TradingOrderGetOrders order:orders){
            ITradingOrderAddMemberMessageAAQToPartner iTradingOrderAddMemberMessageAAQToPartner = (ITradingOrderAddMemberMessageAAQToPartner) ApplicationContextUtil.getBean(ITradingOrderAddMemberMessageAAQToPartner.class);
            ITradingOrderGetOrders iTradingOrderGetOrders= (ITradingOrderGetOrders) ApplicationContextUtil.getBean(ITradingOrderGetOrders.class);
            IUsercontrollerEbayAccount iUsercontrollerEbayAccount = (IUsercontrollerEbayAccount) ApplicationContextUtil.getBean(IUsercontrollerEbayAccount.class);
            ITradingAutoMessageAndOrder iTradingAutoMessageAndOrder= (ITradingAutoMessageAndOrder) ApplicationContextUtil.getBean(ITradingAutoMessageAndOrder.class);
            UsercontrollerEbayAccount ebay=iUsercontrollerEbayAccount.selectByEbayAccount(order.getSelleruserid());
            SiteMessageService siteMessageService = (SiteMessageService) ApplicationContextUtil.getBean(SiteMessageService.class);
            String token=ebay.getEbayToken();
            if(StringUtils.isNotBlank(token)){
                UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();//开发者帐号id
                d.setApiSiteid("0");
                d.setApiCallName(APINameStatic.AddMemberMessageAAQToPartner);
                List<TradingAutoMessageAndOrder> autoMessageAndOrders= iTradingAutoMessageAndOrder.selectAutoMessageAndOrderByAutoOrderId(order.getId());
                if(autoMessageAndOrders!=null&&autoMessageAndOrders.size()>0){
                    for(TradingAutoMessageAndOrder autoMessageAndOrder:autoMessageAndOrders){
                        Map<String,String> messageMap=autoSendMessage(order,autoMessageAndOrder,token,d);
                        if("false".equals(messageMap.get("flag"))){
                            if("自动消息设置的时间没到".equals(messageMap.get("message"))||"没有匹配的自动消息".equals(messageMap.get("message"))){
                                /*logger.error("AutoMessageTaskRun第54:"+messageMap.get("message"));*/
                                /*List<PublicSitemessage> list=siteMessageService.selectPublicSitemessageByMessage("auto_message_task_run_FAIL",order.getOrderid()+order.getSelleruserid());
                                if(list==null||list.size()==0){
                                    TaskMessageVO taskMessageVO = new TaskMessageVO();
                                    taskMessageVO.setMessageType(SiteMessageStatic.AUTO_MESSAGE_TASK_RUN + "_FAIL");
                                    taskMessageVO.setMessageTitle(messageMap.get("message"));
                                    taskMessageVO.setMessageContext(messageMap.get("message"));
                                    taskMessageVO.setMessageTo(ebay.getUserId());
                                    taskMessageVO.setMessageFrom("system");
                                    taskMessageVO.setOrderAndSeller(order.getOrderid()+order.getSelleruserid());
                                    siteMessageService.addSiteMessage(taskMessageVO);
                                }*/
                            }else {
                                logger.error("AutoMessageTaskRun第67:"+messageMap.get("message"));
                            }
                            TradingOrderAddMemberMessageAAQToPartner message=new TradingOrderAddMemberMessageAAQToPartner();
                            message.setBody(messageMap.get("body"));
                            message.setSender(order.getSelleruserid());
                            message.setItemid(order.getItemid());
                            message.setRecipientid(order.getBuyeruserid());
                            message.setSubject(messageMap.get("subject"));
                            message.setTransactionid(order.getTransactionid());
                            message.setCreateUser(order.getCreateUser());
                            message.setMessagetype(2);
                            message.setReplied("false");
                            message.setMessageflag(1);
                            message.setFailereason(messageMap.get("message"));
                            iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message);
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
                            order.setPaypalflag(1);
                            order.setShippedflag(1);
                            iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message);
                            TaskPool.togos.put(order);
                        }
                    }
                }
            }
        }
        if("0".equals(TaskPool.togosBS[0])){
            iTradingOrderGetOrdersNoTransaction.saveOrderGetOrders(null);
        }
    }
    //自动发送发货消息

    public void sendAutoMessage1(List<TradingOrderGetOrders> orders) throws Exception{
        //--------------自动发送消息-------------------------
        ITradingOrderGetOrdersNoTransaction iTradingOrderGetOrdersNoTransaction=(ITradingOrderGetOrdersNoTransaction)ApplicationContextUtil.getBean(ITradingOrderGetOrdersNoTransaction.class);
        for(TradingOrderGetOrders order:orders){
            ITradingOrderAddMemberMessageAAQToPartner iTradingOrderAddMemberMessageAAQToPartner = (ITradingOrderAddMemberMessageAAQToPartner) ApplicationContextUtil.getBean(ITradingOrderAddMemberMessageAAQToPartner.class);
            ITradingOrderGetOrders iTradingOrderGetOrders= (ITradingOrderGetOrders) ApplicationContextUtil.getBean(ITradingOrderGetOrders.class);
            ITradingAutoMessageAndOrder iTradingAutoMessageAndOrder= (ITradingAutoMessageAndOrder) ApplicationContextUtil.getBean(ITradingAutoMessageAndOrder.class);
            IUsercontrollerEbayAccount iUsercontrollerEbayAccount = (IUsercontrollerEbayAccount) ApplicationContextUtil.getBean(IUsercontrollerEbayAccount.class);
            UsercontrollerEbayAccount ebay=iUsercontrollerEbayAccount.selectByEbayAccount(order.getSelleruserid());
            String token=ebay.getEbayToken();
            if(StringUtils.isNotBlank(token)){
                List<TradingOrderAddMemberMessageAAQToPartner> addmessages=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByTransactionId(order.getTransactionid(),2,order.getSelleruserid());
                UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();//开发者帐号id
                d.setApiSiteid("0");
                d.setApiCallName(APINameStatic.AddMemberMessageAAQToPartner);
                List<TradingAutoMessageAndOrder> autoMessageAndOrders= iTradingAutoMessageAndOrder.selectAutoMessageAndOrderByAutoOrderId(order.getId());
                if(autoMessageAndOrders!=null&&autoMessageAndOrders.size()>0){
                    for(TradingAutoMessageAndOrder autoMessageAndOrder:autoMessageAndOrders){
                        Map<String,String> messageMap=autoSendMessage(order,autoMessageAndOrder,token,d);
                        if("false".equals(messageMap.get("flag"))){
                            if("自动消息设置的时间没到".equals(messageMap.get("message"))||"没有匹配的自动消息".equals(messageMap.get("message"))){
                               /* logger.error("AutoMessageTaskRun第110:"+messageMap.get("message"));*/
                                /*SiteMessageService siteMessageService = (SiteMessageService) ApplicationContextUtil.getBean(SiteMessageService.class);
                                List<PublicSitemessage> list=siteMessageService.selectPublicSitemessageByMessage("auto_message_task_run_FAIL",order.getOrderid()+order.getSelleruserid());
                                if(list==null||list.size()==0){
                                    TaskMessageVO taskMessageVO = new TaskMessageVO();
                                    taskMessageVO.setMessageType(SiteMessageStatic.AUTO_MESSAGE_TASK_RUN + "_FAIL");
                                    taskMessageVO.setMessageTitle(messageMap.get("message"));
                                    taskMessageVO.setMessageContext(messageMap.get("message"));
                                    taskMessageVO.setMessageTo(ebay.getUserId());
                                    taskMessageVO.setMessageFrom("system");
                                    taskMessageVO.setOrderAndSeller(order.getOrderid()+order.getSelleruserid());
                                    siteMessageService.addSiteMessage(taskMessageVO);
                                }*/
                            }else {
                                logger.error("AutoMessageTaskRun第124:"+messageMap.get("message"));
                            }
                            TradingOrderAddMemberMessageAAQToPartner message=new TradingOrderAddMemberMessageAAQToPartner();
                            message.setBody(messageMap.get("body"));
                            message.setSender(order.getSelleruserid());
                            message.setItemid(order.getItemid());
                            message.setRecipientid(order.getBuyeruserid());
                            message.setSubject(messageMap.get("subject"));
                            message.setTransactionid(order.getTransactionid());
                            message.setCreateUser(order.getCreateUser());
                            message.setMessagetype(2);
                            message.setReplied("false");
                            message.setMessageflag(2);
                            message.setFailereason(messageMap.get("message"));
                            iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message);
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
                            order.setPaypalflag(1);
                            order.setShippedflag(1);
                            iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message);
                            TaskPool.togos.put(order);
                        }
                    }
                }
            }
        }
        if("0".equals(TaskPool.togosBS[0])){
            iTradingOrderGetOrdersNoTransaction.saveOrderGetOrders(null);
        }
    }
    private Map<String,String> autoSendMessage(TradingOrderGetOrders orderGetOrders,TradingAutoMessageAndOrder autoMessageAndOrder,String token,UsercontrollerDevAccountExtend d) throws Exception{
        Long automessageId=autoMessageAndOrder.getAutomessageId();
        Map<String,String> messageMap=new HashMap<String, String>();
        if(automessageId!=null&&automessageId!=0L){
            Date date=new Date();
            Date date1=autoMessageAndOrder.getSendtime();
            if(date1!=null&&date.after(date1)){
                ITradingAutoMessage iTradingAutoMessage = (ITradingAutoMessage) ApplicationContextUtil.getBean(ITradingAutoMessage.class);
                ITradingMessageTemplate iTradingMessageTemplate = (ITradingMessageTemplate) ApplicationContextUtil.getBean(ITradingMessageTemplate.class);
                List<TradingAutoMessage> autoMessage=iTradingAutoMessage.selectAutoMessageById(automessageId);
                List<TradingMessageTemplate> templates=iTradingMessageTemplate.selectMessageTemplatebyId(autoMessage.get(0).getMessagetemplateId());
                CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
                String body=templates.get(0).getContent();
                if(StringUtils.isNotBlank(orderGetOrders.getBuyeruserid())){
                    body=body.replace("{Buyer_eBay_ID}",orderGetOrders.getBuyeruserid());
                }
                if(StringUtils.isNotBlank(orderGetOrders.getShippingcarrierused())){
                    body=body.replace("{Carrier}",orderGetOrders.getShippingcarrierused());
                }
                if(StringUtils.isNotBlank(orderGetOrders.getItemid())){
                    body=body.replace("{eBay_Item#}",orderGetOrders.getItemid());
                }
                if(StringUtils.isNotBlank(orderGetOrders.getTitle())){
                    body=body.replace("{eBay_Item_Title}",orderGetOrders.getTitle());
                }
                if(orderGetOrders.getPaidtime()!=null){
                    body=body.replace("{Payment_Date}",orderGetOrders.getPaidtime()+"");
                }
                if(StringUtils.isNotBlank(orderGetOrders.getQuantitypurchased())){
                    body=body.replace("{Purchase_Quantity}",orderGetOrders.getQuantitypurchased());
                }
                if(StringUtils.isNotBlank(orderGetOrders.getAmountpaid())){
                    body=body.replace("{Received_Amount}",orderGetOrders.getAmountpaid());
                }
                if(StringUtils.isNotBlank(orderGetOrders.getSelleruserid())){
                    body=body.replace("{Seller_eBay_ID}",orderGetOrders.getSelleruserid());
                }
                if(StringUtils.isNotBlank(orderGetOrders.getSelleremail())){
                    body=body.replace("{Seller_Email}",orderGetOrders.getSelleremail());
                }
                body=body.replace("{Today}",new Date()+"");
                if(StringUtils.isNotBlank(orderGetOrders.getShipmenttrackingnumber())){
                    body=body.replace("{Track_Code}",orderGetOrders.getShipmenttrackingnumber());
                }
                String subject=templates.get(0).getSubject();
                //--测试环境
                d=new UsercontrollerDevAccountExtend();
                d.setApiSiteid("0");
                d.setApiCallName(APINameStatic.AddMemberMessageAAQToPartner);
                //--真实环境
             /*   d=new UsercontrollerDevAccountExtend();
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
                map.put("itemid",orderGetOrders.getItemid());
                map.put("buyeruserid",orderGetOrders.getBuyeruserid());
                String xml = BindAccountAPI.getAddMemberMessageAAQToPartner(map);
                AddApiTask addApiTask = new AddApiTask();
                //--测试环境
                Map<String, String> resMap = addApiTask.exec2(d, xml, commPars.apiUrl);
                //--真实环境
                //Map<String, String> resMap = addApiTask.exec2(d, xml,"https://api.ebay.com/ws/api.dll");
                String r1 = resMap.get("stat");
                String res = resMap.get("message");
                if ("fail".equalsIgnoreCase(r1)) {
                    messageMap.put("flag","false");
                    messageMap.put("message","发送失败第200:"+res);
                }
                String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                if ("Success".equalsIgnoreCase(ack)) {
                    messageMap.put("flag","true");
                    messageMap.put("message","发送成功");
                }else{
                    messageMap.put("flag","false");
                    messageMap.put("message","自动消息发送失败！请稍后重试第208,"+orderGetOrders.getOrderid()+orderGetOrders.getSelleruserid());
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

        int tnumber=TaskPool.countThreadNumByName("thread_" + getScheduledType());
        if(tnumber>10){logger.error("当前执行发送消息线程过多...");return;}
        //--------------
        if(MainTaskStaticParam.CATCH_AUTO_MESSAGE_QUEUE.isEmpty()){
/*            if (!"0".equalsIgnoreCase(this.mark)){
                return;
            }*/
            //logger.error("thread_" + getScheduledType()+"_当前线程编号为0，开始查数据");
            ITradingOrderGetOrders iTradingOrderGetOrders=(ITradingOrderGetOrders) ApplicationContextUtil.getBean(ITradingOrderGetOrders.class);
            //*****************-------------------
            List<OrderGetOrdersQuery> paids1= iTradingOrderGetOrders.selectOrderGetOrdersBySendPaidMessage();
            List<TradingOrderGetOrders> orders=new ArrayList<TradingOrderGetOrders>();
            List<OrderGetOrdersQuery> ships1= iTradingOrderGetOrders.selectOrderGetOrdersBySendShipMessage();
            if(paids1!=null&&paids1.size()>0){
                orders.addAll(paids1);
            }
            if(ships1!=null&&ships1.size()>0){
                orders.addAll(ships1);
            }
            if(orders==null||orders.isEmpty()){
                return;
            }
            //********************
            //todo 记得限制每次获取数据的数量
           /* List<OrderGetOrdersQuery> orders1=iTradingOrderGetOrders.selectOrderGetOrdersByAccountFlag();
            List<TradingOrderGetOrders> orders=new ArrayList<TradingOrderGetOrders>();
            if(orders1!=null&&orders1.size()>0){
                orders.addAll(orders1);
            }
            if (orders==null || orders.isEmpty()){return;}*/

            if(MainTaskStaticParam.CATCH_AUTO_MESSAGE_QUEUE.isEmpty()){
                for (TradingOrderGetOrders order:orders){
                    if(MainTaskStaticParam.CATCH_AUTO_MESSAGE_QUEUE.size()>=2000){break;}
                    try {
                        if (MainTaskStaticParam.CATCH_AUTO_MESSAGE_QUEUE.contains(order)){logger.error("队列中已有"); continue;}
                        Boolean b= TaskPool.threadIsAliveByName("thread_" + getScheduledType() + "_" + order.getId());
                        if (b){logger.error(getScheduledType()+order.getId()+"===之前的发送自动消息任务还未结束不放入===");continue;}
                        MainTaskStaticParam.CATCH_AUTO_MESSAGE_QUEUE.put(order);
                    } catch (Exception e) {logger.error("放入orderAccount队列出错",e);continue;}
                }
            }
        }else {
            //logger.error("线程编号不为0,执行====");
        }

        if( MainTaskStaticParam.CATCH_AUTO_MESSAGE_QUEUE.isEmpty()){return;}
        Iterator<TradingOrderGetOrders> iterator=MainTaskStaticParam.CATCH_AUTO_MESSAGE_QUEUE.iterator();
        int ix=0;
        while (iterator.hasNext()){
            if(MainTaskStaticParam.CATCH_AUTO_MESSAGE_QUEUE.isEmpty()||ix>=20){break;}
            TradingOrderGetOrders oo=null;
            try {
                oo=MainTaskStaticParam.CATCH_AUTO_MESSAGE_QUEUE.take();
                if (oo==null){continue;}
            } catch (Exception e) {continue;}
            String threadName="thread_" + getScheduledType() + "_" + oo.getId();

            Boolean b= TaskPool.threadIsAliveByName(threadName);
            if (b){logger.error(getScheduledType()+oo.getId()+"===之前的发送自动消息任务还未结束取下一个===");continue;}
            Thread.currentThread().setName(threadName);
            Integer paypalflag=oo.getPaypalflag();
            Integer shippedflag=oo.getShippedflag();
            List<TradingOrderGetOrders> paids=new ArrayList<TradingOrderGetOrders>();
            List<TradingOrderGetOrders> ships=new ArrayList<TradingOrderGetOrders>();
            if(paypalflag==1&&shippedflag==0){
                paids.add(oo);
            }
            if(paypalflag==0&&shippedflag==1){
                ships.add(oo);
            }
            try {
                sendAutoMessage(paids);
                sendAutoMessage1(ships);
            } catch (Exception e) {
                logger.error("自动发送消息第364:"+e);
            }
            TaskPool.threadRunTime.remove(threadName);
            Thread.currentThread().setName("_" + threadName + MyStringUtil.getRandomStringAndNum(5));
            //logger.error(getScheduledType()+oo.getId() + "===任务结束===");
            ix++;
        }
        //--------------
        /*String isRunging = TempStoreDataSupport.pullData("task_" + getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_" + getScheduledType(), "x");
        ITradingOrderGetOrders iTradingOrderGetOrders=(ITradingOrderGetOrders) ApplicationContextUtil.getBean(ITradingOrderGetOrders.class);
        List<OrderGetOrdersQuery> paids1= iTradingOrderGetOrders.selectOrderGetOrdersBySendPaidMessage();
        List<TradingOrderGetOrders> paids=new ArrayList<TradingOrderGetOrders>();
        List<OrderGetOrdersQuery> ships1= iTradingOrderGetOrders.selectOrderGetOrdersBySendShipMessage();
        List<TradingOrderGetOrders> ships=new ArrayList<TradingOrderGetOrders>();
        if(paids1!=null&&paids1.size()>0){
            paids.addAll(paids1);
        }
        if(ships1!=null&&ships1.size()>0){
            ships.addAll(ships1);
        }
        try{
            sendAutoMessage(paids);
            sendAutoMessage1(ships);
            TempStoreDataSupport.removeData("task_"+getScheduledType());
        }catch (Exception e){
            TempStoreDataSupport.removeData("task_"+getScheduledType());
            logger.error("订单自动消息发送出错:"+e.getMessage(),e);
        }*/
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

    @Override
    public void setMark(String x) {
        this.mark=x;
    }

    @Override
    public String getMark() {
        return this.mark;
    }
}
