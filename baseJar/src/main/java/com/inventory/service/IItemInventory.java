package com.inventory.service;

import com.base.database.inventory.model.ItemInventory;
import com.base.database.inventory.model.ShihaiyouInventory;
import com.base.domains.querypojos.ItemInventoryQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/11/17.
 */
public interface IItemInventory {
    void saveItemInventory(ItemInventory itemInventory);

    void saveListItemInventory(List<ItemInventory> liii);

    void getChuKouYiInventory();

    void getSiHaiYouInventory();

    List<ShihaiyouInventory> selectBySkuShiHaiYou(String sku, Long productId, String status);

    void saveShiHaiYouList(List<ShihaiyouInventory> li);

    void saveShiHaiYouData(ShihaiyouInventory shihaiyouInventory);

    void getDeShiFangInventory();

    List<ItemInventoryQuery> selectBySku(Map map, Page page);
}
