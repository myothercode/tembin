package com.base.utils.common;

import java.util.UUID;

/**
 * Created by Administrtor on 2014/7/23.
 */
public class UUIDUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
