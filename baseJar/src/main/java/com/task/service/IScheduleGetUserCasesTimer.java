package com.task.service;

import com.base.database.task.model.TaskGetUserCases;

import java.util.List;

/**
 * Created by Administrtor on 2014/10/17.
 */
public interface IScheduleGetUserCasesTimer {

    void synchronizeUserCases(List<TaskGetUserCases> taskGetUserCases) throws Exception;
}
