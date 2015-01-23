package com.base.utils.threadpool;

import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.DateUtils;
import com.base.utils.exception.MyUncaughtExceptionHandler;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleother.domain.SCBaseVO;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by wula on 2014/8/6.
 * 线程池
 */
public class TaskPool {
    static Logger logger = Logger.getLogger(TaskPool.class);
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
        ThreadPoolExecutor t=threadPoolTaskExecutor.getThreadPoolExecutor();
        t.setThreadFactory(new HandlerThreadFactory());
    }
    /**用于定时任务的post请求线程池*/
    public static ThreadPoolTaskExecutor threadPoolTaskExecutor2;
    static {
        threadPoolTaskExecutor2 = (ThreadPoolTaskExecutor)ApplicationContextUtil.getBean("postTaskExecutor2",ThreadPoolTaskExecutor.class);
        ThreadPoolExecutor t=threadPoolTaskExecutor2.getThreadPoolExecutor();
        t.setThreadFactory(new HandlerThreadFactory());
    }
    /**用于定时任务的线程池*/
    public static ThreadPoolTaskExecutor scheduledThreadPoolTaskExecutor;
    static {
        scheduledThreadPoolTaskExecutor = (ThreadPoolTaskExecutor)ApplicationContextUtil.getBean("scheduleTaskExecutor",ThreadPoolTaskExecutor.class);
        //scheduledThreadPoolTaskExecutor.setThreadFactory(new HandlerThreadFactory());
        ThreadPoolExecutor t=scheduledThreadPoolTaskExecutor.getThreadPoolExecutor();
        t.setThreadFactory(new HandlerThreadFactory());


    }
    /**用于内部定时任务的线程池*/
    public static ThreadPoolTaskExecutor otherScheduledThreadPoolTaskExecutor;
    static {
        otherScheduledThreadPoolTaskExecutor = (ThreadPoolTaskExecutor)ApplicationContextUtil.getBean("otherScheduledThreadPoolTaskExecutor",ThreadPoolTaskExecutor.class);
        ThreadPoolExecutor t=otherScheduledThreadPoolTaskExecutor.getThreadPoolExecutor();
        t.setThreadFactory(new HandlerThreadFactory());
    }

    /**需要执行的任务队列一般是不用调用api的定时任务队列*/
    public final static BlockingQueue<SCBaseVO> SCBaseQueue = new ArrayBlockingQueue<SCBaseVO>(60);

    /**有些执行需要争夺资源的任务，做成一个队列*/
    public final static BlockingQueue<TradingOrderGetOrders> togos=new ArrayBlockingQueue<TradingOrderGetOrders>(600);
    public final static String[] togosBS=new String[1];
    static {
        togosBS[0]="0";
    }



    /**用于判断线程是否还在运行的时候，判断是否超时*/
    public static Map<String, Date> threadRunTime = new HashMap<String, Date>();
    /**判断指定名字的线程是否还在运行*/
    public static boolean threadIsAliveByName(String tname){
        int n = Thread.activeCount();
        Thread[] threads = new Thread[n];
        Thread.enumerate(threads);
        boolean b = false;
        for (int i = 0; i < threads.length; i++) {
            Thread thread = threads[i];
            if (thread!=null && tname.equals(thread.getName()) && thread.isAlive()) {
                String x=thread.getState().toString();//指定线程的状态
                if("RUNNABLE".equalsIgnoreCase(x)){
                    return true;
                }else if ("BLOCKED".equalsIgnoreCase(x)){
                    Date d = threadRunTime.get(tname);
                    if(d!=null){
                        int c= DateUtils.minuteBetween(d,new Date());
                        if(c>180){
                            logger.error(tname+"线程处于BLOCKED状态"+"已经超过规定时间，强制终止");
                            threadRunTime.remove(tname);
                            thread.stop(new RuntimeException("==oooooo=="));
                            return true;
                        }else {
                            logger.error(tname+"线程处于BLOCKED状态"+"时间还未超过180分钟");
                            return true;
                        }
                    }
                    logger.error(tname+"线程阻塞！=========");
                    return true;
                }else if("TIMED_WAITING".equalsIgnoreCase(x)){
                    logger.error(tname+"线程定时等待！=========");
                    return true;
                }
                else if("WAITING".equalsIgnoreCase(x)){
                    logger.error(tname+"线程处于waiting状态"+"当前状态为"+x);
                    /**检查该任务是否在执行记录的Map中*/
                   Date d = threadRunTime.get(tname);
                    if(d!=null){
                        int c= DateUtils.minuteBetween(d,new Date());
                        if(c>180){
                            logger.error(tname+"线程处于waiting状态"+"已经超过规定时间，强制终止");
                            threadRunTime.remove(tname);
                            thread.stop(new RuntimeException("==oooooo=="));
                            return true;
                        }else {
                            logger.error(tname+"线程处于waiting状态"+"时间还未超过180分钟");
                            return true;
                        }
                    }
                    threadRunTime.remove(tname);
                    logger.error(tname+"线程处于waiting状态"+"但是已经执行完毕！强制终止");
                    thread.stop(new RuntimeException("==oooooo=="));
                    return true;
                }
                else {
                    b=false;
                    logger.error(tname+"另起线程....."+"当前状态为"+x);
                }


            }
        }
        threadRunTime.put(tname,new Date());
        return b;
    }

    /**统计指定名称的线程有几个*/
    public static int countThreadNumByName(String tn){
        int n = Thread.activeCount();
        Thread[] threads = new Thread[n];
        Thread.enumerate(threads);
        int nn=0;
        for (int i = 0; i < threads.length; i++) {
            Thread thread = threads[i];
            if (thread != null && tn.startsWith(thread.getName())) {
                nn++;
            }
        }
        return nn;
    }



}


/**用来替代ThreadFactory*/
class HandlerThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        //System.out.println("created " + t + " ID:" + t.getId());
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        //System.out.println("eh=" + t.getUncaughtExceptionHandler());
        return t;
    }
}


