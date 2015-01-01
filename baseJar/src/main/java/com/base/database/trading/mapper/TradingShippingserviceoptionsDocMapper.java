package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingShippingserviceoptionsDoc;
import com.base.database.trading.model.TradingShippingserviceoptionsDocExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingShippingserviceoptionsDocMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingserviceoptions_doc
     *
     * @mbggenerated
     */
    int countByExample(TradingShippingserviceoptionsDocExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingserviceoptions_doc
     *
     * @mbggenerated
     */
    int deleteByExample(TradingShippingserviceoptionsDocExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingserviceoptions_doc
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingserviceoptions_doc
     *
     * @mbggenerated
     */
    int insert(TradingShippingserviceoptionsDoc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingserviceoptions_doc
     *
     * @mbggenerated
     */
    int insertSelective(TradingShippingserviceoptionsDoc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingserviceoptions_doc
     *
     * @mbggenerated
     */
    List<TradingShippingserviceoptionsDoc> selectByExample(TradingShippingserviceoptionsDocExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingserviceoptions_doc
     *
     * @mbggenerated
     */
    TradingShippingserviceoptionsDoc selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingserviceoptions_doc
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingShippingserviceoptionsDoc record, @Param("example") TradingShippingserviceoptionsDocExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingserviceoptions_doc
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingShippingserviceoptionsDoc record, @Param("example") TradingShippingserviceoptionsDocExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingserviceoptions_doc
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingShippingserviceoptionsDoc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingserviceoptions_doc
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingShippingserviceoptionsDoc record);
}