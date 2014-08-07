package com.trading.service.impl;

import com.base.database.trading.mapper.TradingPublicLevelAttrMapper;
import com.base.database.trading.model.TradingItemAddressExample;
import com.base.database.trading.model.TradingPublicLevelAttr;
import com.base.database.trading.model.TradingPublicLevelAttrExample;
import com.base.utils.common.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cz on 2014/7/24.
 */
@Service
public class TradingPublicLevelAttrImpl implements com.trading.service.ITradingPublicLevelAttr {
    @Autowired
    private TradingPublicLevelAttrMapper tplam;

    @Override
    public void savePublicLevelAttr(TradingPublicLevelAttr pojo){
        this.tplam.insert(pojo);
    }

    @Override
    public TradingPublicLevelAttr toDAOPojo(String name, String value) throws Exception {
        TradingPublicLevelAttr pojo = new TradingPublicLevelAttr();
        ObjectUtils.toInitPojoForInsert(pojo);
        pojo.setValue(value);
        pojo.setName(name);
        return pojo;
    }

    @Override
    public void deleteByParentID(String name, Long id){
        TradingPublicLevelAttrExample tpae = new TradingPublicLevelAttrExample();
        TradingPublicLevelAttrExample.Criteria cri = tpae.createCriteria();
        if(id!=null&&!"".equals(id+"")){
            cri.andParentIdEqualTo(id);
        }
        if(name!=null&&!"".equals(name)){
            cri.andNameEqualTo(name);
        }

        this.tplam.deleteByExample(tpae);
    }

    @Override
    public List<TradingPublicLevelAttr> selectByParentId(String name, Long id){
        TradingPublicLevelAttrExample tpae = new TradingPublicLevelAttrExample();
        tpae.createCriteria().andParentIdEqualTo(id).andNameEqualTo(name);
        return this.tplam.selectByExample(tpae);
    }
}
