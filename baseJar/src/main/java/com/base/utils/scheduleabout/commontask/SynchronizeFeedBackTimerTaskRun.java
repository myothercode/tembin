package com.base.utils.scheduleabout.commontask;

import com.base.database.sitemessage.model.PublicSitemessage;
import com.base.database.task.model.TaskFeedBack;
import com.base.database.task.model.TaskGetUserCases;
import com.base.database.trading.model.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.MyStringUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.MainTaskStaticParam;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.threadpool.TaskPool;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageService;
import com.sitemessage.service.SiteMessageStatic;
import com.task.service.ITaskFeedBack;
import com.trading.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/29.
 * 定时同步评价，定时任务
 */
public class SynchronizeFeedBackTimerTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SynchronizeFeedBackTimerTaskRun.class);
    String mark="";

    public void synchronizeFeedBack(List<TaskFeedBack> taskFeedBacks){
        SiteMessageService siteMessageService = (SiteMessageService) ApplicationContextUtil.getBean(SiteMessageService.class);
        ITradingFeedBackDetail iTradingFeedBackDetail=(ITradingFeedBackDetail)ApplicationContextUtil.getBean(ITradingFeedBackDetail.class);
        ITradingOrderAddMemberMessageAAQToPartner iTradingOrderAddMemberMessageAAQToPartner=(ITradingOrderAddMemberMessageAAQToPartner)ApplicationContextUtil.getBean(ITradingOrderAddMemberMessageAAQToPartner.class);
        ITradingAutoMessage iTradingAutoMessage=(ITradingAutoMessage)ApplicationContextUtil.getBean(ITradingAutoMessage.class);
        ITaskFeedBack iTaskFeedBack = (ITaskFeedBack) ApplicationContextUtil.getBean(ITaskFeedBack.class);
        IUsercontrollerEbayAccount iUsercontrollerEbayAccount=(IUsercontrollerEbayAccount)ApplicationContextUtil.getBean(IUsercontrollerEbayAccount.class);
        try{
            for(TaskFeedBack taskFeedBack:taskFeedBacks){
                String res = this.cosPostXml(taskFeedBack,1);
                Element el = SamplePaseXml.getApiElement(res, "PaginationResult");
                String pagetStr = SamplePaseXml.getSpecifyElementText(el,"TotalNumberOfPages");
                String ack="";
                try{
                    ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                }catch (Exception e){
                    logger.error("SynchronizeFeedBackTimerTaskRun第56",e);
                    ack="";
                }
                if(ack.equals("Success")||"Warning".equalsIgnoreCase(ack)){
                    if("Warning".equalsIgnoreCase(ack)){
                        List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_feed_back_timer_FAIL", "评价定时任务有警告:" + taskFeedBack.getId());
                        String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                        if(!StringUtils.isNotBlank(errors)){
                            errors = SamplePaseXml.getWarningInformation(res);
                        }
                        if(list1==null||list1.size()==0){

                            TaskMessageVO taskMessageVO = new TaskMessageVO();
                            taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_FEED_BACK_TIMER + "_FAIL");
                            taskMessageVO.setMessageTitle("定时同步评价有警告!");
                            taskMessageVO.setMessageContext("评价调用API有警告:" + errors);
                            taskMessageVO.setMessageTo(taskFeedBack.getUserid());
                            taskMessageVO.setMessageFrom("system");
                            taskMessageVO.setOrderAndSeller("评价定时任务有警告:"+taskFeedBack.getId());
                            siteMessageService.addSiteMessage(taskMessageVO);

                        }
                        logger.error("获取定时评价有警告!" + errors);
                    }
                    int pages = Integer.parseInt(pagetStr);
                    for(int i = pages;i>0;i--){
                        res = this.cosPostXml(taskFeedBack,i);
                        List<TradingFeedBackDetail> litfb = SamplePaseXml.getFeedBackListElement(res);
                        List<TradingFeedBackDetail> linewtfb = new ArrayList<TradingFeedBackDetail>();
                        for(TradingFeedBackDetail feedBackDetail:litfb){
                            List<TradingOrderAddMemberMessageAAQToPartner> addmessages=new ArrayList<TradingOrderAddMemberMessageAAQToPartner>();
                            if(feedBackDetail.getTransactionid()!=null){
                                addmessages=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByTransactionId(feedBackDetail.getTransactionid(),3,null);
                            }else{
                                addmessages=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByItemIdAndSender(feedBackDetail.getItemid(),3,feedBackDetail.getCommentinguser(),taskFeedBack.getEbayname());
                            }
                            List<TradingAutoMessage> partners=new ArrayList<TradingAutoMessage>();
                            TaskMessageVO taskMessageVO = new TaskMessageVO();
                            taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_FEED_BACK_TIMER + "_FAIL");
                            taskMessageVO.setMessageTo(taskFeedBack.getUserid());
                            taskMessageVO.setMessageFrom("system");
                            taskMessageVO.setMessageTitle("定时同步评价,缺少自动消息,请先创建对应的自动消息才能自动发送自动消息!");
                            if("Positive".equals(feedBackDetail.getCommenttype())){
                                partners=iTradingAutoMessage.selectAutoMessageByType("收到买家的正评",taskFeedBack.getUserid());
                            }else if("Neutral".equals(feedBackDetail.getCommenttype())){
                                partners=iTradingAutoMessage.selectAutoMessageByType("收到买家的中评",taskFeedBack.getUserid());
                            }else if("Negative".equals(feedBackDetail.getCommenttype())){
                                partners=iTradingAutoMessage.selectAutoMessageByType("收到买家的负评",taskFeedBack.getUserid());
                            }
                            if(partners.size()==0&&"Positive".equals(feedBackDetail.getCommenttype())){
                                List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_feed_back_timer_FAIL", "评价定时任务:没有(收到买家的正评)自动消息"+taskFeedBack.getEbayname());
                                if(list1==null||list1.size()==0){
                                    taskMessageVO.setMessageContext("没有对应的自动消息,"+taskFeedBack.getEbayname()+"请先创建(收到买家的正评)自动消息");
                                    taskMessageVO.setOrderAndSeller("评价定时任务:没有(收到买家的正评)自动消息"+taskFeedBack.getEbayname());
                                    siteMessageService.addSiteMessage(taskMessageVO);
                                }
                            }else if(partners.size()==0&&"Neutral".equals(feedBackDetail.getCommenttype())){
                                List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_feed_back_timer_FAIL", "评价定时任务:没有(收到买家的中评)自动消息"+taskFeedBack.getEbayname());
                                if(list1==null||list1.size()==0){
                                    taskMessageVO.setMessageContext("没有对应的自动消息,"+taskFeedBack.getEbayname()+"请先创建(收到买家的中评)自动消息");
                                    taskMessageVO.setOrderAndSeller("评价定时任务:没有(收到买家的中评)自动消息"+taskFeedBack.getEbayname());
                                    siteMessageService.addSiteMessage(taskMessageVO);
                                }
                            }else if(partners.size()==0&&"Negative".equals(feedBackDetail.getCommenttype())){
                                List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_feed_back_timer_FAIL", "评价定时任务:没有(收到买家的负评)自动消息"+taskFeedBack.getEbayname());
                                if(list1==null||list1.size()==0){
                                    taskMessageVO.setMessageContext("没有对应的自动消息,"+taskFeedBack.getEbayname()+"请先创建(收到买家的负评)自动消息");
                                    taskMessageVO.setOrderAndSeller("评价定时任务:没有(收到买家的负评)自动消息"+taskFeedBack.getEbayname());
                                    siteMessageService.addSiteMessage(taskMessageVO);
                                }
                            }
                            feedBackDetail=autoMessageSet(partners,taskFeedBack.getUserid(),feedBackDetail);
                            /*for(TradingAutoMessage partner:partners){
                                if(partner.getStartuse()==1){
                                    int day=partner.getDay();
                                    int hour=partner.getHour();
                                    Date date=feedBackDetail.getCommenttime();
                                    Date date1=org.apache.commons.lang.time.DateUtils.addDays(date,day);
                                    Date date2=org.apache.commons.lang.time.DateUtils.addHours(date1,hour);
                                    feedBackDetail.setSenttime(date2);
                                }
                            }*/
                            if(addmessages.size()>0){
                                feedBackDetail.setAutomessageflag(1);
                            }else{
                                feedBackDetail.setAutomessageflag(null);
                            }
                            TradingFeedBackDetail detail=iTradingFeedBackDetail.selectFeedBackDetailByBuyerAndFeedBackId(feedBackDetail.getCommentinguser(), feedBackDetail.getFeedbackid());
                            if(detail!=null){
                                feedBackDetail.setId(detail.getId());
                            }
                            feedBackDetail.setUserId(taskFeedBack.getUserid()+"");
                            feedBackDetail.setEbayAccount(taskFeedBack.getEbayname());
                            feedBackDetail.setUpdatetime(new Date());
                            linewtfb.add(feedBackDetail);
                        }
                        iTradingFeedBackDetail.saveFeedBackDetail(linewtfb);
                    }
                    Integer flag=taskFeedBack.getTokenflag();
                    flag=flag+1;
                    taskFeedBack.setTokenflag(flag);
                    iTaskFeedBack.saveListTaskFeedBack(taskFeedBack);
                }else{
                    List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_feed_back_timer_FAIL", "评价定时任务:" + taskFeedBack.getId());
                    if(list1==null||list1.size()==0){
                        logger.error("定时获取评价参数错误:" + res);
                    }
                }
            }
        }catch (Exception e){
            logger.error("定时同步评价出错:"+e.getMessage());
            //TempStoreDataSupport.removeData("task_"+getScheduledType());
        }

    }
    private TradingFeedBackDetail autoMessageSet(List<TradingAutoMessage> partners,Long userId,TradingFeedBackDetail feedBackDetail){
        ITradingOrderGetOrders iTradingOrderGetOrders=(ITradingOrderGetOrders)ApplicationContextUtil.getBean(ITradingOrderGetOrders.class);
        ITradingAutoMessageAttr iTradingAutoMessageAttr=(ITradingAutoMessageAttr)ApplicationContextUtil.getBean(ITradingAutoMessageAttr.class);
        List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByBuyerAndItemid(feedBackDetail.getItemid(),feedBackDetail.getCommentinguser());
        TradingOrderGetOrders order=null;
        if(orders!=null&&orders.size()>0){
            order=orders.get(0);
        }
        for(TradingAutoMessage partner:partners){
            if(partner==null){
                feedBackDetail.setAutomessageId(0L);
            }
            if (partner.getStartuse() == 1) {
                Boolean autoFlag = false;
                List<TradingAutoMessageAttr> allOrders = iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(partner.getId(), "allOrder");
                if(order!=null){
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
                        Date date2 = sendAutoMessageTime(partner, feedBackDetail);
                        feedBackDetail.setSenttime(date2);
                        feedBackDetail.setAutomessageId(partner.getId());
                        continue;
                    }else{
                        feedBackDetail.setAutomessageId(0L);
                    }
                }else{
                    feedBackDetail.setAutomessageId(0L);
                }
            }else{
                feedBackDetail.setAutomessageId(0L);
            }
        }
        return feedBackDetail;
    }
    private Date sendAutoMessageTime(TradingAutoMessage partner,TradingFeedBackDetail feedBackDetail){
        int day = partner.getDay();
        int hour = partner.getHour();
        Date date = feedBackDetail.getCommenttime();
        Date date1 = org.apache.commons.lang.time.DateUtils.addDays(date, day);
        Date date2 = org.apache.commons.lang.time.DateUtils.addHours(date1, hour);
        return date2;
    }
    public String cosPostXml(TaskFeedBack taskFeedBack,int pageNumber){
        //真实环境
        /*String colStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<GetFeedbackRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                "<RequesterCredentials>" +
                "  <eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**CLSRUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlYCjDJGCqA+dj6x9nY+seQ**FdYBAA**AAMAAA**w2sMbwlQ7TBHWxj9EsVedHQRI3+lonY9MDfiyayQbnFkjEanjL/yMCpS/D2B9xHRzRx+ppxWZkRPgeAKJvNotPLLrVTuEzOl5M7pi6Tw8+pzcmIEsOh7HQO78JlyFlvLc/ruE6/hG0E/HO1UX76YBwxp00N9f1NNUpo5u36D/TYsx5O2jXFTKkCOHwz6RW9vtN6TU39aLm+JQme2+NfFFXnbX8MHzoUiX7Sty0R88ZpX5wLp8ZdgXCEc5zZDQziYB1MSXF9hsmby5wKbxFF+OvW/zKADThk1gprgAgnEOucyoao+cUMHopLlYgMbjnLzdCXP5F9z+fkYTnKF6AEl5eHBpcKQGbPzswnKebRoBVw+bI2I1C/iq+PvBUyndFAexjrvlDQbEKr6qb6AWRVTTfkW2ce6a0ixRuCTq35zEpWpfAqkSKo+X23d/Q4V8R30rDXotOWDZL6o408cMO+UQ17uVA2arA1JNkYfc/AZ0T0z7ze5o/yp93jJPlDgi05Ut4fpCAMZw3X85GxrTlbEtawWgoyUbmMuv4f6QHZLZAerOaJA8DRJkzkzjJJ025bp1HvAECOc4ggdv0cofu4q96shssgNYYZJUPM+q4+0fnGK0pxQTNY9SV6vSaVCVoTZJo6vefW7OiHX2/eLoPKFuUfsKXXEv9OY71gD1xzYg/rpCMAqCTq1dKqqyT1R5fxANnoRX7vwkq+7jkCj2fAfKTnHi9mSuBFsilKLmnsqqWy3IGShMgdxiQwBEk6IWi9C</eBayAuthToken>" +
                "</RequesterCredentials>" +
                "  <UserID>containyou</UserID>" +
                "  <CommentType>"+commentType+"</CommentType>" +
                "  <DetailLevel>ReturnAll</DetailLevel>" +
                "  <FeedbackType>FeedbackReceivedAsSeller</FeedbackType>" +
                "  <Pagination>" +
                "    <EntriesPerPage>100</EntriesPerPage>" +
                "    <PageNumber>"+pageNumber+"</PageNumber>" +
                "  </Pagination>" +
                "</GetFeedbackRequest>";*/
        //测试环境
        String colStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<GetFeedbackRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                "<RequesterCredentials>" +
                "  <eBayAuthToken>"+taskFeedBack.getToken()+"</eBayAuthToken>" +
                "</RequesterCredentials>" +
                "  <UserID>"+taskFeedBack.getEbayname()+"</UserID>" +
                "  <DetailLevel>ReturnAll</DetailLevel>" +
                "  <FeedbackType>"+taskFeedBack.getCommenttype()+"</FeedbackType>" +
                "  <Pagination>" +
                "    <EntriesPerPage>100</EntriesPerPage>" +
                "    <PageNumber>"+pageNumber+"</PageNumber>" +
                "  </Pagination>" +
                "</GetFeedbackRequest>";
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        d.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
        d.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
        d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
        d.setApiCompatibilityLevel("881");
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.GetFeedbackRequest);
        AddApiTask addApiTask = new AddApiTask();
        Map<String, String> resMap= addApiTask.exec2(d, colStr, "https://api.ebay.com/ws/api.dll");
        String res=resMap.get("message");
        return res;
    }
    @Override
    public void run(){
        int i= TaskPool.scheduledThreadPoolTaskExecutor.getActiveCount();
        if(i>30){
            return;
        }

        /*String isRunging = TempStoreDataSupport.pullData("task_"+getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_" + getScheduledType(), "x");*/
        List<TaskFeedBack> list=null;
        if (MainTaskStaticParam.CATCH_FEEDBACK_QUEUE.isEmpty()) {
            if (!"0".equalsIgnoreCase(this.mark)) {
                return;
            }
            ITaskFeedBack iTaskFeedBack = (ITaskFeedBack) ApplicationContextUtil.getBean(ITaskFeedBack.class);
            list=iTaskFeedBack.selectTaskFeedBackByFlagIsFalseOrderBysaveTime();
            if (list==null || list.isEmpty()){return;}

            if(MainTaskStaticParam.CATCH_FEEDBACK_QUEUE.isEmpty()){
                for (TaskFeedBack t : list){
                    try {
                        if(MainTaskStaticParam.CATCH_FEEDBACK_QUEUE.contains(t)){continue;}
                        MainTaskStaticParam.CATCH_FEEDBACK_QUEUE.put(t);
                    } catch (Exception e) {logger.error("放入case队列出错",e);continue;}
                }
            }

        }

        TaskFeedBack o=null;
        try {
            Iterator<TaskFeedBack> iterator=MainTaskStaticParam.CATCH_FEEDBACK_QUEUE.iterator();
            while (iterator.hasNext()){
                if (MainTaskStaticParam.CATCH_FEEDBACK_QUEUE.isEmpty()){break;}
                TaskFeedBack oo=MainTaskStaticParam.CATCH_FEEDBACK_QUEUE.take();
                if (oo==null){continue;}

                Boolean b= TaskPool.threadIsAliveByName("thread_" + getScheduledType()+"_"+oo.getId());
                if (b){logger.error(getScheduledType()+oo.getId()+"===之前的帐号任务还未结束取下一个===");continue;}
                o=oo;
                break;
            }
        } catch (Exception e) {}

        if(o==null){
            return;
        }
        Thread.currentThread().setName("thread_" + getScheduledType() + "_" + o.getId());
        List<TaskFeedBack> feedBacks1=new ArrayList<TaskFeedBack>();
        feedBacks1.add(o);
        synchronizeFeedBack(feedBacks1);
        TaskPool.threadRunTime.remove("thread_" + getScheduledType()+"_"+o.getId());
        Thread.currentThread().setName("_thread_" + getScheduledType()+"_"+o.getId()+ MyStringUtil.getRandomStringAndNum(5));



        /*ITaskFeedBack iTaskFeedBack = (ITaskFeedBack) ApplicationContextUtil.getBean(ITaskFeedBack.class);
        List<TaskFeedBack> feedBacks=iTaskFeedBack.selectTaskFeedBackByFlagIsFalseOrderBysaveTime();
        if(feedBacks!=null&&feedBacks.size()>0){
            List<TaskFeedBack> feedBacks1=new ArrayList<TaskFeedBack>();
            feedBacks1.add(feedBacks.get(0));
            synchronizeFeedBack(feedBacks1);
        }*/
        //TempStoreDataSupport.removeData("task_"+getScheduledType());
    }

    /**只从集合记录取多少条*/
    private List<TaskFeedBack> filterLimitList(List<TaskFeedBack> tlist){

        return filterLimitListFinal(tlist,2);

        /*List<TaskFeedBack> x=new ArrayList<TaskFeedBack>();
        for (int i = 0;i<10;i++){
            x.add(tlist.get(i));
        }
        return x;*/
    }

    public SynchronizeFeedBackTimerTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.SYNCHRONIZE_FEED_BACK_TIMER;
    }

    @Override
    public Integer crTimeMinu() {
        return MainTaskStaticParam.SOME_CRTIMEMINU.get(MainTask.SYNCHRONIZE_FEED_BACK_TIMER);
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
