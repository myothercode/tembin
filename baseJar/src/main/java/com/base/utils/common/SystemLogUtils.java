package com.base.utils.common;

import com.base.database.customtrading.mapper.SystemLogQueryMapper;
import com.base.database.userinfo.mapper.SystemLogMapper;
import com.base.database.userinfo.model.SystemLog;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.SystemLogQuery;
import com.base.mybatis.page.Page;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.applicationcontext.RequestResponseContext;
import com.base.utils.cache.SessionCacheSupport;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/10/14.
 * 记录系统日志，包括用户操作等等
 */
public class SystemLogUtils {

    public static final String FIND_PASSWORD="findPassword";//找回密码事件
    public static final String NO_API_Message="noApiMessage";//不系统通知的时候记录失败日志
    public static final String ORDER_OPERATE_RECORD="orderOperateRecord";//订单操作记录

    /**祝需要传入三个值
     * log.setEventname();
     log.setOperuser();
     log.setEventdesc();*/
    public static void saveLog(SystemLog systemLog) throws Exception {
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        systemLog.setOperuser(sessionVO!=null?sessionVO.getLoginId():systemLog.getOperuser());
        systemLog.setCreatedate(new Date());

        HttpServletRequest request=RequestResponseContext.getRequest();
        if(request!=null){
            systemLog.setEventdesc(systemLog.getEventdesc()+";客户端ip是:"+getIpAddr(request));
        }

        SystemLogMapper systemLogMapper = (SystemLogMapper) ApplicationContextUtil.getBean(SystemLogMapper.class);
        systemLogMapper.insert(systemLog);
    }

    /**获取ip*/
    public static String getIpAddr(HttpServletRequest request) throws Exception {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if(ip != null && ip.equalsIgnoreCase("0:0:0:0:0:0:0:1"))
        {
            ip = InetAddress.getLocalHost().getHostAddress().toString();
        }

        if (ip == null || ip.length() == 0 || "X-Real-IP".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        return ip;

    }
    public static List<SystemLogQuery> selectSystemLogList(Map map,Page page){
        SystemLogQueryMapper systemLogQueryMapper = (SystemLogQueryMapper) ApplicationContextUtil.getBean(SystemLogQueryMapper.class);
        List<SystemLogQuery> list=systemLogQueryMapper.selectSystemLogList(map,page);
        return list;
    }

}
