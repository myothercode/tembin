package com.base.database.trading.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TradingSetComplementExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    public TradingSetComplementExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_set_complement
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
     * This method corresponds to the database table trading_set_complement
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
     * This method corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_set_complement
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
     * This class corresponds to the database table trading_set_complement
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

        public Criteria andComplementTypeIsNull() {
            addCriterion("complement_type is null");
            return (Criteria) this;
        }

        public Criteria andComplementTypeIsNotNull() {
            addCriterion("complement_type is not null");
            return (Criteria) this;
        }

        public Criteria andComplementTypeEqualTo(String value) {
            addCriterion("complement_type =", value, "complementType");
            return (Criteria) this;
        }

        public Criteria andComplementTypeNotEqualTo(String value) {
            addCriterion("complement_type <>", value, "complementType");
            return (Criteria) this;
        }

        public Criteria andComplementTypeGreaterThan(String value) {
            addCriterion("complement_type >", value, "complementType");
            return (Criteria) this;
        }

        public Criteria andComplementTypeGreaterThanOrEqualTo(String value) {
            addCriterion("complement_type >=", value, "complementType");
            return (Criteria) this;
        }

        public Criteria andComplementTypeLessThan(String value) {
            addCriterion("complement_type <", value, "complementType");
            return (Criteria) this;
        }

        public Criteria andComplementTypeLessThanOrEqualTo(String value) {
            addCriterion("complement_type <=", value, "complementType");
            return (Criteria) this;
        }

        public Criteria andComplementTypeLike(String value) {
            addCriterion("complement_type like", value, "complementType");
            return (Criteria) this;
        }

        public Criteria andComplementTypeNotLike(String value) {
            addCriterion("complement_type not like", value, "complementType");
            return (Criteria) this;
        }

        public Criteria andComplementTypeIn(List<String> values) {
            addCriterion("complement_type in", values, "complementType");
            return (Criteria) this;
        }

        public Criteria andComplementTypeNotIn(List<String> values) {
            addCriterion("complement_type not in", values, "complementType");
            return (Criteria) this;
        }

        public Criteria andComplementTypeBetween(String value1, String value2) {
            addCriterion("complement_type between", value1, value2, "complementType");
            return (Criteria) this;
        }

        public Criteria andComplementTypeNotBetween(String value1, String value2) {
            addCriterion("complement_type not between", value1, value2, "complementType");
            return (Criteria) this;
        }

        public Criteria andEbayIdIsNull() {
            addCriterion("ebay_id is null");
            return (Criteria) this;
        }

        public Criteria andEbayIdIsNotNull() {
            addCriterion("ebay_id is not null");
            return (Criteria) this;
        }

        public Criteria andEbayIdEqualTo(Long value) {
            addCriterion("ebay_id =", value, "ebayId");
            return (Criteria) this;
        }

        public Criteria andEbayIdNotEqualTo(Long value) {
            addCriterion("ebay_id <>", value, "ebayId");
            return (Criteria) this;
        }

        public Criteria andEbayIdGreaterThan(Long value) {
            addCriterion("ebay_id >", value, "ebayId");
            return (Criteria) this;
        }

        public Criteria andEbayIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ebay_id >=", value, "ebayId");
            return (Criteria) this;
        }

        public Criteria andEbayIdLessThan(Long value) {
            addCriterion("ebay_id <", value, "ebayId");
            return (Criteria) this;
        }

        public Criteria andEbayIdLessThanOrEqualTo(Long value) {
            addCriterion("ebay_id <=", value, "ebayId");
            return (Criteria) this;
        }

        public Criteria andEbayIdIn(List<Long> values) {
            addCriterion("ebay_id in", values, "ebayId");
            return (Criteria) this;
        }

        public Criteria andEbayIdNotIn(List<Long> values) {
            addCriterion("ebay_id not in", values, "ebayId");
            return (Criteria) this;
        }

        public Criteria andEbayIdBetween(Long value1, Long value2) {
            addCriterion("ebay_id between", value1, value2, "ebayId");
            return (Criteria) this;
        }

        public Criteria andEbayIdNotBetween(Long value1, Long value2) {
            addCriterion("ebay_id not between", value1, value2, "ebayId");
            return (Criteria) this;
        }

        public Criteria andEbayAccountIsNull() {
            addCriterion("ebay_account is null");
            return (Criteria) this;
        }

        public Criteria andEbayAccountIsNotNull() {
            addCriterion("ebay_account is not null");
            return (Criteria) this;
        }

        public Criteria andEbayAccountEqualTo(String value) {
            addCriterion("ebay_account =", value, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountNotEqualTo(String value) {
            addCriterion("ebay_account <>", value, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountGreaterThan(String value) {
            addCriterion("ebay_account >", value, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountGreaterThanOrEqualTo(String value) {
            addCriterion("ebay_account >=", value, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountLessThan(String value) {
            addCriterion("ebay_account <", value, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountLessThanOrEqualTo(String value) {
            addCriterion("ebay_account <=", value, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountLike(String value) {
            addCriterion("ebay_account like", value, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountNotLike(String value) {
            addCriterion("ebay_account not like", value, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountIn(List<String> values) {
            addCriterion("ebay_account in", values, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountNotIn(List<String> values) {
            addCriterion("ebay_account not in", values, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountBetween(String value1, String value2) {
            addCriterion("ebay_account between", value1, value2, "ebayAccount");
            return (Criteria) this;
        }

        public Criteria andEbayAccountNotBetween(String value1, String value2) {
            addCriterion("ebay_account not between", value1, value2, "ebayAccount");
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

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(Long value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(Long value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(Long value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(Long value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(Long value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(Long value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<Long> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<Long> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(Long value1, Long value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(Long value1, Long value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("update_date not between", value1, value2, "updateDate");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table trading_set_complement
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
     * This class corresponds to the database table trading_set_complement
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

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value) {
            super();
            this.condition = condition;
            this.value = value;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.betweenValue = true;
        }
    }
}