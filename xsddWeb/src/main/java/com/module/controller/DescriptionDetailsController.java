package com.module.controller;

import com.base.database.trading.model.TradingDescriptionDetails;
import com.base.database.trading.model.TradingDescriptionDetailsWithBLOBs;
import com.base.database.trading.model.TradingReturnpolicy;
import com.base.domains.CommonParmVO;
import com.base.domains.querypojos.DescriptionDetailsWithBLOBsQuery;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.common.ObjectUtils;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingDescriptionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    @AvoidDuplicateSubmission(needSaveToken = true)
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
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addDescriptionDetails(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        return forword("module/descriptiondetails/adddescriptiondetails",modelMap);
    }

    @RequestMapping("/editDescriptionDetails.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editDescriptionDetails(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        Map m = new HashMap();
        m.put("id",request.getParameter("id"));
        List<DescriptionDetailsWithBLOBsQuery> DescriptionDetailsli = this.iTradingDescriptionDetails.selectByDescriptionDetailsList(m);
        modelMap.put("DescriptionDetails",DescriptionDetailsli.get(0));
        String type = request.getParameter("type");
        modelMap.put("type",type);
        return forword("module/descriptiondetails/adddescriptiondetails",modelMap);
    }

    @RequestMapping("/ajax/saveDescriptionDetails.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveDescriptionDetails(HttpServletRequest request,HttpServletResponse response,
                                       TradingDescriptionDetailsWithBLOBs tradingDescription,
                                       ModelMap modelMap) throws Exception {

        this.iTradingDescriptionDetails.saveDescriptionDetails(tradingDescription);
        AjaxSupport.sendSuccessText("","操作成功!");
    }

    /**
     * 保存数据
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/delDescriptionDetails.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void delDescriptionDetails(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id = request.getParameter("id");
        TradingDescriptionDetailsWithBLOBs tp= this.iTradingDescriptionDetails.selectById(Long.parseLong(id));
        if(tp.getCheckFlag().equals("1")){
            tp.setCheckFlag("0");
        }else{
            tp.setCheckFlag("1");
        }
        this.iTradingDescriptionDetails.saveDescriptionDetails(tp);
        AjaxSupport.sendSuccessText("","操作成功!");
    }
}
