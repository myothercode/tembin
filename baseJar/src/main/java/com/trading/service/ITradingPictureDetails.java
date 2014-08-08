package com.trading.service;

import com.base.database.trading.model.TradingPicturedetails;
import com.base.xmlpojo.trading.addproduct.PictureDetails;

import java.util.List;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingPictureDetails {
    void savePictureDetails(TradingPicturedetails tp) throws Exception;

    TradingPicturedetails toDAOPojo(PictureDetails pd) throws Exception;

    List<TradingPicturedetails> selectByParentId(Long id);
}
