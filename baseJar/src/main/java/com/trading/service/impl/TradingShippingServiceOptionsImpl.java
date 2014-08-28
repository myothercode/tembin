package com.trading.service.impl;

import com.base.database.trading.mapper.TradingShippingserviceoptionsMapper;
import com.base.database.trading.model.TradingShippingserviceoptions;
import com.base.database.trading.model.TradingShippingserviceoptionsExample;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.ShippingServiceOptions;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingServiceAdditionalCost;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingServiceCost;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingSurcharge;
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

    @Override
    public List<ShippingServiceOptions> toXmlPojo(Long id) throws Exception {
        List<ShippingServiceOptions> lisso = new ArrayList();
        TradingShippingserviceoptionsExample tse = new TradingShippingserviceoptionsExample();
        tse.createCriteria().andParentIdEqualTo(id);
        List<TradingShippingserviceoptions> litsso = this.tsm.selectByExample(tse);
        if(litsso!=null&& litsso.size()>0){
            for(TradingShippingserviceoptions tsso : litsso){
                ShippingServiceOptions sso = new ShippingServiceOptions();
                ConvertPOJOUtil.convert(sso,tsso);
                sso.setShippingService(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(sso.getShippingService())).getValue());
                sso.setShippingServiceCost(new ShippingServiceCost("USD",tsso.getShippingservicecost()));
                sso.setShippingServiceAdditionalCost(new ShippingServiceAdditionalCost("USD",tsso.getShippingserviceadditionalcost()));
                if(tsso.getShippingsurcharge()!=null&&tsso.getShippingsurcharge()!=0){
                    sso.setShippingSurcharge(new ShippingSurcharge("USD",tsso.getShippingsurcharge()));
                }
                lisso.add(sso);
            }
        }
        return lisso;

    }
}

