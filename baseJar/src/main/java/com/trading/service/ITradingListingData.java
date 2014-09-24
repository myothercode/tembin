package com.trading.service;

import com.base.database.trading.model.TradingListingData;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/9/23.
 */
public interface ITradingListingData {

    List<TradingListingData> selectData(Map map, Page page);
}
