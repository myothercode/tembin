package com.base.database.customtrading.mapper;

import com.base.database.trading.model.TradingAutoComplement;
import com.base.database.trading.model.TradingInventoryComplement;
import com.base.domains.querypojos.ItemInventoryQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/12/18.
 */

public interface ItemInventoryQueryMapper {


    /**
     *
     * @param map
     * @param page
     * @return
     */
    List<ItemInventoryQuery> selectItemInventoryList(Map map, Page page);

    /**
     *
     * @param map
     * @param page
     * @return
     */
    List<TradingInventoryComplement> selectInventoryComplementList(Map map,Page page);
}
