package com.test.controller;

import com.base.database.publicd.model.PublicDataDict;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.domains.LoginVO;
import com.base.domains.SessionVO;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.test.service.TestService;
import com.trading.service.ITradingDataDictionary;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chace.cai on 2014/6/20.
 * 测试
 */
@Controller
public class TextContraller extends BaseAction {
    static Logger logger = Logger.getLogger(TextContraller.class);
    @Autowired
    private TestService testService;
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ITradingDataDictionary tradingDataDictionary;


    @RequestMapping("/test.do")
    public ModelAndView test(HttpServletRequest request,HttpServletResponse response,
                             @ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
       // Map map = new HashMap();
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        modelMap.put("ccc",sessionVO.getUserName());
        return forword("test",modelMap);
    }

    @RequestMapping("/login.do")
    public ModelAndView login(LoginVO loginVO,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){

        Asserts.assertTrue(!ObjectUtils.isLogicalNull(loginVO.getLoginId()),"登信息为空");
        SessionVO sessionVO = userInfoService.getUserInfo(loginVO);
        Asserts.assertTrue(!ObjectUtils.isLogicalNull(sessionVO),"帐号或者密码不正确!");
        request.getSession().setAttribute(SessionCacheSupport.USERLOGINID, sessionVO.getLoginId());
        sessionVO.setLoginId(sessionVO.getLoginId());
        SessionCacheSupport.remove(sessionVO.getLoginId());
        sessionVO.setSessionID(request.getSession().getId());
        SessionCacheSupport.put(sessionVO);
        return redirect("test.do");
        //modelMap.put("ccc",sessionVO.getUserName());

        //return forword("test",modelMap);
    }

    @RequestMapping("/test1.do")
    @ResponseBody
    public void test1(HttpServletRequest request){
        SessionVO c= SessionCacheSupport.getSessionVO();
        //List<TradingDataDictionary> x= tradingDataDictionary.selectDictionaryByType("site");
        List<TradingDataDictionary> x = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        List<PublicDataDict> x1=DataDictionarySupport.getPublicDataDictionaryByType("category");
        List<PublicUserConfig> x2=DataDictionarySupport.getPublicUserConfigByType("ebayaccount",c.getId());


        ApplicationContext xc= ApplicationContextUtil.getContext();
        CacheManager cacheManager= (CacheManager) xc.getBean(CacheManager.class);
        String[] ss = cacheManager.getCacheNames();//查询有哪些缓存库
        Cache cache =cacheManager.getCache("dataDictionaryCache");
        List ll = cache.getKeys();

        request.getSession().setAttribute("vvv","eee");
        //testService.serviceTest();
        TestVO testVO=new TestVO();
        TestVO testVO1=new TestVO();
        testVO1.setBb("的发");
        TestVO testVO2=new TestVO();
        List<TestVO> testVOs=new ArrayList<TestVO>();
        testVOs.add(testVO);
        testVOs.add(testVO1);
        testVOs.add(testVO2);
//Integer.parseInt("f");
        AjaxSupport.sendSuccessText("啊", c);
    }

    @RequestMapping("/test2.do")
    @ResponseBody
    public void test2(HttpServletRequest request,@RequestParam("tt")String tt) throws Exception {
        //request.getSession().setAttribute("vvv","eee");
        //testService.serviceTest();
       // testService.testReturnPolicy();
//userInfoService.ebayIsBindDev(6L);
        AjaxSupport.sendSuccessText("啊", tt);
    }
    @RequestMapping("/xxlogin.do")
    @ResponseBody
    public void xxlogin(HttpServletRequest request) throws Exception {
        request.getSession().setAttribute("vvv","eee");
        //testService.serviceTest();
       // testService.testReturnPolicy();

        AjaxSupport.sendSuccessText("啊", "dfd");
    }

    @RequestMapping("sometest.do")
    public ModelAndView sometest(ModelMap modelMap){
        return forword("sometest",modelMap);

    }

}
