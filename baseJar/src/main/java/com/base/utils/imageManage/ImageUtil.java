package com.base.utils.imageManage;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Administrator on 2014/11/11.
 */
public class ImageUtil {
    /**判断图片是否存在*/
    public static boolean isPicIsAvail(String urll){
        boolean b=true;
        try {
            URL url = new URL(urll);
            URLConnection uc = url.openConnection();
            uc.setConnectTimeout(10000);
            uc.setReadTimeout(20000);
            InputStream in = uc.getInputStream();
        } catch (Exception e) {
            b=false;
        }
        return b;
    }

    /**缩放图片*/
    public static InputStream zoomPic(InputStream imgStream,int w ,int h) throws Exception {
        if (imgStream==null){return imgStream;}


        BufferedImage im = ImageIO.read(imgStream);
        Iterator<ImageReader> it = ImageIO.getImageReaders(imgStream);
        ImageReader r=null;
        String imgFormat="jpg";//图片格式，默认是jpg
        while(it.hasNext()){
            r=it.next();
            break;
        }
        if(r!=null){
            imgFormat=r.getFormatName().toLowerCase();
        }
        /* 原始图像的宽度和高度 */
        int width = im.getWidth();
        int height = im.getHeight();


        BufferedImage result = null;
        /* 新生成结果图片 */
        result = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
        result.getGraphics().drawImage(im.getScaledInstance(w, h,java.awt.Image.SCALE_SMOOTH), 0, 0, null);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(result, imgFormat, os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        return is;
    }




}
