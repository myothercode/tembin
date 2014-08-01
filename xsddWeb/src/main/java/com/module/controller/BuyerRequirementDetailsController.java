package com.module.controller;

import com.base.database.trading.model.TradingBuyerRequirementDetails;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.domains.CommonParmVO;
import com.base.domains.querypojos.BuyerRequirementDetailsQuery;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.common.ObjectUtils;
import com.base.xmlpojo.trading.addproduct.*;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingBuyerRequirementDetails;
import com.trading.service.ITradingDataDictionary;
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
 * Created by cz on 2014/7/24.
 */
@Controller
public class BuyerRequirementDetailsController extends BaseAction {

    @Autowired
    private ITradingBuyerRequirementDetails iTradingBuyerRequirementDetails;
    @Autowired
    private ITradingDataDictionary iTradingDataDictionary;


    /**
     * 查询列表界面跳转
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/BuyerRequirementDetailsList.do")
    public ModelAndView buyerRequirementDetails(HttpServletRequest request,HttpServletResponse response,
                                                @ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        /*Map m = new HashMap();
        List<BuyerRequirementDetailsQuery> li = this.iTradingBuyerRequirementDetails.selectTradingBuyerRequirementDetailsByList(m);
        modelMap.put("li",li);*/
        return forword("module/buyer/BuyerRequirementDetailsList",modelMap);
    }
    @RequestMapping("/ajax/loadBuyerRequirementDetailsList.do")
    @ResponseBody
    public void loadBuyerRequirementDetailsList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,CommonParmVO commonParmVO){
        Map m = new HashMap();
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<BuyerRequirementDetailsQuery> li = this.iTradingBuyerRequirementDetails.selectTradingBuyerRequirementDetailsByList(m,page);
        jsonBean.setList(li);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    /**
     * 新增界面跳转
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/addBuyer.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addBuyer(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        List<TradingDataDictionary> lidata = this.iTradingDataDictionary.selectDictionaryByType("site");
        modelMap.put("siteList",lidata);
        return forword("module/buyer/addBuyer",modelMap);
    }

    /**
    * 编辑界面跳转
    * @param request
    * @param response
    * @param modelMap
    * @return
    */
    @RequestMapping("/editBuyer.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editBuyer(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        List<TradingDataDictionary> lidata = this.iTradingDataDictionary.selectDictionaryByType("site");
        modelMap.put("siteList",lidata);
        Map m=new HashMap();
        m.put("id",request.getParameter("id"));
        List<BuyerRequirementDetailsQuery> buyerRequires=this.iTradingBuyerRequirementDetails.selectTradingBuyerRequirementDetailsByList(m);
        modelMap.put("buyerRequires",buyerRequires.get(0));
        return forword("module/buyer/addBuyer",modelMap);
    }

    /**
     * 保存数据
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/saveBuyer.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveBuyer(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String buyName = request.getParameter("buyName");
        String site = request.getParameter("site");
        String buyer_flag = request.getParameter("buyer_flag");
        String LinkedPayPalAccount = request.getParameter("LinkedPayPalAccount");
        String ShipToRegistrationCountry = request.getParameter("ShipToRegistrationCountry");
        String Unpaid_count = request.getParameter("Unpaid_count");
        String Unpaid_period = request.getParameter("Unpaid_period");
        String Policy_count = request.getParameter("Policy_count");
        String Policy_period = request.getParameter("Policy_period");
        String MinimumFeedbackScore = request.getParameter("MinimumFeedbackScore");
        String MaximumItemCount = request.getParameter("MaximumItemCount");
        String FeedbackScore = request.getParameter("FeedbackScore");
        String id = request.getParameter("id");
        BuyerRequirementDetails  brd = null;
        if(buyer_flag!=null&&!"1".equals(buyer_flag)){
            brd = new BuyerRequirementDetails();
            if(LinkedPayPalAccount!=null){
                brd.setLinkedPayPalAccount("1".equals(LinkedPayPalAccount)?true:false);
            }
            if(ShipToRegistrationCountry!=null){
                brd.setShipToRegistrationCountry("1".equals(ShipToRegistrationCountry)?true:false);
            }
            if(MinimumFeedbackScore!=null){
                brd.setMinimumFeedbackScore(Integer.parseInt(MinimumFeedbackScore));

                VerifiedUserRequirements vur = new VerifiedUserRequirements();
                vur.setVerifiedUser(true);
                vur.setMinimumFeedbackScore(Integer.parseInt(MinimumFeedbackScore));
                brd.setVerifiedUserRequirements(vur);
            }
            brd.setZeroFeedbackScore(false);
            if(Policy_count!=null&&Policy_period!=null){
                MaximumBuyerPolicyViolations mbpv = new MaximumBuyerPolicyViolations();
                mbpv.setCount(Integer.parseInt(Policy_count));
                mbpv.setPeriod(Policy_period+"_DAY");
                brd.setMaximumBuyerPolicyViolations(mbpv);
            }
            if(Unpaid_count!=null&&Unpaid_period!=null){
                MaximumUnpaidItemStrikesInfo muis = new MaximumUnpaidItemStrikesInfo();
                muis.setCount(Integer.parseInt(Unpaid_count));
                muis.setPeriod(Unpaid_period+"_Day");
                brd.setMaximumUnpaidItemStrikesInfo(muis);
            }
            if(MaximumItemCount!=null||FeedbackScore!=null){
                MaximumItemRequirements mir = new MaximumItemRequirements();
                if(MaximumItemCount!=null) {
                    mir.setMaximumItemCount(Integer.parseInt(MaximumItemCount));
                }
                if(FeedbackScore!=null) {
                    mir.setMinimumFeedbackScore(Integer.parseInt(FeedbackScore));
                }
                brd.setMaximumItemRequirements(mir);
            }

        }
        TradingBuyerRequirementDetails tbrds = this.iTradingBuyerRequirementDetails.toDAOPojo(brd);
        tbrds.setName(buyName);
        tbrds.setBuyerFlag(buyer_flag);
        if(brd!=null){
            if(brd.getMaximumBuyerPolicyViolations()!=null){
                tbrds.setPolicyCount(brd.getMaximumBuyerPolicyViolations().getCount().longValue());
                tbrds.setPolicyPeriod(brd.getMaximumBuyerPolicyViolations().getPeriod());
            }
            if(brd.getMaximumUnpaidItemStrikesInfo()!=null){
                tbrds.setUnpaidCount(brd.getMaximumUnpaidItemStrikesInfo().getCount().longValue());
                tbrds.setUnpaidPeriod(brd.getMaximumUnpaidItemStrikesInfo().getPeriod());
            }
            if(brd.getVerifiedUserRequirements()!=null){
                tbrds.setVerifiedFlag(brd.getVerifiedUserRequirements().getVerifiedUser()==true?"1":"0");
                tbrds.setVerifiedFeedbackscore(Long.parseLong(brd.getVerifiedUserRequirements().getMinimumFeedbackScore()+""));
            }
            if(brd.getMinimumFeedbackScore()!=null){
                tbrds.setFeedbackscore(brd.getMinimumFeedbackScore().longValue());
            }
        }
        tbrds.setSiteCode(Integer.parseInt(site));
        if(!ObjectUtils.isLogicalNull(id)){
            tbrds.setId(Long.parseLong(id));
        }
        //List<TradingDataDictionary> lidata = this.iTradingDataDictionary.s
       // tbrds.setSiteValue(lidata.get(0).getValue());

        this.iTradingBuyerRequirementDetails.saveBuyerRequirementDetails(tbrds);
        AjaxSupport.sendSuccessText("","操作成功!");
    }
}
