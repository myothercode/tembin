package com.trading.service.impl;

import com.base.database.trading.mapper.TradingPicturesMapper;
import com.base.database.trading.model.TradingPictures;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.Pictures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cz on 2014/7/24.
 */
@Service
public class TradingPicturesImpl implements com.trading.service.ITradingPictures {
    @Autowired
    private TradingPicturesMapper tradingPicturesMapper;

    @Override
    public void savePictures(TradingPictures tp){
        this.tradingPicturesMapper.insert(tp);
    }

    @Override
    public TradingPictures toDAOPojo(Pictures pic) throws Exception {
        TradingPictures pojo = new TradingPictures();
        ObjectUtils.toInitPojoForInsert(pojo);
        ConvertPOJOUtil.convert(pojo,pic);
        return pojo;
    }
}
