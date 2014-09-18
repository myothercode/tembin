package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.TradingOrderAddMemberMessageAAQToPartnerQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface AddMemberMessageAAQToPartnerMapper {

    /**
     *
     * @param map
     * @return
     */
    List<TradingOrderAddMemberMessageAAQToPartnerQuery> selectByAddMemberMessageAAQToPartnerList(Map map, Page page);

}