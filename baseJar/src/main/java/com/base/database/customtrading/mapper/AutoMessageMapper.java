package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.AutoMessageQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface AutoMessageMapper {

    /**
     *
     * @param map
     * @return
     */
    List<AutoMessageQuery> selectAutoMessageList(Map map, Page page);
    List<AutoMessageQuery> selectShippingServiceOptionList(Map map, Page page);
    List<AutoMessageQuery> selectInternationalShippingServiceList(Map map, Page page);

}