package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingListingdetails;
import com.base.database.trading.model.TradingListingdetailsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingListingdetailsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listingdetails
     *
     * @mbggenerated
     */
    int countByExample(TradingListingdetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listingdetails
     *
     * @mbggenerated
     */
    int deleteByExample(TradingListingdetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listingdetails
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listingdetails
     *
     * @mbggenerated
     */
    int insert(TradingListingdetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listingdetails
     *
     * @mbggenerated
     */
    int insertSelective(TradingListingdetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listingdetails
     *
     * @mbggenerated
     */
    List<TradingListingdetails> selectByExample(TradingListingdetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listingdetails
     *
     * @mbggenerated
     */
    TradingListingdetails selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listingdetails
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingListingdetails record, @Param("example") TradingListingdetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listingdetails
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingListingdetails record, @Param("example") TradingListingdetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listingdetails
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingListingdetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listingdetails
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingListingdetails record);
}