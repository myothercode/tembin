package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingAddItem;
import com.base.database.trading.model.TradingAddItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingAddItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_add_item
     *
     * @mbggenerated
     */
    int countByExample(TradingAddItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_add_item
     *
     * @mbggenerated
     */
    int deleteByExample(TradingAddItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_add_item
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_add_item
     *
     * @mbggenerated
     */
    int insert(TradingAddItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_add_item
     *
     * @mbggenerated
     */
    int insertSelective(TradingAddItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_add_item
     *
     * @mbggenerated
     */
    List<TradingAddItem> selectByExample(TradingAddItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_add_item
     *
     * @mbggenerated
     */
    TradingAddItem selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_add_item
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingAddItem record, @Param("example") TradingAddItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_add_item
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingAddItem record, @Param("example") TradingAddItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_add_item
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingAddItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_add_item
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingAddItem record);
}