package com.trading.service;

import com.base.database.trading.model.TradingVatdetails;
import com.base.xmlpojo.trading.addproduct.VATDetails;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingVATDetails {
    void saveVATDetails(TradingVatdetails pojo);

    TradingVatdetails toDAOPojo(VATDetails vat) throws Exception;
}
