package com.trading.service.impl;

import com.base.database.trading.mapper.TradingOrderReturnpolicyMapper;
import com.base.database.trading.model.TradingOrderReturnpolicy;
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
public class TradingOrderReturnpolicyImpl implements com.trading.service.ITradingOrderReturnpolicy {

    @Autowired
    private TradingOrderReturnpolicyMapper tradingOrderReturnpolicyMapper;

    @Override
    public void saveOrderReturnpolicy(TradingOrderReturnpolicy OrderReturnpolicy) throws Exception {
        if(OrderReturnpolicy.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderReturnpolicy);
            tradingOrderReturnpolicyMapper.insert(OrderReturnpolicy);
        }else{
            TradingOrderReturnpolicy t=tradingOrderReturnpolicyMapper.selectByPrimaryKey(OrderReturnpolicy.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderReturnpolicyMapper.class,OrderReturnpolicy.getId(),"Synchronize");
            tradingOrderReturnpolicyMapper.updateByPrimaryKeySelective(OrderReturnpolicy);
        }
    }
}
