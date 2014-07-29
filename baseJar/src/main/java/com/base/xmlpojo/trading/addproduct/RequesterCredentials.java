package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Administrtor on 2014/7/18.
 */
@XStreamAlias("RequesterCredentials")
public class RequesterCredentials {
    private String eBayAuthToken;

    public String geteBayAuthToken() {
        return eBayAuthToken;
    }

    public void seteBayAuthToken(String eBayAuthToken) {
        this.eBayAuthToken = eBayAuthToken;
    }
}
