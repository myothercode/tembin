package com.base.database.trading.model;

import java.util.Date;

public class TradingPriceTrackingAutoPricing {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking_auto_pricing.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking_auto_pricing.sku
     *
     * @mbggenerated
     */
    private String sku;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking_auto_pricing.listingDate_id
     *
     * @mbggenerated
     */
    private Long listingdateId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking_auto_pricing.oldPrice
     *
     * @mbggenerated
     */
    private Double oldprice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking_auto_pricing.newPrice
     *
     * @mbggenerated
     */
    private Double newprice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking_auto_pricing.create_user
     *
     * @mbggenerated
     */
    private Long createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking_auto_pricing.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking_auto_pricing.uuid
     *
     * @mbggenerated
     */
    private String uuid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking_auto_pricing.id
     *
     * @return the value of trading_price_tracking_auto_pricing.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking_auto_pricing.id
     *
     * @param id the value for trading_price_tracking_auto_pricing.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking_auto_pricing.sku
     *
     * @return the value of trading_price_tracking_auto_pricing.sku
     *
     * @mbggenerated
     */
    public String getSku() {
        return sku;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking_auto_pricing.sku
     *
     * @param sku the value for trading_price_tracking_auto_pricing.sku
     *
     * @mbggenerated
     */
    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking_auto_pricing.listingDate_id
     *
     * @return the value of trading_price_tracking_auto_pricing.listingDate_id
     *
     * @mbggenerated
     */
    public Long getListingdateId() {
        return listingdateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking_auto_pricing.listingDate_id
     *
     * @param listingdateId the value for trading_price_tracking_auto_pricing.listingDate_id
     *
     * @mbggenerated
     */
    public void setListingdateId(Long listingdateId) {
        this.listingdateId = listingdateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking_auto_pricing.oldPrice
     *
     * @return the value of trading_price_tracking_auto_pricing.oldPrice
     *
     * @mbggenerated
     */
    public Double getOldprice() {
        return oldprice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking_auto_pricing.oldPrice
     *
     * @param oldprice the value for trading_price_tracking_auto_pricing.oldPrice
     *
     * @mbggenerated
     */
    public void setOldprice(Double oldprice) {
        this.oldprice = oldprice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking_auto_pricing.newPrice
     *
     * @return the value of trading_price_tracking_auto_pricing.newPrice
     *
     * @mbggenerated
     */
    public Double getNewprice() {
        return newprice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking_auto_pricing.newPrice
     *
     * @param newprice the value for trading_price_tracking_auto_pricing.newPrice
     *
     * @mbggenerated
     */
    public void setNewprice(Double newprice) {
        this.newprice = newprice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking_auto_pricing.create_user
     *
     * @return the value of trading_price_tracking_auto_pricing.create_user
     *
     * @mbggenerated
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking_auto_pricing.create_user
     *
     * @param createUser the value for trading_price_tracking_auto_pricing.create_user
     *
     * @mbggenerated
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking_auto_pricing.create_time
     *
     * @return the value of trading_price_tracking_auto_pricing.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking_auto_pricing.create_time
     *
     * @param createTime the value for trading_price_tracking_auto_pricing.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking_auto_pricing.uuid
     *
     * @return the value of trading_price_tracking_auto_pricing.uuid
     *
     * @mbggenerated
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking_auto_pricing.uuid
     *
     * @param uuid the value for trading_price_tracking_auto_pricing.uuid
     *
     * @mbggenerated
     */
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }
}