package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingOrderListingDetails;
import com.base.database.trading.model.TradingOrderListingDetailsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingOrderListingDetailsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_listing_details
     *
     * @mbggenerated
     */
    int countByExample(TradingOrderListingDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_listing_details
     *
     * @mbggenerated
     */
    int deleteByExample(TradingOrderListingDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_listing_details
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_listing_details
     *
     * @mbggenerated
     */
    int insert(TradingOrderListingDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_listing_details
     *
     * @mbggenerated
     */
    int insertSelective(TradingOrderListingDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_listing_details
     *
     * @mbggenerated
     */
    List<TradingOrderListingDetails> selectByExample(TradingOrderListingDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_listing_details
     *
     * @mbggenerated
     */
    TradingOrderListingDetails selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_listing_details
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingOrderListingDetails record, @Param("example") TradingOrderListingDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_listing_details
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingOrderListingDetails record, @Param("example") TradingOrderListingDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_listing_details
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingOrderListingDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_listing_details
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingOrderListingDetails record);
}