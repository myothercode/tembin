package com.base.utils.httpclient;

import com.base.sampleapixml.APINameStatic;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wula on 2014/7/6.
 */
public class TestClient {
    private String dd;
    private String bb;

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public String getBb() {
        return bb;
    }

    public void setBb(String bb) {
        this.bb = bb;
    }


    public static void main(String[] args) throws Exception {

        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<GetCategorySpecificsRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                "<RequesterCredentials>" +
                "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**QM20Uw**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**Sd0CAA**AAMAAA**a8K8fmx7s3vurAo/SGFiTl1PTp5pI/+JwbMuDqmBGpz/3++lJUPnHvIVgzjMBQtP89Qx/RDRY0s2Y10elagAew81uMb4EkWRIZIYw7YZdpp+ExQ5BUNnopAPRXeWz9ODtU2ujdk7m1y+wGLeNS8iFwP3bmHjcGyMLHDWvKkyBsky/y9kbpTmMbcry3SlUVykFG5cYeQFgpEjqJohfH6mX2T7NcD0L0eVWzrU4/Wh7NFpmGfxsgzN1e3FA89sLG6HZfJhSg+SBRT+dR29BAf2A9oVI6+yIctnfGPnHL68UrGzmgh+EgUf8aQW8n17zLEZEatUjpwlrAoRIH/CbG+gVZkSkGQyxI/WoqWtwAZKyAApvOSqGNYVRwef61GHMmAyf7eXojMBP3na1wMMAHpde6APji+3QiDlGT49T6wzcUA8TPRTTIQCYuqsEBY0tAjVrTEwcbsPUW9/533q8MNsVywH99VDme1fsKKLK4v93mZg9JzAMbdeNIfrtcH8CVQYQzv4n+xGNvchUD8pwGtZ98RxGk/8dZr0pEMZpcM70YNGLAtzbNEsPvfPdQfDUV6bgsHRBzySa+jAeCDesslrC3fanWJFFa/7YjLnNqdcVpWsC0V/uRWbdzOJ9mo2+sJelL5mPCgWS+YdFHYgdxYRnJCb/VBkkrm7IuSHUuBXWVdlaqs5Miu0fWIj0CZ/KYjBZK7XZyAN7LP1spAiFQJ2vo8/UCqcoay4ftMT68QgNcAyucVEr8gAF7k7RFDFEYSf</eBayAuthToken>" +
                "</RequesterCredentials>" +
                "<CategorySpecificsFileInfo>true</CategorySpecificsFileInfo>" +
                "</GetCategorySpecificsRequest>​";


        List<BasicHeader> headers = new ArrayList<BasicHeader>();
        headers.add(new BasicHeader("X-EBAY-API-COMPATIBILITY-LEVEL","885"));
            /*headers.add(new BasicHeader("X-EBAY-API-DEV-NAME", "bbafa7e7-2f98-4783-9c34-f403faeb007f"));
            headers.add(new BasicHeader("X-EBAY-API-APP-NAME","chengdul-5b82-4a84-a496-6a2c75e4e0d5"));
            headers.add(new BasicHeader("X-EBAY-API-CERT-NAME","724f4467-9280-437c-a998-f5bcfee67ce5"));*/
        headers.add(new BasicHeader("X-EBAY-API-DEV-NAME", "bbafa7e7-2f98-4783-9c34-f403faeb007f"));
        headers.add(new BasicHeader("X-EBAY-API-APP-NAME","chengdul-5b82-4a84-a496-6a2c75e4e0d5"));
        headers.add(new BasicHeader("X-EBAY-API-CERT-NAME","724f4467-9280-437c-a998-f5bcfee67ce5"));
        headers.add(new BasicHeader("X-EBAY-API-SITEID","77"));
        headers.add(new BasicHeader("X-EBAY-API-CALL-NAME", APINameStatic.GetCategorySpecifics));

        HttpClient httpClient= HttpClientUtil.getHttpsClient();
        String res= HttpClientUtil.post(httpClient, "https://api.sandbox.ebay.com/ws/api.dll", xml, "UTF-8", headers);
        System.out.println(res);
    }






}
