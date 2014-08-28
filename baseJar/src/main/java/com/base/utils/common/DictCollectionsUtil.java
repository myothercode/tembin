package com.base.utils.common;

import com.base.database.publicd.model.PublicDataDict;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.TradingDataDictionary;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultiset;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created by Administrtor on 2014/7/28.
 * 数据字典的集合处理类
 */
public class DictCollectionsUtil {
    /**publisc表的itemtype*/
    public static final String categorySpecifics="categorySpecifics";
    /**商品类别*/
    public static final String category="category";

    /**菜单级别*/
    public static final String ITEM_LEVEL_ONE="1";
    public static final String ITEM_LEVEL_TWO="2";
    public static final String ITEM_LEVEL_THREE="3";
    public static final String ITEM_LEVEL_FOUR="4";

    public static final Map<Object,Integer> dictNameMap=new HashMap<Object, Integer>();
    static {
        dictNameMap.put(TradingDataDictionary.class,1);//trading字典表对应的缓存名称
        dictNameMap.put(PublicDataDict.class,2);
        dictNameMap.put(PublicUserConfig.class,3);
    }


    public static<T> List<T> dataCollectionFilterByType(List<T> tList,String type,Object... o){
        if(ObjectUtils.isLogicalNull(tList)){return null;}
        int i=dictNameMap.get(tList.get(0).getClass());
        if(i==0){return null;}
        switch (i){
            case 1:{
                return (List<T>)dataTradingCollectionFilterByType((List<TradingDataDictionary>)tList,type);
            }
            case 2:{
                return (List<T>)dataPublicCollectionFilterByType((List<PublicDataDict>)tList,type);
            }
            case 3:{
                if(ObjectUtils.isLogicalNull(o)){return null;}
                return (List<T>)dataUserConfigCollectionFilterByType((List<PublicUserConfig>)tList,type,(Long)o[0]);
            }
            default:{
                return null;
            }

        }
    }
    public static<T> T dataCollectionFilterByID(List<T> tList,Long type){
        if(ObjectUtils.isLogicalNull(tList)){return null;}
        int i=dictNameMap.get(tList.get(0).getClass());
        if(i==0){return null;}
        switch (i){
            case 1:{
                return (T)dataTradingCollectionFilterByID((List<TradingDataDictionary>)tList,type);
            }
            case 2:{
                return (T)dataPublicCollectionFilterByID((List<PublicDataDict>)tList,type);
            }
            case 3:{
                return (T)dataUserConfigCollectionFilterByID((List<PublicUserConfig>)tList,type);
            }
            default:{
                return null;
            }

        }
    }
    public static<T> List<T> dataPublicDataCollectionsFilterByID(List<T> tList,Long type){
        if(ObjectUtils.isLogicalNull(tList)){return null;}
        return (List<T>)dataPublicCollectionsFilterByID((List<PublicDataDict>)tList,type);
    }
    public static<T> List<T> dataPublicDataCollectionsFilterByItemID(List<T> tList,Long type){
        if(ObjectUtils.isLogicalNull(tList)){return null;}
        return (List<T>)dataPublicCollectionsFilterByItemID((List<PublicDataDict>)tList,type);
    }
    public static<T> List<T> dataPublicDataCollectionsFilterByParentID(List<T> tList,Long type,String itemType){
        if(ObjectUtils.isLogicalNull(tList)){return null;}
        return (List<T>)dataPublicCollectionsFilterByParentID((List<PublicDataDict>)tList,type,itemType);
    }
    public static<T> List<T> dataPublicDataCollectionsFilterByItemLevel(List<T> tList,Long type,String itemlevel,String itemType){
        if(ObjectUtils.isLogicalNull(tList)){return null;}
        return (List<T>)dataPublicCollectionsFilterByItemLevel((List<PublicDataDict>)tList,type,itemlevel,itemType);
    }

