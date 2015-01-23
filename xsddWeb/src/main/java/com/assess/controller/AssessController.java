package com.assess.controller;

import com.base.database.publicd.mapper.PublicUserConfigMapper;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.OrderAutoAssess;
import com.base.database.trading.model.TradingAssessViewSet;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.PaypalQuery;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.orderassess.service.IOrderAutoAssess;
import com.publicd.service.IPublicUserConfig;
import com.trading.service.ITradingAssessViewSet;
import org.apache.http.impl.cookie.DateParseException;
import org.kohsuke.rngom.parse.host.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrtor on 2014/11/19.
 * 自动评价管理Controller
 */
@Controller
public class AssessController extends BaseAction{
    @Autowired
    private IOrderAutoAssess iOrderAutoAssess;
    @Autowired
    private ITradingAssessViewSet iTradingAssessViewSet;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    @Autowired
    private IPublicUserConfig iPublicUserConfig;

    /**
     * 自动评价跳转界面
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/assess/assessManager.do")
    public ModelAndView assessManager(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws DateParseException {
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<Long> liuser = new ArrayList<Long>();
        liuser.add(c.getOrgId());
        List<PublicUserConfig> liconfig = this.iPublicUserConfig.selectUserConfigByItemTypeListUser("autoAssessType", liuser);
        if(liconfig!=null&&liconfig.size()>0){
            modelMap.put("userConfig",liconfig.get(0));
        }
        TradingAssessViewSet ta = this.iTradingAssessViewSet.selectByUserid(c.getId());
        modelMap.put("ta",ta);
        return forword("assess/assessmanager",modelMap);
    }

    /**
     * 添加评价内容
     * @param request
     * @param response
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/ajax/addAssessContent.do")
    @ResponseBody
    public void addAssessContent(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        String content = request.getParameter("content");
        content = new String(content.getBytes("ISO-8859-1"),"UTF-8");
        String id = request.getParameter("id");
        String dataType = request.getParameter("datatype")==null?"1":request.getParameter("datatype");
        OrderAutoAssess oaa = new OrderAutoAssess();
        if(id!=null){
            oaa.setId(Long.parseLong(id));
        }
        oaa.setAssesscontent(content);
        oaa.setCreateDate(new Date());
        oaa.setCreateUser(c.getId());
        oaa.setDataType(dataType);
        this.iOrderAutoAssess.saveAssessConent(oaa);
        AjaxSupport.sendSuccessText("success", "success");
    }

    /**
     * 添加评价内容
     * @param request
     * @param response
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/ajax/loadAssessList.do")
    @ResponseBody
    public void loadAssessList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<OrderAutoAssess> lioa = this.iOrderAutoAssess.selectAssessList(c.getId());
        PageJsonBean jsonBean= new PageJsonBean();
        Page page=jsonBean.toPage();
        page.setPageSize(1000);
        jsonBean.setList(lioa);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
    }

    /**
     * 保存评价设置
     * @param request
     * @param response
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/ajax/savePublicUserConfig.do")
    @ResponseBody
    public void savePublicUserConfig(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        String configValue = request.getParameter("configValue");
        PublicUserConfig puc = new PublicUserConfig();
        puc.setConfigName(configValue);
        puc.setConfigValue(configValue);
        puc.setCreateDate(new Date());
        puc.setUserId(c.getOrgId());
        puc.setConfigType("autoAssessType");
        List<PublicUserConfig> liconfig = DataDictionarySupport.getPublicUserConfigByType("autoAssessType", c.getOrgId());
        if(liconfig!=null&&liconfig.size()>0){
            puc.setId(liconfig.get(0).getId());
        }
        this.iPublicUserConfig.saveUserConfig(puc);
        AjaxSupport.sendSuccessText("","");
    }
    /**
     * 删除评价内容
     * @param request
     * @param response
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/ajax/delAssessContent.do")
    @ResponseBody
    public void delAssessContent(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String id = request.getParameter("id");
        this.iOrderAutoAssess.deltelAccessConent(Long.parseLong(id));
        AjaxSupport.sendSuccessText("","");
    }


    /**
     * 保存评价设置
     * @param request
     * @param response
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/ajax/saveAssessViewSet.do")
    @ResponseBody
    public void saveAssessViewSet(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        String appRange = request.getParameter("appRange").equals("undefined")?"":request.getParameter("appRange");
        String setView = request.getParameter("setView").equals("undefined")?"":request.getParameter("setView");
        TradingAssessViewSet ta = this.iTradingAssessViewSet.selectByUserid(c.getId());
        if(ta==null){
            ta = new TradingAssessViewSet();
            ta.setApprange(appRange);
            ta.setSetview(setView);
            ta.setCreateUser(c.getId());
            ta.setCreateDate(new Date());
        }else{
            ta.setApprange(appRange);
            ta.setSetview(setView);
        }
        this.iTradingAssessViewSet.save(ta);

        AjaxSupport.sendSuccessText("",ta);
    }
}
