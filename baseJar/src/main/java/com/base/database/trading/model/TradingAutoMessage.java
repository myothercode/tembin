package com.base.database.trading.model;

import java.util.Date;

public class TradingAutoMessage {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_auto_message.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_auto_message.messageTemplate_id
     *
     * @mbggenerated
     */
    private Long messagetemplateId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_auto_message.subject
     *
     * @mbggenerated
     */
    private String subject;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_auto_message.type
     *
     * @mbggenerated
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_auto_message.ebayEmail
     *
     * @mbggenerated
     */
    private Integer ebayemail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_auto_message.email
     *
     * @mbggenerated
     */
    private Integer email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_auto_message.day
     *
     * @mbggenerated
     */
    private Integer day;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_auto_message.hour
     *
     * @mbggenerated
     */
    private Integer hour;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_auto_message.comment
     *
     * @mbggenerated
     */
    private String comment;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_auto_message.startUse
     *
     * @mbggenerated
     */
    private Integer startuse;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_auto_message.create_user
     *
     * @mbggenerated
     */
    private Long createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_auto_message.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_auto_message.uuid
     *
     * @mbggenerated
     */
    private String uuid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_auto_message.id
     *
     * @return the value of trading_auto_message.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_auto_message.id
     *
     * @param id the value for trading_auto_message.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_auto_message.messageTemplate_id
     *
     * @return the value of trading_auto_message.messageTemplate_id
     *
     * @mbggenerated
     */
    public Long getMessagetemplateId() {
        return messagetemplateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_auto_message.messageTemplate_id
     *
     * @param messagetemplateId the value for trading_auto_message.messageTemplate_id
     *
     * @mbggenerated
     */
    public void setMessagetemplateId(Long messagetemplateId) {
        this.messagetemplateId = messagetemplateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_auto_message.subject
     *
     * @return the value of trading_auto_message.subject
     *
     * @mbggenerated
     */
    public String getSubject() {
        return subject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_auto_message.subject
     *
     * @param subject the value for trading_auto_message.subject
     *
     * @mbggenerated
     */
    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_auto_message.type
     *
     * @return the value of trading_auto_message.type
     *
     * @mbggenerated
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_auto_message.type
     *
     * @param type the value for trading_auto_message.type
     *
     * @mbggenerated
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_auto_message.ebayEmail
     *
     * @return the value of trading_auto_message.ebayEmail
     *
     * @mbggenerated
     */
    public Integer getEbayemail() {
        return ebayemail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_auto_message.ebayEmail
     *
     * @param ebayemail the value for trading_auto_message.ebayEmail
     *
     * @mbggenerated
     */
    public void setEbayemail(Integer ebayemail) {
        this.ebayemail = ebayemail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_auto_message.email
     *
     * @return the value of trading_auto_message.email
     *
     * @mbggenerated
     */
    public Integer getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_auto_message.email
     *
     * @param email the value for trading_auto_message.email
     *
     * @mbggenerated
     */
    public void setEmail(Integer email) {
        this.email = email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_auto_message.day
     *
     * @return the value of trading_auto_message.day
     *
     * @mbggenerated
     */
    public Integer getDay() {
        return day;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_auto_message.day
     *
     * @param day the value for trading_auto_message.day
     *
     * @mbggenerated
     */
    public void setDay(Integer day) {
        this.day = day;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_auto_message.hour
     *
     * @return the value of trading_auto_message.hour
     *
     * @mbggenerated
     */
    public Integer getHour() {
        return hour;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_auto_message.hour
     *
     * @param hour the value for trading_auto_message.hour
     *
     * @mbggenerated
     */
    public void setHour(Integer hour) {
        this.hour = hour;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_auto_message.comment
     *
     * @return the value of trading_auto_message.comment
     *
     * @mbggenerated
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_auto_message.comment
     *
     * @param comment the value for trading_auto_message.comment
     *
     * @mbggenerated
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_auto_message.startUse
     *
     * @return the value of trading_auto_message.startUse
     *
     * @mbggenerated
     */
    public Integer getStartuse() {
        return startuse;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_auto_message.startUse
     *
     * @param startuse the value for trading_auto_message.startUse
     *
     * @mbggenerated
     */
    public void setStartuse(Integer startuse) {
        this.startuse = startuse;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_auto_message.create_user
     *
     * @return the value of trading_auto_message.create_user
     *
     * @mbggenerated
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_auto_message.create_user
     *
     * @param createUser the value for trading_auto_message.create_user
     *
     * @mbggenerated
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_auto_message.create_time
     *
     * @return the value of trading_auto_message.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_auto_message.create_time
     *
     * @param createTime the value for trading_auto_message.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_auto_message.uuid
     *
     * @return the value of trading_auto_message.uuid
     *
     * @mbggenerated
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_auto_message.uuid
     *
     * @param uuid the value for trading_auto_message.uuid
     *
     * @mbggenerated
     */
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }
}