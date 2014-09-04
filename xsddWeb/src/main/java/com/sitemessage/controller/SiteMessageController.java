package com.sitemessage.controller;

import com.base.database.sitemessage.mapper.CustomPublicSitemessageMapper;
import com.base.database.sitemessage.model.CustomPublicSitemessage;
import com.base.database.sitemessage.model.PublicSitemessage;
import com.base.domains.CommonParmVO;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.utils.exception.Asserts;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.sitemessage.service.SiteMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Administrtor on 2014/9/4.
 */
@Controller
@RequestMapping("sitemessage")
public class SiteMessageController extends BaseAction {

    @Autowired
    private SiteMessageService siteMessageService;

    @RequestMapping("selectSiteMessage.do")
    /**分页查询站内信息列表*/
    public void selectSiteMessage(CommonParmVO commonParmVO,PublicSitemessage publicSitemessage){
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();

        List<CustomPublicSitemessage> customPublicSitemessages = siteMessageService.querySiteMessage(publicSitemessage, page);

        jsonBean.setList(customPublicSitemessages);
        jsonBean.setTotal((int) page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
    }

    @RequestMapping("readSiteMessage.do")
    public void readSiteMessage(PublicSitemessage publicSitemessage){
        Asserts.assertTrue(publicSitemessage.getId()!=null,"id不能为空");
        CustomPublicSitemessage customPublicSitemessage = siteMessageService.fetchSiteMessage(publicSitemessage);
        AjaxSupport.sendSuccessText("",customPublicSitemessage);
    }
}
