package com.base.utils.cxfclient;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/22.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cxfClientRequestVO", propOrder = {
        "headers",
        "url",
        "xml"
})
public class CXFClientRequestVO {
    @XmlElement(nillable = true)
    private HashMap<String,String> headers;
    private String url;
    private String xml;


    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }
}
