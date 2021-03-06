package com.base.database.userinfo.mapper;

import com.base.database.userinfo.model.UsercontrollerUserEbay;
import com.base.database.userinfo.model.UsercontrollerUserEbayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsercontrollerUserEbayMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_ebay
     *
     * @mbggenerated
     */
    int countByExample(UsercontrollerUserEbayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_ebay
     *
     * @mbggenerated
     */
    int deleteByExample(UsercontrollerUserEbayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_ebay
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long userEbayId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_ebay
     *
     * @mbggenerated
     */
    int insert(UsercontrollerUserEbay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_ebay
     *
     * @mbggenerated
     */
    int insertSelective(UsercontrollerUserEbay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_ebay
     *
     * @mbggenerated
     */
    List<UsercontrollerUserEbay> selectByExample(UsercontrollerUserEbayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_ebay
     *
     * @mbggenerated
     */
    UsercontrollerUserEbay selectByPrimaryKey(Long userEbayId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_ebay
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") UsercontrollerUserEbay record, @Param("example") UsercontrollerUserEbayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_ebay
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") UsercontrollerUserEbay record, @Param("example") UsercontrollerUserEbayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_ebay
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UsercontrollerUserEbay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_user_ebay
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UsercontrollerUserEbay record);
}