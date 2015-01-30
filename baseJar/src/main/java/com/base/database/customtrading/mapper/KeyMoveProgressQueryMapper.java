package com.base.database.customtrading.mapper;

import com.base.database.keymove.model.KeyMoveList;
import com.base.domains.querypojos.KeyMoveProgressQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface KeyMoveProgressQueryMapper {

    /**
     *
     * @param map
     * @return
     */
    List<KeyMoveProgressQuery> selectKeyMoveProgressList(Map map);

    /**
     * 查询需要搬家的列表，用于分页查询
     * @param map
     * @param page
     * @return
     */
    List<KeyMoveList> selectByPageKeyMoveList(Map map,Page page);
}