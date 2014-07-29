package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingDescriptionDetails;
import com.base.database.trading.model.TradingDescriptionDetailsExample;
import com.base.database.trading.model.TradingDescriptionDetailsWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingDescriptionDetailsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    int countByExample(TradingDescriptionDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    int deleteByExample(TradingDescriptionDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    int insert(TradingDescriptionDetailsWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    int insertSelective(TradingDescriptionDetailsWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    List<TradingDescriptionDetailsWithBLOBs> selectByExampleWithBLOBs(TradingDescriptionDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    List<TradingDescriptionDetails> selectByExample(TradingDescriptionDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    TradingDescriptionDetailsWithBLOBs selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingDescriptionDetailsWithBLOBs record, @Param("example") TradingDescriptionDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") TradingDescriptionDetailsWithBLOBs record, @Param("example") TradingDescriptionDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingDescriptionDetails record, @Param("example") TradingDescriptionDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingDescriptionDetailsWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(TradingDescriptionDetailsWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_description_details
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingDescriptionDetails record);
}