package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingGetUserCases;
import com.base.database.trading.model.TradingGetUserCasesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingGetUserCasesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_get_user_cases
     *
     * @mbggenerated
     */
    int countByExample(TradingGetUserCasesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_get_user_cases
     *
     * @mbggenerated
     */
    int deleteByExample(TradingGetUserCasesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_get_user_cases
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_get_user_cases
     *
     * @mbggenerated
     */
    int insert(TradingGetUserCases record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_get_user_cases
     *
     * @mbggenerated
     */
    int insertSelective(TradingGetUserCases record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_get_user_cases
     *
     * @mbggenerated
     */
    List<TradingGetUserCases> selectByExample(TradingGetUserCasesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_get_user_cases
     *
     * @mbggenerated
     */
    TradingGetUserCases selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_get_user_cases
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingGetUserCases record, @Param("example") TradingGetUserCasesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_get_user_cases
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingGetUserCases record, @Param("example") TradingGetUserCasesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_get_user_cases
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingGetUserCases record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_get_user_cases
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingGetUserCases record);
}