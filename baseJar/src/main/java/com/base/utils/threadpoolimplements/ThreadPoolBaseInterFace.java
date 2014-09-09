package com.base.utils.threadpoolimplements;

/**
 * Created by Administrtor on 2014/9/9.
 * 用于执行延迟提交的接口
 */
public interface ThreadPoolBaseInterFace {
    public <T> void doWork(String resxml,T... t);

    public String getType();
}
