package com.base.database.trading.model;

import java.util.Date;

public class TradingPriceTracking {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking.itemId
     *
     * @mbggenerated
     */
    private String itemid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking.title
     *
     * @mbggenerated
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking.queryTitle
     *
     * @mbggenerated
     */
    private String querytitle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking.sellerUserName
     *
     * @mbggenerated
     */
    private String sellerusername;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking.currentPrice
     *
     * @mbggenerated
     */
    private String currentprice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking.currencyId
     *
     * @mbggenerated
     */
    private String currencyid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking.categoryId
     *
     * @mbggenerated
     */
    private String categoryid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking.categoryName
     *
     * @mbggenerated
     */
    private String categoryname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking.bidCount
     *
     * @mbggenerated
     */
    private String bidcount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking.itemCode
     *
     * @mbggenerated
     */
    private String itemcode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking.create_user
     *
     * @mbggenerated
     */
    private Long createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking.id
     *
     * @return the value of trading_price_tracking.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking.id
     *
     * @param id the value for trading_price_tracking.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking.itemId
     *
     * @return the value of trading_price_tracking.itemId
     *
     * @mbggenerated
     */
    public String getItemid() {
        return itemid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking.itemId
     *
     * @param itemid the value for trading_price_tracking.itemId
     *
     * @mbggenerated
     */
    public void setItemid(String itemid) {
        this.itemid = itemid == null ? null : itemid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking.title
     *
     * @return the value of trading_price_tracking.title
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking.title
     *
     * @param title the value for trading_price_tracking.title
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking.queryTitle
     *
     * @return the value of trading_price_tracking.queryTitle
     *
     * @mbggenerated
     */
    public String getQuerytitle() {
        return querytitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking.queryTitle
     *
     * @param querytitle the value for trading_price_tracking.queryTitle
     *
     * @mbggenerated
     */
    public void setQuerytitle(String querytitle) {
        this.querytitle = querytitle == null ? null : querytitle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking.sellerUserName
     *
     * @return the value of trading_price_tracking.sellerUserName
     *
     * @mbggenerated
     */
    public String getSellerusername() {
        return sellerusername;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking.sellerUserName
     *
     * @param sellerusername the value for trading_price_tracking.sellerUserName
     *
     * @mbggenerated
     */
    public void setSellerusername(String sellerusername) {
        this.sellerusername = sellerusername == null ? null : sellerusername.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking.currentPrice
     *
     * @return the value of trading_price_tracking.currentPrice
     *
     * @mbggenerated
     */
    public String getCurrentprice() {
        return currentprice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking.currentPrice
     *
     * @param currentprice the value for trading_price_tracking.currentPrice
     *
     * @mbggenerated
     */
    public void setCurrentprice(String currentprice) {
        this.currentprice = currentprice == null ? null : currentprice.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking.currencyId
     *
     * @return the value of trading_price_tracking.currencyId
     *
     * @mbggenerated
     */
    public String getCurrencyid() {
        return currencyid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking.currencyId
     *
     * @param currencyid the value for trading_price_tracking.currencyId
     *
     * @mbggenerated
     */
    public void setCurrencyid(String currencyid) {
        this.currencyid = currencyid == null ? null : currencyid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking.categoryId
     *
     * @return the value of trading_price_tracking.categoryId
     *
     * @mbggenerated
     */
    public String getCategoryid() {
        return categoryid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking.categoryId
     *
     * @param categoryid the value for trading_price_tracking.categoryId
     *
     * @mbggenerated
     */
    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid == null ? null : categoryid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking.categoryName
     *
     * @return the value of trading_price_tracking.categoryName
     *
     * @mbggenerated
     */
    public String getCategoryname() {
        return categoryname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking.categoryName
     *
     * @param categoryname the value for trading_price_tracking.categoryName
     *
     * @mbggenerated
     */
    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname == null ? null : categoryname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking.bidCount
     *
     * @return the value of trading_price_tracking.bidCount
     *
     * @mbggenerated
     */
    public String getBidcount() {
        return bidcount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking.bidCount
     *
     * @param bidcount the value for trading_price_tracking.bidCount
     *
     * @mbggenerated
     */
    public void setBidcount(String bidcount) {
        this.bidcount = bidcount == null ? null : bidcount.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking.itemCode
     *
     * @return the value of trading_price_tracking.itemCode
     *
     * @mbggenerated
     */
    public String getItemcode() {
        return itemcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking.itemCode
     *
     * @param itemcode the value for trading_price_tracking.itemCode
     *
     * @mbggenerated
     */
    public void setItemcode(String itemcode) {
        this.itemcode = itemcode == null ? null : itemcode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking.create_user
     *
     * @return the value of trading_price_tracking.create_user
     *
     * @mbggenerated
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking.create_user
     *
     * @param createUser the value for trading_price_tracking.create_user
     *
     * @mbggenerated
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking.create_time
     *
     * @return the value of trading_price_tracking.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking.create_time
     *
     * @param createTime the value for trading_price_tracking.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}