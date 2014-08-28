package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * 图片分类
 * Created by cz on 2014/7/16.
 */
@XStreamAlias("PictureDetails")
public class PictureDetails {
    /**
     * 图片存放时间
     */
    private String GalleryDuration;
    /**
     * 图片分类
     */
    private String GalleryType;
    /**
     * 图片地址
     */
    private String GalleryURL;
    /**
     * 显示类型
     */
    private String PhotoDisplay;
    /**
     * 图片来源
     */
    private String PictureSource;
    /**
     * 图片ＵＲＬ
     */
    @XStreamImplicit(itemFieldName = "PictureURL")
    private List<String> PictureURL;

    public String getGalleryDuration() {
        return GalleryDuration;
    }

    public void setGalleryDuration(String galleryDuration) {
        GalleryDuration = galleryDuration;
    }

    public String getGalleryType() {
        return GalleryType;
    }

    public void setGalleryType(String galleryType) {
        GalleryType = galleryType;
    }

    public String getGalleryURL() {
        return GalleryURL;
    }

    public void setGalleryURL(String galleryURL) {
        GalleryURL = galleryURL;
    }

    public String getPhotoDisplay() {
        return PhotoDisplay;
    }

    public void setPhotoDisplay(String photoDisplay) {
        PhotoDisplay = photoDisplay;
    }

    public String getPictureSource() {
        return PictureSource;
    }

    public void setPictureSource(String pictureSource) {
        PictureSource = pictureSource;
    }

    public List<String> getPictureURL() {
        return PictureURL;
    }

    public void setPictureURL(List<String> pictureURL) {
        PictureURL = pictureURL;
    }
}
