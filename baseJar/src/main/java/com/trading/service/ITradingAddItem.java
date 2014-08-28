package com.trading.service;

import com.base.database.trading.model.TradingAddItem;

/**
 * Created by Administrtor on 2014/8/14.
 */
public interface ITradingAddItem {
    void saveAddItem(TradingAddItem tradingAddItem) throws Exception;

    TradingAddItem selectParentId(Long id);
}
