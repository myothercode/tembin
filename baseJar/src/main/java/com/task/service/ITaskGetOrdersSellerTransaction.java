package com.task.service;

import com.base.database.task.model.TaskGetOrdersSellerTransaction;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrtor on 2014/10/17.
 */
public interface ITaskGetOrdersSellerTransaction {
    void saveTaskGetOrdersSellerTransaction(TaskGetOrdersSellerTransaction taskGetOrdersSellerTransaction);

    List<TaskGetOrdersSellerTransaction> selectTaskGetOrdersSellerTransactionByflagAndSaveTime(Integer flag, Date saveTime);

    List<TaskGetOrdersSellerTransaction> selectTaskGetOrdersSellerTransactionByFlagIsFalseOrderBysaveTime();
}
