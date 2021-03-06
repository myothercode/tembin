package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingListingReport;
import com.base.database.trading.model.TradingListingReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingListingReportMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    int countByExample(TradingListingReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    int deleteByExample(TradingListingReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    int insert(TradingListingReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    int insertSelective(TradingListingReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    List<TradingListingReport> selectByExample(TradingListingReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    TradingListingReport selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingListingReport record, @Param("example") TradingListingReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingListingReport record, @Param("example") TradingListingReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingListingReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_report
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingListingReport record);
}