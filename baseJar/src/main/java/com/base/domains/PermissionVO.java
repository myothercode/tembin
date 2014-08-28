package com.base.domains;

/**
 * Created by Administrtor on 2014/7/24.
 */
public class PermissionVO {
    private Long permissionID;
    private String permissionURL;
    private String permissionName;

    public Long getPermissionID() {
        return permissionID;
    }

    public void setPermissionID(Long permissionID) {
        this.permissionID = permissionID;
    }

    public String getPermissionURL() {
        return permissionURL;
    }

    public void setPermissionURL(String permissionURL) {
        this.permissionURL = permissionURL;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
}
