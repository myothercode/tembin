package com.trading.service.impl;

import com.base.database.trading.mapper.TradingOrderGetItemMapper;
import com.base.database.trading.mapper.TradingPriceTrackingPricingRuleMapper;
import com.base.database.trading.model.TradingPriceTrackingPricingRule;
import com.base.database.trading.model.TradingPriceTrackingPricingRuleExample;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.trading.service.ITradingPriceTrackingPricingRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/14.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingPriceTrackingPricingRuleImpl implements ITradingPriceTrackingPricingRule {
    @Autowired
    private TradingPriceTrackingPricingRuleMapper tradingPriceTrackingPricingRuleMapper;
    @Override
    public void saveTradingPriceTrackingPricingRule(TradingPriceTrackingPricingRule tradingPriceTrackingPricingRule) throws Exception {
        if(tradingPriceTrackingPricingRule.getId()==null){
            ObjectUtils.toInitPojoForInsert(tradingPriceTrackingPricingRule);
            this.tradingPriceTrackingPricingRuleMapper.insertSelective(tradingPriceTrackingPricingRule);
        }else{
            TradingPriceTrackingPricingRule t=tradingPriceTrackingPricingRuleMapper.selectByPrimaryKey(tradingPriceTrackingPricingRule.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderGetItemMapper.class,tradingPriceTrackingPricingRule.getId());
            tradingPriceTrackingPricingRuleMapper.updateByPrimaryKeySelective(tradingPriceTrackingPricingRule);
        }
    }

    @Override
    public List<TradingPriceTrackingPricingRule> selectTradingPriceTrackingPricingRuleByAutoPricingId(Long autoPricingId) {
        TradingPriceTrackingPricingRuleExample example=new TradingPriceTrackingPricingRuleExample();
        TradingPriceTrackingPricingRuleExample.Criteria cr=example.createCriteria();
        cr.andAutopricingIdEqualTo(autoPricingId);
        List<TradingPriceTrackingPricingRule> list=tradingPriceTrackingPricingRuleMapper.selectByExample(example);
        return list;
    }

    @Override
    public void deleteTradingPriceTrackingPricingRule(TradingPriceTrackingPricingRule tradingPriceTrackingPricingRule) {
        if(tradingPriceTrackingPricingRule!=null&&tradingPriceTrackingPricingRule.getId()!=null){
            tradingPriceTrackingPricingRuleMapper.deleteByPrimaryKey(tradingPriceTrackingPricingRule.getId());
        }
    }

}
