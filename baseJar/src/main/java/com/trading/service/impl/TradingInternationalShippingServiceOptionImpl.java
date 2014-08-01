package com.trading.service.impl;

import com.base.database.trading.mapper.TradingInternationalshippingserviceoptionMapper;
import com.base.database.trading.model.TradingInternationalshippingserviceoption;
import com.base.database.trading.model.TradingInternationalshippingserviceoptionExample;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.InternationalShippingServiceOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cz on 2014/7/24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingInternationalShippingServiceOptionImpl implements com.trading.service.ITradingInternationalShippingServiceOption {
    @Autowired
    private TradingInternationalshippingserviceoptionMapper tradingInternationalshippingserviceoptionMapper;

    @Override
    public void saveInternationalShippingServiceOption(TradingInternationalshippingserviceoption pojo){
        this.tradingInternationalshippingserviceoptionMapper.insert(pojo);
    }

    @Override
    public TradingInternationalshippingserviceoption toDAOPojo(InternationalShippingServiceOption isso) throws Exception {
        TradingInternationalshippingserviceoption pojo = new TradingInternationalshippingserviceoption();
        ObjectUtils.toInitPojoForInsert(pojo);
        ConvertPOJOUtil.convert(pojo,isso);
        pojo.setShippingservicecost(isso.getShippingServiceCost().getValue());
        pojo.setShippingservicepriority(isso.getShippingServicePriority().longValue());
        pojo.setShippingserviceadditionalcost(isso.getShippingServiceAdditionalCost().getValue());
        return pojo;
    }

    /**
     * 通过父ID删除数据
     * @param id
     */
    @Override
    public void deleteByParentId(Long id){
        TradingInternationalshippingserviceoptionExample tie = new TradingInternationalshippingserviceoptionExample();
        tie.createCriteria().andParentIdEqualTo(id);
        this.tradingInternationalshippingserviceoptionMapper.deleteByExample(tie);
    }
}
