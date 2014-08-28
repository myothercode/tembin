package com.base.database.trading.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TradingOrderReturnpolicyExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_order_returnpolicy
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_order_returnpolicy
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_order_returnpolicy
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_returnpolicy
     *
     * @mbggenerated
     */
    public TradingOrderReturnpolicyExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_returnpolicy
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_returnpolicy
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_returnpolicy
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_returnpolicy
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_returnpolicy
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_returnpolicy
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_returnpolicy
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
     * This method corresponds to the database table trading_order_returnpolicy
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
     * This method corresponds to the database table trading_order_returnpolicy
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_returnpolicy
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
     * This class corresponds to the database table trading_order_returnpolicy
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

        public Criteria andRefundoptionIsNull() {
            addCriterion("refundOption is null");
            return (Criteria) this;
        }

        public Criteria andRefundoptionIsNotNull() {
            addCriterion("refundOption is not null");
            return (Criteria) this;
        }

        public Criteria andRefundoptionEqualTo(String value) {
            addCriterion("refundOption =", value, "refundoption");
            return (Criteria) this;
        }

        public Criteria andRefundoptionNotEqualTo(String value) {
            addCriterion("refundOption <>", value, "refundoption");
            return (Criteria) this;
        }

        public Criteria andRefundoptionGreaterThan(String value) {
            addCriterion("refundOption >", value, "refundoption");
            return (Criteria) this;
        }

        public Criteria andRefundoptionGreaterThanOrEqualTo(String value) {
            addCriterion("refundOption >=", value, "refundoption");
            return (Criteria) this;
        }

        public Criteria andRefundoptionLessThan(String value) {
            addCriterion("refundOption <", value, "refundoption");
            return (Criteria) this;
        }

        public Criteria andRefundoptionLessThanOrEqualTo(String value) {
            addCriterion("refundOption <=", value, "refundoption");
            return (Criteria) this;
        }

        public Criteria andRefundoptionLike(String value) {
            addCriterion("refundOption like", value, "refundoption");
            return (Criteria) this;
        }

        public Criteria andRefundoptionNotLike(String value) {
            addCriterion("refundOption not like", value, "refundoption");
            return (Criteria) this;
        }

        public Criteria andRefundoptionIn(List<String> values) {
            addCriterion("refundOption in", values, "refundoption");
            return (Criteria) this;
        }

        public Criteria andRefundoptionNotIn(List<String> values) {
            addCriterion("refundOption not in", values, "refundoption");
            return (Criteria) this;
        }

        public Criteria andRefundoptionBetween(String value1, String value2) {
            addCriterion("refundOption between", value1, value2, "refundoption");
            return (Criteria) this;
        }

        public Criteria andRefundoptionNotBetween(String value1, String value2) {
            addCriterion("refundOption not between", value1, value2, "refundoption");
            return (Criteria) this;
        }

        public Criteria andRefundIsNull() {
            addCriterion("refund is null");
            return (Criteria) this;
        }

        public Criteria andRefundIsNotNull() {
            addCriterion("refund is not null");
            return (Criteria) this;
        }

        public Criteria andRefundEqualTo(String value) {
            addCriterion("refund =", value, "refund");
            return (Criteria) this;
        }

        public Criteria andRefundNotEqualTo(String value) {
            addCriterion("refund <>", value, "refund");
            return (Criteria) this;
        }

        public Criteria andRefundGreaterThan(String value) {
            addCriterion("refund >", value, "refund");
            return (Criteria) this;
        }

        public Criteria andRefundGreaterThanOrEqualTo(String value) {
            addCriterion("refund >=", value, "refund");
            return (Criteria) this;
        }

        public Criteria andRefundLessThan(String value) {
            addCriterion("refund <", value, "refund");
            return (Criteria) this;
        }

        public Criteria andRefundLessThanOrEqualTo(String value) {
            addCriterion("refund <=", value, "refund");
            return (Criteria) this;
        }

        public Criteria andRefundLike(String value) {
            addCriterion("refund like", value, "refund");
            return (Criteria) this;
        }

        public Criteria andRefundNotLike(String value) {
            addCriterion("refund not like", value, "refund");
            return (Criteria) this;
        }

        public Criteria andRefundIn(List<String> values) {
            addCriterion("refund in", values, "refund");
            return (Criteria) this;
        }

        public Criteria andRefundNotIn(List<String> values) {
            addCriterion("refund not in", values, "refund");
            return (Criteria) this;
        }

        public Criteria andRefundBetween(String value1, String value2) {
            addCriterion("refund between", value1, value2, "refund");
            return (Criteria) this;
        }

        public Criteria andRefundNotBetween(String value1, String value2) {
            addCriterion("refund not between", value1, value2, "refund");
            return (Criteria) this;
        }

        public Criteria andReturnswithinoptionIsNull() {
            addCriterion("returnsWithinOption is null");
            return (Criteria) this;
        }

        public Criteria andReturnswithinoptionIsNotNull() {
            addCriterion("returnsWithinOption is not null");
            return (Criteria) this;
        }

        public Criteria andReturnswithinoptionEqualTo(String value) {
            addCriterion("returnsWithinOption =", value, "returnswithinoption");
            return (Criteria) this;
        }

        public Criteria andReturnswithinoptionNotEqualTo(String value) {
            addCriterion("returnsWithinOption <>", value, "returnswithinoption");
            return (Criteria) this;
        }

        public Criteria andReturnswithinoptionGreaterThan(String value) {
            addCriterion("returnsWithinOption >", value, "returnswithinoption");
            return (Criteria) this;
        }

        public Criteria andReturnswithinoptionGreaterThanOrEqualTo(String value) {
            addCriterion("returnsWithinOption >=", value, "returnswithinoption");
            return (Criteria) this;
        }

        public Criteria andReturnswithinoptionLessThan(String value) {
            addCriterion("returnsWithinOption <", value, "returnswithinoption");
            return (Criteria) this;
        }

        public Criteria andReturnswithinoptionLessThanOrEqualTo(String value) {
            addCriterion("returnsWithinOption <=", value, "returnswithinoption");
            return (Criteria) this;
        }

        public Criteria andReturnswithinoptionLike(String value) {
            addCriterion("returnsWithinOption like", value, "returnswithinoption");
            return (Criteria) this;
        }

        public Criteria andReturnswithinoptionNotLike(String value) {
            addCriterion("returnsWithinOption not like", value, "returnswithinoption");
            return (Criteria) this;
        }

        public Criteria andReturnswithinoptionIn(List<String> values) {
            addCriterion("returnsWithinOption in", values, "returnswithinoption");
            return (Criteria) this;
        }

        public Criteria andReturnswithinoptionNotIn(List<String> values) {
            addCriterion("returnsWithinOption not in", values, "returnswithinoption");
            return (Criteria) this;
        }

        public Criteria andReturnswithinoptionBetween(String value1, String value2) {
            addCriterion("returnsWithinOption between", value1, value2, "returnswithinoption");
            return (Criteria) this;
        }

        public Criteria andReturnswithinoptionNotBetween(String value1, String value2) {
            addCriterion("returnsWithinOption not between", value1, value2, "returnswithinoption");
            return (Criteria) this;
        }

        public Criteria andReturnswithinIsNull() {
            addCriterion("returnsWithin is null");
            return (Criteria) this;
        }

        public Criteria andReturnswithinIsNotNull() {
            addCriterion("returnsWithin is not null");
            return (Criteria) this;
        }

        public Criteria andReturnswithinEqualTo(String value) {
            addCriterion("returnsWithin =", value, "returnswithin");
            return (Criteria) this;
        }

        public Criteria andReturnswithinNotEqualTo(String value) {
            addCriterion("returnsWithin <>", value, "returnswithin");
            return (Criteria) this;
        }

        public Criteria andReturnswithinGreaterThan(String value) {
            addCriterion("returnsWithin >", value, "returnswithin");
            return (Criteria) this;
        }

        public Criteria andReturnswithinGreaterThanOrEqualTo(String value) {
            addCriterion("returnsWithin >=", value, "returnswithin");
            return (Criteria) this;
        }

        public Criteria andReturnswithinLessThan(String value) {
            addCriterion("returnsWithin <", value, "returnswithin");
            return (Criteria) this;
        }

        public Criteria andReturnswithinLessThanOrEqualTo(String value) {
            addCriterion("returnsWithin <=", value, "returnswithin");
            return (Criteria) this;
        }

        public Criteria andReturnswithinLike(String value) {
            addCriterion("returnsWithin like", value, "returnswithin");
            return (Criteria) this;
        }

        public Criteria andReturnswithinNotLike(String value) {
            addCriterion("returnsWithin not like", value, "returnswithin");
            return (Criteria) this;
        }

        public Criteria andReturnswithinIn(List<String> values) {
            addCriterion("returnsWithin in", values, "returnswithin");
            return (Criteria) this;
        }

        public Criteria andReturnswithinNotIn(List<String> values) {
            addCriterion("returnsWithin not in", values, "returnswithin");
            return (Criteria) this;
        }

        public Criteria andReturnswithinBetween(String value1, String value2) {
            addCriterion("returnsWithin between", value1, value2, "returnswithin");
            return (Criteria) this;
        }

        public Criteria andReturnswithinNotBetween(String value1, String value2) {
            addCriterion("returnsWithin not between", value1, value2, "returnswithin");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedoptionIsNull() {
            addCriterion("returnsAcceptedOption is null");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedoptionIsNotNull() {
            addCriterion("returnsAcceptedOption is not null");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedoptionEqualTo(String value) {
            addCriterion("returnsAcceptedOption =", value, "returnsacceptedoption");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedoptionNotEqualTo(String value) {
            addCriterion("returnsAcceptedOption <>", value, "returnsacceptedoption");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedoptionGreaterThan(String value) {
            addCriterion("returnsAcceptedOption >", value, "returnsacceptedoption");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedoptionGreaterThanOrEqualTo(String value) {
            addCriterion("returnsAcceptedOption >=", value, "returnsacceptedoption");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedoptionLessThan(String value) {
            addCriterion("returnsAcceptedOption <", value, "returnsacceptedoption");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedoptionLessThanOrEqualTo(String value) {
            addCriterion("returnsAcceptedOption <=", value, "returnsacceptedoption");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedoptionLike(String value) {
            addCriterion("returnsAcceptedOption like", value, "returnsacceptedoption");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedoptionNotLike(String value) {
            addCriterion("returnsAcceptedOption not like", value, "returnsacceptedoption");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedoptionIn(List<String> values) {
            addCriterion("returnsAcceptedOption in", values, "returnsacceptedoption");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedoptionNotIn(List<String> values) {
            addCriterion("returnsAcceptedOption not in", values, "returnsacceptedoption");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedoptionBetween(String value1, String value2) {
            addCriterion("returnsAcceptedOption between", value1, value2, "returnsacceptedoption");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedoptionNotBetween(String value1, String value2) {
            addCriterion("returnsAcceptedOption not between", value1, value2, "returnsacceptedoption");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedIsNull() {
            addCriterion("returnsAccepted is null");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedIsNotNull() {
            addCriterion("returnsAccepted is not null");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedEqualTo(String value) {
            addCriterion("returnsAccepted =", value, "returnsaccepted");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedNotEqualTo(String value) {
            addCriterion("returnsAccepted <>", value, "returnsaccepted");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedGreaterThan(String value) {
            addCriterion("returnsAccepted >", value, "returnsaccepted");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedGreaterThanOrEqualTo(String value) {
            addCriterion("returnsAccepted >=", value, "returnsaccepted");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedLessThan(String value) {
            addCriterion("returnsAccepted <", value, "returnsaccepted");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedLessThanOrEqualTo(String value) {
            addCriterion("returnsAccepted <=", value, "returnsaccepted");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedLike(String value) {
            addCriterion("returnsAccepted like", value, "returnsaccepted");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedNotLike(String value) {
            addCriterion("returnsAccepted not like", value, "returnsaccepted");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedIn(List<String> values) {
            addCriterion("returnsAccepted in", values, "returnsaccepted");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedNotIn(List<String> values) {
            addCriterion("returnsAccepted not in", values, "returnsaccepted");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedBetween(String value1, String value2) {
            addCriterion("returnsAccepted between", value1, value2, "returnsaccepted");
            return (Criteria) this;
        }

        public Criteria andReturnsacceptedNotBetween(String value1, String value2) {
            addCriterion("returnsAccepted not between", value1, value2, "returnsaccepted");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyoptionIsNull() {
            addCriterion("shippingCostPaidByOption is null");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyoptionIsNotNull() {
            addCriterion("shippingCostPaidByOption is not null");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyoptionEqualTo(String value) {
            addCriterion("shippingCostPaidByOption =", value, "shippingcostpaidbyoption");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyoptionNotEqualTo(String value) {
            addCriterion("shippingCostPaidByOption <>", value, "shippingcostpaidbyoption");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyoptionGreaterThan(String value) {
            addCriterion("shippingCostPaidByOption >", value, "shippingcostpaidbyoption");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyoptionGreaterThanOrEqualTo(String value) {
            addCriterion("shippingCostPaidByOption >=", value, "shippingcostpaidbyoption");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyoptionLessThan(String value) {
            addCriterion("shippingCostPaidByOption <", value, "shippingcostpaidbyoption");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyoptionLessThanOrEqualTo(String value) {
            addCriterion("shippingCostPaidByOption <=", value, "shippingcostpaidbyoption");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyoptionLike(String value) {
            addCriterion("shippingCostPaidByOption like", value, "shippingcostpaidbyoption");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyoptionNotLike(String value) {
            addCriterion("shippingCostPaidByOption not like", value, "shippingcostpaidbyoption");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyoptionIn(List<String> values) {
            addCriterion("shippingCostPaidByOption in", values, "shippingcostpaidbyoption");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyoptionNotIn(List<String> values) {
            addCriterion("shippingCostPaidByOption not in", values, "shippingcostpaidbyoption");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyoptionBetween(String value1, String value2) {
            addCriterion("shippingCostPaidByOption between", value1, value2, "shippingcostpaidbyoption");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyoptionNotBetween(String value1, String value2) {
            addCriterion("shippingCostPaidByOption not between", value1, value2, "shippingcostpaidbyoption");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyIsNull() {
            addCriterion("shippingCostPaidBy is null");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyIsNotNull() {
            addCriterion("shippingCostPaidBy is not null");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyEqualTo(String value) {
            addCriterion("shippingCostPaidBy =", value, "shippingcostpaidby");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyNotEqualTo(String value) {
            addCriterion("shippingCostPaidBy <>", value, "shippingcostpaidby");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyGreaterThan(String value) {
            addCriterion("shippingCostPaidBy >", value, "shippingcostpaidby");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyGreaterThanOrEqualTo(String value) {
            addCriterion("shippingCostPaidBy >=", value, "shippingcostpaidby");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyLessThan(String value) {
            addCriterion("shippingCostPaidBy <", value, "shippingcostpaidby");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyLessThanOrEqualTo(String value) {
            addCriterion("shippingCostPaidBy <=", value, "shippingcostpaidby");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyLike(String value) {
            addCriterion("shippingCostPaidBy like", value, "shippingcostpaidby");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyNotLike(String value) {
            addCriterion("shippingCostPaidBy not like", value, "shippingcostpaidby");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyIn(List<String> values) {
            addCriterion("shippingCostPaidBy in", values, "shippingcostpaidby");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyNotIn(List<String> values) {
            addCriterion("shippingCostPaidBy not in", values, "shippingcostpaidby");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyBetween(String value1, String value2) {
            addCriterion("shippingCostPaidBy between", value1, value2, "shippingcostpaidby");
            return (Criteria) this;
        }

        public Criteria andShippingcostpaidbyNotBetween(String value1, String value2) {
            addCriterion("shippingCostPaidBy not between", value1, value2, "shippingcostpaidby");
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
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table trading_order_returnpolicy
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
     * This class corresponds to the database table trading_order_returnpolicy
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