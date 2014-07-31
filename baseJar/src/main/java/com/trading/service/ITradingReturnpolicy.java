package com.trading.service;

import com.base.database.trading.model.TradingReturnpolicy;
import com.base.domains.querypojos.ReturnpolicyQuery;
import com.base.mybatis.page.Page;
import com.base.xmlpojo.trading.addproduct.ReturnPolicy;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingReturnpolicy {
    void saveTradingReturnpolicy(TradingReturnpolicy tradingReturnpolicy);

    TradingReturnpolicy toDAOPojo(ReturnPolicy pojo) throws Exception;

    List<ReturnpolicyQuery> selectByReturnpolicyList(Map map, Page page);

    List<ReturnpolicyQuery> selectByReturnpolicyList(Map map);
}
