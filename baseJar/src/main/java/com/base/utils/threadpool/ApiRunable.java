package com.base.utils.threadpool;

import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cxfclient.CXFPostClient;
import com.base.utils.httpclient.ApiHeader;
import com.base.utils.threadpoolimplements.ThreadPoolBaseInterFace;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageService;
import com.sitemessage.service.SiteMessageStatic;
import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by wula on 2014/8/6.
 */
public class ApiRunable implements Runnable {
    static Logger logger = Logger.getLogger(ApiRunable.class);

    private static final String enCode="utf-8";//使用utf8编码
    private UsercontrollerDevAccountExtend devAccountExtend;
    private String xml;
    private String url;
    private TaskMessageVO taskMessageVO;

    @Override
    public void run() {
        if(xml==null || url==null || devAccountExtend ==null){return ;}

        List<BasicHeader> headers=null;
        if(ApiHeader.DISPUTE_API_HEADER.equalsIgnoreCase(devAccountExtend.getHeaderType())){
            headers=ApiHeader.getDisputeApiHeader(devAccountExtend);
        }else {
            headers = ApiHeader.getApiHeader(devAccountExtend);
        }

        CXFPostClient cxfPostClient = (CXFPostClient) ApplicationContextUtil.getBean(CXFPostClient.class);
        String res = null;
        try {
            res = cxfPostClient.sendPostAction(headers,url,xml);
        } catch (Exception e) {
            logger.error(taskMessageVO.getMessageTo()+":标题"+taskMessageVO.getMessageTitle()+"请求post出错!"+xml,e);
        }

        afterPost(res);


        return;
    }

    public ApiRunable(UsercontrollerDevAccountExtend dev, String xml, String url,TaskMessageVO taskMessageVO){
        this.devAccountExtend=dev;
        this.url=url;
        this.xml=xml;
        this.taskMessageVO=taskMessageVO;
    }

    /**请求完成后要执行的方法*/
    public void afterPost(String res){
        ApplicationContext applicationContext = ApplicationContextUtil.getContext();
        Map<String, ThreadPoolBaseInterFace> map = applicationContext
                .getBeansOfType(ThreadPoolBaseInterFace.class, false, true);

        for (ThreadPoolBaseInterFace f : map.values()){
            if(taskMessageVO.getBeanNameType().equals(f.getType())){
                f.doWork(res,taskMessageVO);//执行自定义的方法
                if(taskMessageVO.isWeitherAddMessage()){
                    addMessage(res);//添加信息
                }
            }
        }

    }



    public void addMessage(String res){
        SiteMessageService siteMessageService= (SiteMessageService) ApplicationContextUtil.getBean(SiteMessageService.class);
        if(StringUtils.isEmpty(res)){
            taskMessageVO.setMessageTitle(taskMessageVO.getMessageTitle()+"执行失败！");
            taskMessageVO.setMessageContext(taskMessageVO.getMessageContext() + ",没有获取到值res!");
            taskMessageVO.setMessageType(taskMessageVO.getMessageType()+"_FAIL");
            siteMessageService.addSiteMessage(taskMessageVO);
            return;
        }

        String ack="";
        try {
            ack = SamplePaseXml.getVFromXmlString(res, "Ack");
            if ("Success".equalsIgnoreCase(ack)) {
                //String itemId = SamplePaseXml.getVFromXmlString(res, "ItemID");
                taskMessageVO.setMessageTitle(taskMessageVO.getMessageTitle()+"执行成功");
                taskMessageVO.setMessageContext(taskMessageVO.getMessageContext() + ",执行成功");
                taskMessageVO.setMessageType(taskMessageVO.getMessageType()+"_SUCCESS");

            }else if("Warning".equalsIgnoreCase(ack)){
                String errors  = SamplePaseXml.getSpecifyElementTextAllInOne(res,"Errors","LongMessage");
                taskMessageVO.setMessageTitle(taskMessageVO.getMessageTitle()+"执行成功，有警告！");
                taskMessageVO.setMessageContext(taskMessageVO.getMessageContext() + ",警告信息:"+errors);
                taskMessageVO.setMessageType(taskMessageVO.getMessageType()+"_SUCCESS");

            } else {
                String errors  = SamplePaseXml.getSpecifyElementTextAllInOne(res,"Errors","LongMessage");
                taskMessageVO.setMessageTitle(taskMessageVO.getMessageTitle()+"执行失败！");
                taskMessageVO.setMessageContext(taskMessageVO.getMessageContext() + ",失败原因:"+errors);
                taskMessageVO.setMessageType(taskMessageVO.getMessageType()+"_FAIL");
            }
        } catch (Exception e) {
            logger.error("解析xml出错",e);
            taskMessageVO.setMessageType(taskMessageVO.getMessageType());
            taskMessageVO.setMessageTitle(taskMessageVO.getMessageTitle()+"解析报错！");
            taskMessageVO.setMessageContext("解析出错！请稍后查看ebay网站是否刊登成功!");
        }finally {
            siteMessageService.addSiteMessage(taskMessageVO);
        }



    }

}
