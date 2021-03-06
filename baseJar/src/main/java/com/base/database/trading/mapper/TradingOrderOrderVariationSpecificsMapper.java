package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingOrderOrderVariationSpecifics;
import com.base.database.trading.model.TradingOrderOrderVariationSpecificsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingOrderOrderVariationSpecificsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_order_variationspecifics
     *
     * @mbggenerated
     */
    int countByExample(TradingOrderOrderVariationSpecificsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_order_variationspecifics
     *
     * @mbggenerated
     */
    int deleteByExample(TradingOrderOrderVariationSpecificsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_order_variationspecifics
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_order_variationspecifics
     *
     * @mbggenerated
     */
    int insert(TradingOrderOrderVariationSpecifics record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_order_variationspecifics
     *
     * @mbggenerated
     */
    int insertSelective(TradingOrderOrderVariationSpecifics record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_order_variationspecifics
     *
     * @mbggenerated
     */
    List<TradingOrderOrderVariationSpecifics> selectByExample(TradingOrderOrderVariationSpecificsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_order_variationspecifics
     *
     * @mbggenerated
     */
    TradingOrderOrderVariationSpecifics selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_order_variationspecifics
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingOrderOrderVariationSpecifics record, @Param("example") TradingOrderOrderVariationSpecificsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_order_variationspecifics
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingOrderOrderVariationSpecifics record, @Param("example") TradingOrderOrderVariationSpecificsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_order_variationspecifics
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingOrderOrderVariationSpecifics record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_order_variationspecifics
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingOrderOrderVariationSpecifics record);
}