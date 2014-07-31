package com.base.utils.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrtor on 2014/7/31.
 * 获取被继承类
 */
public class GetExtendFieldsClass {
    private List<Field[]> fieldsList=new ArrayList<Field[]>();

    public void getExtendClassFields(Class c){
        if(!c.getSuperclass().equals(Object.class)){
            fieldsList.add(c.getSuperclass().getDeclaredFields());
            getExtendClassFields(c.getSuperclass());
        }

    }

    public Field[] get(){
        Field[] f=new Field[]{};
        for (Field[] fa :fieldsList){
            f=MyArrayUtil.concatAllArr(f,fa);
        }
        return f;
    }

}
