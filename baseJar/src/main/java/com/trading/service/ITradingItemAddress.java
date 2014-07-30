package com.trading.service;

import com.base.database.trading.model.TradingItemAddress;
import com.base.domains.querypojos.ItemAddressQuery;
import com.base.mybatis.page.Page;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/23.
 */
public interface ITradingItemAddress {
    void saveItemAddress(TradingItemAddress tradingItemAddress);

    TradingItemAddress toDAOPojo(String name, String address, Long countryId, String postalcode) throws Exception;

    List<ItemAddressQuery> selectByItemAddressQuery(Map map,Page page);

    List<ItemAddressQuery> selectByItemAddressQuery(Map map);
}
