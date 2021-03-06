package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingVatdetails;
import com.base.database.trading.model.TradingVatdetailsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingVatdetailsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    int countByExample(TradingVatdetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    int deleteByExample(TradingVatdetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    int insert(TradingVatdetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    int insertSelective(TradingVatdetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    List<TradingVatdetails> selectByExample(TradingVatdetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    TradingVatdetails selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingVatdetails record, @Param("example") TradingVatdetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingVatdetails record, @Param("example") TradingVatdetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingVatdetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_vatdetails
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingVatdetails record);
}