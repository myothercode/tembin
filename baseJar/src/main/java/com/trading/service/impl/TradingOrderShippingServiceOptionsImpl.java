package com.trading.service.impl;

import com.base.database.trading.mapper.TradingOrderShippingServiceOptionsMapper;
import com.base.database.trading.model.TradingOrderShippingServiceOptions;
import com.base.database.trading.model.TradingOrderShippingServiceOptionsExample;
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
public class TradingOrderShippingServiceOptionsImpl implements com.trading.service.ITradingOrderShippingServiceOptions {

    @Autowired
    private TradingOrderShippingServiceOptionsMapper tradingOrderShippingServiceOptionsMapper;

    @Override
    public void saveOrderShippingServiceOptions(TradingOrderShippingServiceOptions OrderShippingServiceOptions) throws Exception {
        if(OrderShippingServiceOptions.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderShippingServiceOptions);
            tradingOrderShippingServiceOptionsMapper.insertSelective(OrderShippingServiceOptions);
        }else{
            TradingOrderShippingServiceOptions t=tradingOrderShippingServiceOptionsMapper.selectByPrimaryKey(OrderShippingServiceOptions.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderShippingServiceOptionsMapper.class,OrderShippingServiceOptions.getId(),"Synchronize");
            tradingOrderShippingServiceOptionsMapper.updateByPrimaryKeySelective(OrderShippingServiceOptions);
        }
    }

    @Override
    public List<TradingOrderShippingServiceOptions> selectOrderGetItemByShippingDetailsId(Long id) {
        TradingOrderShippingServiceOptionsExample shippingServiceOptionsExample=new TradingOrderShippingServiceOptionsExample();
        TradingOrderShippingServiceOptionsExample.Criteria cr=shippingServiceOptionsExample.createCriteria();
        cr.andShippingdetailsIdEqualTo(id);
        List<TradingOrderShippingServiceOptions> lists=tradingOrderShippingServiceOptionsMapper.selectByExample(shippingServiceOptionsExample);
        return lists;
    }

    @Override
    public void deleteOrderShippingServiceOptions(TradingOrderShippingServiceOptions OrderShippingServiceOptions) throws Exception {
        Long id=OrderShippingServiceOptions.getId();
        if(id!=null){
            tradingOrderShippingServiceOptionsMapper.deleteByPrimaryKey(id);
        }
    }
}
