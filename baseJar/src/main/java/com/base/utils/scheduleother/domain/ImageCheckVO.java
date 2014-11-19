package com.base.utils.scheduleother.domain;

import com.base.utils.scheduleother.StaticParam;

import java.util.Date;

/**
 * Created by Administrator on 2014/11/13.
 * 检查图片
 */
public class ImageCheckVO implements SCBaseVO{

    private Long id;
    private Long ebayAccountId;
    private String siteId;
    /**图片地址*/
    private String url;
    /**ebay图片地址*/
    private String ebayUrl;
    private Date createDate;
    private Date endDate;

    public boolean equals(Object o){//重写equals方法
        if(o == this)return true;
        ImageCheckVO ic=(ImageCheckVO)o;
        if(ic.id==null || this.id==null){return super.equals(o);}
        if(ic.id==this.id){
            return true;
        }else {
            return false;
        }
       }


    /**========================================*/
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public Long getEbayAccountId() {
        return ebayAccountId;
    }

    public void setEbayAccountId(Long ebayAccountId) {
        this.ebayAccountId = ebayAccountId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getEbayUrl() {
        return ebayUrl;
    }

    public void setEbayUrl(String ebayUrl) {
        this.ebayUrl = ebayUrl;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String schedledType() {
        return StaticParam.IMG_CHECK_SC;
    }
}
