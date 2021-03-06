package com.base.utils.common;

import com.base.database.publicd.model.PublicDataDict;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.domains.DictDataFilterParmVO;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultiset;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by Administrtor on 2014/7/28.
 * 数据字典的集合处理类
 */
public class DictCollectionsUtil {
    static Logger logger = Logger.getLogger(DictCollectionsUtil.class);

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
    public static<T> List<T> dataPublicDataCollectionsFilterByItemIDs(List<T> tList,String itemid,String type){
        if(ObjectUtils.isLogicalNull(tList)){return null;}
        return (List<T>)dataPublicCollectionsFilterByItemIDs((List<PublicDataDict>)tList,itemid,type);
    }
    public static<T> List<T> dataPublicDataCollectionsFilterByItemID(List<T> tList,Long type){
        if(ObjectUtils.isLogicalNull(tList)){return null;}
        return (List<T>)dataPublicCollectionsFilterByItemID((List<PublicDataDict>)tList,type);
    }
    public static<T> List<T> dataPublicDataCollectionsFilterByParentID(List<T> tList,DictDataFilterParmVO vo){
        if(ObjectUtils.isLogicalNull(tList)){return null;}
        return (List<T>)dataPublicCollectionsFilterByParentID((List<PublicDataDict>)tList,vo);
    }
    public static<T> List<T> dataPublicDataCollectionsFilterByItemLevel(List<T> tList,DictDataFilterParmVO v){
        if(ObjectUtils.isLogicalNull(tList)){return null;}
        return (List<T>)dataPublicCollectionsFilterByItemLevel((List<PublicDataDict>)tList,v);
    }

    /**根据字典中的类型筛选出指定条件的list<TradingDataDictionary>对象*/
    public static List<TradingDataDictionary> dataTradingCollectionFilterByType(List<TradingDataDictionary> ts,final String type){
        if(ts==null || ts.isEmpty()){return null;}
        Collection<TradingDataDictionary> x= Collections2.filter(ts, new Predicate<TradingDataDictionary>() {
             @Override
             public boolean apply(TradingDataDictionary tradingDataDictionary) {
                 return type.equalsIgnoreCase(tradingDataDictionary.getType());
             }
         });
        TradingDataDictionary[] tt = x.toArray(new TradingDataDictionary[]{});
       return Arrays.asList(tt);
    }

    /**根据map中的条件来筛选publicDataDictionary对象集合*/
    public static List<PublicDataDict> publicDataDictCollectionFilterByMap(List<PublicDataDict> ts,final Map<String , String> map){
        if(ts==null || ts.isEmpty()){return null;}
        Collection<PublicDataDict> x= Collections2.filter(ts, new Predicate<PublicDataDict>() {
            @Override
            public boolean apply(PublicDataDict tradingDataDictionary) {
                boolean b=true;
                for (Map.Entry<String,String> entry : map.entrySet()){
                    String key=entry.getKey();
                    String value = entry.getValue();
                    if(StringUtils.isEmpty(key) || StringUtils.isEmpty(value)){
                        logger.error("publicDataDictionary出错！key:"+key+",value:"+value);
                        b=false;
                        break;
                    }
                    try {
                        String v1 = BeanUtils.getSimpleProperty(tradingDataDictionary,key);
                        if(StringUtils.isEmpty(v1) || !value.equalsIgnoreCase(v1)){
                            b=false;
                            break;
                        }
                    }catch (NoSuchMethodException e1){
                        b=false;
                        break;
                    }catch (Exception e) {
                        logger.error("获取属性出错",e);
                        b=false;
                        break;
                    }
                }
                return b;
            }
        });
        PublicDataDict[] tt = x.toArray(new PublicDataDict[]{});
        return Arrays.asList(tt);
    }


