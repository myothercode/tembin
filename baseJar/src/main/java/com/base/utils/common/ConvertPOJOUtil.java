package com.base.utils.common;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrtor on 2014/7/22.
 * 实体类转换赋值工具
 */
public class ConvertPOJOUtil {

    /**需要排除的不转换的字段*/
    public static List<String> excludeNames=new ArrayList<String>();
    static {
        excludeNames.add("id");
        excludeNames.add("parentid");
        excludeNames.add("parentuuid");
        excludeNames.add("createtime");
        excludeNames.add("createuser");
    }

    /**忽略掉源值为空的字段*/
    public static <T,S> void convertIgnoreNull(T t1,S t2) throws Exception {
        convert(t1,t2,"ignore");
    }

    /**覆盖目标值，去过源值是空，那么会空值去覆盖*/
    public static <T,S> void convert(T t1,S t2,String... mark) throws Exception {
        if(t2==null){return;}
        Field[] targetfs = t1.getClass().getDeclaredFields();
        for (Field f : targetfs) {
            String name = f.getName();
            if(excludeNames.contains(name.toLowerCase())){//如果在排除列表内，那么忽略
              continue;
            }
            Method t2readMethod = getReadMethodName(name, t2);//获取有值的实体类读方法名
            if (t2readMethod == null) {//如果没有这个属性，那么继续
                continue;
            }
            Object o = t2readMethod.invoke(t2);//获取到值

            //然后将该值写入无值的实体类
            Method t1WriteMethod = getWriteMethodName(name, t1);
            if(t1WriteMethod==null){continue;}
            if(o==null){
                if(mark==null || mark.length==0){
                    t1WriteMethod.invoke(t1,new Object[]{ null });//如果源值是空，那么就将目标值也设置成空
                }
                continue;
            }
            Class co=o.getClass();
            Class[] cs = t1WriteMethod.getParameterTypes();
            Class ct=cs[0];
            Object oo = null;
            try {
                oo = ConvertUtil.convert(ct, o);
            } catch (Exception e) {
               throw new RuntimeException("转换字段"+name+"出错!"+o.getClass().getName()+"转为"+ct.getName()+">>>"+e.getMessage(),e);
            }

            if(oo==null){
                continue;
            }
            t1WriteMethod.invoke(t1,oo);
        }
    }

    /**找到读取属性的方法*/
   private static<T> Method getReadMethodName(String name,T t){
      PropertyDescriptor[] s= BeanUtils.getPropertyDescriptors(t.getClass());
       for (PropertyDescriptor p:s){
           if (name.equalsIgnoreCase(p.getName())){
               return p.getReadMethod();
           }
       }
       return null;
   }
    /**获取写值的方法*/
    private static <T> Method getWriteMethodName(String name ,T t){
        PropertyDescriptor[] s= BeanUtils.getPropertyDescriptors(t.getClass());
        for (PropertyDescriptor p:s){
            if (name.equalsIgnoreCase(p.getName())){
                return p.getWriteMethod();
            }
        }
        return null;
    }


}
