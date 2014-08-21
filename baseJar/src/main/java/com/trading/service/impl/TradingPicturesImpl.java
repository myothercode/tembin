package com.trading.service.impl;

import com.base.database.trading.mapper.TradingAttrMoresMapper;
import com.base.database.trading.mapper.TradingPicturesMapper;
import com.base.database.trading.model.TradingAttrMoresExample;
import com.base.database.trading.model.TradingPicturedetails;
import com.base.database.trading.model.TradingPictures;
import com.base.database.trading.model.TradingPicturesExample;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.Pictures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cz on 2014/7/24.
 */
@Service
public class TradingPicturesImpl implements com.trading.service.ITradingPictures {
    @Autowired
    private TradingPicturesMapper tradingPicturesMapper;
    @Autowired
    private TradingAttrMoresMapper attrMoresMapper;

    @Override
    public void savePictures(TradingPictures tp) throws Exception {
        if(tp.getId()==null){
            TradingPicturesExample picturesExample = new TradingPicturesExample();
            picturesExample.createCriteria().andParentIdEqualTo(tp.getId());
            List<TradingPictures> tradingPictureses = tradingPicturesMapper.selectByExample(picturesExample);
            if(!ObjectUtils.isLogicalNull(tradingPictureses)){
                List<Long> longs = new ArrayList<Long>();
                for (TradingPictures tps : tradingPictureses){
                    longs.add(tps.getId());
                }
                TradingAttrMoresExample ttt=new TradingAttrMoresExample();
                ttt.createCriteria().andIdIn(longs).andAttrValueEqualTo("MuAttrPictureURL");
                attrMoresMapper.deleteByExample(ttt);
            }
            picturesExample.clear();
            picturesExample.createCriteria().andParentIdEqualTo(tp.getParentId());
            tradingPicturesMapper.deleteByExample(picturesExample);


           //TradingPicturesExample picturesExample = new TradingPicturesExample();
           // picturesExample.createCriteria().andParentIdEqualTo(tp.getId());
           // tradingPicturesMapper.deleteByExample(picturesExample);

            ObjectUtils.toInitPojoForInsert(tp);
            this.tradingPicturesMapper.insert(tp);
        }else{
            this.tradingPicturesMapper.updateByPrimaryKeySelective(tp);
        }

    }

    @Override
    public TradingPictures toDAOPojo(Pictures pic) throws Exception {
        TradingPictures pojo = new TradingPictures();
        ConvertPOJOUtil.convert(pojo,pic);
        return pojo;
    }
    @Override
    public TradingPictures selectParnetId(Long id){
        TradingPicturesExample tpe = new TradingPicturesExample();
        tpe.createCriteria().andParentIdEqualTo(id);
        return this.tradingPicturesMapper.selectByExample(tpe).get(0);
    }
}
