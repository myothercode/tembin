package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingPictures;
import com.base.database.trading.model.TradingPicturesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingPicturesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_pictures
     *
     * @mbggenerated
     */
    int countByExample(TradingPicturesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_pictures
     *
     * @mbggenerated
     */
    int deleteByExample(TradingPicturesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_pictures
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_pictures
     *
     * @mbggenerated
     */
    int insert(TradingPictures record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_pictures
     *
     * @mbggenerated
     */
    int insertSelective(TradingPictures record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_pictures
     *
     * @mbggenerated
     */
    List<TradingPictures> selectByExample(TradingPicturesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_pictures
     *
     * @mbggenerated
     */
    TradingPictures selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_pictures
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingPictures record, @Param("example") TradingPicturesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_pictures
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingPictures record, @Param("example") TradingPicturesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_pictures
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingPictures record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_pictures
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingPictures record);
}