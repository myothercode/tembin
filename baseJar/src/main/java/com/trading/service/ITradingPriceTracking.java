package com.trading.service;

import com.base.database.trading.model.TradingPriceTracking;
import com.base.domains.querypojos.PriceTrackingQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/14.
 */
public interface ITradingPriceTracking {
    void savePriceTracking(TradingPriceTracking tradingPriceTracking) throws Exception;
    List<TradingPriceTracking> getPriceTrackingItem(String title) throws Exception;
    List<TradingPriceTracking> selectPriceTrackingByItemId(String itemId);
    List<PriceTrackingQuery> selectPriceTrackingList(Map map,Page page);
    List<TradingPriceTracking> selectPriceTracking();
    TradingPriceTracking selectPriceTrackingById(Long id);
}
