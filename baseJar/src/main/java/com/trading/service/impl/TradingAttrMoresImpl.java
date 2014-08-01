package com.trading.service.impl;

import com.base.database.trading.mapper.TradingAttrMoresMapper;
import com.base.database.trading.model.TradingAttrMores;
import com.base.database.trading.model.TradingAttrMoresExample;
import com.base.utils.common.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cz on 2014/7/24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingAttrMoresImpl implements com.trading.service.ITradingAttrMores {
    @Autowired
    private TradingAttrMoresMapper tradingAttrMoresMapper;

    @Override
    public void saveAttrMores(TradingAttrMores tradingAttrMores){
        this.tradingAttrMoresMapper.insert(tradingAttrMores);
    }

    @Override
    public TradingAttrMores toDAOPojo(String attrValue, String value) throws Exception {
        TradingAttrMores pojo = new TradingAttrMores();
        ObjectUtils.toInitPojoForInsert(pojo);
        pojo.setAttrValue(attrValue);
        pojo.setValue(value);
        return pojo;
    }

    /**
     *通过父节点ID删除数据
     * @param id
     */
    @Override
    public void deleteByParentId(Long id){
        TradingAttrMoresExample tame = new TradingAttrMoresExample();
        tame.createCriteria().andParentIdEqualTo(id);
        this.tradingAttrMoresMapper.deleteByExample(tame);
    }
}
