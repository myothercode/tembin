package com.base.utils.threadpool;

import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cxfclient.CXFPostClient;
import com.base.utils.httpclient.ApiHeader;
import com.base.utils.httpclient.HttpClientUtil;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by wula on 2014/8/6.
 */
public class ApiCallable implements Callable {
    static Logger logger = Logger.getLogger(ApiCallable.class);

    private static final String enCode="utf-8";//使用utf8编码
    private UsercontrollerDevAccountExtend devAccountExtend;
    private String xml;
    private String url;

    @Override
    public Object call() throws Exception {
        if(xml==null || url==null || devAccountExtend ==null){return null;}

        List<BasicHeader> headers=null;
        if(ApiHeader.DISPUTE_API_HEADER.equalsIgnoreCase(devAccountExtend.getHeaderType())){
            headers=ApiHeader.getDisputeApiHeader(devAccountExtend);
        }else {
            headers = ApiHeader.getApiHeader(devAccountExtend);
        }

        CXFPostClient cxfPostClient = (CXFPostClient) ApplicationContextUtil.getBean(CXFPostClient.class);
        String res = cxfPostClient.sendPostAction(headers,url,xml);

        return res;
    }

    public ApiCallable(UsercontrollerDevAccountExtend dev,String xml,String url){
        this.devAccountExtend=dev;
        this.url=url;
        this.xml=xml;

    }

}
