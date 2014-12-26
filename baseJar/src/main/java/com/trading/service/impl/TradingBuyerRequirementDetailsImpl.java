package com.trading.service.impl;

import com.base.database.customtrading.mapper.BuyerRequirementDetailsMapper;
import com.base.database.trading.mapper.TradingBuyerRequirementDetailsMapper;
import com.base.database.trading.mapper.TradingDescriptionDetailsMapper;
import com.base.database.trading.model.TradingBuyerRequirementDetails;
import com.base.domains.querypojos.BuyerRequirementDetailsQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.base.xmlpojo.trading.addproduct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 买家要求
 * Created by cz on 2014/7/23.
 */
@Service
@Transactional(rollbackFor = Exception.class)
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
            TradingBuyerRequirementDetails t=tradingBuyerRequirementDetailsMapper.selectByPrimaryKey(tradingBuyerRequirementDetails.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingDescriptionDetailsMapper.class,tradingBuyerRequirementDetails.getId());
            if(tradingBuyerRequirementDetails.getCheckFlag()==null) {
                tradingBuyerRequirementDetails.setCheckFlag(t.getCheckFlag());
            }
            this.tradingBuyerRequirementDetailsMapper.updateByPrimaryKeySelective(tradingBuyerRequirementDetails);
        }
    }
    @Override
    public TradingBuyerRequirementDetails toDAOPojo(BuyerRequirementDetails buyerRequirementDetails) throws Exception {
        TradingBuyerRequirementDetails pojo = new TradingBuyerRequirementDetails();
        ObjectUtils.toInitPojoForInsert(pojo);
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
    public List<BuyerRequirementDetailsQuery> selectTradingBuyerRequirementDetailsByList(Map m,Page page){
        return this.buyerRequirementDetailsMapper.selectByBuyer(m,page);
    }

    @Override
    public List<BuyerRequirementDetailsQuery> selectTradingBuyerRequirementDetailsByList(Map m){
        Page page=new Page();
        page.setPageSize(10);
        page.setCurrentPage(1);
        return this.buyerRequirementDetailsMapper.selectByBuyer(m,page);
    }

    @Override
    public TradingBuyerRequirementDetails selectById(Long id){
        return this.tradingBuyerRequirementDetailsMapper.selectByPrimaryKey(id);
    }
    @Override
    public BuyerRequirementDetails toXmlPojo(Long id){
        BuyerRequirementDetails brd = null;
        TradingBuyerRequirementDetails tbrd = this.selectById(id);
        if(tbrd!=null){
            brd = new BuyerRequirementDetails();
            if(tbrd.getPolicyPeriod()!=null&&tbrd.getPolicyCount()!=null){
                MaximumBuyerPolicyViolations mbpv = new MaximumBuyerPolicyViolations();
                mbpv.setPeriod(tbrd.getPolicyPeriod());
                mbpv.setCount(tbrd.getPolicyCount().intValue());
                brd.setMaximumBuyerPolicyViolations(mbpv);
            }
            if(tbrd.getUnpaidPeriod()!=null&&tbrd.getUnpaidCount()!=null){
                MaximumUnpaidItemStrikesInfo muisi = new MaximumUnpaidItemStrikesInfo();
                muisi.setCount(tbrd.getUnpaidCount().intValue());
                muisi.setPeriod(tbrd.getUnpaidPeriod());
                brd.setMaximumUnpaidItemStrikesInfo(muisi);
            }
            if(tbrd.getVerifiedFlag()!=null&&tbrd.getVerifiedFeedbackscore()!=null){
                VerifiedUserRequirements vur = new VerifiedUserRequirements();
                vur.setVerifiedUser( false);
                vur.setMinimumFeedbackScore(tbrd.getFeedbackscore().intValue());
                brd.setVerifiedUserRequirements(vur);
            }
            MaximumItemRequirements mir = new MaximumItemRequirements();
            if(tbrd.getMaximumitemcount()!=null) {
                mir.setMaximumItemCount(tbrd.getMaximumitemcount().intValue());
            }
            if(tbrd.getFeedbackscore()!=null) {
                mir.setMinimumFeedbackScore(tbrd.getFeedbackscore().intValue());
            }
            if(tbrd.getMaximumitemcount()!=null&&tbrd.getFeedbackscore()!=null){
                brd.setMaximumItemRequirements(mir);
            }
            if(tbrd.getLinkedpaypalaccount()!=null){
                brd.setLinkedPayPalAccount(tbrd.getLinkedpaypalaccount().equals("1")?true:false);
            }
            if(tbrd.getShiptoregistrationcountry()!=null){
                brd.setShipToRegistrationCountry(tbrd.getShiptoregistrationcountry().equals("0")?true:false);
            }
            if(tbrd.getZerofeedbackscore()!=null){
                brd.setZeroFeedbackScore(tbrd.getZerofeedbackscore().equals("1")?true:false);
            }
            if(tbrd.getMinimumfeedbackscore()!=null){
                brd.setMinimumFeedbackScore(tbrd.getMinimumfeedbackscore().intValue());
            }
        }
        return brd;
    }
}
