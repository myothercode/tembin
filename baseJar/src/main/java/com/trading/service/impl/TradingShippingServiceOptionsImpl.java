package com.trading.service.impl;

import com.base.database.trading.mapper.TradingShippingserviceoptionsMapper;
import com.base.database.trading.model.TradingShippingserviceoptions;
import com.base.database.trading.model.TradingShippingserviceoptionsExample;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.ShippingServiceOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cz on 2014/7/24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingShippingServiceOptionsImpl implements com.trading.service.ITradingShippingServiceOptions {
    @Autowired
    private TradingShippingserviceoptionsMapper tsm;

    @Override
    public void saveShippingServiceOptions(TradingShippingserviceoptions pojo){
        this.tsm.insert(pojo);
    }

    @Override
    public TradingShippingserviceoptions toDAOPojo(ShippingServiceOptions sso) throws Exception {
        TradingShippingserviceoptions pojo = new TradingShippingserviceoptions();
        ObjectUtils.toInitPojoForInsert(pojo);
        ConvertPOJOUtil.convert(pojo,sso);
        pojo.setShippingservicecost(sso.getShippingServiceCost().getValue());
        pojo.setShippingserviceadditionalcost(sso.getShippingServiceAdditionalCost().getValue());
        pojo.setShippingsurcharge(sso.getShippingSurcharge().getValue());
        return pojo;
    }

    /**
     * 通过父ID删掉数据
     * @param id
     */
    @Override
    public void deleteByParentId(Long id){
        TradingShippingserviceoptionsExample tse = new TradingShippingserviceoptionsExample();
        tse.createCriteria().andParentIdEqualTo(id);
        this.tsm.deleteByExample(tse);
    }
}

