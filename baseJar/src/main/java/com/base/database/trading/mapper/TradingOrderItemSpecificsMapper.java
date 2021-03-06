package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingOrderItemSpecifics;
import com.base.database.trading.model.TradingOrderItemSpecificsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingOrderItemSpecificsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_itemspecifics
     *
     * @mbggenerated
     */
    int countByExample(TradingOrderItemSpecificsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_itemspecifics
     *
     * @mbggenerated
     */
    int deleteByExample(TradingOrderItemSpecificsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_itemspecifics
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_itemspecifics
     *
     * @mbggenerated
     */
    int insert(TradingOrderItemSpecifics record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_itemspecifics
     *
     * @mbggenerated
     */
    int insertSelective(TradingOrderItemSpecifics record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_itemspecifics
     *
     * @mbggenerated
     */
    List<TradingOrderItemSpecifics> selectByExample(TradingOrderItemSpecificsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_itemspecifics
     *
     * @mbggenerated
     */
    TradingOrderItemSpecifics selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_itemspecifics
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingOrderItemSpecifics record, @Param("example") TradingOrderItemSpecificsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_itemspecifics
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingOrderItemSpecifics record, @Param("example") TradingOrderItemSpecificsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_itemspecifics
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingOrderItemSpecifics record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_itemspecifics
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingOrderItemSpecifics record);
}