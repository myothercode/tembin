package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingOrderAddMemberMessageAAQToPartner;
import com.base.database.trading.model.TradingOrderAddMemberMessageAAQToPartnerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingOrderAddMemberMessageAAQToPartnerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_addmembermessageaaqtopartner
     *
     * @mbggenerated
     */
    int countByExample(TradingOrderAddMemberMessageAAQToPartnerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_addmembermessageaaqtopartner
     *
     * @mbggenerated
     */
    int deleteByExample(TradingOrderAddMemberMessageAAQToPartnerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_addmembermessageaaqtopartner
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_addmembermessageaaqtopartner
     *
     * @mbggenerated
     */
    int insert(TradingOrderAddMemberMessageAAQToPartner record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_addmembermessageaaqtopartner
     *
     * @mbggenerated
     */
    int insertSelective(TradingOrderAddMemberMessageAAQToPartner record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_addmembermessageaaqtopartner
     *
     * @mbggenerated
     */
    List<TradingOrderAddMemberMessageAAQToPartner> selectByExampleWithBLOBs(TradingOrderAddMemberMessageAAQToPartnerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_addmembermessageaaqtopartner
     *
     * @mbggenerated
     */
    List<TradingOrderAddMemberMessageAAQToPartner> selectByExample(TradingOrderAddMemberMessageAAQToPartnerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_addmembermessageaaqtopartner
     *
     * @mbggenerated
     */
    TradingOrderAddMemberMessageAAQToPartner selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_addmembermessageaaqtopartner
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingOrderAddMemberMessageAAQToPartner record, @Param("example") TradingOrderAddMemberMessageAAQToPartnerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_addmembermessageaaqtopartner
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") TradingOrderAddMemberMessageAAQToPartner record, @Param("example") TradingOrderAddMemberMessageAAQToPartnerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_addmembermessageaaqtopartner
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingOrderAddMemberMessageAAQToPartner record, @Param("example") TradingOrderAddMemberMessageAAQToPartnerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_addmembermessageaaqtopartner
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingOrderAddMemberMessageAAQToPartner record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_addmembermessageaaqtopartner
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(TradingOrderAddMemberMessageAAQToPartner record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_addmembermessageaaqtopartner
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingOrderAddMemberMessageAAQToPartner record);
}