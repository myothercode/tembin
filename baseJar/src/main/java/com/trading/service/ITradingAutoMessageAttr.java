package com.trading.service;

import com.base.database.trading.model.TradingAutoMessageAttr;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/14.
 */
public interface ITradingAutoMessageAttr {

    void saveAutoMessageAttr(TradingAutoMessageAttr autoMessageAttr) throws Exception;

    List<TradingAutoMessageAttr> selectAutoMessageListByautoMessageId(Long autoMessageId,String Type);

    List<TradingAutoMessageAttr> selectAutoMessageListByDictionaryIdIsNull(String Type);

    List<TradingAutoMessageAttr> selectAutoMessageListById(Long id);

    List<TradingAutoMessageAttr> selectAutoMessageListByMessageId(Long autoMessageId);

    void deleteAutoMessageAttr(TradingAutoMessageAttr autoMessageAttr) throws Exception;
}
