package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingOrderShippingServiceOptions;
import com.base.database.trading.model.TradingOrderShippingServiceOptionsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingOrderShippingServiceOptionsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    int countByExample(TradingOrderShippingServiceOptionsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    int deleteByExample(TradingOrderShippingServiceOptionsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    int insert(TradingOrderShippingServiceOptions record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    int insertSelective(TradingOrderShippingServiceOptions record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    List<TradingOrderShippingServiceOptions> selectByExample(TradingOrderShippingServiceOptionsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    TradingOrderShippingServiceOptions selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingOrderShippingServiceOptions record, @Param("example") TradingOrderShippingServiceOptionsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingOrderShippingServiceOptions record, @Param("example") TradingOrderShippingServiceOptionsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingOrderShippingServiceOptions record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingserviceoptions
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingOrderShippingServiceOptions record);
}