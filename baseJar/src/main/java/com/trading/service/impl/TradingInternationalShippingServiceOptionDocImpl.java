package com.trading.service.impl;

import com.base.database.trading.mapper.TradingInternationalshippingserviceoptionDocMapper;
import com.base.database.trading.mapper.TradingInternationalshippingserviceoptionMapper;
import com.base.database.trading.model.*;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.InternationalShippingServiceOption;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingServiceAdditionalCost;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingServiceCost;
import com.trading.service.ITradingAttrMores;
import com.trading.service.ITradingDataDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cz on 2014/7/24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingInternationalShippingServiceOptionDocImpl implements com.trading.service.ITradingInternationalShippingServiceOptionDoc {

    @Autowired
    private TradingInternationalshippingserviceoptionDocMapper tradingInternationalshippingserviceoptionDocMapper;

    @Override
    public void saveTradingInternationalshippingserviceoptionDoc(List<TradingInternationalshippingserviceoptionDoc> litd){
        for(TradingInternationalshippingserviceoptionDoc td:litd){
            this.tradingInternationalshippingserviceoptionDocMapper.insertSelective(td);
        }
    }

    @Override
    public void delTradingInternationalshippingserviceoptionDoc(long docId){
        TradingInternationalshippingserviceoptionDocExample tde = new TradingInternationalshippingserviceoptionDocExample();
        tde.createCriteria().andDocIdEqualTo(docId);
        this.tradingInternationalshippingserviceoptionDocMapper.deleteByExample(tde);
    }

    @Override
    public List<TradingInternationalshippingserviceoptionDoc> selectByParentIdDocId(long docId){
        TradingInternationalshippingserviceoptionDocExample tde = new TradingInternationalshippingserviceoptionDocExample();
        tde.createCriteria().andDocIdEqualTo(docId);
        return this.tradingInternationalshippingserviceoptionDocMapper.selectByExample(tde);
    }
}
