package com.trading.service;

import com.base.database.trading.model.TradingShippingpackagedetails;
import com.base.xmlpojo.trading.addproduct.ShippingPackageDetails;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingShippingPackageDetails {
    void saveShippingPackageDetails(TradingShippingpackagedetails pojo);

    TradingShippingpackagedetails toDAOPojo(ShippingPackageDetails spd) throws Exception;
}
