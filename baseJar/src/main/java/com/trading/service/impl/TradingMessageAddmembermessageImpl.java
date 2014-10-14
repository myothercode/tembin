package com.trading.service.impl;

import com.base.database.customtrading.mapper.MessageAddmymessageMapper;
import com.base.database.trading.mapper.TradingMessageAddmembermessageMapper;
import com.base.database.trading.model.TradingMessageAddmembermessage;
import com.base.database.trading.model.TradingMessageAddmembermessageExample;
import com.base.domains.querypojos.MessageAddmymessageQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 商品描述模块
 * Created by cz on 2014/7/23.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingMessageAddmembermessageImpl implements com.trading.service.ITradingMessageAddmembermessage {
    @Autowired
    private TradingMessageAddmembermessageMapper TradingMessageAddmembermessageMapper;
    @Autowired
    private MessageAddmymessageMapper messageAddmymessageMapper;
   

    @Override
    public void saveMessageAddmembermessage(TradingMessageAddmembermessage MessageAddmembermessage) throws Exception {
        if(MessageAddmembermessage.getId()==null){
            ObjectUtils.toInitPojoForInsert(MessageAddmembermessage);
            this.TradingMessageAddmembermessageMapper.insertSelective(MessageAddmembermessage);
        }else{
            TradingMessageAddmembermessage t=TradingMessageAddmembermessageMapper.selectByPrimaryKey(MessageAddmembermessage.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingMessageAddmembermessageMapper.class,MessageAddmembermessage.getId());

            this.TradingMessageAddmembermessageMapper.updateByPrimaryKeySelective(MessageAddmembermessage);
        }
    }

    @Override
    public List<TradingMessageAddmembermessage> selectMessageGetmymessageByMessageId(String messageId,String replied) {
        TradingMessageAddmembermessageExample example=new TradingMessageAddmembermessageExample();
        TradingMessageAddmembermessageExample.Criteria ce=example.createCriteria();
        ce.andMessageidEqualTo(messageId);
        ce.andRepliedEqualTo(replied);
        List<TradingMessageAddmembermessage> list=TradingMessageAddmembermessageMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<MessageAddmymessageQuery> selectMessageGetmymessageByGroupList(Map map, Page page) {
        return messageAddmymessageMapper.selectMessageGetmymessageByGroupList(map,page);
    }

}