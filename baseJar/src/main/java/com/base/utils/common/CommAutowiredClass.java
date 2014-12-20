package com.base.utils.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Administrtor on 2014/8/29.
 */
@Component
public class CommAutowiredClass {
    @Value("${EBAY.API.URL}")
    public String apiUrl;//api的调用地址
    @Value("${IS_START_TIMER_TASK}")
    public String isStartTimerTask;//本机是否开启定时任务
    @Value("${SERVICE_ITEM_URL}")
    public String serviceItemUrl;//访问商品信息url
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
}
