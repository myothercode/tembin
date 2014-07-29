package com.trading.service;

import com.base.database.trading.model.TradingPublicLevelAttr;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingPublicLevelAttr {
    void savePublicLevelAttr(TradingPublicLevelAttr pojo);

    TradingPublicLevelAttr toDAOPojo(String name, String value) throws Exception;
}
