package com.assess.controller;

import com.base.database.trading.model.TradingFeedBackDetail;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.PaypalQuery;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.utils.cache.SessionCacheSupport;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingFeedBackDetail;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.cookie.DateParseException;
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
 * Created by Administrtor on 2014/12/10.
 */
@Controller
public class ClientAssessController extends BaseAction{

    @Autowired
    private ITradingFeedBackDetail iTradingFeedBackDetail;
    /**
     * 客户评价管理界面
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/clientassess/clientAssessManager.do")
    public ModelAndView clientAssessManager(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws DateParseException {
        return forword("clientassess/clientassessmanager",modelMap);
    }

    /**
     * 加载界面数据
     * @param request
     * @param modelMap
     * @param commonParmVO
     * @throws DateParseException
     */
    @RequestMapping("/ajax/loadClientAssessTable.do")
    @ResponseBody
    public void loadClientAssessTable(HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,CommonParmVO commonParmVO) throws DateParseException {
        Map m = new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        m.put("userid",c.getId());
        String commentType = request.getParameter("commentType");
        if(commentType!=null&&!"".equals(commentType)){
            m.put("commentType",commentType);
        }
        String selecttype = request.getParameter("selecttype");
        String selectvalue = request.getParameter("selectvalue");
        if(StringUtils.isNotEmpty(selecttype)){
            m.put("selecttype",selecttype);
            if(StringUtils.isNotEmpty(selectvalue)){
                m.put("selectvalue",selectvalue);
            }
        }

        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<TradingFeedBackDetail> paypalli = this.iTradingFeedBackDetail.selectClientAssessFeedBackList(m,page);
        jsonBean.setList(paypalli);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

}
