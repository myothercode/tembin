package com.base.domains.querypojos;

import com.base.database.trading.model.TradingPublicLevelAttr;
import com.base.database.trading.model.TradingVariation;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/12.
 */
public class VariationQuery extends TradingVariation {
    public List<TradingPublicLevelAttr> tradingPublicLevelAttr;

    public List<TradingPublicLevelAttr> getTradingPublicLevelAttr() {
        return tradingPublicLevelAttr;
    }

    public void setTradingPublicLevelAttr(List<TradingPublicLevelAttr> tradingPublicLevelAttr) {
        this.tradingPublicLevelAttr = tradingPublicLevelAttr;
    }
}
