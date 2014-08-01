package com.module.controller;

import com.base.database.trading.model.TradingDescriptionDetailsWithBLOBs;
import com.base.domains.CommonParmVO;
import com.base.domains.querypojos.DescriptionDetailsWithBLOBsQuery;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.utils.common.ObjectUtils;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingDescriptionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lq on 2014/7/29.
 */
@Controller
public class DescriptionDetailsController extends BaseAction{

    @Autowired
    private ITradingDescriptionDetails iTradingDescriptionDetails;

    @RequestMapping("/DescriptionDetailsList.do")
    public ModelAndView DescriptionDetailsList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
      /*  Map m = new HashMap();
        List<DescriptionDetailsWithBLOBsQuery> DescriptionDetailsli = this.iTradingDescriptionDetails.selectByDescriptionDetailsList(m);
        modelMap.put("DescriptionDetailsli",DescriptionDetailsli);*/
        return forword("module/descriptiondetails/descriptiondetailsList",modelMap);
    }

    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadDescriptionDetailsList.do")
    @ResponseBody
    public void loadDescriptionDetailsList(CommonParmVO commonParmVO){
        Map m = new HashMap();
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<DescriptionDetailsWithBLOBsQuery> DescriptionDetails = this.iTradingDescriptionDetails.selectByDescriptionDetailsList(m,page);
        jsonBean.setList(DescriptionDetails);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    @RequestMapping("/addDescriptionDetails.do")
    public ModelAndView addDescriptionDetails(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("module/descriptiondetails/adddescriptiondetails",modelMap);
    }

    @RequestMapping("/editDescriptionDetails.do")
    public ModelAndView editDescriptionDetails(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        Map m = new HashMap();
        m.put("id",request.getParameter("id"));
        List<DescriptionDetailsWithBLOBsQuery> DescriptionDetailsli = this.iTradingDescriptionDetails.selectByDescriptionDetailsList(m);
        modelMap.put("DescriptionDetails",DescriptionDetailsli.get(0));
        return forword("module/descriptiondetails/adddescriptiondetails",modelMap);
    }

    @RequestMapping("/saveDescriptionDetails.do")
    public ModelAndView saveDescriptionDetails(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String name = request.getParameter("name");
        String id = request.getParameter("id");
        String payment=request.getParameter("Payment");
        String shipping=request.getParameter("Shipping");
        String contact =request.getParameter("Contact");
        String guarantee =request.getParameter("Guarantee");
        String feedback=request.getParameter("Feedback");
        TradingDescriptionDetailsWithBLOBs tp = new TradingDescriptionDetailsWithBLOBs();
        if(!ObjectUtils.isLogicalNull(id)){
            tp.setId(Long.parseLong(id));
        }
        tp.setName(name);
        tp.setPayInfo(payment);
        tp.setContactInfo(contact);
        tp.setShippingInfo(shipping);
        tp.setGuaranteeInfo(guarantee);
        tp.setFeedbackInfo(feedback);
        ObjectUtils.toPojo(tp);
        this.iTradingDescriptionDetails.saveDescriptionDetails(tp);
        return forword("module/descriptiondetails/adddescriptiondetails",modelMap);
    }
}