package com.trading.service.impl;

import com.base.database.trading.mapper.TradingOrderSellerMapper;
import com.base.database.trading.model.TradingOrderSeller;
import com.base.database.trading.model.TradingOrderSellerExample;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 退货政策
 * Created by lq on 2014/7/29.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingOrderSellerImpl implements com.trading.service.ITradingOrderSeller {

    @Autowired
    private TradingOrderSellerMapper tradingOrderSellerMapper;

    @Override
    public void saveOrderSeller(TradingOrderSeller OrderSeller) throws Exception {
        if(OrderSeller.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderSeller);
            tradingOrderSellerMapper.insert(OrderSeller);
        }else{
            TradingOrderSeller t=tradingOrderSellerMapper.selectByPrimaryKey(OrderSeller.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderSellerMapper.class,OrderSeller.getId());
            tradingOrderSellerMapper.updateByPrimaryKeySelective(OrderSeller);
        }
    }

    @Override
    public List<TradingOrderSeller> selectOrderGetItemById(Long Id) {
        TradingOrderSellerExample sellerExample=new TradingOrderSellerExample();
        TradingOrderSellerExample.Criteria cr=sellerExample.createCriteria();
        cr.andIdEqualTo(Id);
        List<TradingOrderSeller> lists=tradingOrderSellerMapper.selectByExample(sellerExample);
        return lists;
    }
}
