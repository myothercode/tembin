package com.trading.service;

import com.base.database.trading.model.TradingOrderReturnpolicy;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingOrderReturnpolicy {

    void saveOrderReturnpolicy(TradingOrderReturnpolicy OrderReturnpolicy) throws Exception;
}
