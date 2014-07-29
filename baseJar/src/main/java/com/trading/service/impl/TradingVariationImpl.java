package com.trading.service.impl;

import com.base.database.trading.mapper.TradingVariationMapper;
import com.base.database.trading.model.TradingVariation;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.Variation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cz on 2014/7/24.
 */
@Service
public class TradingVariationImpl {
    @Autowired
    private TradingVariationMapper tvm;

    public void saveVariation(TradingVariation pojo){
        this.tvm.insert(pojo);
    }

    public TradingVariation toDAOPojo(Variation var) throws Exception {
        TradingVariation pojo = new TradingVariation();
        ObjectUtils.toPojo(pojo);
        ConvertPOJOUtil.convert(pojo,var);
        ConvertPOJOUtil.convert(pojo,var.getDiscountPriceInfo());
        return pojo;
    }
}

