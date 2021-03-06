package com.base.database.trading.model;

import java.util.ArrayList;
import java.util.List;

public class UsercontrollerDevAccountExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table usercontroller_dev_account
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table usercontroller_dev_account
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table usercontroller_dev_account
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_dev_account
     *
     * @mbggenerated
     */
    public UsercontrollerDevAccountExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_dev_account
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_dev_account
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_dev_account
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_dev_account
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_dev_account
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_dev_account
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_dev_account
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
     * This method corresponds to the database table usercontroller_dev_account
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
     * This method corresponds to the database table usercontroller_dev_account
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_dev_account
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
     * This class corresponds to the database table usercontroller_dev_account
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

        public Criteria andDevUserIsNull() {
            addCriterion("dev_user is null");
            return (Criteria) this;
        }

        public Criteria andDevUserIsNotNull() {
            addCriterion("dev_user is not null");
            return (Criteria) this;
        }

        public Criteria andDevUserEqualTo(String value) {
            addCriterion("dev_user =", value, "devUser");
            return (Criteria) this;
        }

        public Criteria andDevUserNotEqualTo(String value) {
            addCriterion("dev_user <>", value, "devUser");
            return (Criteria) this;
        }

        public Criteria andDevUserGreaterThan(String value) {
            addCriterion("dev_user >", value, "devUser");
            return (Criteria) this;
        }

        public Criteria andDevUserGreaterThanOrEqualTo(String value) {
            addCriterion("dev_user >=", value, "devUser");
            return (Criteria) this;
        }

        public Criteria andDevUserLessThan(String value) {
            addCriterion("dev_user <", value, "devUser");
            return (Criteria) this;
        }

        public Criteria andDevUserLessThanOrEqualTo(String value) {
            addCriterion("dev_user <=", value, "devUser");
            return (Criteria) this;
        }

        public Criteria andDevUserLike(String value) {
            addCriterion("dev_user like", value, "devUser");
            return (Criteria) this;
        }

        public Criteria andDevUserNotLike(String value) {
            addCriterion("dev_user not like", value, "devUser");
            return (Criteria) this;
        }

        public Criteria andDevUserIn(List<String> values) {
            addCriterion("dev_user in", values, "devUser");
            return (Criteria) this;
        }

        public Criteria andDevUserNotIn(List<String> values) {
            addCriterion("dev_user not in", values, "devUser");
            return (Criteria) this;
        }

        public Criteria andDevUserBetween(String value1, String value2) {
            addCriterion("dev_user between", value1, value2, "devUser");
            return (Criteria) this;
        }

        public Criteria andDevUserNotBetween(String value1, String value2) {
            addCriterion("dev_user not between", value1, value2, "devUser");
            return (Criteria) this;
        }

        public Criteria andApiDevNameIsNull() {
            addCriterion("API_DEV_NAME is null");
            return (Criteria) this;
        }

        public Criteria andApiDevNameIsNotNull() {
            addCriterion("API_DEV_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andApiDevNameEqualTo(String value) {
            addCriterion("API_DEV_NAME =", value, "apiDevName");
            return (Criteria) this;
        }

        public Criteria andApiDevNameNotEqualTo(String value) {
            addCriterion("API_DEV_NAME <>", value, "apiDevName");
            return (Criteria) this;
        }

        public Criteria andApiDevNameGreaterThan(String value) {
            addCriterion("API_DEV_NAME >", value, "apiDevName");
            return (Criteria) this;
        }

        public Criteria andApiDevNameGreaterThanOrEqualTo(String value) {
            addCriterion("API_DEV_NAME >=", value, "apiDevName");
            return (Criteria) this;
        }

        public Criteria andApiDevNameLessThan(String value) {
            addCriterion("API_DEV_NAME <", value, "apiDevName");
            return (Criteria) this;
        }

        public Criteria andApiDevNameLessThanOrEqualTo(String value) {
            addCriterion("API_DEV_NAME <=", value, "apiDevName");
            return (Criteria) this;
        }

        public Criteria andApiDevNameLike(String value) {
            addCriterion("API_DEV_NAME like", value, "apiDevName");
            return (Criteria) this;
        }

        public Criteria andApiDevNameNotLike(String value) {
            addCriterion("API_DEV_NAME not like", value, "apiDevName");
            return (Criteria) this;
        }

        public Criteria andApiDevNameIn(List<String> values) {
            addCriterion("API_DEV_NAME in", values, "apiDevName");
            return (Criteria) this;
        }

        public Criteria andApiDevNameNotIn(List<String> values) {
            addCriterion("API_DEV_NAME not in", values, "apiDevName");
            return (Criteria) this;
        }

        public Criteria andApiDevNameBetween(String value1, String value2) {
            addCriterion("API_DEV_NAME between", value1, value2, "apiDevName");
            return (Criteria) this;
        }

        public Criteria andApiDevNameNotBetween(String value1, String value2) {
            addCriterion("API_DEV_NAME not between", value1, value2, "apiDevName");
            return (Criteria) this;
        }

        public Criteria andApiAppNameIsNull() {
            addCriterion("API_APP_NAME is null");
            return (Criteria) this;
        }

        public Criteria andApiAppNameIsNotNull() {
            addCriterion("API_APP_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andApiAppNameEqualTo(String value) {
            addCriterion("API_APP_NAME =", value, "apiAppName");
            return (Criteria) this;
        }

        public Criteria andApiAppNameNotEqualTo(String value) {
            addCriterion("API_APP_NAME <>", value, "apiAppName");
            return (Criteria) this;
        }

        public Criteria andApiAppNameGreaterThan(String value) {
            addCriterion("API_APP_NAME >", value, "apiAppName");
            return (Criteria) this;
        }

        public Criteria andApiAppNameGreaterThanOrEqualTo(String value) {
            addCriterion("API_APP_NAME >=", value, "apiAppName");
            return (Criteria) this;
        }

        public Criteria andApiAppNameLessThan(String value) {
            addCriterion("API_APP_NAME <", value, "apiAppName");
            return (Criteria) this;
        }

        public Criteria andApiAppNameLessThanOrEqualTo(String value) {
            addCriterion("API_APP_NAME <=", value, "apiAppName");
            return (Criteria) this;
        }

        public Criteria andApiAppNameLike(String value) {
            addCriterion("API_APP_NAME like", value, "apiAppName");
            return (Criteria) this;
        }

        public Criteria andApiAppNameNotLike(String value) {
            addCriterion("API_APP_NAME not like", value, "apiAppName");
            return (Criteria) this;
        }

        public Criteria andApiAppNameIn(List<String> values) {
            addCriterion("API_APP_NAME in", values, "apiAppName");
            return (Criteria) this;
        }

        public Criteria andApiAppNameNotIn(List<String> values) {
            addCriterion("API_APP_NAME not in", values, "apiAppName");
            return (Criteria) this;
        }

        public Criteria andApiAppNameBetween(String value1, String value2) {
            addCriterion("API_APP_NAME between", value1, value2, "apiAppName");
            return (Criteria) this;
        }

        public Criteria andApiAppNameNotBetween(String value1, String value2) {
            addCriterion("API_APP_NAME not between", value1, value2, "apiAppName");
            return (Criteria) this;
        }

        public Criteria andApiCertNameIsNull() {
            addCriterion("API_CERT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andApiCertNameIsNotNull() {
            addCriterion("API_CERT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andApiCertNameEqualTo(String value) {
            addCriterion("API_CERT_NAME =", value, "apiCertName");
            return (Criteria) this;
        }

        public Criteria andApiCertNameNotEqualTo(String value) {
            addCriterion("API_CERT_NAME <>", value, "apiCertName");
            return (Criteria) this;
        }

        public Criteria andApiCertNameGreaterThan(String value) {
            addCriterion("API_CERT_NAME >", value, "apiCertName");
            return (Criteria) this;
        }

        public Criteria andApiCertNameGreaterThanOrEqualTo(String value) {
            addCriterion("API_CERT_NAME >=", value, "apiCertName");
            return (Criteria) this;
        }

        public Criteria andApiCertNameLessThan(String value) {
            addCriterion("API_CERT_NAME <", value, "apiCertName");
            return (Criteria) this;
        }

        public Criteria andApiCertNameLessThanOrEqualTo(String value) {
            addCriterion("API_CERT_NAME <=", value, "apiCertName");
            return (Criteria) this;
        }

        public Criteria andApiCertNameLike(String value) {
            addCriterion("API_CERT_NAME like", value, "apiCertName");
            return (Criteria) this;
        }

        public Criteria andApiCertNameNotLike(String value) {
            addCriterion("API_CERT_NAME not like", value, "apiCertName");
            return (Criteria) this;
        }

        public Criteria andApiCertNameIn(List<String> values) {
            addCriterion("API_CERT_NAME in", values, "apiCertName");
            return (Criteria) this;
        }

        public Criteria andApiCertNameNotIn(List<String> values) {
            addCriterion("API_CERT_NAME not in", values, "apiCertName");
            return (Criteria) this;
        }

        public Criteria andApiCertNameBetween(String value1, String value2) {
            addCriterion("API_CERT_NAME between", value1, value2, "apiCertName");
            return (Criteria) this;
        }

        public Criteria andApiCertNameNotBetween(String value1, String value2) {
            addCriterion("API_CERT_NAME not between", value1, value2, "apiCertName");
            return (Criteria) this;
        }

        public Criteria andApiCompatibilityLevelIsNull() {
            addCriterion("API_COMPATIBILITY_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andApiCompatibilityLevelIsNotNull() {
            addCriterion("API_COMPATIBILITY_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andApiCompatibilityLevelEqualTo(String value) {
            addCriterion("API_COMPATIBILITY_LEVEL =", value, "apiCompatibilityLevel");
            return (Criteria) this;
        }

        public Criteria andApiCompatibilityLevelNotEqualTo(String value) {
            addCriterion("API_COMPATIBILITY_LEVEL <>", value, "apiCompatibilityLevel");
            return (Criteria) this;
        }

        public Criteria andApiCompatibilityLevelGreaterThan(String value) {
            addCriterion("API_COMPATIBILITY_LEVEL >", value, "apiCompatibilityLevel");
            return (Criteria) this;
        }

        public Criteria andApiCompatibilityLevelGreaterThanOrEqualTo(String value) {
            addCriterion("API_COMPATIBILITY_LEVEL >=", value, "apiCompatibilityLevel");
            return (Criteria) this;
        }

        public Criteria andApiCompatibilityLevelLessThan(String value) {
            addCriterion("API_COMPATIBILITY_LEVEL <", value, "apiCompatibilityLevel");
            return (Criteria) this;
        }

        public Criteria andApiCompatibilityLevelLessThanOrEqualTo(String value) {
            addCriterion("API_COMPATIBILITY_LEVEL <=", value, "apiCompatibilityLevel");
            return (Criteria) this;
        }

        public Criteria andApiCompatibilityLevelLike(String value) {
            addCriterion("API_COMPATIBILITY_LEVEL like", value, "apiCompatibilityLevel");
            return (Criteria) this;
        }

        public Criteria andApiCompatibilityLevelNotLike(String value) {
            addCriterion("API_COMPATIBILITY_LEVEL not like", value, "apiCompatibilityLevel");
            return (Criteria) this;
        }

        public Criteria andApiCompatibilityLevelIn(List<String> values) {
            addCriterion("API_COMPATIBILITY_LEVEL in", values, "apiCompatibilityLevel");
            return (Criteria) this;
        }

        public Criteria andApiCompatibilityLevelNotIn(List<String> values) {
            addCriterion("API_COMPATIBILITY_LEVEL not in", values, "apiCompatibilityLevel");
            return (Criteria) this;
        }

        public Criteria andApiCompatibilityLevelBetween(String value1, String value2) {
            addCriterion("API_COMPATIBILITY_LEVEL between", value1, value2, "apiCompatibilityLevel");
            return (Criteria) this;
        }

        public Criteria andApiCompatibilityLevelNotBetween(String value1, String value2) {
            addCriterion("API_COMPATIBILITY_LEVEL not between", value1, value2, "apiCompatibilityLevel");
            return (Criteria) this;
        }

        public Criteria andRunnameIsNull() {
            addCriterion("runName is null");
            return (Criteria) this;
        }

        public Criteria andRunnameIsNotNull() {
            addCriterion("runName is not null");
            return (Criteria) this;
        }

        public Criteria andRunnameEqualTo(String value) {
            addCriterion("runName =", value, "runname");
            return (Criteria) this;
        }

        public Criteria andRunnameNotEqualTo(String value) {
            addCriterion("runName <>", value, "runname");
            return (Criteria) this;
        }

        public Criteria andRunnameGreaterThan(String value) {
            addCriterion("runName >", value, "runname");
            return (Criteria) this;
        }

        public Criteria andRunnameGreaterThanOrEqualTo(String value) {
            addCriterion("runName >=", value, "runname");
            return (Criteria) this;
        }

        public Criteria andRunnameLessThan(String value) {
            addCriterion("runName <", value, "runname");
            return (Criteria) this;
        }

        public Criteria andRunnameLessThanOrEqualTo(String value) {
            addCriterion("runName <=", value, "runname");
            return (Criteria) this;
        }

        public Criteria andRunnameLike(String value) {
            addCriterion("runName like", value, "runname");
            return (Criteria) this;
        }

        public Criteria andRunnameNotLike(String value) {
            addCriterion("runName not like", value, "runname");
            return (Criteria) this;
        }

        public Criteria andRunnameIn(List<String> values) {
            addCriterion("runName in", values, "runname");
            return (Criteria) this;
        }

        public Criteria andRunnameNotIn(List<String> values) {
            addCriterion("runName not in", values, "runname");
            return (Criteria) this;
        }

        public Criteria andRunnameBetween(String value1, String value2) {
            addCriterion("runName between", value1, value2, "runname");
            return (Criteria) this;
        }

        public Criteria andRunnameNotBetween(String value1, String value2) {
            addCriterion("runName not between", value1, value2, "runname");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table usercontroller_dev_account
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
     * This class corresponds to the database table usercontroller_dev_account
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