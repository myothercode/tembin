package com.trading.service.impl;

import com.base.database.trading.mapper.TradingAutoMessageAttrMapper;
import com.base.database.trading.model.TradingAutoMessageAttr;
import com.base.database.trading.model.TradingAutoMessageAttrExample;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/14.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingAutoMessageAttrImpl implements com.trading.service.ITradingAutoMessageAttr {
    @Autowired
    private TradingAutoMessageAttrMapper tradingAutoMessageAttrMapper;

    @Override
    public void saveAutoMessageAttr(TradingAutoMessageAttr AutoMessageAttr) throws Exception {
        if(AutoMessageAttr.getId()==null){
            ObjectUtils.toInitPojoForInsert(AutoMessageAttr);
            this.tradingAutoMessageAttrMapper.insertSelective(AutoMessageAttr);
        }else{
            TradingAutoMessageAttr t=tradingAutoMessageAttrMapper.selectByPrimaryKey(AutoMessageAttr.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingAutoMessageAttrMapper.class,AutoMessageAttr.getId());
            this.tradingAutoMessageAttrMapper.updateByPrimaryKeySelective(AutoMessageAttr);
        }
    }

    @Override
    public List<TradingAutoMessageAttr> selectAutoMessageListByautoMessageId(Long autoMessageId,String Type) {
        TradingAutoMessageAttrExample example=new TradingAutoMessageAttrExample();
        TradingAutoMessageAttrExample.Criteria cr=example.createCriteria();
        cr.andAutomessageIdEqualTo(autoMessageId);
        cr.andTypeEqualTo(Type);
        List<TradingAutoMessageAttr> list= tradingAutoMessageAttrMapper.selectByExample(example);
        return list;
    }
}
