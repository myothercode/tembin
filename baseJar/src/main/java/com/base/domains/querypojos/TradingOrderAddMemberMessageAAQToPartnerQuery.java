package com.base.domains.querypojos;

import com.base.database.trading.model.TradingOrderAddMemberMessageAAQToPartner;

/**
 * Created by Administrtor on 2014/9/16.
 */
public class TradingOrderAddMemberMessageAAQToPartnerQuery extends TradingOrderAddMemberMessageAAQToPartner {
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
}
