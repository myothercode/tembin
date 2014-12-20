package com.base.utils.threadpool;

import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.xmlutils.CheckResXMLUtil;
import com.sitemessage.service.SiteMessageService;
import com.sitemessage.service.SiteMessageStatic;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.ListenableFutureTask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrtor on 2014/8/7.
 */
public class AddApiTask {
    static Logger logger = Logger.getLogger(AddApiTask.class);

    /**执行调任务，等待返回结果,用于页面操作中需要发起请求的地方*/
    public Map<String,String> exec(final UsercontrollerDevAccountExtend d,String xml,String url){
        final Map<String,String> map=new HashMap();
        if(TaskPool.threadPoolTaskExecutor.getActiveCount()>=30){
            map.put("stat","fail");
            map.put("message","当前队列任务太多，请稍后再试");
        }

        /**根据新逻辑，重新查询开发帐号取得用量最少的*/
        UserInfoService userInfoService = (UserInfoService) ApplicationContextUtil.getBean(UserInfoService.class);
        UsercontrollerDevAccountExtend dt=null;
        if(APINameStatic.FetchToken.equalsIgnoreCase(d.getApiCallName()) ||
                APINameStatic.GetSessionID.equalsIgnoreCase(d.getApiCallName())){

        }else {
            try {
                if (url.indexOf("api.sandbox.ebay.com")>-1){
                    CommAutowiredClass autowiredClass = ApplicationContextUtil.getBean(CommAutowiredClass.class);
                    dt = userInfoService.getDevInfo(Long.valueOf(autowiredClass.snadboxDevID));
                }else {
                    dt = userInfoService.getDevByOrder(new HashMap());
                }

                //Map map1 = new HashMap();
                //map1.put("id",dt.getId());
                //userInfoService.addUseNum(map1);//累计一次调用量

                if(dt!=null){
                    d.setRunname(dt.getRunname());
                    d.setApiAppName(dt.getApiAppName());
                    d.setApiDevName(dt.getApiDevName());
                    d.setApiCertName(dt.getApiCertName());
                    d.setApiCompatibilityLevel(dt.getApiCompatibilityLevel());
                }
            } catch (Exception e) {
                logger.error("获取开发帐号失败!"+xml,e);
            }
        }



        if(APINameStatic.GetSessionID.equalsIgnoreCase(d.getApiCallName())){
                xml = StringUtils.replace(xml,"<RuName>runName</RuName>","<RuName>"+d.getRunname()+"</RuName>");
        }
        ListenableFutureTask<String> task = new ListenableFutureTask<String>(new ApiCallable(d,xml,url));
        /**添加成功和失败后的处理*/
        task.addCallback(new ListenableFutureCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFailure(Throwable t) {
                logger.error("运行异常！获取"+d.getApiCallName()+"失败!"+t.getMessage(),t);
                map.put("stat","fail");
                map.put("message",t.getMessage());
            }
        });
        TaskPool.threadPoolTaskExecutor.submit(task);
        String res= null;
        try {
            res = task.get(500, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("执行线程任务失败！",e);
            map.put("stat","fail");
            map.put("message",e.getMessage()+"调用"+d.getApiCallName()+"运行异常！失败");
            map.put("activeCount",String.valueOf(TaskPool.threadPoolTaskExecutor.getActiveCount()) ) ;
            return map;
        }

        if(StringUtils.isEmpty(res)){
            map.put("stat","fail");
            map.put("message","调用"+d.getApiCallName()+"失败;没有获取到返回参数");
            map.put("activeCount",String.valueOf(TaskPool.threadPoolTaskExecutor.getActiveCount()) ) ;
            return map;
        }
        if("apiLimit".equalsIgnoreCase(res)){
            map.put("stat","fail");
            map.put("message","apiLimit");
            return map;
        }

        map.put("stat","success");
        map.put("message",res);
        map.put("activeCount",String.valueOf(TaskPool.threadPoolTaskExecutor.getActiveCount()) ) ;
        return map;

    }
    /**执行调任务，等待返回结果,用于定时任务中需要发起请求的地方*/
    public Map<String,String> exec2(final UsercontrollerDevAccountExtend d,String xml,String url){
        final Map<String,String> map=new HashMap();
        if(TaskPool.threadPoolTaskExecutor2.getActiveCount()>=30){
            map.put("stat","fail");
            map.put("message","当前队列任务太多，请稍后再试");
        }

        /**根据新逻辑，重新查询开发帐号取得用量最少的*/
        UserInfoService userInfoService = (UserInfoService) ApplicationContextUtil.getBean(UserInfoService.class);
        UsercontrollerDevAccountExtend dt=null;
        if(APINameStatic.FetchToken.equalsIgnoreCase(d.getApiCallName()) ||
                APINameStatic.GetSessionID.equalsIgnoreCase(d.getApiCallName())){

        }else {
            try {
                dt = userInfoService.getDevByOrder(new HashMap());
                Map map1 = new HashMap();
                map1.put("id",dt.getId());
                //userInfoService.addUseNum(map1);//累计一次调用量

                if(dt!=null){
                    d.setRunname(dt.getRunname());
                    d.setApiAppName(dt.getApiAppName());
                    d.setApiDevName(dt.getApiDevName());
                    d.setApiCertName(dt.getApiCertName());
                    d.setApiCompatibilityLevel(dt.getApiCompatibilityLevel());
                }
            } catch (Exception e) {
                logger.error("获取开发帐号失败!"+xml,e);
            }
        }



        if(APINameStatic.GetSessionID.equalsIgnoreCase(d.getApiCallName())){
            xml = StringUtils.replace(xml,"<RuName>runName</RuName>","<RuName>"+d.getRunname()+"</RuName>");
        }
        ListenableFutureTask<String> task = new ListenableFutureTask<String>(new ApiCallable(d,xml,url));
        /**添加成功和失败后的处理*/
        task.addCallback(new ListenableFutureCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFailure(Throwable t) {
                logger.error("获取"+d.getApiCallName()+"失败"+t.getMessage(),t);
                map.put("stat","fail");
                map.put("message",t.getMessage());
            }
        });
        TaskPool.threadPoolTaskExecutor2.submit(task);
        String res= null;
        try {
            res = task.get(500, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("执行线程任务失败2！",e);
            map.put("stat","fail");
            map.put("message",e.getMessage()+"调用"+d.getApiCallName()+"失败2");
            map.put("activeCount",String.valueOf(TaskPool.threadPoolTaskExecutor2.getActiveCount()) ) ;
            return map;
        }

        if(StringUtils.isEmpty(res)){
            map.put("stat","fail");
            map.put("message","调用"+d.getApiCallName()+"失败;没有获取到返回参数2");
            map.put("activeCount",String.valueOf(TaskPool.threadPoolTaskExecutor2.getActiveCount()) ) ;
            return map;
        }
        if("apiLimit".equalsIgnoreCase(res)){
            map.put("stat","fail");
            map.put("message","apiLimit");
            return map;
        }

        map.put("stat","success");
        map.put("message",res);
        map.put("activeCount",String.valueOf(TaskPool.threadPoolTaskExecutor2.getActiveCount()) ) ;
        return map;

    }


