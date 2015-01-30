package com.base.utils.scheduleabout.commontask;

import com.base.database.sitemessage.model.PublicSitemessage;
import com.base.database.task.model.TaskGetMessages;
import com.base.database.trading.model.TradingMessageGetmymessage;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.BindAccountAPI;
import com.base.sampleapixml.GetMyMessageAPI;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
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
import com.task.service.ITaskGetMessages;
import com.trading.service.ITradingMessageGetmymessage;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/29.
 * 定时同步message，定时任务
 */
public class SynchronizeGetMessagesTimerTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SynchronizeGetMessagesTimerTaskRun.class);

    public void synchronizeOrders(List<TaskGetMessages> TaskGetMessages){
        ITaskGetMessages iTaskGetMessages = (ITaskGetMessages) ApplicationContextUtil.getBean(ITaskGetMessages.class);
        ITradingMessageGetmymessage iTradingMessageGetmymessage=(ITradingMessageGetmymessage) ApplicationContextUtil.getBean(ITradingMessageGetmymessage.class);
        CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
        SiteMessageService siteMessageService = (SiteMessageService) ApplicationContextUtil.getBean(SiteMessageService.class);
        UserInfoService userInfoService=(UserInfoService)ApplicationContextUtil.getBean(UserInfoService.class);
        for(TaskGetMessages taskGetMessages:TaskGetMessages){
            UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();//开发者帐号id
            d.setApiSiteid("0");
            //真实环境
     /*       UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
            d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
            d.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
            d.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
            d.setApiCompatibilityLevel("881");
            d.setApiSiteid("0");*/


            Map map=new HashMap();
            d.setApiCallName(APINameStatic.GetMyMessages);
            map.put("token", taskGetMessages.getToken());
            map.put("detail", "ReturnHeaders");
            map.put("startTime", taskGetMessages.getFromtime());
            map.put("endTime", taskGetMessages.getEndtime());
            String xml = BindAccountAPI.getGetMyMessages(map);//获取接受消息
            AddApiTask addApiTask = new AddApiTask();
            Map<String, String> resMap = addApiTask.exec2(d, xml,commPars.apiUrl);
            //Map<String, String> resMap = addApiTask.exec2(d, xml,"https://api.ebay.com/ws/api.dll");
            String r1 = resMap.get("stat");
            String res = resMap.get("message");
            if ("fail".equalsIgnoreCase(r1)) {
                List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_get_messages_timer_FAIL","Message定时任务:"+taskGetMessages.getId());
                if(list1==null||list1.size()==0){
                    TaskMessageVO taskMessageVO = new TaskMessageVO();
                    taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_GET_MESSAGES_TIMER + "_FAIL");
                    taskMessageVO.setMessageTitle("定时同步Message失败!");
                    taskMessageVO.setMessageContext("Message调用API失败:" + res);
                    taskMessageVO.setMessageTo(taskGetMessages.getUserid());
                    taskMessageVO.setMessageFrom("system");
                    taskMessageVO.setOrderAndSeller("Message定时任务:"+taskGetMessages.getId());
                    siteMessageService.addSiteMessage(taskMessageVO);

                }
                logger.error("调用Message API失败:"+res);
            }
            String ack = null;
            try {
                try{
                    ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                }catch (Exception e){
                    logger.error("SynchronizeGetMessagesTimerTaskRun第91",e);
                    ack="";
                }
                if ("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)) {
                    if("Warning".equalsIgnoreCase(ack)){
                        List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_get_messages_timer_FAIL","Message定时任务有警告:"+taskGetMessages.getId());
                        String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                        if(!StringUtils.isNotBlank(errors)){
                            errors = SamplePaseXml.getWarningInformation(res);
                        }
                        if(list1==null||list1.size()==0){
                            TaskMessageVO taskMessageVO = new TaskMessageVO();
                            taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_GET_MESSAGES_TIMER + "_FAIL");
                            taskMessageVO.setMessageTitle("定时同步Message有警告!");
                            taskMessageVO.setMessageContext("Message调用API有警告:" + errors);
                            taskMessageVO.setMessageTo(taskGetMessages.getUserid());
                            taskMessageVO.setMessageFrom("system");
                            taskMessageVO.setOrderAndSeller("Message定时任务有警告:"+taskGetMessages.getId());
                            siteMessageService.addSiteMessage(taskMessageVO);

                        }
                        logger.error("获取定时Message有警告!" + errors);
                    }
                    List<Element> messages = GetMyMessageAPI.getMessages(res);
                    for(Element message:messages){
                        TradingMessageGetmymessage ms= GetMyMessageAPI.addDatabase(message, taskGetMessages.getUserid(), taskGetMessages.getEbayid());//保存到数据库
                        d.setApiSiteid("0");
                        //真实环境
                /*     d=new UsercontrollerDevAccountExtend();
                    d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
                    d.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
                    d.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
                    d.setApiCompatibilityLevel("881");
                    d.setApiSiteid("0");*/
                        d.setApiCallName(APINameStatic.GetMyMessages);
                        Map parms=new HashMap();
                        parms.put("messageId", ms.getMessageid());
                        parms.put("ebayId",ms.getEbayAccountId());
                        parms.put("devAccount",d);
                        //测试环境
                        parms.put("url",commPars.apiUrl);
                        //真实环境
                        //parms.put("url","https://api.ebay.com/ws/api.dll");

                        parms.put("userInfoService",userInfoService);
                        String content=GetMyMessageAPI.getContent(parms);
                        if(!StringUtils.isNotBlank(content)){
                            content=null;
                        }
                        ms.setTextHtml(StringEscapeUtils.escapeXml(content));
                        List<TradingMessageGetmymessage> getmymessages=iTradingMessageGetmymessage.selectMessageGetmymessageByMessageId(ms.getMessageid(),ms.getSender());
                        if(getmymessages.size()>0){
                            ms.setId(getmymessages.get(0).getId());
                            if("true".equals(ms.getReplied())){
                                ms.setRead("true");
                            }else{
                                ms.setRead(getmymessages.get(0).getRead());
                            }
                        }
                        ms.setCreateUser(taskGetMessages.getUserid());
                        ms.setUpdatetime(new Date());
                        iTradingMessageGetmymessage.saveMessageGetmymessage(ms);
                    }

                } else {
                    List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_get_messages_timer_FAIL", "Message定时任务:" + taskGetMessages.getId());
                    if(list1==null||list1.size()==0){
                        logger.error("定时获取Message参数错误:"+res);
                    }
                }
            } catch (Exception e) {
                TempStoreDataSupport.removeData("task_"+getScheduledType());
                logger.error("定时同步消息每两分钟出错:"+e.getMessage(),e);
            }
            Integer flag=taskGetMessages.getTokenflag();
            flag=flag+1;
            taskGetMessages.setTokenflag(flag);
            iTaskGetMessages.saveListTaskGetMessages(taskGetMessages);
        }
    }
    public void synchronizeOrders1(List<TaskGetMessages> TaskGetMessages){
        ITaskGetMessages iTaskGetMessages = (ITaskGetMessages) ApplicationContextUtil.getBean(ITaskGetMessages.class);
        ITradingMessageGetmymessage iTradingMessageGetmymessage=(ITradingMessageGetmymessage) ApplicationContextUtil.getBean(ITradingMessageGetmymessage.class);
        CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
        SiteMessageService siteMessageService = (SiteMessageService) ApplicationContextUtil.getBean(SiteMessageService.class);
        UserInfoService userInfoService=(UserInfoService)ApplicationContextUtil.getBean(UserInfoService.class);
        for(TaskGetMessages taskGetMessages:TaskGetMessages){
            UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();//开发者帐号id
            d.setApiSiteid("0");
            //真实环境
         /*   UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
            d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
            d.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
            d.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
            d.setApiCompatibilityLevel("881");
            d.setApiSiteid("0");*/


            Map map=new HashMap();
            d.setApiCallName(APINameStatic.GetMyMessages);
            map.put("token", taskGetMessages.getToken());
            map.put("detail", "ReturnHeaders");
            map.put("startTime", taskGetMessages.getFromtime());
            map.put("endTime", taskGetMessages.getEndtime());
            String xml = BindAccountAPI.getGetMyMessages1(map);//获取接受消息
            AddApiTask addApiTask = new AddApiTask();
            Map<String, String> resMap = addApiTask.exec2(d, xml,commPars.apiUrl);
            //Map<String, String> resMap = addApiTask.exec2(d, xml,"https://api.ebay.com/ws/api.dll");
            String r1 = resMap.get("stat");
            String res = resMap.get("message");
            if ("fail".equalsIgnoreCase(r1)) {
                List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_get_messages_timer_FAIL","Message定时任务:"+taskGetMessages.getId());
                if(list1==null||list1.size()==0){
                    TaskMessageVO taskMessageVO = new TaskMessageVO();
                    taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_GET_MESSAGES_TIMER + "_FAIL");
                    taskMessageVO.setMessageTitle("定时同步Message失败!");
                    taskMessageVO.setMessageContext("Message调用API失败:" + res);
                    taskMessageVO.setMessageTo(taskGetMessages.getUserid());
                    taskMessageVO.setMessageFrom("system");
                    taskMessageVO.setOrderAndSeller("Message定时任务:"+taskGetMessages.getId());
                    siteMessageService.addSiteMessage(taskMessageVO);

                }
                logger.error("调用Message API失败:"+res);
            }
            String ack = null;
            try {
                try{
                    ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                }catch (Exception e){
                    logger.error("SynchronizeGetMessagesTimerTaskRun第91",e);
                    ack="";
                }
                if ("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)) {
                    if("Warning".equalsIgnoreCase(ack)){
                        List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_get_messages_timer_FAIL","Message定时任务有警告:"+taskGetMessages.getId());
                        String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                        if(!StringUtils.isNotBlank(errors)){
                            errors = SamplePaseXml.getWarningInformation(res);
                        }
                        if(list1==null||list1.size()==0){
                            TaskMessageVO taskMessageVO = new TaskMessageVO();
                            taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_GET_MESSAGES_TIMER + "_FAIL");
                            taskMessageVO.setMessageTitle("定时同步Message有警告!");
                            taskMessageVO.setMessageContext("Message调用API有警告:" + errors);
                            taskMessageVO.setMessageTo(taskGetMessages.getUserid());
                            taskMessageVO.setMessageFrom("system");
                            taskMessageVO.setOrderAndSeller("Message定时任务有警告:"+taskGetMessages.getId());
                            siteMessageService.addSiteMessage(taskMessageVO);

                        }
                        logger.error("获取定时Message有警告!" + errors);
                    }
                    List<Element> messages = GetMyMessageAPI.getMessages(res);
                    for(Element message:messages){
                        TradingMessageGetmymessage ms= GetMyMessageAPI.addDatabase(message, taskGetMessages.getUserid(), taskGetMessages.getEbayid());//保存到数据库
                        d.setApiSiteid("0");
                        //真实环境
             /*        d=new UsercontrollerDevAccountExtend();
                    d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
                    d.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
                    d.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
                    d.setApiCompatibilityLevel("881");
                    d.setApiSiteid("0");*/
                        d.setApiCallName(APINameStatic.GetMyMessages);
                        Map parms=new HashMap();
                        parms.put("messageId", ms.getMessageid());
                        parms.put("ebayId",ms.getEbayAccountId());
                        parms.put("devAccount",d);
                        //测试环境
                        parms.put("url",commPars.apiUrl);
                        //真实环境
                        //parms.put("url","https://api.ebay.com/ws/api.dll");

                        parms.put("userInfoService",userInfoService);
                        String content=GetMyMessageAPI.getContent1(parms);
                        if(!StringUtils.isNotBlank(content)){
                            content=null;
                        }
                        ms.setTextHtml(StringEscapeUtils.escapeXml(content));
                        List<TradingMessageGetmymessage> getmymessages=iTradingMessageGetmymessage.selectMessageGetmymessageByMessageId(ms.getMessageid(),ms.getSender());
                        if(getmymessages.size()>0){
                            ms.setId(getmymessages.get(0).getId());
                            if("true".equals(ms.getReplied())){
                                ms.setRead("true");
                            }else{
                                ms.setRead(getmymessages.get(0).getRead());
                            }
                        }
                        ms.setCreateUser(taskGetMessages.getUserid());
                        ms.setUpdatetime(new Date());
                        iTradingMessageGetmymessage.saveMessageGetmymessage(ms);
                    }

                } else {
                    List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_get_messages_timer_FAIL", "Message定时任务:" + taskGetMessages.getId());
                    if(list1==null||list1.size()==0){
                        logger.error("定时获取Message参数错误:"+res);
                    }
                }
            } catch (Exception e) {
                TempStoreDataSupport.removeData("task_"+getScheduledType());
                logger.error("定时同步消息每两分钟出错:"+e.getMessage(),e);
            }
            Integer flag=taskGetMessages.getTokenflag();
            flag=flag+1;
            taskGetMessages.setTokenflag(flag);
            iTaskGetMessages.saveListTaskGetMessages(taskGetMessages);
        }
    }
    @Override
    public void run(){
        int i= TaskPool.scheduledThreadPoolTaskExecutor.getActiveCount();
        if(i>30){
            return;
        }
      /*  String isRunging = TempStoreDataSupport.pullData("task_"+getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_" + getScheduledType(), "x");*/
        //----------------------------------
        List<TaskGetMessages> list=null;
        if (MainTaskStaticParam.CATCH_EBAYMESSAGE_QUEUE.isEmpty()){
            ITaskGetMessages iTaskGetMessages = (ITaskGetMessages) ApplicationContextUtil.getBean(ITaskGetMessages.class);
            list=iTaskGetMessages.selectTaskGetMessagesByFlagIsFalseOrderBysaveTime();
            if (list==null || list.isEmpty()){return;}
        }
        if(MainTaskStaticParam.CATCH_EBAYMESSAGE_QUEUE.isEmpty()){
            for (TaskGetMessages t : list){
                try {
                    if(MainTaskStaticParam.CATCH_EBAYMESSAGE_QUEUE.contains(t)){continue;}
                    MainTaskStaticParam.CATCH_EBAYMESSAGE_QUEUE.put(t);
                } catch (Exception e) {logger.error("放入message队列出错",e);continue;}
            }
        }
        TaskGetMessages o=null;
        try {
            Iterator<TaskGetMessages> iterator=MainTaskStaticParam.CATCH_EBAYMESSAGE_QUEUE.iterator();
            while (iterator.hasNext()){
                if (MainTaskStaticParam.CATCH_EBAYMESSAGE_QUEUE.isEmpty()){break;}
                TaskGetMessages oo=MainTaskStaticParam.CATCH_EBAYMESSAGE_QUEUE.take();
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
        //logger.error(getScheduledType() +o.getId() + "===任务开始===");
        Thread.currentThread().setName("thread_" + getScheduledType() + "_" + o.getId());
        List<TaskGetMessages> list1=new ArrayList<TaskGetMessages>();
        list1.add(o);

        synchronizeOrders(list1);
        //synchronizeOrders1(list1);
        TaskPool.threadRunTime.remove("thread_" + getScheduledType()+"_"+o.getId());
        Thread.currentThread().setName("thread_" + getScheduledType()+"_"+o.getId()+ MyStringUtil.getRandomStringAndNum(5));
        //logger.error(getScheduledType()+o.getId() + "===任务结束===");
        //----------------------
        /*if(list!=null&&list.size()>0){
            List<TaskGetMessages> list1=new ArrayList<TaskGetMessages>();
            list1.add(list.get(0));
            synchronizeOrders(list1);
        }

        TempStoreDataSupport.removeData("task_"+getScheduledType());*/
    }

    /**只从集合记录取多少条*/
    private List<TaskGetMessages> filterLimitList(List<TaskGetMessages> tlist){

        return filterLimitListFinal(tlist,2);

        /*List<TaskGetMessages> x=new ArrayList<TaskGetMessages>();
        for (int i = 0;i<2;i++){
            x.add(tlist.get(i));
        }
        return x;*/
    }

    public SynchronizeGetMessagesTimerTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.SYNCHRONIZE_GET_MESSAGES_TIMER;
    }

    @Override
    public Integer crTimeMinu() {
        return MainTaskStaticParam.SOME_CRTIMEMINU.get(getScheduledType());
    }

    @Override
    public void setMark(String x) {

    }

    @Override
    public String getMark() {
        return null;
    }
}
