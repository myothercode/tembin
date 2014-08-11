package com.base.utils.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrtor on 2014/8/11.
 */
public class MyCollectionsUtil {

    /**list去重，利用Set*/
    public static<T> List<T> listUnique(List<T> list){
        Set<T> set=new HashSet();
        for (T t:list){
            set.add(t);
        }
        return new ArrayList<T>(set);
    }
}
