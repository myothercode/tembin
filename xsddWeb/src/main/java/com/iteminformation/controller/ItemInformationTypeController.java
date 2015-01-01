package com.iteminformation.controller;

import com.base.database.publicd.model.PublicItemInformation;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.ItemInformationQuery;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.publicd.service.IPublicItemInformation;
import com.publicd.service.IPublicUserConfig;
import org.apache.commons.lang.StringUtils;
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
 * Created by Administrtor on 2014/9/4.
 */
@Controller
@RequestMapping("informationType")
public class ItemInformationTypeController extends BaseAction {
    @Autowired
    private IPublicItemInformation iPublicItemInformation;
    @Autowired
    private IPublicUserConfig iPublicUserConfig;
    /*
   *商品分类列表
   */
    @RequestMapping("/itemInformationTypeList.do")
    public ModelAndView itemInformationList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("/itemInformationType/itemInformation",modelMap);
    }
    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadItemInformationTypeList.do")
    @ResponseBody
    public void loadItemInformationList(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        Map m = new HashMap();
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        if(sessionVO!=null){
            m.put("userID",sessionVO.getId());
        }else{
            m.put("userID",null);
        }
        List<ItemInformationQuery> list=iPublicItemInformation.selectItemInformationByType(m, page);
        jsonBean.setList(list);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
    /*
     *初始化添加商品分类界面
     */
    @RequestMapping("/addType.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addType(HttpServletRequest request,HttpServletResponse response,
                                           @ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        String id1=request.getParameter("id");
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        Long id=null;
        if(StringUtils.isNotBlank(id1)){
            id=Long.valueOf(id1);
        }
        List<PublicUserConfig> types=iPublicUserConfig.selectUserConfigByItemType("itemType",sessionVO.getId());
        modelMap.put("types",types);
        modelMap.put("id",id);
        return forword("/itemInformationType/addType",modelMap);
    }
    /*
   *保存商品分类
   */
    @RequestMapping("/ajax/saveinformationType.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveinformationType(HttpServletRequest request) throws Exception {
        String typeName=request.getParameter("typeName");
        String parent=request.getParameter("parent");
        if(!StringUtils.isNotBlank(typeName)){
            AjaxSupport.sendFailText("fail","分类名称不能为空");
        }
        PublicUserConfig type=new PublicUserConfig();
        type.setConfigType("itemType");
        type.setConfigName(typeName);
        if(!"0".equals(parent)){
            type.setItemParentId(parent);
            PublicUserConfig config=iPublicUserConfig.selectUserConfigById(Long.valueOf(parent));
            if(config!=null){
                type.setItemLevel((Integer.valueOf(config.getItemLevel())+1)+"");
            }
        }else{
            type.setItemLevel("1");
        }
        iPublicUserConfig.saveUserConfig(type);
        DataDictionarySupport.removePublicUserConfig(type.getUserId());
        AjaxSupport.sendSuccessText("", "操作成功!");
    }


    /**
     * 查询当然用户所在部门，所创建的商品
     * @param commonParmVO
     * @param request
     * @throws Exception
     */
    @RequestMapping("/ajax/loadOrgIdItemInformationList.do")
    @ResponseBody
    public void loadOrgIdItemInformationList(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        Map m = new HashMap();
        String content = request.getParameter("content");
        SessionVO c= SessionCacheSupport.getSessionVO();
        m.put("userId",c.getId());
        m.put("content",content);
        Page page = new Page();
        page.setPageSize(10);
        page.setCurrentPage(1);
        page.setTotalCount(100);
        List<ItemInformationQuery> list=iPublicItemInformation.selectItemInformationByOrgId(m, page);
        AjaxSupport.sendSuccessText("", list);
    }

    /**
     * 查询商品详情
     * @param commonParmVO
     * @param request
     * @throws Exception
     */
    @RequestMapping("/ajax/loadItemInformationMessage.do")
    @ResponseBody
    public void loadItemInformationMessage(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String id = request.getParameter("id");
        PublicItemInformation information=iPublicItemInformation.selectItemInformationByid(Long.valueOf(id));
        AjaxSupport.sendSuccessText("", information);
    }

}
