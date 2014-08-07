package com.message.controller;

import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.domains.CommonParmVO;
import com.base.domains.querypojos.MessageGetmymessageQuery;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.SessionCacheSupport;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingMessageGetmymessage;
import com.trading.service.IUsercontrollerEbayAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
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
 * Created by Administrtor on 2014/8/6.
 */
@Controller
public class GetmymessageController extends BaseAction{

    @Autowired
    private ITradingMessageGetmymessage iTradingMessageGetmymessage;

    @Autowired
    private IUsercontrollerEbayAccount iUsercontrollerEbayAccount;

   /* private ITradingMessageAddmembermessage*/
    /**
     * 消息列表
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/MessageGetmymessageList.do")
    public ModelAndView MessageGetmymessageList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("MessageGetmymessage/MessageGetmymessageList",modelMap);
    }

    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadMessageGetmymessageList.do")
    @ResponseBody
    public void loadMessageGetmymessageList(CommonParmVO commonParmVO,HttpServletRequest request){
        Map m = new HashMap();
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        long UserId=  SessionCacheSupport.getSessionVO().getId();
        List<UsercontrollerEbayAccount> ebays=iUsercontrollerEbayAccount.selectUsercontrollerEbayAccountByUserId(UserId);
        List<MessageGetmymessageQuery> MessageGetmymessage = new ArrayList<MessageGetmymessageQuery>();
        for(UsercontrollerEbayAccount ebay:ebays){
            m.put("sendtoname",ebay.getEbayName());
            List<MessageGetmymessageQuery> lists= this.iTradingMessageGetmymessage.selectMessageGetmymessageByGroupList(m,page);
            MessageGetmymessage.addAll(lists);
        }
        jsonBean.setList(MessageGetmymessage);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

   /**
     * 查看消息
     * @param request
     * @param response
     * @param modelMap
     * @return
     */

    @RequestMapping("/viewMessageGetmymessage.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewTemplateInitTable(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        String ebayAccountId=request.getParameter("ebayAccountId");
        String sendtoname=request.getParameter("sendtoname");
        Map m=new HashMap();
        m.put("ebayAccountId",ebayAccountId);
        m.put("sendtoname",sendtoname);
        List<MessageGetmymessageQuery> messages=iTradingMessageGetmymessage.selectMessageGetmymessageBySender(m);
        modelMap.put("messages",messages);
        return forword("MessageGetmymessage/viewMessageGetmymessage",modelMap);
    }

    @RequestMapping("/sendMessageGetmymessage.do")
    public ModelAndView sendMessageGetmymessage(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("MessageGetmymessage/sendMessageGetmymessageList",modelMap);
    }
    /*
     * 接受消息编辑
     * @param request
     * @param response
     * @param modelMap
     * @return
     *//*
    @RequestMapping("/editMessageGetmymessage.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editMessageGetmymessage(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        Map m = new HashMap();
        m.put("id",request.getParameter("id"));
        List<MessageGetmymessageQuery> MessageGetmymessageli = this.iTradingMessageGetmymessage.selectByMessageGetmymessageList(m);
        modelMap.put("MessageGetmymessage",MessageGetmymessageli.get(0));
        return forword("MessageGetmymessage/addMessageGetmymessage",modelMap);
    }


    *//**
     * 保存接受消息数据
     * @param request
     * @throws Exception
     */
    @RequestMapping("/ajax/saveMessageGetmymessage.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveMessageGetmymessage(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {

        AjaxSupport.sendSuccessText("message", "操作成功！");
    }
}
