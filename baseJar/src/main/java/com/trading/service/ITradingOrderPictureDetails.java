package com.trading.service;

import com.base.database.trading.model.TradingOrderPictureDetails;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingOrderPictureDetails {

    void saveOrderPictureDetails(TradingOrderPictureDetails OrderPictureDetails) throws Exception;
    List<TradingOrderPictureDetails> selectOrderGetItemById(Long id);
}
