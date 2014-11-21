package com.base.utils.aspectj;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2014/11/21.
 * 日志拦截logger
 */
@Aspect
@Component
public class AspectJLogger {
    static Logger logger = Logger.getLogger(AspectJLogger.class);
    /**声明切入点*/
    public static final String EDP = "execution(* com.postcontrollers.impl.*.*(..))";
    @Around(EDP) //spring中Around通知
    public Object logAround(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Object obj = null;
        try {
            obj = joinPoint.proceed(args);
        } catch (Throwable throwable) {
            logger.error("调用api出现错误!",throwable);
        }
        return obj;
    }
}
