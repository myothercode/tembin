package com.orderassess.service.impl;

import com.base.database.trading.mapper.AutoAssessDetailMapper;
import com.base.database.trading.model.AutoAssessDetail;
import com.base.database.trading.model.AutoAssessDetailExample;
import com.base.database.trading.model.AutoAssessDetailWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrtor on 2014/11/20.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AutoAssessDetailImpl implements com.orderassess.service.IAutoAssessDetail {
    @Autowired
    private AutoAssessDetailMapper autoAssessDetailMapper;

    @Override
    public void saveAutoAssessDetail(AutoAssessDetailWithBLOBs autoAssessDetail){
        if(autoAssessDetail.getId()==null){
            this.autoAssessDetailMapper.insertSelective(autoAssessDetail);
        }else{
            this.autoAssessDetailMapper.updateByPrimaryKeySelective(autoAssessDetail);
        }
    }

    @Override
    public List<AutoAssessDetailWithBLOBs> selectByList(Long sourceId,String dataType){
        AutoAssessDetailExample aade = new AutoAssessDetailExample();
        aade.createCriteria().andDataTypeEqualTo(dataType).andSourceIdEqualTo(sourceId);
        return this.autoAssessDetailMapper.selectByExampleWithBLOBs(aade);
    }
}

