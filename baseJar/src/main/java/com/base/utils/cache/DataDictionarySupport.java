package com.base.utils.cache;

import com.base.database.publicd.model.PublicDataDict;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.domains.DictDataFilterParmVO;
import com.base.domains.SessionVO;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.ObjectUtils;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.trading.service.ITradingDataDictionary;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.util.*;

/**
 * Created by Administrtor on 2014/7/24.
 */
public class DataDictionarySupport extends CacheBaseSupport{
    public static final String DICT_CACHE_NAME = "dataDictionaryCache";//数据字典所在的cache名

    public static final String TRADING_DATA_DICTIONARY = "tradingDataDictionary";//trading数据字典在cache中的名称
    public static final String PUBLIC_DATA_DICTIONARY = "publicDataDictionary";//PUBLIC数据字典在cache中的名称
    public static final String PUBLIC_USER_CONFIG = "publicUserConfig";//用户配置数据字典在cache中的名称

    /**站点字典*/
    public static final String DATA_DICT_SITE ="site";//数据字典站点数据

    public static final String DATA_DICT_DELTA ="delta";//地区

    public static final String DATA_DICT_COUNTRY ="country";//国家

    public static final String DATA_DICT_SHIPPING_TYPE ="domestic transportation";//国内运输方式

    public static final String DATA_DICT_SHIPPINGINTER_TYPE ="International transport";//国际运输方式

    public static final String DATA_DICT_SHIPPINGPACKAGE ="ShippingPackage";//包裹大小

    public static final String PUBLIC_DATA_DICT_PAYPAL = "paypal";

    public static final String PUBLIC_DATA_DICT_EBAYACCOUNT = "ebayaccount";

    public static final String INVENTORY = "inventory";






    public static final Map<Object,String> dictNameMap=new HashMap<Object, String>();
    static {
        dictNameMap.put(TradingDataDictionary.class,TRADING_DATA_DICTIONARY);//trading字典表对应的缓存名称
        dictNameMap.put(PublicDataDict.class,PUBLIC_DATA_DICTIONARY);
        dictNameMap.put(PublicUserConfig.class,PUBLIC_USER_CONFIG);
    }
    public static final String RETURNS_ACCEPTED_OPTION = "ReturnsAcceptedOption";//数据字典在cache中的名称
    public static final String RETURNS_WITHIN_OPTION = "ReturnsWithinOption";//数据字典在cache中的名称
    public static final String REFUND_OPTION = "RefundOption";//数据字典在cache中的名称
    public static final String SHIPPING_COST_PAID = "ShippingCostPaidByOption";//数据字典在cache中的名称
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
    /**移除publish数据字典*/
    public static void removePublicDictCache(){
        Cache cache = cacheManager.getCache(DICT_CACHE_NAME);
        cache.remove(PUBLIC_DATA_DICTIONARY);
    }
    /**获取userConfig数据字典*/
    public static List<PublicUserConfig> getPublicUserConfig(Long userID){
        if(userID==null || userID==0){return null;}
        Cache cache = cacheManager.getCache(DICT_CACHE_NAME);
        Element element=cache.get(PUBLIC_USER_CONFIG+userID.toString());
        if(element==null){return null;}
        return (List<PublicUserConfig>) element.getObjectValue();
    }
    /**获取userConfig数据字典*/
    public static void removePublicUserConfig(Long userID){
        if(userID==null || userID==0){
            SessionVO sessionVO=SessionCacheSupport.getSessionVO();
            userID=sessionVO.getId();
        }
        Cache cache = cacheManager.getCache(DICT_CACHE_NAME);
        cache.remove(PUBLIC_USER_CONFIG+userID.toString());
    }
    /**将数据集合放入缓存*/
    public static<T> void put(List<T> t,String... name){
        if (ObjectUtils.isLogicalNull(t)){return;}
        String cacheElementName=dictNameMap.get(t.get(0).getClass());
        Cache cache = cacheManager.getCache(DICT_CACHE_NAME);
        if(name!=null && name.length>0){
            Element element = new Element(cacheElementName+name[0],t);
            cache.put(element);
        }else {
            Element element = new Element(cacheElementName,t);
            cache.put(element);
        }

    }

