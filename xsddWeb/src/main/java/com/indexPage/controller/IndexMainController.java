package com.indexPage.controller;

import com.base.database.trading.model.TradingOrderAddMemberMessageAAQToPartner;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.hicharts.HchartsIndexVO;
import com.base.domains.querypojos.hicharts.PieVo;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.MyNumberUtils;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingOrderAddMemberMessageAAQToPartner;
import com.trading.service.ITradingOrderGetOrders;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2014/11/5.
 * 系统主页
 */
@Controller
public class IndexMainController extends BaseAction{
    @Autowired
    private ITradingOrderGetOrders iTradingOrderGetOrders;
    @Autowired
    private ITradingOrderAddMemberMessageAAQToPartner iTradingOrderAddMemberMessageAAQToPartner;
    /**打开首页*/
    @RequestMapping("indexInit.do")
    public ModelAndView indexInit(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        List<TradingOrderAddMemberMessageAAQToPartner> ebayMessages=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByTypeAndReplied(4,"false",sessionVO.getId());
        List<TradingOrderAddMemberMessageAAQToPartner> caseMessages=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByTypeAndReplied(1,"fasle",sessionVO.getId());
        modelMap.put("ebayMessages",ebayMessages.size());
        modelMap.put("caseMessages",caseMessages.size());
        return forword("/indexpage/indexPage",modelMap);
    }

    @RequestMapping("getTrenchData.do")
    @ResponseBody
    /**渠道分布数据(饼图)*/
    public void getTrenchData(){
        List<PieVo> pieVos=new ArrayList<PieVo>();
        PieVo pieVo=new PieVo();
        pieVo.setName("new york");
        pieVo.setY(MyNumberUtils.str2DoubleAndSCALE2("26.8"));
        pieVos.add(pieVo);

        PieVo pieVo1=new PieVo();
        pieVo1.setName("new york1");
        pieVo1.setY(MyNumberUtils.str2DoubleAndSCALE2("26.8"));
        pieVos.add(pieVo1);

        PieVo pieVo2=new PieVo();
        pieVo2.setName("new york2");
        pieVo2.setY(MyNumberUtils.str2DoubleAndSCALE2("26.8"));
        pieVos.add(pieVo2);

        PieVo pieVo3=new PieVo();
        pieVo3.setName("new york3");
        pieVo3.setY(MyNumberUtils.str2DoubleAndSCALE2("80"));
        pieVos.add(pieVo3);


        List<HchartsIndexVO> list=new ArrayList<HchartsIndexVO>();
        HchartsIndexVO hchartsIndexVO=new HchartsIndexVO();
        hchartsIndexVO.setName("qudao");
        hchartsIndexVO.setType("pie");
        hchartsIndexVO.setData(pieVos);
        list.add(hchartsIndexVO);

        AjaxSupport.sendSuccessText("",list);

    }

    /**单量走势，曲线图*/
    @RequestMapping("getOrderCountData.do")
    @ResponseBody
    public void getOrderCountData(HttpServletRequest request){
        String time=request.getParameter("time");
        if(time==null){
            time="15";
        }
        /*String xAxis="['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']";*/
        String xAxis="";
        List<HchartsIndexVO> hchartsIndexVOs=new ArrayList<HchartsIndexVO>();
        HchartsIndexVO h1=new HchartsIndexVO();
        h1.setName("d1");
        List<Double> l1 = new ArrayList<Double>();
        Date date1=new Date();
        if("1".equals(time)){
            Date date=DateUtils.addYears(date1,-1);
            int yeat= com.base.utils.common.DateUtils.getFullYear(date);
            int month= com.base.utils.common.DateUtils.getMonth(date);
            date=com.base.utils.common.DateUtils.buildDate(yeat, date.getMonth(), 1);
            date= com.base.utils.common.DateUtils.turnToDateStart(date);
            Map<Integer,String> map=new HashMap<Integer, String>();
            map.put(1,"Jan");
            map.put(2,"Feb");
            map.put(3,"Mar");
            map.put(4,"Apr");
            map.put(5,"May");
            map.put(6,"Jun");
            map.put(7,"Jul");
            map.put(8,"Aug");
            map.put(9,"Sep");
            map.put(10,"Oct");
            map.put(11,"Nov");
            map.put(12,"Dec");
            for(int i=1;i<13;i++){
                Date date3=DateUtils.addMonths(date,i+1);
                Date start=DateUtils.addMonths(date, i);
                Date end=com.base.utils.common.DateUtils.turnToDateEnd(DateUtils.addDays(date3, -1));
                int count=iTradingOrderGetOrders.selectOrderGetOrdersByeBayAccountAndTime(null,start,end).size();
                l1.add(Double.parseDouble(count+""));
                if(i==12){
                    xAxis+="'"+map.get(start.getMonth()+1)+"']";
                }else if(i==1){
                    xAxis+="['"+map.get(start.getMonth()+1)+"',";
                }else{
                    xAxis+="'"+map.get(start.getMonth()+1)+"',";
                }
            }
            /*xAxis="['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']";*/

        }else if("15".equals(time)){
            Date date=DateUtils.addDays(date1,-14);
            date1=com.base.utils.common.DateUtils.turnToDateEnd(date);
            date= com.base.utils.common.DateUtils.turnToDateStart(date);
            for(int i=0;i<15;i++){
                Date start=DateUtils.addDays(date,i);
                Date end=DateUtils.addDays(date1,i);
                int count=iTradingOrderGetOrders.selectOrderGetOrdersByeBayAccountAndTime(null,start,end).size();
                l1.add(Double.parseDouble(count+""));
                int month=start.getMonth()+1;
                int day= com.base.utils.common.DateUtils.getDayOfMonth(start);
                if(i==14){
                    xAxis+="'"+(month+"月"+day+"号")+"']";
                }else if(i==0){
                    xAxis+="['"+(month+"月"+day+"号")+"',";
                }else{
                    xAxis+="'"+(month+"月"+day+"号")+"',";
                }
            }
        }else if("30".equals(time)){
            Date date=DateUtils.addDays(date1,-29);
            date1=com.base.utils.common.DateUtils.turnToDateEnd(date);
            date= com.base.utils.common.DateUtils.turnToDateStart(date);
            for(int i=0;i<30;i++){
                Date start=DateUtils.addDays(date,i);
                Date end=DateUtils.addDays(date1,i);
                int count=iTradingOrderGetOrders.selectOrderGetOrdersByeBayAccountAndTime(null,start,end).size();
                l1.add(Double.parseDouble(count+""));
                int month=start.getMonth()+1;
                int day= com.base.utils.common.DateUtils.getDayOfMonth(start);
                if(i==29){
                    xAxis+="'"+(month+"-"+day)+"']";
                }else if(i==0){
                    xAxis+="['"+(month+"-"+day)+"',";
                }else{
                    xAxis+="'"+(month+"-"+day)+"',";
                }
            }
        }
        h1.setData(l1);
        hchartsIndexVOs.add(h1);
        AjaxSupport.sendSuccessText(xAxis,hchartsIndexVOs);
    }
}
