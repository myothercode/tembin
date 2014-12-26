package com.base.domains.querypojos;


import com.base.database.trading.model.TradingListingData;

public class KeyMoveProgressQuery{


    private Long userId;

    private String siteId;

    private String taskFlag;

    private String paypalId;

    private int tjNumber;

    private Long progressId;

    private String startDate;

    private String endDate;

    private String ebayAccount;

    private String waitcount;

    private String docount;

    private String errorcount;

    public String getWaitcount() {
        return waitcount;
    }

    public void setWaitcount(String waitcount) {
        this.waitcount = waitcount;
    }

    public String getDocount() {
        return docount;
    }

    public void setDocount(String docount) {
        this.docount = docount;
    }

    public String getErrorcount() {
        return errorcount;
    }

    public void setErrorcount(String errorcount) {
        this.errorcount = errorcount;
    }

    public String getEbayAccount() {
        return ebayAccount;
    }

    public void setEbayAccount(String ebayAccount) {
        this.ebayAccount = ebayAccount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getTaskFlag() {
        return taskFlag;
    }

    public void setTaskFlag(String taskFlag) {
        this.taskFlag = taskFlag;
    }

    public String getPaypalId() {
        return paypalId;
    }

    public void setPaypalId(String paypalId) {
        this.paypalId = paypalId;
    }

    public int getTjNumber() {
        return tjNumber;
    }

    public void setTjNumber(int tjNumber) {
        this.tjNumber = tjNumber;
    }

    public Long getProgressId() {
        return progressId;
    }

    public void setProgressId(Long progressId) {
        this.progressId = progressId;
    }
}