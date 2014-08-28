package com.base.database.trading.model;

import java.util.Date;

public class TradingBuyerRequirementDetails {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_buyer_requirement_details.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_buyer_requirement_details.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_buyer_requirement_details.site_code
     *
     * @mbggenerated
     */
    private Integer siteCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_buyer_requirement_details.buyer_flag
     *
     * @mbggenerated
     */
    private String buyerFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_buyer_requirement_details.LinkedPayPalAccount
     *
     * @mbggenerated
     */
    private String linkedpaypalaccount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_buyer_requirement_details.ShipToRegistrationCountry
     *
     * @mbggenerated
     */
    private String shiptoregistrationcountry;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_buyer_requirement_details.MinimumFeedbackScore
     *
     * @mbggenerated
     */
    private Long minimumfeedbackscore;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_buyer_requirement_details.Policy_count
     *
     * @mbggenerated
     */
    private Long policyCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_buyer_requirement_details.Policy_period
     *
     * @mbggenerated
     */
    private String policyPeriod;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_buyer_requirement_details.Unpaid_count
     *
     * @mbggenerated
     */
    private Long unpaidCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_buyer_requirement_details.Unpaid_period
     *
     * @mbggenerated
     */
    private String unpaidPeriod;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_buyer_requirement_details.MaximumItemCount
     *
     * @mbggenerated
     */
    private Long maximumitemcount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_buyer_requirement_details.FeedbackScore
     *
     * @mbggenerated
     */
    private Long feedbackscore;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_buyer_requirement_details.Verified_flag
     *
     * @mbggenerated
     */
    private String verifiedFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_buyer_requirement_details.Verified_FeedbackScore
     *
     * @mbggenerated
     */
    private Long verifiedFeedbackscore;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_buyer_requirement_details.ZeroFeedbackScore
     *
     * @mbggenerated
     */
    private String zerofeedbackscore;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_buyer_requirement_details.create_user
     *
     * @mbggenerated
     */
    private Long createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_buyer_requirement_details.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_buyer_requirement_details.uuid
     *
     * @mbggenerated
     */
    private String uuid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_buyer_requirement_details.site_value
     *
     * @mbggenerated
     */
    private String siteValue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_buyer_requirement_details.check_flag
     *
     * @mbggenerated
     */
    private String checkFlag;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_buyer_requirement_details.id
     *
     * @return the value of trading_buyer_requirement_details.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_buyer_requirement_details.id
     *
     * @param id the value for trading_buyer_requirement_details.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_buyer_requirement_details.name
     *
     * @return the value of trading_buyer_requirement_details.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_buyer_requirement_details.name
     *
     * @param name the value for trading_buyer_requirement_details.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_buyer_requirement_details.site_code
     *
     * @return the value of trading_buyer_requirement_details.site_code
     *
     * @mbggenerated
     */
    public Integer getSiteCode() {
        return siteCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_buyer_requirement_details.site_code
     *
     * @param siteCode the value for trading_buyer_requirement_details.site_code
     *
     * @mbggenerated
     */
    public void setSiteCode(Integer siteCode) {
        this.siteCode = siteCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_buyer_requirement_details.buyer_flag
     *
     * @return the value of trading_buyer_requirement_details.buyer_flag
     *
     * @mbggenerated
     */
    public String getBuyerFlag() {
        return buyerFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_buyer_requirement_details.buyer_flag
     *
     * @param buyerFlag the value for trading_buyer_requirement_details.buyer_flag
     *
     * @mbggenerated
     */
    public void setBuyerFlag(String buyerFlag) {
        this.buyerFlag = buyerFlag == null ? null : buyerFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_buyer_requirement_details.LinkedPayPalAccount
     *
     * @return the value of trading_buyer_requirement_details.LinkedPayPalAccount
     *
     * @mbggenerated
     */
    public String getLinkedpaypalaccount() {
        return linkedpaypalaccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_buyer_requirement_details.LinkedPayPalAccount
     *
     * @param linkedpaypalaccount the value for trading_buyer_requirement_details.LinkedPayPalAccount
     *
     * @mbggenerated
     */
    public void setLinkedpaypalaccount(String linkedpaypalaccount) {
        this.linkedpaypalaccount = linkedpaypalaccount == null ? null : linkedpaypalaccount.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_buyer_requirement_details.ShipToRegistrationCountry
     *
     * @return the value of trading_buyer_requirement_details.ShipToRegistrationCountry
     *
     * @mbggenerated
     */
    public String getShiptoregistrationcountry() {
        return shiptoregistrationcountry;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_buyer_requirement_details.ShipToRegistrationCountry
     *
     * @param shiptoregistrationcountry the value for trading_buyer_requirement_details.ShipToRegistrationCountry
     *
     * @mbggenerated
     */
    public void setShiptoregistrationcountry(String shiptoregistrationcountry) {
        this.shiptoregistrationcountry = shiptoregistrationcountry == null ? null : shiptoregistrationcountry.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_buyer_requirement_details.MinimumFeedbackScore
     *
     * @return the value of trading_buyer_requirement_details.MinimumFeedbackScore
     *
     * @mbggenerated
     */
    public Long getMinimumfeedbackscore() {
        return minimumfeedbackscore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_buyer_requirement_details.MinimumFeedbackScore
     *
     * @param minimumfeedbackscore the value for trading_buyer_requirement_details.MinimumFeedbackScore
     *
     * @mbggenerated
     */
    public void setMinimumfeedbackscore(Long minimumfeedbackscore) {
        this.minimumfeedbackscore = minimumfeedbackscore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_buyer_requirement_details.Policy_count
     *
     * @return the value of trading_buyer_requirement_details.Policy_count
     *
     * @mbggenerated
     */
    public Long getPolicyCount() {
        return policyCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_buyer_requirement_details.Policy_count
     *
     * @param policyCount the value for trading_buyer_requirement_details.Policy_count
     *
     * @mbggenerated
     */
    public void setPolicyCount(Long policyCount) {
        this.policyCount = policyCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_buyer_requirement_details.Policy_period
     *
     * @return the value of trading_buyer_requirement_details.Policy_period
     *
     * @mbggenerated
     */
    public String getPolicyPeriod() {
        return policyPeriod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_buyer_requirement_details.Policy_period
     *
     * @param policyPeriod the value for trading_buyer_requirement_details.Policy_period
     *
     * @mbggenerated
     */
    public void setPolicyPeriod(String policyPeriod) {
        this.policyPeriod = policyPeriod == null ? null : policyPeriod.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_buyer_requirement_details.Unpaid_count
     *
     * @return the value of trading_buyer_requirement_details.Unpaid_count
     *
     * @mbggenerated
     */
    public Long getUnpaidCount() {
        return unpaidCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_buyer_requirement_details.Unpaid_count
     *
     * @param unpaidCount the value for trading_buyer_requirement_details.Unpaid_count
     *
     * @mbggenerated
     */
    public void setUnpaidCount(Long unpaidCount) {
        this.unpaidCount = unpaidCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_buyer_requirement_details.Unpaid_period
     *
     * @return the value of trading_buyer_requirement_details.Unpaid_period
     *
     * @mbggenerated
     */
    public String getUnpaidPeriod() {
        return unpaidPeriod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_buyer_requirement_details.Unpaid_period
     *
     * @param unpaidPeriod the value for trading_buyer_requirement_details.Unpaid_period
     *
     * @mbggenerated
     */
    public void setUnpaidPeriod(String unpaidPeriod) {
        this.unpaidPeriod = unpaidPeriod == null ? null : unpaidPeriod.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_buyer_requirement_details.MaximumItemCount
     *
     * @return the value of trading_buyer_requirement_details.MaximumItemCount
     *
     * @mbggenerated
     */
    public Long getMaximumitemcount() {
        return maximumitemcount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_buyer_requirement_details.MaximumItemCount
     *
     * @param maximumitemcount the value for trading_buyer_requirement_details.MaximumItemCount
     *
     * @mbggenerated
     */
    public void setMaximumitemcount(Long maximumitemcount) {
        this.maximumitemcount = maximumitemcount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_buyer_requirement_details.FeedbackScore
     *
     * @return the value of trading_buyer_requirement_details.FeedbackScore
     *
     * @mbggenerated
     */
    public Long getFeedbackscore() {
        return feedbackscore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_buyer_requirement_details.FeedbackScore
     *
     * @param feedbackscore the value for trading_buyer_requirement_details.FeedbackScore
     *
     * @mbggenerated
     */
    public void setFeedbackscore(Long feedbackscore) {
        this.feedbackscore = feedbackscore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_buyer_requirement_details.Verified_flag
     *
     * @return the value of trading_buyer_requirement_details.Verified_flag
     *
     * @mbggenerated
     */
    public String getVerifiedFlag() {
        return verifiedFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_buyer_requirement_details.Verified_flag
     *
     * @param verifiedFlag the value for trading_buyer_requirement_details.Verified_flag
     *
     * @mbggenerated
     */
    public void setVerifiedFlag(String verifiedFlag) {
        this.verifiedFlag = verifiedFlag == null ? null : verifiedFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_buyer_requirement_details.Verified_FeedbackScore
     *
     * @return the value of trading_buyer_requirement_details.Verified_FeedbackScore
     *
     * @mbggenerated
     */
    public Long getVerifiedFeedbackscore() {
        return verifiedFeedbackscore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_buyer_requirement_details.Verified_FeedbackScore
     *
     * @param verifiedFeedbackscore the value for trading_buyer_requirement_details.Verified_FeedbackScore
     *
     * @mbggenerated
     */
    public void setVerifiedFeedbackscore(Long verifiedFeedbackscore) {
        this.verifiedFeedbackscore = verifiedFeedbackscore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_buyer_requirement_details.ZeroFeedbackScore
     *
     * @return the value of trading_buyer_requirement_details.ZeroFeedbackScore
     *
     * @mbggenerated
     */
    public String getZerofeedbackscore() {
        return zerofeedbackscore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_buyer_requirement_details.ZeroFeedbackScore
     *
     * @param zerofeedbackscore the value for trading_buyer_requirement_details.ZeroFeedbackScore
     *
     * @mbggenerated
     */
    public void setZerofeedbackscore(String zerofeedbackscore) {
        this.zerofeedbackscore = zerofeedbackscore == null ? null : zerofeedbackscore.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_buyer_requirement_details.create_user
     *
     * @return the value of trading_buyer_requirement_details.create_user
     *
     * @mbggenerated
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_buyer_requirement_details.create_user
     *
     * @param createUser the value for trading_buyer_requirement_details.create_user
     *
     * @mbggenerated
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_buyer_requirement_details.create_time
     *
     * @return the value of trading_buyer_requirement_details.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_buyer_requirement_details.create_time
     *
     * @param createTime the value for trading_buyer_requirement_details.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_buyer_requirement_details.uuid
     *
     * @return the value of trading_buyer_requirement_details.uuid
     *
     * @mbggenerated
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_buyer_requirement_details.uuid
     *
     * @param uuid the value for trading_buyer_requirement_details.uuid
     *
     * @mbggenerated
     */
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_buyer_requirement_details.site_value
     *
     * @return the value of trading_buyer_requirement_details.site_value
     *
     * @mbggenerated
     */
    public String getSiteValue() {
        return siteValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_buyer_requirement_details.site_value
     *
     * @param siteValue the value for trading_buyer_requirement_details.site_value
     *
     * @mbggenerated
     */
    public void setSiteValue(String siteValue) {
        this.siteValue = siteValue == null ? null : siteValue.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_buyer_requirement_details.check_flag
     *
     * @return the value of trading_buyer_requirement_details.check_flag
     *
     * @mbggenerated
     */
    public String getCheckFlag() {
        return checkFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_buyer_requirement_details.check_flag
     *
     * @param checkFlag the value for trading_buyer_requirement_details.check_flag
     *
     * @mbggenerated
     */
    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag == null ? null : checkFlag.trim();
    }
}