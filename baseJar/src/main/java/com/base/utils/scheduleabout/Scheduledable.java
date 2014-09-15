package com.base.utils.scheduleabout;

/**
 * Created by Administrtor on 2014/8/29.
 * 所有的定时任务都需实现此接口
 */
public interface Scheduledable extends Runnable {
    /**获取任务类型*/
    public String getScheduledType();
}
