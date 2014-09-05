package com.base.utils.threadpool;

import com.base.database.sitemessage.model.PublicSitemessage;

import java.util.Date;

/**
 * Created by Administrtor on 2014/9/5.
 * 用于任务中传递消息
 */
public class TaskMessageVO {
    private String messageType;//类型，参见TaskPool类中的定义
    private String messageTitle;//标题
    private String messageContext;//消息内容前缀
    private Long messageTo;//接收人
    private String messageFrom;//发送人

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
