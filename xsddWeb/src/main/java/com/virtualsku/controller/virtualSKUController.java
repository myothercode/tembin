package com.virtualsku.controller;

import com.common.base.web.BaseAction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrtor on 2014/9/16.
 */
@Controller
@RequestMapping("virtualSKU")
public class virtualSKUController extends BaseAction {
    @RequestMapping("/virtualSKUList.do")
    public ModelAndView autoSendMessageList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("virtualSKU/virtualSKUList",modelMap);
    }
}
