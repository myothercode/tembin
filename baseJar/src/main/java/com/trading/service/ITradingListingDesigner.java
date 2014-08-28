package com.trading.service;

import com.base.database.trading.model.TradingListingdesigner;
import com.base.xmlpojo.trading.addproduct.ListingDesigner;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingListingDesigner {
    void saveListingdesigner(TradingListingdesigner pojo);

    TradingListingdesigner toDAOPojo(ListingDesigner ld) throws Exception;
}
