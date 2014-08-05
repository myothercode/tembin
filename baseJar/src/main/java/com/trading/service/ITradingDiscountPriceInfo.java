package com.trading.service;

import com.base.database.trading.model.TradingDiscountpriceinfo;
import com.base.domains.querypojos.DiscountpriceinfoQuery;
import com.base.mybatis.page.Page;
import com.base.xmlpojo.trading.addproduct.DiscountPriceInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/23.
 */
public interface ITradingDiscountPriceInfo {
    void saveDiscountpriceinfo(TradingDiscountpriceinfo pojo) throws Exception;

    TradingDiscountpriceinfo toDAOPojo(DiscountPriceInfo discountPriceInfo) throws Exception;

    List<DiscountpriceinfoQuery> selectByDiscountpriceinfo(Map map);

    List<DiscountpriceinfoQuery> selectByDiscountpriceinfo(Map map, Page page);

    TradingDiscountpriceinfo selectById(Long id);
}
