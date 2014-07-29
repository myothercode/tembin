package com.trading.service;

import com.base.database.trading.model.TradingDiscountpriceinfo;
import com.base.xmlpojo.trading.addproduct.DiscountPriceInfo;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Administrtor on 2014/7/23.
 */
public interface ITradingDiscountPriceInfo {
    void saveDiscountpriceinfo(TradingDiscountpriceinfo pojo);

    TradingDiscountpriceinfo toDAOPojo(DiscountPriceInfo discountPriceInfo) throws Exception;
}
