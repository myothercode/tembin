package com.trading.service;

import com.base.database.trading.model.TradingAutoMessageAndOrder;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/14.
 */
public interface ITradingAutoMessageAndOrder {

    void saveAutoMessageAndOrder(TradingAutoMessageAndOrder autoMessageAndOrder) throws Exception;

    TradingAutoMessageAndOrder selectAutoMessageAndOrderById(Long id);

    void deleteAutoMessageAndOrder(TradingAutoMessageAndOrder autoMessageAndOrder) throws Exception;

    List<TradingAutoMessageAndOrder> selectAutoMessageAndOrderByAutoMessageId(Long autoMessageId);

    List<TradingAutoMessageAndOrder> selectAutoMessageAndOrderByAutoOrderId(Long orderid);
}
