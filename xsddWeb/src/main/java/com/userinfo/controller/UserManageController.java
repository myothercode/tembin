package com.userinfo.controller;

import com.base.domains.CommonParmVO;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.userinfo.service.UserInfoService;
import com.common.base.web.BaseAction;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrtor on 2014/8/6.
 * 用户管理
 */
@Controller
@RequestMapping("user")
public class UserManageController extends BaseAction {
    static Logger logger = Logger.getLogger(UserManageController.class);
    @Autowired
    private UserInfoService userInfoService;

    public ModelAndView bindEbayAccount(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,
                                        CommonParmVO commonParmVO) throws Exception {
      UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(commonParmVO.getId());
        d.setApiSiteid("0");
        d.setApiCallName("GeteBayOfficialTime");
      return   forword("userinfobindEbayAccount",modelMap);
    }

}
