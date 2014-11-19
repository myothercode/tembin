package com.base.database.inventory.mapper;

import com.base.database.inventory.model.ItemInventory;
import com.base.database.inventory.model.ItemInventoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemInventoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_inventory
     *
     * @mbggenerated
     */
    int countByExample(ItemInventoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_inventory
     *
     * @mbggenerated
     */
    int deleteByExample(ItemInventoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_inventory
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_inventory
     *
     * @mbggenerated
     */
    int insert(ItemInventory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_inventory
     *
     * @mbggenerated
     */
    int insertSelective(ItemInventory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_inventory
     *
     * @mbggenerated
     */
    List<ItemInventory> selectByExample(ItemInventoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_inventory
     *
     * @mbggenerated
     */
    ItemInventory selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_inventory
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") ItemInventory record, @Param("example") ItemInventoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_inventory
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") ItemInventory record, @Param("example") ItemInventoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_inventory
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ItemInventory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_inventory
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ItemInventory record);
}