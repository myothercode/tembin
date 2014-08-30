package com.trading.service.impl;

import com.base.database.publicd.mapper.PublicDataDictMapper;
import com.base.database.publicd.mapper.PublicUserConfigMapper;
import com.base.database.publicd.model.PublicDataDict;
import com.base.database.publicd.model.PublicDataDictExample;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.publicd.model.PublicUserConfigExample;
import com.base.database.trading.mapper.TradingDataDictionaryMapper;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.database.trading.model.TradingDataDictionaryExample;
import com.base.domains.SessionVO;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DictCollectionsUtil;
import com.base.utils.common.ObjectUtils;
import com.base.utils.xmlutils.SamplePaseXml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cz on 2014/7/24.
 * 数据字典、trading数据字典和用户配置字典都是这个service
 */
@Service
public class TradingDataDictionaryImpl implements com.trading.service.ITradingDataDictionary {
    @Autowired
    private TradingDataDictionaryMapper tradingDataDictionaryMapper;
    @Autowired
    private PublicDataDictMapper publicDataDictMapper;
    @Autowired
    private PublicUserConfigMapper publicUserConfigMapper;

    @Override
    public void saveDataDictionary(TradingDataDictionary tradingDataDictionary){
        this.tradingDataDictionaryMapper.insert(tradingDataDictionary);
    }

    @Override
    public TradingDataDictionary toDAOPojo(String value, String name, String type, String value1, String name1) throws Exception {
        TradingDataDictionary pojo = new TradingDataDictionary();
        ObjectUtils.toInitPojoForInsert(pojo);
        pojo.setValue(value);
        pojo.setValue1(value1);
        pojo.setName(name);
        pojo.setName1(name1);
        return pojo;
    }

    @Override
    //@Cacheable(value ="dataDictionaryCache",key = "#type")
    public List<TradingDataDictionary> selectDictionaryByType(String type, String name1, String name, String value){
        TradingDataDictionaryExample tradingDataDictionaryExample =new TradingDataDictionaryExample();
        TradingDataDictionaryExample.Criteria tc = tradingDataDictionaryExample.createCriteria().andTypeEqualTo(type);
        if(name1!=null){
            tc.andName1EqualTo(name1);
        }
        if(name!=null){
            tc.andNameEqualTo(name);
        }
        if(value!=null){
            tc.andValueEqualTo(value);
        }
       return this.tradingDataDictionaryMapper.selectByExample(tradingDataDictionaryExample);
    }

    /**根据类型查询TradingDataDictionary*/
    @Override
    public List<TradingDataDictionary> selectDictionaryByType(String type){
        /*TradingDataDictionaryExample tradingDataDictionaryExample =new TradingDataDictionaryExample();
        TradingDataDictionaryExample.Criteria tc = tradingDataDictionaryExample.createCriteria().andTypeEqualTo(type);
        return this.tradingDataDictionaryMapper.selectByExample(tradingDataDictionaryExample);*/
        List<TradingDataDictionary> ts=queryDictAll();
        List<TradingDataDictionary> n= DictCollectionsUtil.dataCollectionFilterByType(ts, type);
        return n;
    }

    @Override
    /**根据类型查询PublicDataDict*/
    public List<PublicDataDict> selectPublicDataDictByType(String type){
        List<PublicDataDict> ts=queryPublicDictAll();
        List<PublicDataDict> n= DictCollectionsUtil.dataCollectionFilterByType(ts, type);
        return n;
    }
    @Override
    /**根据类型和用户id查询userDataDict*/
    public List<PublicUserConfig> selectUserConfigDataDictByType(String type, Long userId){
        List<PublicUserConfig> ts=queryPublicUserConfigAll(userId);
        List<PublicUserConfig> n= DictCollectionsUtil.dataCollectionFilterByType(ts, type,userId);
        return n;
    }



    @Override
    /**根据ID查询TradingDataDictionary*/
    public TradingDataDictionary selectDictionaryByID(Long id){
        List<TradingDataDictionary> ts=queryDictAll();
        TradingDataDictionary n= DictCollectionsUtil.dataCollectionFilterByID(ts, id);
        return n;
    }
    @Override
    /**根据ID查询publicDataDictionary*/
    public List<PublicDataDict> selectPublicDictionaryByID(Long id){
        List<PublicDataDict> ts=queryPublicDictAll();
        List<PublicDataDict> n= DictCollectionsUtil.dataPublicDataCollectionsFilterByID(ts, id);
        return n;
    }

