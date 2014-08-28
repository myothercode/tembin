package com.base.database.trading.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TradingOrderShippingServiceOptionsExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    public TradingOrderShippingServiceOptionsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingserviceoptions
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
     * This method corresponds to the database table trading_order_shippingserviceoptions
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
     * This method corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingserviceoptions
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
     * This class corresponds to the database table trading_order_shippingserviceoptions
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

        public Criteria andShippingserviceIsNull() {
            addCriterion("shippingService is null");
            return (Criteria) this;
        }

        public Criteria andShippingserviceIsNotNull() {
            addCriterion("shippingService is not null");
            return (Criteria) this;
        }

        public Criteria andShippingserviceEqualTo(String value) {
            addCriterion("shippingService =", value, "shippingservice");
            return (Criteria) this;
        }

        public Criteria andShippingserviceNotEqualTo(String value) {
            addCriterion("shippingService <>", value, "shippingservice");
            return (Criteria) this;
        }

        public Criteria andShippingserviceGreaterThan(String value) {
            addCriterion("shippingService >", value, "shippingservice");
            return (Criteria) this;
        }

        public Criteria andShippingserviceGreaterThanOrEqualTo(String value) {
            addCriterion("shippingService >=", value, "shippingservice");
            return (Criteria) this;
        }

        public Criteria andShippingserviceLessThan(String value) {
            addCriterion("shippingService <", value, "shippingservice");
            return (Criteria) this;
        }

        public Criteria andShippingserviceLessThanOrEqualTo(String value) {
            addCriterion("shippingService <=", value, "shippingservice");
            return (Criteria) this;
        }

        public Criteria andShippingserviceLike(String value) {
            addCriterion("shippingService like", value, "shippingservice");
            return (Criteria) this;
        }

        public Criteria andShippingserviceNotLike(String value) {
            addCriterion("shippingService not like", value, "shippingservice");
            return (Criteria) this;
        }

        public Criteria andShippingserviceIn(List<String> values) {
            addCriterion("shippingService in", values, "shippingservice");
            return (Criteria) this;
        }

        public Criteria andShippingserviceNotIn(List<String> values) {
            addCriterion("shippingService not in", values, "shippingservice");
            return (Criteria) this;
        }

        public Criteria andShippingserviceBetween(String value1, String value2) {
            addCriterion("shippingService between", value1, value2, "shippingservice");
            return (Criteria) this;
        }

        public Criteria andShippingserviceNotBetween(String value1, String value2) {
            addCriterion("shippingService not between", value1, value2, "shippingservice");
            return (Criteria) this;
        }

        public Criteria andShippingservicepriorityIsNull() {
            addCriterion("shippingServicePriority is null");
            return (Criteria) this;
        }

        public Criteria andShippingservicepriorityIsNotNull() {
            addCriterion("shippingServicePriority is not null");
            return (Criteria) this;
        }

        public Criteria andShippingservicepriorityEqualTo(Integer value) {
            addCriterion("shippingServicePriority =", value, "shippingservicepriority");
            return (Criteria) this;
        }

        public Criteria andShippingservicepriorityNotEqualTo(Integer value) {
            addCriterion("shippingServicePriority <>", value, "shippingservicepriority");
            return (Criteria) this;
        }

        public Criteria andShippingservicepriorityGreaterThan(Integer value) {
            addCriterion("shippingServicePriority >", value, "shippingservicepriority");
            return (Criteria) this;
        }

        public Criteria andShippingservicepriorityGreaterThanOrEqualTo(Integer value) {
            addCriterion("shippingServicePriority >=", value, "shippingservicepriority");
            return (Criteria) this;
        }

        public Criteria andShippingservicepriorityLessThan(Integer value) {
            addCriterion("shippingServicePriority <", value, "shippingservicepriority");
            return (Criteria) this;
        }

        public Criteria andShippingservicepriorityLessThanOrEqualTo(Integer value) {
            addCriterion("shippingServicePriority <=", value, "shippingservicepriority");
            return (Criteria) this;
        }

        public Criteria andShippingservicepriorityIn(List<Integer> values) {
            addCriterion("shippingServicePriority in", values, "shippingservicepriority");
            return (Criteria) this;
        }

        public Criteria andShippingservicepriorityNotIn(List<Integer> values) {
            addCriterion("shippingServicePriority not in", values, "shippingservicepriority");
            return (Criteria) this;
        }

        public Criteria andShippingservicepriorityBetween(Integer value1, Integer value2) {
            addCriterion("shippingServicePriority between", value1, value2, "shippingservicepriority");
            return (Criteria) this;
        }

        public Criteria andShippingservicepriorityNotBetween(Integer value1, Integer value2) {
            addCriterion("shippingServicePriority not between", value1, value2, "shippingservicepriority");
            return (Criteria) this;
        }

        public Criteria andExpeditedserviceIsNull() {
            addCriterion("expeditedService is null");
            return (Criteria) this;
        }

        public Criteria andExpeditedserviceIsNotNull() {
            addCriterion("expeditedService is not null");
            return (Criteria) this;
        }

        public Criteria andExpeditedserviceEqualTo(String value) {
            addCriterion("expeditedService =", value, "expeditedservice");
            return (Criteria) this;
        }

        public Criteria andExpeditedserviceNotEqualTo(String value) {
            addCriterion("expeditedService <>", value, "expeditedservice");
            return (Criteria) this;
        }

        public Criteria andExpeditedserviceGreaterThan(String value) {
            addCriterion("expeditedService >", value, "expeditedservice");
            return (Criteria) this;
        }

        public Criteria andExpeditedserviceGreaterThanOrEqualTo(String value) {
            addCriterion("expeditedService >=", value, "expeditedservice");
            return (Criteria) this;
        }

        public Criteria andExpeditedserviceLessThan(String value) {
            addCriterion("expeditedService <", value, "expeditedservice");
            return (Criteria) this;
        }

        public Criteria andExpeditedserviceLessThanOrEqualTo(String value) {
            addCriterion("expeditedService <=", value, "expeditedservice");
            return (Criteria) this;
        }

        public Criteria andExpeditedserviceLike(String value) {
            addCriterion("expeditedService like", value, "expeditedservice");
            return (Criteria) this;
        }

        public Criteria andExpeditedserviceNotLike(String value) {
            addCriterion("expeditedService not like", value, "expeditedservice");
            return (Criteria) this;
        }

        public Criteria andExpeditedserviceIn(List<String> values) {
            addCriterion("expeditedService in", values, "expeditedservice");
            return (Criteria) this;
        }

        public Criteria andExpeditedserviceNotIn(List<String> values) {
            addCriterion("expeditedService not in", values, "expeditedservice");
            return (Criteria) this;
        }

        public Criteria andExpeditedserviceBetween(String value1, String value2) {
            addCriterion("expeditedService between", value1, value2, "expeditedservice");
            return (Criteria) this;
        }

        public Criteria andExpeditedserviceNotBetween(String value1, String value2) {
            addCriterion("expeditedService not between", value1, value2, "expeditedservice");
            return (Criteria) this;
        }

        public Criteria andShippingtimeminIsNull() {
            addCriterion("shippingTimeMin is null");
            return (Criteria) this;
        }

        public Criteria andShippingtimeminIsNotNull() {
            addCriterion("shippingTimeMin is not null");
            return (Criteria) this;
        }

        public Criteria andShippingtimeminEqualTo(Integer value) {
            addCriterion("shippingTimeMin =", value, "shippingtimemin");
            return (Criteria) this;
        }

        public Criteria andShippingtimeminNotEqualTo(Integer value) {
            addCriterion("shippingTimeMin <>", value, "shippingtimemin");
            return (Criteria) this;
        }

        public Criteria andShippingtimeminGreaterThan(Integer value) {
            addCriterion("shippingTimeMin >", value, "shippingtimemin");
            return (Criteria) this;
        }

        public Criteria andShippingtimeminGreaterThanOrEqualTo(Integer value) {
            addCriterion("shippingTimeMin >=", value, "shippingtimemin");
            return (Criteria) this;
        }

        public Criteria andShippingtimeminLessThan(Integer value) {
            addCriterion("shippingTimeMin <", value, "shippingtimemin");
            return (Criteria) this;
        }

        public Criteria andShippingtimeminLessThanOrEqualTo(Integer value) {
            addCriterion("shippingTimeMin <=", value, "shippingtimemin");
            return (Criteria) this;
        }

        public Criteria andShippingtimeminIn(List<Integer> values) {
            addCriterion("shippingTimeMin in", values, "shippingtimemin");
            return (Criteria) this;
        }

        public Criteria andShippingtimeminNotIn(List<Integer> values) {
            addCriterion("shippingTimeMin not in", values, "shippingtimemin");
            return (Criteria) this;
        }

        public Criteria andShippingtimeminBetween(Integer value1, Integer value2) {
            addCriterion("shippingTimeMin between", value1, value2, "shippingtimemin");
            return (Criteria) this;
        }

        public Criteria andShippingtimeminNotBetween(Integer value1, Integer value2) {
            addCriterion("shippingTimeMin not between", value1, value2, "shippingtimemin");
            return (Criteria) this;
        }

        public Criteria andShippingtimemaxIsNull() {
            addCriterion("shippingTimeMax is null");
            return (Criteria) this;
        }

        public Criteria andShippingtimemaxIsNotNull() {
            addCriterion("shippingTimeMax is not null");
            return (Criteria) this;
        }

        public Criteria andShippingtimemaxEqualTo(Integer value) {
            addCriterion("shippingTimeMax =", value, "shippingtimemax");
            return (Criteria) this;
        }

        public Criteria andShippingtimemaxNotEqualTo(Integer value) {
            addCriterion("shippingTimeMax <>", value, "shippingtimemax");
            return (Criteria) this;
        }

        public Criteria andShippingtimemaxGreaterThan(Integer value) {
            addCriterion("shippingTimeMax >", value, "shippingtimemax");
            return (Criteria) this;
        }

        public Criteria andShippingtimemaxGreaterThanOrEqualTo(Integer value) {
            addCriterion("shippingTimeMax >=", value, "shippingtimemax");
            return (Criteria) this;
        }

        public Criteria andShippingtimemaxLessThan(Integer value) {
            addCriterion("shippingTimeMax <", value, "shippingtimemax");
            return (Criteria) this;
        }

        public Criteria andShippingtimemaxLessThanOrEqualTo(Integer value) {
            addCriterion("shippingTimeMax <=", value, "shippingtimemax");
            return (Criteria) this;
        }

        public Criteria andShippingtimemaxIn(List<Integer> values) {
            addCriterion("shippingTimeMax in", values, "shippingtimemax");
            return (Criteria) this;
        }

        public Criteria andShippingtimemaxNotIn(List<Integer> values) {
            addCriterion("shippingTimeMax not in", values, "shippingtimemax");
            return (Criteria) this;
        }

        public Criteria andShippingtimemaxBetween(Integer value1, Integer value2) {
            addCriterion("shippingTimeMax between", value1, value2, "shippingtimemax");
            return (Criteria) this;
        }

        public Criteria andShippingtimemaxNotBetween(Integer value1, Integer value2) {
            addCriterion("shippingTimeMax not between", value1, value2, "shippingtimemax");
            return (Criteria) this;
        }

        public Criteria andFreeshippingIsNull() {
            addCriterion("freeShipping is null");
            return (Criteria) this;
        }

        public Criteria andFreeshippingIsNotNull() {
            addCriterion("freeShipping is not null");
            return (Criteria) this;
        }

        public Criteria andFreeshippingEqualTo(String value) {
            addCriterion("freeShipping =", value, "freeshipping");
            return (Criteria) this;
        }

        public Criteria andFreeshippingNotEqualTo(String value) {
            addCriterion("freeShipping <>", value, "freeshipping");
            return (Criteria) this;
        }

        public Criteria andFreeshippingGreaterThan(String value) {
            addCriterion("freeShipping >", value, "freeshipping");
            return (Criteria) this;
        }

        public Criteria andFreeshippingGreaterThanOrEqualTo(String value) {
            addCriterion("freeShipping >=", value, "freeshipping");
            return (Criteria) this;
        }

        public Criteria andFreeshippingLessThan(String value) {
            addCriterion("freeShipping <", value, "freeshipping");
            return (Criteria) this;
        }

        public Criteria andFreeshippingLessThanOrEqualTo(String value) {
            addCriterion("freeShipping <=", value, "freeshipping");
            return (Criteria) this;
        }

        public Criteria andFreeshippingLike(String value) {
            addCriterion("freeShipping like", value, "freeshipping");
            return (Criteria) this;
        }

        public Criteria andFreeshippingNotLike(String value) {
            addCriterion("freeShipping not like", value, "freeshipping");
            return (Criteria) this;
        }

        public Criteria andFreeshippingIn(List<String> values) {
            addCriterion("freeShipping in", values, "freeshipping");
            return (Criteria) this;
        }

        public Criteria andFreeshippingNotIn(List<String> values) {
            addCriterion("freeShipping not in", values, "freeshipping");
            return (Criteria) this;
        }

        public Criteria andFreeshippingBetween(String value1, String value2) {
            addCriterion("freeShipping between", value1, value2, "freeshipping");
            return (Criteria) this;
        }

        public Criteria andFreeshippingNotBetween(String value1, String value2) {
            addCriterion("freeShipping not between", value1, value2, "freeshipping");
            return (Criteria) this;
        }

        public Criteria andShippingdetailsIdIsNull() {
            addCriterion("ShippingDetails_id is null");
            return (Criteria) this;
        }

        public Criteria andShippingdetailsIdIsNotNull() {
            addCriterion("ShippingDetails_id is not null");
            return (Criteria) this;
        }

        public Criteria andShippingdetailsIdEqualTo(Long value) {
            addCriterion("ShippingDetails_id =", value, "shippingdetailsId");
            return (Criteria) this;
        }

        public Criteria andShippingdetailsIdNotEqualTo(Long value) {
            addCriterion("ShippingDetails_id <>", value, "shippingdetailsId");
            return (Criteria) this;
        }

        public Criteria andShippingdetailsIdGreaterThan(Long value) {
            addCriterion("ShippingDetails_id >", value, "shippingdetailsId");
            return (Criteria) this;
        }

        public Criteria andShippingdetailsIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ShippingDetails_id >=", value, "shippingdetailsId");
            return (Criteria) this;
        }

        public Criteria andShippingdetailsIdLessThan(Long value) {
            addCriterion("ShippingDetails_id <", value, "shippingdetailsId");
            return (Criteria) this;
        }

        public Criteria andShippingdetailsIdLessThanOrEqualTo(Long value) {
            addCriterion("ShippingDetails_id <=", value, "shippingdetailsId");
            return (Criteria) this;
        }

        public Criteria andShippingdetailsIdIn(List<Long> values) {
            addCriterion("ShippingDetails_id in", values, "shippingdetailsId");
            return (Criteria) this;
        }

        public Criteria andShippingdetailsIdNotIn(List<Long> values) {
            addCriterion("ShippingDetails_id not in", values, "shippingdetailsId");
            return (Criteria) this;
        }

        public Criteria andShippingdetailsIdBetween(Long value1, Long value2) {
            addCriterion("ShippingDetails_id between", value1, value2, "shippingdetailsId");
            return (Criteria) this;
        }

        public Criteria andShippingdetailsIdNotBetween(Long value1, Long value2) {
            addCriterion("ShippingDetails_id not between", value1, value2, "shippingdetailsId");
            return (Criteria) this;
        }

        public Criteria andShiptolocationIsNull() {
            addCriterion("shipToLocation is null");
            return (Criteria) this;
        }

        public Criteria andShiptolocationIsNotNull() {
            addCriterion("shipToLocation is not null");
            return (Criteria) this;
        }

        public Criteria andShiptolocationEqualTo(String value) {
            addCriterion("shipToLocation =", value, "shiptolocation");
            return (Criteria) this;
        }

        public Criteria andShiptolocationNotEqualTo(String value) {
            addCriterion("shipToLocation <>", value, "shiptolocation");
            return (Criteria) this;
        }

        public Criteria andShiptolocationGreaterThan(String value) {
            addCriterion("shipToLocation >", value, "shiptolocation");
            return (Criteria) this;
        }

        public Criteria andShiptolocationGreaterThanOrEqualTo(String value) {
            addCriterion("shipToLocation >=", value, "shiptolocation");
            return (Criteria) this;
        }

        public Criteria andShiptolocationLessThan(String value) {
            addCriterion("shipToLocation <", value, "shiptolocation");
            return (Criteria) this;
        }

        public Criteria andShiptolocationLessThanOrEqualTo(String value) {
            addCriterion("shipToLocation <=", value, "shiptolocation");
            return (Criteria) this;
        }

        public Criteria andShiptolocationLike(String value) {
            addCriterion("shipToLocation like", value, "shiptolocation");
            return (Criteria) this;
        }

        public Criteria andShiptolocationNotLike(String value) {
            addCriterion("shipToLocation not like", value, "shiptolocation");
            return (Criteria) this;
        }

        public Criteria andShiptolocationIn(List<String> values) {
            addCriterion("shipToLocation in", values, "shiptolocation");
            return (Criteria) this;
        }

        public Criteria andShiptolocationNotIn(List<String> values) {
            addCriterion("shipToLocation not in", values, "shiptolocation");
            return (Criteria) this;
        }

        public Criteria andShiptolocationBetween(String value1, String value2) {
            addCriterion("shipToLocation between", value1, value2, "shiptolocation");
            return (Criteria) this;
        }

        public Criteria andShiptolocationNotBetween(String value1, String value2) {
            addCriterion("shipToLocation not between", value1, value2, "shiptolocation");
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
     * This class corresponds to the database table trading_order_shippingserviceoptions
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
     * This class corresponds to the database table trading_order_shippingserviceoptions
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