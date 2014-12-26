package com.common.base.utils.logUtil;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by chace.cai on 2014/4/11.
 * 记录定时任务线程错误
 * 重写 DailyRollingFileAppender 方法，使其具有改变log默认路径的能力该类为记录定时任务日志
 */
public class MyTaskTimerDailyRollingFileAppender extends DailyRollingFileAppender {
    private String defaultLogDirectory;

    public void setDefaultLogDirectory(String defaultLogDirectory) {
        this.defaultLogDirectory = defaultLogDirectory;
    }

    public MyTaskTimerDailyRollingFileAppender() {
    }

    public MyTaskTimerDailyRollingFileAppender(Layout layout, String filename, String datePattern) throws IOException {
        super(layout, filename, datePattern);
    }

    @Override
    public void activateOptions() {
        //fileName=d:/log/rootpom.log
        String osName = System.getProperty("os.name");
        if(StringUtils.hasText(fileName)) {
            if(StringUtils.hasText(osName)) {
                if(osName.indexOf("Windows")>-1){
                    fileName="d:/log/timerTasklog.log";
                }else {
                    fileName="/var/tembinConfig/logs/timerTasklog.log";
                }
                //fileName = parent.endsWith(File.separator) ? (parent + fileName) : (parent + File.separator + fileName);
            } else if(StringUtils.hasText(defaultLogDirectory)) {
                fileName = defaultLogDirectory.endsWith(File.separator) ? (defaultLogDirectory + fileName) : (
                        defaultLogDirectory + File.separator + fileName);
            }
        }
        super.activateOptions();
    }

    @Override
    protected void subAppend(LoggingEvent event) {
        ErrorInforHandler.handleError(event, qw);
        super.subAppend(event);
    }
}
