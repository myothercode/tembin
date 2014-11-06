package com.task.service;

import com.base.database.task.model.TaskGetOrders;

import java.util.List;

/**
 * Created by Administrtor on 2014/10/17.
 */
public interface IScheduleGetTimerOrders {

    void synchronizeOrders(List<TaskGetOrders> taskGetOrders);
}
