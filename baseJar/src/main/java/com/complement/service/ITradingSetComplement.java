package com.complement.service;

import com.base.database.trading.model.TradingSetComplement;
import com.base.domains.querypojos.SetComplementQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/12/17.
 */
public interface ITradingSetComplement {
    void saveTradingSetComplement(TradingSetComplement tradingSetComplement);

    TradingSetComplement selectById(long id);

    void delTradingSetComplement(long id);

    List<SetComplementQuery> selectBySetComplementList(Map map,Page page);

    TradingSetComplement selectByEbayId(long ebayid);

    TradingSetComplement selectByEbayAccount(String ebayAccount);

    void delSetEbayComplement(long id);
}
