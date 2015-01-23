package com.base.utils.threadpoolimplements;

import com.base.database.task.model.TaskGetMessages;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.threadpool.TaskMessageVO;
import com.sitemessage.service.SiteMessageStatic;
import com.task.service.ITaskGetMessages;
import com.trading.service.ITradingMessageGetmymessage;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/9/13.
 */
@Service
public class SynchronizeGetMessageImpl implements ThreadPoolBaseInterFace {
    static Logger logger = Logger.getLogger(SynchronizeGetMessageImpl.class);
    @Autowired
    private ITradingMessageGetmymessage iTradingMessageGetmymessage;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ITaskGetMessages iTaskGetMessages;

    @Value("${EBAY.API.URL}")
    private String apiUrl;
    @Override
    public <T> void doWork(String res, T... t) {
        if(StringUtils.isEmpty(res)){return;}
        TaskMessageVO taskMessageVO = (TaskMessageVO)t[0];
        Map map=(Map)taskMessageVO.getObjClass();
        Long accountId= (Long) map.get("accountId");
        Long ebay= (Long) map.get("ebay");
        List<TaskGetMessages> taskGetMessageses= iTaskGetMessages.selectTaskGetMessagesByFlagIsFalseOrderBysaveTime();
        if(taskGetMessageses!=null&&taskGetMessageses.size()>0){
            Long ebayId=taskGetMessageses.get(0).getEbayid();
            if(ebay!=ebayId){
                Integer flag=taskGetMessageses.get(0).getTokenflag();
                for(TaskGetMessages taskGetMessages:taskGetMessageses){
                    Integer flag1=taskGetMessages.getTokenflag();
                    if(flag1<=flag){
                        flag=flag1;
                    }
                }
                for(TaskGetMessages taskGetMessages:taskGetMessageses){
                    if(ebay==taskGetMessages.getEbayid()){
                        flag=flag-1;
                        taskGetMessages.setTokenflag(flag);
                        taskGetMessages.setLastsyctime(new Date());
                        iTaskGetMessages.saveListTaskGetMessages(taskGetMessages);
                    }
                }
            }
        }
        /*UsercontrollerDevAccountExtend dev =(UsercontrollerDevAccountExtend)map.get("dev");
        String ack = null;
        try {
            ack = SamplePaseXml.getVFromXmlString(res, "Ack");
            if ("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)) {
                if("Warning".equalsIgnoreCase(ack)){
                    String errors = SamplePaseXml.getWarningInformation(res);
                    logger.error("获取Message有警告!" + errors);
                }
                List<Element> messages = GetMyMessageAPI.getMessages(res);
                for(Element message:messages){
                    TradingMessageGetmymessage ms= GetMyMessageAPI.addDatabase(message, accountId, ebay);//保存到数据库
                    dev.setApiSiteid("0");
                    //真实环境
              *//*      UsercontrollerDevAccountExtend dev=new UsercontrollerDevAccountExtend();
                    dev.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
                    dev.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
                    dev.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
                    dev.setApiCompatibilityLevel("881");
                    dev.setApiSiteid("0");*//*
                    dev.setApiCallName(APINameStatic.GetMyMessages);
                    Map parms=new HashMap();
                    parms.put("messageId", ms.getMessageid());
                    parms.put("ebayId",ms.getEbayAccountId());
                    parms.put("devAccount",dev);
                    //测试环境
                    parms.put("url",apiUrl);
                    //真实环境
                 *//*   parms.put("url","https://api.ebay.com/ws/api.dll");*//*
                    parms.put("userInfoService",userInfoService);
                    String content=GetMyMessageAPI.getContent(parms);
                    ms.setTextHtml(StringEscapeUtils.escapeXml(content));
                    List<TradingMessageGetmymessage> getmymessages=iTradingMessageGetmymessage.selectMessageGetmymessageByMessageId(ms.getMessageid());
                    if(getmymessages.size()>0){
                        ms.setId(getmymessages.get(0).getId());
                    }
                    ms.setCreateUser(taskMessageVO.getMessageTo());
                    iTradingMessageGetmymessage.saveMessageGetmymessage(ms);
                }
            }else {
                String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                logger.error("Message API调用失败!" + errors);
            }
        } catch (Exception e) {
            logger.error("解析xml出错,请稍后到ebay网站确认结果199"+res,e);
            return;
        }*/

    }

    @Override
    public String getType() {
        return SiteMessageStatic.SYNCHRONIZE_GET_MESSAGE_BEAN;
    }
}
