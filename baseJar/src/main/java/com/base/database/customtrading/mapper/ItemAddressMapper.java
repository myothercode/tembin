package com.base.database.customtrading.mapper;

import com.base.database.trading.model.TradingItemAddress;
import com.base.database.trading.model.TradingItemAddressExample;
import com.base.domains.querypojos.ItemAddressQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemAddressMapper {

    /**
     *
     * @param map
     * @return
     */
    List<ItemAddressQuery> selectByItemAddressList(Map map);
}