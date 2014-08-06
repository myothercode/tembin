package com.base.utils.httpclient;

import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/6.
 */
public class ApiHeader {
    public static List<Header> getApiHeader(UsercontrollerDevAccountExtend devAccountExtend){
        List<Header> headers = new ArrayList<org.apache.http.Header>();
        headers.add(new BasicHeader("X-EBAY-API-COMPATIBILITY-LEVEL",devAccountExtend.getApiCompatibilityLevel()));
        headers.add(new BasicHeader("X-EBAY-API-DEV-NAME",devAccountExtend.getApiDevName()));
        headers.add(new BasicHeader("X-EBAY-API-APP-NAME",devAccountExtend.getApiAppName()));
        headers.add(new BasicHeader("X-EBAY-API-CERT-NAME",devAccountExtend.getApiCertName()));
        headers.add(new BasicHeader("X-EBAY-API-SITEID",devAccountExtend.getApiSiteid()));
        headers.add(new BasicHeader("X-EBAY-API-CALL-NAME",devAccountExtend.getApiCallName()));
        return headers;
    }
}
