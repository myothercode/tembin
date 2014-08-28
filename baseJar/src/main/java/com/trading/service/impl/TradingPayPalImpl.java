package com.trading.service.impl;

import com.base.database.customtrading.mapper.PaypalMapper;
import com.base.database.trading.mapper.TradingPaypalMapper;
import com.base.database.trading.model.TradingPaypal;
import com.base.domains.querypojos.PaypalQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void savePaypal(TradingPaypal tradingPaypal) throws Exception {
        if(tradingPaypal.getId()==null){
            ObjectUtils.toInitPojoForInsert(tradingPaypal);
            this.tradingPaypalMapper.insertSelective(tradingPaypal);
        }else{
            TradingPaypal t=tradingPaypalMapper.selectByPrimaryKey(tradingPaypal.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingPaypalMapper.class,tradingPaypal.getId());
            this.tradingPaypalMapper.updateByPrimaryKey(tradingPaypal);
        }

    }
    @Override
    public TradingPaypal toDAOPojo(String payName, String site, String paypal, String paymentinstructions) throws Exception {
        TradingPaypal pojo = new TradingPaypal();
        ObjectUtils.toInitPojoForInsert(pojo);
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

    @Override
    public TradingPaypal selectById(Long id){
        return this.tradingPaypalMapper.selectByPrimaryKey(id);
    }

}
