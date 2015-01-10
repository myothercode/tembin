package com.task.service.impl;

import com.base.database.task.mapper.ListingDataTaskMapper;
import com.base.database.task.mapper.TradingTaskXmlMapper;
import com.base.database.task.model.ListingDataTask;
import com.base.database.task.model.ListingDataTaskExample;
import com.base.database.task.model.TradingTaskXml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrtor on 2014/10/17.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingTaskXmlImpl implements com.task.service.ITradingTaskXml {
    @Autowired
    private TradingTaskXmlMapper tradingTaskXmlMapper;

    @Override
    public void saveTradingTaskXml(TradingTaskXml tradingTaskXml){
        this.tradingTaskXmlMapper.insertSelective(tradingTaskXml);
    }
}
