package com.trading.service.impl;

import com.base.database.trading.mapper.TradingGetDisputeMessageMapper;
import com.base.database.trading.model.TradingGetDisputeMessage;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 退货政策
 * Created by lq on 2014/7/29.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingGetDisputeMessageImpl implements com.trading.service.ITradingGetDisputeMessage {

    @Autowired
    private TradingGetDisputeMessageMapper tradingGetDisputeMessageMapper;

    @Override
    public void saveGetDisputeMessage(TradingGetDisputeMessage GetDisputeMessage) throws Exception {
        if(GetDisputeMessage.getId()==null){
            ObjectUtils.toInitPojoForInsert(GetDisputeMessage);
            tradingGetDisputeMessageMapper.insert(GetDisputeMessage);
        }else{
            TradingGetDisputeMessage t=tradingGetDisputeMessageMapper.selectByPrimaryKey(GetDisputeMessage.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingGetDisputeMessageMapper.class,GetDisputeMessage.getId());
            tradingGetDisputeMessageMapper.updateByPrimaryKeySelective(GetDisputeMessage);
        }
    }
}
