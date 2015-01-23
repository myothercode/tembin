package com.base.utils.common;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/11.
 */
public class MyCollectionsUtil {

    /**list去重，利用Set*/
    public static<T> List<T> listUnique(List<T> list){
        if (list==null){return list;}
        Set<T> set=new LinkedHashSet<T>();
        for (T t:list){
            set.add(t);
        }
        return new ArrayList<T>(set);
    }
}
