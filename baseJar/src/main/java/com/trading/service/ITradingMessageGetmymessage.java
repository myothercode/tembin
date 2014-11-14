package com.trading.service;

import com.base.database.trading.model.TradingMessageGetmymessage;
import com.base.domains.querypojos.MessageGetmymessageQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/05.
 */
public interface ITradingMessageGetmymessage {
    void saveMessageGetmymessage(TradingMessageGetmymessage MessageGetmymessage) throws Exception;

    /*TradingMessageGetmymessage toDAOPojo(String payInfo, String shippingInfo, String contactInfo, String guaranteeInfo, String feedbackInfo) throws  Exception;*/

    List<MessageGetmymessageQuery> selectByMessageGetmymessageList(Map map);

    List<MessageGetmymessageQuery> selectByMessageGetmymessageList(Map map, Page page);

    List<MessageGetmymessageQuery> selectMessageGetmymessageByGroupList(Map map, Page page);

    List<MessageGetmymessageQuery> selectMessageGetmymessageBySender(Map map);

    List<TradingMessageGetmymessage> selectMessageGetmymessageByNoRead(String read);

    List<TradingMessageGetmymessage> selectMessageGetmymessageByMessageId(String messageID);

    List<TradingMessageGetmymessage> selectMessageGetmymessageByItemIdAndSender(String itemid,String sender,String recipient );

    TradingMessageGetmymessage selectMessageGetmymessageById(Long id);

    List<TradingMessageGetmymessage> selectMessageGetmymessageByReplied(Long userId);
}
