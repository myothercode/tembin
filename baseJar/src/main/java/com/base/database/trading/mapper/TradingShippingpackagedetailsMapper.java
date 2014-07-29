package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingShippingpackagedetails;
import com.base.database.trading.model.TradingShippingpackagedetailsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingShippingpackagedetailsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingpackagedetails
     *
     * @mbggenerated
     */
    int countByExample(TradingShippingpackagedetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingpackagedetails
     *
     * @mbggenerated
     */
    int deleteByExample(TradingShippingpackagedetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingpackagedetails
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingpackagedetails
     *
     * @mbggenerated
     */
    int insert(TradingShippingpackagedetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingpackagedetails
     *
     * @mbggenerated
     */
    int insertSelective(TradingShippingpackagedetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingpackagedetails
     *
     * @mbggenerated
     */
    List<TradingShippingpackagedetails> selectByExample(TradingShippingpackagedetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingpackagedetails
     *
     * @mbggenerated
     */
    TradingShippingpackagedetails selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingpackagedetails
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingShippingpackagedetails record, @Param("example") TradingShippingpackagedetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingpackagedetails
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingShippingpackagedetails record, @Param("example") TradingShippingpackagedetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingpackagedetails
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingShippingpackagedetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingpackagedetails
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingShippingpackagedetails record);
}