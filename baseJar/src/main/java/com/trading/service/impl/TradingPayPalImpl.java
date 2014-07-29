package com.trading.service.impl;

import com.base.database.customtrading.mapper.PaypalMapper;
import com.base.database.trading.mapper.TradingPaypalMapper;
import com.base.database.trading.model.TradingPaypal;
import com.base.domains.querypojos.PaypalQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 付款模块
 * Created by cz on 2014/7/23.
 */
@Service
public class TradingPayPalImpl implements com.trading.service.ITradingPayPal {
    @Autowired
    private TradingPaypalMapper tradingPaypalMapper;

    @Autowired
    private PaypalMapper paypalMapper;


    @Override
    public void savePaypal(TradingPaypal tradingPaypal){
        if(tradingPaypal.getId()==null){
            this.tradingPaypalMapper.insertSelective(tradingPaypal);
        }else{
            this.tradingPaypalMapper.updateByPrimaryKey(tradingPaypal);
        }

    }
    @Override
    public TradingPaypal toDAOPojo(String payName, String site, String paypal, String paymentinstructions) throws Exception {
        TradingPaypal pojo = new TradingPaypal();
        ObjectUtils.toPojo(pojo);
        pojo.setPayName(payName);
        pojo.setSite(site);
        pojo.setPaypal(paypal);
        pojo.setPaymentinstructions(paymentinstructions);
        return pojo;
    }

    @Override
    public List<PaypalQuery> selectByPayPalList(Map map,Page page){
        return this.paypalMapper.selectByPayPalList(map,page);
    }

    @Override
    public PaypalQuery selectByPayPal(Map map){
        Page page=new Page();
        page.setCurrentPage(1);
        page.setPageSize(100);
        return this.paypalMapper.selectByPayPalList(map,page).get(0);
    }

}
