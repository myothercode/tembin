package com.base.domains.userinfo;

import com.base.database.trading.model.UsercontrollerDevAccount;

/**
 * Created by Administrtor on 2014/8/6.
 */
public class UsercontrollerDevAccountExtend extends UsercontrollerDevAccount {
    private String apiSiteid;
    private String apiCallName;

    public String getApiSiteid() {
        return apiSiteid;
    }

    public void setApiSiteid(String apiSiteid) {
        this.apiSiteid = apiSiteid;
    }

    public String getApiCallName() {
        return apiCallName;
    }

    public void setApiCallName(String apiCallName) {
        this.apiCallName = apiCallName;
    }
}
