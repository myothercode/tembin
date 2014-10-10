package com.trading.service.impl;

import com.base.database.customtrading.mapper.MessageGetmymessageMapper;
import com.base.database.trading.mapper.TradingMessageGetmymessageMapper;
import com.base.database.trading.model.TradingMessageGetmymessage;
import com.base.database.trading.model.TradingMessageGetmymessageExample;
import com.base.domains.querypojos.MessageGetmymessageQuery;
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
public class TradingMessageGetmymessageImpl implements com.trading.service.ITradingMessageGetmymessage {
    @Autowired
    private TradingMessageGetmymessageMapper TradingMessageGetmymessageMapper;

    @Autowired
    private MessageGetmymessageMapper MessageGetmymessageMapper;

    @Override
    public void saveMessageGetmymessage(TradingMessageGetmymessage MessageGetmymessage) throws Exception {
        if(MessageGetmymessage.getId()==null){
            ObjectUtils.toInitPojoForInsert(MessageGetmymessage);
            this.TradingMessageGetmymessageMapper.insert(MessageGetmymessage);
        }else{
            TradingMessageGetmymessage t=TradingMessageGetmymessageMapper.selectByPrimaryKey(MessageGetmymessage.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingMessageGetmymessageMapper.class,MessageGetmymessage.getId());

            this.TradingMessageGetmymessageMapper.updateByPrimaryKeySelective(MessageGetmymessage);
        }
    }

    @Override
    public List<MessageGetmymessageQuery> selectByMessageGetmymessageList(Map map) {
        Page page=new Page();
        page.setPageSize(10);
        page.setCurrentPage(1);
        return MessageGetmymessageMapper.selectByMessageGetmymessageList(map,page);
    }

    @Override
    public List<MessageGetmymessageQuery> selectByMessageGetmymessageList(Map map, Page page) {
        return MessageGetmymessageMapper.selectByMessageGetmymessageList(map,page);
    }

    @Override
    public List<MessageGetmymessageQuery> selectMessageGetmymessageByGroupList(Map map, Page page) {
        return MessageGetmymessageMapper.selectMessageGetmymessageByGroupList(map,page);
    }

    @Override
    public List<MessageGetmymessageQuery> selectMessageGetmymessageBySender(Map map) {
        return MessageGetmymessageMapper.selectMessageGetmymessageBySender(map);
    }

    @Override
    public List<TradingMessageGetmymessage> selectMessageGetmymessageByItemId(String itemid) {
        TradingMessageGetmymessageExample example=new TradingMessageGetmymessageExample();
        TradingMessageGetmymessageExample.Criteria cr=example.createCriteria();
        cr.andItemidEqualTo(itemid);
        List<TradingMessageGetmymessage> list=TradingMessageGetmymessageMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<TradingMessageGetmymessage> selectMessageGetmymessageByItemIdAndSender(String itemid, String sender,String recipient ) {
        TradingMessageGetmymessageExample example=new TradingMessageGetmymessageExample();
        TradingMessageGetmymessageExample.Criteria cr=example.createCriteria();
        cr.andItemidEqualTo(itemid);
        cr.andSenderEqualTo(sender);
        cr.andRecipientuseridEqualTo(recipient);
        List<TradingMessageGetmymessage> list=TradingMessageGetmymessageMapper.selectByExampleWithBLOBs(example);
        return list;
    }
}