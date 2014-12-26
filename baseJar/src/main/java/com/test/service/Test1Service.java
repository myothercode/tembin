package com.test.service;

import com.base.database.publicd.model.PublicDataDict;

import java.util.List;
import java.util.Map;

/**
 * Created by chace.cai on 2014/7/8.
 */
public interface Test1Service {
    void serviceTest();

    List<PublicDataDict> selectpddhData(Map map);

    void  updateData(PublicDataDict publicDataDict);
}
