package com.trading.service;

import com.base.database.trading.model.TradingInternationalshippingserviceoption;
import com.base.database.trading.model.TradingShippingdetails;
import com.base.database.trading.model.TradingShippingserviceoptions;
import com.base.domains.querypojos.ShippingdetailsQuery;
import com.base.mybatis.page.Page;
import com.base.xmlpojo.trading.addproduct.ShippingDetails;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/23.
 */
public interface ITradingShippingDetails {
    void saveShippingDetails(TradingShippingdetails pojo) throws Exception;

    TradingShippingdetails toDAOPojo(ShippingDetails shippingDetails) throws Exception;

    List<ShippingdetailsQuery> selectByShippingdetailsQuery(Map map,Page page);

    TradingShippingdetails selectById(Long id);

    List<TradingShippingserviceoptions> selectByShippingserviceoptions(Long id);

    List<TradingInternationalshippingserviceoption> selectByInternationalshippingserviceoption(Long id);

    void saveAllData(TradingShippingdetails tradingShippingdetails, ShippingDetails shippingDetails, String noLocations) throws Exception;

    ShippingDetails toXmlPojo(Long id) throws Exception;
}
