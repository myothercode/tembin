package com.userinfo.controller;

import com.base.domains.RoleVO;
import com.base.domains.querypojos.CommonParmVO;
import com.base.domains.userinfo.AddSubUserVO;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.exception.Asserts;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @RequestMapping("sysUserManPage.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView sysUserManPage(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        return forword("/userinfo/systemUserManager",modelMap);
    }
    /**初始化增加子用户的页面*/
    @RequestMapping("addSubUserInit.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addSubUserInit(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        return forword("/userinfo/pop/addSubSystenUser",modelMap);
    }
    /**初始化修改子用户的页面*/
    @RequestMapping("editSubUserInit.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editSubUserInit(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,String userID){
        if(StringUtils.isNotEmpty(userID)){
            modelMap.put("userID",userID);
        }
        return forword("/userinfo/pop/addSubSystenUser",modelMap);
    }

    /**获取帐号列表数据*/
    @RequestMapping("getSysManData.do")
    @ResponseBody
    public void getSysManData(CommonParmVO commonParmVO,@RequestParam(value = "isShowStop",required = false)String isShowStop){
        Map map=new HashMap();
        if(StringUtils.isNotEmpty(isShowStop)){
            map.put("isShowStop",isShowStop);
        }else {
            map.put("isShowStop","active");
        }
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<UsercontrollerUserExtend> lists=  systemUserManagerService.queryAccountListByUserID(map,page);
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    /**对帐号进行操作*/
    @RequestMapping("operationAccount.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void operationAccount(String userid,String doaction){
        Asserts.assertTrue(StringUtils.isNotEmpty(userid)&&StringUtils.isNotEmpty(doaction),"帐号或者操作参数不能为空!");
        Map map =new HashMap();
        map.put("userid",userid);
        map.put("doaction",doaction);
        systemUserManagerService.operationAccount(map);
        AjaxSupport.sendSuccessText("","修改成功！");
    }

    @RequestMapping("queryMySpecAllRole.do")
    @ResponseBody
    /**获取当前登录用户定义的所有角色*/
    public void queryMySpecAllRole(Long userID){
        Map map =new HashMap();

        List<RoleVO> roleVOs = systemUserManagerService.queryAllCustomRole(map);
        AjaxSupport.sendSuccessText("",roleVOs);
    }

    @RequestMapping("queryMySpecAllEbay.do")
    @ResponseBody
    /**获取当前登录用户定义的所有ebay账户*/
    public void queryMySpecAllEbay(Long userID){
        Map map =new HashMap();

        List<UsercontrollerEbayAccountExtend> roleVOs = systemUserManagerService.queryCurrAllEbay(map);
        AjaxSupport.sendSuccessText("",roleVOs);
    }

    /**编辑页面获取用户信息*/
    @RequestMapping("queryUserAccountInfo.do")
    @ResponseBody
    public void queryUserAccountInfo(Long userID){
        AddSubUserVO addSubUserVO=systemUserManagerService.queryAllUserAccountInfo(userID);
        AjaxSupport.sendSuccessText("",addSubUserVO);
    }

    /**修改子用户*/
    @RequestMapping("editSubUser.do")
    @ResponseBody
    @AvoidDuplicateSubmission(needRemoveToken = true)
    public void editSubUser(AddSubUserVO addSubUserVO){
        systemUserManagerService.editSubAccount(addSubUserVO);
        AjaxSupport.sendSuccessText("","修改成功!");
    }

    /**添加子用户*/
    @RequestMapping("addSubUser.do")
    @ResponseBody
    @AvoidDuplicateSubmission(needRemoveToken = true)
    public void addSubUser(AddSubUserVO addSubUserVO){
        systemUserManagerService.addSubAccount(addSubUserVO);
        AjaxSupport.sendSuccessText("","添加成功");
    }

    /**修改密码*/
    @RequestMapping("changePWD.do")
    @ResponseBody
    @AvoidDuplicateSubmission(needRemoveToken = true)
    public void changePWD(String oldPWD,String newPWD){
        Asserts.assertTrue(StringUtils.isNotEmpty(oldPWD) && StringUtils.isNotEmpty(newPWD),"原密码和新密码不能为空");
        systemUserManagerService.changePWD(oldPWD, newPWD);
        AjaxSupport.sendSuccessText("","修改成功！");
    }

}
