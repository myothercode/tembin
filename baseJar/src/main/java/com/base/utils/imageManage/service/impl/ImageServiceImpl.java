package com.base.utils.imageManage.service.impl;

import com.base.domains.SessionVO;
import com.base.utils.cache.SessionCacheSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/1.
 * 图片管理service
 */
@Service
public class ImageServiceImpl implements com.base.utils.imageManage.service.ImageService {
    @Value("${IMAGE.ImageTempDir}")
    private  String imageTempDir;//临时存放图片的目录
    @Value("${FTP.IP}")
    private String ftpIP;//ftpip
    @Value("${FTP.PORT}")
    private String ftpPort;
    @Value("${FTP.USER}")
    private String ftpUserName;
    @Value("${FTP.PASSWORD}")
    private String ftpPassword;
    @Value("${IS_BAKUP_LOCAL}")
    private String sfBackUp;//是否在本地备份

    @Override
    public String getImageDir(){
        return imageTempDir;
    }

    @Override
    public String getImageUserDir(){
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        return sessionVO.getLoginId();
    }

    @Override
    public Map<String,String> getFTPINfo(){
        Map map=new HashMap();
        map.put("ftpIP",ftpIP);
        map.put("ftpPort",ftpPort);
        map.put("ftpUserName",ftpUserName);
        map.put("ftpPassword",ftpPassword);
        return map;
    }

    @Override
    /**图片是否在本地保存*/
    public boolean getISBackLocal(){
        if(sfBackUp==null){return false;}
        return sfBackUp.equalsIgnoreCase("true")?true:false;
    }
}