package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.database.trading.model.TradingOrderGetOrdersExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TradingOrderGetOrdersMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_get_orders
     *
     * @mbggenerated
     */
    int countByExample(TradingOrderGetOrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_get_orders
     *
     * @mbggenerated
     */
    int deleteByExample(TradingOrderGetOrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_get_orders
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_get_orders
     *
     * @mbggenerated
     */
    int insert(TradingOrderGetOrders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_get_orders
     *
     * @mbggenerated
     */
    int insertSelective(TradingOrderGetOrders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_get_orders
     *
     * @mbggenerated
     */
    List<TradingOrderGetOrders> selectByExample(TradingOrderGetOrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_get_orders
     *
     * @mbggenerated
     */
    TradingOrderGetOrders selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_get_orders
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingOrderGetOrders record, @Param("example") TradingOrderGetOrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_get_orders
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingOrderGetOrders record, @Param("example") TradingOrderGetOrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_get_orders
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingOrderGetOrders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_get_orders
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingOrderGetOrders record);
}