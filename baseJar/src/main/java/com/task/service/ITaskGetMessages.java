package com.task.service;

import com.base.database.task.model.TaskGetMessages;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrtor on 2014/10/17.
 */
public interface ITaskGetMessages {
    void saveListTaskGetMessages(TaskGetMessages taskGetMessages);

    List<TaskGetMessages> selectTaskGetMessagesByflagAndSaveTime(Integer flag, Date saveTime);

    List<TaskGetMessages> selectTaskGetMessagesByFlagIsFalseOrderBysaveTime();

    List<TaskGetMessages> selectTaskGetMessagesByFlagIsFalseOrderByLastSycTimeAndEbayName(String ebayName);
}
