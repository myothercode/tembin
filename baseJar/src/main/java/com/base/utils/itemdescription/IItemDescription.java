package com.base.utils.itemdescription;

import com.base.database.trading.model.TradingItemWithBLOBs;
import com.base.xmlpojo.trading.addproduct.Item;

/**
 * Created by Administrtor on 2015/1/22.
 */
public interface IItemDescription {
    String getPaDescription(String des, TradingItemWithBLOBs tradingItem,Item item);
}
