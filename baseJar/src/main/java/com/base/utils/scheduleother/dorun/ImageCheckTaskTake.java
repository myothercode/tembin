package com.base.utils.scheduleother.dorun;

import com.base.database.trading.mapper.TradingListingpicUrlMapper;
import com.base.database.trading.model.TradingListingpicUrl;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.imageinfo.mapper.ImageInfoMapper;
import com.base.sampleapixml.APINameStatic;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.DateUtils;
import com.base.utils.imageManage.ImageUtil;
import com.base.utils.scheduleother.StaticParam;
import com.base.utils.scheduleother.domain.ImageCheckVO;
import com.base.utils.scheduleother.domain.SCBaseVO;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskPool;
import com.base.utils.xmlutils.SamplePaseXml;
import com.trading.service.IUsercontrollerEbayAccount;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/11/14.
 * 取出图片检查任务队列
 */
public class ImageCheckTaskTake implements ScheduleOtherBase {
    static Logger logger = Logger.getLogger(ImageCheckTaskTake.class);

    public ImageCheckTaskTake(){}
    private ImageCheckVO imageCheckVO;
    public ImageCheckTaskTake(ImageCheckVO imageCheckVO1){
        this.imageCheckVO=imageCheckVO1;
    }

    @Override
    public String stype() {
        return StaticParam.IMG_CHECK_SC_TAKE;
    }

    @Override
    public Integer cyclesTime() {
        return 0;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(StaticParam.IMG_CHECK_SC_TAKE);
        while (true){
            SCBaseVO scBaseVO=null;
            try {
                scBaseVO = TaskPool.SCBaseQueue.take();
                //System.out.println(TaskPool.SCBaseQueue.size() + "个...");
                if(scBaseVO==null){continue;}
                ImageCheckVO imageCheckVO1= (ImageCheckVO) scBaseVO;
                TradingListingpicUrlMapper picu=ApplicationContextUtil.getBean(TradingListingpicUrlMapper.class);
                //首先检查ebay图片地址是否有效
                if(StringUtils.isNotEmpty(imageCheckVO1.getEbayUrl())){
                    int t=DateUtils.daysBetween(imageCheckVO1.getCreateDate(),new Date());
                    boolean b= ImageUtil.isPicIsAvail(imageCheckVO1.getEbayUrl());
                    if (b && t<=28){
                        TradingListingpicUrl listingpicUrl = picu.selectByPrimaryKey(imageCheckVO1.getId());
                        listingpicUrl.setCheckTimer(new Date());
                        updateImgUrl(picu, listingpicUrl);
                        continue;}//如果图片正常，那么继续下一张

                }



                if (StringUtils.isEmpty(imageCheckVO1.getUrl())){
                    picu.deleteByPrimaryKey(imageCheckVO1.getId());
                    continue;
                }else {
                    boolean b=ImageUtil.isPicIsAvail(imageCheckVO1.getUrl());
                    if(!b){
                        picu.deleteByPrimaryKey(imageCheckVO1.getId());
                        continue;
                    }//如果原始图片地址不存在，那么就删除该图片
                    else {//如果原始地址图片存在，那么就重新上传
                        uploadIMG(imageCheckVO1);
                    }
                }

            } catch (Exception e) {
                logger.error("取出图片检查队列出错",e);
                continue;
            }
        }
    }

    /**更新图片时间等信息*/
    private void updateImgUrl(TradingListingpicUrlMapper mapper,TradingListingpicUrl urlVO){
        urlVO.setCheckTimer(new Date());
        mapper.updateByPrimaryKey(urlVO);
    }

    /**上传图片*/
    private void uploadIMG(ImageCheckVO imageCheckVO1) throws Exception{
        TradingListingpicUrlMapper picu=ApplicationContextUtil.getBean(TradingListingpicUrlMapper.class);
        IUsercontrollerEbayAccount iUsercontrollerEbayAccount=ApplicationContextUtil.getBean(IUsercontrollerEbayAccount.class);
        UsercontrollerEbayAccount ua = iUsercontrollerEbayAccount.selectById(imageCheckVO1.getEbayAccountId());
        String picName = imageCheckVO1.getUrl().substring(imageCheckVO1.getUrl().lastIndexOf("/") + 1, imageCheckVO1.getUrl().lastIndexOf("."));
        String xml= SamplePaseXml.uploadEbayImage(ua, imageCheckVO1.getUrl(), picName);//获取xml

        AddApiTask addApiTask = new AddApiTask();
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        d.setApiSiteid(StringUtils.isEmpty(imageCheckVO1.getSiteId())?"0":imageCheckVO1.getSiteId() );
        d.setApiCallName(APINameStatic.UploadSiteHostedPictures);
        CommAutowiredClass commAutowiredClass=ApplicationContextUtil.getBean(CommAutowiredClass.class);
        Map<String ,String> map = addApiTask.exec(d,xml,commAutowiredClass.apiUrl);
        String ebpRes = map.get("message");
        String ebpAck = null;
        ebpAck = SamplePaseXml.getVFromXmlString(ebpRes, "Ack");
        if ("Success".equalsIgnoreCase(ebpAck) || "Warning".equalsIgnoreCase(ebpAck)) {
            Document document= SamplePaseXml.formatStr2Doc(ebpRes);
            Element rootElt = document.getRootElement();
            Element picelt = rootElt.element("SiteHostedPictureDetails");
            String fullUrl = picelt.elementText("FullURL");
            String enddate = picelt.elementText("UseByDate");

            TradingListingpicUrl listingpicUrl = picu.selectByPrimaryKey(imageCheckVO1.getId());
            listingpicUrl.setCheckFlag("1");
            listingpicUrl.setEbayurl(fullUrl);
            listingpicUrl.setEndDate(DateUtils.returnDate(enddate));
            listingpicUrl.setCheckTimer(new Date());
            listingpicUrl.setCreateDate(new Date());
            //picu.updateByPrimaryKey(listingpicUrl);
            updateImgUrl(picu, listingpicUrl);

        }else {
            TradingListingpicUrl listingpicUrl = picu.selectByPrimaryKey(imageCheckVO1.getId());
            listingpicUrl.setCheckFlag("2");
            listingpicUrl.setCheckTimer(new Date());
            updateImgUrl(picu,listingpicUrl);
        }
    }

}
