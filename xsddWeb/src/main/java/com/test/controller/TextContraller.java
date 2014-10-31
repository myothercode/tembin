package com.test.controller;

import com.base.database.publicd.model.PublicDataDict;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.database.userinfo.model.UsercontrollerOrg;
import com.base.database.userinfo.model.UsercontrollerUser;
import com.base.domains.LoginVO;
import com.base.domains.SessionVO;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.base.utils.mailUtil.MailUtils;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.test.service.TestService;
import com.trading.service.ITradingDataDictionary;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**登录后的主框架页面*/
    @RequestMapping("/mainFrame.do")
    public ModelAndView mainFrame(HttpServletRequest request,HttpServletResponse response,
                             @ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        // Map map = new HashMap();
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        modelMap.put("ccc",sessionVO.getUserName());
        return forword("mainFrame",modelMap);
    }

   /* *//**注册用户*/
    @RequestMapping("doReglogin.do")
    public void doReg(UsercontrollerUser user ,UsercontrollerOrg org,
                      HttpServletRequest request,HttpServletResponse response,
                              @ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        //Asserts.assertTrue(ObjectUtils.isNumOrChar(user.getUserLoginId()),"登录名只能由数字和字母组成!");
        Asserts.assertTrue(StringUtils.isNotEmpty(user.getUserEmail()),"邮箱不能为空!");
        Asserts.assertTrue(StringUtils.isNotEmpty(user.getUserPassword()),"密码不能为空!");
        user.setUserLoginId(user.getUserEmail());
        Map map =new HashMap();
        map.put("UsercontrollerUser",user);
        map.put("UsercontrollerOrg",org);
        userInfoService.regInsertUserInfo(map);
        AjaxSupport.sendSuccessText("success","注册成功!请登录!");
    }


    /**登录操作*/
    @RequestMapping("/login.do")
    public ModelAndView login(LoginVO loginVO,HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap
                              ){
        if(ObjectUtils.isLogicalNull(loginVO.getLoginId()) || "请输入你的帐号".equalsIgnoreCase(loginVO.getLoginId())){
            request.getSession().setAttribute("errMessage_","登信息为空");
            return redirect("/login.jsp");
        }
        //首先检查是否需要检查验证码，以及验证验证码是否正确
        Object vcode = request.getSession().getAttribute("valCapCode");
        if(vcode!=null && "yes".equalsIgnoreCase((String)vcode)){
            String code = (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
            if(!code.equalsIgnoreCase(loginVO.getCapcode())){
                request.getSession().setAttribute("showCapImage","yes");
                request.getSession().setAttribute("valCapCode","yes");
                request.getSession().setAttribute("errMessage_","验证码错误!");
                return redirect("/login.jsp");
            }
        }
       //检查账户密码师傅正确
        SessionVO sessionVO = userInfoService.getUserInfo(loginVO);
        if(ObjectUtils.isLogicalNull(sessionVO)){
            request.getSession().setAttribute("errMessage_","帐号或者密码不正确");
            Object num=request.getSession().getAttribute("loginFailNum_");
            if(num==null){
                request.getSession().setAttribute("loginFailNum_",1);
            }else {
                Integer i=(Integer)num+1;
                request.getSession().setAttribute("loginFailNum_",i);
            }
            Integer ii = (Integer) request.getSession().getAttribute("loginFailNum_");
            if(ii>3){//如果输入次数超过了3次，那么就需要输入验证码
                request.getSession().setAttribute("showCapImage","yes");
                request.getSession().setAttribute("valCapCode","yes");
            }

            return redirect("/login.jsp");
        }
        request.getSession().removeAttribute("valCapCode");
        request.getSession().setAttribute(SessionCacheSupport.USERLOGINID, sessionVO.getLoginId());
        sessionVO.setLoginId(sessionVO.getLoginId());
        SessionCacheSupport.remove(sessionVO.getLoginId());
        sessionVO.setSessionID(request.getSession().getId());
        SessionCacheSupport.put(sessionVO);
        return redirect("mainFrame.do");
        //modelMap.put("ccc",sessionVO.getUserName());
        //return forword("test",modelMap);
    }
    /**登出操作*/
    @RequestMapping("/logout.do")
    public ModelAndView logout(HttpServletRequest request) throws IOException {
        SessionVO sessionVO=SessionCacheSupport.getSessionVO();
        SessionCacheSupport.remove(sessionVO.getLoginId());
        HttpSession session = request.getSession();
        session.removeAttribute(SessionCacheSupport.USERLOGINID);
        session.invalidate();
        return redirect("/login.jsp");
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
        String x = TempStoreDataSupport.pullData(request.getSession().getId());
        //testService.serviceTest();
       // testService.testReturnPolicy();

        AjaxSupport.sendSuccessText("啊", x);
    }

    /**发送修改密码的验证码*/
    @RequestMapping("sometest.do")
    public void sometest() throws Exception {
        Email email = new SimpleEmail();
        email.addTo("caixu23@qq.com");
        email.setSubject("这个是标题");
        email.setMsg("这个是测试邮件");

        MailUtils mailUtils= (MailUtils) ApplicationContextUtil.getBean("mailUtils");
        mailUtils.sendMail(email);
        AjaxSupport.sendSuccessText("","a");
    }

}
