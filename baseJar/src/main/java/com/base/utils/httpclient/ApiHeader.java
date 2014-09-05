package com.base.utils.httpclient;

import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/6.
 */
public class ApiHeader {

    public static final String DISPUTE_API_HEADER = "DisputeApiHeader";

    /**返回普通api的header*/
    public static List<BasicHeader> getApiHeader(UsercontrollerDevAccountExtend devAccountExtend){
        List<BasicHeader> headers = new ArrayList<BasicHeader>();
        headers.add(new BasicHeader("X-EBAY-API-COMPATIBILITY-LEVEL",devAccountExtend.getApiCompatibilityLevel()));
        headers.add(new BasicHeader("X-EBAY-API-DEV-NAME",devAccountExtend.getApiDevName()));
        headers.add(new BasicHeader("X-EBAY-API-APP-NAME",devAccountExtend.getApiAppName()));
        headers.add(new BasicHeader("X-EBAY-API-CERT-NAME",devAccountExtend.getApiCertName()));
        headers.add(new BasicHeader("X-EBAY-API-SITEID",devAccountExtend.getApiSiteid()));
        headers.add(new BasicHeader("X-EBAY-API-CALL-NAME",devAccountExtend.getApiCallName()));
        return headers;
    }

    /**返回纠纷请求的header*/
    public static List<BasicHeader> getDisputeApiHeader(UsercontrollerDevAccountExtend devAccountExtend){
        List<BasicHeader> headers = new ArrayList<BasicHeader>();
        headers.add(new BasicHeader("X-EBAY-SOA-OPERATION-NAME",devAccountExtend.getSoaOperationName()));
        headers.add(new BasicHeader("X-EBAY-SOA-SECURITY-TOKEN ",devAccountExtend.getSoaSecurityToken()));
       /* headers.add(new BasicHeader("X-EBAY-SOA-REQUEST-DATA-FORMAT",devAccountExtend.getSoaRequestDataFormat()));
        headers.add(new BasicHeader("X-EBAY-SOA-SERVICE-NAME",devAccountExtend.getSoaServiceName()));
        headers.add(new BasicHeader("X-EBAY-SOA-SERVICE-VERSION",devAccountExtend.getSoaServiceVersion()));
        headers.add(new BasicHeader("X-EBAY-SOA-GLOBAL-ID",devAccountExtend.getSoaGlobalId()));*/
   /*     headers.add(new BasicHeader("X-EBAY-SOA-RESPONSE-DATA-FORMAT",devAccountExtend.getSoaResponseDataformat()));*/
        return headers;
    }


    /**header转为map*/
    public static HashMap<String,String> header2Map(List<BasicHeader> basicHeaders){
        HashMap<String,String> hashMap = new HashMap();
        for (BasicHeader b:basicHeaders){
            hashMap.put(b.getName(),b.getValue());
        }
        return hashMap;

    }
}
