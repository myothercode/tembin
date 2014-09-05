package com.base.domains.querypojos;

import com.base.database.publicd.model.PublicItemInformation;

/**
 * Created by Administrtor on 2014/9/4.
 */
public class ItemInformationQuery extends PublicItemInformation {
    private String typeName;
    private String pictureUrl;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
