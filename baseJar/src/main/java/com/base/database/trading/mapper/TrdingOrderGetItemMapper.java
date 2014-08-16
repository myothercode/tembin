package com.base.database.trading.mapper;

import com.base.database.trading.model.TrdingOrderGetItem;
import com.base.database.trading.model.TrdingOrderGetItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TrdingOrderGetItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_get_item
     *
     * @mbggenerated
     */
    int countByExample(TrdingOrderGetItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_get_item
     *
     * @mbggenerated
     */
    int deleteByExample(TrdingOrderGetItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_get_item
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_get_item
     *
     * @mbggenerated
     */
    int insert(TrdingOrderGetItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_get_item
     *
     * @mbggenerated
     */
    int insertSelective(TrdingOrderGetItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_get_item
     *
     * @mbggenerated
     */
    List<TrdingOrderGetItem> selectByExampleWithBLOBs(TrdingOrderGetItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_get_item
     *
     * @mbggenerated
     */
    List<TrdingOrderGetItem> selectByExample(TrdingOrderGetItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_get_item
     *
     * @mbggenerated
     */
    TrdingOrderGetItem selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_get_item
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TrdingOrderGetItem record, @Param("example") TrdingOrderGetItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_get_item
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") TrdingOrderGetItem record, @Param("example") TrdingOrderGetItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_get_item
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TrdingOrderGetItem record, @Param("example") TrdingOrderGetItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_get_item
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TrdingOrderGetItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_get_item
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(TrdingOrderGetItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_get_item
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TrdingOrderGetItem record);
}