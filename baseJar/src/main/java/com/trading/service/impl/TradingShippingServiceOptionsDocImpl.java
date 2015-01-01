package com.trading.service.impl;

import com.base.database.trading.mapper.TradingShippingserviceoptionsDocMapper;
import com.base.database.trading.model.*;
import com.trading.service.ITradingShippingServiceOptionsDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by cz on 2014/7/24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingShippingServiceOptionsDocImpl implements ITradingShippingServiceOptionsDoc {

    @Autowired
    private TradingShippingserviceoptionsDocMapper tradingShippingserviceoptionsDocMapper;

    @Override
    public void saveTradingShippingserviceoptionsDoc(List<TradingShippingserviceoptionsDoc> lidoc){
        for(TradingShippingserviceoptionsDoc doc:lidoc){
            this.tradingShippingserviceoptionsDocMapper.insertSelective(doc);
        }
    }

    @Override
    public void delTradingShippingserviceoptionsDoc(long docId){
        TradingShippingserviceoptionsDocExample tdoc = new TradingShippingserviceoptionsDocExample();
        tdoc.createCriteria().andDocIdEqualTo(docId);
        this.tradingShippingserviceoptionsDocMapper.deleteByExample(tdoc);
    }

    @Override
    public List<TradingShippingserviceoptionsDoc> slectByParentIdDocId(long docId){
        TradingShippingserviceoptionsDocExample tdoc = new TradingShippingserviceoptionsDocExample();
        tdoc.createCriteria().andDocIdEqualTo(docId);
        return this.tradingShippingserviceoptionsDocMapper.selectByExample(tdoc);
    }

}

