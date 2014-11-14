package com.base.utils.scheduleother.domain;

import com.base.utils.scheduleother.StaticParam;

/**
 * Created by Administrator on 2014/11/13.
 * 检查图片
 */
public class ImageCheckVO implements SCBaseVO{

    /**图片地址*/
    private String url;

    /**========================================*/
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
