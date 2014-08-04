package com.base.utils.imageManage.service.impl;

import com.base.domains.SessionVO;
import com.base.utils.cache.SessionCacheSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrtor on 2014/8/1.
 * 图片管理service
 */
@Service
public class ImageServiceImpl implements com.base.utils.imageManage.service.ImageService {
    @Value("${IMAGE.ImageTempDir}")
    private  String imageTempDir;//临时存放图片的目录

    @Override
    public String getImageDir(){
        return imageTempDir;
    }

    @Override
    public String getImageUserDir(){
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        return sessionVO.getLoginId();
    }
}
