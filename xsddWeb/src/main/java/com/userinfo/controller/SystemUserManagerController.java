package com.userinfo.controller;

import com.base.domains.querypojos.CommonParmVO;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.SystemUserManagerService;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2014/9/19.
 * 系统账户管理
 */
@Controller
@RequestMapping("systemuser")
public class SystemUserManagerController extends BaseAction {

    @Autowired
    private SystemUserManagerService systemUserManagerService;
    /**打开系统账户管理页面*/
    @RequestMapping("sysUserManPage")
    public ModelAndView sysUserManPage(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        return forword("/userinfo/systemUserManager",modelMap);
    }

    @RequestMapping("getSysManData")
    @ResponseBody
    public void getSysManData(CommonParmVO commonParmVO){
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<UsercontrollerUserExtend> lists=  systemUserManagerService.queryAccountListByUserID(new HashMap(),page);
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
}
