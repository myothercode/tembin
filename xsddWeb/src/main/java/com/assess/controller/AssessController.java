package com.assess.controller;

import com.base.database.publicd.mapper.PublicUserConfigMapper;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.OrderAutoAssess;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.PaypalQuery;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.orderassess.service.IOrderAutoAssess;
import org.kohsuke.rngom.parse.host.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private PublicUserConfigMapper publicUserConfigMapper;

    /**
     * 自动评价跳转界面
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/assess/assessManager.do")
    public ModelAndView assessManager(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        SessionVO c= SessionCacheSupport.getSessionVO();
        DataDictionarySupport.removePublicUserConfig(c.getId());
        List<PublicUserConfig> liconfig = DataDictionarySupport.getPublicUserConfigByType("autoAssessType", c.getId());
        if(liconfig!=null&&liconfig.size()>0){
            modelMap.put("userConfig",liconfig.get(0));
        }
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
    public void addAssessContent(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        String content = request.getParameter("content");
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
    public void savePublicUserConfig(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        String configValue = request.getParameter("configValue");
        PublicUserConfig puc = new PublicUserConfig();
        puc.setConfigName(configValue);
        puc.setConfigValue(configValue);
        puc.setCreateDate(new Date());
        puc.setUserId(c.getId());
        List<PublicUserConfig> liconfig = DataDictionarySupport.getPublicUserConfigByType("autoAssessType", c.getId());
        if(liconfig!=null&&liconfig.size()>0){
            puc.setId(liconfig.get(0).getId());
            this.publicUserConfigMapper.updateByPrimaryKeySelective(puc);
        }else{
            this.publicUserConfigMapper.insertSelective(puc);
        }
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
    public void delAssessContent(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String id = request.getParameter("id");
        this.iOrderAutoAssess.deltelAccessConent(Long.parseLong(id));
        AjaxSupport.sendSuccessText("","");
    }
}
