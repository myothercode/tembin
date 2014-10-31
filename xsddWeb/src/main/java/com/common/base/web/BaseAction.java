package com.common.base.web;

import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.imageManage.service.ImageService;
import com.common.base.utils.EditorSupportUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by wula on 2014/6/22.
 */
public class BaseAction {

    @ModelAttribute( "initSomeParmMap" )
    public ModelMap initSomeParm(){
        ModelMap map=new ModelMap();
        map.put("nowDateTime",new Date());
        map.put("jscacheVersion","3");
        try {
            ImageService imageService= (ImageService) ApplicationContextUtil.getBean(ImageService.class);
            CommAutowiredClass commAutowiredClass= (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);
            map.put("itemListIconUrl",imageService.getItemListIconUrl());
            map.put("serviceItemUrl",commAutowiredClass.serviceItemUrl);
        } catch (Exception e) {}
        return map;
    }

    /**初始化时设置一些参数的转换*/
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        binder.registerCustomEditor(int.class,new EditorSupportUtils());
    }

    /**所有的forword action都由这里转发*/
    public ModelAndView forword(String viewName,Map context){
        /*ModelAndView mv = new ModelAndView();
        //添加模型数据 可以是任意的POJO对象
        mv.addObject("message", "Hello World!");
        //设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
        mv.setViewName("hello");*/
        return new ModelAndView(viewName,context);
    }

    public ModelAndView redirect(String url){
        return new ModelAndView("redirect:"+url);
        /*如果需要addFlashAttribute传参数的话
        @RequestMapping(value="addcustomer", method=RequestMethod.POST)
        public String addCustomer(@ModelAttribute("customer") Customer customer,
        final RedirectAttributes redirectAttributes) {
            redirectAttributes.addFlashAttribute("customer", customer);
            redirectAttributes.addFlashAttribute("message","Added successfully.");
            return "redirect:showcustomer.html";
        }*/
    }
}
