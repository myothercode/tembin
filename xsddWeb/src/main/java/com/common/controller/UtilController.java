package com.common.controller;

import com.base.database.publicd.model.PublicDataDict;
import com.base.domains.SessionVO;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.CategoryAPI;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DictCollectionsUtil;
import com.base.utils.common.UUIDUtil;
import com.base.utils.exception.Asserts;
import com.base.utils.threadpool.AddApiTask;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingDataDictionary;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/31.
 * 提供一些通用值返回
 */

@Controller
public class UtilController extends BaseAction{
    static Logger logger = Logger.getLogger(UtilController.class);
    @Value("${EBAY.API.URL}")
    private String apiUrl;

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ITradingDataDictionary tradingDataDictionary;

    /**用于更新页面token*/
    @RequestMapping("/ajax/getToken.do")
    @ResponseBody
    public void getToken(HttpServletRequest request){
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        if(sessionVO==null){
            AjaxSupport.sendFailText("sessionStatusFalse", "未登录或者已过期");
            return;
        }
        String t=sessionVO.getLoginId()+ UUIDUtil.getUUID();
        request.getSession(false).removeAttribute("_token");
        request.getSession(false).setAttribute("_token", t);
        AjaxSupport.sendSuccessText("_token",t);
    }

    /**商品类别相关开始==========================================================*/
    /**用于获取商品类别的属性*/
    @RequestMapping("/ajax/getCategorySpecifics.do")
    @ResponseBody
    public void getCategorySpecifics(String parentCategoryID,HttpServletRequest request) throws Exception {
        Asserts.assertTrue(StringUtils.isNotEmpty(parentCategoryID),"目录id不能为空");
        List<PublicDataDict> publicDataDictList = DataDictionarySupport.getPublicDataDictionaryByParentID(Long.valueOf(parentCategoryID),
                DictCollectionsUtil.categorySpecifics);
        if(publicDataDictList!=null && publicDataDictList.size()>0){
            AjaxSupport.sendSuccessText("",publicDataDictList);
            return;
        }
        //获取当前帐号绑定的开发帐号信息
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.GetCategorySpecifics);
        //获取当前登录人的所有ebay帐号
        List<UsercontrollerEbayAccountExtend> ebays = userInfoService.getEbayAccountForCurrUser();
        Long ebayID=ebays.get(0).getId();
        String token = userInfoService.getTokenByEbayID(ebayID);
        String xml=CategoryAPI.getCategorySpecificsRequest(token,parentCategoryID);

        AddApiTask addApiTask = new AddApiTask();
        Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
        String r1 = resMap.get("stat");
        String res = resMap.get("message");
        if("fail".equalsIgnoreCase(r1)){
            AjaxSupport.sendFailText("fail",res);
            return;
        }
        List<PublicDataDict> publicDataDictList1 = tradingDataDictionary.addPublicData(res);

       // DataDictionarySupport.removePublicDictCache();

        AjaxSupport.sendSuccessText("",publicDataDictList1);
        return;
    }

    /**获取类别目录菜单
     * 如果两个参数都不传，那么返回一级菜单
     * 如果只传parentid，那么返回对应的parent菜单
     * */
    @RequestMapping("/ajax/getCategoryMenu.do")
    @ResponseBody
    public void getCategoryMenu(String parentID,String level){
        List<PublicDataDict> publicDataDictList=null;
        if(StringUtils.isEmpty(level)){
            level=DictCollectionsUtil.ITEM_LEVEL_ONE;
        }
        if(StringUtils.isEmpty(parentID) || "0".equals(parentID)){
            parentID="0";
            level = DictCollectionsUtil.ITEM_LEVEL_ONE;
            publicDataDictList = DataDictionarySupport.getPublicDataDictionaryByItemLevel(Long.valueOf(parentID),level,DictCollectionsUtil.category);
        }else {
            publicDataDictList = DataDictionarySupport.getPublicDataDictionaryByParentID(Long.valueOf(parentID),DictCollectionsUtil.category);
        }


        AjaxSupport.sendSuccessText("",publicDataDictList);
        return;

    }
    /**商品目录选择页面初始化*/
    @RequestMapping("category/initSelectCategoryPage.do")
    public ModelAndView initSelectCategoryPage(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        return forword("/commonPage/category/popSelectCategoryPage",modelMap);
    }
/**商品类别相关结束==========================================================*/

}
