package com.base.utils.xmlutils;

import com.thoughtworks.xstream.XStream;

/**
 * Created by Administrtor on 2014/7/16.
 */
public class PojoXmlUtil {
    /**
     * xml pojo对像转换成xml
     * @param cc
     * @param <T>
     * @return
     */
    public static<T> String pojoToXml(T... cc){
        StringBuffer sb=new StringBuffer();
        for (T c : cc) {
            XStream xs = new XStream();
            xs.processAnnotations(c.getClass());
            sb.append(xs.toXML(c)).append("\n") ;
        }
        return sb.toString().trim();
    }
}
