package com.trading.service;

import com.base.database.trading.model.TradingInternationalshippingserviceoption;
import com.base.xmlpojo.trading.addproduct.InternationalShippingServiceOption;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingInternationalShippingServiceOption {
    void saveInternationalShippingServiceOption(TradingInternationalshippingserviceoption pojo) throws Exception;

    TradingInternationalshippingserviceoption toDAOPojo(InternationalShippingServiceOption isso) throws Exception;

    void deleteByParentId(Long id);


    List<InternationalShippingServiceOption> toXmlPojo(Long id) throws Exception;
}
