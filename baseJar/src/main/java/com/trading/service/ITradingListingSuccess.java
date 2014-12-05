package com.trading.service;

import com.base.database.task.model.ListingDataTask;
import com.base.database.trading.model.TradingListingReport;
import com.base.database.trading.model.TradingListingSuccess;
import com.base.domains.querypojos.ListingItemReportQuery;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/11/8.
 */
public interface ITradingListingSuccess {
    void save(TradingListingSuccess tradingListingSuccess);

    List<TradingListingSuccess> selectByItemid(String itemId);

    List<ListingItemReportQuery> selectListingItemReport(long userid,String type,String flag,String soldflag);

    List<ListingItemReportQuery> selectListingItemReportFee(long userid,String type, String flag, String soldflag);

    List<ListingItemReportQuery> selectListingItemSales(String type, String flag, String soldflag);

    List<TradingListingReport> selectItemReportList(Map m);

    ListingDataTask selectByMaxCreateDate(Map m);
}
