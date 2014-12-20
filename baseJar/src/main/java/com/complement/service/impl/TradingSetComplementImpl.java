package com.complement.service.impl;

import com.base.database.customtrading.mapper.SetComplementQueryMapper;
import com.base.database.trading.mapper.TradingSetComplementMapper;
import com.base.database.trading.model.TradingSetComplement;
import com.base.database.trading.model.TradingSetComplementExample;
import com.base.domains.querypojos.SetComplementQuery;
import com.base.mybatis.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/12/17.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingSetComplementImpl implements com.complement.service.ITradingSetComplement {
    @Autowired
    private TradingSetComplementMapper tradingSetComplementMapper;

    @Autowired
    private SetComplementQueryMapper setComplementQueryMapper;
    @Override
    public void saveTradingSetComplement(TradingSetComplement tradingSetComplement){
        if(tradingSetComplement.getId()==null){
            this.tradingSetComplementMapper.insertSelective(tradingSetComplement);
        }else{
            this.tradingSetComplementMapper.updateByPrimaryKeySelective(tradingSetComplement);
        }
    }

    @Override
    public TradingSetComplement selectById(long id){
        return this.tradingSetComplementMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delTradingSetComplement(long id){
        this.tradingSetComplementMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<SetComplementQuery> selectBySetComplementList(Map map,Page page){
        return this.setComplementQueryMapper.selectSetComplementByList(map,page);
    }
    @Override
    public TradingSetComplement selectByEbayId(long ebayid){
        TradingSetComplementExample tsce = new TradingSetComplementExample();
        tsce.createCriteria().andEbayIdEqualTo(ebayid);
        List<TradingSetComplement> listc= this.tradingSetComplementMapper.selectByExample(tsce);
        if(listc!=null&&listc.size()>0){
            return listc.get(0);
        }else{
            return null;
        }
    }

    @Override
    public TradingSetComplement selectByEbayAccount(String ebayAccount){
        TradingSetComplementExample tsce = new TradingSetComplementExample();
        tsce.createCriteria().andEbayAccountEqualTo(ebayAccount);
        List<TradingSetComplement> listc= this.tradingSetComplementMapper.selectByExample(tsce);
        if(listc!=null&&listc.size()>0){
            return listc.get(0);
        }else{
            return null;
        }
    }
    @Override
    public void delSetEbayComplement(long id){
        this.tradingSetComplementMapper.deleteByPrimaryKey(id);
    }
}
