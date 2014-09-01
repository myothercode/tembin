package com.trading.service.impl;

import com.base.database.trading.mapper.TradingAttrMoresMapper;
import com.base.database.trading.mapper.TradingPicturesMapper;
import com.base.database.trading.model.*;
import com.base.utils.common.ConvertPOJOUtil;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.Pictures;
import com.trading.service.ITradingAttrMores;
import com.trading.service.ITradingPublicLevelAttr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cz on 2014/7/24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingPicturesImpl implements com.trading.service.ITradingPictures {
    @Autowired
    private TradingPicturesMapper tradingPicturesMapper;
    @Autowired
    private TradingAttrMoresMapper attrMoresMapper;
    @Autowired
    private ITradingPublicLevelAttr iTradingPublicLevelAttr;
    @Autowired
    private ITradingAttrMores iTradingAttrMores;

    @Override
    public void savePictures(TradingPictures tp) throws Exception {
        if(tp.getId()==null){
            TradingPicturesExample picturesExample = new TradingPicturesExample();
            picturesExample.createCriteria().andParentIdEqualTo(tp.getParentId());
            List<TradingPictures> tradingPictureses = tradingPicturesMapper.selectByExample(picturesExample);
            if(!ObjectUtils.isLogicalNull(tradingPictureses)){
                List<Long> longs = new ArrayList<Long>();
                for (TradingPictures tps : tradingPictureses){
                    List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificPictureSet", tps.getId());
                    for (TradingPublicLevelAttr tpa : litpla) {
                        this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecificValue",tpa.getId());
                        this.iTradingAttrMores.deleteByParentId("MuAttrPictureURL",tpa.getId());
                    }
                    this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecificPictureSet", tps.getId());
                    longs.add(tps.getId());
                }
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
        List<TradingPictures> tp =this.tradingPicturesMapper.selectByExample(tpe);
        if(tp==null||tp.size()==0){
            return null;
        }else{
            return tp.get(0);
        }
    }
}
