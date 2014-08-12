package com.trading.service.impl;

import com.base.database.customtrading.mapper.VariationMapper;
import com.base.database.trading.mapper.TradingVariationMapper;
import com.base.database.trading.model.TradingVariation;
import com.base.database.trading.model.TradingVariationExample;
import com.base.domains.querypojos.VariationQuery;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.Variation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by cz on 2014/7/24.
 */
@Service
public class TradingVariationImpl implements com.trading.service.ITradingVariation {
    @Autowired
    private TradingVariationMapper tvm;

    @Autowired
    VariationMapper variationMapper;
    @Override
    public void saveVariation(TradingVariation pojo) throws Exception {
        if(pojo.getId()==null){
            ObjectUtils.toInitPojoForInsert(pojo);
            this.tvm.insert(pojo);
        }else{
            this.tvm.updateByPrimaryKeySelective(pojo);
        }

    }
    @Override
    public TradingVariation toDAOPojo(Variation var) throws Exception {
        TradingVariation pojo = new TradingVariation();
        ConvertPOJOUtil.convert(pojo,var);
        ConvertPOJOUtil.convert(pojo,var.getDiscountPriceInfo());
        return pojo;
    }

    @Override
    public List<VariationQuery> selectByParentId(Map m) throws Exception {
        return this.variationMapper.selectByExample(m);
    }
}

