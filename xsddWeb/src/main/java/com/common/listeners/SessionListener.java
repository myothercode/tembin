package com.common.listeners;

import com.base.utils.cache.SessionCacheSupport;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by Administrtor on 2014/7/20.
 * session监听器
 * 坚挺session的创建和销毁
 */
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        /**销毁session的时候，将缓存数据也清除掉*/
        String loginKey=(String) httpSessionEvent.getSession().getAttribute(SessionCacheSupport.USERLOGINID);
        if (StringUtils.isNotEmpty(loginKey)){
            SessionCacheSupport.remove(loginKey);
        }
    }
}
