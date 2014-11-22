package com.base.utils.scheduleabout;

/**
 * Created by Administrtor on 2014/8/29.
 * 所有的定时任务都需实现此接口
 */
public interface Scheduledable extends Runnable {
    /**获取任务类型*/
    public String getScheduledType();

    /**执行任务的时间间隔 单位是分钟*/
    public Integer crTimeMinu();
}
