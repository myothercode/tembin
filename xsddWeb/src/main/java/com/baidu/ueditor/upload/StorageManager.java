package com.baidu.ueditor.upload;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;

import java.io.*;
import java.util.Map;

import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.MyStringUtil;
import com.base.utils.ftpabout.FtpUploadFile;
import com.base.utils.imageManage.service.ImageService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class StorageManager {
	public static final int BUFFER_SIZE = 8192;
    static Logger logger = Logger.getLogger(StorageManager.class);

	public StorageManager() {
	}

	public static State saveBinaryFile(byte[] data, String path) {
		//File file = getTmpFile();
        String[] skuandfname= StringUtils.split(path,"/");
        ArrayUtils.reverse(skuandfname);
        File tfileDir = FileUtils.getTempDirectory();
        File file = new File(tfileDir,skuandfname[0]);

		State state = valid(file);

		if (!state.isSuccess()) {
			return state;
		}


		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			bos.write(data);
			bos.flush();
			bos.close();
		} catch (IOException ioe) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}
        state = ftpUploadFile(file,path);
		/*state = new BaseState(true, file.getAbsolutePath());
		state.putInfo( "size", data.length );
		state.putInfo( "title", file.getName() );*/
		return state;
	}

	public static State saveFileByInputStream(InputStream is, String path,
			long maxSize) {
		State state = null;

		File tmpFile = getTmpFile();

		byte[] dataBuf = new byte[ 2048 ];
		BufferedInputStream bis = new BufferedInputStream(is, StorageManager.BUFFER_SIZE);

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(tmpFile), StorageManager.BUFFER_SIZE);

			int count = 0;
			while ((count = bis.read(dataBuf)) != -1) {
				bos.write(dataBuf, 0, count);
			}
			bos.flush();
			bos.close();

			if (tmpFile.length() > maxSize) {
				tmpFile.delete();
				return new BaseState(false, AppInfo.MAX_SIZE);
			}

			//state = saveTmpFile(tmpFile, path); //todo 保存文件的方式，是存本地还是上传ftp
            state=ftpUploadFile(tmpFile,path);
            ImageService imageService= (ImageService) ApplicationContextUtil.getBean(ImageService.class);
            if (state.isSuccess() && imageService.getISBackLocal()){
                saveTmpFile(tmpFile, imageService.getImageDir()+"/"+path);
            }else {
                tmpFile.delete();
            }

			return state;
			
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	public static State saveFileByInputStream(InputStream is, String path) {
		State state = null;

		File tmpFile = getTmpFile();

		byte[] dataBuf = new byte[ 2048 ];
		BufferedInputStream bis = new BufferedInputStream(is, StorageManager.BUFFER_SIZE);

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(tmpFile), StorageManager.BUFFER_SIZE);

			int count = 0;
			while ((count = bis.read(dataBuf)) != -1) {
				bos.write(dataBuf, 0, count);
			}
			bos.flush();
			bos.close();
            state=ftpUploadFile(tmpFile,path);
            ImageService imageService= (ImageService) ApplicationContextUtil.getBean(ImageService.class);
            if (state.isSuccess() && imageService.getISBackLocal()){
                saveTmpFile(tmpFile, imageService.getImageDir()+"/"+path);
            }else {
                tmpFile.delete();
            }

			return state;
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static File getTmpFile() {
		File tmpDir = FileUtils.getTempDirectory();
		String tmpFileName = (Math.random() * 10000 + "").replace(".", "");
		return new File(tmpDir, tmpFileName);
	}

	private static State saveTmpFile(File tmpFile, String path) {
		State state = null;
		File targetFile = new File(path);

		if (targetFile.canWrite()) {
			return new BaseState(false, AppInfo.PERMISSION_DENIED);
		}
		try {
			FileUtils.copyFile(tmpFile, targetFile);
		} catch (IOException e) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}finally {
            tmpFile.delete();
        }

		state = new BaseState(true);
		state.putInfo( "size", targetFile.length() );
		state.putInfo( "title", targetFile.getName() );
		
		return state;
	}

	private static State valid(File file) {
		File parentPath = file.getParentFile();

		if ((!parentPath.exists()) && (!parentPath.mkdirs())) {
			return new BaseState(false, AppInfo.FAILED_CREATE_FILE);
		}

		if (!parentPath.canWrite()) {
			return new BaseState(false, AppInfo.PERMISSION_DENIED);
		}

		return new BaseState(true);
	}


    /**ftp上传文件*/
    public static String ftpUploadFile(InputStream inputStream ,String skuName,String stuff){
        return FtpUploadFile.ftpUploadFile(inputStream,skuName,stuff);
    }

    public static State ftpUploadFile(File file,String path){
       // http://www.open-open.com/lib/view/open1384090071946.html
        //ftpClient.makeDirectory(new String(pathName.getBytes("UTF-8"),"iso-8859-1"));创建目录
        State state = null;
        String[] skuandfname= StringUtils.split(path,"/");
        ArrayUtils.reverse(skuandfname);
        String skuName=skuandfname[1];
        String fileName=skuandfname[0];
        String userName=skuandfname[2];

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
                return new BaseState(false, AppInfo.FTP_LOGIN_FAILED);
            }
            // 转移工作目录至指定目录下
            boolean change = ftpClient.changeWorkingDirectory(userName);
            if(!change){
                boolean cdSku = ftpClient.makeDirectory(userName);
                if(!cdSku){
                    logger.error("在ftp上创建用户目录失败!"+userName);
                    return new BaseState(false, AppInfo.FAILED_CREATE_FILE);
                }
            }
            boolean skudir = ftpClient.changeWorkingDirectory(skuName);
            if(!skudir){
                boolean cdSku = ftpClient.makeDirectory(skuName);
                if(!cdSku){
                    logger.error("在ftp上创建sku目录失败"+userName+":"+skuName);
                    return new BaseState(false, AppInfo.FAILED_CREATE_FILE);
                }
            }
           ftpClient.changeWorkingDirectory(skuName);
           // String[] x= ftpClient.listNames();
           // String fileName = new String(file.getName().getBytes("utf-8"),"iso-8859-1");
          boolean  result = ftpClient.storeFile(fileName, new FileInputStream(file));
            if (result) {
                logger.info("ftp上传成功!");
            }

        } catch (Exception e) {
            logger.error("ftp出错"+e.getMessage(),e);
            file.delete();
            return new BaseState(false, AppInfo.IO_ERROR);
        } finally {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }

        }

        state = new BaseState(true);
        state.putInfo( "size", file.length() );
        state.putInfo( "title", file.getName() );
        return state;
    }

}
