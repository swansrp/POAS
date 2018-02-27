package com.srct.ril.poas.dao.pojo;

import java.util.ArrayList;
import java.util.List;

public class StoreTBExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB
     *
     * @mbggenerated
     */
    public StoreTBExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB
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
     * This method corresponds to the database table TB
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
     * This method corresponds to the database table TB
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB
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
     * This class corresponds to the database table TB
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andFirstcommentIsNull() {
            addCriterion("firstcomment is null");
            return (Criteria) this;
        }

        public Criteria andFirstcommentIsNotNull() {
            addCriterion("firstcomment is not null");
            return (Criteria) this;
        }

        public Criteria andFirstcommentEqualTo(String value) {
            addCriterion("firstcomment =", value, "firstcomment");
            return (Criteria) this;
        }

        public Criteria andFirstcommentNotEqualTo(String value) {
            addCriterion("firstcomment <>", value, "firstcomment");
            return (Criteria) this;
        }

        public Criteria andFirstcommentGreaterThan(String value) {
            addCriterion("firstcomment >", value, "firstcomment");
            return (Criteria) this;
        }

        public Criteria andFirstcommentGreaterThanOrEqualTo(String value) {
            addCriterion("firstcomment >=", value, "firstcomment");
            return (Criteria) this;
        }

        public Criteria andFirstcommentLessThan(String value) {
            addCriterion("firstcomment <", value, "firstcomment");
            return (Criteria) this;
        }

        public Criteria andFirstcommentLessThanOrEqualTo(String value) {
            addCriterion("firstcomment <=", value, "firstcomment");
            return (Criteria) this;
        }

        public Criteria andFirstcommentLike(String value) {
            addCriterion("firstcomment like", value, "firstcomment");
            return (Criteria) this;
        }

        public Criteria andFirstcommentNotLike(String value) {
            addCriterion("firstcomment not like", value, "firstcomment");
            return (Criteria) this;
        }

        public Criteria andFirstcommentIn(List<String> values) {
            addCriterion("firstcomment in", values, "firstcomment");
            return (Criteria) this;
        }

        public Criteria andFirstcommentNotIn(List<String> values) {
            addCriterion("firstcomment not in", values, "firstcomment");
            return (Criteria) this;
        }

        public Criteria andFirstcommentBetween(String value1, String value2) {
            addCriterion("firstcomment between", value1, value2, "firstcomment");
            return (Criteria) this;
        }

        public Criteria andFirstcommentNotBetween(String value1, String value2) {
            addCriterion("firstcomment not between", value1, value2, "firstcomment");
            return (Criteria) this;
        }

        public Criteria andDateIsNull() {
            addCriterion("date is null");
            return (Criteria) this;
        }

        public Criteria andDateIsNotNull() {
            addCriterion("date is not null");
            return (Criteria) this;
        }

        public Criteria andDateEqualTo(String value) {
            addCriterion("date =", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotEqualTo(String value) {
            addCriterion("date <>", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThan(String value) {
            addCriterion("date >", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThanOrEqualTo(String value) {
            addCriterion("date >=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThan(String value) {
            addCriterion("date <", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThanOrEqualTo(String value) {
            addCriterion("date <=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLike(String value) {
            addCriterion("date like", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotLike(String value) {
            addCriterion("date not like", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateIn(List<String> values) {
            addCriterion("date in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotIn(List<String> values) {
            addCriterion("date not in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateBetween(String value1, String value2) {
            addCriterion("date between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotBetween(String value1, String value2) {
            addCriterion("date not between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andAppendcommentIsNull() {
            addCriterion("appendComment is null");
            return (Criteria) this;
        }

        public Criteria andAppendcommentIsNotNull() {
            addCriterion("appendComment is not null");
            return (Criteria) this;
        }

        public Criteria andAppendcommentEqualTo(String value) {
            addCriterion("appendComment =", value, "appendcomment");
            return (Criteria) this;
        }

        public Criteria andAppendcommentNotEqualTo(String value) {
            addCriterion("appendComment <>", value, "appendcomment");
            return (Criteria) this;
        }

        public Criteria andAppendcommentGreaterThan(String value) {
            addCriterion("appendComment >", value, "appendcomment");
            return (Criteria) this;
        }

        public Criteria andAppendcommentGreaterThanOrEqualTo(String value) {
            addCriterion("appendComment >=", value, "appendcomment");
            return (Criteria) this;
        }

        public Criteria andAppendcommentLessThan(String value) {
            addCriterion("appendComment <", value, "appendcomment");
            return (Criteria) this;
        }

        public Criteria andAppendcommentLessThanOrEqualTo(String value) {
            addCriterion("appendComment <=", value, "appendcomment");
            return (Criteria) this;
        }

        public Criteria andAppendcommentLike(String value) {
            addCriterion("appendComment like", value, "appendcomment");
            return (Criteria) this;
        }

        public Criteria andAppendcommentNotLike(String value) {
            addCriterion("appendComment not like", value, "appendcomment");
            return (Criteria) this;
        }

        public Criteria andAppendcommentIn(List<String> values) {
            addCriterion("appendComment in", values, "appendcomment");
            return (Criteria) this;
        }

        public Criteria andAppendcommentNotIn(List<String> values) {
            addCriterion("appendComment not in", values, "appendcomment");
            return (Criteria) this;
        }

        public Criteria andAppendcommentBetween(String value1, String value2) {
            addCriterion("appendComment between", value1, value2, "appendcomment");
            return (Criteria) this;
        }

        public Criteria andAppendcommentNotBetween(String value1, String value2) {
            addCriterion("appendComment not between", value1, value2, "appendcomment");
            return (Criteria) this;
        }

        public Criteria andAppenddateIsNull() {
            addCriterion("appenddate is null");
            return (Criteria) this;
        }

        public Criteria andAppenddateIsNotNull() {
            addCriterion("appenddate is not null");
            return (Criteria) this;
        }

        public Criteria andAppenddateEqualTo(String value) {
            addCriterion("appenddate =", value, "appenddate");
            return (Criteria) this;
        }

        public Criteria andAppenddateNotEqualTo(String value) {
            addCriterion("appenddate <>", value, "appenddate");
            return (Criteria) this;
        }

        public Criteria andAppenddateGreaterThan(String value) {
            addCriterion("appenddate >", value, "appenddate");
            return (Criteria) this;
        }

        public Criteria andAppenddateGreaterThanOrEqualTo(String value) {
            addCriterion("appenddate >=", value, "appenddate");
            return (Criteria) this;
        }

        public Criteria andAppenddateLessThan(String value) {
            addCriterion("appenddate <", value, "appenddate");
            return (Criteria) this;
        }

        public Criteria andAppenddateLessThanOrEqualTo(String value) {
            addCriterion("appenddate <=", value, "appenddate");
            return (Criteria) this;
        }

        public Criteria andAppenddateLike(String value) {
            addCriterion("appenddate like", value, "appenddate");
            return (Criteria) this;
        }

        public Criteria andAppenddateNotLike(String value) {
            addCriterion("appenddate not like", value, "appenddate");
            return (Criteria) this;
        }

        public Criteria andAppenddateIn(List<String> values) {
            addCriterion("appenddate in", values, "appenddate");
            return (Criteria) this;
        }

        public Criteria andAppenddateNotIn(List<String> values) {
            addCriterion("appenddate not in", values, "appenddate");
            return (Criteria) this;
        }

        public Criteria andAppenddateBetween(String value1, String value2) {
            addCriterion("appenddate between", value1, value2, "appenddate");
            return (Criteria) this;
        }

        public Criteria andAppenddateNotBetween(String value1, String value2) {
            addCriterion("appenddate not between", value1, value2, "appenddate");
            return (Criteria) this;
        }

        public Criteria andReplyIsNull() {
            addCriterion("reply is null");
            return (Criteria) this;
        }

        public Criteria andReplyIsNotNull() {
            addCriterion("reply is not null");
            return (Criteria) this;
        }

        public Criteria andReplyEqualTo(String value) {
            addCriterion("reply =", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyNotEqualTo(String value) {
            addCriterion("reply <>", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyGreaterThan(String value) {
            addCriterion("reply >", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyGreaterThanOrEqualTo(String value) {
            addCriterion("reply >=", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyLessThan(String value) {
            addCriterion("reply <", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyLessThanOrEqualTo(String value) {
            addCriterion("reply <=", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyLike(String value) {
            addCriterion("reply like", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyNotLike(String value) {
            addCriterion("reply not like", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyIn(List<String> values) {
            addCriterion("reply in", values, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyNotIn(List<String> values) {
            addCriterion("reply not in", values, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyBetween(String value1, String value2) {
            addCriterion("reply between", value1, value2, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyNotBetween(String value1, String value2) {
            addCriterion("reply not between", value1, value2, "reply");
            return (Criteria) this;
        }

        public Criteria andReferencenameIsNull() {
            addCriterion("referenceName is null");
            return (Criteria) this;
        }

        public Criteria andReferencenameIsNotNull() {
            addCriterion("referenceName is not null");
            return (Criteria) this;
        }

        public Criteria andReferencenameEqualTo(String value) {
            addCriterion("referenceName =", value, "referencename");
            return (Criteria) this;
        }

        public Criteria andReferencenameNotEqualTo(String value) {
            addCriterion("referenceName <>", value, "referencename");
            return (Criteria) this;
        }

        public Criteria andReferencenameGreaterThan(String value) {
            addCriterion("referenceName >", value, "referencename");
            return (Criteria) this;
        }

        public Criteria andReferencenameGreaterThanOrEqualTo(String value) {
            addCriterion("referenceName >=", value, "referencename");
            return (Criteria) this;
        }

        public Criteria andReferencenameLessThan(String value) {
            addCriterion("referenceName <", value, "referencename");
            return (Criteria) this;
        }

        public Criteria andReferencenameLessThanOrEqualTo(String value) {
            addCriterion("referenceName <=", value, "referencename");
            return (Criteria) this;
        }

        public Criteria andReferencenameLike(String value) {
            addCriterion("referenceName like", value, "referencename");
            return (Criteria) this;
        }

        public Criteria andReferencenameNotLike(String value) {
            addCriterion("referenceName not like", value, "referencename");
            return (Criteria) this;
        }

        public Criteria andReferencenameIn(List<String> values) {
            addCriterion("referenceName in", values, "referencename");
            return (Criteria) this;
        }

        public Criteria andReferencenameNotIn(List<String> values) {
            addCriterion("referenceName not in", values, "referencename");
            return (Criteria) this;
        }

        public Criteria andReferencenameBetween(String value1, String value2) {
            addCriterion("referenceName between", value1, value2, "referencename");
            return (Criteria) this;
        }

        public Criteria andReferencenameNotBetween(String value1, String value2) {
            addCriterion("referenceName not between", value1, value2, "referencename");
            return (Criteria) this;
        }

        public Criteria andLinkIsNull() {
            addCriterion("link is null");
            return (Criteria) this;
        }

        public Criteria andLinkIsNotNull() {
            addCriterion("link is not null");
            return (Criteria) this;
        }

        public Criteria andLinkEqualTo(String value) {
            addCriterion("link =", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotEqualTo(String value) {
            addCriterion("link <>", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkGreaterThan(String value) {
            addCriterion("link >", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkGreaterThanOrEqualTo(String value) {
            addCriterion("link >=", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkLessThan(String value) {
            addCriterion("link <", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkLessThanOrEqualTo(String value) {
            addCriterion("link <=", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkLike(String value) {
            addCriterion("link like", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotLike(String value) {
            addCriterion("link not like", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkIn(List<String> values) {
            addCriterion("link in", values, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotIn(List<String> values) {
            addCriterion("link not in", values, "link");
            return (Criteria) this;
        }

        public Criteria andLinkBetween(String value1, String value2) {
            addCriterion("link between", value1, value2, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotBetween(String value1, String value2) {
            addCriterion("link not between", value1, value2, "link");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB
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
     * This class corresponds to the database table TB
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