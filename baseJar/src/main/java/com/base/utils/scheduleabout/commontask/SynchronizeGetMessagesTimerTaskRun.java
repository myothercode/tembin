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
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.threadpool.TaskPool;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageService;
import com.sitemessage.service.SiteMessageStatic;
import com.task.service.ITaskGetMessages;
import com.trading.service.ITradingMessageGetmymessage;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/29.
 * 在线商品每晚执行，定时任务
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
            /*UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
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
            String r1 = resMap.get("stat");
            String res = resMap.get("message");
            if ("fail".equalsIgnoreCase(r1)) {
                List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_get_messages_timer_FAIL","Message定时任务:"+taskGetMessages.getId());
                if(list1!=null&&list1.size()>0){
                    return;
                }
                TaskMessageVO taskMessageVO = new TaskMessageVO();
                taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_GET_MESSAGES_TIMER + "_FAIL");
                taskMessageVO.setMessageTitle("定时同步Message失败!");
                taskMessageVO.setMessageContext("Message调用API失败:" + res);
                taskMessageVO.setMessageTo(taskGetMessages.getUserid());
                taskMessageVO.setMessageFrom("system");
                taskMessageVO.setOrderAndSeller("Message定时任务:"+taskGetMessages.getId());
                siteMessageService.addSiteMessage(taskMessageVO);
                return;
            }
            String ack = null;
            try {
                ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                if ("Success".equalsIgnoreCase(ack)) {
                    List<Element> messages = GetMyMessageAPI.getMessages(res);
                    for(Element message:messages){
                        TradingMessageGetmymessage ms= GetMyMessageAPI.addDatabase(message, taskGetMessages.getUserid(), taskGetMessages.getEbayid());//保存到数据库
                        d.setApiSiteid("0");
                        //真实环境
                   /* UsercontrollerDevAccountExtend dev=new UsercontrollerDevAccountExtend();
                    dev.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
                    dev.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
                    dev.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
                    dev.setApiCompatibilityLevel("881");
                    dev.setApiSiteid("0");*/
                        d.setApiCallName(APINameStatic.GetMyMessages);
                        Map parms=new HashMap();
                        parms.put("messageId", ms.getMessageid());
                        parms.put("ebayId",ms.getEbayAccountId());
                        parms.put("devAccount",d);
                        //测试环境
                        parms.put("url",commPars.apiUrl);
                        //真实环境
               /* parms.put("url","https://api.ebay.com/ws/api.dll");*/

                        parms.put("userInfoService",userInfoService);
                        String content=GetMyMessageAPI.getContent(parms);
                        ms.setTextHtml(content);
                        List<TradingMessageGetmymessage> getmymessages=iTradingMessageGetmymessage.selectMessageGetmymessageByMessageId(ms.getMessageid());
                        if(getmymessages.size()>0){
                            ms.setId(getmymessages.get(0).getId());
                        }
                        ms.setCreateUser(taskGetMessages.getUserid());
                        iTradingMessageGetmymessage.saveMessageGetmymessage(ms);
                    }

                } else {
                    List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("synchronize_get_messages_timer_FAIL", "Message定时任务:" + taskGetMessages.getId());
                    if(list1!=null&&list1.size()>0){
                        return;
                    }
                    TaskMessageVO taskMessageVO = new TaskMessageVO();
                    taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_GET_MESSAGES_TIMER + "_FAIL");
                    taskMessageVO.setMessageTitle("定时同步Message失败!");
                    taskMessageVO.setMessageContext("Message调用API失败:" + res);
                    taskMessageVO.setMessageTo(taskGetMessages.getUserid());
                    taskMessageVO.setMessageFrom("system");
                    taskMessageVO.setOrderAndSeller("Message定时任务:"+taskGetMessages.getId());
                    siteMessageService.addSiteMessage(taskMessageVO);
                }
                taskGetMessages.setSavetime(null);
                taskGetMessages.setTokenflag(1);
                iTaskGetMessages.saveListTaskGetMessages(taskGetMessages);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

        ITaskGetMessages iTaskGetMessages = (ITaskGetMessages) ApplicationContextUtil.getBean(ITaskGetMessages.class);
        List<TaskGetMessages> list=iTaskGetMessages.selectTaskGetMessagesByFlagIsFalseOrderBysaveTime();
        if(list.size()>2){
            list=filterLimitList(list);
        }
        synchronizeOrders(list);
        TempStoreDataSupport.removeData("task_"+getScheduledType());
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
}
