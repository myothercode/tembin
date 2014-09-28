package com.base.utils.myheaderUtil;

import org.apache.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/25.
 */
public class MyHeaderUtil {

    /**map转成herder*/
    public static List<BasicHeader> map2Header(Map<String,String> map){
        List<BasicHeader> headers = new ArrayList<BasicHeader>();
        for (Map.Entry<String, String> entry: map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            headers.add(new BasicHeader(key,value));
        }
        return headers;
    }
}
