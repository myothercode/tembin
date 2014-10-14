package com.base.database.userinfo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SystemLogExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table system_log
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table system_log
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table system_log
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_log
     *
     * @mbggenerated
     */
    public SystemLogExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_log
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_log
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_log
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_log
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_log
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_log
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_log
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
     * This method corresponds to the database table system_log
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
     * This method corresponds to the database table system_log
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_log
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
     * This class corresponds to the database table system_log
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

        public Criteria andEventnameIsNull() {
            addCriterion("eventName is null");
            return (Criteria) this;
        }

        public Criteria andEventnameIsNotNull() {
            addCriterion("eventName is not null");
            return (Criteria) this;
        }

        public Criteria andEventnameEqualTo(String value) {
            addCriterion("eventName =", value, "eventname");
            return (Criteria) this;
        }

        public Criteria andEventnameNotEqualTo(String value) {
            addCriterion("eventName <>", value, "eventname");
            return (Criteria) this;
        }

        public Criteria andEventnameGreaterThan(String value) {
            addCriterion("eventName >", value, "eventname");
            return (Criteria) this;
        }

        public Criteria andEventnameGreaterThanOrEqualTo(String value) {
            addCriterion("eventName >=", value, "eventname");
            return (Criteria) this;
        }

        public Criteria andEventnameLessThan(String value) {
            addCriterion("eventName <", value, "eventname");
            return (Criteria) this;
        }

        public Criteria andEventnameLessThanOrEqualTo(String value) {
            addCriterion("eventName <=", value, "eventname");
            return (Criteria) this;
        }

        public Criteria andEventnameLike(String value) {
            addCriterion("eventName like", value, "eventname");
            return (Criteria) this;
        }

        public Criteria andEventnameNotLike(String value) {
            addCriterion("eventName not like", value, "eventname");
            return (Criteria) this;
        }

        public Criteria andEventnameIn(List<String> values) {
            addCriterion("eventName in", values, "eventname");
            return (Criteria) this;
        }

        public Criteria andEventnameNotIn(List<String> values) {
            addCriterion("eventName not in", values, "eventname");
            return (Criteria) this;
        }

        public Criteria andEventnameBetween(String value1, String value2) {
            addCriterion("eventName between", value1, value2, "eventname");
            return (Criteria) this;
        }

        public Criteria andEventnameNotBetween(String value1, String value2) {
            addCriterion("eventName not between", value1, value2, "eventname");
            return (Criteria) this;
        }

        public Criteria andEventdescIsNull() {
            addCriterion("eventDesc is null");
            return (Criteria) this;
        }

        public Criteria andEventdescIsNotNull() {
            addCriterion("eventDesc is not null");
            return (Criteria) this;
        }

        public Criteria andEventdescEqualTo(String value) {
            addCriterion("eventDesc =", value, "eventdesc");
            return (Criteria) this;
        }

        public Criteria andEventdescNotEqualTo(String value) {
            addCriterion("eventDesc <>", value, "eventdesc");
            return (Criteria) this;
        }

        public Criteria andEventdescGreaterThan(String value) {
            addCriterion("eventDesc >", value, "eventdesc");
            return (Criteria) this;
        }

        public Criteria andEventdescGreaterThanOrEqualTo(String value) {
            addCriterion("eventDesc >=", value, "eventdesc");
            return (Criteria) this;
        }

        public Criteria andEventdescLessThan(String value) {
            addCriterion("eventDesc <", value, "eventdesc");
            return (Criteria) this;
        }

        public Criteria andEventdescLessThanOrEqualTo(String value) {
            addCriterion("eventDesc <=", value, "eventdesc");
            return (Criteria) this;
        }

        public Criteria andEventdescLike(String value) {
            addCriterion("eventDesc like", value, "eventdesc");
            return (Criteria) this;
        }

        public Criteria andEventdescNotLike(String value) {
            addCriterion("eventDesc not like", value, "eventdesc");
            return (Criteria) this;
        }

        public Criteria andEventdescIn(List<String> values) {
            addCriterion("eventDesc in", values, "eventdesc");
            return (Criteria) this;
        }

        public Criteria andEventdescNotIn(List<String> values) {
            addCriterion("eventDesc not in", values, "eventdesc");
            return (Criteria) this;
        }

        public Criteria andEventdescBetween(String value1, String value2) {
            addCriterion("eventDesc between", value1, value2, "eventdesc");
            return (Criteria) this;
        }

        public Criteria andEventdescNotBetween(String value1, String value2) {
            addCriterion("eventDesc not between", value1, value2, "eventdesc");
            return (Criteria) this;
        }

        public Criteria andOperuserIsNull() {
            addCriterion("operUser is null");
            return (Criteria) this;
        }

        public Criteria andOperuserIsNotNull() {
            addCriterion("operUser is not null");
            return (Criteria) this;
        }

        public Criteria andOperuserEqualTo(String value) {
            addCriterion("operUser =", value, "operuser");
            return (Criteria) this;
        }

        public Criteria andOperuserNotEqualTo(String value) {
            addCriterion("operUser <>", value, "operuser");
            return (Criteria) this;
        }

        public Criteria andOperuserGreaterThan(String value) {
            addCriterion("operUser >", value, "operuser");
            return (Criteria) this;
        }

        public Criteria andOperuserGreaterThanOrEqualTo(String value) {
            addCriterion("operUser >=", value, "operuser");
            return (Criteria) this;
        }

        public Criteria andOperuserLessThan(String value) {
            addCriterion("operUser <", value, "operuser");
            return (Criteria) this;
        }

        public Criteria andOperuserLessThanOrEqualTo(String value) {
            addCriterion("operUser <=", value, "operuser");
            return (Criteria) this;
        }

        public Criteria andOperuserLike(String value) {
            addCriterion("operUser like", value, "operuser");
            return (Criteria) this;
        }

        public Criteria andOperuserNotLike(String value) {
            addCriterion("operUser not like", value, "operuser");
            return (Criteria) this;
        }

        public Criteria andOperuserIn(List<String> values) {
            addCriterion("operUser in", values, "operuser");
            return (Criteria) this;
        }

        public Criteria andOperuserNotIn(List<String> values) {
            addCriterion("operUser not in", values, "operuser");
            return (Criteria) this;
        }

        public Criteria andOperuserBetween(String value1, String value2) {
            addCriterion("operUser between", value1, value2, "operuser");
            return (Criteria) this;
        }

        public Criteria andOperuserNotBetween(String value1, String value2) {
            addCriterion("operUser not between", value1, value2, "operuser");
            return (Criteria) this;
        }

        public Criteria andCreatedateIsNull() {
            addCriterion("createDate is null");
            return (Criteria) this;
        }

        public Criteria andCreatedateIsNotNull() {
            addCriterion("createDate is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedateEqualTo(Date value) {
            addCriterion("createDate =", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotEqualTo(Date value) {
            addCriterion("createDate <>", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThan(Date value) {
            addCriterion("createDate >", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThanOrEqualTo(Date value) {
            addCriterion("createDate >=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThan(Date value) {
            addCriterion("createDate <", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThanOrEqualTo(Date value) {
            addCriterion("createDate <=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateIn(List<Date> values) {
            addCriterion("createDate in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotIn(List<Date> values) {
            addCriterion("createDate not in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateBetween(Date value1, Date value2) {
            addCriterion("createDate between", value1, value2, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotBetween(Date value1, Date value2) {
            addCriterion("createDate not between", value1, value2, "createdate");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table system_log
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
     * This class corresponds to the database table system_log
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