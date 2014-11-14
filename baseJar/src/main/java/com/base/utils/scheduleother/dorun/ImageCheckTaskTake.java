package com.base.utils.scheduleother.dorun;

import com.base.utils.scheduleother.StaticParam;
import com.base.utils.scheduleother.domain.ImageCheckVO;
import com.base.utils.scheduleother.domain.SCBaseVO;
import com.base.utils.threadpool.TaskPool;
import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2014/11/14.
 * 取出图片检查任务队列
 */
public class ImageCheckTaskTake implements ScheduleOtherBase {
    static Logger logger = Logger.getLogger(ImageCheckTaskTake.class);

    private ImageCheckVO imageCheckVO;
    public ImageCheckTaskTake(ImageCheckVO imageCheckVO1){
        this.imageCheckVO=imageCheckVO1;
    }

    @Override
    public String stype() {
        return StaticParam.IMG_CHECK_SC_TAKE;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(StaticParam.IMG_CHECK_SC_TAKE);
        while (true){
            SCBaseVO scBaseVO=null;
            try {
                scBaseVO = TaskPool.SCBaseQueue.take();
                if(scBaseVO==null){continue;}
                ImageCheckVO imageCheckVO1= (ImageCheckVO) scBaseVO;
                logger.info(imageCheckVO1.getUrl());
            } catch (Exception e) {
                logger.error("取出图片检查队列出错",e);
                continue;
            }
        }

    }
}
