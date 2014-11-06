package com.indexPage.controller;

import com.common.base.web.BaseAction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2014/11/5.
 * 系统主页
 */
@Controller
public class IndexMainController extends BaseAction{
    /**打开首页*/
    @RequestMapping("indexInit.do")
    public ModelAndView indexInit(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        return forword("/indexpage/indexPage",modelMap);
    }
}
