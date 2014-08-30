package com.base.utils.threadpool;

import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
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

    public Map<String,String> exec(final UsercontrollerDevAccountExtend d,String xml,String url){
        final Map<String,String> map=new HashMap();

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
        TaskPool.threadPoolTaskExecutor.submit(task);
        String res= null;
        try {
            res = task.get(120, TimeUnit.SECONDS);
        } catch (Exception e) {
            map.put("stat","fail");
            map.put("message",e.getMessage()+"调用"+d.getApiCallName()+"失败");
            return map;
        }

        map.put("stat","success");
        map.put("message",res);
        map.put("activeCount",String.valueOf(TaskPool.threadPoolTaskExecutor.getActiveCount()) ) ;
        return map;

    }
}
