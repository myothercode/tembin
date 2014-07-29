package com.trading.service;

import com.base.database.trading.model.TradingDescriptionDetailsWithBLOBs;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Administrtor on 2014/7/23.
 */
public interface ITradingDescriptionDetails {
    void saveDescriptionDetails(TradingDescriptionDetailsWithBLOBs pojo);

    TradingDescriptionDetailsWithBLOBs toDAOPojo(String payInfo, String shippingInfo, String contactInfo, String guaranteeInfo, String feedbackInfo) throws  Exception;
}
