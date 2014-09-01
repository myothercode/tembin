package com.trading.service.impl;

import com.base.database.trading.mapper.TradingReseCategoryMapper;
import com.base.database.trading.model.TradingReseCategory;
import com.base.database.trading.model.TradingReseCategoryExample;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/29.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingReseCategoryImpl implements com.trading.service.ITradingReseCategory {
    @Autowired
    private TradingReseCategoryMapper tradingReseCategoryMapper;
    @Override
    public void saveTradingReseCategory(TradingReseCategory tradingReseCategory) throws Exception {
        Map m = new HashMap();
        m.put("key",tradingReseCategory.getCategoryKey());
        List<TradingReseCategory> lit = this.selectByList(m);
        if(lit!=null&&lit.size()>0){
            TradingReseCategory trc= lit.get(0);
            ConvertPOJOUtil.convert(tradingReseCategory,trc);
            this.tradingReseCategoryMapper.updateByPrimaryKeySelective(tradingReseCategory);
        }else{
            this.tradingReseCategoryMapper.insertSelective(tradingReseCategory);
        }
    }

    @Override
    public List<TradingReseCategory> selectByList(Map map){
        TradingReseCategoryExample trce = new TradingReseCategoryExample();
        TradingReseCategoryExample.Criteria c = trce.createCriteria();
        if(map.get("key")!=null){
            c.andCategoryKeyEqualTo(map.get("key").toString());
        }
        if(map.get("categoryId")!=null){
            c.andCategoryIdEqualTo(map.get("categoryId").toString());
        }

        return this.tradingReseCategoryMapper.selectByExample(trce);
    }


}
