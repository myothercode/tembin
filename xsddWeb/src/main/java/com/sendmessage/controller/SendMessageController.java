package com.sendmessage.controller;

import com.base.database.trading.model.TradingMessageTemplate;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.MessageTemplateQuery;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.SessionCacheSupport;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingMessageTemplate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrtor on 2014/9/16.
 */
@Controller
@RequestMapping("sendMessage")
public class SendMessageController extends BaseAction{
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ITradingMessageTemplate iTradingMessageTemplate;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    @RequestMapping("/sendMessageList.do")
    public ModelAndView sendMessageList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("sendMessage/sendMessageList",modelMap);
    }
    /**获取发送消息list数据的ajax方法*/
    @RequestMapping("/ajax/loadSendMessageList.do")
    @ResponseBody
    public void loadSendMessageList(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String status=request.getParameter("status");
        String orderby=request.getParameter("orderby");
        Map m = new HashMap();
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        if(!StringUtils.isNotBlank(orderby)){
            orderby=null;
        }
        if(!StringUtils.isNotBlank(status)){
            status=null;
        }
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        List<MessageTemplateQuery> lists=new ArrayList<MessageTemplateQuery>();
        m.put("status",status);
        m.put("userId",sessionVO.getId());
        m.put("orderby",orderby);
        lists= iTradingMessageTemplate.selectMessageTemplateList(m, page);
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
    //添加模板初始化
    @RequestMapping("/addMessageTemplate.do")
    public ModelAndView addMessageTemplate(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String id=request.getParameter("id");
        if(StringUtils.isNotBlank(id)){
            List<TradingMessageTemplate> list=iTradingMessageTemplate.selectMessageTemplatebyId(Long.valueOf(id));
            modelMap.put("template",list.get(0));
        }
        modelMap.put("id",id);
        return forword("sendMessage/addMessageTemplate",modelMap);
    }
    @RequestMapping("/saveMessageTemplate.do")
    @ResponseBody
    public void saveMessageTemplate(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String id=request.getParameter("id");
        String name=request.getParameter("name");
        String content=request.getParameter("content");
        String caseType=request.getParameter("caseType");
        String autoType=request.getParameter("autoType");
        String messageType=request.getParameter("messageType");
        if(!StringUtils.isNotBlank(name)&&!StringUtils.isNotBlank(content)){
            AjaxSupport.sendFailText("fail","模板名称或者模板内容不能为空!");
            return;
        }
        TradingMessageTemplate template=new TradingMessageTemplate();
        if(StringUtils.isNotBlank(id)){
            template.setId(Long.valueOf(id));
        }else{
            template.setStatus(1);
        }
        if(StringUtils.isNotBlank(name)){
            template.setName(name);
        }
        if(StringUtils.isNotBlank(content)){
            template.setContent(content);
        }
        if(StringUtils.isNotBlank(caseType)){
            template.setCasetype(Integer.parseInt(caseType));
        }else{
            template.setCasetype(0);
        }
        if(StringUtils.isNotBlank(autoType)){
            template.setAutotype(Integer.parseInt(autoType));
        }else{
            template.setAutotype(0);
        }
        if(StringUtils.isNotBlank(messageType)){
            template.setMessagetype(Integer.parseInt(messageType));
        }else{
            template.setMessagetype(0);
        }
        iTradingMessageTemplate.saveMessageTemplate(template);
        AjaxSupport.sendSuccessText("", "保存成功");
    }
    //修改状态
    @RequestMapping("/useStatus.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void useStatus(HttpServletRequest request) throws Exception {
        String id=request.getParameter("id");
        String status=request.getParameter("status");
        if(!StringUtils.isNotBlank(id)){
            AjaxSupport.sendFailText("fail","该模板不存在");
            return;
        }
        List<TradingMessageTemplate> list=iTradingMessageTemplate.selectMessageTemplatebyId(Long.valueOf(id));
        if(list!=null&&list.size()>0){
            TradingMessageTemplate template=list.get(0);
            if(StringUtils.isNotBlank(status)){
                template.setStatus(Integer.parseInt(status));
                iTradingMessageTemplate.saveMessageTemplate(template);
            }
        }
        if("1".equals(status)){
            AjaxSupport.sendSuccessText("", "启用成功");
        }else if("0".equals(status)){
            AjaxSupport.sendSuccessText("", "禁用成功");
        }
    }
  /* *//* *获取自动发送消息list数据的ajax方法*//*
    @RequestMapping("/ajax/loadAutoSendMessageList.do")
    @ResponseBody
    public void loadAutoSendMessageList(CommonParmVO commonParmVO) throws Exception {
        Map m = new HashMap();
        *//**分页组装*//*
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        Map ebayMap=new HashMap();
        List<UsercontrollerEbayAccountExtend> ebays=systemUserManagerService.queryCurrAllEbay(ebayMap);
        List<MessageTemplateQuery> lists=new ArrayList<MessageTemplateQuery>();
        if(ebays.size()>0){
            m.put("ebays",ebays);
            m.put("type","autoType");
            lists= iTradingMessageTemplate.selectMessageTemplateList(m, page);
        }
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }*/
    /*
    *删除发送消息
    */
    @RequestMapping("/ajax/removesendMessage.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void removesendMessage(HttpServletRequest request) throws Exception {
        String id=request.getParameter("id");
        if(!StringUtils.isNotBlank(id)){
            AjaxSupport.sendFailText("fail", "该模板不存在");
        }else{
            List<TradingMessageTemplate> list=iTradingMessageTemplate.selectMessageTemplatebyId(Long.valueOf(id));
            if(list!=null&&list.size()>0) {
                TradingMessageTemplate template = list.get(0);
                iTradingMessageTemplate.deleteMessageTemplate(template);
            }
            AjaxSupport.sendSuccessText("","删除成功");
        }
    }
    /*
    *删除自动发送消息
    *//*
    @RequestMapping("/ajax/deleteAutoSendMessage.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void deleteAutoSendMessage(HttpServletRequest request) throws Exception {
        String transactionid=request.getParameter("transactionid");
        String messagetype=request.getParameter("messagetype");
        String messageflag=request.getParameter("messageflag");
        if(StringUtils.isNotBlank(transactionid)&&StringUtils.isNotBlank(messagetype)){
            List<TradingOrderAddMemberMessageAAQToPartner> list= iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByTransactionId(transactionid,Integer.valueOf(messagetype),Integer.valueOf(messageflag));
            for(TradingOrderAddMemberMessageAAQToPartner partner:list){
                iTradingOrderAddMemberMessageAAQToPartner.deleteTradingOrderAddMemberMessageAAQToPartner(partner.getId());
            }
            AjaxSupport.sendSuccessText("", "删除成功!");
        }else{
            AjaxSupport.sendFailText("fail","发送消息不存在");
        }
    }
    *//*
    *删除自动发送消息
    *//*
    @RequestMapping("/ajax/removeAutoSendMessage.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void removeAutoSendMessage(HttpServletRequest request) throws Exception {
        int i=0;
        List<Long> idList=new ArrayList<Long>();
        while(i>=0) {
            String id = request.getParameter("id[" + i + "]");
            if(!StringUtils.isNotBlank(id)&&i==0){
                i=-2;
            }else if(StringUtils.isNotBlank(id)){
                idList.add(Long.valueOf(id));
                i++;
            }else{
                i=-1;
            }
        }
        if(idList.size()>0){
            for(Long id:idList){
                iTradingOrderAddMemberMessageAAQToPartner.deleteTradingOrderAddMemberMessageAAQToPartner(id);
            }
            AjaxSupport.sendSuccessText("", "删除成功!");
        }else{
            AjaxSupport.sendFailText("fail","该消息不存在");
        }
    }*/
}
