package com.complement.service;

import com.base.database.trading.model.TradingAutoComplement;
import com.base.domains.querypojos.SystemLogQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/12/11.
 */
public interface ITradingAutoComplement {
    List<TradingAutoComplement> selectByList(Map m, Page page);

    void saveAutoComplement(TradingAutoComplement tradingAutoComplement);

    int delAutoComplement(long id);

    TradingAutoComplement selectById(long id);

    List<SystemLogQuery> selectLogList(Map m, Page page);

    List<TradingAutoComplement> selectByEbayAccount(String ebayAccount);
}
