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
    List<TradingPriceTracking> selectPriceTrackingByTileAndSeller(String itemId,String title,String querytitle,Long userId,String seller);
    List<PriceTrackingQuery> selectPriceTrackingList(Map map,Page page);
}
