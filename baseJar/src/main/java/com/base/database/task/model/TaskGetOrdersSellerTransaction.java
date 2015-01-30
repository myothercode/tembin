package com.base.database.task.model;

import java.util.Date;

public class TaskGetOrdersSellerTransaction {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_get_orders_seller_transaction.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_get_orders_seller_transaction.userid
     *
     * @mbggenerated
     */
    private Long userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_get_orders_seller_transaction.tokenFlag
     *
     * @mbggenerated
     */
    private Integer tokenflag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_get_orders_seller_transaction.savetime
     *
     * @mbggenerated
     */
    private Date savetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_get_orders_seller_transaction.token
     *
     * @mbggenerated
     */
    private String token;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_get_orders_seller_transaction.id
     *
     * @return the value of task_get_orders_seller_transaction.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_get_orders_seller_transaction.id
     *
     * @param id the value for task_get_orders_seller_transaction.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_get_orders_seller_transaction.userid
     *
     * @return the value of task_get_orders_seller_transaction.userid
     *
     * @mbggenerated
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_get_orders_seller_transaction.userid
     *
     * @param userid the value for task_get_orders_seller_transaction.userid
     *
     * @mbggenerated
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_get_orders_seller_transaction.tokenFlag
     *
     * @return the value of task_get_orders_seller_transaction.tokenFlag
     *
     * @mbggenerated
     */
    public Integer getTokenflag() {
        return tokenflag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_get_orders_seller_transaction.tokenFlag
     *
     * @param tokenflag the value for task_get_orders_seller_transaction.tokenFlag
     *
     * @mbggenerated
     */
    public void setTokenflag(Integer tokenflag) {
        this.tokenflag = tokenflag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_get_orders_seller_transaction.savetime
     *
     * @return the value of task_get_orders_seller_transaction.savetime
     *
     * @mbggenerated
     */
    public Date getSavetime() {
        return savetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_get_orders_seller_transaction.savetime
     *
     * @param savetime the value for task_get_orders_seller_transaction.savetime
     *
     * @mbggenerated
     */
    public void setSavetime(Date savetime) {
        this.savetime = savetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_get_orders_seller_transaction.token
     *
     * @return the value of task_get_orders_seller_transaction.token
     *
     * @mbggenerated
     */
    public String getToken() {
        return token;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_get_orders_seller_transaction.token
     *
     * @param token the value for task_get_orders_seller_transaction.token
     *
     * @mbggenerated
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }
}