package com.trading.service.impl;

import com.base.database.trading.mapper.TradingStorefrontMapper;
import com.base.database.trading.model.TradingStorefront;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.Storefront;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cz on 2014/7/24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingStorefrontImpl implements com.trading.service.ITradingStorefront {
    @Autowired
    private TradingStorefrontMapper tsm;

    @Override
    public void saveStorefront(TradingStorefront pojo){
        this.tsm.insert(pojo);
    }

    @Override
    public TradingStorefront toDAOPojo(Storefront sf) throws Exception {
        TradingStorefront pojo = new TradingStorefront();
        ObjectUtils.toInitPojoForInsert(pojo);
        ConvertPOJOUtil.convert(pojo,sf);
        return pojo;
    }
}
