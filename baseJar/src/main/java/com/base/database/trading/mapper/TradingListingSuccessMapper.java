package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingListingSuccess;
import com.base.database.trading.model.TradingListingSuccessExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingListingSuccessMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_success
     *
     * @mbggenerated
     */
    int countByExample(TradingListingSuccessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_success
     *
     * @mbggenerated
     */
    int deleteByExample(TradingListingSuccessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_success
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_success
     *
     * @mbggenerated
     */
    int insert(TradingListingSuccess record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_success
     *
     * @mbggenerated
     */
    int insertSelective(TradingListingSuccess record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_success
     *
     * @mbggenerated
     */
    List<TradingListingSuccess> selectByExample(TradingListingSuccessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_success
     *
     * @mbggenerated
     */
    TradingListingSuccess selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_success
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingListingSuccess record, @Param("example") TradingListingSuccessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_success
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingListingSuccess record, @Param("example") TradingListingSuccessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_success
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingListingSuccess record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_success
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingListingSuccess record);
}