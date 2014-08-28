package com.common.interceptor.randr;

import com.base.domains.SessionVO;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.applicationcontext.RequestResponseContext;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.UUIDUtil;
import com.common.base.utils.ajax.AjaxSupport;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by wula on 2014/7/6.
 * 设置request和response，供以后使用
 * 以及增加防重复提交验证
 */
public class RequestAndResponseInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = Logger.getLogger(RequestAndResponseInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
        RequestResponseContext.setRequest(request);
        RequestResponseContext.setResponse(response);

        //防重复提交验证
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        if(sessionVO!=null){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            AvoidDuplicateSubmission annotation = method.getAnnotation(AvoidDuplicateSubmission.class);
            if (annotation != null) {
                boolean needSaveSession = annotation.needSaveToken();
                if (needSaveSession) {
                    request.getSession(false).setAttribute("_token", sessionVO.getLoginId()+UUIDUtil.getUUID());
                }

                boolean needRemoveSession = annotation.needRemoveToken();
                if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                        logger.warn("please don't repeat submit,[user:" + sessionVO.getLoginId() + ",url:"
                                + request.getServletPath() + "]");
                        AjaxSupport.sendFailText("提交失败","请不要重复提交");
                        return false;
                    }
                    request.getSession(false).removeAttribute("_token");
                }
            }
        }
        return true;


    }

    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession(false).getAttribute("_token");
        if (serverToken == null) {
            return true;
        }
        String clinetToken = request.getParameter("_token");
        if (clinetToken == null) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }

}
