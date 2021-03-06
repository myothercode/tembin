package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingPriceTracking;
import com.base.database.trading.model.TradingPriceTrackingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingPriceTrackingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_price_tracking
     *
     * @mbggenerated
     */
    int countByExample(TradingPriceTrackingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_price_tracking
     *
     * @mbggenerated
     */
    int deleteByExample(TradingPriceTrackingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_price_tracking
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_price_tracking
     *
     * @mbggenerated
     */
    int insert(TradingPriceTracking record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_price_tracking
     *
     * @mbggenerated
     */
    int insertSelective(TradingPriceTracking record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_price_tracking
     *
     * @mbggenerated
     */
    List<TradingPriceTracking> selectByExample(TradingPriceTrackingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_price_tracking
     *
     * @mbggenerated
     */
    TradingPriceTracking selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_price_tracking
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingPriceTracking record, @Param("example") TradingPriceTrackingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_price_tracking
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingPriceTracking record, @Param("example") TradingPriceTrackingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_price_tracking
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingPriceTracking record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_price_tracking
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingPriceTracking record);
}