package com.base.domains.querypojos;


import com.base.database.trading.model.TradingListingData;

public class KeyMoveProgressQuery{


    private Long userId;

    private String siteId;

    private String taskFlag;

    private String paypalId;

    private int tjNumber;

    private Long progressId;

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