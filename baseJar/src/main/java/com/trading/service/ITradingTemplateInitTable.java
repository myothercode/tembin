package com.trading.service;

import com.base.database.trading.model.TradingTemplateInitTable;
import com.base.domains.querypojos.TemplateInitTableQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/05.
 */
public interface ITradingTemplateInitTable {
    void saveTemplateInitTable(TradingTemplateInitTable templateInitTable) throws Exception;

    /*tradingTemplateInitTable toDAOPojo(String payInfo, String shippingInfo, String contactInfo, String guaranteeInfo, String feedbackInfo) throws  Exception;*/

    List<TemplateInitTableQuery> selectByTemplateInitTableList(Map map);

    List<TemplateInitTableQuery> selectByTemplateInitTableList(Map map,Page page);

    List<TemplateInitTableQuery> selectTemplateType(Map map);

    TradingTemplateInitTable selectById(Long id);

    List<TradingTemplateInitTable> selectByType(long typeid);
}
