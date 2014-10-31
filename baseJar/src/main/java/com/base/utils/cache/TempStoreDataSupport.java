package com.base.utils.cache;

import com.base.utils.common.ObjectUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * Created by Administrator on 2014/10/29.
 * 临时数据缓存工具类，该缓存只占用很短的时间
 */
public class TempStoreDataSupport extends CacheBaseSupport {
    public static final String TEMP_DATA_CACHE="tempStoreData"; //临时数据所占用的缓存名

    /**放入数据*/
    public static <T> void pushData(String key,T t){
        Cache cache =cacheManager.getCache(TEMP_DATA_CACHE);
        Element element=new Element(key+"_session",t);
        cache.put(element);
    }

    /**取出数据*/
    public static <T> T pullData(String key){
        Cache cache =cacheManager.getCache(TEMP_DATA_CACHE);
        Element element=cache.get(key+"_session");
        if(ObjectUtils.isLogicalNull(element)){
            return null;
        }
        return (T) element.getObjectValue();
    }

    /**移除数据*/
    public static void removeData(String key){
        Cache cache =cacheManager.getCache(TEMP_DATA_CACHE);
        cache.remove(key+"_session");
    }
}