    /**执行任务，延迟返回结果*/
    public void execDelayReturn(final UsercontrollerDevAccountExtend d,String xml,String url,TaskMessageVO taskMessageVO){
        /**如果队列已经大于50，那么就不再添加*/
        if(TaskPool.threadPoolTaskExecutor.getActiveCount()>=50){
            SiteMessageService siteMessageService= (SiteMessageService) ApplicationContextUtil.getBean(SiteMessageService.class);
            taskMessageVO.setMessageTitle(taskMessageVO.getMessageTitle()+"执行失败！");
            taskMessageVO.setMessageContext(taskMessageVO.getMessageContext() + ",失败原因:队列太多，请稍后再试!");
            taskMessageVO.setMessageType(taskMessageVO.getMessageType()+"_FAIL");
            siteMessageService.addSiteMessage(taskMessageVO);
            return;
        }

        /**根据新逻辑，重新查询开发帐号取得用量最少的*/
        UserInfoService userInfoService = (UserInfoService) ApplicationContextUtil.getBean(UserInfoService.class);
        UsercontrollerDevAccountExtend dt=null;
        try {
            dt = userInfoService.getDevByOrder(new HashMap());
            Map map1 = new HashMap();
            map1.put("id",dt.getId());
            //userInfoService.addUseNum(map1);//累计一次调用量
        } catch (Exception e) {
            logger.error("获取开发帐号失败!"+xml,e);
            return;
        }

        if(dt!=null){
            d.setRunname(dt.getRunname());
            d.setApiAppName(dt.getApiAppName());
            d.setApiDevName(dt.getApiDevName());
            d.setApiCertName(dt.getApiCertName());
            d.setApiCompatibilityLevel(dt.getApiCompatibilityLevel());
        }
        TaskPool.threadPoolTaskExecutor.execute(new ApiRunable(d,xml,url,taskMessageVO));
    }


}
