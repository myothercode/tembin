package com.common.aspect;

import org.apache.commons.lang.reflect.MethodUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2014/11/21.
 * 记录多线程任务的日志---无效，不采用了
 */
//@Aspect
//@Component
public class AspectJRunLogger {
    static Logger logger = Logger.getLogger(AspectJRunLogger.class);
    /**声明切入点*/
    public static final String EDP = "execution(* com.base.utils.scheduleabout.commontask.*.*(..))";
    /**所有实现了Scheduledable接口的类任意方法的执行*/
    public static final String ScheduledableEDP="execution(* com.base.utils.scheduleabout.Scheduledable.*(..)) ";


    @Around(EDP) //spring中Around通知
    public Object logAround(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Object obj = null;
        try {
            obj = joinPoint.proceed(args);
            Object o= joinPoint.getThis();
            Object oo = MethodUtils.invokeMethod(o, "getScheduledType", null);
            logger.info(oo);
        } catch (Throwable throwable) {
            logger.error("执行定时任务出现错误!",throwable);
        }
        return obj;
    }
}
