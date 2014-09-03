package com.trading.service.impl;


import com.base.database.trading.mapper.TradingOrderPicturesMapper;
import com.base.database.trading.model.TradingOrderPictures;
import com.base.database.trading.model.TradingOrderPicturesExample;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.trading.service.ITradingOrderPictures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 退货政策
 * Created by lq on 2014/7/29.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingOrderPicturesImpl implements ITradingOrderPictures {

    @Autowired
    private TradingOrderPicturesMapper tradingOrderPicturesMapper;

    @Override
    public void saveOrderPictures(TradingOrderPictures OrderPictures) throws Exception {
        if(OrderPictures.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderPictures);
            tradingOrderPicturesMapper.insert(OrderPictures);
        }else{
            TradingOrderPictures t=tradingOrderPicturesMapper.selectByPrimaryKey(OrderPictures.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderPicturesMapper.class,OrderPictures.getId());
            tradingOrderPicturesMapper.updateByPrimaryKeySelective(OrderPictures);
        }
    }

    @Override
    public List<TradingOrderPictures> selectOrderPicturesByItemId(Long Id) {
        TradingOrderPicturesExample shippingDetailsExample=new TradingOrderPicturesExample();
        TradingOrderPicturesExample.Criteria cr=shippingDetailsExample.createCriteria();
        cr.andOrderitemIdEqualTo(Id);
        List<TradingOrderPictures> lists=tradingOrderPicturesMapper.selectByExample(shippingDetailsExample);
        return lists;
    }

    @Override
    public void deleteOrderPictures(TradingOrderPictures OrderPictures) throws Exception {
        Long id=OrderPictures.getId();
        if(id!=null){
            tradingOrderPicturesMapper.deleteByPrimaryKey(id);
        }
    }
}
