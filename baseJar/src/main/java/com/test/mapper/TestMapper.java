package com.test.mapper;

import com.base.database.publicd.model.PublicDataDict;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by chace.cai on 2014/7/9.
 */
public interface TestMapper {
    List<Map> queryTest(Map map);
    void updateTest(Map map);
    List<PublicDataDict> selectForCatchData(Map map);
}
