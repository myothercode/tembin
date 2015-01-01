package com.base.utils.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/29.
 */
@Component
public class CommAutowiredClass {
    static Logger logger = Logger.getLogger(CommAutowiredClass.class);

    @Value("${EBAY.API.URL}")
    public String apiUrl;//api的调用地址
    @Value("${IS_START_TIMER_TASK}")
    public String isStartTimerTask;//本机是否开启定时任务
    @Value("${SERVICE_ITEM_URL}")
    public String serviceItemUrl;//访问全球站点商品信息url
    @Value("${SERVICE_ITEM_URLS}")
    public String serviceItemUrls;//所有站点的urls
    @Value("${EBAY.FINDING.KEY.API.URL}")
    public String findingkeyapiUrl;//访问商品分类信息


    /**测试环境发布预览需要的帐号*/
    @Value("${SANDBOX_EBAY_ID}")
    public String sandboxEbayID;
    @Value(("${SANDBOX_DEV_ID}"))
    public String snadboxDevID;


    /**91track信息*/
    @Value("${TOKEN_91TRACK}")
    public String token91Track;
    @Value("${URL_91TRACK}")
    public String url91Track;




    /**经过处理后的数据，站点URL*/
    public static Map<String,String> URLMap=new HashMap<String, String>();
    /**根据站点获取站点的商品url*/
    public String getSiteURL(String siteCode){
        if (URLMap.isEmpty()){
            try {
                URLMap = JSON.parseObject(this.serviceItemUrls, HashMap.class);
            } catch (Exception e){
                logger.error("格式化站点地址失败!",e);
                return "";
            }
        }
        return URLMap.get("URL"+siteCode);

    }


}
