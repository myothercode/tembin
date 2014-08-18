package com.trading.service;

import com.base.database.trading.model.TradingOrderGetItem;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingOrderGetItem {

    void saveOrderGetItem(TradingOrderGetItem OrderGetItem) throws Exception;
    List<TradingOrderGetItem> selectOrderGetItemByItemId(String ItemId);
}
