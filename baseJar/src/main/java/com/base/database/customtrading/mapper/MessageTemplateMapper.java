package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.MessageTemplateQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface MessageTemplateMapper {

    /**
     *
     * @param map
     * @return
     */
    List<MessageTemplateQuery> selectMessageTemplateList(Map map, Page page);

}