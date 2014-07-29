package com.trading.service;

import com.base.database.trading.model.TradingShippingdetails;
import com.base.xmlpojo.trading.addproduct.ShippingDetails;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Administrtor on 2014/7/23.
 */
public interface ITradingShippingDetails {
    void saveShippingDetails(TradingShippingdetails pojo);

    TradingShippingdetails toDAOPojo(ShippingDetails shippingDetails) throws Exception;
}
