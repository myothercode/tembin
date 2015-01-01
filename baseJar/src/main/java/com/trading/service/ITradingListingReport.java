package com.trading.service;

import com.base.database.trading.model.TradingListingReport;
import org.apache.http.impl.cookie.DateParseException;

import java.util.List;

/**
 * Created by Administrtor on 2014/11/8.
 */
public interface ITradingListingReport {
    void save(TradingListingReport tradingListingReport);

    List<TradingListingReport> selectByNowDate(String dataType,String userId) throws DateParseException;

    void initListingReport(long userId);
}
