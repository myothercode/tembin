package com.task.service;

import com.base.database.task.model.TaskGetOrders;
import com.base.database.task.model.TaskGetOrdersSellerTransaction;
import com.base.database.trading.model.TradingOrderGetOrders;

import java.util.List;

/**
 * Created by Administrtor on 2014/10/17.
 */
public interface IScheduleGetTimerOrders {

    void synchronizeOrders(List<TaskGetOrders> taskGetOrders) throws Exception;

    void synchronizeOrderItems(List<TradingOrderGetOrders> orders) throws Exception;

    void synchronizeOrderAccount(List<TradingOrderGetOrders> orders) throws Exception;

    void synchronizeOrderSellerTrasaction(TaskGetOrdersSellerTransaction sellerTransaction) throws Exception;
}
