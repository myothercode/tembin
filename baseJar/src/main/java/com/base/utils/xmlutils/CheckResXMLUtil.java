package com.base.utils.xmlutils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2014/12/4.
 * 定义一些指定的err xml
 */
public class CheckResXMLUtil {
    static Logger logger = Logger.getLogger(CheckResXMLUtil.class);

    public static boolean checkApiLimit(String res){
        if(StringUtils.isNotEmpty(res) &&
                res.indexOf("<ErrorCode>518</ErrorCode>")>-1 &&
                res.indexOf("<Ack>Failure</Ack>")>-1 &&
                res.indexOf("limit")>-1
                ){
            logger.error("api次数用完！");
            return true;
        }
        return false;
    }
}
