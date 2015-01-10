package com.trading.service;

import com.base.database.trading.model.TradingPriceTrackingPricingRule;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/14.
 */
public interface ITradingPriceTrackingPricingRule {
    void saveTradingPriceTrackingPricingRule(TradingPriceTrackingPricingRule tradingPriceTrackingPricingRule) throws Exception;
    List<TradingPriceTrackingPricingRule> selectTradingPriceTrackingPricingRuleByAutoPricingId(Long autoPricingId);
    void deleteTradingPriceTrackingPricingRule(TradingPriceTrackingPricingRule tradingPriceTrackingPricingRule);
}
