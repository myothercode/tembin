package com.trading.service.impl;

import com.base.database.trading.mapper.TradingVariationsMapper;
import com.base.database.trading.model.TradingVariations;
import com.base.database.trading.model.TradingVariationsExample;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.Variations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by cz on 2014/7/24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
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

    @Override
    public TradingVariations selectByParentId(Long id){
        TradingVariationsExample tve = new TradingVariationsExample();
        tve.createCriteria().andParentIdEqualTo(id);
        List<TradingVariations> litv = this.tvm.selectByExample(tve);
        if(litv.size()==0)
            return null;
        return litv.get(litv.size()-1);
    }
}
