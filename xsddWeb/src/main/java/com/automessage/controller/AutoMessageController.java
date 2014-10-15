package com.automessage.controller;

import com.base.database.trading.model.TradingAutoMessageAttr;
import com.base.domains.CommonParmVO;
import com.base.domains.querypojos.AutoMessageQuery;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.SystemUserManagerService;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingAutoMessage;
import com.trading.service.ITradingAutoMessageAttr;
import com.trading.service.ITradingMessageTemplate;
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
 * Created by Administrtor on 2014/10/14.
 */
@Controller
@RequestMapping("autoMessage")
public class AutoMessageController extends BaseAction {
    @Autowired
    private ITradingMessageTemplate iTradingMessageTemplate;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    @Autowired
    private ITradingAutoMessage iTradingAutoMessage;
    @Autowired
    private ITradingAutoMessageAttr iTradingAutoMessageAttr;

    @RequestMapping("/autoMessageList.do")
    public ModelAndView sendMessageList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("autoMessage/autoMessageList",modelMap);
    }
    /**获取发送消息list数据的ajax方法*/
    @RequestMapping("/ajax/loadAutoMessageList.do")
    @ResponseBody
    public void loadSendMessageList(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        Map m=new HashMap();
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<AutoMessageQuery> lists=iTradingAutoMessage.selectAutoMessageList(m,page);
        if(lists!=null&&lists.size()>0){
           for(AutoMessageQuery auto:lists){
               List<TradingAutoMessageAttr> attrs1=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(auto.getId(),"country");
               List<TradingAutoMessageAttr> attrs2=iTradingAutoMessageAttr.selectAutoMessageListByautoMessageId(auto.getId(),"ebayEmail");
               List<String> countrys=new ArrayList<String>();
               List<String> ebayNames=new ArrayList<String>();
               for(TradingAutoMessageAttr country:attrs1){
                   countrys.add(country.getValue());
               }
               for(TradingAutoMessageAttr ebay:attrs2){
                   ebayNames.add(ebay.getValue());
               }
               auto.setCountrys(countrys);
               auto.setEbayNames(ebayNames);
           }
        }
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
}
