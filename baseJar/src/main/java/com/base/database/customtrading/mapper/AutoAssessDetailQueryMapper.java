package com.base.database.customtrading.mapper;

import com.base.database.trading.model.AutoAssessDetail;
import com.base.database.trading.model.AutoAssessDetailExample;
import com.base.database.trading.model.AutoAssessDetailWithBLOBs;
import com.base.mybatis.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AutoAssessDetailQueryMapper {

    List<AutoAssessDetailWithBLOBs> selectAutoAssessDetailList(Map map, Page page);
}