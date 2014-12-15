package com.base.database.customtrading.mapper;

import com.base.database.trading.model.TradingFeedBackDetail;
import com.base.domains.querypojos.AutoMessageQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface ClientAssessFeedBackMapper {

    /**
     *
     * @param map
     * @return
     */
    List<TradingFeedBackDetail> selectClientAssessFeedBackList(Map map, Page page);

}