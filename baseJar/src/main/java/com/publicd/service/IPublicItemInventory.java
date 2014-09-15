package com.publicd.service;

import com.base.database.publicd.model.PublicItemInventory;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface IPublicItemInventory {

    void saveItemInventory(PublicItemInventory ItemInventory) throws Exception;

    PublicItemInventory selectItemInventoryByid(Long id);

    void deleteItemInventory (Long id) throws Exception;
}
