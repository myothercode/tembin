package com.trading.service.impl;

import com.base.database.customtrading.mapper.OrderItemMapper;
import com.base.database.trading.mapper.TradingOrderGetItemMapper;
import com.base.database.trading.model.TradingOrderGetItem;
import com.base.database.trading.model.TradingOrderGetItemExample;
import com.base.domains.querypojos.OrderItemQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 退货政策
 * Created by lq on 2014/7/29.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingOrderGetItemImpl implements com.trading.service.ITradingOrderGetItem {

    @Autowired
    private TradingOrderGetItemMapper tradingOrderGetItemMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Override
    public void saveOrderGetItem(TradingOrderGetItem OrderGetItem) throws Exception {
        if(OrderGetItem.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderGetItem);
            tradingOrderGetItemMapper.insert(OrderGetItem);
        }else{
            TradingOrderGetItem t=tradingOrderGetItemMapper.selectByPrimaryKey(OrderGetItem.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderGetItemMapper.class,OrderGetItem.getId(),"Synchronize");
            tradingOrderGetItemMapper.updateByPrimaryKeySelective(OrderGetItem);
        }
    }

    @Override
    public List<TradingOrderGetItem> selectOrderGetItemByItemId(String ItemId) {
        TradingOrderGetItemExample itemExample=new TradingOrderGetItemExample();
        TradingOrderGetItemExample.Criteria cr=itemExample.createCriteria();
        cr.andItemidEqualTo(ItemId);
        /*cr.andItemidLike(ItemId);*/
        List<TradingOrderGetItem> lists=tradingOrderGetItemMapper.selectByExample(itemExample);
        return lists;
    }

    @Override
    public List<OrderItemQuery> selectOrderItemList(Map map, Page page) {
        return orderItemMapper.selectOrderItemList(map,page);
    }
}
