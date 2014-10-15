package com.trading.service.impl;

import com.base.database.customtrading.mapper.MessageTemplateMapper;
import com.base.database.trading.mapper.TradingMessageAddmembermessageMapper;
import com.base.database.trading.mapper.TradingMessageTemplateMapper;
import com.base.database.trading.model.TradingMessageAddmembermessage;
import com.base.database.trading.model.TradingMessageAddmembermessageExample;
import com.base.database.trading.model.TradingMessageTemplate;
import com.base.database.trading.model.TradingMessageTemplateExample;
import com.base.domains.querypojos.MessageAddmymessageQuery;
import com.base.domains.querypojos.MessageTemplateQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 商品描述模块
 * Created by cz on 2014/7/23.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingMessageTemplateImpl implements com.trading.service.ITradingMessageTemplate {
    @Autowired
    private TradingMessageTemplateMapper tradingMessageTemplateMapper;
    @Autowired
    private MessageTemplateMapper messageAddmembermessage;

    @Override
    public void saveMessageTemplate(TradingMessageTemplate messageTemplate) throws Exception {
        if(messageTemplate.getId()==null){
            ObjectUtils.toInitPojoForInsert(messageTemplate);
            this.tradingMessageTemplateMapper.insertSelective(messageTemplate);
        }else{
            TradingMessageTemplate t=tradingMessageTemplateMapper.selectByPrimaryKey(messageTemplate.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingMessageTemplateMapper.class,messageTemplate.getId());
            this.tradingMessageTemplateMapper.updateByPrimaryKeySelective(messageTemplate);
        }
    }

    @Override
    public List<MessageTemplateQuery> selectMessageTemplateList(Map map, Page page) {
        return messageAddmembermessage.selectMessageTemplateList(map,page);
    }

    @Override
    public List<TradingMessageTemplate> selectMessageTemplatebyId(Long id) {
        TradingMessageTemplateExample example=new TradingMessageTemplateExample();
        TradingMessageTemplateExample.Criteria cr=example.createCriteria();
        cr.andIdEqualTo(id);
        List<TradingMessageTemplate> list=tradingMessageTemplateMapper.selectByExample(example);
        return list;
    }

    @Override
    public void deleteMessageTemplate(TradingMessageTemplate messageTemplate) throws Exception {
        if(messageTemplate!=null&&messageTemplate.getId()!=null){
            tradingMessageTemplateMapper.deleteByPrimaryKey(messageTemplate.getId());
        }
    }
}