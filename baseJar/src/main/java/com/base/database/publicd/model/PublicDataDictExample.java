package com.base.database.publicd.model;

import java.util.ArrayList;
import java.util.List;

public class PublicDataDictExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public_data_dict
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public_data_dict
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public_data_dict
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_data_dict
     *
     * @mbggenerated
     */
    public PublicDataDictExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_data_dict
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_data_dict
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_data_dict
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_data_dict
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_data_dict
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_data_dict
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_data_dict
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
     * This method corresponds to the database table public_data_dict
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
     * This method corresponds to the database table public_data_dict
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_data_dict
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
     * This class corresponds to the database table public_data_dict
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

        public Criteria andItemIdIsNull() {
            addCriterion("item_id is null");
            return (Criteria) this;
        }

        public Criteria andItemIdIsNotNull() {
            addCriterion("item_id is not null");
            return (Criteria) this;
        }

        public Criteria andItemIdEqualTo(String value) {
            addCriterion("item_id =", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotEqualTo(String value) {
            addCriterion("item_id <>", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdGreaterThan(String value) {
            addCriterion("item_id >", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdGreaterThanOrEqualTo(String value) {
            addCriterion("item_id >=", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdLessThan(String value) {
            addCriterion("item_id <", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdLessThanOrEqualTo(String value) {
            addCriterion("item_id <=", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdLike(String value) {
            addCriterion("item_id like", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotLike(String value) {
            addCriterion("item_id not like", value, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdIn(List<String> values) {
            addCriterion("item_id in", values, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotIn(List<String> values) {
            addCriterion("item_id not in", values, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdBetween(String value1, String value2) {
            addCriterion("item_id between", value1, value2, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemIdNotBetween(String value1, String value2) {
            addCriterion("item_id not between", value1, value2, "itemId");
            return (Criteria) this;
        }

        public Criteria andItemEnNameIsNull() {
            addCriterion("item_en_name is null");
            return (Criteria) this;
        }

        public Criteria andItemEnNameIsNotNull() {
            addCriterion("item_en_name is not null");
            return (Criteria) this;
        }

        public Criteria andItemEnNameEqualTo(String value) {
            addCriterion("item_en_name =", value, "itemEnName");
            return (Criteria) this;
        }

        public Criteria andItemEnNameNotEqualTo(String value) {
            addCriterion("item_en_name <>", value, "itemEnName");
            return (Criteria) this;
        }

        public Criteria andItemEnNameGreaterThan(String value) {
            addCriterion("item_en_name >", value, "itemEnName");
            return (Criteria) this;
        }

        public Criteria andItemEnNameGreaterThanOrEqualTo(String value) {
            addCriterion("item_en_name >=", value, "itemEnName");
            return (Criteria) this;
        }

        public Criteria andItemEnNameLessThan(String value) {
            addCriterion("item_en_name <", value, "itemEnName");
            return (Criteria) this;
        }

        public Criteria andItemEnNameLessThanOrEqualTo(String value) {
            addCriterion("item_en_name <=", value, "itemEnName");
            return (Criteria) this;
        }

        public Criteria andItemEnNameLike(String value) {
            addCriterion("item_en_name like", value, "itemEnName");
            return (Criteria) this;
        }

        public Criteria andItemEnNameNotLike(String value) {
            addCriterion("item_en_name not like", value, "itemEnName");
            return (Criteria) this;
        }

        public Criteria andItemEnNameIn(List<String> values) {
            addCriterion("item_en_name in", values, "itemEnName");
            return (Criteria) this;
        }

        public Criteria andItemEnNameNotIn(List<String> values) {
            addCriterion("item_en_name not in", values, "itemEnName");
            return (Criteria) this;
        }

        public Criteria andItemEnNameBetween(String value1, String value2) {
            addCriterion("item_en_name between", value1, value2, "itemEnName");
            return (Criteria) this;
        }

        public Criteria andItemEnNameNotBetween(String value1, String value2) {
            addCriterion("item_en_name not between", value1, value2, "itemEnName");
            return (Criteria) this;
        }

        public Criteria andItemChNameIsNull() {
            addCriterion("item_ch_name is null");
            return (Criteria) this;
        }

        public Criteria andItemChNameIsNotNull() {
            addCriterion("item_ch_name is not null");
            return (Criteria) this;
        }

        public Criteria andItemChNameEqualTo(String value) {
            addCriterion("item_ch_name =", value, "itemChName");
            return (Criteria) this;
        }

        public Criteria andItemChNameNotEqualTo(String value) {
            addCriterion("item_ch_name <>", value, "itemChName");
            return (Criteria) this;
        }

        public Criteria andItemChNameGreaterThan(String value) {
            addCriterion("item_ch_name >", value, "itemChName");
            return (Criteria) this;
        }

        public Criteria andItemChNameGreaterThanOrEqualTo(String value) {
            addCriterion("item_ch_name >=", value, "itemChName");
            return (Criteria) this;
        }

        public Criteria andItemChNameLessThan(String value) {
            addCriterion("item_ch_name <", value, "itemChName");
            return (Criteria) this;
        }

        public Criteria andItemChNameLessThanOrEqualTo(String value) {
            addCriterion("item_ch_name <=", value, "itemChName");
            return (Criteria) this;
        }

        public Criteria andItemChNameLike(String value) {
            addCriterion("item_ch_name like", value, "itemChName");
            return (Criteria) this;
        }

        public Criteria andItemChNameNotLike(String value) {
            addCriterion("item_ch_name not like", value, "itemChName");
            return (Criteria) this;
        }

        public Criteria andItemChNameIn(List<String> values) {
            addCriterion("item_ch_name in", values, "itemChName");
            return (Criteria) this;
        }

        public Criteria andItemChNameNotIn(List<String> values) {
            addCriterion("item_ch_name not in", values, "itemChName");
            return (Criteria) this;
        }

        public Criteria andItemChNameBetween(String value1, String value2) {
            addCriterion("item_ch_name between", value1, value2, "itemChName");
            return (Criteria) this;
        }

        public Criteria andItemChNameNotBetween(String value1, String value2) {
            addCriterion("item_ch_name not between", value1, value2, "itemChName");
            return (Criteria) this;
        }

        public Criteria andItemLevelIsNull() {
            addCriterion("item_level is null");
            return (Criteria) this;
        }

        public Criteria andItemLevelIsNotNull() {
            addCriterion("item_level is not null");
            return (Criteria) this;
        }

        public Criteria andItemLevelEqualTo(String value) {
            addCriterion("item_level =", value, "itemLevel");
            return (Criteria) this;
        }

        public Criteria andItemLevelNotEqualTo(String value) {
            addCriterion("item_level <>", value, "itemLevel");
            return (Criteria) this;
        }

        public Criteria andItemLevelGreaterThan(String value) {
            addCriterion("item_level >", value, "itemLevel");
            return (Criteria) this;
        }

        public Criteria andItemLevelGreaterThanOrEqualTo(String value) {
            addCriterion("item_level >=", value, "itemLevel");
            return (Criteria) this;
        }

        public Criteria andItemLevelLessThan(String value) {
            addCriterion("item_level <", value, "itemLevel");
            return (Criteria) this;
        }

        public Criteria andItemLevelLessThanOrEqualTo(String value) {
            addCriterion("item_level <=", value, "itemLevel");
            return (Criteria) this;
        }

        public Criteria andItemLevelLike(String value) {
            addCriterion("item_level like", value, "itemLevel");
            return (Criteria) this;
        }

        public Criteria andItemLevelNotLike(String value) {
            addCriterion("item_level not like", value, "itemLevel");
            return (Criteria) this;
        }

        public Criteria andItemLevelIn(List<String> values) {
            addCriterion("item_level in", values, "itemLevel");
            return (Criteria) this;
        }

        public Criteria andItemLevelNotIn(List<String> values) {
            addCriterion("item_level not in", values, "itemLevel");
            return (Criteria) this;
        }

        public Criteria andItemLevelBetween(String value1, String value2) {
            addCriterion("item_level between", value1, value2, "itemLevel");
            return (Criteria) this;
        }

        public Criteria andItemLevelNotBetween(String value1, String value2) {
            addCriterion("item_level not between", value1, value2, "itemLevel");
            return (Criteria) this;
        }

        public Criteria andItemParentIdIsNull() {
            addCriterion("item_parent_id is null");
            return (Criteria) this;
        }

        public Criteria andItemParentIdIsNotNull() {
            addCriterion("item_parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andItemParentIdEqualTo(String value) {
            addCriterion("item_parent_id =", value, "itemParentId");
            return (Criteria) this;
        }

        public Criteria andItemParentIdNotEqualTo(String value) {
            addCriterion("item_parent_id <>", value, "itemParentId");
            return (Criteria) this;
        }

        public Criteria andItemParentIdGreaterThan(String value) {
            addCriterion("item_parent_id >", value, "itemParentId");
            return (Criteria) this;
        }

        public Criteria andItemParentIdGreaterThanOrEqualTo(String value) {
            addCriterion("item_parent_id >=", value, "itemParentId");
            return (Criteria) this;
        }

        public Criteria andItemParentIdLessThan(String value) {
            addCriterion("item_parent_id <", value, "itemParentId");
            return (Criteria) this;
        }

        public Criteria andItemParentIdLessThanOrEqualTo(String value) {
            addCriterion("item_parent_id <=", value, "itemParentId");
            return (Criteria) this;
        }

        public Criteria andItemParentIdLike(String value) {
            addCriterion("item_parent_id like", value, "itemParentId");
            return (Criteria) this;
        }

        public Criteria andItemParentIdNotLike(String value) {
            addCriterion("item_parent_id not like", value, "itemParentId");
            return (Criteria) this;
        }

        public Criteria andItemParentIdIn(List<String> values) {
            addCriterion("item_parent_id in", values, "itemParentId");
            return (Criteria) this;
        }

        public Criteria andItemParentIdNotIn(List<String> values) {
            addCriterion("item_parent_id not in", values, "itemParentId");
            return (Criteria) this;
        }

        public Criteria andItemParentIdBetween(String value1, String value2) {
            addCriterion("item_parent_id between", value1, value2, "itemParentId");
            return (Criteria) this;
        }

        public Criteria andItemParentIdNotBetween(String value1, String value2) {
            addCriterion("item_parent_id not between", value1, value2, "itemParentId");
            return (Criteria) this;
        }

        public Criteria andItemTypeIsNull() {
            addCriterion("item_type is null");
            return (Criteria) this;
        }

        public Criteria andItemTypeIsNotNull() {
            addCriterion("item_type is not null");
            return (Criteria) this;
        }

        public Criteria andItemTypeEqualTo(String value) {
            addCriterion("item_type =", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeNotEqualTo(String value) {
            addCriterion("item_type <>", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeGreaterThan(String value) {
            addCriterion("item_type >", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeGreaterThanOrEqualTo(String value) {
            addCriterion("item_type >=", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeLessThan(String value) {
            addCriterion("item_type <", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeLessThanOrEqualTo(String value) {
            addCriterion("item_type <=", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeLike(String value) {
            addCriterion("item_type like", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeNotLike(String value) {
            addCriterion("item_type not like", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeIn(List<String> values) {
            addCriterion("item_type in", values, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeNotIn(List<String> values) {
            addCriterion("item_type not in", values, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeBetween(String value1, String value2) {
            addCriterion("item_type between", value1, value2, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeNotBetween(String value1, String value2) {
            addCriterion("item_type not between", value1, value2, "itemType");
            return (Criteria) this;
        }

        public Criteria andSiteIdIsNull() {
            addCriterion("site_id is null");
            return (Criteria) this;
        }

        public Criteria andSiteIdIsNotNull() {
            addCriterion("site_id is not null");
            return (Criteria) this;
        }

        public Criteria andSiteIdEqualTo(String value) {
            addCriterion("site_id =", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotEqualTo(String value) {
            addCriterion("site_id <>", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdGreaterThan(String value) {
            addCriterion("site_id >", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdGreaterThanOrEqualTo(String value) {
            addCriterion("site_id >=", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLessThan(String value) {
            addCriterion("site_id <", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLessThanOrEqualTo(String value) {
            addCriterion("site_id <=", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLike(String value) {
            addCriterion("site_id like", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotLike(String value) {
            addCriterion("site_id not like", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdIn(List<String> values) {
            addCriterion("site_id in", values, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotIn(List<String> values) {
            addCriterion("site_id not in", values, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdBetween(String value1, String value2) {
            addCriterion("site_id between", value1, value2, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotBetween(String value1, String value2) {
            addCriterion("site_id not between", value1, value2, "siteId");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table public_data_dict
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
     * This class corresponds to the database table public_data_dict
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