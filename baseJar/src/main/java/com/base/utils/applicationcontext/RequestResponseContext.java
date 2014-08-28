package com.base.utils.applicationcontext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wula on 2014/7/6.
 * 获取request和response的工具
 */
public class RequestResponseContext {

    private static ThreadLocal<HttpServletRequest> request_threadLocal = new ThreadLocal<HttpServletRequest>();

    private static ThreadLocal<HttpServletResponse> reponse_threadLocal = new ThreadLocal<HttpServletResponse>();

    public static void setRequest(HttpServletRequest request) {
        request_threadLocal.set(request);
    }

    public static HttpServletRequest getRequest() {
        return request_threadLocal.get();
    }

    public static void removeRequest() {
        request_threadLocal.remove();
    }

    public static void setResponse(HttpServletResponse response) {
        reponse_threadLocal.set(response);
    }

    public static HttpServletResponse getResponse() {
        return reponse_threadLocal.get();
    }

    public static void removeResponse() {
        reponse_threadLocal.remove();
    }
}
