package com.orchard.ehow.mapper;

import com.orchard.ehow.dao.EnrollEnrollsinfo;
import com.orchard.ehow.dao.EnrollEnrollsinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EnrollEnrollsinfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enroll_enrollsinfo
     *
     * @mbg.generated
     */
    long countByExample(EnrollEnrollsinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enroll_enrollsinfo
     *
     * @mbg.generated
     */
    int deleteByExample(EnrollEnrollsinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enroll_enrollsinfo
     *
     * @mbg.generated
     */
    int insert(EnrollEnrollsinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enroll_enrollsinfo
     *
     * @mbg.generated
     */
    int insertSelective(EnrollEnrollsinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enroll_enrollsinfo
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    EnrollEnrollsinfo selectOneByExample(EnrollEnrollsinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enroll_enrollsinfo
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    EnrollEnrollsinfo selectOneByExampleSelective(@Param("example") EnrollEnrollsinfoExample example, @Param("selective") EnrollEnrollsinfo.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enroll_enrollsinfo
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<EnrollEnrollsinfo> selectByExampleSelective(@Param("example") EnrollEnrollsinfoExample example, @Param("selective") EnrollEnrollsinfo.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enroll_enrollsinfo
     *
     * @mbg.generated
     */
    List<EnrollEnrollsinfo> selectByExample(EnrollEnrollsinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enroll_enrollsinfo
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") EnrollEnrollsinfo record, @Param("example") EnrollEnrollsinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enroll_enrollsinfo
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") EnrollEnrollsinfo record, @Param("example") EnrollEnrollsinfoExample example);
}