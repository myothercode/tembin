package com.trading.service.impl;

import com.base.database.trading.mapper.TradingMessageAddmembermessageMapper;
import com.base.database.trading.model.TradingMessageAddmembermessage;
import com.base.database.trading.model.TradingMessageAddmembermessageExample;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品描述模块
 * Created by cz on 2014/7/23.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingMessageAddmembermessageImpl implements com.trading.service.ITradingMessageAddmembermessage {
    @Autowired
    private TradingMessageAddmembermessageMapper TradingMessageAddmembermessageMapper;

   

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
    public List<TradingMessageAddmembermessage> selectMessageGetmymessageByItemId(String itemid) {
        TradingMessageAddmembermessageExample example=new TradingMessageAddmembermessageExample();
        TradingMessageAddmembermessageExample.Criteria ce=example.createCriteria();
        ce.andItemidEqualTo(itemid);
        List<TradingMessageAddmembermessage> list=TradingMessageAddmembermessageMapper.selectByExample(example);
        return list;
    }

}