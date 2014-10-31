package com.task.service;

import com.base.database.task.model.TaskFeedBack;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrtor on 2014/10/17.
 */
public interface ITaskFeedBack {
    void saveListTaskFeedBack(TaskFeedBack taskFeedBack);

    List<TaskFeedBack> selectTaskFeedBackByflagAndSaveTime(Integer flag, Date saveTime);

    List<TaskFeedBack> selectTaskFeedBackByFlagIsFalseOrderBysaveTime();
}
