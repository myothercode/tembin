package com.sitemessage.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrtor on 2014/9/5.
 * 站内消息类型
 */
public class SiteMessageStatic {
    /**每种消息类型请添加进map*/
    public static final Map messageMap=new HashMap();
    static {
        messageMap.put("listing_message","刊登结果");
        messageMap.put("listing_pic_url_message","上传图片");
        messageMap.put("listing_get_message_type","系统消息");
        messageMap.put("synchronize_user_case_type","同步纠纷");
        messageMap.put("synchronize_user_case_dispute_type","同步一般纠纷");
        messageMap.put("synchronize_user_case_ebp_type","同步EBP纠纷");
        messageMap.put("synchronize_get_order_type","同步订单");
        messageMap.put("syn_message_listing_data_type","同步在线商品");
        messageMap.put("auto_message_task_run","定时发送付款或发货后的自动消息");
        messageMap.put("feed_back_auto_message_task_run","定时发送获取评价后的自动消息");
        messageMap.put("synchronize_get_order_timer","定时同步订单");
        messageMap.put("synchronize_feed_back_timer","定时获取评价");
        messageMap.put("update_listing_data_message_type","范本修改在线商品");
        messageMap.put("listing_key_move_message_type","一键搬家");
    }

    /**刊登的消息类型*/
    public static final String LISTING_MESSAGE_TYPE="listing_message";
    /**通过范本修改在线商品的消息类型*/
    public static final String UPDATE_LISTING_DATA_MESSAGE_TYPE="update_listing_data_message_type";
    /**刊登成功的消息类型
    public static final String LISTING_MESSAGE_SUCCESS="listing_message_success";
    *//**刊登失败的消息类型*//*
    public static final String LISTING_MESSAGE_FAIL="listing_message_fail";*/
    /**上传图片的消息类型*/
    public static final String LISTING_PIC_URL_MESSAGE="listing_pic_url_message";
    /**上传图片的消息类型*/
    public static final String LISTING_PIC_URL_BEAN="listing_pic_url_bean";

    /**上传图片的消息类型*/
    public static final String LISTING_KEY_MOVE_BEAN="listing_key_move_bean";
    /**刊登成功的消息类型*/
    public static final String LISTING_ITEM_BEAN="listing_item_bean";
    /**同步在线商品*/
    public static final String SYN_MESSAGE_LISTING_DATA_TYPE="syn_message_listing_data_type";
    /**一键搬家*/
    public static final String LISTING_KEY_MOVE_MESSAGE_TYPE = "listing_key_move_message_type";

    /**同步在线商品bean*/
    public static final String SYN_MESSAGE_LISTING_DATA_BEAN="syn_message_listing_data_bean";
    /**在线编辑数据类型**/
    public static final String LISTING_DATA_UPDATE="listing_data_update";
    /**同步接受消息的bean类型*/
    public static final String SYNCHRONIZE_GET_MESSAGE_BEAN="listing_get_message_bean";

    /**接受消息类型*/
    public static final String SYNCHRONIZE_GET_MESSAGE_TYPE="listing_get_message_type";
    /**同步纠纷bean类型*/
    public static final String SYNCHRONIZE_USER_CASE_BEAN="synchronize_user_case_bean";

    /**同步纠纷类型*/
    public static final String SYNCHRONIZE_USER_CASE_TYPE="synchronize_user_case_type";

    /**同步一般纠纷bean类型*/
    public static final String SYNCHRONIZE_USER_CASE_DISPUTE_BEAN="synchronize_user_case_dispute_bean";

    /**同步一般纠纷类型*/
    public static final String SYNCHRONIZE_USER_CASE_DISPUTE_TYPE="synchronize_user_case_dispute_type";

    /**同步EBP纠纷bean类型*/
    public static final String SYNCHRONIZE_USER_CASE_EBP_BEAN="synchronize_user_case_ebp_bean";

    /**同步EBP纠纷类型*/
    public static final String SYNCHRONIZE_USER_CASE_EBP_TYPE="synchronize_user_case_ebp_type";
    /**同步订单bean类型*/
    public static final String SYNCHRONIZE_GET_ORDER_BEAN="synchronize_get_order_bean";

    /**同步订单类型*/
    public static final String SYNCHRONIZE_GET_ORDER_TYPE="synchronize_get_order_type";

    /**定时发送付款或发货后的自动消息*/
    public static final String AUTO_MESSAGE_TASK_RUN="auto_message_task_run";

    /**定时发送获取评价后的自动消息*/
    public static final String FEED_BACK_AUTO_MESSAGE_TASK_RUN="feed_back_auto_message_task_run";

    /**定时同步订单*/
    public static final String SYNCHRONIZE_GET_ORDER_TIMER="synchronize_get_order_timer";

    /**定时获取评价*/
    public static final String SYNCHRONIZE_FEED_BACK_TIMER="synchronize_feed_back_timer";

}
