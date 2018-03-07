package com.srct.ril.poas.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.srct.ril.poas.dao.pojo.DaoTM;
import com.srct.ril.poas.dao.pojo.DaoTMExample;

public interface DaoTMMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TM
     *
     * @mbggenerated
     */
    int countByExample(DaoTMExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TM
     *
     * @mbggenerated
     */
    int deleteByExample(DaoTMExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TM
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TM
     *
     * @mbggenerated
     */
    int insert(DaoTM record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TM
     *
     * @mbggenerated
     */
    int insertSelective(DaoTM record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TM
     *
     * @mbggenerated
     */
    List<DaoTM> selectByExample(DaoTMExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TM
     *
     * @mbggenerated
     */
    DaoTM selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TM
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") DaoTM record, @Param("example") DaoTMExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TM
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") DaoTM record, @Param("example") DaoTMExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TM
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(DaoTM record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TM
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(DaoTM record);
}