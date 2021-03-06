package com.base.database.trading.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TradingDescriptionDetailsExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    public TradingDescriptionDetailsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(Long value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(Long value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(Long value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(Long value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(Long value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(Long value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<Long> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<Long> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(Long value1, Long value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(Long value1, Long value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUuidIsNull() {
            addCriterion("uuid is null");
            return (Criteria) this;
        }

        public Criteria andUuidIsNotNull() {
            addCriterion("uuid is not null");
            return (Criteria) this;
        }

        public Criteria andUuidEqualTo(String value) {
            addCriterion("uuid =", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotEqualTo(String value) {
            addCriterion("uuid <>", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidGreaterThan(String value) {
            addCriterion("uuid >", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidGreaterThanOrEqualTo(String value) {
            addCriterion("uuid >=", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLessThan(String value) {
            addCriterion("uuid <", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLessThanOrEqualTo(String value) {
            addCriterion("uuid <=", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLike(String value) {
            addCriterion("uuid like", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotLike(String value) {
            addCriterion("uuid not like", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidIn(List<String> values) {
            addCriterion("uuid in", values, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotIn(List<String> values) {
            addCriterion("uuid not in", values, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidBetween(String value1, String value2) {
            addCriterion("uuid between", value1, value2, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotBetween(String value1, String value2) {
            addCriterion("uuid not between", value1, value2, "uuid");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andPayTitleIsNull() {
            addCriterion("pay_title is null");
            return (Criteria) this;
        }

        public Criteria andPayTitleIsNotNull() {
            addCriterion("pay_title is not null");
            return (Criteria) this;
        }

        public Criteria andPayTitleEqualTo(String value) {
            addCriterion("pay_title =", value, "payTitle");
            return (Criteria) this;
        }

        public Criteria andPayTitleNotEqualTo(String value) {
            addCriterion("pay_title <>", value, "payTitle");
            return (Criteria) this;
        }

        public Criteria andPayTitleGreaterThan(String value) {
            addCriterion("pay_title >", value, "payTitle");
            return (Criteria) this;
        }

        public Criteria andPayTitleGreaterThanOrEqualTo(String value) {
            addCriterion("pay_title >=", value, "payTitle");
            return (Criteria) this;
        }

        public Criteria andPayTitleLessThan(String value) {
            addCriterion("pay_title <", value, "payTitle");
            return (Criteria) this;
        }

        public Criteria andPayTitleLessThanOrEqualTo(String value) {
            addCriterion("pay_title <=", value, "payTitle");
            return (Criteria) this;
        }

        public Criteria andPayTitleLike(String value) {
            addCriterion("pay_title like", value, "payTitle");
            return (Criteria) this;
        }

        public Criteria andPayTitleNotLike(String value) {
            addCriterion("pay_title not like", value, "payTitle");
            return (Criteria) this;
        }

        public Criteria andPayTitleIn(List<String> values) {
            addCriterion("pay_title in", values, "payTitle");
            return (Criteria) this;
        }

        public Criteria andPayTitleNotIn(List<String> values) {
            addCriterion("pay_title not in", values, "payTitle");
            return (Criteria) this;
        }

        public Criteria andPayTitleBetween(String value1, String value2) {
            addCriterion("pay_title between", value1, value2, "payTitle");
            return (Criteria) this;
        }

        public Criteria andPayTitleNotBetween(String value1, String value2) {
            addCriterion("pay_title not between", value1, value2, "payTitle");
            return (Criteria) this;
        }

        public Criteria andShippingTitleIsNull() {
            addCriterion("shipping_title is null");
            return (Criteria) this;
        }

        public Criteria andShippingTitleIsNotNull() {
            addCriterion("shipping_title is not null");
            return (Criteria) this;
        }

        public Criteria andShippingTitleEqualTo(String value) {
            addCriterion("shipping_title =", value, "shippingTitle");
            return (Criteria) this;
        }

        public Criteria andShippingTitleNotEqualTo(String value) {
            addCriterion("shipping_title <>", value, "shippingTitle");
            return (Criteria) this;
        }

        public Criteria andShippingTitleGreaterThan(String value) {
            addCriterion("shipping_title >", value, "shippingTitle");
            return (Criteria) this;
        }

        public Criteria andShippingTitleGreaterThanOrEqualTo(String value) {
            addCriterion("shipping_title >=", value, "shippingTitle");
            return (Criteria) this;
        }

        public Criteria andShippingTitleLessThan(String value) {
            addCriterion("shipping_title <", value, "shippingTitle");
            return (Criteria) this;
        }

        public Criteria andShippingTitleLessThanOrEqualTo(String value) {
            addCriterion("shipping_title <=", value, "shippingTitle");
            return (Criteria) this;
        }

        public Criteria andShippingTitleLike(String value) {
            addCriterion("shipping_title like", value, "shippingTitle");
            return (Criteria) this;
        }

        public Criteria andShippingTitleNotLike(String value) {
            addCriterion("shipping_title not like", value, "shippingTitle");
            return (Criteria) this;
        }

        public Criteria andShippingTitleIn(List<String> values) {
            addCriterion("shipping_title in", values, "shippingTitle");
            return (Criteria) this;
        }

        public Criteria andShippingTitleNotIn(List<String> values) {
            addCriterion("shipping_title not in", values, "shippingTitle");
            return (Criteria) this;
        }

        public Criteria andShippingTitleBetween(String value1, String value2) {
            addCriterion("shipping_title between", value1, value2, "shippingTitle");
            return (Criteria) this;
        }

        public Criteria andShippingTitleNotBetween(String value1, String value2) {
            addCriterion("shipping_title not between", value1, value2, "shippingTitle");
            return (Criteria) this;
        }

        public Criteria andContactTitleIsNull() {
            addCriterion("contact_title is null");
            return (Criteria) this;
        }

        public Criteria andContactTitleIsNotNull() {
            addCriterion("contact_title is not null");
            return (Criteria) this;
        }

        public Criteria andContactTitleEqualTo(String value) {
            addCriterion("contact_title =", value, "contactTitle");
            return (Criteria) this;
        }

        public Criteria andContactTitleNotEqualTo(String value) {
            addCriterion("contact_title <>", value, "contactTitle");
            return (Criteria) this;
        }

        public Criteria andContactTitleGreaterThan(String value) {
            addCriterion("contact_title >", value, "contactTitle");
            return (Criteria) this;
        }

        public Criteria andContactTitleGreaterThanOrEqualTo(String value) {
            addCriterion("contact_title >=", value, "contactTitle");
            return (Criteria) this;
        }

        public Criteria andContactTitleLessThan(String value) {
            addCriterion("contact_title <", value, "contactTitle");
            return (Criteria) this;
        }

        public Criteria andContactTitleLessThanOrEqualTo(String value) {
            addCriterion("contact_title <=", value, "contactTitle");
            return (Criteria) this;
        }

        public Criteria andContactTitleLike(String value) {
            addCriterion("contact_title like", value, "contactTitle");
            return (Criteria) this;
        }

        public Criteria andContactTitleNotLike(String value) {
            addCriterion("contact_title not like", value, "contactTitle");
            return (Criteria) this;
        }

        public Criteria andContactTitleIn(List<String> values) {
            addCriterion("contact_title in", values, "contactTitle");
            return (Criteria) this;
        }

        public Criteria andContactTitleNotIn(List<String> values) {
            addCriterion("contact_title not in", values, "contactTitle");
            return (Criteria) this;
        }

        public Criteria andContactTitleBetween(String value1, String value2) {
            addCriterion("contact_title between", value1, value2, "contactTitle");
            return (Criteria) this;
        }

        public Criteria andContactTitleNotBetween(String value1, String value2) {
            addCriterion("contact_title not between", value1, value2, "contactTitle");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTitleIsNull() {
            addCriterion("Guarantee_title is null");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTitleIsNotNull() {
            addCriterion("Guarantee_title is not null");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTitleEqualTo(String value) {
            addCriterion("Guarantee_title =", value, "guaranteeTitle");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTitleNotEqualTo(String value) {
            addCriterion("Guarantee_title <>", value, "guaranteeTitle");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTitleGreaterThan(String value) {
            addCriterion("Guarantee_title >", value, "guaranteeTitle");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTitleGreaterThanOrEqualTo(String value) {
            addCriterion("Guarantee_title >=", value, "guaranteeTitle");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTitleLessThan(String value) {
            addCriterion("Guarantee_title <", value, "guaranteeTitle");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTitleLessThanOrEqualTo(String value) {
            addCriterion("Guarantee_title <=", value, "guaranteeTitle");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTitleLike(String value) {
            addCriterion("Guarantee_title like", value, "guaranteeTitle");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTitleNotLike(String value) {
            addCriterion("Guarantee_title not like", value, "guaranteeTitle");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTitleIn(List<String> values) {
            addCriterion("Guarantee_title in", values, "guaranteeTitle");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTitleNotIn(List<String> values) {
            addCriterion("Guarantee_title not in", values, "guaranteeTitle");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTitleBetween(String value1, String value2) {
            addCriterion("Guarantee_title between", value1, value2, "guaranteeTitle");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTitleNotBetween(String value1, String value2) {
            addCriterion("Guarantee_title not between", value1, value2, "guaranteeTitle");
            return (Criteria) this;
        }

        public Criteria andFeedbackTitleIsNull() {
            addCriterion("Feedback_title is null");
            return (Criteria) this;
        }

        public Criteria andFeedbackTitleIsNotNull() {
            addCriterion("Feedback_title is not null");
            return (Criteria) this;
        }

        public Criteria andFeedbackTitleEqualTo(String value) {
            addCriterion("Feedback_title =", value, "feedbackTitle");
            return (Criteria) this;
        }

        public Criteria andFeedbackTitleNotEqualTo(String value) {
            addCriterion("Feedback_title <>", value, "feedbackTitle");
            return (Criteria) this;
        }

        public Criteria andFeedbackTitleGreaterThan(String value) {
            addCriterion("Feedback_title >", value, "feedbackTitle");
            return (Criteria) this;
        }

        public Criteria andFeedbackTitleGreaterThanOrEqualTo(String value) {
            addCriterion("Feedback_title >=", value, "feedbackTitle");
            return (Criteria) this;
        }

        public Criteria andFeedbackTitleLessThan(String value) {
            addCriterion("Feedback_title <", value, "feedbackTitle");
            return (Criteria) this;
        }

        public Criteria andFeedbackTitleLessThanOrEqualTo(String value) {
            addCriterion("Feedback_title <=", value, "feedbackTitle");
            return (Criteria) this;
        }

        public Criteria andFeedbackTitleLike(String value) {
            addCriterion("Feedback_title like", value, "feedbackTitle");
            return (Criteria) this;
        }

        public Criteria andFeedbackTitleNotLike(String value) {
            addCriterion("Feedback_title not like", value, "feedbackTitle");
            return (Criteria) this;
        }

        public Criteria andFeedbackTitleIn(List<String> values) {
            addCriterion("Feedback_title in", values, "feedbackTitle");
            return (Criteria) this;
        }

        public Criteria andFeedbackTitleNotIn(List<String> values) {
            addCriterion("Feedback_title not in", values, "feedbackTitle");
            return (Criteria) this;
        }

        public Criteria andFeedbackTitleBetween(String value1, String value2) {
            addCriterion("Feedback_title between", value1, value2, "feedbackTitle");
            return (Criteria) this;
        }

        public Criteria andFeedbackTitleNotBetween(String value1, String value2) {
            addCriterion("Feedback_title not between", value1, value2, "feedbackTitle");
            return (Criteria) this;
        }

        public Criteria andCheckFlagIsNull() {
            addCriterion("check_flag is null");
            return (Criteria) this;
        }

        public Criteria andCheckFlagIsNotNull() {
            addCriterion("check_flag is not null");
            return (Criteria) this;
        }

        public Criteria andCheckFlagEqualTo(String value) {
            addCriterion("check_flag =", value, "checkFlag");
            return (Criteria) this;
        }

        public Criteria andCheckFlagNotEqualTo(String value) {
            addCriterion("check_flag <>", value, "checkFlag");
            return (Criteria) this;
        }

        public Criteria andCheckFlagGreaterThan(String value) {
            addCriterion("check_flag >", value, "checkFlag");
            return (Criteria) this;
        }

        public Criteria andCheckFlagGreaterThanOrEqualTo(String value) {
            addCriterion("check_flag >=", value, "checkFlag");
            return (Criteria) this;
        }

        public Criteria andCheckFlagLessThan(String value) {
            addCriterion("check_flag <", value, "checkFlag");
            return (Criteria) this;
        }

        public Criteria andCheckFlagLessThanOrEqualTo(String value) {
            addCriterion("check_flag <=", value, "checkFlag");
            return (Criteria) this;
        }

        public Criteria andCheckFlagLike(String value) {
            addCriterion("check_flag like", value, "checkFlag");
            return (Criteria) this;
        }

        public Criteria andCheckFlagNotLike(String value) {
            addCriterion("check_flag not like", value, "checkFlag");
            return (Criteria) this;
        }

        public Criteria andCheckFlagIn(List<String> values) {
            addCriterion("check_flag in", values, "checkFlag");
            return (Criteria) this;
        }

        public Criteria andCheckFlagNotIn(List<String> values) {
            addCriterion("check_flag not in", values, "checkFlag");
            return (Criteria) this;
        }

        public Criteria andCheckFlagBetween(String value1, String value2) {
            addCriterion("check_flag between", value1, value2, "checkFlag");
            return (Criteria) this;
        }

        public Criteria andCheckFlagNotBetween(String value1, String value2) {
            addCriterion("check_flag not between", value1, value2, "checkFlag");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table trading_description_details
     *
     * @mbggenerated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}