package com.common.interceptor.randr;

import com.base.domains.SessionVO;
import com.base.utils.cache.SessionCacheSupport;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrtor on 2014/7/23.
 * 权限拦截器
 */
public class AuthorityInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception{
        String currURL=request.getRequestURI();
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        return true;

    }



}
