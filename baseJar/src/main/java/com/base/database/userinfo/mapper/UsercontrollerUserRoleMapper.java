package com.base.database.userinfo.mapper;

import com.base.database.userinfo.model.UsercontrollerUserRole;
import com.base.database.userinfo.model.UsercontrollerUserRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsercontrollerUserRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_role
     *
     * @mbggenerated
     */
    int countByExample(UsercontrollerUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_role
     *
     * @mbggenerated
     */
    int deleteByExample(UsercontrollerUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_role
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer userRoleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_role
     *
     * @mbggenerated
     */
    int insert(UsercontrollerUserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_role
     *
     * @mbggenerated
     */
    int insertSelective(UsercontrollerUserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_role
     *
     * @mbggenerated
     */
    List<UsercontrollerUserRole> selectByExample(UsercontrollerUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_role
     *
     * @mbggenerated
     */
    UsercontrollerUserRole selectByPrimaryKey(Integer userRoleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_role
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") UsercontrollerUserRole record, @Param("example") UsercontrollerUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_role
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") UsercontrollerUserRole record, @Param("example") UsercontrollerUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_role
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UsercontrollerUserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_role
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UsercontrollerUserRole record);
}