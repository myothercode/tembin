package com.base.utils.scheduleother.dorun;

/**
 * Created by Administrator on 2014/11/14.
 */
public interface ScheduleOtherBase extends Runnable {
    public String stype();//类型
    public Integer cyclesTime();//循环时间间隔单位是分钟 ,如果定义为0，那么就是不执行的
}
