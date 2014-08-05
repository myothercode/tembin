package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.TemplateInitTableQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface TemplateInitTableMapper {

    /**
     *
     * @param map
     * @return
     */
    List<TemplateInitTableQuery> selectByTemplateInitTableList(Map map, Page page);
}