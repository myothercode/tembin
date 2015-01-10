package com.trading.service;

import com.base.database.trading.model.TradingPriceTrackingAutoPricing;
import com.base.domains.querypojos.PriceTrackingAutoPricingQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/14.
 */
public interface ITradingPriceTrackingAutoPricing {
    void savePriceTrackingAutoPricing(TradingPriceTrackingAutoPricing tradingPriceTrackingAutoPricing) throws Exception;

    TradingPriceTrackingAutoPricing selectPriceTrackingAutoPricingByListingDateId(Long listingDateId);

    List<PriceTrackingAutoPricingQuery> selectPriceTrackingAutoPricingList(Map map,Page page);

    void deletePriceTrackingAutoPricing(TradingPriceTrackingAutoPricing tradingPriceTrackingAutoPricing) throws Exception;

    TradingPriceTrackingAutoPricing selectPriceTrackingAutoPricingById(Long id);

    List<TradingPriceTrackingAutoPricing> selectPriceTrackingAutoPricings();
}
