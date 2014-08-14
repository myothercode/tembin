package com.base.sampleapixml;

/**
 * Created by Administrtor on 2014/8/14.
 * 商品类别有关的api
 */
public class CategoryAPI {
    /**根据商品类别获取商品类的属性*/
    public static String getCategorySpecificsRequest(String token,String categoryID){
        String x="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<GetCategorySpecificsRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">" +
                "<RequesterCredentials>" +
                "<eBayAuthToken>"+token+"</eBayAuthToken>" +
                "</RequesterCredentials>" +
                "<CategoryID>"+categoryID+"</CategoryID>" +
                "</GetCategorySpecificsRequest>​";

        return x;

    }
}
