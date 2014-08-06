package com.module.controller;

import com.base.database.trading.model.tradingTemplateInitTable;
import com.base.domains.CommonParmVO;
import com.base.domains.querypojos.TemplateInitTableQuery;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.common.ObjectUtils;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingTemplateInitTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/５.
 */
@Controller
public class TemplateInitTableController extends BaseAction{

    @Autowired
    private ITradingTemplateInitTable iTradingTemplateInitTable;
    /**
     * 模板模块
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/TemplateInitTableList.do")
    public ModelAndView TemplateInitTableList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("module/templateinittable/templateinittableList",modelMap);
    }

    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadTemplateInitTableList.do")
    @ResponseBody
    public void loadTemplateInitTableList(CommonParmVO commonParmVO){
        Map m = new HashMap();
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<TemplateInitTableQuery> TemplateInitTable = this.iTradingTemplateInitTable.selectByTemplateInitTableList(m,page);
        jsonBean.setList(TemplateInitTable);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    /**
     * 模板添加
     * @param request
     * @param response
     * @param modelMap
     * @return
     */

    @RequestMapping("/addTemplateInitTable.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addTemplateInitTable(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("module/templateinittable/addTemplateInitTable",modelMap);
    }
    /**
     * 模板编辑
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/editTemplateInitTable.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editTemplateInitTable(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        Map m = new HashMap();
        m.put("id",request.getParameter("id"));
        List<TemplateInitTableQuery> TemplateInitTableli = this.iTradingTemplateInitTable.selectByTemplateInitTableList(m);
        modelMap.put("TemplateInitTable",TemplateInitTableli.get(0));
        return forword("module/templateinittable/addTemplateInitTable",modelMap);
    }

    @RequestMapping("/viewTemplateInitTable.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewTemplateInitTable(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        Map m = new HashMap();
        m.put("id",request.getParameter("id"));
        List<TemplateInitTableQuery> TemplateInitTableli = this.iTradingTemplateInitTable.selectByTemplateInitTableList(m);
        modelMap.put("TemplateInitTable",TemplateInitTableli.get(0));
        return forword("module/templateinittable/viewTemplateInitTable",modelMap);
    }
    /**
     * 保存模板数据
     * @param request
     * @throws Exception
     */
    @RequestMapping("/ajax/saveTemplateInitTable.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveTemplateInitTable(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String id=request.getParameter("id");
        String templatehtml=request.getParameter("templateHtml");
        String tLevel=request.getParameter("level");
        String templateName=request.getParameter("templateName");
        tradingTemplateInitTable tm=new tradingTemplateInitTable();
        if(!ObjectUtils.isLogicalNull(id)){
            tm.setId(Long.parseLong(id));
        }
        tm.setTemplateHtml(templatehtml);
        tm.setTLevel(tLevel);
        tm.setTemplateName(templateName);
        iTradingTemplateInitTable.saveTemplateInitTable(tm);
        AjaxSupport.sendSuccessText("message", "操作成功！");
    }
}
