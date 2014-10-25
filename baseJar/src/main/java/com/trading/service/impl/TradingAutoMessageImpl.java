package com.trading.service.impl;

import com.base.database.customtrading.mapper.AutoMessageMapper;
import com.base.database.trading.mapper.TradingAutoMessageMapper;
import com.base.database.trading.model.TradingAddItem;
import com.base.database.trading.model.TradingAutoMessage;
import com.base.database.trading.model.TradingAutoMessageExample;
import com.base.domains.querypojos.AutoMessageQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/14.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingAutoMessageImpl implements com.trading.service.ITradingAutoMessage {
    @Autowired
    private TradingAutoMessageMapper tradingAutoMessageMapper;
    @Autowired
    private AutoMessageMapper autoMessageMapper;

    @Override
    public void saveAutoMessage(TradingAutoMessage autoMessage) throws Exception {
        if(autoMessage.getId()==null){
            ObjectUtils.toInitPojoForInsert(autoMessage);
            this.tradingAutoMessageMapper.insertSelective(autoMessage);
        }else{
            TradingAutoMessage t=tradingAutoMessageMapper.selectByPrimaryKey(autoMessage.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingAutoMessageMapper.class,autoMessage.getId());
            this.tradingAutoMessageMapper.updateByPrimaryKeySelective(autoMessage);
        }
    }

    @Override
    public List<AutoMessageQuery> selectAutoMessageList(Map map, Page page) {
        return autoMessageMapper.selectAutoMessageList(map,page);
    }

    @Override
    public List<TradingAutoMessage> selectAutoMessageById(Long id) {
        TradingAutoMessageExample example=new TradingAutoMessageExample();
        TradingAutoMessageExample.Criteria cr=example.createCriteria();
        cr.andIdEqualTo(id);
        List<TradingAutoMessage> list=tradingAutoMessageMapper.selectByExample(example);
        return list;
    }

    @Override
    public void deleteAutoMessage(TradingAutoMessage autoMessage) throws Exception {
        if(autoMessage!=null&&autoMessage.getId()!=null){
            tradingAutoMessageMapper.deleteByPrimaryKey(autoMessage.getId());
        }
    }

    @Override
    public List<AutoMessageQuery> selectShippingServiceOptionList(Map map, Page page) {
        return autoMessageMapper.selectShippingServiceOptionList(map,page);
    }

    @Override
    public List<AutoMessageQuery> selectInternationalShippingServiceList(Map map, Page page) {
        return autoMessageMapper.selectInternationalShippingServiceList(map,page);
    }

    @Override
    public List<TradingAutoMessage> selectAutoMessageByType(String type) {
        TradingAutoMessageExample example=new TradingAutoMessageExample();
        TradingAutoMessageExample.Criteria cr=example.createCriteria();
        cr.andTypeEqualTo(type);
        List<TradingAutoMessage> list=tradingAutoMessageMapper.selectByExample(example);
        return list;
    }
}
