package com.base.domains.userinfo;

import com.base.database.trading.model.UsercontrollerEbayAccount;

/**
 * Created by Administrtor on 2014/8/8.
 */
public class UsercontrollerEbayAccountExtend extends UsercontrollerEbayAccount {
    private String paypalName;

    public String getPaypalName() {
        return paypalName;
    }

    public void setPaypalName(String paypalName) {
        this.paypalName = paypalName;
    }
}
