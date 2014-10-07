package com.base.domains.querypojos;

import com.base.database.trading.model.TradingGetUserCases;

/**
 * Created by cz on 2014/7/28.
 */
public class UserCasesQuery extends TradingGetUserCases{

    private String disputeReason;

    private String ebpReason;

    public String getDisputeReason() {
        return disputeReason;
    }

    public void setDisputeReason(String disputeReason) {
        this.disputeReason = disputeReason;
    }

    public String getEbpReason() {
        return ebpReason;
    }

    public void setEbpReason(String ebpReason) {
        this.ebpReason = ebpReason;
    }
}