    /**根据map中的条件来筛选TradingDataDictionary对象集合*/
    public static List<TradingDataDictionary> dataTradingCollectionFilterByMap(List<TradingDataDictionary> ts,final Map<String , String> map){
        if(ts==null || ts.isEmpty()){return null;}
        Collection<TradingDataDictionary> x= Collections2.filter(ts, new Predicate<TradingDataDictionary>() {
            @Override
            public boolean apply(TradingDataDictionary tradingDataDictionary) {
                boolean b=true;
                for (Map.Entry<String,String> entry : map.entrySet()){
                    String key=entry.getKey();
                    String value = entry.getValue();
                    if(StringUtils.isEmpty(key) || StringUtils.isEmpty(value)){
                        logger.error("dataTradingCollectionFilterByMap出错！key:"+key+",value:"+value);
                        b=false;
                        break;
                    }
                    try {
                        String v1 = BeanUtils.getSimpleProperty(tradingDataDictionary,key);
                        if(StringUtils.isEmpty(v1) || !value.equalsIgnoreCase(v1)){
                            b=false;
                            break;
                        }
                    }catch (NoSuchMethodException e1){
                        b=false;
                        break;
                    }catch (Exception e) {
                       logger.error("获取属性出错",e);
                        b=false;
                        break;
                    }
                }
                return b;
            }
        });
        TradingDataDictionary[] tt = x.toArray(new TradingDataDictionary[]{});
        return Arrays.asList(tt);
    }


    /**根据字典中的ID筛选出指定条件的list<TradingDataDictionary>对象*/
    public static TradingDataDictionary dataTradingCollectionFilterByID(List<TradingDataDictionary> ts,final Long id){
        if(ts==null || ts.isEmpty()){return null;}
        Collection<TradingDataDictionary> x= Collections2.filter(ts, new Predicate<TradingDataDictionary>() {
            @Override
            public boolean apply(TradingDataDictionary tradingDataDictionary) {
                return id==tradingDataDictionary.getId()||id.equals(tradingDataDictionary.getId());
            }
        });
        if(x==null || x.isEmpty()){return null;}
        TradingDataDictionary[] tt = x.toArray(new TradingDataDictionary[]{});
        return Arrays.asList(tt).get(0);
    }

    /*=========================*/

    /**根据字典中的类型筛选出指定条件的list<publicDataDictionary>对象*/
    public static PublicDataDict dataPublicCollectionFilterByType(List<PublicDataDict> ts,final String type){
        if(ts==null || ts.isEmpty()){return null;}
        Collection<PublicDataDict> x= Collections2.filter(ts, new Predicate<PublicDataDict>() {
            @Override
            public boolean apply(PublicDataDict tradingDataDictionary) {
                return type.equalsIgnoreCase(tradingDataDictionary.getItemType());
            }
        });
        if(x==null || x.isEmpty()){return null;}
        PublicDataDict[] tt = x.toArray(new PublicDataDict[]{});
        return Arrays.asList(tt).get(0);
    }

    /**根据字典中的ID筛选出指定条件的<publicDataDictionary>对象*/
    public static List<PublicDataDict> dataPublicCollectionsFilterByID(List<PublicDataDict> ts,final Long id){
        if(ts==null || ts.isEmpty()){return null;}
        Collection<PublicDataDict> x= Collections2.filter(ts, new Predicate<PublicDataDict>() {
            @Override
            public boolean apply(PublicDataDict tradingDataDictionary) {
                return id==tradingDataDictionary.getId()||id.equals(tradingDataDictionary.getId());
            }
        });
        PublicDataDict[] tt = x.toArray(new PublicDataDict[]{});
        return Arrays.asList(tt);
    }

    /**根据字典中的ItemID筛选出指定条件的<publicDataDictionary>对象*/
    public static List<PublicDataDict> dataPublicCollectionsFilterByItemIDs(List<PublicDataDict> ts,final String itemId,final String type){
        if(ts==null || ts.isEmpty()){return null;}
        Collection<PublicDataDict> x= Collections2.filter(ts, new Predicate<PublicDataDict>() {
            @Override
            public boolean apply(PublicDataDict tradingDataDictionary) {
                return ((itemId==tradingDataDictionary.getItemId()||itemId.equals(tradingDataDictionary.getItemId()))&& type.equalsIgnoreCase(tradingDataDictionary.getItemType()));
            }
        });
        PublicDataDict[] tt = x.toArray(new PublicDataDict[]{});
        return Arrays.asList(tt);
    }

