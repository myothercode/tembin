package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingTempTypeKey;
import com.base.database.trading.model.TradingTempTypeKeyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingTempTypeKeyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_temp_type_key
     *
     * @mbggenerated
     */
    int countByExample(TradingTempTypeKeyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_temp_type_key
     *
     * @mbggenerated
     */
    int deleteByExample(TradingTempTypeKeyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_temp_type_key
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_temp_type_key
     *
     * @mbggenerated
     */
    int insert(TradingTempTypeKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_temp_type_key
     *
     * @mbggenerated
     */
    int insertSelective(TradingTempTypeKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_temp_type_key
     *
     * @mbggenerated
     */
    List<TradingTempTypeKey> selectByExample(TradingTempTypeKeyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_temp_type_key
     *
     * @mbggenerated
     */
    TradingTempTypeKey selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_temp_type_key
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingTempTypeKey record, @Param("example") TradingTempTypeKeyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_temp_type_key
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingTempTypeKey record, @Param("example") TradingTempTypeKeyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_temp_type_key
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingTempTypeKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_temp_type_key
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingTempTypeKey record);
}