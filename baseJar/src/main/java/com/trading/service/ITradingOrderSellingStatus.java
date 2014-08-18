package com.trading.service;

import com.base.database.trading.model.TradingOrderSellingStatus;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingOrderSellingStatus {

    void saveOrderSellingStatus(TradingOrderSellingStatus OrderSellingStatus) throws Exception;
}
