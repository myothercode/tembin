package com.common.base.utils.json;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

/**
 * Created by wula on 2014/7/6.
 */
public class JsonUtils {
    public static <T> String toBrowserJson(T obj, List<String> includePropertyList) {
        JSONSerializer jsonSerializer = new JSONContextableSerializer();
        jsonSerializer.config(SerializerFeature.WriteNullStringAsEmpty, true);
        jsonSerializer.config(SerializerFeature.WriteDateUseDateFormat, true);
        jsonSerializer.config(SerializerFeature.WriteMapNullValue, true);
        jsonSerializer.config(SerializerFeature.WriteNullListAsEmpty, true);
        jsonSerializer.config(SerializerFeature.WriteNullBooleanAsFalse, true);
        jsonSerializer.config(SerializerFeature.DisableCircularReferenceDetect, true);

        jsonSerializer.write(obj);
        String text = jsonSerializer.getWriter().toString();
        return text;
    }

}
