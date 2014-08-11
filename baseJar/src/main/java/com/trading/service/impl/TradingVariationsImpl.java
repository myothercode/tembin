package com.trading.service.impl;

import com.base.database.trading.mapper.TradingVariationsMapper;
import com.base.database.trading.model.TradingVariations;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.Variations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cz on 2014/7/24.
 */
@Service
public class TradingVariationsImpl implements com.trading.service.ITradingVariations {

    @Autowired
    private TradingVariationsMapper tvm;

    @Override
    public void saveVariations(TradingVariations pojo) throws Exception {
        if(pojo.getId()==null){
            ObjectUtils.toInitPojoForInsert(pojo);
            this.tvm.insert(pojo);
        }else{
            this.tvm.updateByPrimaryKeySelective(pojo);
        }

    }
    @Override
    public TradingVariations toDAOPojo(Variations var) throws Exception {
        TradingVariations pojo = new TradingVariations();
        return pojo;
    }
}
