package com.trading.service.impl;

import com.base.database.trading.mapper.TradingShippingserviceoptionsMapper;
import com.base.database.trading.model.*;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.ShippingServiceOptions;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingServiceAdditionalCost;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingServiceCost;
import com.base.xmlpojo.trading.addproduct.attrclass.ShippingSurcharge;
import com.trading.service.ITradingDataDictionary;
import com.trading.service.ITradingShippingServiceOptionsDoc;
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
    @Autowired
    private ITradingDataDictionary iTradingDataDictionary;
    @Autowired
    private ITradingShippingServiceOptionsDoc iTradingShippingServiceOptionsDoc;
    @Override
    public void saveShippingServiceOptions(TradingShippingserviceoptions pojo) throws Exception {
        if(pojo.getId()==null) {
            ObjectUtils.toInitPojoForInsert(pojo);
            this.tsm.insert(pojo);
        }else{
            this.tsm.updateByPrimaryKeySelective(pojo);
        }
    }

    @Override
    public TradingShippingserviceoptions toDAOPojo(ShippingServiceOptions sso) throws Exception {
        TradingShippingserviceoptions pojo = new TradingShippingserviceoptions();

        ConvertPOJOUtil.convert(pojo,sso);
        if(sso.getShippingServiceCost()!=null) {
            pojo.setShippingservicecost(sso.getShippingServiceCost().getValue());
        }else{
            pojo.setShippingservicecost(0.00);
        }
        if(sso.getShippingServiceAdditionalCost()!=null) {
            pojo.setShippingserviceadditionalcost(sso.getShippingServiceAdditionalCost().getValue());
        }else{
            pojo.setShippingserviceadditionalcost(0.00);
        }
        if(sso.getShippingSurcharge()!=null) {
            pojo.setShippingsurcharge(sso.getShippingSurcharge().getValue());
        }else{
            pojo.setShippingsurcharge(0.00);
        }
        return pojo;
    }
    @Override
    public List<TradingShippingserviceoptions> selectByParentId(Long parentid){
        TradingShippingserviceoptionsExample tse = new TradingShippingserviceoptionsExample();
        tse.createCriteria().andParentIdEqualTo(parentid);
        return this.tsm.selectByExample(tse);
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
    public List<ShippingServiceOptions> toXmlPojo(Long id,TradingShippingdetails tradingShippingdetails,Long docId) throws Exception {
        List<ShippingServiceOptions> lisso = new ArrayList();
        List<TradingShippingserviceoptionsDoc> lidoc = this.iTradingShippingServiceOptionsDoc.slectByParentIdDocId(docId);
        TradingDataDictionary tdd = this.iTradingDataDictionary.selectDictionaryByID(Long.parseLong(tradingShippingdetails.getSite()));
        if(lidoc==null||lidoc.size()==0){
            TradingShippingserviceoptionsExample tse = new TradingShippingserviceoptionsExample();
            tse.createCriteria().andParentIdEqualTo(id);
            List<TradingShippingserviceoptions> litsso = this.tsm.selectByExample(tse);
            if(litsso!=null&& litsso.size()>0){
                for(TradingShippingserviceoptions tsso : litsso){
                    ShippingServiceOptions sso = new ShippingServiceOptions();
                    ConvertPOJOUtil.convert(sso,tsso);
                    sso.setShippingService(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(sso.getShippingService())).getValue());
                    sso.setShippingServiceCost(new ShippingServiceCost(tdd.getValue1(),tsso.getShippingservicecost()));
                    sso.setShippingServiceAdditionalCost(new ShippingServiceAdditionalCost(tdd.getValue1(),tsso.getShippingserviceadditionalcost()));
                    if(tsso.getShippingsurcharge()!=null&&tsso.getShippingsurcharge()!=0){
                        sso.setShippingSurcharge(new ShippingSurcharge(tdd.getValue1(),tsso.getShippingsurcharge()));
                    }
                    lisso.add(sso);
                }
            }
        }else{
            for(TradingShippingserviceoptionsDoc doc:lidoc){
                ShippingServiceOptions sso = new ShippingServiceOptions();
                ConvertPOJOUtil.convert(sso,doc);
                sso.setShippingService(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(sso.getShippingService())).getValue());
                sso.setShippingServiceCost(new ShippingServiceCost(tdd.getValue1(),doc.getShippingservicecost()));
                sso.setShippingServiceAdditionalCost(new ShippingServiceAdditionalCost(tdd.getValue1(),doc.getShippingserviceadditionalcost()));
                if(doc.getShippingsurcharge()!=null&&doc.getShippingsurcharge()!=0){
                    sso.setShippingSurcharge(new ShippingSurcharge(tdd.getValue1(),doc.getShippingsurcharge()));
                }
                if(sso.getShippingServiceCost().getValue()>0||sso.getShippingServiceAdditionalCost().getValue()>0){
                    sso.setFreeShipping(false);
                }
                lisso.add(sso);
            }
        }
        return lisso;

    }
}

