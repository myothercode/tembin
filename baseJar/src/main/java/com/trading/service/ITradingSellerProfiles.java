package com.trading.service;

import com.base.database.trading.model.TradingSellerprofiles;
import com.base.xmlpojo.trading.addproduct.SellerProfiles;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingSellerProfiles {
    void saveSellerprofiles(TradingSellerprofiles pojo);

    TradingSellerprofiles toDAOPojo(SellerProfiles sp) throws Exception;
}
