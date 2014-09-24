package com.base.domains.querypojos;

import com.base.database.trading.model.TradingOrderGetOrders;

import java.util.Map;

/**
 * Created by cz on 2014/7/28.
 */
public class OrderGetOrdersQuery extends TradingOrderGetOrders{

    private Long countNum;

    private String pictrue;

    private String itemUrl;

    private String message;

    private Map<String,String> variationspecifics;

    public Map<String, String> getVariationspecifics() {
        return variationspecifics;
    }

    public void setVariationspecifics(Map<String, String> variationspecifics) {
        this.variationspecifics = variationspecifics;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPictrue() {
        return pictrue;
    }

    public void setPictrue(String pictrue) {
        this.pictrue = pictrue;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public Long getCountNum() {
        return countNum;
    }

    public void setCountNum(Long countNum) {
        this.countNum = countNum;
    }

}
