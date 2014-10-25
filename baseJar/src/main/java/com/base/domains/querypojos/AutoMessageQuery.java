package com.base.domains.querypojos;

import com.base.database.trading.model.TradingAutoMessage;

/**
 * Created by cz on 2014/7/28.
 */
public class AutoMessageQuery extends TradingAutoMessage{
    private String item;
    private String country;
    private String amount;
    private String service;
    private String internationalService;
    private String exceptCountry;
    private String allOrder;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getInternationalService() {
        return internationalService;
    }

    public void setInternationalService(String internationalService) {
        this.internationalService = internationalService;
    }

    public String getExceptCountry() {
        return exceptCountry;
    }

    public void setExceptCountry(String exceptCountry) {
        this.exceptCountry = exceptCountry;
    }

    public String getAllOrder() {
        return allOrder;
    }

    public void setAllOrder(String allOrder) {
        this.allOrder = allOrder;
    }
}
