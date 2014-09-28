package com.postcontrollers.impl;

import com.base.utils.cxfclient.CXFClientRequestVO;
import com.base.utils.httpclient.HttpClientUtil;
import com.base.utils.myheaderUtil.MyHeaderUtil;
import com.postcontrollers.PostApiService;
import org.apache.http.client.HttpClient;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/22.
 */
@WebService(serviceName = "PostApiService",portName = "PostApiPort",targetNamespace = "http://namespace.rootpom/")
@Scope(value = "prototype")
public class PostApiServiceImpl implements PostApiService {
    private static final String enCode="utf-8";//使用utf8编码

    static Logger logger = Logger.getLogger(PostApiServiceImpl.class);

    @Override
    @WebMethod(operationName = "PostApi")
    public String postApi(@WebParam(name = "CXFClientRequestVO") CXFClientRequestVO CXFClientRequestVO) throws Exception {
        HttpClient httpClient= HttpClientUtil.getHttpsClient();
        String res= HttpClientUtil.post(httpClient,CXFClientRequestVO.getUrl(),CXFClientRequestVO.getXml(),enCode,
                MyHeaderUtil.map2Header(CXFClientRequestVO.getHeaders()));
        return res;
    }
}