    /**根据字典中的类型筛选出指定条件的list<TradingDataDictionary>对象*/
    public static List<TradingDataDictionary> dataTradingCollectionFilterByType(List<TradingDataDictionary> ts,final String type){
         Collection<TradingDataDictionary> x= Collections2.filter(ts, new Predicate<TradingDataDictionary>() {
             @Override
             public boolean apply(TradingDataDictionary tradingDataDictionary) {
                 return type.equalsIgnoreCase(tradingDataDictionary.getType());
             }
         });
        TradingDataDictionary[] tt = x.toArray(new TradingDataDictionary[]{});
       return Arrays.asList(tt);
    }

    /**根据字典中的ID筛选出指定条件的list<TradingDataDictionary>对象*/
    public static TradingDataDictionary dataTradingCollectionFilterByID(List<TradingDataDictionary> ts,final Long id){
        Collection<TradingDataDictionary> x= Collections2.filter(ts, new Predicate<TradingDataDictionary>() {
            @Override
            public boolean apply(TradingDataDictionary tradingDataDictionary) {
                return id==tradingDataDictionary.getId()||id.equals(tradingDataDictionary.getId());
            }
        });
        TradingDataDictionary[] tt = x.toArray(new TradingDataDictionary[]{});
        return Arrays.asList(tt).get(0);
    }

    /*=========================*/

    /**根据字典中的类型筛选出指定条件的list<publicDataDictionary>对象*/
    public static PublicDataDict dataPublicCollectionFilterByType(List<PublicDataDict> ts,final String type){
        Collection<PublicDataDict> x= Collections2.filter(ts, new Predicate<PublicDataDict>() {
            @Override
            public boolean apply(PublicDataDict tradingDataDictionary) {
                return type.equalsIgnoreCase(tradingDataDictionary.getItemType());
            }
        });
        PublicDataDict[] tt = x.toArray(new PublicDataDict[]{});
        return Arrays.asList(tt).get(0);
    }

    /**根据字典中的ID筛选出指定条件的<publicDataDictionary>对象*/
    public static List<PublicDataDict> dataPublicCollectionsFilterByID(List<PublicDataDict> ts,final Long id){
        Collection<PublicDataDict> x= Collections2.filter(ts, new Predicate<PublicDataDict>() {
            @Override
            public boolean apply(PublicDataDict tradingDataDictionary) {
                return id==tradingDataDictionary.getId()||id.equals(tradingDataDictionary.getId());
            }
        });
        PublicDataDict[] tt = x.toArray(new PublicDataDict[]{});
        return Arrays.asList(tt);
    }

    /**根据字典中的itemID筛选出指定条件的<publicDataDictionary>对象*/
    public static List<PublicDataDict> dataPublicCollectionsFilterByItemID(List<PublicDataDict> ts,final Long id){
        Collection<PublicDataDict> x= Collections2.filter(ts, new Predicate<PublicDataDict>() {
            @Override
            public boolean apply(PublicDataDict tradingDataDictionary) {
                return id==Long.parseLong(tradingDataDictionary.getItemId())||id.equals(Long.parseLong(tradingDataDictionary.getItemParentId()));
            }
        });
        PublicDataDict[] tt = x.toArray(new PublicDataDict[]{});
        return Arrays.asList(tt);
    }

