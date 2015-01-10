package com.trading.service.impl;

import com.base.database.customtrading.mapper.PriceTrackingAutoPricingMapper;
import com.base.database.trading.mapper.TradingAddItemMapper;
import com.base.database.trading.mapper.TradingOrderGetItemMapper;
import com.base.database.trading.mapper.TradingPriceTrackingAutoPricingMapper;
import com.base.database.trading.mapper.TradingReturnpolicyMapper;
import com.base.database.trading.model.*;
import com.base.domains.querypojos.PriceTrackingAutoPricingQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.trading.service.ITradingPriceTrackingAutoPricing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/14.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingPriceTrackingAutoPricingImpl implements ITradingPriceTrackingAutoPricing{
    @Autowired
    private TradingPriceTrackingAutoPricingMapper tradingPriceTrackingAutoPricingMapper;
    @Autowired
    private PriceTrackingAutoPricingMapper priceTrackingAutoPricingMapper;

    @Override
    public void savePriceTrackingAutoPricing(TradingPriceTrackingAutoPricing tradingPriceTrackingAutoPricing) throws Exception {
        if(tradingPriceTrackingAutoPricing.getId()==null){
            ObjectUtils.toInitPojoForInsert(tradingPriceTrackingAutoPricing);
            this.tradingPriceTrackingAutoPricingMapper.insertSelective(tradingPriceTrackingAutoPricing);
        }else{
            TradingPriceTrackingAutoPricing t=tradingPriceTrackingAutoPricingMapper.selectByPrimaryKey(tradingPriceTrackingAutoPricing.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderGetItemMapper.class,tradingPriceTrackingAutoPricing.getId(),"Synchronize");
            tradingPriceTrackingAutoPricingMapper.updateByPrimaryKeySelective(tradingPriceTrackingAutoPricing);
        }
    }

    @Override
    public TradingPriceTrackingAutoPricing selectPriceTrackingAutoPricingByListingDateId(Long listingDateId) {
        TradingPriceTrackingAutoPricingExample example=new TradingPriceTrackingAutoPricingExample();
        TradingPriceTrackingAutoPricingExample.Criteria cr=example.createCriteria();
        cr.andListingdateIdEqualTo(listingDateId);
        List<TradingPriceTrackingAutoPricing> list=tradingPriceTrackingAutoPricingMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }

    @Override
    public List<PriceTrackingAutoPricingQuery> selectPriceTrackingAutoPricingList(Map map, Page page) {
        return priceTrackingAutoPricingMapper.selectPriceTrackingAutoPricingList(map,page);
    }

    @Override
    public void deletePriceTrackingAutoPricing(TradingPriceTrackingAutoPricing tradingPriceTrackingAutoPricing) throws Exception {
        if(tradingPriceTrackingAutoPricing!=null&&tradingPriceTrackingAutoPricing.getId()!=null){
            tradingPriceTrackingAutoPricingMapper.deleteByPrimaryKey(tradingPriceTrackingAutoPricing.getId());
        }
    }

    @Override
    public TradingPriceTrackingAutoPricing selectPriceTrackingAutoPricingById(Long id) {
        return tradingPriceTrackingAutoPricingMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TradingPriceTrackingAutoPricing> selectPriceTrackingAutoPricings() {
        TradingPriceTrackingAutoPricingExample example=new TradingPriceTrackingAutoPricingExample();
        TradingPriceTrackingAutoPricingExample.Criteria cr=example.createCriteria();
        List<TradingPriceTrackingAutoPricing> list=tradingPriceTrackingAutoPricingMapper.selectByExample(example);
        return list;
    }
}
