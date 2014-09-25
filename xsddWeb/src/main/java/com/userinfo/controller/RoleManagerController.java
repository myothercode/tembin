package com.userinfo.controller;

import com.base.database.userinfo.model.UsercontrollerRole;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.CommonParmVO;
import com.base.domains.userinfo.AddSubUserVO;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.RoleManagerService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.exception.Asserts;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
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
 * 角色管理
 * Created by Administrator on 2014/9/24.
 */
@Controller
@RequestMapping("role")
public class RoleManagerController extends BaseAction {
    @Autowired
    private RoleManagerService roleManagerService;


    /**新增页面*/
    @RequestMapping("addRolePageInit.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addRolePageInit(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        return forword("/userinfo/pop/addRolePage",modelMap);
    }
    /**编辑页面*/
    @RequestMapping("editRolePageInit.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editRolePageInit(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,String roleId){
        if(StringUtils.isNotEmpty(roleId)){
            modelMap.put("roleId",roleId);
        }
        return forword("/userinfo/pop/addRolePage",modelMap);
    }


    @RequestMapping("queryRoleList.do")
    //@AvoidDuplicateSubmission(needSaveToken = true)
    @ResponseBody
    /**查询角色列表*/
    public void queryRoleList(CommonParmVO commonParmVO){
        Map map=new HashMap();
        PageJsonBean jsonBean = commonParmVO.getJsonBean();
        Page page = jsonBean.toPage();
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        map.put("createUser",sessionVO.getId());
        List<UsercontrollerRole> usercontrollerRoleList = roleManagerService.queryRoleList(map ,page);
        jsonBean.setTotal((int) page.getTotalCount());
        jsonBean.setList(usercontrollerRoleList);
        AjaxSupport.sendSuccessText("",jsonBean);
    }

    @RequestMapping("deleteRole.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    /**删除一个角色*/
    public void deleteRole(Long roleId){
        Map map=new HashMap();
        map.put("roleId",roleId);
        roleManagerService.deleteRoleById(map);
        AjaxSupport.sendSuccessText("","删除成功！");
    }

    @RequestMapping("addOrEditRole.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    /**新增加或者修改一个角色*/
    public void addOrEditRole(AddSubUserVO addSubUserVO){
        roleManagerService.addOrEditRole(addSubUserVO);
        AjaxSupport.sendSuccessText("","操作成功！");
    }

    @RequestMapping("getRoleInfoById.do")
    @ResponseBody
    /**根据id查询一个角色的信息*/
    public void getRoleInfoById(Integer roleId){
        Asserts.assertTrue(roleId!=null,"roleID为空！");
        AddSubUserVO addSubUserVO=roleManagerService.getRoleInfoById(roleId);
        AjaxSupport.sendSuccessText("",addSubUserVO);
    }

}
