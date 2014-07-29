/** Created by flym at 13-3-8 */
package com.base.utils.common;



import com.base.database.trading.model.TradingCharity;
import com.base.utils.cache.SessionCacheSupport;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;


/**
 * 对象处理工具类
 *
 * @author flym
 */
public class ObjectUtils {

	/** 判断一个对象在业务上是否为空的 */
	public static boolean isLogicalNull(Object obj) {
		if(obj == null)
			return true;

		//数字
		if(obj instanceof Number) {
			return ((Number) obj).doubleValue() == .0;
		}

		//字符串
		if(obj instanceof CharSequence)
			return !org.springframework.util.StringUtils.hasText((CharSequence) obj);

		//集合
		if(obj instanceof Collection)
			return ((Collection) obj).isEmpty();

		//数组
		if(obj.getClass().isArray())
			return Array.getLength(obj) == 0;

		return false;
	}


    /**为指定对象塞入固定值*/
    public static<T> void toPojo(T t) throws Exception {
        Field[] fs= t.getClass().getDeclaredFields();
        for (Field f:fs){
            String name = f.getName();
            if (name.equalsIgnoreCase("uuid")){
                PropertyUtils.setSimpleProperty(t,name,UUIDUtil.getUUID());
            }else if (name.equalsIgnoreCase("createTime")){
                PropertyUtils.setSimpleProperty(t,name,new Date());
            }else if (name.equalsIgnoreCase("createUser")){
                PropertyUtils.setSimpleProperty(t,name, SessionCacheSupport.getSessionVO().getId());
            }else{
                continue;
            }
        }
    }



}
