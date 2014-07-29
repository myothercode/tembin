package com.trading.service.impl;

import com.base.database.customtrading.mapper.BuyerRequirementDetailsMapper;
import com.base.database.trading.mapper.TradingBuyerRequirementDetailsMapper;
import com.base.database.trading.model.TradingBuyerRequirementDetails;
import com.base.database.trading.model.TradingBuyerRequirementDetailsExample;
import com.base.database.trading.model.TradingDataDictionaryExample;
import com.base.domains.querypojos.BuyerRequirementDetailsQuery;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.BuyerRequirementDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.processing.SupportedSourceVersion;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 买家要求
 * Created by cz on 2014/7/23.
 */
@Service
public class TradingBuyerRequirementDetailsImpl implements com.trading.service.ITradingBuyerRequirementDetails {
    @Autowired
    private TradingBuyerRequirementDetailsMapper tradingBuyerRequirementDetailsMapper;

    @Autowired
    private BuyerRequirementDetailsMapper buyerRequirementDetailsMapper;

    @Override
    public void saveBuyerRequirementDetails(TradingBuyerRequirementDetails tradingBuyerRequirementDetails){
        if(tradingBuyerRequirementDetails.getId()==null) {
            this.tradingBuyerRequirementDetailsMapper.insertSelective(tradingBuyerRequirementDetails);
        }else{
            this.tradingBuyerRequirementDetailsMapper.updateByPrimaryKey(tradingBuyerRequirementDetails);
        }
    }
    @Override
    public TradingBuyerRequirementDetails toDAOPojo(BuyerRequirementDetails buyerRequirementDetails) throws Exception {
        TradingBuyerRequirementDetails pojo = new TradingBuyerRequirementDetails();
        ObjectUtils.toPojo(pojo);
        ConvertPOJOUtil.convert(pojo,buyerRequirementDetails);
        if(buyerRequirementDetails!=null){
            ConvertPOJOUtil.convert(pojo,buyerRequirementDetails.getMaximumBuyerPolicyViolations());
            ConvertPOJOUtil.convert(pojo,buyerRequirementDetails.getMaximumUnpaidItemStrikesInfo());
            ConvertPOJOUtil.convert(pojo,buyerRequirementDetails.getMaximumItemRequirements());
            ConvertPOJOUtil.convert(pojo,buyerRequirementDetails.getVerifiedUserRequirements());
        }
        return pojo;
    }

    @Override
    public List<BuyerRequirementDetailsQuery> selectTradingBuyerRequirementDetailsByList(Map m){
        return this.buyerRequirementDetailsMapper.selectByBuyer(m);
    }

}
