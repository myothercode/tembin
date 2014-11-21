package com.base.database.task.mapper;

import com.base.database.task.model.TaskGetMessages;
import com.base.database.task.model.TaskGetMessagesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskGetMessagesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_messages
     *
     * @mbggenerated
     */
    int countByExample(TaskGetMessagesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_messages
     *
     * @mbggenerated
     */
    int deleteByExample(TaskGetMessagesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_messages
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_messages
     *
     * @mbggenerated
     */
    int insert(TaskGetMessages record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_messages
     *
     * @mbggenerated
     */
    int insertSelective(TaskGetMessages record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_messages
     *
     * @mbggenerated
     */
    List<TaskGetMessages> selectByExampleWithBLOBs(TaskGetMessagesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_messages
     *
     * @mbggenerated
     */
    List<TaskGetMessages> selectByExample(TaskGetMessagesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_messages
     *
     * @mbggenerated
     */
    TaskGetMessages selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_messages
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TaskGetMessages record, @Param("example") TaskGetMessagesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_messages
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") TaskGetMessages record, @Param("example") TaskGetMessagesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_messages
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TaskGetMessages record, @Param("example") TaskGetMessagesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_messages
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TaskGetMessages record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_messages
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(TaskGetMessages record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_messages
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TaskGetMessages record);
}