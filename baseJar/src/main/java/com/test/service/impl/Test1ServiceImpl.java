package com.test.service.impl;

import com.base.database.publicd.mapper.PublicDataDictMapper;
import com.base.database.publicd.model.PublicDataDict;
import com.base.database.trading.mapper.TradingPicturesMapper;
import com.base.database.trading.model.TradingBuyerRequirementDetails;
import com.base.database.trading.model.TradingItemWithBLOBs;
import com.base.database.trading.model.TradingReturnpolicy;
import com.base.xmlpojo.trading.addproduct.*;
import com.base.xmlpojo.trading.addproduct.attrclass.StartPrice;
import com.test.mapper.TestMapper;
import com.test.service.Test1Service;
import com.test.service.TestService;
import com.trading.service.ITradingBuyerRequirementDetails;
import com.trading.service.ITradingItem;
import com.trading.service.ITradingReturnpolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chace.cai on 2014/7/8.
 */
@Service("test1Service")
@Transactional(rollbackFor = Exception.class)
public class Test1ServiceImpl implements Test1Service {
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private ITradingReturnpolicy itradingReturnpolicy;
    @Autowired
    private PublicDataDictMapper publicDataDictMapper;


    @Override
   // @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void serviceTest(){
        Map map=new HashMap();
        map.put("id","59");
        map.put("StringV","fgf");
        //List<String> strings = testMapper.queryTest(map,page);
        testMapper.updateTest(map);


    }


    @Override
    public List<PublicDataDict> selectpddhData(Map map){
        return testMapper.selectpddhData(map);
    }


    @Override
    public void  updateData(PublicDataDict publicDataDict){
        int x= publicDataDictMapper.updateByPrimaryKeySelective(publicDataDict);

        PublicDataDict xx=publicDataDictMapper.selectByPrimaryKey(publicDataDict.getId());
        System.out.println(x);
    }


}
