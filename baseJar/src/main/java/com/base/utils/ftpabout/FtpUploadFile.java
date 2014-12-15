package com.base.utils.ftpabout;

import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.ConvertUtil;
import com.base.utils.common.MyStringUtil;
import com.base.utils.imageManage.ImageUtil;
import com.base.utils.imageManage.service.ImageService;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/13.
 */
public class FtpUploadFile {
    static Logger logger = Logger.getLogger(FtpUploadFile.class);

    /**ftp上传文件*/
    public static String ftpUploadFile(InputStream inputStream ,String skuName,String stuff) throws Exception {
        String fileName="";
        FTPClient ftpClient = new FTPClient();
        try {
            ImageService imageService1= (ImageService) ApplicationContextUtil.getBean(ImageService.class);
            Map<String,String> map=imageService1.getFTPINfo();
            ftpClient.connect(map.get("ftpIP"),Integer.parseInt(map.get("ftpPort")));
            ftpClient.login(map.get("ftpUserName"), map.get("ftpPassword"));
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setUseEPSVwithIPv4( true );//java7在windows上的一个bug

            int reply;//检查登陆结果
            reply=ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)){
                logger.error("ftp登陆失败!登陆结果是"+reply);
                ftpClient.disconnect();
                return null;
            }

            if(skuName.indexOf("/")>-1){
                skuName=skuName.toLowerCase();
                skuName.replace("?set_id=880000500f","").replace("?set_id=880000500F", "");
                stuff=stuff.toLowerCase();
                stuff.replace("?set_id=880000500f","").replace("?set_id=880000500F","");
                if(stuff.indexOf("?")>-1){
                    String r=stuff.substring(stuff.lastIndexOf("?"), stuff.length());
                    stuff=stuff.replace(r,"");
                }

                String[] dnames=skuName.split("/");
                boolean skudir = ftpClient.changeWorkingDirectory(dnames[0]);
                if(!skudir){
                    boolean cdSku = ftpClient.makeDirectory(dnames[0]);
                    if(cdSku){
                        ftpClient.changeWorkingDirectory(dnames[0]);
                        boolean cdSku2 = ftpClient.makeDirectory(dnames[1]);
                        if(cdSku2){
                            ftpClient.changeWorkingDirectory(dnames[1]);
                        }else {
                            logger.error("在ftp上创建sku目录失败1"+":"+dnames[1]);
                            return null;
                        }

                    }else {
                        logger.error("在ftp上创建sku目录失败2"+":"+dnames[0]);
                        return null;
                    }

                }else {
                    boolean cdSku2 = ftpClient.changeWorkingDirectory(dnames[1]);
                    if(!cdSku2){
                        boolean cdSku3 = ftpClient.makeDirectory(dnames[1]);
                        if(!cdSku3){
                            logger.error("在ftp上创建sku目录失败3"+":"+skuName);
                            return null;
                        }
                    }
                    ftpClient.changeWorkingDirectory(dnames[1]);
                }
            }else {
                boolean skudir = ftpClient.changeWorkingDirectory(skuName);
                if(!skudir){
                    boolean cdSku = ftpClient.makeDirectory(skuName);
                    if(!cdSku){
                        logger.error("在ftp上创建sku目录失败4"+":"+skuName);
                        return null;
                    }
                }
                ftpClient.changeWorkingDirectory(skuName);
            }

            // String[] x= ftpClient.listNames();
            // String fileName = new String(file.getName().getBytes("utf-8"),"iso-8859-1");
            fileName= MyStringUtil.generateRandomFilename()+stuff;
            inputStream.mark(1);
            InputStream smin= ConvertUtil.byteTOInputStream(ConvertUtil.InputStreamTOByte(inputStream)) ;
            InputStream smin1=ConvertUtil.byteTOInputStream(ConvertUtil.InputStreamTOByte(smin)) ;
            smin.reset();
            smin1.reset();
            boolean  result = ftpClient.storeFile(fileName, smin);
            boolean  result1;
            try {
                result1 = ftpClient.storeFile(MyStringUtil.getFimeNoStuff(fileName)+"_small"+MyStringUtil.getExtension(fileName, ""),
                        ImageUtil.zoomPic(smin1, 80, 80));
            } catch (Exception e) {
                result1=false;
                logger.info("ftp上传小图失败!直接上传成大图!"+fileName,e);
            }
            if (result) {
                logger.info("ftp上传成功!");
            }
            if (!result1){
                smin.reset();
                ftpClient.storeFile(MyStringUtil.getFimeNoStuff(fileName)+"_small"+MyStringUtil.getExtension(fileName,""),
                        smin);
            }

        } catch (Exception e) {
            logger.error("ftp出错"+e.getMessage(),e);
            throw new Exception(e.getMessage(),e);
            //return null;

        } finally {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }

        }


        return fileName;
    }
}
