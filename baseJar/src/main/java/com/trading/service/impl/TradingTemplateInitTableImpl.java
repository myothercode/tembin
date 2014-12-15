package com.trading.service.impl;

import com.base.database.customtrading.mapper.TemplateInitTableMapper;
import com.base.database.trading.mapper.TradingTemplateInitTableMapper;
import com.base.database.trading.model.TradingTemplateInitTable;
import com.base.database.trading.model.TradingTemplateInitTableExample;
import com.base.domains.querypojos.TemplateInitTableQuery;
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
public class TradingTemplateInitTableImpl implements com.trading.service.ITradingTemplateInitTable {
    @Autowired
    private TradingTemplateInitTableMapper tradingTemplateInitTableMapper;

    @Autowired
    private TemplateInitTableMapper templateInitTableMapper;

    @Override
    public void saveTemplateInitTable(TradingTemplateInitTable templateInitTable) throws Exception {
        if(templateInitTable.getId()==null){
            ObjectUtils.toInitPojoForInsert(templateInitTable);
            this.tradingTemplateInitTableMapper.insertSelective(templateInitTable);
        }else{
            TradingTemplateInitTable t=tradingTemplateInitTableMapper.selectByPrimaryKey(templateInitTable.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingTemplateInitTableMapper.class,templateInitTable.getId());
            this.tradingTemplateInitTableMapper.updateByPrimaryKeySelective(templateInitTable);
        }
    }

    @Override
    public List<TemplateInitTableQuery> selectByTemplateInitTableList(Map map) {
        Page page=new Page();
        page.setPageSize(10);
        page.setCurrentPage(1);
        return templateInitTableMapper.selectByTemplateInitTableList(map,page);
    }

    @Override
    public List<TemplateInitTableQuery> selectByTemplateInitTableList(Map map, Page page) {
        return templateInitTableMapper.selectByTemplateInitTableList(map,page);
    }
    @Override
    public List<TemplateInitTableQuery> selectTemplateType(Map map) {
        return templateInitTableMapper.selectTemplateType(map);
    }
    @Override
    public TradingTemplateInitTable selectById(Long id){
        return this.tradingTemplateInitTableMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TradingTemplateInitTable> selectByType(long typeid){
        TradingTemplateInitTableExample tte = new TradingTemplateInitTableExample();
        tte.createCriteria().andTLevelEqualTo("0").andTemplateTypeIdEqualTo(typeid);
        return this.tradingTemplateInitTableMapper.selectByExampleWithBLOBs(tte);
    }
}