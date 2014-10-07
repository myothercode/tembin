package com.trading.service;

import com.base.database.trading.model.TradingTablePrice;
import com.base.domains.querypojos.TablePriceQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/9/29.
 */
public interface ITradingTablePrice {
    void saveTablePrice(TradingTablePrice tradingTablePrice);

    void saveTablePriceList(List<TradingTablePrice> li);

    List<TablePriceQuery> selectByList(Map map, Page page);

    TradingTablePrice selectById(Long id);

    List<TradingTablePrice> selectByList(String sku, String ebayAccount);
}