    /**根据字典中的itemID筛选出指定条件的<publicDataDictionary>对象*/
    public static List<PublicDataDict> dataPublicCollectionsFilterByItemID(List<PublicDataDict> ts,final Long id){
        if(ts==null || ts.isEmpty()){return null;}
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
    public static List<PublicDataDict> dataPublicCollectionsFilterByParentID(List<PublicDataDict> ts,DictDataFilterParmVO vo){
        if(ts==null || ts.isEmpty()){return null;}
        final Long id=vo.getLongV1();
        final String itemType = vo.getStringV1();
        final String siteID = vo.getStringV2();
        Collection<PublicDataDict> x= Collections2.filter(ts, new Predicate<PublicDataDict>() {
            @Override
            public boolean apply(PublicDataDict tradingDataDictionary) {
                return (id==Long.parseLong(tradingDataDictionary.getItemParentId())
                        ||id.equals(Long.parseLong(tradingDataDictionary.getItemParentId())))
                        && siteID.equalsIgnoreCase(tradingDataDictionary.getSiteId())
                        && itemType.equalsIgnoreCase(tradingDataDictionary.getItemType());
            }
        });
        PublicDataDict[] tt = x.toArray(new PublicDataDict[]{});
        return Arrays.asList(tt);

    }
    /**根据字典中的parentID和itemlevel筛选出指定条件的<publicDataDictionary>对象*/
    public static List<PublicDataDict> dataPublicCollectionsFilterByItemLevel(List<PublicDataDict> ts,DictDataFilterParmVO v){
        if(ts==null || ts.isEmpty()){return null;}
        final Long id = v.getLongV1();
        final String itemLevel=v.getStringV1();
        final String itemType=v.getStringV2();
        final String siteID = v.getStringV3();
        Collection<PublicDataDict> x= Collections2.filter(ts, new Predicate<PublicDataDict>() {
            @Override
            public boolean apply(PublicDataDict tradingDataDictionary) {

                if(id==null || id==0L){
                    return (itemType.equalsIgnoreCase(tradingDataDictionary.getItemType())
                            && siteID.equalsIgnoreCase(tradingDataDictionary.getSiteId())
                            && itemLevel.equalsIgnoreCase(tradingDataDictionary.getItemLevel()));
                }
                else  if(StringUtils.isEmpty(itemLevel)||"0".equalsIgnoreCase(itemLevel)){
                    return itemType.equalsIgnoreCase(tradingDataDictionary.getItemType())
                            && siteID.equalsIgnoreCase(tradingDataDictionary.getSiteId())
                            && (id==Long.parseLong(tradingDataDictionary.getItemParentId())
                            ||id.equals(Long.parseLong(tradingDataDictionary.getItemParentId())));
                }
                else {
                    return (id==Long.parseLong(tradingDataDictionary.getItemParentId())
                            ||id.equals(Long.parseLong(tradingDataDictionary.getItemParentId())))
                            && siteID.equalsIgnoreCase(tradingDataDictionary.getSiteId())
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
        if(ts==null || ts.isEmpty()){return null;}
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
        if(ts==null || ts.isEmpty()){return null;}
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
        if(ts==null || ts.isEmpty()){return null;}
        Collection<PublicUserConfig> x= Collections2.filter(ts, new Predicate<PublicUserConfig>() {
            @Override
            public boolean apply(PublicUserConfig tradingDataDictionary) {
                return id==tradingDataDictionary.getId()||id.equals(tradingDataDictionary.getId());
            }
        });
        PublicUserConfig[] tt = x.toArray(new PublicUserConfig[]{});
        return Arrays.asList(tt).get(0);
    }

    /**根据map中的条件来筛选PublicUserConfig对象集合*/
    public static List<PublicUserConfig> dataUserConfigCollectionFilterByMap(List<PublicUserConfig> ts,final Map<String , String> map){
        if(ts==null || ts.isEmpty()){return null;}
        Collection<PublicUserConfig> x= Collections2.filter(ts, new Predicate<PublicUserConfig>() {
            @Override
            public boolean apply(PublicUserConfig publicUserConfig) {
                boolean b=true;
                for (Map.Entry<String,String> entry : map.entrySet()){
                    String key=entry.getKey();
                    String value = entry.getValue();
                    try {
                        String v1 = BeanUtils.getSimpleProperty(publicUserConfig,key);
                        if(StringUtils.isEmpty(v1) || !value.equalsIgnoreCase(v1)){
                            b=false;
                            break;
                        }
                    }catch (NoSuchMethodException e1){
                        b=false;
                        break;
                    }catch (Exception e) {
                        logger.error("获取属性出错",e);
                        b=false;
                        break;
                    }
                }
                return b;
            }
        });
        PublicUserConfig[] tt = x.toArray(new PublicUserConfig[]{});
        return Arrays.asList(tt);
    }



}
