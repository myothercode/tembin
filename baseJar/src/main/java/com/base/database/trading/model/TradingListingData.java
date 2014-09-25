package com.base.database.trading.model;

import java.util.Date;

public class TradingListingData {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_listing_data.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_listing_data.item_id
     *
     * @mbggenerated
     */
    private String itemId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_listing_data.pic_url
     *
     * @mbggenerated
     */
    private String picUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_listing_data.sku
     *
     * @mbggenerated
     */
    private String sku;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_listing_data.ebay_account
     *
     * @mbggenerated
     */
    private String ebayAccount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_listing_data.site
     *
     * @mbggenerated
     */
    private String site;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_listing_data.listing_type
     *
     * @mbggenerated
     */
    private String listingType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_listing_data.price
     *
     * @mbggenerated
     */
    private Double price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_listing_data.shipping_price
     *
     * @mbggenerated
     */
    private Double shippingPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_listing_data.Quantity
     *
     * @mbggenerated
     */
    private Long quantity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_listing_data.QuantitySold
     *
     * @mbggenerated
     */
    private Long quantitysold;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_listing_data.ListingDuration
     *
     * @mbggenerated
     */
    private String listingduration;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_listing_data.StartTime
     *
     * @mbggenerated
     */
    private Date starttime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_listing_data.EndTime
     *
     * @mbggenerated
     */
    private Date endtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_listing_data.do_rate
     *
     * @mbggenerated
     */
    private Long doRate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_listing_data.create_date
     *
     * @mbggenerated
     */
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_listing_data.update_date
     *
     * @mbggenerated
     */
    private Date updateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_listing_data.title
     *
     * @mbggenerated
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_listing_data.create_user
     *
     * @mbggenerated
     */
    private Long createUser;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_listing_data.id
     *
     * @return the value of trading_listing_data.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_listing_data.id
     *
     * @param id the value for trading_listing_data.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_listing_data.item_id
     *
     * @return the value of trading_listing_data.item_id
     *
     * @mbggenerated
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_listing_data.item_id
     *
     * @param itemId the value for trading_listing_data.item_id
     *
     * @mbggenerated
     */
    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_listing_data.pic_url
     *
     * @return the value of trading_listing_data.pic_url
     *
     * @mbggenerated
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_listing_data.pic_url
     *
     * @param picUrl the value for trading_listing_data.pic_url
     *
     * @mbggenerated
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_listing_data.sku
     *
     * @return the value of trading_listing_data.sku
     *
     * @mbggenerated
     */
    public String getSku() {
        return sku;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_listing_data.sku
     *
     * @param sku the value for trading_listing_data.sku
     *
     * @mbggenerated
     */
    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_listing_data.ebay_account
     *
     * @return the value of trading_listing_data.ebay_account
     *
     * @mbggenerated
     */
    public String getEbayAccount() {
        return ebayAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_listing_data.ebay_account
     *
     * @param ebayAccount the value for trading_listing_data.ebay_account
     *
     * @mbggenerated
     */
    public void setEbayAccount(String ebayAccount) {
        this.ebayAccount = ebayAccount == null ? null : ebayAccount.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_listing_data.site
     *
     * @return the value of trading_listing_data.site
     *
     * @mbggenerated
     */
    public String getSite() {
        return site;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_listing_data.site
     *
     * @param site the value for trading_listing_data.site
     *
     * @mbggenerated
     */
    public void setSite(String site) {
        this.site = site == null ? null : site.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_listing_data.listing_type
     *
     * @return the value of trading_listing_data.listing_type
     *
     * @mbggenerated
     */
    public String getListingType() {
        return listingType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_listing_data.listing_type
     *
     * @param listingType the value for trading_listing_data.listing_type
     *
     * @mbggenerated
     */
    public void setListingType(String listingType) {
        this.listingType = listingType == null ? null : listingType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_listing_data.price
     *
     * @return the value of trading_listing_data.price
     *
     * @mbggenerated
     */
    public Double getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_listing_data.price
     *
     * @param price the value for trading_listing_data.price
     *
     * @mbggenerated
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_listing_data.shipping_price
     *
     * @return the value of trading_listing_data.shipping_price
     *
     * @mbggenerated
     */
    public Double getShippingPrice() {
        return shippingPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_listing_data.shipping_price
     *
     * @param shippingPrice the value for trading_listing_data.shipping_price
     *
     * @mbggenerated
     */
    public void setShippingPrice(Double shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_listing_data.Quantity
     *
     * @return the value of trading_listing_data.Quantity
     *
     * @mbggenerated
     */
    public Long getQuantity() {
        return quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_listing_data.Quantity
     *
     * @param quantity the value for trading_listing_data.Quantity
     *
     * @mbggenerated
     */
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_listing_data.QuantitySold
     *
     * @return the value of trading_listing_data.QuantitySold
     *
     * @mbggenerated
     */
    public Long getQuantitysold() {
        return quantitysold;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_listing_data.QuantitySold
     *
     * @param quantitysold the value for trading_listing_data.QuantitySold
     *
     * @mbggenerated
     */
    public void setQuantitysold(Long quantitysold) {
        this.quantitysold = quantitysold;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_listing_data.ListingDuration
     *
     * @return the value of trading_listing_data.ListingDuration
     *
     * @mbggenerated
     */
    public String getListingduration() {
        return listingduration;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_listing_data.ListingDuration
     *
     * @param listingduration the value for trading_listing_data.ListingDuration
     *
     * @mbggenerated
     */
    public void setListingduration(String listingduration) {
        this.listingduration = listingduration == null ? null : listingduration.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_listing_data.StartTime
     *
     * @return the value of trading_listing_data.StartTime
     *
     * @mbggenerated
     */
    public Date getStarttime() {
        return starttime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_listing_data.StartTime
     *
     * @param starttime the value for trading_listing_data.StartTime
     *
     * @mbggenerated
     */
    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_listing_data.EndTime
     *
     * @return the value of trading_listing_data.EndTime
     *
     * @mbggenerated
     */
    public Date getEndtime() {
        return endtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_listing_data.EndTime
     *
     * @param endtime the value for trading_listing_data.EndTime
     *
     * @mbggenerated
     */
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_listing_data.do_rate
     *
     * @return the value of trading_listing_data.do_rate
     *
     * @mbggenerated
     */
    public Long getDoRate() {
        return doRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_listing_data.do_rate
     *
     * @param doRate the value for trading_listing_data.do_rate
     *
     * @mbggenerated
     */
    public void setDoRate(Long doRate) {
        this.doRate = doRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_listing_data.create_date
     *
     * @return the value of trading_listing_data.create_date
     *
     * @mbggenerated
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_listing_data.create_date
     *
     * @param createDate the value for trading_listing_data.create_date
     *
     * @mbggenerated
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_listing_data.update_date
     *
     * @return the value of trading_listing_data.update_date
     *
     * @mbggenerated
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_listing_data.update_date
     *
     * @param updateDate the value for trading_listing_data.update_date
     *
     * @mbggenerated
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_listing_data.title
     *
     * @return the value of trading_listing_data.title
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_listing_data.title
     *
     * @param title the value for trading_listing_data.title
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_listing_data.create_user
     *
     * @return the value of trading_listing_data.create_user
     *
     * @mbggenerated
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_listing_data.create_user
     *
     * @param createUser the value for trading_listing_data.create_user
     *
     * @mbggenerated
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
}