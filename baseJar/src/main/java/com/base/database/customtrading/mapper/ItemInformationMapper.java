package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.ItemInformationQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface ItemInformationMapper {
    /**
     * 用于查询买家要求列表
     * @param
     * @return
     */
    List<ItemInformationQuery> selectItemInformation(Map map, Page page);

    List<ItemInformationQuery> selectItemInformationByType(Map map, Page page);

    List<ItemInformationQuery> selectItemInformationByOrgId(Map map, Page page);

    List<ItemInformationQuery> selectItemInformationByTypeIsNull(Map map, Page page);
}