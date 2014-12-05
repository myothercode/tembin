package com.base.utils.exception;

import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2014/12/3.
 * 用来捕获多线程中的报错日志
 */
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    static Logger logger = Logger.getLogger(MyUncaughtExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logger.error("定时任务多线程报错!线程:" + t.getName(), e);
    }
}
