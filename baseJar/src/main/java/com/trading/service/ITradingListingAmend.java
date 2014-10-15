package com.trading.service;

import com.base.database.trading.model.TradingListingAmend;
import com.base.database.trading.model.TradingListingAmendWithBLOBs;

/**
 * Created by Administrtor on 2014/9/29.
 */
public interface ITradingListingAmend {
    void saveListingAmend(TradingListingAmendWithBLOBs tradingListingAmend);

    TradingListingAmend selectByItemID(String itemid, String amendtype);
}
