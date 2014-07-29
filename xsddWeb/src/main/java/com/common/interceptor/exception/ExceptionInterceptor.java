package com.common.interceptor.exception;

import com.base.utils.exception.AssertsException;
import com.common.base.utils.ajax.AjaxSupport;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * Created by wula on 2014/7/6.
 */
public class ExceptionInterceptor  extends HandlerInterceptorAdapter {
    static Logger log= Logger.getLogger(ExceptionInterceptor.class);
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        logger(request, handler, ex);
        response.setStatus(response.SC_SERVICE_UNAVAILABLE);
        if(ex!=null){
            AjaxSupport.sendFailText("出现错误！",ex.getMessage());
        }

    }

    public void logger(HttpServletRequest request,Object handler, Exception ex){
        if(ex==null){
            return;
        }
        if (ex instanceof AssertsException){ //如果是断言异常，不处理
            return;
        }
        StringBuffer msg = new StringBuffer();
        msg.append("异常拦截日志");
        msg.append("[uri＝").append(request.getRequestURI()).append("]");
        Enumeration<String> enumer= request.getParameterNames();
        while (enumer.hasMoreElements()) {
            String name = (String) enumer.nextElement();
            String[] values = request.getParameterValues(name);
            msg.append("[").append(name).append("=");
            if(values != null){
                int i=0;
                for(String value: values){
                    i++;
                    msg.append(value);
                    if(i < values.length){
                        msg.append("｜");
                    }

                }
            }
            msg.append("]");
        }
        log.error(msg,ex);
    }
}
