package com.trading.service;

import com.base.database.trading.model.TradingProductlistingdetails;
import com.base.xmlpojo.trading.addproduct.ProductListingDetails;

/**
 * Created by Administrtor on 2014/7/24.
 */
public interface ITradingProductListingDetails {
    void saveProductListingDetails(TradingProductlistingdetails pojo);

    TradingProductlistingdetails toDAOPojo(ProductListingDetails pld) throws Exception;
}
