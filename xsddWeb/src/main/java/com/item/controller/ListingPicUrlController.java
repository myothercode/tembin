package com.item.controller;

import com.base.aboutpaypal.service.PayPalService;
import com.base.database.trading.model.*;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.PaypalQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.sampleapixml.APINameStatic;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DateUtils;
import com.base.utils.common.EncryptionUtil;
import com.base.utils.common.ObjectUtils;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.SamplePaseXml;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.sitemessage.service.SiteMessageStatic;
import com.trading.service.ITradingDataDictionary;
import com.trading.service.ITradingListingPicUrl;
import com.trading.service.ITradingPayPal;
import com.trading.service.IUsercontrollerEbayAccount;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by cz on 2014/7/28.
 */
@Controller
public class ListingPicUrlController extends BaseAction{


    @Autowired
    private ITradingListingPicUrl iTradingListingPicUrl;
    @Autowired
    private IUsercontrollerEbayAccount iUsercontrollerEbayAccount;
    @Value("${EBAY.API.URL}")
    private String apiUrl;

    @RequestMapping("/ajax/saveListingPicUrl.do")
    @ResponseBody
    public void saveListingPicUrl(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        String urls = request.getParameter("urls");
        String ebayid = request.getParameter("ebayid");
        String siteid = request.getParameter("siteid");
        String [] urlss = urls.split(",");
        List<TradingListingpicUrl> lipic = new ArrayList<TradingListingpicUrl>();

        for(int i = 0;i<urlss.length;i++){
            String url = urlss[i];
            String mackid = EncryptionUtil.md5Encrypt(url);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, 10);
            List<TradingListingpicUrl> liplu = this.iTradingListingPicUrl.selectByMackId(mackid);
            if(liplu==null||liplu.size()==0){
                TradingListingpicUrl tlu = new TradingListingpicUrl();
                tlu.setUrl(url);
                tlu.setCreateDate(new Date());
                tlu.setMackId(EncryptionUtil.md5Encrypt(url));
                //tlu.setEndDate(c.getTime());
                tlu.setEbayAccountId(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(siteid)).getName1());
                tlu.setSiteId(siteid);
                this.iTradingListingPicUrl.saveListingPicUrl(tlu);
                lipic.add(tlu);
                UsercontrollerEbayAccount ua = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(ebayid));
                String picName = url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("."));

                String xml=SamplePaseXml.uploadEbayImage(ua,url,picName);//获取xml

                AddApiTask addApiTask = new AddApiTask();
                UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
                d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(siteid)).getName1());
                d.setApiCallName(APINameStatic.UploadSiteHostedPictures);
                TaskMessageVO taskMessageVO=new TaskMessageVO();
                taskMessageVO.setMessageContext("上传图片");
                taskMessageVO.setMessageTitle("上传图片");
                taskMessageVO.setMessageType(SiteMessageStatic.LISTING_PIC_URL_MESSAGE);
                taskMessageVO.setBeanNameType(SiteMessageStatic.LISTING_PIC_URL_BEAN);
                taskMessageVO.setMessageFrom("system");
                SessionVO sessionVO=SessionCacheSupport.getSessionVO();
                taskMessageVO.setMessageTo(sessionVO.getId());
                taskMessageVO.setObjClass(tlu);
                taskMessageVO.setWeithSendSuccessMessage(false);
                System.out.println(xml);
                addApiTask.execDelayReturn(d, xml, apiUrl, taskMessageVO);
            }else {
                lipic.add(liplu.get(0));
            }
        }
        AjaxSupport.sendSuccessText("",lipic);
    }
}
