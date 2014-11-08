package com.trading.service;

import com.base.database.trading.model.TradingListingSuccess;
import com.base.domains.querypojos.ListingItemReportQuery;

import java.util.List;

/**
 * Created by Administrtor on 2014/11/8.
 */
public interface ITradingListingSuccess {
    void save(TradingListingSuccess tradingListingSuccess);

    List<TradingListingSuccess> selectByItemid(String itemId);

    List<ListingItemReportQuery> selectListingItemReport(String type,String flag,String soldflag);
}
