package com.trading.service.impl;

import com.base.database.trading.mapper.TradingVariationsMapper;
import com.base.database.trading.model.TradingVariations;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.Variations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cz on 2014/7/24.
 */
@Service
public class TradingVariationsImpl {
    @Autowired
    private TradingVariationsMapper tvm;

    public void saveVariations(TradingVariations pojo){
        this.tvm.insert(pojo);
    }

    public TradingVariations toDAOPojo(Variations var) throws Exception {
        TradingVariations pojo = new TradingVariations();
        ObjectUtils.toPojo(pojo);
        return pojo;
    }
}
