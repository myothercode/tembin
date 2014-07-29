package com.common.interceptor.randr;

import com.base.utils.applicationcontext.RequestResponseContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wula on 2014/7/6.
 * 设置request和response，供以后使用
 */
public class RequestAndResponseInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
        RequestResponseContext.setRequest(request);
        RequestResponseContext.setResponse(response);
        return true;
    }

}
