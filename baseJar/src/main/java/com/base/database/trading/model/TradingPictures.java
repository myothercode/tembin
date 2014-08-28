package com.base.database.trading.model;

import java.util.Date;

public class TradingPictures {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_pictures.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_pictures.VariationSpecificName
     *
     * @mbggenerated
     */
    private String variationspecificname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_pictures.create_user
     *
     * @mbggenerated
     */
    private Long createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_pictures.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_pictures.uuid
     *
     * @mbggenerated
     */
    private String uuid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_pictures.parent_id
     *
     * @mbggenerated
     */
    private Long parentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_pictures.parent_uuid
     *
     * @mbggenerated
     */
    private String parentUuid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_pictures.id
     *
     * @return the value of trading_pictures.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_pictures.id
     *
     * @param id the value for trading_pictures.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_pictures.VariationSpecificName
     *
     * @return the value of trading_pictures.VariationSpecificName
     *
     * @mbggenerated
     */
    public String getVariationspecificname() {
        return variationspecificname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_pictures.VariationSpecificName
     *
     * @param variationspecificname the value for trading_pictures.VariationSpecificName
     *
     * @mbggenerated
     */
    public void setVariationspecificname(String variationspecificname) {
        this.variationspecificname = variationspecificname == null ? null : variationspecificname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_pictures.create_user
     *
     * @return the value of trading_pictures.create_user
     *
     * @mbggenerated
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_pictures.create_user
     *
     * @param createUser the value for trading_pictures.create_user
     *
     * @mbggenerated
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_pictures.create_time
     *
     * @return the value of trading_pictures.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_pictures.create_time
     *
     * @param createTime the value for trading_pictures.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_pictures.uuid
     *
     * @return the value of trading_pictures.uuid
     *
     * @mbggenerated
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_pictures.uuid
     *
     * @param uuid the value for trading_pictures.uuid
     *
     * @mbggenerated
     */
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_pictures.parent_id
     *
     * @return the value of trading_pictures.parent_id
     *
     * @mbggenerated
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_pictures.parent_id
     *
     * @param parentId the value for trading_pictures.parent_id
     *
     * @mbggenerated
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_pictures.parent_uuid
     *
     * @return the value of trading_pictures.parent_uuid
     *
     * @mbggenerated
     */
    public String getParentUuid() {
        return parentUuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_pictures.parent_uuid
     *
     * @param parentUuid the value for trading_pictures.parent_uuid
     *
     * @mbggenerated
     */
    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid == null ? null : parentUuid.trim();
    }
}