    /**TradingDataDictionary字典表查询,用类型作为条件*/
    public static List<TradingDataDictionary> getTradingDataDictionaryByType(String type){
        ITradingDataDictionary dictionary = (ITradingDataDictionary) ApplicationContextUtil.getBean(ITradingDataDictionary.class);
        List<TradingDataDictionary> tradingDataDictionaries = dictionary.selectDictionaryByType(type);
        return tradingDataDictionaries;
    }
    /**TradingDataDictionary字典表查询,用map作为条件 todo*/
    public static List<TradingDataDictionary> getTradingDataDictionaryByMap(Map<String,String> map){
        ITradingDataDictionary dictionary = (ITradingDataDictionary) ApplicationContextUtil.getBean(ITradingDataDictionary.class);
        List<TradingDataDictionary> tradingDataDictionaries = dictionary.selectDictionaryByMap(map);
        return tradingDataDictionaries;
    }
    /**TradingDataDictionary字典表查询,用类型个parentid作为条件*/
    public static List<TradingDataDictionary> getTradingDataDictionaryByType(String type,final Long parentID){
        ITradingDataDictionary dictionary = (ITradingDataDictionary) ApplicationContextUtil.getBean(ITradingDataDictionary.class);
        List<TradingDataDictionary> tradingDataDictionaries = dictionary.selectDictionaryByType(type);
        //再根据parentID来筛选
        Collection<TradingDataDictionary> x= Collections2.filter(tradingDataDictionaries, new Predicate<TradingDataDictionary>() {
            @Override
            public boolean apply(TradingDataDictionary tradingDataDictionary) {
                return parentID==tradingDataDictionary.getParentId() || parentID.equals(tradingDataDictionary.getParentId());
            }
        });
        TradingDataDictionary[] tt = x.toArray(new TradingDataDictionary[]{});

        return Arrays.asList(tt);
    }

    /**TradingDataDictionary通过id去找*/
    public static TradingDataDictionary getTradingDataDictionaryByID(Long id){
        ITradingDataDictionary dictionary = (ITradingDataDictionary) ApplicationContextUtil.getBean(ITradingDataDictionary.class);
        TradingDataDictionary tradingDataDictionaries = dictionary.selectDictionaryByID(id);
        return tradingDataDictionaries;
    }


    /**PublicDataDict字典表查询,用map作为条件 todo*/
    public static List<PublicDataDict> getPublicDataDictionaryByMap(Map<String,String> map){
        ITradingDataDictionary dictionary = (ITradingDataDictionary) ApplicationContextUtil.getBean(ITradingDataDictionary.class);
        List<PublicDataDict> tradingDataDictionaries = dictionary.selectPublicDictionaryByMap(map);
        return tradingDataDictionaries;
    }
    /**PublicDataDict字典表查询,用类型作为条件*/
    public static List<PublicDataDict> getPublicDataDictionaryByType(String type){
        ITradingDataDictionary dictionary = (ITradingDataDictionary) ApplicationContextUtil.getBean(ITradingDataDictionary.class);
        List<PublicDataDict> tradingDataDictionaries = dictionary.selectPublicDataDictByType(type);

        return tradingDataDictionaries;
    }
    /**PublicDataDict通过id去找*/
    public static List<PublicDataDict> getPublicDataDictionaryByID(Long id){
        ITradingDataDictionary dictionary = (ITradingDataDictionary) ApplicationContextUtil.getBean(ITradingDataDictionary.class);
        List<PublicDataDict> tradingDataDictionaries = dictionary.selectPublicDictionaryByID(id);
        return tradingDataDictionaries;
    }

    /**PublicDataDict通过itemid去找*/
    public static PublicDataDict getPublicDataDictionaryByItemIDs(String itemid,String type){
        ITradingDataDictionary dictionary = (ITradingDataDictionary) ApplicationContextUtil.getBean(ITradingDataDictionary.class);
        List<PublicDataDict> tradingDataDictionaries = dictionary.selectPublicDictionaryByItemIDs(itemid,type);
        return tradingDataDictionaries.get(0);
    }
    /**PublicDataDict通过itemid去找*/
    public static List<PublicDataDict> getPublicDataDictionaryByitemID(Long id){
        ITradingDataDictionary dictionary = (ITradingDataDictionary) ApplicationContextUtil.getBean(ITradingDataDictionary.class);
        List<PublicDataDict> tradingDataDictionaries = dictionary.selectPublicDictionaryByItemID(id);
        return tradingDataDictionaries;
    }
    /**PublicDataDict通过parentid和type去找*/
    public static List<PublicDataDict> getPublicDataDictionaryByParentID(DictDataFilterParmVO vo){
        ITradingDataDictionary dictionary = (ITradingDataDictionary) ApplicationContextUtil.getBean(ITradingDataDictionary.class);
        List<PublicDataDict> tradingDataDictionaries = dictionary.selectPublicDictionaryByParentID(vo);
        return tradingDataDictionaries;
    }
    /**PublicDataDict通过parentid和level去找*/
    public static List<PublicDataDict> getPublicDataDictionaryByItemLevel(DictDataFilterParmVO v){
        ITradingDataDictionary dictionary = (ITradingDataDictionary) ApplicationContextUtil.getBean(ITradingDataDictionary.class);
        List<PublicDataDict> tradingDataDictionaries = dictionary.selectPublicDictionaryByItemLevel(v);
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
    /**PublicUserConfig字典表查询,用map作为条件*/
    public static List<PublicUserConfig> getUserConfigDictionaryByMap(Map<String,String> map){
        ITradingDataDictionary dictionary = (ITradingDataDictionary) ApplicationContextUtil.getBean(ITradingDataDictionary.class);
        List<PublicUserConfig> publicUserConfigs=dictionary.selectUserConfigDictionaryByMap(map);
        return publicUserConfigs;
    }
}
