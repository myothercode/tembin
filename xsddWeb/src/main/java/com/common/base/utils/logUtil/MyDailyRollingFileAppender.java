package com.common.base.utils.logUtil;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by chace.cai on 2014/4/11.
 * 重写 DailyRollingFileAppender 方法，使其具有改变log默认路径的能力
 */
public class MyDailyRollingFileAppender extends DailyRollingFileAppender {
    private String defaultLogDirectory;

    public void setDefaultLogDirectory(String defaultLogDirectory) {
        this.defaultLogDirectory = defaultLogDirectory;
    }

    public MyDailyRollingFileAppender() {
    }

    public MyDailyRollingFileAppender(Layout layout, String filename, String datePattern) throws IOException {
        super(layout, filename, datePattern);
    }

    @Override
    public void activateOptions() {
        String parent = System.getProperty("jboss.server.log.dir");   //以jboss为例
        if(StringUtils.hasText(fileName)) {
            if(StringUtils.hasText(parent)) {
                fileName = parent.endsWith(File.separator) ? (parent + fileName) : (parent + File.separator + fileName);
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
