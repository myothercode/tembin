package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingVariations;
import com.base.database.trading.model.TradingVariationsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingVariationsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variations
     *
     * @mbggenerated
     */
    int countByExample(TradingVariationsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variations
     *
     * @mbggenerated
     */
    int deleteByExample(TradingVariationsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variations
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variations
     *
     * @mbggenerated
     */
    int insert(TradingVariations record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variations
     *
     * @mbggenerated
     */
    int insertSelective(TradingVariations record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variations
     *
     * @mbggenerated
     */
    List<TradingVariations> selectByExample(TradingVariationsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variations
     *
     * @mbggenerated
     */
    TradingVariations selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variations
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingVariations record, @Param("example") TradingVariationsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variations
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingVariations record, @Param("example") TradingVariationsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variations
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingVariations record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variations
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingVariations record);
}