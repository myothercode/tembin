package com.base.utils.scheduleabout;

import com.base.utils.threadpool.TaskPool;

/**
 * Created by Administrtor on 2014/8/29.
 * 所有的任务类都必须继承此类
 */
public class BaseScheduledClass {
    /**获取当前线程池中还有多少任务*/
    public int getCurrActiveCount(){
        return TaskPool.scheduledThreadPoolTaskExecutor.getActiveCount();
    }
}
