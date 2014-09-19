package com.module.controller;

import com.base.domains.SessionVO;
import com.base.utils.cache.SessionCacheSupport;
import com.common.base.web.BaseAction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrtor on 2014/9/19.
 */
@Controller
public class ModuleController extends BaseAction {
    @RequestMapping("/moduleManager.do")
    public ModelAndView moduleManager(HttpServletRequest request,HttpServletResponse response,
                             @ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        // Map map = new HashMap();
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        modelMap.put("ccc",sessionVO.getUserName());
        return forword("module/modulemanager",modelMap);
    }
}
