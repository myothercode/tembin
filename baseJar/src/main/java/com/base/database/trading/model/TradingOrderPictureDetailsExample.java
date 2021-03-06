package com.base.database.trading.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TradingOrderPictureDetailsExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    public TradingOrderPictureDetailsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_picturedetails
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
     * This method corresponds to the database table trading_order_picturedetails
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
     * This method corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_picturedetails
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
     * This class corresponds to the database table trading_order_picturedetails
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

        public Criteria andGallerytypeIsNull() {
            addCriterion("galleryType is null");
            return (Criteria) this;
        }

        public Criteria andGallerytypeIsNotNull() {
            addCriterion("galleryType is not null");
            return (Criteria) this;
        }

        public Criteria andGallerytypeEqualTo(String value) {
            addCriterion("galleryType =", value, "gallerytype");
            return (Criteria) this;
        }

        public Criteria andGallerytypeNotEqualTo(String value) {
            addCriterion("galleryType <>", value, "gallerytype");
            return (Criteria) this;
        }

        public Criteria andGallerytypeGreaterThan(String value) {
            addCriterion("galleryType >", value, "gallerytype");
            return (Criteria) this;
        }

        public Criteria andGallerytypeGreaterThanOrEqualTo(String value) {
            addCriterion("galleryType >=", value, "gallerytype");
            return (Criteria) this;
        }

        public Criteria andGallerytypeLessThan(String value) {
            addCriterion("galleryType <", value, "gallerytype");
            return (Criteria) this;
        }

        public Criteria andGallerytypeLessThanOrEqualTo(String value) {
            addCriterion("galleryType <=", value, "gallerytype");
            return (Criteria) this;
        }

        public Criteria andGallerytypeLike(String value) {
            addCriterion("galleryType like", value, "gallerytype");
            return (Criteria) this;
        }

        public Criteria andGallerytypeNotLike(String value) {
            addCriterion("galleryType not like", value, "gallerytype");
            return (Criteria) this;
        }

        public Criteria andGallerytypeIn(List<String> values) {
            addCriterion("galleryType in", values, "gallerytype");
            return (Criteria) this;
        }

        public Criteria andGallerytypeNotIn(List<String> values) {
            addCriterion("galleryType not in", values, "gallerytype");
            return (Criteria) this;
        }

        public Criteria andGallerytypeBetween(String value1, String value2) {
            addCriterion("galleryType between", value1, value2, "gallerytype");
            return (Criteria) this;
        }

        public Criteria andGallerytypeNotBetween(String value1, String value2) {
            addCriterion("galleryType not between", value1, value2, "gallerytype");
            return (Criteria) this;
        }

        public Criteria andGalleryurlIsNull() {
            addCriterion("galleryURL is null");
            return (Criteria) this;
        }

        public Criteria andGalleryurlIsNotNull() {
            addCriterion("galleryURL is not null");
            return (Criteria) this;
        }

        public Criteria andGalleryurlEqualTo(String value) {
            addCriterion("galleryURL =", value, "galleryurl");
            return (Criteria) this;
        }

        public Criteria andGalleryurlNotEqualTo(String value) {
            addCriterion("galleryURL <>", value, "galleryurl");
            return (Criteria) this;
        }

        public Criteria andGalleryurlGreaterThan(String value) {
            addCriterion("galleryURL >", value, "galleryurl");
            return (Criteria) this;
        }

        public Criteria andGalleryurlGreaterThanOrEqualTo(String value) {
            addCriterion("galleryURL >=", value, "galleryurl");
            return (Criteria) this;
        }

        public Criteria andGalleryurlLessThan(String value) {
            addCriterion("galleryURL <", value, "galleryurl");
            return (Criteria) this;
        }

        public Criteria andGalleryurlLessThanOrEqualTo(String value) {
            addCriterion("galleryURL <=", value, "galleryurl");
            return (Criteria) this;
        }

        public Criteria andGalleryurlLike(String value) {
            addCriterion("galleryURL like", value, "galleryurl");
            return (Criteria) this;
        }

        public Criteria andGalleryurlNotLike(String value) {
            addCriterion("galleryURL not like", value, "galleryurl");
            return (Criteria) this;
        }

        public Criteria andGalleryurlIn(List<String> values) {
            addCriterion("galleryURL in", values, "galleryurl");
            return (Criteria) this;
        }

        public Criteria andGalleryurlNotIn(List<String> values) {
            addCriterion("galleryURL not in", values, "galleryurl");
            return (Criteria) this;
        }

        public Criteria andGalleryurlBetween(String value1, String value2) {
            addCriterion("galleryURL between", value1, value2, "galleryurl");
            return (Criteria) this;
        }

        public Criteria andGalleryurlNotBetween(String value1, String value2) {
            addCriterion("galleryURL not between", value1, value2, "galleryurl");
            return (Criteria) this;
        }

        public Criteria andPhotodisplayIsNull() {
            addCriterion("photoDisplay is null");
            return (Criteria) this;
        }

        public Criteria andPhotodisplayIsNotNull() {
            addCriterion("photoDisplay is not null");
            return (Criteria) this;
        }

        public Criteria andPhotodisplayEqualTo(String value) {
            addCriterion("photoDisplay =", value, "photodisplay");
            return (Criteria) this;
        }

        public Criteria andPhotodisplayNotEqualTo(String value) {
            addCriterion("photoDisplay <>", value, "photodisplay");
            return (Criteria) this;
        }

        public Criteria andPhotodisplayGreaterThan(String value) {
            addCriterion("photoDisplay >", value, "photodisplay");
            return (Criteria) this;
        }

        public Criteria andPhotodisplayGreaterThanOrEqualTo(String value) {
            addCriterion("photoDisplay >=", value, "photodisplay");
            return (Criteria) this;
        }

        public Criteria andPhotodisplayLessThan(String value) {
            addCriterion("photoDisplay <", value, "photodisplay");
            return (Criteria) this;
        }

        public Criteria andPhotodisplayLessThanOrEqualTo(String value) {
            addCriterion("photoDisplay <=", value, "photodisplay");
            return (Criteria) this;
        }

        public Criteria andPhotodisplayLike(String value) {
            addCriterion("photoDisplay like", value, "photodisplay");
            return (Criteria) this;
        }

        public Criteria andPhotodisplayNotLike(String value) {
            addCriterion("photoDisplay not like", value, "photodisplay");
            return (Criteria) this;
        }

        public Criteria andPhotodisplayIn(List<String> values) {
            addCriterion("photoDisplay in", values, "photodisplay");
            return (Criteria) this;
        }

        public Criteria andPhotodisplayNotIn(List<String> values) {
            addCriterion("photoDisplay not in", values, "photodisplay");
            return (Criteria) this;
        }

        public Criteria andPhotodisplayBetween(String value1, String value2) {
            addCriterion("photoDisplay between", value1, value2, "photodisplay");
            return (Criteria) this;
        }

        public Criteria andPhotodisplayNotBetween(String value1, String value2) {
            addCriterion("photoDisplay not between", value1, value2, "photodisplay");
            return (Criteria) this;
        }

        public Criteria andPictureurlIsNull() {
            addCriterion("pictureURL is null");
            return (Criteria) this;
        }

        public Criteria andPictureurlIsNotNull() {
            addCriterion("pictureURL is not null");
            return (Criteria) this;
        }

        public Criteria andPictureurlEqualTo(String value) {
            addCriterion("pictureURL =", value, "pictureurl");
            return (Criteria) this;
        }

        public Criteria andPictureurlNotEqualTo(String value) {
            addCriterion("pictureURL <>", value, "pictureurl");
            return (Criteria) this;
        }

        public Criteria andPictureurlGreaterThan(String value) {
            addCriterion("pictureURL >", value, "pictureurl");
            return (Criteria) this;
        }

        public Criteria andPictureurlGreaterThanOrEqualTo(String value) {
            addCriterion("pictureURL >=", value, "pictureurl");
            return (Criteria) this;
        }

        public Criteria andPictureurlLessThan(String value) {
            addCriterion("pictureURL <", value, "pictureurl");
            return (Criteria) this;
        }

        public Criteria andPictureurlLessThanOrEqualTo(String value) {
            addCriterion("pictureURL <=", value, "pictureurl");
            return (Criteria) this;
        }

        public Criteria andPictureurlLike(String value) {
            addCriterion("pictureURL like", value, "pictureurl");
            return (Criteria) this;
        }

        public Criteria andPictureurlNotLike(String value) {
            addCriterion("pictureURL not like", value, "pictureurl");
            return (Criteria) this;
        }

        public Criteria andPictureurlIn(List<String> values) {
            addCriterion("pictureURL in", values, "pictureurl");
            return (Criteria) this;
        }

        public Criteria andPictureurlNotIn(List<String> values) {
            addCriterion("pictureURL not in", values, "pictureurl");
            return (Criteria) this;
        }

        public Criteria andPictureurlBetween(String value1, String value2) {
            addCriterion("pictureURL between", value1, value2, "pictureurl");
            return (Criteria) this;
        }

        public Criteria andPictureurlNotBetween(String value1, String value2) {
            addCriterion("pictureURL not between", value1, value2, "pictureurl");
            return (Criteria) this;
        }

        public Criteria andPictureurl1IsNull() {
            addCriterion("pictureURL1 is null");
            return (Criteria) this;
        }

        public Criteria andPictureurl1IsNotNull() {
            addCriterion("pictureURL1 is not null");
            return (Criteria) this;
        }

        public Criteria andPictureurl1EqualTo(String value) {
            addCriterion("pictureURL1 =", value, "pictureurl1");
            return (Criteria) this;
        }

        public Criteria andPictureurl1NotEqualTo(String value) {
            addCriterion("pictureURL1 <>", value, "pictureurl1");
            return (Criteria) this;
        }

        public Criteria andPictureurl1GreaterThan(String value) {
            addCriterion("pictureURL1 >", value, "pictureurl1");
            return (Criteria) this;
        }

        public Criteria andPictureurl1GreaterThanOrEqualTo(String value) {
            addCriterion("pictureURL1 >=", value, "pictureurl1");
            return (Criteria) this;
        }

        public Criteria andPictureurl1LessThan(String value) {
            addCriterion("pictureURL1 <", value, "pictureurl1");
            return (Criteria) this;
        }

        public Criteria andPictureurl1LessThanOrEqualTo(String value) {
            addCriterion("pictureURL1 <=", value, "pictureurl1");
            return (Criteria) this;
        }

        public Criteria andPictureurl1Like(String value) {
            addCriterion("pictureURL1 like", value, "pictureurl1");
            return (Criteria) this;
        }

        public Criteria andPictureurl1NotLike(String value) {
            addCriterion("pictureURL1 not like", value, "pictureurl1");
            return (Criteria) this;
        }

        public Criteria andPictureurl1In(List<String> values) {
            addCriterion("pictureURL1 in", values, "pictureurl1");
            return (Criteria) this;
        }

        public Criteria andPictureurl1NotIn(List<String> values) {
            addCriterion("pictureURL1 not in", values, "pictureurl1");
            return (Criteria) this;
        }

        public Criteria andPictureurl1Between(String value1, String value2) {
            addCriterion("pictureURL1 between", value1, value2, "pictureurl1");
            return (Criteria) this;
        }

        public Criteria andPictureurl1NotBetween(String value1, String value2) {
            addCriterion("pictureURL1 not between", value1, value2, "pictureurl1");
            return (Criteria) this;
        }

        public Criteria andPictureurl2IsNull() {
            addCriterion("pictureURL2 is null");
            return (Criteria) this;
        }

        public Criteria andPictureurl2IsNotNull() {
            addCriterion("pictureURL2 is not null");
            return (Criteria) this;
        }

        public Criteria andPictureurl2EqualTo(String value) {
            addCriterion("pictureURL2 =", value, "pictureurl2");
            return (Criteria) this;
        }

        public Criteria andPictureurl2NotEqualTo(String value) {
            addCriterion("pictureURL2 <>", value, "pictureurl2");
            return (Criteria) this;
        }

        public Criteria andPictureurl2GreaterThan(String value) {
            addCriterion("pictureURL2 >", value, "pictureurl2");
            return (Criteria) this;
        }

        public Criteria andPictureurl2GreaterThanOrEqualTo(String value) {
            addCriterion("pictureURL2 >=", value, "pictureurl2");
            return (Criteria) this;
        }

        public Criteria andPictureurl2LessThan(String value) {
            addCriterion("pictureURL2 <", value, "pictureurl2");
            return (Criteria) this;
        }

        public Criteria andPictureurl2LessThanOrEqualTo(String value) {
            addCriterion("pictureURL2 <=", value, "pictureurl2");
            return (Criteria) this;
        }

        public Criteria andPictureurl2Like(String value) {
            addCriterion("pictureURL2 like", value, "pictureurl2");
            return (Criteria) this;
        }

        public Criteria andPictureurl2NotLike(String value) {
            addCriterion("pictureURL2 not like", value, "pictureurl2");
            return (Criteria) this;
        }

        public Criteria andPictureurl2In(List<String> values) {
            addCriterion("pictureURL2 in", values, "pictureurl2");
            return (Criteria) this;
        }

        public Criteria andPictureurl2NotIn(List<String> values) {
            addCriterion("pictureURL2 not in", values, "pictureurl2");
            return (Criteria) this;
        }

        public Criteria andPictureurl2Between(String value1, String value2) {
            addCriterion("pictureURL2 between", value1, value2, "pictureurl2");
            return (Criteria) this;
        }

        public Criteria andPictureurl2NotBetween(String value1, String value2) {
            addCriterion("pictureURL2 not between", value1, value2, "pictureurl2");
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
     * This class corresponds to the database table trading_order_picturedetails
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
     * This class corresponds to the database table trading_order_picturedetails
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