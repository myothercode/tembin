package com.base.database.userinfo.mapper;

import com.base.database.userinfo.model.UsercontrollerUser;
import com.base.database.userinfo.model.UsercontrollerUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsercontrollerUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user
     *
     * @mbggenerated
     */
    int countByExample(UsercontrollerUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user
     *
     * @mbggenerated
     */
    int deleteByExample(UsercontrollerUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user
     *
     * @mbggenerated
     */
    int insert(UsercontrollerUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user
     *
     * @mbggenerated
     */
    int insertSelective(UsercontrollerUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user
     *
     * @mbggenerated
     */
    List<UsercontrollerUser> selectByExample(UsercontrollerUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user
     *
     * @mbggenerated
     */
    UsercontrollerUser selectByPrimaryKey(Integer userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") UsercontrollerUser record, @Param("example") UsercontrollerUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") UsercontrollerUser record, @Param("example") UsercontrollerUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UsercontrollerUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UsercontrollerUser record);
}