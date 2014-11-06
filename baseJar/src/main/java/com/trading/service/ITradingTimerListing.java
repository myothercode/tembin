package com.trading.service;

import com.base.database.trading.model.TradingTimerListingWithBLOBs;

/**
 * Created by Administrtor on 2014/8/27.
 */
public interface ITradingTimerListing {
    void saveTradingTimer(TradingTimerListingWithBLOBs tradingTimerListing) throws Exception;

    void delTradingTimer(String itemid);
}
