package com.base.database.inventory.model;

import java.util.Date;

public class TaskSyncInventory {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_sync_inventory.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_sync_inventory.user_name
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_sync_inventory.user_key
     *
     * @mbggenerated
     */
    private String userKey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_sync_inventory.create_date
     *
     * @mbggenerated
     */
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_sync_inventory.task_flag
     *
     * @mbggenerated
     */
    private String taskFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_sync_inventory.data_type
     *
     * @mbggenerated
     */
    private String dataType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_sync_inventory.org_id
     *
     * @mbggenerated
     */
    private Long orgId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_sync_inventory.id
     *
     * @return the value of task_sync_inventory.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_sync_inventory.id
     *
     * @param id the value for task_sync_inventory.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_sync_inventory.user_name
     *
     * @return the value of task_sync_inventory.user_name
     *
     * @mbggenerated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_sync_inventory.user_name
     *
     * @param userName the value for task_sync_inventory.user_name
     *
     * @mbggenerated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_sync_inventory.user_key
     *
     * @return the value of task_sync_inventory.user_key
     *
     * @mbggenerated
     */
    public String getUserKey() {
        return userKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_sync_inventory.user_key
     *
     * @param userKey the value for task_sync_inventory.user_key
     *
     * @mbggenerated
     */
    public void setUserKey(String userKey) {
        this.userKey = userKey == null ? null : userKey.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_sync_inventory.create_date
     *
     * @return the value of task_sync_inventory.create_date
     *
     * @mbggenerated
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_sync_inventory.create_date
     *
     * @param createDate the value for task_sync_inventory.create_date
     *
     * @mbggenerated
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_sync_inventory.task_flag
     *
     * @return the value of task_sync_inventory.task_flag
     *
     * @mbggenerated
     */
    public String getTaskFlag() {
        return taskFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_sync_inventory.task_flag
     *
     * @param taskFlag the value for task_sync_inventory.task_flag
     *
     * @mbggenerated
     */
    public void setTaskFlag(String taskFlag) {
        this.taskFlag = taskFlag == null ? null : taskFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_sync_inventory.data_type
     *
     * @return the value of task_sync_inventory.data_type
     *
     * @mbggenerated
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_sync_inventory.data_type
     *
     * @param dataType the value for task_sync_inventory.data_type
     *
     * @mbggenerated
     */
    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_sync_inventory.org_id
     *
     * @return the value of task_sync_inventory.org_id
     *
     * @mbggenerated
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_sync_inventory.org_id
     *
     * @param orgId the value for task_sync_inventory.org_id
     *
     * @mbggenerated
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}