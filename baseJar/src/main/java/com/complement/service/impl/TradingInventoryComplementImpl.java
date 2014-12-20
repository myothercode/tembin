package com.complement.service.impl;

import com.base.database.customtrading.mapper.ItemInventoryQueryMapper;
import com.base.database.trading.mapper.TradingInventoryComplementMapper;
import com.base.database.trading.mapper.TradingInventoryComplementMoreMapper;
import com.base.database.trading.model.TradingInventoryComplement;
import com.base.database.trading.model.TradingInventoryComplementMore;
import com.base.database.trading.model.TradingInventoryComplementMoreExample;
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
public class TradingInventoryComplementImpl implements com.complement.service.ITradingInventoryComplement {
    @Autowired
    private TradingInventoryComplementMapper tradingInventoryComplementMapper;
    @Autowired
    private TradingInventoryComplementMoreMapper tradingInventoryComplementMoreMapper;
    @Autowired
    private ItemInventoryQueryMapper itemInventoryQueryMapper;

    @Override
    public void saveInventoryComplement(TradingInventoryComplement tradingInventoryComplement,List<TradingInventoryComplementMore> liti){
        if(tradingInventoryComplement.getId()==null){
            this.tradingInventoryComplementMapper.insertSelective(tradingInventoryComplement);
        }else{
            this.tradingInventoryComplementMapper.updateByPrimaryKeySelective(tradingInventoryComplement);
        }
        this.delByParentId(tradingInventoryComplement.getId());
        for(TradingInventoryComplementMore tc:liti){
            tc.setParentId(tradingInventoryComplement.getId());
            this.tradingInventoryComplementMoreMapper.insertSelective(tc);
        }
    }

    @Override
    public void delByParentId(long parentId){
        TradingInventoryComplementMoreExample te = new TradingInventoryComplementMoreExample();
        te.createCriteria().andParentIdEqualTo(parentId);
        this.tradingInventoryComplementMoreMapper.deleteByExample(te);
    }
    @Override
    public void delByid(long id){
        this.tradingInventoryComplementMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delAllData(long id){
        TradingInventoryComplement tcm = this.selectById(id);
        this.delByParentId(tcm.getId());
        this.delByid(tcm.getId());
    }
    @Override
    public List<TradingInventoryComplement> selectByInventoryComplementList(Map map, Page page){
        return this.itemInventoryQueryMapper.selectInventoryComplementList(map,page);
    }

    @Override
    public TradingInventoryComplement selectById(long id){
        return this.tradingInventoryComplementMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TradingInventoryComplementMore> slectByParentId(long parentId){
        TradingInventoryComplementMoreExample tme = new TradingInventoryComplementMoreExample();
        tme.createCriteria().andParentIdEqualTo(parentId);
        return this.tradingInventoryComplementMoreMapper.selectByExample(tme);
    }
}
