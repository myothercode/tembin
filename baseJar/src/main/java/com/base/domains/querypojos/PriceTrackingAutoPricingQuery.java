package com.base.domains.querypojos;


import com.base.database.trading.model.TradingPriceTrackingAutoPricing;

/**
 * Created by cz on 2014/7/28.
 */
public class PriceTrackingAutoPricingQuery extends TradingPriceTrackingAutoPricing{
    private String competitorsItemids;

    private String ruleType;

    private String increaseOrDecrease;

    private String inputValue;

    private String sign;

    private Integer competitorsTotal;

    public Integer getCompetitorsTotal() {
        return competitorsTotal;
    }

    public void setCompetitorsTotal(Integer competitorsTotal) {
        this.competitorsTotal = competitorsTotal;
    }

    public String getCompetitorsItemids() {
        return competitorsItemids;
    }

    public void setCompetitorsItemids(String competitorsItemids) {
        this.competitorsItemids = competitorsItemids;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getIncreaseOrDecrease() {
        return increaseOrDecrease;
    }

    public void setIncreaseOrDecrease(String increaseOrDecrease) {
        this.increaseOrDecrease = increaseOrDecrease;
    }

    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
