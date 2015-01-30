package com.userinventory.controller;

import com.base.database.inventory.model.UserInventorySet;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.PaypalQuery;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.exception.Asserts;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.inventory.service.IUserInventorySet;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrtor on 2015/1/27.
 */
@Controller
@RequestMapping("inventoryset")
public class UserInventorySetController extends BaseAction {
    @Autowired
    private IUserInventorySet iUserInventorySet;

    /**
     * 跳转界面
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/userinventorymanager.do")
    public ModelAndView getListingItem(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        return forword("inventory/userinventorymanager",modelMap);
    }

    /**
     * 加载数据
     * @param request
     * @param response
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/ajax/loadUserInventoryList.do")
    @ResponseBody
    public void continueWork(HttpServletRequest request,HttpServletResponse response,CommonParmVO commonParmVO,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<UserInventorySet> liins = this.iUserInventorySet.selectByOrgIdList(c.getOrgId());
        for(UserInventorySet uis:liins){
            uis.setDataType(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(uis.getDataType())).getName());
        }
        page.setTotalCount(liins.size());
        jsonBean.setList(liins);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    /**
     * 新增页面跳转
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/addInventorySet.do")
    public ModelAndView addInventorySet(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,CommonParmVO commonParmVO){
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.INVENTORY);
        modelMap.put("inventoryList",lidata);
        return forword("inventory/addInventorySet",modelMap);
    }

    /**
     * 编辑页面跳转
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/editInventorySet.do")
    public ModelAndView editInventorySet(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,CommonParmVO commonParmVO){
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.INVENTORY);
        modelMap.put("inventoryList",lidata);
        String id = request.getParameter("id");
        UserInventorySet us = this.iUserInventorySet.selectById(Long.parseLong(id));
        modelMap.put("inventory",us);
        String type = request.getParameter("type");
        if(type!=null&&!"".equals(type)){
            modelMap.put("type",type);
        }
        return forword("inventory/addInventorySet",modelMap);
    }

    /**
     * 保存数据
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/saveInventorySet.do")
    @ResponseBody
    public void saveInventorySet(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,UserInventorySet userInventorySet) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        userInventorySet.setOrgId(c.getOrgId());
        Asserts.assertTrue(this.iUserInventorySet.selectDistinc(userInventorySet.getDataType(),userInventorySet.getUserName(),c.getOrgId())==null,"该公司有相同用户配置了该仓库！");
        this.iUserInventorySet.saveUserInventorySet(userInventorySet);
        AjaxSupport.sendSuccessText("","操作成功!");
    }


    /**
     * 删除设置
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/delInventorySet.do")
    @ResponseBody
    public void delInventorySet(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id = request.getParameter("id");
        this.iUserInventorySet.deleteInventorySet(Long.parseLong(id));
        AjaxSupport.sendSuccessText("","操作成功!");
    }

}
