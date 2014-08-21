package com.trading.service.impl;

import com.base.database.trading.mapper.TradingAttrMoresMapper;
import com.base.database.trading.mapper.TradingPicturedetailsMapper;
import com.base.database.trading.model.TradingAttrMoresExample;
import com.base.database.trading.model.TradingPicturedetails;
import com.base.database.trading.model.TradingPicturedetailsExample;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.PictureDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cz on 2014/7/24.
 */
@Service
public class TradingPictureDetailsImpl implements com.trading.service.ITradingPictureDetails {
    @Autowired
    private TradingPicturedetailsMapper tpm;
    @Autowired
    private TradingAttrMoresMapper attrMoresMapper;

    @Override
    public void savePictureDetails(TradingPicturedetails tp) throws Exception {
        if(tp.getId()==null){
            TradingPicturedetailsExample details = new TradingPicturedetailsExample();
            details.createCriteria().andParentIdEqualTo(tp.getParentId());
            List<TradingPicturedetails> tradingPicturedetailsList = tpm.selectByExample(details);
            if(!ObjectUtils.isLogicalNull(tradingPicturedetailsList)){
                List<Long> longs = new ArrayList<Long>();
                for (TradingPicturedetails tps : tradingPicturedetailsList){
                    longs.add(tps.getId());
                }
                TradingAttrMoresExample ttt=new TradingAttrMoresExample();
                ttt.createCriteria().andIdIn(longs).andAttrValueEqualTo("PictureURL");
                attrMoresMapper.deleteByExample(ttt);
            }
            details.clear();
            details.createCriteria().andParentIdEqualTo(tp.getParentId());
            tpm.deleteByExample(details);

            ObjectUtils.toInitPojoForInsert(tp);
            this.tpm.insert(tp);
        }else{
            this.tpm.updateByPrimaryKeySelective(tp);
        }
    }

    @Override
    public TradingPicturedetails toDAOPojo(PictureDetails pd) throws Exception {
        TradingPicturedetails pojo = new TradingPicturedetails();
        ConvertPOJOUtil.convert(pojo,pd);
        return pojo;
    }

    @Override
    public List<TradingPicturedetails> selectByParentId(Long id){
        TradingPicturedetailsExample tpe = new TradingPicturedetailsExample();
        tpe.createCriteria().andParentIdEqualTo(id);
        return this.tpm.selectByExample(tpe);
    }
}
