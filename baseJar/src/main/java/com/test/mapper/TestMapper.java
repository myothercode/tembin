package com.test.mapper;

import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by chace.cai on 2014/7/9.
 */
public interface TestMapper {
    List<String> queryTest(Map map,Page page);
    void updateTest(Map map);
}
