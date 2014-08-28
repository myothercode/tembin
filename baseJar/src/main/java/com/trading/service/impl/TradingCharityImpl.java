package com.trading.service.impl;

import com.base.database.trading.mapper.TradingCharityMapper;
import com.base.database.trading.model.TradingCharity;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.Charity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cz on 2014/7/24.
 */
@Service
public class TradingCharityImpl implements com.trading.service.ITradingCharity {
    @Autowired
    private TradingCharityMapper tradingCharityMapper;

    @Override
    public void saveCharity(TradingCharity tradingCharity){
        this.tradingCharityMapper.insert(tradingCharity);
    }
    @Override
    public TradingCharity toDAOPojo(Charity charity) throws Exception {
        TradingCharity pojo = new TradingCharity();
        ObjectUtils.toInitPojoForInsert(pojo);
        ConvertPOJOUtil.convert(pojo,charity);
        return pojo;
    }
}
