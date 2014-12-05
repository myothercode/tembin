package com.base.utils.cache;

import com.base.domains.SessionVO;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.applicationcontext.RequestResponseContext;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrtor on 2014/7/21.
 * session缓存管理支持
 */
@Service
public class SessionCacheSupport extends CacheBaseSupport {
    private static final Log logger = LogFactory.getLog(SessionCacheSupport.class);
    private static final String sessionName="sessionCache";//使用的缓存库名
    public static final String USERLOGINID="userLoginId";//存放在session中的用户信息变量

    /*private static CacheManager cacheManager;
    static {
        ApplicationContext x= ApplicationContextUtil.getContext();
        cacheManager= (CacheManager) x.getBean(CacheManager.class);
    }*/


    /**产生缓存的主键key*/
    private static String generateKey(){
         HttpSession session = RequestResponseContext.getRequest().getSession();
        String userLoginId= (String) session.getAttribute(USERLOGINID);
        Asserts.assertTrue(StringUtils.isNotEmpty(userLoginId),"未获取到用户ID");
        return userLoginId;
    }
    /**放入值*/
    public static<T extends SessionVO> void put(T t){
        Cache cache =cacheManager.getCache(sessionName);
        Element element=new Element(generateKey(),t);
        cache.put(element);
    }
    /**取出值*/
    public static SessionVO get(String userLoginId){
        Cache cache =cacheManager.getCache(sessionName);
        Element element=cache.get(userLoginId);
        if(ObjectUtils.isLogicalNull(element)){
            return null;
        }
        return (SessionVO)element.getObjectValue();
    }

    /**移除值*/
    public static void remove(String userLoginId){
        Cache cache =cacheManager.getCache(sessionName);
        cache.remove(userLoginId);
    }

    /**获取当前用户session*/
    public static SessionVO getSessionVO(){
        if(RequestResponseContext.getRequest()==null){return null;}
        HttpSession session = RequestResponseContext.getRequest().getSession();
        return SessionCacheSupport.get((String) session.getAttribute(USERLOGINID));
    }


    /**放入其它值*/
    /**放入值*/
    public static<T> void putOther(String key ,T t){
        Cache cache =cacheManager.getCache(sessionName);
        Element element=new Element("other_"+key,t);
        cache.put(element);
    }
    /**取出值*/
    public static <T> T getOther(String key){
        Cache cache =cacheManager.getCache(sessionName);
        Element element=cache.get("other_"+key);
        if(ObjectUtils.isLogicalNull(element)){
            return null;
        }
        return (T)element.getObjectValue();
    }
    /**移除值*/
    public static void removeOther(String key){
        Cache cache =cacheManager.getCache(sessionName);
        cache.remove("other_"+key);
    }
}
