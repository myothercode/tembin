package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.MessageAddmymessageQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface MessageAddmymessageMapper {

    /**
     *
     * @param map
     * @return
     */
    List<MessageAddmymessageQuery> selectMessageGetmymessageByGroupList(Map map, Page page);

}