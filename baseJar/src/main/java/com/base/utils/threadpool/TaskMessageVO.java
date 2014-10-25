package com.base.utils.threadpool;

import com.base.database.sitemessage.model.PublicSitemessage;

import java.util.Date;

/**
 * Created by Administrtor on 2014/9/5.
 * 用于任务中传递消息
 */
public class  TaskMessageVO<T> {
    private String messageType;//类型，参见TaskPool类中的定义
    private String messageTitle;//标题
    private String messageContext;//消息内容前缀
    private Long messageTo;//接收人
    private String messageFrom;//发送人
    private Boolean sendOrNotSend=true;//是否发送消息
    private Boolean weithSendSuccessMessage=true;//是否发送成功消息
    private String beanNameType;//要执行的bean类型名
    private T objClass;//传递的对象参数


    public Boolean getWeithSendSuccessMessage() {
        return weithSendSuccessMessage;
    }

    public void setWeithSendSuccessMessage(Boolean weithSendSuccessMessage) {
        this.weithSendSuccessMessage = weithSendSuccessMessage;
    }

    public Boolean getSendOrNotSend() {
        return sendOrNotSend;
    }

    public void setSendOrNotSend(Boolean sendOrNotSend) {
        this.sendOrNotSend = sendOrNotSend;
    }

    public String getBeanNameType() {
        return beanNameType;
    }

    public T getObjClass() {
        return objClass;
    }

    public void setObjClass(T objClass) {
        this.objClass = objClass;
    }

    public void setBeanNameType(String beanNameType) {
        this.beanNameType = beanNameType;
    }



    public String getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(String messageFrom) {
        this.messageFrom = messageFrom;
    }

    public Long getMessageTo() {
        return messageTo;
    }

    public void setMessageTo(Long messageTo) {
        this.messageTo = messageTo;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageContext() {
        return messageContext;
    }

    public void setMessageContext(String messageContext) {
        this.messageContext = messageContext;
    }

    public PublicSitemessage toPublicSiteMessage(){
        PublicSitemessage publicSitemessage = new PublicSitemessage();
        publicSitemessage.setReaded("0");
        publicSitemessage.setCreateTime(new Date());
        publicSitemessage.setMessageFrom(this.getMessageFrom()==null?"unknown":this.getMessageFrom());
        publicSitemessage.setMessageTo(this.getMessageTo());
        publicSitemessage.setMessageTitle(this.getMessageTitle());
        publicSitemessage.setMessage(this.getMessageContext());
        publicSitemessage.setMessageType(this.getMessageType());
        return publicSitemessage;
    }
}
