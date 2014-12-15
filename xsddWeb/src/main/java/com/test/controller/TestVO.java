package com.test.controller;

import com.base.database.publicd.model.PublicDataDict;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.sampleapixml.APINameStatic;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.common.DictCollectionsUtil;
import com.base.utils.httpclient.HttpClientUtil;
import com.base.utils.tranfiles.XMLBufferTool;
import com.base.utils.xmlutils.SamplePaseXml;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wula on 2014/7/6.
 */
public class TestVO {
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
                "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**wV1JUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**2kuzIn+bBej1QDsDFfI2N74mj8psZYNYrtgX97fzWSGXO7EjvdlE9leu9HCY1bR9wdrzlAE7AKcT9Oz5BDNZbNQLS+uoifmNUM47lSqxWeYTQS2GtMK25LPYhxY+OQp6UVZ8lUh6Oqr91ub03emzufuZHo+6KSNJfNXMtOBVaB7PDeBQyNWoFBO0/LYiS5ql6HXB7vCj0W+K/iT4t3aPs5KlXAXjewM/Sa+nUDtjT9SseqrKrxdZx5fkAePeSrBs229tdCrkTtE0n+ZE9ppwJjElZpu7yfQL44McNa16KBxYYO0PnX7ENg2yMxf3H4aji0BEfB41lrC1LwhmNSebJGrJXRQVS9jmZyDqYiBdn1t536va/LPTP8kc3GZ7hnZRJuhMxoGGgx4ev5Hip0L7dk6cAPKHIkHUIjfA5pwVHEJZpvea+7uvwAh5pj9U7r6rmB9FXH2G9l+F5SytYlIXsDjwNtrEN53k5HrM0vhnGdd7pUwvyu7Nu4U5aPkZQZjTr6OrTWioDsZZwEz+pf0scw0IYweMhicCqMTNbvkJsj2cikX49C6XSAcoUyrGtGa11vFChrifmq74dPZmUEtT1hDtwL1Ix3VPyZcJtTukKljxa0W0IwIe676X5HmiGhvk5qPPUImkXcZdQUK1gMdZmw0seMl5xmFG33kKVSD9H0p0JAEF4lOcDvjADQZtwLXY3qIhvYcKdOrIffrUAURnJRYnrB/MixizWvw252xBn9tmxpm68O3KsGBzcUwEB0Su</eBayAuthToken>" +
                "</RequesterCredentials>" +
                "<CategorySpecificsFileInfo>true</CategorySpecificsFileInfo>" +
                "</GetCategorySpecificsRequest>​";


        List<BasicHeader> headers = new ArrayList<BasicHeader>();
        headers.add(new BasicHeader("X-EBAY-API-COMPATIBILITY-LEVEL","885"));
            /*headers.add(new BasicHeader("X-EBAY-API-DEV-NAME", "bbafa7e7-2f98-4783-9c34-f403faeb007f"));
            headers.add(new BasicHeader("X-EBAY-API-APP-NAME","chengdul-5b82-4a84-a496-6a2c75e4e0d5"));
            headers.add(new BasicHeader("X-EBAY-API-CERT-NAME","724f4467-9280-437c-a998-f5bcfee67ce5"));*/
        headers.add(new BasicHeader("X-EBAY-API-DEV-NAME", "5d70d647-b1e2-4c7c-a034-b343d58ca425"));
        headers.add(new BasicHeader("X-EBAY-API-APP-NAME","sandpoin-1f58-4e64-a45b-56507b02bbeb"));
        headers.add(new BasicHeader("X-EBAY-API-CERT-NAME","936fc911-c05c-455c-8838-3a698f2da43a"));
        headers.add(new BasicHeader("X-EBAY-API-SITEID","123"));
        headers.add(new BasicHeader("X-EBAY-API-CALL-NAME", APINameStatic.GetCategorySpecifics));

        HttpClient httpClient= HttpClientUtil.getHttpsClient();
        String res= HttpClientUtil.post(httpClient, "https://api.sandbox.ebay.com/ws/api.dll", xml, "UTF-8", headers);
        System.out.println(res);
    }






}
