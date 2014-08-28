package com.base.database.trading.mapper;

import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.database.trading.model.UsercontrollerEbayAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsercontrollerEbayAccountMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_ebay_account
     *
     * @mbggenerated
     */
    int countByExample(UsercontrollerEbayAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_ebay_account
     *
     * @mbggenerated
     */
    int deleteByExample(UsercontrollerEbayAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_ebay_account
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_ebay_account
     *
     * @mbggenerated
     */
    int insert(UsercontrollerEbayAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_ebay_account
     *
     * @mbggenerated
     */
    int insertSelective(UsercontrollerEbayAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_ebay_account
     *
     * @mbggenerated
     */
    List<UsercontrollerEbayAccount> selectByExampleWithBLOBs(UsercontrollerEbayAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_ebay_account
     *
     * @mbggenerated
     */
    List<UsercontrollerEbayAccount> selectByExample(UsercontrollerEbayAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_ebay_account
     *
     * @mbggenerated
     */
    UsercontrollerEbayAccount selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_ebay_account
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") UsercontrollerEbayAccount record, @Param("example") UsercontrollerEbayAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_ebay_account
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") UsercontrollerEbayAccount record, @Param("example") UsercontrollerEbayAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_ebay_account
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") UsercontrollerEbayAccount record, @Param("example") UsercontrollerEbayAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_ebay_account
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UsercontrollerEbayAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_ebay_account
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(UsercontrollerEbayAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_ebay_account
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UsercontrollerEbayAccount record);
}