package com.inventory.service;

import com.base.database.inventory.model.UserInventorySet;

import java.util.List;

/**
 * Created by Administrtor on 2015/1/27.
 */
public interface IUserInventorySet {
    void saveUserInventorySet(UserInventorySet userInventorySet);

    List<UserInventorySet> selectByOrgIdList(long orgId);

    UserInventorySet selectById(long id);

    void deleteInventorySet(Long id);

    UserInventorySet selectDistinc(String dataType, String userName, Long orgId);
}
