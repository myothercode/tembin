package com.trading.service;

import com.base.database.trading.model.TradingDescriptionDetailsWithBLOBs;
import com.base.domains.querypojos.DescriptionDetailsWithBLOBsQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/23.
 */
public interface ITradingDescriptionDetails {
    void saveDescriptionDetails(TradingDescriptionDetailsWithBLOBs pojo) throws Exception;

    TradingDescriptionDetailsWithBLOBs toDAOPojo(String payInfo, String shippingInfo, String contactInfo, String guaranteeInfo, String feedbackInfo) throws  Exception;

    List<DescriptionDetailsWithBLOBsQuery> selectByDescriptionDetailsList(Map map);

    List<DescriptionDetailsWithBLOBsQuery> selectByDescriptionDetailsList(Map map,Page page);
}
