package com.trading.service;

import com.base.database.trading.model.TradingVariation;
import com.base.domains.querypojos.VariationQuery;
import com.base.xmlpojo.trading.addproduct.Variation;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/11.
 */
public interface ITradingVariation {
    void saveVariation(TradingVariation pojo) throws Exception;

    TradingVariation toDAOPojo(Variation var) throws Exception;

    List<TradingVariation> selectByParentId(Long id);

    void deleteParentId(Long id);

    List<VariationQuery> selectByParentId(Map m) throws Exception;
}
