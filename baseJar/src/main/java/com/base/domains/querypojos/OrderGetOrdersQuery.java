package com.base.domains.querypojos;

import com.base.database.trading.model.TradingOrderGetOrders;

import java.util.Date;
import java.util.Map;

/**
 * Created by cz on 2014/7/28.
 */
public class OrderGetOrdersQuery extends TradingOrderGetOrders{

    private Long countNum;

    private String pictrue;

    private String itemUrl;

    private String message;

    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private Map<String,String> variationspecifics;

    private Date paypalPaidTime;

    private Date paypalPaymentTime;

    private String externalTransactionID;

    public String getExternalTransactionID() {
        return externalTransactionID;
    }

    public void setExternalTransactionID(String externalTransactionID) {
        this.externalTransactionID = externalTransactionID;
    }

    public Date getPaypalPaidTime() {
        return paypalPaidTime;
    }

    public void setPaypalPaidTime(Date paypalPaidTime) {
        this.paypalPaidTime = paypalPaidTime;
    }

    public Date getPaypalPaymentTime() {
        return paypalPaymentTime;
    }

    public void setPaypalPaymentTime(Date paypalPaymentTime) {
        this.paypalPaymentTime = paypalPaymentTime;
    }

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
