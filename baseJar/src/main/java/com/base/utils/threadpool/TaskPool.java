package com.base.utils.threadpool;

import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.scheduleother.domain.SCBaseVO;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
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


    /**用于发起普通post请求的线程池*/
    public static ThreadPoolTaskExecutor threadPoolTaskExecutor;
    static {
        threadPoolTaskExecutor = (ThreadPoolTaskExecutor)ApplicationContextUtil.getBean("postTaskExecutor",ThreadPoolTaskExecutor.class);
    }
    /**用于定时任务的post请求线程池*/
    public static ThreadPoolTaskExecutor threadPoolTaskExecutor2;
    static {
        threadPoolTaskExecutor2 = (ThreadPoolTaskExecutor)ApplicationContextUtil.getBean("postTaskExecutor2",ThreadPoolTaskExecutor.class);
    }
    /**用于定时任务的线程池*/
    public static ThreadPoolTaskExecutor scheduledThreadPoolTaskExecutor;
    static {
        scheduledThreadPoolTaskExecutor = (ThreadPoolTaskExecutor)ApplicationContextUtil.getBean("scheduleTaskExecutor",ThreadPoolTaskExecutor.class);
    }
    /**用于内部定时任务的线程池*/
    public static ThreadPoolTaskExecutor otherScheduledThreadPoolTaskExecutor;
    static {
        otherScheduledThreadPoolTaskExecutor = (ThreadPoolTaskExecutor)ApplicationContextUtil.getBean("otherScheduledThreadPoolTaskExecutor",ThreadPoolTaskExecutor.class);
    }

    /**需要执行的任务队列一般是不用调用api的定时任务队列*/
    public final static BlockingQueue<SCBaseVO> SCBaseQueue = new ArrayBlockingQueue<SCBaseVO>(60);


    /**判断指定名字的线程是否还在运行*/
    public static boolean threadIsAliveByName(String tname){
        int n = Thread.activeCount();
        Thread[] threads = new Thread[n];
        Thread.enumerate(threads);
        boolean b = false;
        for (int i = 0; i < threads.length; i++) {
            Thread thread = threads[i];
            System.out.println(thread.getName());
            if (thread.getName().equals(tname) && thread.isAlive()) {
                b = true;
            }
        }
        return b;
    }




}
