package com.base.utils.scheduleother.dorun;

import com.base.utils.scheduleother.StaticParam;
import com.base.utils.scheduleother.domain.ImageCheckVO;
import com.base.utils.threadpool.TaskPool;
import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2014/11/14.
 * 图片检查任务，图片获取线程
 */
public class ImageCheckTaskPut implements ScheduleOtherBase{
    static Logger logger = Logger.getLogger(ImageCheckTaskPut.class);

    private ImageCheckVO imageCheckVO;

    public ImageCheckTaskPut(ImageCheckVO imageCheckVO1){
        this.imageCheckVO=imageCheckVO1;
    }

    @Override
    public String stype() {
        return StaticParam.IMG_CHECK_SC;
    }

    @Override
    public void run() {
        try {
            if(TaskPool.SCBaseQueue.size()>50){return;}
            TaskPool.SCBaseQueue.put(imageCheckVO);
        } catch (Exception e) {
            logger.error("放入队列出错",e);
            return;
        }
    }
}
