package com.base.database.trading.model;

import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.utils.common.ConvertPOJOUtil;

public class UsercontrollerDevAccount {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usercontroller_dev_account.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usercontroller_dev_account.dev_user
     *
     * @mbggenerated
     */
    private String devUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usercontroller_dev_account.API_DEV_NAME
     *
     * @mbggenerated
     */
    private String apiDevName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usercontroller_dev_account.API_APP_NAME
     *
     * @mbggenerated
     */
    private String apiAppName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usercontroller_dev_account.API_CERT_NAME
     *
     * @mbggenerated
     */
    private String apiCertName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usercontroller_dev_account.API_COMPATIBILITY_LEVEL
     *
     * @mbggenerated
     */
    private String apiCompatibilityLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usercontroller_dev_account.runName
     *
     * @mbggenerated
     */
    private String runname;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usercontroller_dev_account.id
     *
     * @return the value of usercontroller_dev_account.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usercontroller_dev_account.id
     *
     * @param id the value for usercontroller_dev_account.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usercontroller_dev_account.dev_user
     *
     * @return the value of usercontroller_dev_account.dev_user
     *
     * @mbggenerated
     */
    public String getDevUser() {
        return devUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usercontroller_dev_account.dev_user
     *
     * @param devUser the value for usercontroller_dev_account.dev_user
     *
     * @mbggenerated
     */
    public void setDevUser(String devUser) {
        this.devUser = devUser == null ? null : devUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usercontroller_dev_account.API_DEV_NAME
     *
     * @return the value of usercontroller_dev_account.API_DEV_NAME
     *
     * @mbggenerated
     */
    public String getApiDevName() {
        return apiDevName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usercontroller_dev_account.API_DEV_NAME
     *
     * @param apiDevName the value for usercontroller_dev_account.API_DEV_NAME
     *
     * @mbggenerated
     */
    public void setApiDevName(String apiDevName) {
        this.apiDevName = apiDevName == null ? null : apiDevName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usercontroller_dev_account.API_APP_NAME
     *
     * @return the value of usercontroller_dev_account.API_APP_NAME
     *
     * @mbggenerated
     */
    public String getApiAppName() {
        return apiAppName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usercontroller_dev_account.API_APP_NAME
     *
     * @param apiAppName the value for usercontroller_dev_account.API_APP_NAME
     *
     * @mbggenerated
     */
    public void setApiAppName(String apiAppName) {
        this.apiAppName = apiAppName == null ? null : apiAppName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usercontroller_dev_account.API_CERT_NAME
     *
     * @return the value of usercontroller_dev_account.API_CERT_NAME
     *
     * @mbggenerated
     */
    public String getApiCertName() {
        return apiCertName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usercontroller_dev_account.API_CERT_NAME
     *
     * @param apiCertName the value for usercontroller_dev_account.API_CERT_NAME
     *
     * @mbggenerated
     */
    public void setApiCertName(String apiCertName) {
        this.apiCertName = apiCertName == null ? null : apiCertName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usercontroller_dev_account.API_COMPATIBILITY_LEVEL
     *
     * @return the value of usercontroller_dev_account.API_COMPATIBILITY_LEVEL
     *
     * @mbggenerated
     */
    public String getApiCompatibilityLevel() {
        return apiCompatibilityLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usercontroller_dev_account.API_COMPATIBILITY_LEVEL
     *
     * @param apiCompatibilityLevel the value for usercontroller_dev_account.API_COMPATIBILITY_LEVEL
     *
     * @mbggenerated
     */
    public void setApiCompatibilityLevel(String apiCompatibilityLevel) {
        this.apiCompatibilityLevel = apiCompatibilityLevel == null ? null : apiCompatibilityLevel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usercontroller_dev_account.runName
     *
     * @return the value of usercontroller_dev_account.runName
     *
     * @mbggenerated
     */
    public String getRunname() {
        return runname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usercontroller_dev_account.runName
     *
     * @param runname the value for usercontroller_dev_account.runName
     *
     * @mbggenerated
     */
    public void setRunname(String runname) {
        this.runname = runname == null ? null : runname.trim();
    }

    public UsercontrollerDevAccountExtend toExtend() throws Exception {
        UsercontrollerDevAccountExtend usd=new UsercontrollerDevAccountExtend();
        usd.setApiCallName(this.getApiAppName());
        usd.setApiCertName(this.getApiCertName());
        usd.setApiCompatibilityLevel(this.getApiCompatibilityLevel());
        usd.setApiDevName(this.getApiDevName());
        usd.setRunname(this.getRunname());
        usd.setDevUser(this.getDevUser());
        usd.setApiAppName(this.getApiAppName());
        usd.setId(this.getId());
        return usd;
    }
}