package com.base.utils.threadpool;

import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.cxfclient.CXFPostClient;
import com.base.utils.httpclient.ApiHeader;
import com.base.utils.httpclient.HttpClientUtil;
import com.base.utils.xmlutils.CheckResXMLUtil;
import org.apache.commons.lang.StringUtils;
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
        String isLimit=SessionCacheSupport.getOther("devLimit");
        if(StringUtils.isNotEmpty(isLimit) && "limit".equalsIgnoreCase(isLimit)){
            return "apiLimit";
        }

        List<BasicHeader> headers=null;
        if(ApiHeader.DISPUTE_API_HEADER.equalsIgnoreCase(devAccountExtend.getHeaderType())){
            headers=ApiHeader.getDisputeApiHeader(devAccountExtend);
        }else {
            headers = ApiHeader.getApiHeader(devAccountExtend);
        }

        CXFPostClient cxfPostClient = (CXFPostClient) ApplicationContextUtil.getBean(CXFPostClient.class);
        String res = cxfPostClient.sendPostAction(headers,url,xml);

        boolean b= CheckResXMLUtil.checkApiLimit(res);
        if(b){
            SessionCacheSupport.putOther("devLimit","limit");//放入次数调用完毕的参数
            return "apiLimit";
        }
        return res;
    }

    public ApiCallable(UsercontrollerDevAccountExtend dev,String xml,String url){
        this.devAccountExtend=dev;
        this.url=url;
        this.xml=xml;

    }



}
