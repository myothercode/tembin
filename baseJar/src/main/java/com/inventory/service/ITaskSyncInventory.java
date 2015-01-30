package com.inventory.service;

import com.base.database.inventory.model.TaskSyncInventory;

import java.util.List;

/**
 * Created by Administrtor on 2015/1/29.
 */
public interface ITaskSyncInventory {
    void saveTaskSyncInventory(TaskSyncInventory taskSyncInventory);

    TaskSyncInventory selectByUserName(String dataType, String userName);

    List<TaskSyncInventory> selectByPageList();
}
