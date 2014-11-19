package com.inventory.service;

import com.base.database.inventory.model.ItemInventory;
import com.base.database.inventory.model.ShihaiyouInventory;

import java.util.List;

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
}
