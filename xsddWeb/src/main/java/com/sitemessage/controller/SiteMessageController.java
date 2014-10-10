package com.sitemessage.controller;

import com.base.database.sitemessage.mapper.CustomPublicSitemessageMapper;
import com.base.database.sitemessage.model.CustomPublicSitemessage;
import com.base.database.sitemessage.model.PublicSitemessage;
import com.base.domains.CommonParmVO;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.sitemessage.service.SiteMessageService;
import com.sitemessage.service.SiteMessageStatic;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/9/4.
 */
@Controller
@RequestMapping("sitemessage")
public class SiteMessageController extends BaseAction {

    @Autowired
    private SiteMessageService siteMessageService;

    /**消息列表页面*/
    @RequestMapping("siteMessagePage.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView siteMessagePage(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        modelMap.put("mtype", SiteMessageStatic.messageMap);
        return forword("/sitemessage/siteMessagePage",modelMap);
    }

    /**查看消息的页面*/
    @RequestMapping("readMessagePage.do")
    public ModelAndView readMessagePage(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,Long messageId){
        if(messageId!=null){
            modelMap.put("messageId",messageId);
        }
        return forword("/sitemessage/readMessagePage",modelMap);
    }

    @RequestMapping("selectSiteMessage.do")
    @ResponseBody
    /**分页查询站内信息列表*/
    public void selectSiteMessage(CommonParmVO commonParmVO,PublicSitemessage publicSitemessage){
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();

        List<CustomPublicSitemessage> customPublicSitemessages = siteMessageService.querySiteMessage(publicSitemessage, page);

        if("num".equalsIgnoreCase(commonParmVO.getStrV1())){
            String r="{\"systemMessageNum\":\""+page.getTotalCount()+"\",\"ebayMessageNum\":\""+0+"\"}";
            AjaxSupport.sendSuccessText("",r);
            return;
        }
        if(customPublicSitemessages!=null){
            for (CustomPublicSitemessage c : customPublicSitemessages){
                if(StringUtils.isNotEmpty(c.getMessageType())){
                   String ty=StringUtils.replaceEach(c.getMessageType(), new String[]{"_SUCCESS", "_FAIL"}, new String[]{"", ""});
                    String nty=SiteMessageStatic.messageMap.get(ty)==null?"":(String)SiteMessageStatic.messageMap.get(ty);
                    c.setMessageType(nty);
                }
            }
        }

        jsonBean.setList(customPublicSitemessages);
        jsonBean.setTotal((int) page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
    }

    /**查看并标记*/
    @RequestMapping("readSiteMessage.do")
    @ResponseBody
    public void readSiteMessage(PublicSitemessage publicSitemessage){
        Asserts.assertTrue(publicSitemessage.getId()!=null,"id不能为空");
        CustomPublicSitemessage customPublicSitemessage = siteMessageService.fetchSiteMessage(publicSitemessage);
        AjaxSupport.sendSuccessText("",customPublicSitemessage);
    }

    @RequestMapping("markReaded.do")
    @ResponseBody
    @AvoidDuplicateSubmission(needRemoveToken = true)
    /**标记*/
    public void markReaded(String[] ids){
        Map map=new HashMap();
        if (!ObjectUtils.isLogicalNull(ids)){
            map.put("idArray",ids);
        }
        siteMessageService.batchSetReaded(map);
        AjaxSupport.sendSuccessText("","标记成功！");
    }
}
