package com.trading.service;

import com.base.database.trading.model.TradingReturnpolicy;
import com.base.xmlpojo.trading.addproduct.ReturnPolicy;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingReturnpolicy {
    void saveTradingReturnpolicy(TradingReturnpolicy tradingReturnpolicy);

    TradingReturnpolicy toDAOPojo(ReturnPolicy pojo) throws Exception;
}
