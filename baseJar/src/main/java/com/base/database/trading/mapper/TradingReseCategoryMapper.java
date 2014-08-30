package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingReseCategory;
import com.base.database.trading.model.TradingReseCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingReseCategoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_rese_category
     *
     * @mbggenerated
     */
    int countByExample(TradingReseCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_rese_category
     *
     * @mbggenerated
     */
    int deleteByExample(TradingReseCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_rese_category
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_rese_category
     *
     * @mbggenerated
     */
    int insert(TradingReseCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_rese_category
     *
     * @mbggenerated
     */
    int insertSelective(TradingReseCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_rese_category
     *
     * @mbggenerated
     */
    List<TradingReseCategory> selectByExample(TradingReseCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_rese_category
     *
     * @mbggenerated
     */
    TradingReseCategory selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_rese_category
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingReseCategory record, @Param("example") TradingReseCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_rese_category
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingReseCategory record, @Param("example") TradingReseCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_rese_category
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingReseCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_rese_category
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingReseCategory record);
}