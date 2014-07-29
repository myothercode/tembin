package com.trading.service;

import com.base.database.trading.model.TradingItem;
import com.base.xmlpojo.trading.addproduct.Item;

/**
 * Created by Administrtor on 2014/7/23.
 */
public interface ITradingItem {
    void saveTradingItem(TradingItem pojo);

    TradingItem toDAOPojo(Item item) throws Exception;
}
