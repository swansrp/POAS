package com.srct.ril.poas.dao.pojo;

public class StoreTB {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB.username
     *
     * @mbggenerated
     */
    private String username;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB.date
     *
     * @mbggenerated
     */
    private String date;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB.appenddate
     *
     * @mbggenerated
     */
    private String appenddate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB.referenceName
     *
     * @mbggenerated
     */
    private String referencename;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB.id
     *
     * @return the value of TB.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB.id
     *
     * @param id the value for TB.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB.username
     *
     * @return the value of TB.username
     *
     * @mbggenerated
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB.username
     *
     * @param username the value for TB.username
     *
     * @mbggenerated
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB.date
     *
     * @return the value of TB.date
     *
     * @mbggenerated
     */
    public String getDate() {
        return date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB.date
     *
     * @param date the value for TB.date
     *
     * @mbggenerated
     */
    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB.appenddate
     *
     * @return the value of TB.appenddate
     *
     * @mbggenerated
     */
    public String getAppenddate() {
        return appenddate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB.appenddate
     *
     * @param appenddate the value for TB.appenddate
     *
     * @mbggenerated
     */
    public void setAppenddate(String appenddate) {
        this.appenddate = appenddate == null ? null : appenddate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB.referenceName
     *
     * @return the value of TB.referenceName
     *
     * @mbggenerated
     */
    public String getReferencename() {
        return referencename;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB.referenceName
     *
     * @param referencename the value for TB.referenceName
     *
     * @mbggenerated
     */
    public void setReferencename(String referencename) {
        this.referencename = referencename == null ? null : referencename.trim();
    }
}