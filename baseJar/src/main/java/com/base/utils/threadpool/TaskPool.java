package com.base.utils.threadpool;

import com.base.utils.applicationcontext.ApplicationContextUtil;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by wula on 2014/8/6.
 * 线程池
 */
public class TaskPool {
    /**刊登任务
    public static final String LISTINGITEM_TASK="listingitem_task";

    public static Map task_name_mapping=new HashMap();
    static {
        task_name_mapping.put(LISTINGITEM_TASK,"刊登任务");
    }*/


    /**用于发起post请求的线程池*/
    public static ThreadPoolTaskExecutor threadPoolTaskExecutor;
    static {
        threadPoolTaskExecutor = (ThreadPoolTaskExecutor)ApplicationContextUtil.getBean("postTaskExecutor",ThreadPoolTaskExecutor.class);
    }
    /**用于定时任务的线程池*/
    public static ThreadPoolTaskExecutor scheduledThreadPoolTaskExecutor;
    static {
        scheduledThreadPoolTaskExecutor = (ThreadPoolTaskExecutor)ApplicationContextUtil.getBean("scheduleTaskExecutor",ThreadPoolTaskExecutor.class);
    }
}
