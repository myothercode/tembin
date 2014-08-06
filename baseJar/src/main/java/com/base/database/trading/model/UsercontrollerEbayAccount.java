package com.base.database.trading.model;

import java.util.Date;

public class UsercontrollerEbayAccount {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usercontroller_ebay_account.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usercontroller_ebay_account.user_id
     *
     * @mbggenerated
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usercontroller_ebay_account.ebay_name
     *
     * @mbggenerated
     */
    private String ebayName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usercontroller_ebay_account.create_user
     *
     * @mbggenerated
     */
    private Long createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usercontroller_ebay_account.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usercontroller_ebay_account.uuid
     *
     * @mbggenerated
     */
    private String uuid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usercontroller_ebay_account.ebay_token
     *
     * @mbggenerated
     */
    private String ebayToken;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usercontroller_ebay_account.id
     *
     * @return the value of usercontroller_ebay_account.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usercontroller_ebay_account.id
     *
     * @param id the value for usercontroller_ebay_account.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usercontroller_ebay_account.user_id
     *
     * @return the value of usercontroller_ebay_account.user_id
     *
     * @mbggenerated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usercontroller_ebay_account.user_id
     *
     * @param userId the value for usercontroller_ebay_account.user_id
     *
     * @mbggenerated
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usercontroller_ebay_account.ebay_name
     *
     * @return the value of usercontroller_ebay_account.ebay_name
     *
     * @mbggenerated
     */
    public String getEbayName() {
        return ebayName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usercontroller_ebay_account.ebay_name
     *
     * @param ebayName the value for usercontroller_ebay_account.ebay_name
     *
     * @mbggenerated
     */
    public void setEbayName(String ebayName) {
        this.ebayName = ebayName == null ? null : ebayName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usercontroller_ebay_account.create_user
     *
     * @return the value of usercontroller_ebay_account.create_user
     *
     * @mbggenerated
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usercontroller_ebay_account.create_user
     *
     * @param createUser the value for usercontroller_ebay_account.create_user
     *
     * @mbggenerated
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usercontroller_ebay_account.create_time
     *
     * @return the value of usercontroller_ebay_account.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usercontroller_ebay_account.create_time
     *
     * @param createTime the value for usercontroller_ebay_account.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usercontroller_ebay_account.uuid
     *
     * @return the value of usercontroller_ebay_account.uuid
     *
     * @mbggenerated
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usercontroller_ebay_account.uuid
     *
     * @param uuid the value for usercontroller_ebay_account.uuid
     *
     * @mbggenerated
     */
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usercontroller_ebay_account.ebay_token
     *
     * @return the value of usercontroller_ebay_account.ebay_token
     *
     * @mbggenerated
     */
    public String getEbayToken() {
        return ebayToken;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usercontroller_ebay_account.ebay_token
     *
     * @param ebayToken the value for usercontroller_ebay_account.ebay_token
     *
     * @mbggenerated
     */
    public void setEbayToken(String ebayToken) {
        this.ebayToken = ebayToken == null ? null : ebayToken.trim();
    }
}