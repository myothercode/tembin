package com.trading.service.impl;

import com.base.database.trading.mapper.TradingPicturedetailsMapper;
import com.base.database.trading.model.TradingPicturedetails;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.PictureDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cz on 2014/7/24.
 */
@Service
public class TradingPictureDetailsImpl implements com.trading.service.ITradingPictureDetails {
    @Autowired
    private TradingPicturedetailsMapper tpm;

    @Override
    public void savePictureDetails(TradingPicturedetails tp){
        this.tpm.insert(tp);
    }

    @Override
    public TradingPicturedetails toDAOPojo(PictureDetails pd) throws Exception {
        TradingPicturedetails pojo = new TradingPicturedetails();
        ObjectUtils.toPojo(pojo);
        ConvertPOJOUtil.convert(pojo,pd);
        return pojo;
    }
}
