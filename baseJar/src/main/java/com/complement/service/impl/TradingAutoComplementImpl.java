package com.complement.service.impl;

import com.base.database.customtrading.mapper.AutoComplementMapper;
import com.base.database.customtrading.mapper.SystemLogQueryMapper;
import com.base.database.trading.mapper.TradingAutoComplementMapper;
import com.base.database.trading.model.TradingAutoComplement;
import com.base.database.trading.model.TradingAutoComplementExample;
import com.base.domains.querypojos.SystemLogQuery;
import com.base.mybatis.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/12/11.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingAutoComplementImpl implements com.complement.service.ITradingAutoComplement {

    @Autowired
    private TradingAutoComplementMapper tradingAutoComplementMapper;

    @Autowired
    private AutoComplementMapper autoComplementMapper;
    @Autowired
    private SystemLogQueryMapper systemLogQueryMapper;

    @Override
    public List<TradingAutoComplement> selectByList(Map m, Page page){
        return this.autoComplementMapper.selectAutoComplementList(m,page);
    }

    @Override
    public void saveAutoComplement(TradingAutoComplement tradingAutoComplement){
        if(tradingAutoComplement.getId()!=null){
            this.tradingAutoComplementMapper.updateByPrimaryKeySelective(tradingAutoComplement);
        }else{
            this.tradingAutoComplementMapper.insertSelective(tradingAutoComplement);
        }
    }
    @Override
    public int delAutoComplement(long id){
        return this.tradingAutoComplementMapper.deleteByPrimaryKey(id);
    }
    @Override
    public TradingAutoComplement selectById(long id){
        return this.tradingAutoComplementMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemLogQuery> selectLogList(Map m, Page page){
        return this.systemLogQueryMapper.selectLogList(m,page);
    }

    @Override
    public List<TradingAutoComplement> selectByEbayAccount(String ebayAccount){
        TradingAutoComplementExample tace = new TradingAutoComplementExample();
        tace.createCriteria().andEbayAccountEqualTo(ebayAccount).andCheckFlagEqualTo("0");
        return this.tradingAutoComplementMapper.selectByExample(tace);
    }
}
