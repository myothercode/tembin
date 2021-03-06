package com.base.database.inventory.mapper;

import com.base.database.inventory.model.ShihaiyouInventory;
import com.base.database.inventory.model.ShihaiyouInventoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShihaiyouInventoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shihaiyou_inventory
     *
     * @mbggenerated
     */
    int countByExample(ShihaiyouInventoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shihaiyou_inventory
     *
     * @mbggenerated
     */
    int deleteByExample(ShihaiyouInventoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shihaiyou_inventory
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shihaiyou_inventory
     *
     * @mbggenerated
     */
    int insert(ShihaiyouInventory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shihaiyou_inventory
     *
     * @mbggenerated
     */
    int insertSelective(ShihaiyouInventory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shihaiyou_inventory
     *
     * @mbggenerated
     */
    List<ShihaiyouInventory> selectByExample(ShihaiyouInventoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shihaiyou_inventory
     *
     * @mbggenerated
     */
    ShihaiyouInventory selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shihaiyou_inventory
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") ShihaiyouInventory record, @Param("example") ShihaiyouInventoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shihaiyou_inventory
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") ShihaiyouInventory record, @Param("example") ShihaiyouInventoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shihaiyou_inventory
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ShihaiyouInventory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shihaiyou_inventory
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ShihaiyouInventory record);
}