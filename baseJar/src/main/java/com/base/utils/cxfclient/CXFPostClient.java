package com.base.utils.cxfclient;

import com.base.utils.cxfclient.postapi.namespace.PostApiService;
import com.base.utils.httpclient.ApiHeader;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/22.
 */
@Component("cxfPostClient")
@Scope(value = "prototype")
public class CXFPostClient {
    @Value("${POSTSERVER.WS.URL}")
    private String wsurl;
    public static final int CXF_CLIENT_CONNECT_TIMEOUT = 8 * 1000 * 60;//设置连接超时时间，单位是毫秒
    public static final int CXF_CLIENT_RECEIVE_TIMEOUT = 8 * 1000 * 60;//设置接收数据超时时间



    public String sendPostAction(List<BasicHeader> basicHeaders,String url,String xml){
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(PostApiService.class);
        factory.setAddress(wsurl);
        PostApiService client= (PostApiService) factory.create();
        configTimeout(client);

        CXFClientRequestVO cxfClientRequestVO=new CXFClientRequestVO();
        cxfClientRequestVO.setHeaders(ApiHeader.header2Map(basicHeaders));
        cxfClientRequestVO.setXml(xml);
        cxfClientRequestVO.setUrl(url);

        String result =client.postApi(cxfClientRequestVO);
        return result;
    }


/***设置超时时间*/
    public static void configTimeout(Object service) {
        Client proxy = ClientProxy.getClient(service);
        HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();
        policy.setConnectionTimeout(CXF_CLIENT_CONNECT_TIMEOUT);
        policy.setReceiveTimeout(CXF_CLIENT_RECEIVE_TIMEOUT);
        conduit.setClient(policy);
    }


    /*public static void main(String[] args) {
        CXFClientRequestVO cxfClientRequestVO=new CXFClientRequestVO();
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(PostApiService.class);
        factory.setAddress("http://192.168.0.241:8080/postApi/ws/postApiService");
        PostApiService client= (PostApiService) factory.create();
        String result =client.postApi(null);
    }*/
}
