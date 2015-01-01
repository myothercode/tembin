package com.common.listeners;

import com.base.database.userinfo.model.SystemLog;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.SystemLogUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;

/**
 * Created by Administrtor on 2014/7/20.
 * session监听器
 * 坚挺session的创建和销毁
 */
public class SessionListener implements HttpSessionListener {
    static Logger logger = Logger.getLogger(SessionListener.class);
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        /**销毁session的时候，将缓存数据也清除掉*/
        String loginKey=(String) httpSessionEvent.getSession().getAttribute(SessionCacheSupport.USERLOGINID);
        if (StringUtils.isNotEmpty(loginKey)){
            SessionCacheSupport.removeCa(loginKey, httpSessionEvent.getSession().getId());
        }
        SystemLog systemLog=new SystemLog();
        systemLog.setCreatedate(new Date());
        systemLog.setEventname(SystemLogUtils.USER_UNLOGIN_LOG);
        systemLog.setOperuser(loginKey);
        try {
            systemLog.setEventdesc("用户session销毁！sessionID:"+httpSessionEvent.getSession().getId());
            SystemLogUtils.saveLog(systemLog);
        } catch (Exception e) {
            logger.error("记录登销毁志报错!",e);
        }
    }
}
