package com.trading.service;

import com.base.database.trading.model.TradingPublicLevelAttr;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingPublicLevelAttr {
    void savePublicLevelAttr(TradingPublicLevelAttr pojo);

    TradingPublicLevelAttr toDAOPojo(String name, String value) throws Exception;

    void deleteByParentID(String name, Long id);

    List<TradingPublicLevelAttr> selectByParentId(String name, Long id);
}
