package com.trading.service.impl;

import com.base.database.trading.mapper.TradingProductlistingdetailsMapper;
import com.base.database.trading.model.TradingProductlistingdetails;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.ProductListingDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cz on 2014/7/24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingProductListingDetailsImpl implements com.trading.service.ITradingProductListingDetails {
    @Autowired
    private TradingProductlistingdetailsMapper tpm;

    @Override
    public void saveProductListingDetails(TradingProductlistingdetails pojo){
        this.tpm.insert(pojo);
    }

    @Override
    public TradingProductlistingdetails toDAOPojo(ProductListingDetails pld) throws Exception {
        TradingProductlistingdetails pojo = new TradingProductlistingdetails();
        ObjectUtils.toInitPojoForInsert(pojo);
        ConvertPOJOUtil.convert(pojo,pld);
        ConvertPOJOUtil.convert(pojo,pld.getBrandMPN());
        ConvertPOJOUtil.convert(pojo,pld.getTicketListingDetails());
        return pojo;
    }
}
