package com.iteminformation.controller;

import com.base.database.publicd.model.*;
import com.base.domains.CommonParmVO;
import com.base.domains.querypojos.ItemInformationQuery;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.publicd.service.*;
import org.apache.commons.lang.StringUtils;
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
 * Created by Administrtor on 2014/9/4.
 */
@Controller
@RequestMapping("information")
public class ItemInformationController extends BaseAction {
    @Autowired
    private IPublicItemInformation iPublicItemInformation;
    @Autowired
    private IPublicUserConfig iPublicUserConfig;
    @Autowired
    private IPublicItemSupplier iPublicItemSupplier;
    @Autowired
    private IPublicItemCustom iPublicItemCustom;
    @Autowired
    private IPublicItemInventory iPublicItemInventory;
    /*
   *纠纷列表
   */
    @RequestMapping("/itemInformationList.do")
    public ModelAndView itemInformationList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("/itemInformation/itemInformationList",modelMap);
    }
    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadItemInformationList.do")
    @ResponseBody
    public void loadItemInformationList(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        /**分页组装*/
        String remark=request.getParameter("remark");
        String information=request.getParameter("information");
        String itemType=request.getParameter("itemType");
        String content=request.getParameter("content");
        if("all".equals(remark)){
            remark=null;
        }
        if("all".equals(information)){
            information=null;
        }
        if("all".equals(itemType)){
            itemType=null;
        }
        if(StringUtils.isNotBlank(content)){
            content=null;
        }
        Map m = new HashMap();
        m.put("remark",remark);
        m.put("information",information);
        m.put("itemType",itemType);
        m.put("content",content);
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<ItemInformationQuery> list=iPublicItemInformation.selectItemInformation(m,page);
        jsonBean.setList(list);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
    /*
     *初始化添加商品界面
     */
    @RequestMapping("/addItemInformation.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addItemInformation(HttpServletRequest request,HttpServletResponse response,
                                        @ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        String id=request.getParameter("id");
        PublicItemInformation itemInformation=new PublicItemInformation();
        PublicItemInventory inventory=new PublicItemInventory();
        PublicItemCustom custom=new PublicItemCustom();
        PublicItemSupplier supplier=null;
        if(StringUtils.isNotBlank(id)){
            itemInformation=iPublicItemInformation.selectItemInformationByid(Long.valueOf(id));
            if(itemInformation.getInventoryId()!=null){
                inventory=iPublicItemInventory.selectItemInventoryByid(itemInformation.getInventoryId());
            }
            if(itemInformation.getCustomId()!=null){
                custom=iPublicItemCustom.selectItemCustomByid(itemInformation.getCustomId());
            }
            if(itemInformation.getSupplierId()!=null){
                supplier=iPublicItemSupplier.selectItemSupplierByid(itemInformation.getSupplierId());
            }
        }
        List<PublicUserConfig> types=iPublicUserConfig.selectUserConfigByItemType();
        modelMap.put("types",types);
        modelMap.put("itemInformation",itemInformation);
        modelMap.put("inventory",inventory);
        modelMap.put("custom",custom);
        modelMap.put("supplier",supplier);
        return forword("/itemInformation/addItemInformation",modelMap);
    }
   /* *//*
     *添加供应商
     *//*
    @RequestMapping("/addSupplier.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addSupplier(HttpServletRequest request,HttpServletResponse response,
                                           @ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        return forword("/itemInformation/addSupplier",modelMap);
    }
    /*
     *删除产品信息
     */
   @RequestMapping("/ajax/removeItemInformation.do")
   @AvoidDuplicateSubmission(needRemoveToken = true)
   @ResponseBody
   public void removeItemInformation(HttpServletRequest request) throws Exception {
       String id=request.getParameter("id");
       if(StringUtils.isNotBlank(id)){
           iPublicItemInformation.deleteItemInformation(Long.valueOf(id));
       }
       AjaxSupport.sendSuccessText("", "删除成功!");
   }
    /*
     *保存产品信息
     */
    @RequestMapping("/ajax/saveItemInformation.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveItemInformation(HttpServletRequest request) throws Exception {
        String name=request.getParameter("name");
        String sku=request.getParameter("sku");
        String itemType=request.getParameter("itemType");
        String warning=request.getParameter("warning");
        String length=request.getParameter("length");
        String width=request.getParameter("width");
        String height=request.getParameter("height");
        String customName=request.getParameter("customName");
        String weight=request.getParameter("weight");
        String declaredValue=request.getParameter("declaredValue");
        String currencyType=request.getParameter("currencyType");
        String place=request.getParameter("place");
        String supplierId=request.getParameter("supplierId");
        String supplierName=request.getParameter("supplierName");
        String supplierPrice=request.getParameter("supplierPrice");
        String supplierCurrency=request.getParameter("supplierCurrency");
        String supperSku=request.getParameter("supperSku");
        String supplierRemark=request.getParameter("supplierRemark");
        String customEnglishName=request.getParameter("customEnglishName");
        String id=request.getParameter("id");
        String inventoryid=request.getParameter("inventoryid");
        String customid=request.getParameter("customid");
        String supplierid=request.getParameter("supplierid");
        PublicItemInformation itemInformation=new PublicItemInformation();
        if(!StringUtils.isNotBlank(name)){
            AjaxSupport.sendFailText("fail","商品名称不能为空");
            return;
        }
        if(!StringUtils.isNotBlank(sku)){
            AjaxSupport.sendFailText("fail","sku不能为空");
            return;
        }
        if(supplierId!=null){
            PublicItemSupplier supplier=new PublicItemSupplier();
            supplier.setSupplierid(supplierId);
            supplier.setName(supplierName);
            supplier.setRemark(supplierRemark);
            supplier.setSuppersku(supperSku);
            supplier.setCurrency(supplierCurrency);
            if(StringUtils.isNotBlank(supplierPrice)){
                supplier.setPrice(Double.valueOf(supplierPrice));
            }
            if(StringUtils.isNotBlank(supplierid)){
                supplier.setId(Long.valueOf(supplierid));
            }
            iPublicItemSupplier.saveItemSupplier(supplier);
            itemInformation.setSupplierId(supplier.getId());
        }
        if(StringUtils.isNotBlank(customName)||StringUtils.isNotBlank(customEnglishName)||StringUtils.isNotBlank(currencyType)||StringUtils.isNotBlank(declaredValue)||StringUtils.isNotBlank(weight)||StringUtils.isNotBlank(place)){
            PublicItemCustom custom = new PublicItemCustom();
            custom.setName(customName);
            custom.setEnglishname(customEnglishName);
            custom.setCurrency(currencyType);
            if (StringUtils.isNotBlank(declaredValue)) {
                custom.setDeclaredvalue(Double.valueOf(declaredValue));
            }
            custom.setProductionplace(place);
            if (StringUtils.isNotBlank(weight)) {
                custom.setWeight(Double.valueOf(weight));
            }
            if(StringUtils.isNotBlank(customid)){
                custom.setId(Long.valueOf(customid));
            }
            iPublicItemCustom.saveItemCustom(custom);
            itemInformation.setCustomId(custom.getId());
        }
        if(StringUtils.isNotBlank(height)||StringUtils.isNotBlank(length)||StringUtils.isNotBlank(width)||StringUtils.isNotBlank(warning)) {
            PublicItemInventory inventory = new PublicItemInventory();
            if (StringUtils.isNotBlank(height)) {
                inventory.setHeight(Double.valueOf(height));
            }
            if (StringUtils.isNotBlank(length)) {
                inventory.setHeight(Double.valueOf(length));
            }
            if (StringUtils.isNotBlank(width)) {
                inventory.setHeight(Double.valueOf(width));
            }
            if (StringUtils.isNotBlank(warning)) {
                inventory.setHeight(Double.valueOf(warning));
            }
            if(StringUtils.isNotBlank(inventoryid)){
                inventory.setId(Long.valueOf(inventoryid));
            }
            iPublicItemInventory.saveItemInventory(inventory);
            itemInformation.setInventoryId(inventory.getId());
        }
        itemInformation.setName(name);
        itemInformation.setSku(sku);
        itemInformation.setTypeId(Long.valueOf(itemType));
        if(StringUtils.isNotBlank(id)){
            itemInformation.setId(Long.valueOf(id));
        }
        iPublicItemInformation.saveItemInformation(itemInformation);
        AjaxSupport.sendSuccessText("", "操作成功!");
    }
}
