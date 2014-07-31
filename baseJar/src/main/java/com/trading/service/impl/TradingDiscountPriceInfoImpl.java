package com.trading.service.impl;

import com.base.database.customtrading.mapper.DiscountpriceinfoMapper;
import com.base.database.trading.mapper.TradingDiscountpriceinfoMapper;
import com.base.database.trading.model.TradingDiscountpriceinfo;
import com.base.domains.querypojos.DiscountpriceinfoQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.DiscountPriceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/23.
 */
@Service
public class TradingDiscountPriceInfoImpl implements com.trading.service.ITradingDiscountPriceInfo {
    @Autowired
    private TradingDiscountpriceinfoMapper tradingDiscountpriceinfoMapper;
    @Autowired
    private DiscountpriceinfoMapper discountpriceinfoMapper;

    @Override
    public void saveDiscountpriceinfo(TradingDiscountpriceinfo pojo){
        if(ObjectUtils.isLogicalNull(pojo.getId())) {
            this.tradingDiscountpriceinfoMapper.insertSelective(pojo);
        }else{
            this.tradingDiscountpriceinfoMapper.updateByPrimaryKey(pojo);
        }
    }

    @Override
    public TradingDiscountpriceinfo toDAOPojo(DiscountPriceInfo discountPriceInfo) throws Exception {
        TradingDiscountpriceinfo pojo = new TradingDiscountpriceinfo();
        ObjectUtils.toPojo(pojo);
        ConvertPOJOUtil.convert(pojo,discountPriceInfo);
        return pojo;
    }

    @Override
    public List<DiscountpriceinfoQuery> selectByDiscountpriceinfo(Map map,Page page){
        return this.discountpriceinfoMapper.selectByDiscountpriceinfoList(map,page);
    }

    @Override
    public List<DiscountpriceinfoQuery> selectByDiscountpriceinfo(Map map){
        Page page=new Page();
        page.setPageSize(10);
        page.setPageSize(1);
        return this.discountpriceinfoMapper.selectByDiscountpriceinfoList(map,page);
    }

}
