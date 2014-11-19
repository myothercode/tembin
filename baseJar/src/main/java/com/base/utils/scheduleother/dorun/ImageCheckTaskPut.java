package com.base.utils.scheduleother.dorun;

import com.base.imageinfo.mapper.ImageInfoMapper;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.scheduleother.StaticParam;
import com.base.utils.scheduleother.domain.ImageCheckVO;
import com.base.utils.threadpool.TaskPool;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/11/14.
 * 图片检查任务，图片获取线程
 */
public class ImageCheckTaskPut implements ScheduleOtherBase{
    static Logger logger = Logger.getLogger(ImageCheckTaskPut.class);
    public ImageCheckTaskPut(){}
    private ImageCheckVO imageCheckVO;

    public ImageCheckTaskPut(ImageCheckVO imageCheckVO1){
        this.imageCheckVO=imageCheckVO1;
    }

    @Override
    public String stype() {//任务类型
        return StaticParam.IMG_CHECK_SC;
    }

    @Override
    public Integer cyclesTime() {//间隔时间
        return 5;
    }

    @Override
    public void run() {

        try {
            if(TaskPool.SCBaseQueue.size()>30){return;}
            ImageInfoMapper imageInfoMapper= ApplicationContextUtil.getBean(ImageInfoMapper.class);
            Map map =new HashMap();
            List<ImageCheckVO> imageCheckVOList = imageInfoMapper.queryCheckImage(map);
            if(imageCheckVOList!=null && !imageCheckVOList.isEmpty()){
                for (ImageCheckVO v:imageCheckVOList){
                    if(!TaskPool.SCBaseQueue.contains(v)){
                        TaskPool.SCBaseQueue.put(v);
                    }else {
                        logger.info("已经有数据！");
                    }
                }
            }
            System.out.println(TaskPool.SCBaseQueue.size() + "个...");
        } catch (Exception e) {
            logger.error("放入队列出错",e);
            return;
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ImageCheckVO imageCheckVO1=new ImageCheckVO();
        imageCheckVO1.setId(2L);
        imageCheckVO1.setUrl("http://www.netbian.com/d/file/20141023/f27171362c5f0a35f217e18a18a34a63.jpg");
        ImageCheckVO imageCheckVO2=new ImageCheckVO();
        imageCheckVO2.setId(2L);
        imageCheckVO2.setUrl("http://www.netbian.com/d/file/20141023/f27171362c5f0a35f217e18a18a34a63.jpg");
        TaskPool.SCBaseQueue.put(imageCheckVO1);

        System.out.println(TaskPool.SCBaseQueue.contains(imageCheckVO2));
        System.out.println(imageCheckVO1.equals(imageCheckVO2));
    }
}
