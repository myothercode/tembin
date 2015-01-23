package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.FeedBackQuery;
import com.base.domains.querypojos.FeedBackReportQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface FeedBackReportMapper {

    /**
     *
     * @param map
     * @return
     */
    List<FeedBackReportQuery> selectFeedBackReportList(Map map);

    List<FeedBackQuery> selectFeedBackDetailByAutoMessageFlag(Map map,Page page);
}