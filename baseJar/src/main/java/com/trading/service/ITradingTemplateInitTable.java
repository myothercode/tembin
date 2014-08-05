package com.trading.service;

import com.base.database.trading.model.tradingTemplateInitTable;
import com.base.domains.querypojos.TemplateInitTableQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/05.
 */
public interface ITradingTemplateInitTable {
    void saveTemplateInitTable(tradingTemplateInitTable templateInitTable) throws Exception;

    /*tradingTemplateInitTable toDAOPojo(String payInfo, String shippingInfo, String contactInfo, String guaranteeInfo, String feedbackInfo) throws  Exception;*/

    List<TemplateInitTableQuery> selectByTemplateInitTableList(Map map);

    List<TemplateInitTableQuery> selectByTemplateInitTableList(Map map,Page page);
}
