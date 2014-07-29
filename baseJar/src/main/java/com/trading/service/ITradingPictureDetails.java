package com.trading.service;

import com.base.database.trading.model.TradingPicturedetails;
import com.base.xmlpojo.trading.addproduct.PictureDetails;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingPictureDetails {
    void savePictureDetails(TradingPicturedetails tp);

    TradingPicturedetails toDAOPojo(PictureDetails pd) throws Exception;
}
