package com.base.utils.cache;

import com.base.database.publicd.model.PublicDataDict;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.ObjectUtils;
import com.trading.service.ITradingDataDictionary;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/24.
 */
public class DataDictionarySupport extends CacheBaseSupport{
    /**站点字典*/
    public static final String DATA_DICT_SITE ="site";//数据字典站点数据
    public static final String DATA_DICT_PAYPAL = "paypal";

    public static final String DICT_CACHE_NAME = "dataDictionaryCache";//数据字典所在的cache名

    public static final String TRADING_DATA_DICTIONARY = "tradingDataDictionary";//trading数据字典在cache中的名称
    public static final String PUBLIC_DATA_DICTIONARY = "publicDataDictionary";//PUBLIC数据字典在cache中的名称
    public static final String PUBLIC_USER_CONFIG = "publicUserConfig";//用户配置数据字典在cache中的名称

    public static final Map<Object,String> dictNameMap=new HashMap<Object, String>();
    static {
        dictNameMap.put(TradingDataDictionary.class,TRADING_DATA_DICTIONARY);//trading字典表对应的缓存名称
        dictNameMap.put(PublicDataDict.class,PUBLIC_DATA_DICTIONARY);
        dictNameMap.put(PublicUserConfig.class,PUBLIC_USER_CONFIG);
    }
    /*private static CacheManager cacheManager;
    static {
        ApplicationContext x= ApplicationContextUtil.getContext();
        cacheManager= (CacheManager) x.getBean(CacheManager.class);
    }*/

    /**获取Trading数据字典*/
    public static List<TradingDataDictionary> getTradingDictCache(){
        Cache cache = cacheManager.getCache(DICT_CACHE_NAME);
        Element element=cache.get(TRADING_DATA_DICTIONARY);
        if(element==null){return null;}
        return (List<TradingDataDictionary>) element.getObjectValue();
    }
    /**获取public数据字典*/
    public static List<PublicDataDict> getPublicDictCache(){
        Cache cache = cacheManager.getCache(DICT_CACHE_NAME);
        Element element=cache.get(PUBLIC_DATA_DICTIONARY);
        if(element==null){return null;}
        return (List<PublicDataDict>) element.getObjectValue();
    }
    /**获取userConfig数据字典*/
    public static List<PublicUserConfig> getPublicUserConfig(){
        Cache cache = cacheManager.getCache(DICT_CACHE_NAME);
        Element element=cache.get(PUBLIC_USER_CONFIG);
        if(element==null){return null;}
        return (List<PublicUserConfig>) element.getObjectValue();
    }
    /**将数据集合放入缓存*/
    public static<T> void put(List<T> t){
        if (ObjectUtils.isLogicalNull(t)){return;}
        String cacheElementName=dictNameMap.get(t.get(0).getClass());
        Cache cache = cacheManager.getCache(DICT_CACHE_NAME);
        Element element = new Element(cacheElementName,t);
        cache.put(element);
    }

    /**TradingDataDictionary字典表查询,用类型作为条件*/
    public static List<TradingDataDictionary> getTradingDataDictionaryByType(String type){
        ITradingDataDictionary dictionary = (ITradingDataDictionary) ApplicationContextUtil.getBean(ITradingDataDictionary.class);
        List<TradingDataDictionary> tradingDataDictionaries = dictionary.selectDictionaryByType(type);

        return tradingDataDictionaries;
    }
    /**TradingDataDictionary通过id去找*/
    public static TradingDataDictionary getTradingDataDictionaryByID(Long id){
        ITradingDataDictionary dictionary = (ITradingDataDictionary) ApplicationContextUtil.getBean(ITradingDataDictionary.class);
        TradingDataDictionary tradingDataDictionaries = dictionary.selectDictionaryByID(id);
        return tradingDataDictionaries;
    }
    /**PublicDataDict字典表查询,用类型作为条件*/
    public static List<PublicDataDict> getPublicDataDictionaryByType(String type){
        ITradingDataDictionary dictionary = (ITradingDataDictionary) ApplicationContextUtil.getBean(ITradingDataDictionary.class);
        List<PublicDataDict> tradingDataDictionaries = dictionary.selectPublicDataDictByType(type);

        return tradingDataDictionaries;
    }
    /**PublicDataDict通过id去找*/
    public static PublicDataDict getPublicDataDictionaryByID(Long id){
        ITradingDataDictionary dictionary = (ITradingDataDictionary) ApplicationContextUtil.getBean(ITradingDataDictionary.class);
        PublicDataDict tradingDataDictionaries = dictionary.selectPublicDictionaryByID(id);
        return tradingDataDictionaries;
    }
    /**PublicUserConfig字典表查询,用类型作为条件*/
    public static List<PublicUserConfig> getPublicUserConfigByType(String type,Long userId){
        ITradingDataDictionary dictionary = (ITradingDataDictionary) ApplicationContextUtil.getBean(ITradingDataDictionary.class);
        List<PublicUserConfig> tradingDataDictionaries = dictionary.selectUserConfigDataDictByType(type,userId);
        return tradingDataDictionaries;
    }
    /**PublicUserConfig通过id去找*/
    public static PublicUserConfig getPublicUserConfigByID(Long id){
        ITradingDataDictionary dictionary = (ITradingDataDictionary) ApplicationContextUtil.getBean(ITradingDataDictionary.class);
        PublicUserConfig tradingDataDictionaries = dictionary.selectUserConfigByID(id);
        return tradingDataDictionaries;
    }
}
