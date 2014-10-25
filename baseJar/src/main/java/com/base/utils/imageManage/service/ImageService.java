package com.base.utils.imageManage.service;

import java.util.Map;

/**
 * Created by Administrtor on 2014/8/1.
 */
public interface ImageService {
    String getImageDir();

    /**获取图片前缀*/
    String getImageUrlPrefix();

    String getImageUserDir();

    Map<String,String> getFTPINfo();

    String getItemListIconUrl();

    /**图片是否在本地保存*/
    boolean getISBackLocal();
}
