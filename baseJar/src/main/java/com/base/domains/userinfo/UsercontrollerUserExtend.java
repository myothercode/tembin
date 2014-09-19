package com.base.domains.userinfo;

import com.base.database.userinfo.model.UsercontrollerUser;

/**
 * Created by Administrator on 2014/9/19.
 */
public class UsercontrollerUserExtend extends UsercontrollerUser {
    private String orgName;
    private String roleName;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
