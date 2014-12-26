package com.test.service;

import com.base.database.publicd.model.PublicDataDict;

import java.util.List;
import java.util.Map;

/**
 * Created by chace.cai on 2014/7/8.
 */
public interface TestService {
    void serviceTest();

    void testReturnPolicy() throws Exception;



    void  updateData(PublicDataDict publicDataDict);
}
