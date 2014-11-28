package com.base.domains.querypojos;

import com.base.database.publicd.model.PublicItemInformation;

/**
 * Created by Administrtor on 2014/9/4.
 */
public class ItemInformationQuery extends PublicItemInformation {
    private String typeName1;
    private String pictureUrl;
    private String remark;
    private Integer countNum;
    private Long typeId;

    public String getTypeName1() {
        return typeName1;
    }

    public void setTypeName1(String typeName1) {
        this.typeName1 = typeName1;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCountNum() {
        return countNum;
    }

    public void setCountNum(Integer countNum) {
        this.countNum = countNum;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}
