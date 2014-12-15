package com.base.database.customtrading.mapper;

import com.base.database.trading.model.TradingAutoComplement;
import com.base.database.trading.model.TradingFeedBackDetail;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface AutoComplementMapper {

    /**
     *
     * @param map
     * @return
     */
    List<TradingAutoComplement> selectAutoComplementList(Map map, Page page);

}