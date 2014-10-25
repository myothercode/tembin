package com.trading.service;

import com.base.database.trading.model.TradingMessageTemplate;
import com.base.domains.querypojos.MessageTemplateQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/05.
 */
public interface ITradingMessageTemplate {

    void saveMessageTemplate(TradingMessageTemplate messageTemplate) throws Exception;

    List<MessageTemplateQuery> selectMessageTemplateList(Map map, Page page);

    List<TradingMessageTemplate> selectMessageTemplatebyId(Long id);

    void deleteMessageTemplate (TradingMessageTemplate messageTemplate) throws Exception;

    List<TradingMessageTemplate> selectMessageTemplatebType(String type);
}
