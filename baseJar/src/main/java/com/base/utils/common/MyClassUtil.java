package com.base.utils.common;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/29.
 */
public class MyClassUtil {
    static Logger logger = Logger.getLogger(MyClassUtil.class);
    /** 初始化对象，如果按默认的方法构造失败，则忽略之 注意，clazzCollection必须包含一个无参数的构造方法 */
    public static <T> List<T> newInstance(Collection<Class<? extends T>> clazzCollection) {
        List<T> clazzList = new ArrayList<T>(clazzCollection.size());
        for(Class<? extends T> clazz : clazzCollection) {
            if(clazz.isInterface())
                continue;
            try {
                T t = clazz.newInstance();
                clazzList.add(t);
            } catch(Exception e) {
                logger.warn("默认构造失败!"+e.getMessage() + clazz.getName(), e);
                e.printStackTrace();
            }
        }
        return clazzList;
    }
}
