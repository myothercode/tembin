package com.iteminformation.controller;

import com.base.database.publicd.model.*;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.ItemInformationQuery;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.publicd.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
    @Autowired
    private IPublicItemPictureaddrAndAttr iPublicItemPictureaddrAndAttr;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    /*
   *纠纷列表
   */
    @RequestMapping("/itemInformationList.do")
    public ModelAndView itemInformationList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        List<PublicUserConfig> types= iPublicUserConfig.selectUserConfigByItemType("itemType");
        List<PublicUserConfig> remarks=iPublicUserConfig.selectUserConfigByItemType("remark");
        modelMap.put("types",types);
        modelMap.put("remarks",remarks);
        return forword("/itemInformation/itemInformation",modelMap);
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
        if("all".equals(remark)||!StringUtils.isNotBlank(remark)){
            remark=null;
        }
        if("all".equals(information)||!StringUtils.isNotBlank(information)){
            information=null;
        }
        if("all".equals(itemType)||!StringUtils.isNotBlank(itemType)){
            itemType=null;
        }
        if(!StringUtils.isNotBlank(content)){
            content=null;
        }
        Map m = new HashMap();
        SessionVO sessionVO=SessionCacheSupport.getSessionVO();
        m.put("remark",remark);
        m.put("information",information);
        m.put("itemType",itemType);
        m.put("content",content);
        if(sessionVO!=null){
            m.put("userID",sessionVO.getId());
        }else{
            m.put("userID",null);
        }
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<ItemInformationQuery> lists=iPublicItemInformation.selectItemInformation(m,page);
        jsonBean.setList(lists);
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
        List<PublicItemPictureaddrAndAttr> pictures=null;
        List<PublicItemPictureaddrAndAttr> attrs=null;
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
            SessionVO c= SessionCacheSupport.getSessionVO();
            pictures=iPublicItemPictureaddrAndAttr.selectPictureaddrAndAttrByInformationId(itemInformation.getId(),"picture",c.getId());
            attrs=iPublicItemPictureaddrAndAttr.selectPictureaddrAndAttrByInformationId(itemInformation.getId(),"attr",c.getId());
        }
        List<PublicUserConfig> types=iPublicUserConfig.selectUserConfigByItemType("itemType");
        modelMap.put("types",types);
        modelMap.put("itemInformation",itemInformation);
        modelMap.put("inventory",inventory);
        modelMap.put("custom",custom);
        modelMap.put("supplier",supplier);
        modelMap.put("pictures",pictures);
        modelMap.put("attrs",attrs);
        return forword("/itemInformation/addItemInformation",modelMap);
    }
    /*
     *添加供应商
     */
    @RequestMapping("/addDiscription.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addDiscription(HttpServletRequest request,HttpServletResponse response,
                                           @ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws UnsupportedEncodingException {
        String id=request.getParameter("id");
        PublicItemInformation itemInformation=new PublicItemInformation();
        if(StringUtils.isNotBlank(id)){
            itemInformation=iPublicItemInformation.selectItemInformationByid(Long.valueOf(id));
        }
        modelMap.put("itemInformation",itemInformation);
        return forword("/itemInformation/addDiscription",modelMap);
    }
    /*
    *初始化添加商品标签界面
    */
    @RequestMapping("/addRemark.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addRemark(HttpServletRequest request,HttpServletResponse response,
                                           @ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        String id=request.getParameter("id");
        List<PublicUserConfig> parents=iPublicUserConfig.selectUserConfigByItemType("remark");
        modelMap.put("id",id);
        modelMap.put("parents",parents);
        return forword("/itemInformation/addRemark",modelMap);
    }
    /*
     *删除产品信息
     */
   @RequestMapping("/ajax/removeItemInformation.do")
   @AvoidDuplicateSubmission(needRemoveToken = true)
   @ResponseBody
   public void removeItemInformation(HttpServletRequest request) throws Exception {
       int i=0;
       while(i>=0){
           String id=request.getParameter("id["+i+"]");
           if(!StringUtils.isNotBlank(id)&&i==0){
               i=-2;
           }else if(StringUtils.isNotBlank(id)){
               PublicItemInformation itemInformation= iPublicItemInformation.selectItemInformationByid(Long.valueOf(id));
               if(itemInformation!=null&&itemInformation.getInventoryId()!=null){
                   iPublicItemInventory.deleteItemInventory(itemInformation.getInventoryId());
               }
               if(itemInformation!=null&&itemInformation.getSupplierId()!=null){
                   iPublicItemSupplier.deleteItemSupplier(itemInformation.getSupplierId());
               }
               if(itemInformation!=null&&itemInformation.getCustomId()!=null){
                   iPublicItemCustom.deleteItemCustom(itemInformation.getCustomId());
               }
               iPublicItemInformation.deleteItemInformation(Long.valueOf(id));
               i++;
           }else{
               i=-1;
           }
       }
       if(i==-2){
           AjaxSupport.sendFailText("fail","商品不存在");
       }else{
           AjaxSupport.sendSuccessText("", "删除成功!");
       }

   }
    /*
    *导出产品信息
    */
    @RequestMapping("/exportItemInformation1.do")
    public void  exportItemInformation1(HttpServletRequest request,HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        int i=0;
        List<PublicItemInformation> list=new ArrayList<PublicItemInformation>();
        String outputFile1= request.getSession().getServletContext().getRealPath("/");
        String outputFile=outputFile1+"download\\itemInformation.xls";
        while(i>=0){
            String id=request.getParameter("id["+i+"]");
            if(!StringUtils.isNotBlank(id)&&i==0) {
                i = -2;
            }else if(StringUtils.isNotBlank(id)){
                PublicItemInformation itemInformation= new PublicItemInformation();
                itemInformation =iPublicItemInformation.selectItemInformationByid(Long.valueOf(id));
                list.add(itemInformation);
                i++;
            }else{
                i=-1;
            }
        }
        if(i!=-2) {
            response.setHeader("Content-Disposition","attachment;filename=itemInformation.xls");// 组装附件名称和格式
            ServletOutputStream outputStream = response.getOutputStream();
            iPublicItemInformation.exportItemInformation(list, outputFile,outputStream);
        }
    }
    /*
   *导入商品标签界面初始化
   */
    @RequestMapping("/importItemInformation.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView importItemInformation(HttpServletRequest request,HttpServletResponse response,
                                  @ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        modelMap.put("flag","false");
        return forword("/itemInformation/importItemInformation",modelMap);
    }
    /*
   *导入
   */
    @RequestMapping("/ajax/importInformation.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView importInformation(@RequestParam(value = "file", required = false)MultipartFile file,HttpServletRequest request,ModelMap modelMap) throws Exception {
        String fileName ="upload.xls";
        String path=request.getSession().getServletContext().getRealPath("/")+"upload";
        File f=new File(path,fileName);
        String fileName1 = file.getOriginalFilename();
        if(!f.exists()){
            f.mkdirs();
        }
        file.transferTo(f);
        List<PublicItemInformation> list= iPublicItemInformation.importItemInformation(f);
        for(PublicItemInformation itemInformation:list){
            iPublicItemInformation.saveItemInformation(itemInformation);
        }
        modelMap.put("flag","true");
        return forword("/itemInformation/importItemInformation",modelMap);
    }
    /*
    *保存标签
    */
    @RequestMapping("/ajax/saveremark.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveremark(HttpServletRequest request) throws Exception {
        String remark=request.getParameter("remark");
        String id=request.getParameter("id");
        String parentid=request.getParameter("parentid");
        if(StringUtils.isNotBlank(id)){
            PublicUserConfig config=new PublicUserConfig();
            config.setConfigType("remark");
            config.setConfigName(remark);
            if(!"0".equals(parentid)){
                config.setItemParentId(parentid);
                PublicUserConfig c=iPublicUserConfig.selectUserConfigById(Long.valueOf(parentid));
                String level=c.getItemLevel();
                config.setItemLevel((Integer.valueOf(level)+1)+"");
            }else{
                config.setItemLevel("1");
            }
            iPublicUserConfig.saveUserConfig(config);
            DataDictionarySupport.removePublicUserConfig(config.getUserId());
            PublicItemInformation itemInformation= iPublicItemInformation.selectItemInformationByid(Long.valueOf(id));
            itemInformation.setRemarkId(config.getId());
            iPublicItemInformation.saveItemInformation(itemInformation);
            AjaxSupport.sendSuccessText("", "添加成功!");
        }

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
        String discription=request.getParameter("discription");
        List<String> pictures=new ArrayList<String>();
        List<String> attrs=new ArrayList<String>();
        List<String> attrNames=new ArrayList<String>();
        int i=0;
        while(i>=0){

            String picture=request.getParameter("Picture[" + i + "]");
            if(StringUtils.isNotBlank(picture)){
                pictures.add(picture);
                i++;
            }else{
                i=-1;
            }
        }
        i=0;
        while(i>=0){
            String attr=request.getParameter("attrValue["+i+"]");
            String attrName=request.getParameter("attrName["+i+"]");
            if(StringUtils.isNotBlank(attr)){
                attrs.add(attr);
                attrNames.add(attrName);
                i++;
            }else{
                i=-1;
            }
        }
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
                inventory.setLength(Double.valueOf(length));
            }
            if (StringUtils.isNotBlank(width)) {
                inventory.setWidth(Double.valueOf(width));
            }
            if (StringUtils.isNotBlank(warning)) {
                inventory.setWarningnumber(Integer.valueOf(warning));
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
        if(StringUtils.isNotBlank(discription)){
            itemInformation.setDescription(discription);
        }
        iPublicItemInformation.saveItemInformation(itemInformation);
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<PublicItemPictureaddrAndAttr> pictureaddrAnds=iPublicItemPictureaddrAndAttr.selectPictureaddrAndAttrByInformationId(itemInformation.getId(),"picture",c.getId()) ;
        List<PublicItemPictureaddrAndAttr> AndAttrs=iPublicItemPictureaddrAndAttr.selectPictureaddrAndAttrByInformationId(itemInformation.getId(),"attr",c.getId()) ;
        for(PublicItemPictureaddrAndAttr p:pictureaddrAnds){
            iPublicItemPictureaddrAndAttr.deletePublicItemPictureaddrAndAttr(p);
        }
        for(PublicItemPictureaddrAndAttr p:AndAttrs){
            iPublicItemPictureaddrAndAttr.deletePublicItemPictureaddrAndAttr(p);
        }
        for(int j=0;j<pictures.size();j++){
            PublicItemPictureaddrAndAttr picture=new PublicItemPictureaddrAndAttr();
            picture.setIteminformationId(itemInformation.getId());
            picture.setAttrname("picture");
            picture.setAttrvalue(pictures.get(j));
            picture.setAttrtype("picture");
            iPublicItemPictureaddrAndAttr.saveItemPictureaddrAndAttr(picture);
        }
        for(int j=0;j<attrs.size();j++){
            String attr1=attrs.get(j);
            String attrName1=attrNames.get(j);
            PublicItemPictureaddrAndAttr attr=new PublicItemPictureaddrAndAttr();
            attr.setAttrname(attrName1);
            attr.setAttrvalue(attr1);
            attr.setIteminformationId(itemInformation.getId());
            attr.setAttrtype("attr");
            iPublicItemPictureaddrAndAttr.saveItemPictureaddrAndAttr(attr);
        }
        AjaxSupport.sendSuccessText("", "操作成功!");
    }

    /**
     * 得到产品图片信息
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping("/ajax/getPicList.do")
    @ResponseBody
    public void getPicList(ModelMap modelMap,HttpServletRequest request){
        String informationid = request.getParameter("informationid");
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<PublicItemPictureaddrAndAttr> lippa = this.iPublicItemPictureaddrAndAttr.selectPictureaddrAndAttrByInformationId(Long.parseLong(informationid),"picture",c.getId());

        AjaxSupport.sendSuccessText("",lippa);
    }
}
