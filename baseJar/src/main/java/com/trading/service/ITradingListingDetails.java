package com.trading.service;

import com.base.database.trading.model.TradingListingdetails;
import com.base.xmlpojo.trading.addproduct.ListingDetails;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingListingDetails {
    void saveListingdetails(TradingListingdetails pojo);

    TradingListingdetails toDAOPojo(ListingDetails listingDetails) throws Exception;
}
