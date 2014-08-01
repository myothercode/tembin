package com.trading.service.impl;

import com.base.database.trading.mapper.TradingVatdetailsMapper;
import com.base.database.trading.model.TradingVatdetails;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.VATDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cz on 2014/7/24.
 */
@Service
public class TradingVATDetailsImpl implements com.trading.service.ITradingVATDetails {
    @Autowired
    private TradingVatdetailsMapper tvm;

    @Override
    public void saveVATDetails(TradingVatdetails pojo){
        this.tvm.insert(pojo);
    }
    @Override
    public TradingVatdetails toDAOPojo(VATDetails vat) throws Exception {
        TradingVatdetails pojo = new TradingVatdetails();
        ObjectUtils.toInitPojoForInsert(pojo);
        ConvertPOJOUtil.convert(pojo,vat);
        return pojo;
    }
}
