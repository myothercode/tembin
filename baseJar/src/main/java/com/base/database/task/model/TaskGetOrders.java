package com.base.database.task.model;

import java.util.Date;

public class TaskGetOrders {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_get_orders.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_get_orders.userid
     *
     * @mbggenerated
     */
    private Long userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_get_orders.fromtime
     *
     * @mbggenerated
     */
    private String fromtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_get_orders.totime
     *
     * @mbggenerated
     */
    private String totime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_get_orders.tokenFlag
     *
     * @mbggenerated
     */
    private Integer tokenflag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_get_orders.savetime
     *
     * @mbggenerated
     */
    private Date savetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_get_orders.token
     *
     * @mbggenerated
     */
    private String token;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_get_orders.id
     *
     * @return the value of task_get_orders.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_get_orders.id
     *
     * @param id the value for task_get_orders.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_get_orders.userid
     *
     * @return the value of task_get_orders.userid
     *
     * @mbggenerated
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_get_orders.userid
     *
     * @param userid the value for task_get_orders.userid
     *
     * @mbggenerated
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_get_orders.fromtime
     *
     * @return the value of task_get_orders.fromtime
     *
     * @mbggenerated
     */
    public String getFromtime() {
        return fromtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_get_orders.fromtime
     *
     * @param fromtime the value for task_get_orders.fromtime
     *
     * @mbggenerated
     */
    public void setFromtime(String fromtime) {
        this.fromtime = fromtime == null ? null : fromtime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_get_orders.totime
     *
     * @return the value of task_get_orders.totime
     *
     * @mbggenerated
     */
    public String getTotime() {
        return totime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_get_orders.totime
     *
     * @param totime the value for task_get_orders.totime
     *
     * @mbggenerated
     */
    public void setTotime(String totime) {
        this.totime = totime == null ? null : totime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_get_orders.tokenFlag
     *
     * @return the value of task_get_orders.tokenFlag
     *
     * @mbggenerated
     */
    public Integer getTokenflag() {
        return tokenflag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_get_orders.tokenFlag
     *
     * @param tokenflag the value for task_get_orders.tokenFlag
     *
     * @mbggenerated
     */
    public void setTokenflag(Integer tokenflag) {
        this.tokenflag = tokenflag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_get_orders.savetime
     *
     * @return the value of task_get_orders.savetime
     *
     * @mbggenerated
     */
    public Date getSavetime() {
        return savetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_get_orders.savetime
     *
     * @param savetime the value for task_get_orders.savetime
     *
     * @mbggenerated
     */
    public void setSavetime(Date savetime) {
        this.savetime = savetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_get_orders.token
     *
     * @return the value of task_get_orders.token
     *
     * @mbggenerated
     */
    public String getToken() {
        return token;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_get_orders.token
     *
     * @param token the value for task_get_orders.token
     *
     * @mbggenerated
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }
}