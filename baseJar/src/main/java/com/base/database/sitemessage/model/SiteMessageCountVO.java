package com.base.database.sitemessage.model;

/**
 * Created by Administrtor on 2014/9/4.
 * 用于统计各种消息的数量
 */
public class SiteMessageCountVO {
    private String mtype;
    private Long mnum;

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public Long getMnum() {
        return mnum;
    }

    public void setMnum(Long mnum) {
        this.mnum = mnum;
    }
}
