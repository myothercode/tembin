package com.base.utils.cache;

import com.base.utils.applicationcontext.ApplicationContextUtil;
import net.sf.ehcache.CacheManager;
import org.springframework.context.ApplicationContext;

/**
 * Created by Administrtor on 2014/7/28.
 */
public class CacheBaseSupport {
    public static CacheManager cacheManager;
    static {
        ApplicationContext x= ApplicationContextUtil.getContext();
        cacheManager= (CacheManager) x.getBean(CacheManager.class);
    }
}
