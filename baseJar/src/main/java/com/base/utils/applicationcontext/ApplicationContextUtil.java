package com.base.utils.applicationcontext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by wula on 2014/7/6.
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext context;
    //声明一个静态变量保存
    public void setApplicationContext(ApplicationContext contex) throws BeansException {
        this.context = contex;
    }
    public static ApplicationContext getContext() {
        return context;
    }
    public final static Object getBean(String beanName) {
        return context.getBean(beanName);
    }
    public final static Object getBean(String beanName, Class<?> requiredType) {
        return context.getBean(beanName, requiredType);
    }
    public final static Object getBean(Class<?> c){
        return context.getBean(c);
    }
}
