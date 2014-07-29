package com.trading.service;

import com.base.database.trading.model.TradingListingcheckoutredirectpreference;
import com.base.xmlpojo.trading.addproduct.ListingCheckoutRedirectPreference;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingListingCheckoutRedirectPreference {
    void saveListingCheckoutRedirectPreference(TradingListingcheckoutredirectpreference pojo);

    TradingListingcheckoutredirectpreference toDAOPojo(ListingCheckoutRedirectPreference lcrp) throws Exception;
}
