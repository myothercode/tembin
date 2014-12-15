package com.task.service;

import com.base.database.task.model.TaskComplement;

import java.util.List;

/**
 * Created by Administrtor on 2014/12/11.
 */
public interface ITaskComplement {
    void saveTaskComplement(TaskComplement taskComplement);

    List<TaskComplement> selectByList();
}
