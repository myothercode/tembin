package com.trading.service;

import com.base.database.keymove.model.KeyMoveList;
import com.base.database.trading.model.TradingItem;
import com.base.database.trading.model.TradingItemWithBLOBs;
import com.base.domains.querypojos.ItemQuery;
import com.base.mybatis.page.Page;
import com.base.xmlpojo.trading.addproduct.Item;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/23.
 */
public interface ITradingItem {
    void saveTradingItem(TradingItemWithBLOBs pojo) throws Exception;

    void saveTradingItemList(List<TradingItemWithBLOBs> liti) throws Exception;

    TradingItemWithBLOBs toDAOPojo(Item item) throws Exception;

    Map saveItem(Item item, TradingItemWithBLOBs tradingItem) throws Exception;

    Item toItem(TradingItem tradingItem) throws Exception;

    List<ItemQuery> selectByItemList(Map map, Page page);

    TradingItem selectById(Long id);

    TradingItemWithBLOBs selectByIdBL(Long id);

    TradingItemWithBLOBs selectByItemId(String itemId);

    void updateTradingItem(Item item, TradingItemWithBLOBs tradingItem) throws Exception;

    void saveListingItem(Item item, KeyMoveList kml) throws Exception;

    // }
    void delItem(String[] ids);

    void rename(String[] ids, String fileName);

    void copyItem(String[] ids, String ebayaccount) throws Exception;

    Item toEditItem(TradingItem tradingItem) throws Exception;
}
