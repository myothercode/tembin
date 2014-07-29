package com.trading.service.impl;

import com.base.database.trading.mapper.TradingAttrMoresMapper;
import com.base.database.trading.model.TradingAttrMores;
import com.base.utils.common.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cz on 2014/7/24.
 */
@Service
public class TradingAttrMoresImpl implements com.trading.service.ITradingAttrMores {
    @Autowired
    private TradingAttrMoresMapper tradingAttrMoresMapper;

    @Override
    public void saveAttrMores(TradingAttrMores tradingAttrMores){
        this.tradingAttrMoresMapper.insert(tradingAttrMores);
    }

    @Override
    public TradingAttrMores toDAOPojo(String attrValue, String value) throws Exception {
        TradingAttrMores pojo = new TradingAttrMores();
        ObjectUtils.toPojo(pojo);
        pojo.setAttrValue(attrValue);
        pojo.setValue(value);
        return pojo;
    }
}
