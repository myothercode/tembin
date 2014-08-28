package com.common.controller;

import com.baidu.ueditor.upload.StorageManager;
import com.base.utils.common.MyStringUtil;
import com.base.utils.common.ObjectUtils;
import com.base.utils.imageManage.service.ImageService;
import com.common.base.utils.ajax.AjaxResponse;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrtor on 2014/8/1.
 * 图片管理
 */
@Controller
//@Scope(value = "prototype")
public class ImageManageController {
    static Logger logger = Logger.getLogger(ImageManageController.class);

    @Autowired
    private ImageService imageService;
    @RequestMapping("/upLoadImage.do")
    public void upLoadImage(@RequestParam("multipartFiles")MultipartFile[] multipartFiles,HttpServletResponse response) throws IOException {

        if(ObjectUtils.isLogicalNull(multipartFiles)){
            AjaxResponse.sendText(response,"nofile");
            return;
        }
        MultipartFile multipartFile = multipartFiles[0];
        String stuff= MyStringUtil.getExtension(multipartFile.getOriginalFilename(),"");
        String r = StorageManager.ftpUploadFile(multipartFile.getInputStream(),"templateViewIMG",stuff);
        if(r==null){r="err";}
        AjaxResponse.sendText(response,"/templateViewIMG/"+r);
    }
@RequestMapping("/getImageStream.do")
@ResponseBody
public void getImageStream(@RequestParam("path") String path,HttpServletResponse response) throws Exception{
    String firstPath = imageService.getImageDir();
    InputStream input=new FileInputStream(new File(firstPath+path));
//    InputStream input=new FileInputStream(new File(path));
    response.setContentType("image/jpeg");
    ServletOutputStream output=  response.getOutputStream();
    int count=0;
    byte[] bt=new byte[512];
    while((count=input.read(bt))!=-1){
        output.write(bt);
    }
    output.flush();
    output.close();
    input.close();
}

}
