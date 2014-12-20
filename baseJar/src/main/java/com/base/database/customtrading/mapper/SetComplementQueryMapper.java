package com.base.database.customtrading.mapper;

import com.base.database.task.model.ListingDataTask;
import com.base.domains.querypojos.SetComplementQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface SetComplementQueryMapper {
    List<SetComplementQuery> selectSetComplementByList(Map map,Page page);
}