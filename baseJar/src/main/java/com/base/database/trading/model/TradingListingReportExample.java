package com.base.database.trading.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TradingListingReportExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    public TradingListingReportExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_report
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
     * This method corresponds to the database table trading_listing_report
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
     * This method corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_report
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
     * This class corresponds to the database table trading_listing_report
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

        public Criteria andDatatypeIsNull() {
            addCriterion("datatype is null");
            return (Criteria) this;
        }

        public Criteria andDatatypeIsNotNull() {
            addCriterion("datatype is not null");
            return (Criteria) this;
        }

        public Criteria andDatatypeEqualTo(String value) {
            addCriterion("datatype =", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeNotEqualTo(String value) {
            addCriterion("datatype <>", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeGreaterThan(String value) {
            addCriterion("datatype >", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeGreaterThanOrEqualTo(String value) {
            addCriterion("datatype >=", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeLessThan(String value) {
            addCriterion("datatype <", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeLessThanOrEqualTo(String value) {
            addCriterion("datatype <=", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeLike(String value) {
            addCriterion("datatype like", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeNotLike(String value) {
            addCriterion("datatype not like", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeIn(List<String> values) {
            addCriterion("datatype in", values, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeNotIn(List<String> values) {
            addCriterion("datatype not in", values, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeBetween(String value1, String value2) {
            addCriterion("datatype between", value1, value2, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeNotBetween(String value1, String value2) {
            addCriterion("datatype not between", value1, value2, "datatype");
            return (Criteria) this;
        }

        public Criteria andDayIsNull() {
            addCriterion("day is null");
            return (Criteria) this;
        }

        public Criteria andDayIsNotNull() {
            addCriterion("day is not null");
            return (Criteria) this;
        }

        public Criteria andDayEqualTo(String value) {
            addCriterion("day =", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayNotEqualTo(String value) {
            addCriterion("day <>", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayGreaterThan(String value) {
            addCriterion("day >", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayGreaterThanOrEqualTo(String value) {
            addCriterion("day >=", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayLessThan(String value) {
            addCriterion("day <", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayLessThanOrEqualTo(String value) {
            addCriterion("day <=", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayLike(String value) {
            addCriterion("day like", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayNotLike(String value) {
            addCriterion("day not like", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayIn(List<String> values) {
            addCriterion("day in", values, "day");
            return (Criteria) this;
        }

        public Criteria andDayNotIn(List<String> values) {
            addCriterion("day not in", values, "day");
            return (Criteria) this;
        }

        public Criteria andDayBetween(String value1, String value2) {
            addCriterion("day between", value1, value2, "day");
            return (Criteria) this;
        }

        public Criteria andDayNotBetween(String value1, String value2) {
            addCriterion("day not between", value1, value2, "day");
            return (Criteria) this;
        }

        public Criteria andYesterdayIsNull() {
            addCriterion("yesterday is null");
            return (Criteria) this;
        }

        public Criteria andYesterdayIsNotNull() {
            addCriterion("yesterday is not null");
            return (Criteria) this;
        }

        public Criteria andYesterdayEqualTo(String value) {
            addCriterion("yesterday =", value, "yesterday");
            return (Criteria) this;
        }

        public Criteria andYesterdayNotEqualTo(String value) {
            addCriterion("yesterday <>", value, "yesterday");
            return (Criteria) this;
        }

        public Criteria andYesterdayGreaterThan(String value) {
            addCriterion("yesterday >", value, "yesterday");
            return (Criteria) this;
        }

        public Criteria andYesterdayGreaterThanOrEqualTo(String value) {
            addCriterion("yesterday >=", value, "yesterday");
            return (Criteria) this;
        }

        public Criteria andYesterdayLessThan(String value) {
            addCriterion("yesterday <", value, "yesterday");
            return (Criteria) this;
        }

        public Criteria andYesterdayLessThanOrEqualTo(String value) {
            addCriterion("yesterday <=", value, "yesterday");
            return (Criteria) this;
        }

        public Criteria andYesterdayLike(String value) {
            addCriterion("yesterday like", value, "yesterday");
            return (Criteria) this;
        }

        public Criteria andYesterdayNotLike(String value) {
            addCriterion("yesterday not like", value, "yesterday");
            return (Criteria) this;
        }

        public Criteria andYesterdayIn(List<String> values) {
            addCriterion("yesterday in", values, "yesterday");
            return (Criteria) this;
        }

        public Criteria andYesterdayNotIn(List<String> values) {
            addCriterion("yesterday not in", values, "yesterday");
            return (Criteria) this;
        }

        public Criteria andYesterdayBetween(String value1, String value2) {
            addCriterion("yesterday between", value1, value2, "yesterday");
            return (Criteria) this;
        }

        public Criteria andYesterdayNotBetween(String value1, String value2) {
            addCriterion("yesterday not between", value1, value2, "yesterday");
            return (Criteria) this;
        }

        public Criteria andWeekIsNull() {
            addCriterion("week is null");
            return (Criteria) this;
        }

        public Criteria andWeekIsNotNull() {
            addCriterion("week is not null");
            return (Criteria) this;
        }

        public Criteria andWeekEqualTo(String value) {
            addCriterion("week =", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotEqualTo(String value) {
            addCriterion("week <>", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekGreaterThan(String value) {
            addCriterion("week >", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekGreaterThanOrEqualTo(String value) {
            addCriterion("week >=", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekLessThan(String value) {
            addCriterion("week <", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekLessThanOrEqualTo(String value) {
            addCriterion("week <=", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekLike(String value) {
            addCriterion("week like", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotLike(String value) {
            addCriterion("week not like", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekIn(List<String> values) {
            addCriterion("week in", values, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotIn(List<String> values) {
            addCriterion("week not in", values, "week");
            return (Criteria) this;
        }

        public Criteria andWeekBetween(String value1, String value2) {
            addCriterion("week between", value1, value2, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotBetween(String value1, String value2) {
            addCriterion("week not between", value1, value2, "week");
            return (Criteria) this;
        }

        public Criteria andThatweekIsNull() {
            addCriterion("thatweek is null");
            return (Criteria) this;
        }

        public Criteria andThatweekIsNotNull() {
            addCriterion("thatweek is not null");
            return (Criteria) this;
        }

        public Criteria andThatweekEqualTo(String value) {
            addCriterion("thatweek =", value, "thatweek");
            return (Criteria) this;
        }

        public Criteria andThatweekNotEqualTo(String value) {
            addCriterion("thatweek <>", value, "thatweek");
            return (Criteria) this;
        }

        public Criteria andThatweekGreaterThan(String value) {
            addCriterion("thatweek >", value, "thatweek");
            return (Criteria) this;
        }

        public Criteria andThatweekGreaterThanOrEqualTo(String value) {
            addCriterion("thatweek >=", value, "thatweek");
            return (Criteria) this;
        }

        public Criteria andThatweekLessThan(String value) {
            addCriterion("thatweek <", value, "thatweek");
            return (Criteria) this;
        }

        public Criteria andThatweekLessThanOrEqualTo(String value) {
            addCriterion("thatweek <=", value, "thatweek");
            return (Criteria) this;
        }

        public Criteria andThatweekLike(String value) {
            addCriterion("thatweek like", value, "thatweek");
            return (Criteria) this;
        }

        public Criteria andThatweekNotLike(String value) {
            addCriterion("thatweek not like", value, "thatweek");
            return (Criteria) this;
        }

        public Criteria andThatweekIn(List<String> values) {
            addCriterion("thatweek in", values, "thatweek");
            return (Criteria) this;
        }

        public Criteria andThatweekNotIn(List<String> values) {
            addCriterion("thatweek not in", values, "thatweek");
            return (Criteria) this;
        }

        public Criteria andThatweekBetween(String value1, String value2) {
            addCriterion("thatweek between", value1, value2, "thatweek");
            return (Criteria) this;
        }

        public Criteria andThatweekNotBetween(String value1, String value2) {
            addCriterion("thatweek not between", value1, value2, "thatweek");
            return (Criteria) this;
        }

        public Criteria andMonthIsNull() {
            addCriterion("month is null");
            return (Criteria) this;
        }

        public Criteria andMonthIsNotNull() {
            addCriterion("month is not null");
            return (Criteria) this;
        }

        public Criteria andMonthEqualTo(String value) {
            addCriterion("month =", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotEqualTo(String value) {
            addCriterion("month <>", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthGreaterThan(String value) {
            addCriterion("month >", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthGreaterThanOrEqualTo(String value) {
            addCriterion("month >=", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthLessThan(String value) {
            addCriterion("month <", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthLessThanOrEqualTo(String value) {
            addCriterion("month <=", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthLike(String value) {
            addCriterion("month like", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotLike(String value) {
            addCriterion("month not like", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthIn(List<String> values) {
            addCriterion("month in", values, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotIn(List<String> values) {
            addCriterion("month not in", values, "month");
            return (Criteria) this;
        }

        public Criteria andMonthBetween(String value1, String value2) {
            addCriterion("month between", value1, value2, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotBetween(String value1, String value2) {
            addCriterion("month not between", value1, value2, "month");
            return (Criteria) this;
        }

        public Criteria andThatmonthIsNull() {
            addCriterion("thatmonth is null");
            return (Criteria) this;
        }

        public Criteria andThatmonthIsNotNull() {
            addCriterion("thatmonth is not null");
            return (Criteria) this;
        }

        public Criteria andThatmonthEqualTo(String value) {
            addCriterion("thatmonth =", value, "thatmonth");
            return (Criteria) this;
        }

        public Criteria andThatmonthNotEqualTo(String value) {
            addCriterion("thatmonth <>", value, "thatmonth");
            return (Criteria) this;
        }

        public Criteria andThatmonthGreaterThan(String value) {
            addCriterion("thatmonth >", value, "thatmonth");
            return (Criteria) this;
        }

        public Criteria andThatmonthGreaterThanOrEqualTo(String value) {
            addCriterion("thatmonth >=", value, "thatmonth");
            return (Criteria) this;
        }

        public Criteria andThatmonthLessThan(String value) {
            addCriterion("thatmonth <", value, "thatmonth");
            return (Criteria) this;
        }

        public Criteria andThatmonthLessThanOrEqualTo(String value) {
            addCriterion("thatmonth <=", value, "thatmonth");
            return (Criteria) this;
        }

        public Criteria andThatmonthLike(String value) {
            addCriterion("thatmonth like", value, "thatmonth");
            return (Criteria) this;
        }

        public Criteria andThatmonthNotLike(String value) {
            addCriterion("thatmonth not like", value, "thatmonth");
            return (Criteria) this;
        }

        public Criteria andThatmonthIn(List<String> values) {
            addCriterion("thatmonth in", values, "thatmonth");
            return (Criteria) this;
        }

        public Criteria andThatmonthNotIn(List<String> values) {
            addCriterion("thatmonth not in", values, "thatmonth");
            return (Criteria) this;
        }

        public Criteria andThatmonthBetween(String value1, String value2) {
            addCriterion("thatmonth between", value1, value2, "thatmonth");
            return (Criteria) this;
        }

        public Criteria andThatmonthNotBetween(String value1, String value2) {
            addCriterion("thatmonth not between", value1, value2, "thatmonth");
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
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table trading_listing_report
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
     * This class corresponds to the database table trading_listing_report
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