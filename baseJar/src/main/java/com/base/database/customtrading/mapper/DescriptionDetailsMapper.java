package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.DescriptionDetailsWithBLOBsQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface DescriptionDetailsMapper {

    /**
     *
     * @param map
     * @return
     */
    List<DescriptionDetailsWithBLOBsQuery> selectByDescriptionDetailsList(Map map,Page page);
}