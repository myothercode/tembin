package com.task.service;

import com.base.database.task.model.TaskGetOrders;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrtor on 2014/10/17.
 */
public interface ITaskGetOrders {
    void saveListTaskGetOrders(TaskGetOrders taskGetOrders);

    List<TaskGetOrders> selectTaskGetOrdersByflagAndSaveTime(Integer flag, Date saveTime);

    List<TaskGetOrders> selectTaskGetOrdersByFlagIsFalseOrderBysaveTime();
}
