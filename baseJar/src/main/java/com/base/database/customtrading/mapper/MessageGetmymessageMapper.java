package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.MessageGetmymessageQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface MessageGetmymessageMapper {

    /**
     *
     * @param map
     * @return
     */
    List<MessageGetmymessageQuery> selectByMessageGetmymessageList(Map map, Page page);

    List<MessageGetmymessageQuery> selectMessageGetmymessageByGroupList (Map map, Page page);

    List<MessageGetmymessageQuery> selectMessageGetmymessageBySender (Map map);
}