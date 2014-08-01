package com.common.controller;

import com.base.utils.imageManage.service.ImageService;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by Administrtor on 2014/8/1.
 * 图片管理
 */
@Controller
public class ImageManageController {
    @Autowired
    private ImageService imageService;
/*    @RequestMapping("/upLoadImage.do")
    public void upLoadImage(@RequestParam("upfile")MultipartFile[] multipartFiles){
        for (MultipartFile multipartFile : multipartFiles){
            System.out.println(multipartFile.getName());
        }
    }*/
@RequestMapping("/getImageStream.do")
@ResponseBody
public void getImageStream(@RequestParam("path") String path,HttpServletResponse response) throws Exception{
    String firstPath = imageService.getImageDir();
    InputStream input=new FileInputStream(new File(firstPath+path));
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
