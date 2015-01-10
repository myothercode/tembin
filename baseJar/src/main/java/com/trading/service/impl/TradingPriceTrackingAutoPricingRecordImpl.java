package com.trading.service.impl;

import com.base.database.customtrading.mapper.PriceTrackingAutoPricingMapper;
import com.base.database.customtrading.mapper.PriceTrackingAutoPricingRecordMapper;
import com.base.database.trading.mapper.TradingOrderGetItemMapper;
import com.base.database.trading.mapper.TradingPriceTrackingAutoPricingMapper;
import com.base.database.trading.mapper.TradingPriceTrackingAutoPricingRecordMapper;
import com.base.database.trading.model.TradingPriceTrackingAutoPricing;
import com.base.database.trading.model.TradingPriceTrackingAutoPricingExample;
import com.base.database.trading.model.TradingPriceTrackingAutoPricingRecord;
import com.base.domains.querypojos.PriceTrackingAutoPricingQuery;
import com.base.domains.querypojos.PriceTrackingAutoPricingRecordQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.trading.service.ITradingPriceTrackingAutoPricing;
import com.trading.service.ITradingPriceTrackingAutoPricingRecord;
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
public class TradingPriceTrackingAutoPricingRecordImpl implements ITradingPriceTrackingAutoPricingRecord{

    @Autowired
    private PriceTrackingAutoPricingRecordMapper priceTrackingAutoPricingRecordMapper;

    @Autowired
    private TradingPriceTrackingAutoPricingRecordMapper tradingPriceTrackingAutoPricingRecordMapper;

    @Override
    public List<PriceTrackingAutoPricingRecordQuery> selectPriceTrackingAutoPricingRecordList(Map map, Page page) {
        return priceTrackingAutoPricingRecordMapper.selectPriceTrackingAutoPricingRecordList(map,page);
    }

    @Override
    public void savePriceTrackingAutoPricingRecord(TradingPriceTrackingAutoPricingRecord tradingPriceTrackingAutoPricingRecord) throws Exception {
        if(tradingPriceTrackingAutoPricingRecord.getId()==null){
            ObjectUtils.toInitPojoForInsert(tradingPriceTrackingAutoPricingRecord);
            this.tradingPriceTrackingAutoPricingRecordMapper.insertSelective(tradingPriceTrackingAutoPricingRecord);
        }else{
            TradingPriceTrackingAutoPricingRecord t=tradingPriceTrackingAutoPricingRecordMapper.selectByPrimaryKey(tradingPriceTrackingAutoPricingRecord.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderGetItemMapper.class,tradingPriceTrackingAutoPricingRecord.getId());
            tradingPriceTrackingAutoPricingRecordMapper.updateByPrimaryKeySelective(tradingPriceTrackingAutoPricingRecord);
        }
    }
}
