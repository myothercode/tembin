package com.orderassess.service.impl;

import com.base.database.customtrading.mapper.AutoAssessDetailQueryMapper;
import com.base.database.trading.mapper.AutoAssessDetailMapper;
import com.base.database.trading.model.AutoAssessDetail;
import com.base.database.trading.model.AutoAssessDetailExample;
import com.base.database.trading.model.AutoAssessDetailWithBLOBs;
import com.base.mybatis.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/11/20.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AutoAssessDetailImpl implements com.orderassess.service.IAutoAssessDetail {
    @Autowired
    private AutoAssessDetailMapper autoAssessDetailMapper;

    @Autowired
    private AutoAssessDetailQueryMapper autoAssessDetailQueryMapper;
    @Override
    public void saveAutoAssessDetail(AutoAssessDetailWithBLOBs autoAssessDetail){
        if(autoAssessDetail.getId()==null){
            this.autoAssessDetailMapper.insertSelective(autoAssessDetail);
        }else{
            this.autoAssessDetailMapper.updateByPrimaryKeySelective(autoAssessDetail);
        }
    }

    @Override
    public List<AutoAssessDetailWithBLOBs> selectByList(Long sourceId,String dataType,String isFlag){
        AutoAssessDetailExample aade = new AutoAssessDetailExample();
        aade.createCriteria().andDataTypeEqualTo(dataType).andSourceIdEqualTo(sourceId).andIsFlagEqualTo(isFlag);
        return this.autoAssessDetailMapper.selectByExampleWithBLOBs(aade);
    }


    @Override
    public List<AutoAssessDetailWithBLOBs> selectByListIsFlag(String isFlag){
        Map map = new HashMap();
        map.put("isFlag",isFlag);
        Page page = new Page();
        page.setPageSize(20);
        page.setCurrentPage(1);
        return this.autoAssessDetailQueryMapper.selectAutoAssessDetailList(map,page);
    }

}

