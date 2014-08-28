package com.trading.service;

import com.base.database.trading.model.TradingBuyerRequirementDetails;
import com.base.domains.querypojos.BuyerRequirementDetailsQuery;
import com.base.mybatis.page.Page;
import com.base.xmlpojo.trading.addproduct.BuyerRequirementDetails;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/23.
 */
public interface ITradingBuyerRequirementDetails {
    void saveBuyerRequirementDetails(TradingBuyerRequirementDetails tradingBuyerRequirementDetails);

    TradingBuyerRequirementDetails toDAOPojo(BuyerRequirementDetails buyerRequirementDetails) throws Exception;

    List<BuyerRequirementDetailsQuery> selectTradingBuyerRequirementDetailsByList(Map m,Page page);

    List<BuyerRequirementDetailsQuery> selectTradingBuyerRequirementDetailsByList(Map m);

    TradingBuyerRequirementDetails selectById(Long id);

    BuyerRequirementDetails toXmlPojo(Long id);
}
