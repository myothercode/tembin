package com.base.utils.common;

import com.google.common.base.Function;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Administrtor on 2014/7/31.
 * 数组工具
 */
public class MyArrayUtil {

    /**合并数组*/
    public static <T> T[] concatAllArr(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    //去除数组中重复的记录
    public static<T> T[] array_unique(T[] a) {
        // array_unique
        Set<T> set = new LinkedHashSet<T>();
        for(int i = 0; i < a.length; i++) {
         set.add(a[i]);
        }
        return (T[])set.toArray();
    }

    /**将一个类型的数组转为另一个类型的数组*/
    public static <S, D> D[] transform(S[] ts, Class<D> clazz, Function<S, D> function) {
        if(ObjectUtils.isLogicalNull(ts))
            return (D[]) Array.newInstance(clazz, 0);
        D[] ds = (D[]) Array.newInstance(clazz, ts.length);
        for(int i = 0;i < ts.length;i++) {
            D d = function.apply(ts[i]);
            ds[i] = d;
        }
        return ds;
    }
}
