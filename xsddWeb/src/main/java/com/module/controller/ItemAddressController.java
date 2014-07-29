package com.module.controller;

import com.base.database.trading.model.TradingDataDictionary;
import com.base.database.trading.model.TradingItemAddress;
import com.base.domains.querypojos.ItemAddressQuery;
import com.base.utils.common.ObjectUtils;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingDataDictionary;
import com.trading.service.ITradingItemAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping("/ItemAddressList.do")
    public ModelAndView itemAddressList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        List<ItemAddressQuery> li = this.iTradingItemAddress.selectByItemAddressQuery(null);
        modelMap.put("li",li);
        return forword("module/itemaddr/ItemAddressList",modelMap);
    }

    @RequestMapping("/saveItemAddress.do")
    public ModelAndView saveItemAddress(String name, HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        //String name = request.getParameter("name");
        String address = request.getParameter("address");
        String countryList = request.getParameter("countryList");
        String postalCode = request.getParameter("postalCode");
        String id = request.getParameter("id");

        TradingItemAddress tia = new TradingItemAddress();
        ObjectUtils.toPojo(tia);
        if(!ObjectUtils.isLogicalNull(id)){
            tia.setId(Long.parseLong(id));
        }
        tia.setName(name);
        tia.setAddress(address);
        tia.setPostalcode(postalCode);
        tia.setCountryId(Long.parseLong(countryList));
        ObjectUtils.toPojo(tia);
        this.iTradingItemAddress.saveItemAddress(tia);

        return forword("module/itemaddr/ItemAddressList",modelMap);
    }

    @RequestMapping("/addItemAddress.do")
    public ModelAndView addItemAddress(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        List<TradingDataDictionary> lidata = this.iTradingDataDictionary.selectDictionaryByType("country");
        modelMap.put("countryList",lidata);
        return forword("module/itemaddr/addItemAddress",modelMap);
    }

    @RequestMapping("/editItemAddress.do")
    public ModelAndView editItemAddress(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        List<TradingDataDictionary> lidata = this.iTradingDataDictionary.selectDictionaryByType("country");
        modelMap.put("countryList",lidata);
        Map map = new HashMap();
        map.put("id",request.getParameter("id"));
        List<ItemAddressQuery> li = this.iTradingItemAddress.selectByItemAddressQuery(map);
        modelMap.put("itemAddress",li.get(0));

        return forword("module/itemaddr/addItemAddress",modelMap);
    }

}