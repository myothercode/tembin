package com.module.controller;

import com.base.database.trading.model.TradingDataDictionary;
import com.base.database.trading.model.TradingItemAddress;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.ItemAddressQuery;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.ObjectUtils;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingDataDictionary;
import com.trading.service.ITradingItemAddress;
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
 * Created by Administrtor on 2014/7/28.
 */
@Controller
public class ItemAddressController  extends BaseAction {

    @Autowired
    private ITradingItemAddress iTradingItemAddress;
    @Autowired
    private ITradingDataDictionary iTradingDataDictionary;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    /**
     * 查询列表页面跳转
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/ItemAddressList.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView itemAddressList(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        /*List<ItemAddressQuery> li = this.iTradingItemAddress.selectByItemAddressQuery(null);
        modelMap.put("li",li);*/
        return forword("module/itemaddr/ItemAddressList",modelMap);
    }
    @RequestMapping("/ajax/loadItemAddressList.do")
    @ResponseBody
    public void loadItemAddressList(HttpServletRequest request,CommonParmVO commonParmVO){
        Map m = new HashMap();
        String checkFlag = request.getParameter("checkFlag");
        m.put("checkFlag",checkFlag);
        SessionVO c= SessionCacheSupport.getSessionVO();
        if(systemUserManagerService.isAdminRole()){
            List<UsercontrollerUserExtend> liuue = systemUserManagerService.queryAllUsersByOrgID("yes");
            List<String> liue = new ArrayList<String>();
            for(UsercontrollerUserExtend uue:liuue){
                liue.add(uue.getUserId()+"");
            }
            liue.add(c.getId()+"");
            m.put("liue",liue);
        }else{
            m.put("userid",c.getId());
        }
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<ItemAddressQuery> li = this.iTradingItemAddress.selectByItemAddressQuery(m,page);
        jsonBean.setList(li);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    /**
     * 保存数据
     * @param name
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/saveItemAddress.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveItemAddress(String name, HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        //String name = request.getParameter("name");
        String address = request.getParameter("address");
        String countryList = request.getParameter("countryList");
        String postalCode = request.getParameter("postalCode");
        String id = request.getParameter("id");

        TradingItemAddress tia = new TradingItemAddress();
        ObjectUtils.toInitPojoForInsert(tia);
        if(!ObjectUtils.isLogicalNull(id)){
            tia.setId(Long.parseLong(id));
        }
        tia.setName(name);
        tia.setAddress(address);
        tia.setPostalcode(postalCode);
        tia.setCountryId(Long.parseLong(countryList));
        this.iTradingItemAddress.saveItemAddress(tia);
        AjaxSupport.sendSuccessText("","操作成功!");
    }

    /**
     * 新增界面跳转
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/addItemAddress.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addItemAddress(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap")ModelMap modelMap){
        List<TradingDataDictionary> lidata = this.iTradingDataDictionary.selectDictionaryByType("country");
        modelMap.put("countryList",lidata);
        return forword("module/itemaddr/addItemAddress",modelMap);
    }

    /**
     * 编辑页面跳转
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/editItemAddress.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editItemAddress(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        List<TradingDataDictionary> lidata = this.iTradingDataDictionary.selectDictionaryByType("country");
        modelMap.put("countryList",lidata);
        Map map = new HashMap();
        map.put("id",request.getParameter("id"));
        List<ItemAddressQuery> li = this.iTradingItemAddress.selectByItemAddressQuery(map);
        modelMap.put("itemAddress",li.get(0));
        String type = request.getParameter("type");
        if(type!=null&&!"".equals(type)){
            modelMap.put("type",type);
        }
        return forword("module/itemaddr/addItemAddress",modelMap);
    }

    /**
     * 保存数据
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/delItemAddress.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void delItemAddress(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id = request.getParameter("id");
        TradingItemAddress tp= this.iTradingItemAddress.selectById(Long.parseLong(id));
        if("1".equals(tp.getCheckFlag())){
            tp.setCheckFlag("0");
        }else{
            tp.setCheckFlag("1");
        }
        this.iTradingItemAddress.saveItemAddress(tp);
        AjaxSupport.sendSuccessText("","操作成功!");
    }

}
