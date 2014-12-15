package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.KeyMoveProgressQuery;

import java.util.List;
import java.util.Map;

public interface KeyMoveProgressQueryMapper {

    /**
     *
     * @param map
     * @return
     */
    List<KeyMoveProgressQuery> selectKeyMoveProgressList(Map map);
}