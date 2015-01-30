package com.base.database.customtrading.mapper;

import com.base.database.task.model.TaskComplement;
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

    /**
     * 查询需要补库存，调 价格的数据，进行分页查询
     * @param map
     * @param page
     * @return
     */
    List<TaskComplement> selectTaskComplementList(Map map, Page page);

}