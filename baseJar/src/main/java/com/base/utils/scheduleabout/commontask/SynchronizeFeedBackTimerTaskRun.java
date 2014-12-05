package com.base.utils.scheduleabout.commontask;

import com.base.database.sitemessage.model.PublicSitemessage;
import com.base.database.task.model.TaskFeedBack;
import com.base.database.trading.model.TradingAutoMessage;
import com.base.database.trading.model.TradingFeedBackDetail;
import com.base.database.trading.model.TradingOrderAddMemberMessageAAQToPartner;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/29.
 * 在线商品每晚执行，定时任务
 */
public class SynchronizeFeedBackTimerTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SynchronizeFeedBackTimerTaskRun.class);

    public void synchronizeFeedBack(List<TaskFeedBack> taskFeedBacks){
        SiteMessageService siteMessageService = (SiteMessageService) ApplicationContextUtil.getBean(SiteMessageService.class);
        ITradingFeedBackDetail iTradingFeedBackDetail=(ITradingFeedBackDetail)ApplicationContextUtil.getBean(ITradingFeedBackDetail.class);
        ITradingOrderAddMemberMessageAAQToPartner iTradingOrderAddMemberMessageAAQToPartner=(ITradingOrderAddMemberMessageAAQToPartner)ApplicationContextUtil.getBean(ITradingOrderAddMemberMessageAAQToPartner.class);
        ITradingListingData iTradingListingData = (ITradingListingData) ApplicationContextUtil.getBean(ITradingListingData.class);
        ITradingAutoMessage iTradingAutoMessage=(ITradingAutoMessage)ApplicationContextUtil.getBean(ITradingAutoMessage.class);
        ITaskFeedBack iTaskFeedBack = (ITaskFeedBack) ApplicationContextUtil.getBean(ITaskFeedBack.class);
        IUsercontrollerEbayAccount iUsercontrollerEbayAccount=(IUsercontrollerEbayAccount)ApplicationContextUtil.getBean(IUsercontrollerEbayAccount.class);
        try{
            for(TaskFeedBack taskFeedBack:taskFeedBacks){
                taskFeedBack.setTokenflag(1);
                iTaskFeedBack.saveListTaskFeedBack(taskFeedBack);
                String res = this.cosPostXml(taskFeedBack,1);
                Element el = SamplePaseXml.getApiElement(res, "PaginationResult");
                String pagetStr = SamplePaseXml.getSpecifyElementText(el,"TotalNumberOfPages");
                String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                if(ack.equals("Success")){
                    int pages = Integer.parseInt(pagetStr);
                    for(int i = pages;i>0;i--){
                        res = this.cosPostXml(taskFeedBack,i);
                        List<TradingFeedBackDetail> litfb = SamplePaseXml.getFeedBackListElement(res);
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
                                if(list1!=null&&list1.size()>0){
                                    continue;
                                }else{
                                    taskMessageVO.setMessageContext("没有对应的自动消息,"+taskFeedBack.getEbayname()+"请先创建(收到买家的正评)自动消息");
                                    taskMessageVO.setOrderAndSeller("评价定时任务:没有(收到买家的正评)自动消息"+taskFeedBack.getEbayname());
                                    siteMessageService.addSiteMessage(taskMessageVO);
                                }
                            }else if(partners.size()==0&&"Neutral".equals(feedBackDetail.getCommenttype())){
                                List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_feed_back_timer_FAIL", "评价定时任务:没有(收到买家的中评)自动消息"+taskFeedBack.getEbayname());
                                if(list1!=null&&list1.size()>0){
                                    continue;
                                }else{
                                    taskMessageVO.setMessageContext("没有对应的自动消息,"+taskFeedBack.getEbayname()+"请先创建(收到买家的中评)自动消息");
                                    taskMessageVO.setOrderAndSeller("评价定时任务:没有(收到买家的中评)自动消息"+taskFeedBack.getEbayname());
                                    siteMessageService.addSiteMessage(taskMessageVO);
                                }
                            }else if(partners.size()==0&&"Negative".equals(feedBackDetail.getCommenttype())){
                                List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_feed_back_timer_FAIL", "评价定时任务:没有(收到买家的负评)自动消息"+taskFeedBack.getEbayname());
                                if(list1!=null&&list1.size()>0){
                                    continue;
                                }else{
                                    taskMessageVO.setMessageContext("没有对应的自动消息,"+taskFeedBack.getEbayname()+"请先创建(收到买家的负评)自动消息");
                                    taskMessageVO.setOrderAndSeller("评价定时任务:没有(收到买家的负评)自动消息"+taskFeedBack.getEbayname());
                                    siteMessageService.addSiteMessage(taskMessageVO);
                                }
                            }
                            for(TradingAutoMessage partner:partners){
                                if(partner.getStartuse()==1){
                                    int day=partner.getDay();
                                    int hour=partner.getHour();
                                    Date date=feedBackDetail.getCommenttime();
                                    Date date1=org.apache.commons.lang.time.DateUtils.addDays(date,day);
                                    Date date2=org.apache.commons.lang.time.DateUtils.addHours(date1,hour);
                                    feedBackDetail.setSenttime(date2);
                                }
                            }
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
                        }
                        iTradingFeedBackDetail.saveFeedBackDetail(litfb);
                    }
                }else{
                    List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_feed_back_timer_FAIL", "评价定时任务:" + taskFeedBack.getId());
                    if(list1!=null&&list1.size()>0){
                        return;
                    }
                    TaskMessageVO taskMessageVO = new TaskMessageVO();
                    taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_FEED_BACK_TIMER + "_FAIL");
                    taskMessageVO.setMessageTitle("定时同步评价失败!");
                    taskMessageVO.setMessageContext("评价调用API失败:" + res);
                    taskMessageVO.setMessageTo(taskFeedBack.getUserid());
                    taskMessageVO.setMessageFrom("system");
                    taskMessageVO.setOrderAndSeller("评价定时任务:"+taskFeedBack.getId());
                    siteMessageService.addSiteMessage(taskMessageVO);


                    return;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            TempStoreDataSupport.removeData("task_"+getScheduledType());
        }

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
                "  <CommentType>"+taskFeedBack.getCommenttype()+"</CommentType>" +
                "  <DetailLevel>ReturnAll</DetailLevel>" +
                "  <FeedbackType>FeedbackReceivedAsSeller</FeedbackType>" +
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

        String isRunging = TempStoreDataSupport.pullData("task_"+getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_" + getScheduledType(), "x");

        ITaskFeedBack iTaskFeedBack = (ITaskFeedBack) ApplicationContextUtil.getBean(ITaskFeedBack.class);
        List<TaskFeedBack> feedBacks=iTaskFeedBack.selectTaskFeedBackByFlagIsFalseOrderBysaveTime();
        if(feedBacks.size()>2){
            feedBacks=filterLimitList(feedBacks);
        }
        synchronizeFeedBack(feedBacks);
        TempStoreDataSupport.removeData("task_"+getScheduledType());
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
        return null;
    }
}
