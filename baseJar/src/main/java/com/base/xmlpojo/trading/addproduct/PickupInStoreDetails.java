package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 商店要求
 * Created by cz on 2014/7/17.
 */
@XStreamAlias("PickupInStoreDetails")
public class PickupInStoreDetails {
    /**
     * 是否符合商店要求
     */
    private Boolean EligibleForPickupInStore;

    public Boolean getEligibleForPickupInStore() {
        return EligibleForPickupInStore;
    }

    public void setEligibleForPickupInStore(Boolean eligibleForPickupInStore) {
        EligibleForPickupInStore = eligibleForPickupInStore;
    }
}