    /**根据ItemID查询publicDataDictionary*/
    @Override
    public List<PublicDataDict> selectPublicDictionaryByItemIDs(String itemid,String type){
        List<PublicDataDict> ts=queryPublicDictAll();
        List<PublicDataDict> n= DictCollectionsUtil.dataPublicDataCollectionsFilterByItemIDs(ts, itemid,type);
        return n;
    }
    @Override
    /**根据itemID查询publicDataDictionary*/
    public List<PublicDataDict> selectPublicDictionaryByItemID(Long id){
        List<PublicDataDict> ts=queryPublicDictAll();
        List<PublicDataDict> n= DictCollectionsUtil.dataPublicDataCollectionsFilterByItemID(ts, id);
        return n;
    }
    @Override
    /**根据parentID查询publicDataDictionary*/
    public List<PublicDataDict> selectPublicDictionaryByParentID(Long id , String itemType){
        List<PublicDataDict> ts=queryPublicDictAll();
        List<PublicDataDict> n= DictCollectionsUtil.dataPublicDataCollectionsFilterByParentID(ts, id,itemType);
        return n;
    }
    @Override
    /**根据parentID和itemlevel查询publicDataDictionary*/
    public List<PublicDataDict> selectPublicDictionaryByItemLevel(Long id , String itemLevel,String itemType){
        List<PublicDataDict> ts=queryPublicDictAll();
        List<PublicDataDict> n= DictCollectionsUtil.dataPublicDataCollectionsFilterByItemLevel(ts, id,itemLevel,itemType);
        return n;
    }
    @Override
    /**根据ID查询userConfig*/
    public PublicUserConfig selectUserConfigByID(Long id){
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        List<PublicUserConfig> ts=queryPublicUserConfigAll(sessionVO.getId());
        PublicUserConfig n= DictCollectionsUtil.dataCollectionFilterByID(ts, id);
        return n;
    }

    @Override
    /**添加类别属性信息，并返回解析完毕的集合*/
    public List<PublicDataDict> addPublicData(String xml) throws Exception {
        List<PublicDataDict> publicDataDictList = SamplePaseXml.getListForPublicDataDict(xml);
        if(publicDataDictList.isEmpty()){return new ArrayList<PublicDataDict>();}
        for (PublicDataDict publicDataDict : publicDataDictList){
            publicDataDictMapper.insertSelective(publicDataDict);
        }
        return publicDataDictList;
    }



    //@Override
    //@Cacheable(value ="dataDictionaryCache")
    /**查询所有的TradingDataDictionary数据字典数据*/
    private List<TradingDataDictionary> queryDictAll(){
        /**检查缓存里面是否已经有数据*/
        List<TradingDataDictionary> tradingDataDictionaries=DataDictionarySupport.getTradingDictCache();
        if(ObjectUtils.isLogicalNull(tradingDataDictionaries)){
            TradingDataDictionaryExample tradingDataDictionaryExample =new TradingDataDictionaryExample();
            tradingDataDictionaries = this.tradingDataDictionaryMapper.selectByExample(tradingDataDictionaryExample);
            DataDictionarySupport.put(tradingDataDictionaries);
        }
       return tradingDataDictionaries;
    }
    /**查询所有的publicDataDictionary数据字典数据*/
    private List<PublicDataDict> queryPublicDictAll(){
        /**检查缓存里面是否已经有数据*/
        List<PublicDataDict> tradingDataDictionaries=DataDictionarySupport.getPublicDictCache();
        if(ObjectUtils.isLogicalNull(tradingDataDictionaries)){
            PublicDataDictExample tradingDataDictionaryExample =new PublicDataDictExample();
            tradingDataDictionaries = this.publicDataDictMapper.selectByExample(tradingDataDictionaryExample);
            DataDictionarySupport.put(tradingDataDictionaries);
        }
        return tradingDataDictionaries;
    }
    /**查询指定用户id的userConfig数据字典数据*/
    private List<PublicUserConfig> queryPublicUserConfigAll(Long userID){
        /**检查缓存里面是否已经有数据*/
        List<PublicUserConfig> tradingDataDictionaries=DataDictionarySupport.getPublicUserConfig(userID);
        if(ObjectUtils.isLogicalNull(tradingDataDictionaries)){
            PublicUserConfigExample tradingDataDictionaryExample =new PublicUserConfigExample();
            PublicUserConfigExample.Criteria tc = tradingDataDictionaryExample.createCriteria().andUserIdEqualTo(userID);
            tradingDataDictionaries = this.publicUserConfigMapper.selectByExample(tradingDataDictionaryExample);
            DataDictionarySupport.put(tradingDataDictionaries,userID.toString());
        }
        return tradingDataDictionaries;
    }


}
