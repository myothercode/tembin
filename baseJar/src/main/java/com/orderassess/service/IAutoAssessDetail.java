package com.orderassess.service;

import com.base.database.trading.model.AutoAssessDetailWithBLOBs;

import java.util.List;

/**
 * Created by Administrtor on 2014/11/20.
 */
public interface IAutoAssessDetail {
    void saveAutoAssessDetail(AutoAssessDetailWithBLOBs autoAssessDetail);

    List<AutoAssessDetailWithBLOBs> selectByList(Long sourceId, String dataType);
}