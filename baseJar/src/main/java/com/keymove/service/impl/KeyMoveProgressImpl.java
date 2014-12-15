package com.keymove.service.impl;

import com.base.database.customtrading.mapper.KeyMoveProgressQueryMapper;
import com.base.database.trading.mapper.TradingProgressMapper;
import com.base.database.trading.model.TradingProgress;
import com.base.domains.querypojos.KeyMoveProgressQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/12/8.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class KeyMoveProgressImpl implements com.keymove.service.IKeyMoveProgress {

    @Autowired
    private KeyMoveProgressQueryMapper keyMoveProgressQueryMapper;
    @Autowired
    private TradingProgressMapper tradingProgressMapper;

    @Override
    public List<KeyMoveProgressQuery> selectByUserId(long userId){
        Map m = new HashMap();
        m.put("userId",userId);
        return this.keyMoveProgressQueryMapper.selectKeyMoveProgressList(m);
    }

    @Override
    public void saveProgress(TradingProgress tradingProgress){
        if(tradingProgress.getId()!=null){
            this.tradingProgressMapper.updateByPrimaryKeySelective(tradingProgress);
        }else{
            this.tradingProgressMapper.insertSelective(tradingProgress);
        }
    }

    @Override
    public void deleteById(Long id){
        this.tradingProgressMapper.deleteByPrimaryKey(id);
    }

    @Override
    public TradingProgress selectById(Long id){
        return this.tradingProgressMapper.selectByPrimaryKey(id);
    }

}
