package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingAutoMessageAttr;
import com.base.database.trading.model.TradingAutoMessageAttrExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingAutoMessageAttrMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message_attr
     *
     * @mbggenerated
     */
    int countByExample(TradingAutoMessageAttrExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message_attr
     *
     * @mbggenerated
     */
    int deleteByExample(TradingAutoMessageAttrExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message_attr
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message_attr
     *
     * @mbggenerated
     */
    int insert(TradingAutoMessageAttr record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message_attr
     *
     * @mbggenerated
     */
    int insertSelective(TradingAutoMessageAttr record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message_attr
     *
     * @mbggenerated
     */
    List<TradingAutoMessageAttr> selectByExample(TradingAutoMessageAttrExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message_attr
     *
     * @mbggenerated
     */
    TradingAutoMessageAttr selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message_attr
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingAutoMessageAttr record, @Param("example") TradingAutoMessageAttrExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message_attr
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingAutoMessageAttr record, @Param("example") TradingAutoMessageAttrExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message_attr
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingAutoMessageAttr record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message_attr
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingAutoMessageAttr record);
}