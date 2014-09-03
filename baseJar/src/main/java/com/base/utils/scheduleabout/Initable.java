package com.base.utils.scheduleabout;

import org.springframework.core.Ordered;

/**
 * Created by Administrtor on 2014/9/3.
 * 用于描述系统启动后需要执行的任务
 */
public interface Initable extends Ordered {
    void init();
}
