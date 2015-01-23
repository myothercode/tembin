package com.aboutpaypal.controller;

import com.base.aboutpaypal.domain.PaypalVO;
import com.base.aboutpaypal.service.PayPalService;
import com.base.database.trading.model.UsercontrollerPaypalAccount;
import com.base.domains.CommonParmVO;
import com.base.domains.querypojos.BaseTjReportQuery;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.utils.exception.Asserts;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/13.
 * 有关paypal帐号的操作
 */
@Controller
@RequestMapping("paypal")
public class PaypalController extends BaseAction {
    @Autowired
    private PayPalService payPalService;

    @RequestMapping("addPayPalPage.do")
    /**打开新增paypal帐号页面*/
    public ModelAndView addPayPalPage(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        return   forword("/userinfo/pop/addPayPalPage",modelMap);
    }

    @RequestMapping("selectPayPalPage.do")
    /**选择绑定paypal帐号页面*/
    public ModelAndView selectPayPalPage(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        return   forword("/userinfo/pop/paypalListPage",modelMap);
    }


    @RequestMapping("getPaypalBalance.do")
    @ResponseBody
    /**获取paypal余额*/
    public void getPaypalBalance(CommonParmVO commonParmVO) throws Exception {
        PaypalVO acc = payPalService.getPaypalBalance(commonParmVO.getId());
        AjaxSupport.sendSuccessText("",acc);
        return;
    }

    @RequestMapping("getPaypalYanZheng.do")
    @ResponseBody
    /**获取余额，用来验证是否授权*/
    public void getPaypalYanZheng(CommonParmVO commonParmVO) throws Exception {
        Asserts.assertTrue(commonParmVO.getId()!=null,"id不能为空!");
        PaypalVO acc = payPalService.getPaypalBalance(commonParmVO.getId());
        String r="验证失败";
        if ("Success".equalsIgnoreCase(acc.getAck())){
            r="验证成功！";
            payPalService.setPayPalSFCheck(commonParmVO.getId(),"1");
        }
        AjaxSupport.sendSuccessText("",r);
        return;
    }

    /**获取交易的税费和交易费*/
    @RequestMapping("getTransactionDetails")
    @ResponseBody
    public void getTransactionDetails(CommonParmVO commonParmVO) throws Exception {
        Map map =new HashMap();
        map.put("paypalId",commonParmVO.getId());
        map.put("transactionID",commonParmVO.getStrV1());
        PaypalVO acc = (PaypalVO) payPalService.getTransactionDetails(map).get("paypal");
        AjaxSupport.sendSuccessText("",acc);
        return;
    }

    @RequestMapping("queryPaypalList.do")
    @ResponseBody
    /**查询paypalList 分页*/
    public void queryPaypalList(com.base.domains.querypojos.CommonParmVO commonParmVO){
        Map map =new HashMap();
        PageJsonBean jsonBean = commonParmVO.getJsonBean();
        Page page = jsonBean.toPage();
        //List<UsercontrollerPaypalAccount> paypalAccounts = payPalService.queryPayPalList(map, page);
        List<BaseTjReportQuery> li1 = this.payPalService.selectPayPalReportList("1");//当天
        List<BaseTjReportQuery> li2 = this.payPalService.selectPayPalReportList("2");//昨天
        List<BaseTjReportQuery> li3 = this.payPalService.selectPayPalReportList("3");//本周
        List<BaseTjReportQuery> li4 = this.payPalService.selectPayPalReportList("4");//上周
        List<BaseTjReportQuery> li5 = this.payPalService.selectPayPalReportList("5");//本月
        List<BaseTjReportQuery> li6 = this.payPalService.selectPayPalReportList("6");//上月
        List<UsercontrollerPaypalAccount> paypalAccounts = payPalService.queryPayPalsByUserIdReport(map, page);
        List<Map> lim = new ArrayList<Map>();
        DecimalFormat df = new DecimalFormat("###.00");
        for(UsercontrollerPaypalAccount upa:paypalAccounts){
            Map m = new HashMap();
            m.put("paypalAccount",upa.getPaypalAccount());
            m.put("status",upa.getStatus());
            m.put("id",upa.getId());
            for(BaseTjReportQuery bt:li1){
                if((upa.getId()+"").equals(bt.getReturnType())){
                    m.put("day",df.format(Double.parseDouble(bt.getTjNumber())));
                }
            }
            if(m.get("day")==null){
                m.put("day","0.00");
            }
            for(BaseTjReportQuery bt:li2){
                if((upa.getId()+"").equals(bt.getReturnType())){
                    m.put("yesterday",df.format(Double.parseDouble(bt.getTjNumber())));
                }
            }
            if(m.get("yesterday")==null){
                m.put("yesterday","0.00");
            }
            for(BaseTjReportQuery bt:li3){
                if((upa.getId()+"").equals(bt.getReturnType())){
                    m.put("week",df.format(Double.parseDouble(bt.getTjNumber())));
                }
            }
            if(m.get("week")==null){
                m.put("week","0.00");
            }
            for(BaseTjReportQuery bt:li4){
                if((upa.getId()+"").equals(bt.getReturnType())){
                    m.put("thatweek",df.format(Double.parseDouble(bt.getTjNumber())));
                }
            }
            if(m.get("thatweek")==null){
                m.put("thatweek","0.00");
            }
            for(BaseTjReportQuery bt:li5){
                if((upa.getId()+"").equals(bt.getReturnType())){
                    m.put("month",df.format(Double.parseDouble(bt.getTjNumber())));
                }
            }
            if(m.get("month")==null){
                m.put("month","0.00");
            }
            for(BaseTjReportQuery bt:li6){
                if((upa.getId()+"").equals(bt.getReturnType())){
                    m.put("thatmonth",df.format(Double.parseDouble(bt.getTjNumber())));
                }
            }
            if(m.get("thatmonth")==null){
                m.put("thatmonth","0.00");
            }
            lim.add(m);
        }
        jsonBean.setList(lim);
        jsonBean.setTotal((int) page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
    }

    @RequestMapping("operationPayPalAccount.do")
    @ResponseBody
    /**修改paypal状态*/
    public void operationPayPalAccount(Long paypalId,String stat){
        Map map=new HashMap();
        map.put("paypalId",paypalId);
        map.put("status",stat);
        payPalService.operationPayPalAccount(map);
        AjaxSupport.sendSuccessText("","操作成功!");
    }

    @RequestMapping("addPayPalAccount.do")
    @ResponseBody
    /**增加y一条paypal账户*/
    public void addPayPalAccount(UsercontrollerPaypalAccount paypalAccount){
        Map map =new HashMap();
        map.put("paypalAccountVO",paypalAccount);
        payPalService.addPayPalAccount(map);
        AjaxSupport.sendSuccessText("","操作成功！");
    }


}
