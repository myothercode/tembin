package com.base.utils.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Administrtor on 2014/8/29.
 */
@Component
public class CommAutowiredClass {
    @Value("${EBAY.API.URL}")
    public String apiUrl;//api的调用地址
    @Value("${IS_START_TIMER_TASK}")
    public String isStartTimerTask;//本机是否开启定时任务



}
