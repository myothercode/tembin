package com.baidu.ueditor.hunter;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.imageManage.service.ImageService;
import org.apache.commons.io.FileUtils;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.MultiState;
import com.baidu.ueditor.define.State;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class FileManager {
    static Logger logger = Logger.getLogger(FileManager.class);

	private String dir = null;
    private String ftpDirPath=null;
	private String rootPath = null;
	private String[] allowFiles = null;
	private int count = 0;
    private String action=null;

	public FileManager ( Map<String, Object> conf ) {

        this.ftpDirPath=(String)conf.get( "dir" );
		this.rootPath = (String)conf.get( "rootPath" );
		this.dir = this.rootPath + (String)conf.get( "dir" );
		this.allowFiles = this.getAllowFiles( conf.get("allowFiles") );
		this.count = (Integer)conf.get( "count" );
        this.action=conf.get("action")==null?null:(String)conf.get("action");
	}
	
	public State listFile ( int index ) {
		
		File dir = new File( this.dir );
		State state = null;
        int size=0;


        if ("listImage".equalsIgnoreCase(this.action)) {
            List<String> fs = this.getListFromeFTP();
            if(fs==null){return new BaseState( false, /*AppInfo.NOT_EXIST*/"图片路径不存在!"+"::"+this.ftpDirPath );}
            size=fs.size();
            if ( index < 0 || index > fs.size() ) {
                state = new MultiState( true );
            }else {
                state=getStateWithList(fs);
            }

        }else {
            if ( !dir.exists() ) {
                return new BaseState( false, AppInfo.NOT_EXIST+":::"+this.dir );
            }

            if ( !dir.isDirectory() ) {
                return new BaseState( false, AppInfo.NOT_DIRECTORY );
            }
            Collection<File> list = FileUtils.listFiles( dir, this.allowFiles, true );
            size=list.size();
            if ( index < 0 || index > list.size() ) {
                state = new MultiState( true );
            } else {
                Object[] fileList = Arrays.copyOfRange( list.toArray(), index, index + this.count );
                state = this.getState(fileList);

            }
        }

		
		state.putInfo( "start", index );
		state.putInfo( "total", size );
		
		return state;
		
	}
	
	private State getState ( Object[] files ) {
		
		MultiState state = new MultiState( true );
		BaseState fileState = null;
		
		File file = null;
		
		for ( Object obj : files ) {
			if ( obj == null ) {
				break;
			}
			file = (File)obj;
			fileState = new BaseState( true );
			fileState.putInfo( "url", PathFormat.format( this.getPath( file ) ) );
			state.addState( fileState );
		}
		return state;
	}

    /**从文件list中组装state*/
    private State getStateWithList(List<String> filePaths){
        MultiState state = new MultiState( true );
        BaseState fileState = null;

        String file = null;

        for ( String obj : filePaths ) {
            if ( obj == null ) {
                break;
            }
            fileState = new BaseState( true );
            fileState.putInfo( "url", PathFormat.format( obj ));
            state.addState( fileState );
        }
        return state;
    }

    /**获取到ftp上的文件列表*/
    private List<String> getListFromeFTP(){
        ImageService imageService1= (ImageService) ApplicationContextUtil.getBean(ImageService.class);
        Map<String,String> map=imageService1.getFTPINfo();
        FTPClient ftpClient = new FTPClient();

        try {
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
            boolean change = ftpClient.changeWorkingDirectory(ftpDirPath);
            if(!change){return  null;}
            String[] ns=ftpClient.listNames();
            if(ns==null || ns.length==0){
                return null;
            }
            List<String> ls=new ArrayList<String>();
            for (String s:ns){
                ls.add(ftpDirPath+"/"+s);
            }
            return ls;
        } catch (Exception e) {
            logger.error("获取ftp图片目录出错!"+e.getMessage(),e);
        }
        return null;
    }

/**todo 地址是不带rootpath的*/
    private State getStateWithNoRootPath ( Object[] files ) {

        MultiState state = new MultiState( true );
        BaseState fileState = null;

        File file = null;

        for ( Object obj : files ) {
            if ( obj == null ) {
                break;
            }
            file = (File)obj;
            fileState = new BaseState( true );
            fileState.putInfo( "url", PathFormat.format( this.getPath( file ) ).replace(this.rootPath,"") );
            state.addState( fileState );
        }
        return state;
    }
	
	private String getPath ( File file ) {
		
		String path = file.getAbsolutePath();
		
		return path.replace( this.rootPath, "/" );
		
	}
	
	private String[] getAllowFiles ( Object fileExt ) {
		
		String[] exts = null;
		String ext = null;
		
		if ( fileExt == null ) {
			return new String[ 0 ];
		}
		
		exts = (String[])fileExt;
		
		for ( int i = 0, len = exts.length; i < len; i++ ) {
			
			ext = exts[ i ];
			exts[ i ] = ext.replace( ".", "" );
			
		}
		
		return exts;
		
	}
	
}
