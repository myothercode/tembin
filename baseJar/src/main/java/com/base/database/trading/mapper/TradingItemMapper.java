package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingItem;
import com.base.database.trading.model.TradingItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_item
     *
     * @mbggenerated
     */
    int countByExample(TradingItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_item
     *
     * @mbggenerated
     */
    int deleteByExample(TradingItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_item
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_item
     *
     * @mbggenerated
     */
    int insert(TradingItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_item
     *
     * @mbggenerated
     */
    int insertSelective(TradingItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_item
     *
     * @mbggenerated
     */
    List<TradingItem> selectByExampleWithBLOBs(TradingItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_item
     *
     * @mbggenerated
     */
    List<TradingItem> selectByExample(TradingItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_item
     *
     * @mbggenerated
     */
    TradingItem selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_item
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingItem record, @Param("example") TradingItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_item
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") TradingItem record, @Param("example") TradingItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_item
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingItem record, @Param("example") TradingItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_item
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_item
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(TradingItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_item
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingItem record);
}