package com.trading.service;

import com.base.database.trading.model.TradingPictures;
import com.base.xmlpojo.trading.addproduct.Pictures;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingPictures {
    void savePictures(TradingPictures tp);

    TradingPictures toDAOPojo(Pictures pic) throws Exception;
}
