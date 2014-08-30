package com.base.domains.userinfo;

import com.base.database.trading.model.UsercontrollerDevAccount;

/**
 * Created by Administrtor on 2014/8/6.
 */
public class UsercontrollerDevAccountExtend extends UsercontrollerDevAccount {
    private String apiSiteid;
    private String apiCallName;

    private String headerType;

    private String soaGlobalId;

    private String soaServiceName;

    private String soaOperationName;

    private String soaServiceVersion;

    private String soaSecurityToken;

    private String soaRequestDataFormat;

    private String soaResponseDataformat;

    public String getSoaGlobalId() {
        return soaGlobalId;
    }

    public void setSoaGlobalId(String soaGlobalId) {
        this.soaGlobalId = soaGlobalId;
    }

    public String getSoaServiceName() {
        return soaServiceName;
    }

    public void setSoaServiceName(String soaServiceName) {
        this.soaServiceName = soaServiceName;
    }

    public String getSoaOperationName() {
        return soaOperationName;
    }

    public void setSoaOperationName(String soaOperationName) {
        this.soaOperationName = soaOperationName;
    }

    public String getSoaServiceVersion() {
        return soaServiceVersion;
    }

    public void setSoaServiceVersion(String soaServiceVersion) {
        this.soaServiceVersion = soaServiceVersion;
    }

    public String getSoaSecurityToken() {
        return soaSecurityToken;
    }

    public void setSoaSecurityToken(String soaSecurityToken) {
        this.soaSecurityToken = soaSecurityToken;
    }

    public String getSoaRequestDataFormat() {
        return soaRequestDataFormat;
    }

    public void setSoaRequestDataFormat(String soaRequestDataFormat) {
        this.soaRequestDataFormat = soaRequestDataFormat;
    }

    public String getHeaderType() {
        return headerType;
    }

    public void setHeaderType(String headerType) {
        this.headerType = headerType;
    }

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

    public String getSoaResponseDataformat() {
        return soaResponseDataformat;
    }

    public void setSoaResponseDataformat(String soaResponseDataformat) {
        this.soaResponseDataformat = soaResponseDataformat;
    }
}
