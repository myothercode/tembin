package com.trading.service;

import com.base.database.trading.model.TradingShippingservicecostoverride;
import com.base.xmlpojo.trading.addproduct.ShippingServiceCostOverride;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingShippingServiceCostOverride {
    void saveShippingServiceCostOverride(TradingShippingservicecostoverride pojo);

    TradingShippingservicecostoverride toDAOPojo(ShippingServiceCostOverride ssco) throws Exception;
}
