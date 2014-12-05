package com.trading.service;

import com.base.database.trading.model.TradingAutoMessage;
import com.base.domains.querypojos.AutoMessageQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/14.
 */
public interface ITradingAutoMessage {

    void saveAutoMessage(TradingAutoMessage autoMessage) throws Exception;

    List<AutoMessageQuery> selectAutoMessageList(Map map, Page page);

    List<TradingAutoMessage> selectAutoMessageById(Long id);

    void deleteAutoMessage(TradingAutoMessage autoMessage) throws Exception;

    List<AutoMessageQuery> selectShippingServiceOptionList(Map map, Page page);

    List<AutoMessageQuery> selectInternationalShippingServiceList(Map map, Page page);

    List<TradingAutoMessage> selectAutoMessageByType(String type,Long userid);
}
