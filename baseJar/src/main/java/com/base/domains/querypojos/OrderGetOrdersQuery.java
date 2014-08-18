package com.base.domains.querypojos;

import com.base.database.trading.model.TradingOrderGetOrders;

/**
 * Created by cz on 2014/7/28.
 */
public class OrderGetOrdersQuery extends TradingOrderGetOrders{

    private Long countNum;

    private String pictrue;

    private String itemUrl;

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
