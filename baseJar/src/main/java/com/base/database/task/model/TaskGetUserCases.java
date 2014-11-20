package com.base.database.task.model;

import java.util.Date;

public class TaskGetUserCases {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_get_user_cases.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_get_user_cases.userId
     *
     * @mbggenerated
     */
    private Long userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_get_user_cases.fromtime
     *
     * @mbggenerated
     */
    private String fromtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_get_user_cases.endtime
     *
     * @mbggenerated
     */
    private String endtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_get_user_cases.tokenFlag
     *
     * @mbggenerated
     */
    private Integer tokenflag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_get_user_cases.savetime
     *
     * @mbggenerated
     */
    private Date savetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_get_user_cases.token
     *
     * @mbggenerated
     */
    private String token;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_get_user_cases.id
     *
     * @return the value of task_get_user_cases.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_get_user_cases.id
     *
     * @param id the value for task_get_user_cases.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_get_user_cases.userId
     *
     * @return the value of task_get_user_cases.userId
     *
     * @mbggenerated
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_get_user_cases.userId
     *
     * @param userid the value for task_get_user_cases.userId
     *
     * @mbggenerated
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_get_user_cases.fromtime
     *
     * @return the value of task_get_user_cases.fromtime
     *
     * @mbggenerated
     */
    public String getFromtime() {
        return fromtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_get_user_cases.fromtime
     *
     * @param fromtime the value for task_get_user_cases.fromtime
     *
     * @mbggenerated
     */
    public void setFromtime(String fromtime) {
        this.fromtime = fromtime == null ? null : fromtime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_get_user_cases.endtime
     *
     * @return the value of task_get_user_cases.endtime
     *
     * @mbggenerated
     */
    public String getEndtime() {
        return endtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_get_user_cases.endtime
     *
     * @param endtime the value for task_get_user_cases.endtime
     *
     * @mbggenerated
     */
    public void setEndtime(String endtime) {
        this.endtime = endtime == null ? null : endtime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_get_user_cases.tokenFlag
     *
     * @return the value of task_get_user_cases.tokenFlag
     *
     * @mbggenerated
     */
    public Integer getTokenflag() {
        return tokenflag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_get_user_cases.tokenFlag
     *
     * @param tokenflag the value for task_get_user_cases.tokenFlag
     *
     * @mbggenerated
     */
    public void setTokenflag(Integer tokenflag) {
        this.tokenflag = tokenflag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_get_user_cases.savetime
     *
     * @return the value of task_get_user_cases.savetime
     *
     * @mbggenerated
     */
    public Date getSavetime() {
        return savetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_get_user_cases.savetime
     *
     * @param savetime the value for task_get_user_cases.savetime
     *
     * @mbggenerated
     */
    public void setSavetime(Date savetime) {
        this.savetime = savetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_get_user_cases.token
     *
     * @return the value of task_get_user_cases.token
     *
     * @mbggenerated
     */
    public String getToken() {
        return token;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_get_user_cases.token
     *
     * @param token the value for task_get_user_cases.token
     *
     * @mbggenerated
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }
}