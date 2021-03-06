package com.base.database.publicd.mapper;

import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.publicd.model.PublicUserConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PublicUserConfigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_user_config
     *
     * @mbggenerated
     */
    int countByExample(PublicUserConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_user_config
     *
     * @mbggenerated
     */
    int deleteByExample(PublicUserConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_user_config
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_user_config
     *
     * @mbggenerated
     */
    int insert(PublicUserConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_user_config
     *
     * @mbggenerated
     */
    int insertSelective(PublicUserConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_user_config
     *
     * @mbggenerated
     */
    List<PublicUserConfig> selectByExample(PublicUserConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_user_config
     *
     * @mbggenerated
     */
    PublicUserConfig selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_user_config
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") PublicUserConfig record, @Param("example") PublicUserConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_user_config
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") PublicUserConfig record, @Param("example") PublicUserConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_user_config
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(PublicUserConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_user_config
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(PublicUserConfig record);
}