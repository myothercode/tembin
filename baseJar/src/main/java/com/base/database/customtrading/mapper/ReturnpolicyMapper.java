package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.ReturnpolicyQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface ReturnpolicyMapper {

    /**
     *
     * @param map
     * @return
     */
    List<ReturnpolicyQuery> selectByReturnpolicyList(Map map,Page page);
}