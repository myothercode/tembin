package com.common.controller;

import com.base.domains.CommonParmVO;
import com.base.domains.PermissionVO;
import com.base.userinfo.service.MenuService;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2014/9/15.
 */
@Controller
@RequestMapping("menu")
public class MenuController extends BaseAction {
    @Autowired
    private MenuService menuService;

    /**获取当前登录人菜单*/
    @RequestMapping("getUserMenuList")
    @ResponseBody
    public void getUserMenuList(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,
                                CommonParmVO commonParmVO){
        List<PermissionVO> permissionVOs=menuService.getUserMenuList(new HashMap());
        AjaxSupport.sendSuccessText("menu",permissionVOs);
        return;
    }

    /**获取当前登录人菜单*/
    @RequestMapping("getAllMenuList.do")
    @ResponseBody
    public void getAllMenuList(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,
                                CommonParmVO commonParmVO){
        List<PermissionVO> permissionVOs=menuService.getAllMenuList(new HashMap());
        AjaxSupport.sendSuccessText("menu",permissionVOs);
        return;
    }

}
