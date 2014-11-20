package com.task.service;

import com.base.database.task.model.TaskGetUserCases;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrtor on 2014/10/17.
 */
public interface ITaskGetUserCases {
    void saveListTaskGetUserCases(TaskGetUserCases TaskGetUserCases);

    List<TaskGetUserCases> selectTaskGetUserCasesByflagAndSaveTime(Integer flag, Date saveTime);

    List<TaskGetUserCases> selectTaskGetUserCasesByFlagIsFalseOrderBysaveTime();
}
