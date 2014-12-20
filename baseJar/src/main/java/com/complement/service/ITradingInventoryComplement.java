package com.complement.service;

import com.base.database.trading.model.TradingInventoryComplement;
import com.base.database.trading.model.TradingInventoryComplementMore;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/12/17.
 */
public interface ITradingInventoryComplement {
    void saveInventoryComplement(TradingInventoryComplement tradingInventoryComplement, List<TradingInventoryComplementMore> liti);

    void delByParentId(long parentId);

    void delByid(long id);

    void delAllData(long id);

    List<TradingInventoryComplement> selectByInventoryComplementList(Map map, Page page);

    TradingInventoryComplement selectById(long id);

    List<TradingInventoryComplementMore> slectByParentId(long parentId);
}
