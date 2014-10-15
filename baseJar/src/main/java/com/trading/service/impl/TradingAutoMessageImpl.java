package com.trading.service.impl;

import com.base.database.customtrading.mapper.AutoMessageMapper;
import com.base.database.trading.mapper.TradingAutoMessageMapper;
import com.base.database.trading.model.TradingAddItem;
import com.base.database.trading.model.TradingAutoMessage;
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
}
