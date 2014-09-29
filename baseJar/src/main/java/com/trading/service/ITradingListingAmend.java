package com.trading.service;

import com.base.database.trading.model.TradingListingAmend;

/**
 * Created by Administrtor on 2014/9/29.
 */
public interface ITradingListingAmend {
    void saveListingAmend(TradingListingAmend tradingListingAmend);

    TradingListingAmend selectByItemID(String itemid, String amendtype);
}
