package com.trading.service.impl;

import com.base.database.customtrading.mapper.TablePriceMapper;
import com.base.database.trading.mapper.TradingListingAmendMapper;
import com.base.database.trading.mapper.TradingTablePriceMapper;
import com.base.database.trading.model.TradingListingAmend;
import com.base.database.trading.model.TradingListingAmendExample;
import com.base.database.trading.model.TradingTablePrice;
import com.base.database.trading.model.TradingTablePriceExample;
import com.base.domains.querypojos.TablePriceQuery;
import com.base.mybatis.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/9/24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingTablePriceImpl implements com.trading.service.ITradingTablePrice {
    @Autowired
    private TradingTablePriceMapper tradingTablePriceMapper;
    @Autowired
    public TablePriceMapper tablePriceMapper;

    @Override
    public void saveTablePrice(TradingTablePrice tradingTablePrice){
        if(tradingTablePrice.getId()==null){
            this.tradingTablePriceMapper.insertSelective(tradingTablePrice);
        }else{
            this.tradingTablePriceMapper.updateByPrimaryKey(tradingTablePrice);
        }

    }

    @Override
    public void saveTablePriceList(List<TradingTablePrice> li){
        for(TradingTablePrice ttp:li){
            this.saveTablePrice(ttp);
        }
    }

    @Override
    public List<TablePriceQuery> selectByList(Map map, Page page){
        return this.tablePriceMapper.selectByList(map,page);
    }

    @Override
    public TradingTablePrice selectById(Long id){
        return this.tradingTablePriceMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TradingTablePrice> selectByList(String sku,String ebayAccount){
        TradingTablePriceExample ttpe = new TradingTablePriceExample();
        ttpe.createCriteria().andSkuEqualTo(sku).andEbayAccountEqualTo(ebayAccount);
        return this.tradingTablePriceMapper.selectByExample(ttpe);
    }
}
