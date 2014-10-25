package com.base.domains.querypojos;

import com.base.database.trading.model.TradingMessageGetmymessage;

/**
 * Created by cz on 2014/7/28.
 */
public class MessageGetmymessageQuery extends TradingMessageGetmymessage{
    private String ebayAccountName;

    private Long countNum;

    private String sku;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Long getCountNum() {
        return countNum;
    }

    public void setCountNum(Long countNum) {
        this.countNum = countNum;
    }

    public String getEbayAccountName() {
        return ebayAccountName;
    }

    public void setEbayAccountName(String ebayAccountName) {
        this.ebayAccountName = ebayAccountName;
    }
}