    /**根据字典中的parentID和itemtype筛选出指定条件的<publicDataDictionary>对象*/
    public static List<PublicDataDict> dataPublicCollectionsFilterByParentID(List<PublicDataDict> ts,final Long id,final String itemType){
        Collection<PublicDataDict> x= Collections2.filter(ts, new Predicate<PublicDataDict>() {
            @Override
            public boolean apply(PublicDataDict tradingDataDictionary) {
                return (id==Long.parseLong(tradingDataDictionary.getItemParentId())
                        ||id.equals(Long.parseLong(tradingDataDictionary.getItemParentId())))
                        && itemType.equalsIgnoreCase(tradingDataDictionary.getItemType());
            }
        });
        PublicDataDict[] tt = x.toArray(new PublicDataDict[]{});
        return Arrays.asList(tt);

    }
    /**根据字典中的parentID和itemlevel筛选出指定条件的<publicDataDictionary>对象*/
    public static List<PublicDataDict> dataPublicCollectionsFilterByItemLevel(List<PublicDataDict> ts,final Long id,final String itemLevel,final String itemType){
        Collection<PublicDataDict> x= Collections2.filter(ts, new Predicate<PublicDataDict>() {
            @Override
            public boolean apply(PublicDataDict tradingDataDictionary) {

                if(id==null || id==0L){
                    return (itemType.equalsIgnoreCase(tradingDataDictionary.getItemType())
                            && itemLevel.equalsIgnoreCase(tradingDataDictionary.getItemLevel()));
                }
                else  if(StringUtils.isEmpty(itemLevel)||"0".equalsIgnoreCase(itemLevel)){
                    return itemType.equalsIgnoreCase(tradingDataDictionary.getItemType())
                            && (id==Long.parseLong(tradingDataDictionary.getItemParentId())
                            ||id.equals(Long.parseLong(tradingDataDictionary.getItemParentId())));
                }
                else {
                    return (id==Long.parseLong(tradingDataDictionary.getItemParentId())
                            ||id.equals(Long.parseLong(tradingDataDictionary.getItemParentId())))
                            && itemLevel.equalsIgnoreCase(tradingDataDictionary.getItemLevel())
                            && itemType.equalsIgnoreCase(tradingDataDictionary.getItemType());
                }

            }
        });
        PublicDataDict[] tt = x.toArray(new PublicDataDict[]{});
        return Arrays.asList(tt);

    }

    /**根据字典中的ID筛选出指定条件的<publicDataDictionary>对象*/
    public static PublicDataDict dataPublicCollectionFilterByID(List<PublicDataDict> ts,final Long id){
        Collection<PublicDataDict> x= Collections2.filter(ts, new Predicate<PublicDataDict>() {
            @Override
            public boolean apply(PublicDataDict tradingDataDictionary) {
                return id==tradingDataDictionary.getId()||id.equals(tradingDataDictionary.getId());
            }
        });
        PublicDataDict[] tt = x.toArray(new PublicDataDict[]{});
        return Arrays.asList(tt).get(0);
    }

    /*====================*/
    /**根据字典中的类型和用户id筛选出指定条件的list<PublicUserConfig>对象*/
    public static List<PublicUserConfig> dataUserConfigCollectionFilterByType(List<PublicUserConfig> ts,final String type,final Long userId){
        Collection<PublicUserConfig> x= Collections2.filter(ts, new Predicate<PublicUserConfig>() {
            @Override
            public boolean apply(PublicUserConfig tradingDataDictionary) {
                return type.equalsIgnoreCase(tradingDataDictionary.getConfigType()) &&(userId==tradingDataDictionary.getUserId()||userId.equals(tradingDataDictionary.getUserId()));
            }
        });
        PublicUserConfig[] tt = x.toArray(new PublicUserConfig[]{});
        return Arrays.asList(tt);
    }

    /**根据字典中的ID筛选出指定条件的<PublicUserConfig>对象*/
    public static PublicUserConfig dataUserConfigCollectionFilterByID(List<PublicUserConfig> ts,final Long id){
        Collection<PublicUserConfig> x= Collections2.filter(ts, new Predicate<PublicUserConfig>() {
            @Override
            public boolean apply(PublicUserConfig tradingDataDictionary) {
                return id==tradingDataDictionary.getId()||id.equals(tradingDataDictionary.getId());
            }
        });
        PublicUserConfig[] tt = x.toArray(new PublicUserConfig[]{});
        return Arrays.asList(tt).get(0);
    }
}
