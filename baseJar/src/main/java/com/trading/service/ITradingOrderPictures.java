package com.trading.service;

import com.base.database.trading.model.TradingOrderPictures;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/16.
 */
public interface ITradingOrderPictures {

    void saveOrderPictures(TradingOrderPictures OrderPictures) throws Exception;

    List<TradingOrderPictures> selectOrderPicturesByItemId(Long Id);

    void deleteOrderPictures (TradingOrderPictures OrderPictures) throws Exception;

}
