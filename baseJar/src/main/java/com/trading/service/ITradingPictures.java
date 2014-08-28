package com.trading.service;

import com.base.database.trading.model.TradingPictures;
import com.base.xmlpojo.trading.addproduct.Pictures;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingPictures {
    void savePictures(TradingPictures tp) throws Exception;

    TradingPictures toDAOPojo(Pictures pic) throws Exception;

    TradingPictures selectParnetId(Long id);
}
