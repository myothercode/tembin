package com.base.utils.cxfclient;

import com.base.utils.cxfclient.postapi.namespace.PostApiService;
import com.base.utils.httpclient.ApiHeader;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/22.
 */
@Component("cxfPostClient")
public class CXFPostClient {
    @Value("${POSTSERVER.WS.URL}")
    private String wsurl;



    public String sendPostAction(List<BasicHeader> basicHeaders,String url,String xml){
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(PostApiService.class);
        factory.setAddress(wsurl);
        PostApiService client= (PostApiService) factory.create();

        CXFClientRequestVO cxfClientRequestVO=new CXFClientRequestVO();
        cxfClientRequestVO.setHeaders(ApiHeader.header2Map(basicHeaders));
        cxfClientRequestVO.setXml(xml);
        cxfClientRequestVO.setUrl(url);

        String result =client.postApi(cxfClientRequestVO);
        return result;
    }
}