package com.trading.service.impl;

import com.base.database.trading.mapper.TradingOrderSellingStatusMapper;
import com.base.database.trading.model.TradingOrderSellingStatus;
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
public class TradingOrderSellingStatusImpl implements com.trading.service.ITradingOrderSellingStatus {

    @Autowired
    private TradingOrderSellingStatusMapper tradingOrderSellingStatusMapper;

    @Override
    public void saveOrderSellingStatus(TradingOrderSellingStatus OrderSellingStatus) throws Exception {
        if(OrderSellingStatus.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderSellingStatus);
            tradingOrderSellingStatusMapper.insert(OrderSellingStatus);
        }else{
            TradingOrderSellingStatus t=tradingOrderSellingStatusMapper.selectByPrimaryKey(OrderSellingStatus.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderSellingStatusMapper.class,OrderSellingStatus.getId());
            tradingOrderSellingStatusMapper.updateByPrimaryKeySelective(OrderSellingStatus);
        }
    }
}
