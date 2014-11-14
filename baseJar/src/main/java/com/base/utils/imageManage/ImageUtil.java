package com.base.utils.imageManage;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2014/11/11.
 */
public class ImageUtil {
    /**判断图片是否存在*/
    public boolean isPicIsAvail(String urll){
        boolean b=true;
        try {
            URL url = new URL(urll);
            URLConnection uc = url.openConnection();
            InputStream in = uc.getInputStream();
        } catch (Exception e) {
            b=false;
        }
        return b;
    }
}
