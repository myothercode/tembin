/** Created by flym at 13-3-8 */
package com.base.utils.common;



import com.base.domains.SessionVO;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.exception.Asserts;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
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

    /**获取一个实体类所有的字段，包括被继承的*/
    public static  <T> Field[] getAllFieldInClassAndExtend(T t){
        Field[] fields = t.getClass().getDeclaredFields();//得到本类的字段
        GetExtendFieldsClass g=new GetExtendFieldsClass();
        g.getExtendClassFields(t.getClass());
        Field[] ga=g.get();

        Field[] fields1=MyArrayUtil.concatAllArr(fields,ga);
        return fields1;

    }

    /**insert的时候为指定对象塞入基本信息值*/
    public static<T> void toInitPojoForInsert(T t) throws Exception {
        Field[] fs= getAllFieldInClassAndExtend(t);
        for (Field f:fs){
            String name = f.getName();
            if (name.equalsIgnoreCase("uuid")){
                PropertyUtils.setSimpleProperty(t,name,UUIDUtil.getUUID());
            }else if (name.equalsIgnoreCase("createTime")){
                PropertyUtils.setSimpleProperty(t,name,new Date());
            }else if (name.equalsIgnoreCase("createUser")){
                String v = BeanUtils.getSimpleProperty(t,name);
                if(StringUtils.isEmpty(v)) {
                    PropertyUtils.setSimpleProperty(t, name, SessionCacheSupport.getSessionVO().getId());
                }
            }else{
                continue;
            }
        }
    }

    /**验证当前用户是否与想要修改的记录创建者一致*/
    public static boolean valiUpdate(long createUserID,Class c,long itemID,String... type){
        if(!ObjectUtils.isLogicalNull(type)){
            if("Synchronize".equalsIgnoreCase(type[0])){
                return true;
            }
        }
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        Asserts.assertTrue(createUserID == sessionVO.getId(),"您没有权限修改");
        return true;
    }


}
