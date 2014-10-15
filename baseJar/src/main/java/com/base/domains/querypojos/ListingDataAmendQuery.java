package com.base.domains.querypojos;


import com.base.database.trading.model.TradingListingAmend;
import com.base.database.trading.model.TradingListingData;

import java.util.Date;

public class ListingDataAmendQuery extends TradingListingData{

    private String amendType;

    private Long amendUser;

    private Date amendTime;

    private String endisflag;

    private String content;

    private String endid;

    public String getEndid() {
        return endid;
    }

    public void setEndid(String endid) {
        this.endid = endid;
    }

    public String getAmendType() {
        return amendType;
    }

    public void setAmendType(String amendType) {
        this.amendType = amendType;
    }

    public Long getAmendUser() {
        return amendUser;
    }

    public void setAmendUser(Long amendUser) {
        this.amendUser = amendUser;
    }

    public Date getAmendTime() {
        return amendTime;
    }

    public void setAmendTime(Date amendTime) {
        this.amendTime = amendTime;
    }

    public String getEndisflag() {
        return endisflag;
    }

    public void setEndisflag(String endisflag) {
        this.endisflag = endisflag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}