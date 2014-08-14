package com.trading.service.impl;

import com.base.database.trading.mapper.TradingAddItemMapper;
import com.base.database.trading.model.TradingAddItem;
import com.base.database.trading.model.TradingAddItemExample;
import com.base.utils.common.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrtor on 2014/8/14.
 */
@Service
public class TradingAddItemImpl implements com.trading.service.ITradingAddItem {
    @Autowired
    private TradingAddItemMapper tradingAddItemMapper;

    @Override
    public void saveAddItem(TradingAddItem tradingAddItem) throws Exception {
        if(tradingAddItem.getId()==null){
            ObjectUtils.toInitPojoForInsert(tradingAddItem);
            this.tradingAddItemMapper.insert(tradingAddItem);
        }else{
            this.tradingAddItemMapper.updateByPrimaryKeySelective(tradingAddItem);
        }
    }

    @Override
    public TradingAddItem selectParentId(Long id){
        TradingAddItemExample tae = new TradingAddItemExample();
        tae.createCriteria().andParentIdEqualTo(id);
        return this.tradingAddItemMapper.selectByExample(tae).get(0);
    }

}
