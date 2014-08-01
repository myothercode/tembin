package com.trading.service;

import com.base.database.trading.model.TradingInternationalshippingserviceoption;
import com.base.xmlpojo.trading.addproduct.InternationalShippingServiceOption;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingInternationalShippingServiceOption {
    void saveInternationalShippingServiceOption(TradingInternationalshippingserviceoption pojo);

    TradingInternationalshippingserviceoption toDAOPojo(InternationalShippingServiceOption isso) throws Exception;

    void deleteByParentId(Long id);
}
