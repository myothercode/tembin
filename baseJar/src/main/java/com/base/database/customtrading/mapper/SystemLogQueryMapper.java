package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.SystemLogQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface SystemLogQueryMapper {

    List<SystemLogQuery> selectSystemLogList(Map map, Page page);

    List<SystemLogQuery> selectLogList(Map map, Page page);
}