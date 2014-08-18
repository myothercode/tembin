package com.trading.service.impl;

import com.base.database.trading.mapper.TradingOrderPictureDetailsMapper;
import com.base.database.trading.model.TradingOrderPictureDetails;
import com.base.database.trading.model.TradingOrderPictureDetailsExample;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
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
public class TradingOrderPictureDetailsImpl implements com.trading.service.ITradingOrderPictureDetails {

    @Autowired
    private TradingOrderPictureDetailsMapper tradingOrderPictureDetailsMapper;

    @Override
    public void saveOrderPictureDetails(TradingOrderPictureDetails OrderPictureDetails) throws Exception {
        if(OrderPictureDetails.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderPictureDetails);
            tradingOrderPictureDetailsMapper.insert(OrderPictureDetails);
        }else{
            TradingOrderPictureDetails t=tradingOrderPictureDetailsMapper.selectByPrimaryKey(OrderPictureDetails.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderPictureDetailsMapper.class,OrderPictureDetails.getId());
            tradingOrderPictureDetailsMapper.updateByPrimaryKeySelective(OrderPictureDetails);
        }
    }

    @Override
    public List<TradingOrderPictureDetails> selectOrderGetItemById(Long id) {
        TradingOrderPictureDetailsExample pictureDetailsExample=new TradingOrderPictureDetailsExample();
        TradingOrderPictureDetailsExample.Criteria cr=pictureDetailsExample.createCriteria();
        cr.andIdEqualTo(id);
        List<TradingOrderPictureDetails> lists=tradingOrderPictureDetailsMapper.selectByExample(pictureDetailsExample);
        return lists;
    }
}
