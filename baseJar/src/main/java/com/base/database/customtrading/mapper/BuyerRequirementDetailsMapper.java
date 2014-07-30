package com.base.database.customtrading.mapper;

import com.base.database.trading.model.TradingBuyerRequirementDetails;
import com.base.database.trading.model.TradingBuyerRequirementDetailsExample;
import com.base.domains.querypojos.BuyerRequirementDetailsQuery;
import com.base.mybatis.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BuyerRequirementDetailsMapper {
    /**
     * 用于查询买家要求列表
     * @param
     * @return
     */
    List<BuyerRequirementDetailsQuery> selectByBuyer(Map map,Page page);

}