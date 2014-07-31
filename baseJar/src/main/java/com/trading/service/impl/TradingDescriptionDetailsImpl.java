package com.trading.service.impl;

import com.base.database.customtrading.mapper.DescriptionDetailsMapper;
import com.base.database.trading.mapper.TradingDescriptionDetailsMapper;
import com.base.database.trading.model.TradingDescriptionDetailsWithBLOBs;
import com.base.domains.querypojos.DescriptionDetailsWithBLOBsQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 商品描述模块
 * Created by cz on 2014/7/23.
 */
@Service
public class TradingDescriptionDetailsImpl implements com.trading.service.ITradingDescriptionDetails {
    @Autowired
    private TradingDescriptionDetailsMapper tradingDescriptionDetailsMapper;

    @Autowired
    private DescriptionDetailsMapper DescriptionDetailsMapper;

    @Override
    public void saveDescriptionDetails(TradingDescriptionDetailsWithBLOBs pojo){
        if(pojo.getId()==null){
            this.tradingDescriptionDetailsMapper.insert(pojo);
        }else{
            this.tradingDescriptionDetailsMapper.updateByPrimaryKeySelective(pojo);
        }
    }
    @Override
    public TradingDescriptionDetailsWithBLOBs toDAOPojo(String payInfo, String shippingInfo, String contactInfo, String guaranteeInfo, String feedbackInfo) throws Exception {
        TradingDescriptionDetailsWithBLOBs pojo = new TradingDescriptionDetailsWithBLOBs();
        pojo.setPayInfo(payInfo);
        pojo.setShippingInfo(shippingInfo);
        pojo.setContactInfo(contactInfo);
        pojo.setGuaranteeInfo(guaranteeInfo);
        pojo.setFeedbackInfo(feedbackInfo);
        ObjectUtils.toPojo(pojo);
        return pojo;
    }

    @Override
    public List<DescriptionDetailsWithBLOBsQuery> selectByDescriptionDetailsList(Map map,Page page) {
        return this.DescriptionDetailsMapper.selectByDescriptionDetailsList(map,page);
    }

    @Override
    public List<DescriptionDetailsWithBLOBsQuery> selectByDescriptionDetailsList(Map map) {
        Page page=new Page();
        page.setPageSize(10);
        return this.DescriptionDetailsMapper.selectByDescriptionDetailsList(map,page);
    }
}