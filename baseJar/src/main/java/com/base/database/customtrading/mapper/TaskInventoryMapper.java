package com.base.database.customtrading.mapper;

import com.base.database.inventory.model.TaskSyncInventory;
import com.base.domains.querypojos.ListingDataQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface TaskInventoryMapper {

    List<TaskSyncInventory> selectBydoExample(Map map, Page page);

}