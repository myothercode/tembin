package com.trading.service;

import com.base.database.trading.model.TradingShippingserviceoptions;
import com.base.xmlpojo.trading.addproduct.ShippingServiceOptions;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingShippingServiceOptions {
    void saveShippingServiceOptions(TradingShippingserviceoptions pojo) throws Exception;

    TradingShippingserviceoptions toDAOPojo(ShippingServiceOptions sso) throws Exception;

    void deleteByParentId(Long id);

    List<ShippingServiceOptions> toXmlPojo(Long id) throws Exception;
}
