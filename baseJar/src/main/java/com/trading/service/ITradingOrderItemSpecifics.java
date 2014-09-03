package com.trading.service;

import com.base.database.trading.model.TradingOrderItemSpecifics;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/16.
 */
public interface ITradingOrderItemSpecifics {

    void saveOrderItemSpecifics(TradingOrderItemSpecifics OrderItemSpecifics) throws Exception;

    List<TradingOrderItemSpecifics> selectOrderItemSpecificsByItemId(Long Id);

    void deleteOrderItemSpecifics (TradingOrderItemSpecifics OrderItemSpecifics) throws Exception;

}
