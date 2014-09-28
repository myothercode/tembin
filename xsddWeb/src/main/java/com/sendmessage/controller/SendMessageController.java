package com.sendmessage.controller;

import com.base.database.trading.model.TradingOrderAddMemberMessageAAQToPartner;
import com.base.domains.CommonParmVO;
import com.base.domains.querypojos.TradingOrderAddMemberMessageAAQToPartnerQuery;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingOrderAddMemberMessageAAQToPartner;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/9/16.
 */
@Controller
@RequestMapping("sendMessage")
public class SendMessageController extends BaseAction{
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ITradingOrderAddMemberMessageAAQToPartner iTradingOrderAddMemberMessageAAQToPartner;
    @RequestMapping("/sendMessageList.do")
    public ModelAndView sendMessageList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("sendMessage/sendMessageList",modelMap);
    }
    @RequestMapping("/autoSendMessageList.do")
    public ModelAndView autoSendMessageList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("sendMessage/autoSendMessageList",modelMap);
    }
    /**获取发送消息list数据的ajax方法*/
    @RequestMapping("/ajax/loadSendMessageList.do")
    @ResponseBody
    public void loadSendMessageList(CommonParmVO commonParmVO) throws Exception {
        Map m = new HashMap();
        /**分页组装*/
        m.put("sendMessage","notAutoSendMessage");
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<TradingOrderAddMemberMessageAAQToPartnerQuery> lists= iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartner(m, page);
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
    /**获取自动发送消息list数据的ajax方法*/
    @RequestMapping("/ajax/loadAutoSendMessageList.do")
    @ResponseBody
    public void loadAutoSendMessageList(CommonParmVO commonParmVO) throws Exception {
        Map m = new HashMap();
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<TradingOrderAddMemberMessageAAQToPartnerQuery> lists= iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartner(m, page);
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
    /*
    *删除发送消息
    */
    @RequestMapping("/ajax/removesendMessage.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void removesendMessage(HttpServletRequest request) throws Exception {
        String transactionid=request.getParameter("transactionid");
        String messagetype=request.getParameter("messagetype");
        if(StringUtils.isNotBlank(transactionid)&&StringUtils.isNotBlank(messagetype)){
            List<TradingOrderAddMemberMessageAAQToPartner> list= iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByTransactionId(transactionid,Integer.valueOf(messagetype));
            for(TradingOrderAddMemberMessageAAQToPartner partner:list){
                iTradingOrderAddMemberMessageAAQToPartner.deleteTradingOrderAddMemberMessageAAQToPartner(partner.getId());
            }
            AjaxSupport.sendSuccessText("", "删除成功!");
        }else{
            AjaxSupport.sendFailText("fail","发送消息不存在");
        }
    }
    /*
    *删除自动发送消息
    */
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
    /*
    *删除自动发送消息
    */
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
    }
}
