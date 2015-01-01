package com.inventorysale.controller;

import com.base.database.inventory.model.ItemInventory;
import com.base.database.inventory.model.ShihaiyouInventory;
import com.base.domains.CommonParmVO;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.inventory.service.IItemInventory;
import org.apache.log4j.Logger;
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
 * Created by Administrtor on 2014/12/22.
 */
@Controller
@RequestMapping("inventorySale")
public class InventorySaleController extends BaseAction{
    static Logger logger = Logger.getLogger(InventorySaleController.class);
    @Autowired
    private IItemInventory iItemInventory;
    @RequestMapping("/inventorySaleList.do")
    public ModelAndView queryOrdersList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("/inventorySale/inventorySaleList",modelMap);
    }
    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadItemInventoryList.do")
    @ResponseBody
    public void loadItemInventoryList(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String content=request.getParameter("content");
        String flag=request.getParameter("flag");
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        Map m=new HashMap();
        m.put("content",content);
        m.put("flag",flag);
        List lists=new ArrayList();
        if("true".equals(flag)){
            lists=iItemInventory.selectItemInventoryTableList(m, page);
        }else{
            lists=iItemInventory.selectItemInventoryTableList1(m,page);
        }
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
    @RequestMapping("/editChuKouYi.do")
    public ModelAndView editChuKouYi(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String sku=request.getParameter("sku");
        List<ItemInventory> inventory= iItemInventory.selectBySku(sku);
        modelMap.put("inventory",inventory.get(0));
        return forword("/inventorySale/editChuKouYi",modelMap);
    }
    @RequestMapping("/editSiHaiYou.do")
    public ModelAndView editSiHaiYou(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String sku=request.getParameter("sku");
        List<ShihaiyouInventory> inventory= iItemInventory.selectShiHaiYouByBySku(sku);
        modelMap.put("inventory",inventory.get(0));
        return forword("/inventorySale/editSiHaiYou",modelMap);
    }
    /**获取list数据的ajax方法*/
    @RequestMapping(" /ajax/saveChuKouYi.do")
    @ResponseBody
    public void saveChuKouYi(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String id=request.getParameter("id");
        String sku=request.getParameter("sku");
        List<ItemInventory> inventorie=iItemInventory.selectBySku(sku);
        AjaxSupport.sendSuccessText("","保存成功");
    }
    /**获取list数据的ajax方法*/
    @RequestMapping(" /ajax/saveShiHaiYou.do")
    @ResponseBody
    public void saveShiHaiYou(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String id=request.getParameter("id");
        String sku=request.getParameter("sku");
        List<ShihaiyouInventory> inventories=iItemInventory.selectShiHaiYouByBySku(sku);
        AjaxSupport.sendSuccessText("","保存成功");

    }
}
