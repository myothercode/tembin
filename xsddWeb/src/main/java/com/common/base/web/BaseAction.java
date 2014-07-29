package com.common.base.web;

import com.common.base.utils.EditorSupportUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by wula on 2014/6/22.
 */
public class BaseAction {

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
}