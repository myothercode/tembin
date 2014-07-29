package com.trading.service;

import com.base.database.trading.model.TradingPaypal;
import com.base.domains.querypojos.PaypalQuery;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/23.
 */
public interface ITradingPayPal {
    void savePaypal(TradingPaypal tradingPaypal);

    TradingPaypal toDAOPojo(String payName, String site, String paypal, String paymentinstructions) throws Exception;

    List<PaypalQuery> selectByPayPalList(Map map);
}
