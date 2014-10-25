package com.listingitem.controller;

import com.base.database.customtrading.mapper.ListingDataTaskQueryMapper;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.task.model.ListingDataTask;
import com.base.database.trading.model.*;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.ListingDataAmendQuery;
import com.base.domains.querypojos.TablePriceQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DateUtils;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.PojoXmlUtil;
import com.base.utils.xmlutils.SamplePaseXml;
import com.base.xmlpojo.trading.addproduct.Item;
import com.base.xmlpojo.trading.addproduct.RequesterCredentials;
import com.base.xmlpojo.trading.addproduct.ReviseItemRequest;
import com.base.xmlpojo.trading.addproduct.ShippingDetails;
import com.base.xmlpojo.trading.addproduct.attrclass.StartPrice;
import com.common.base.utils.ajax.AjaxResponse;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.sitemessage.service.SiteMessageService;
import com.sitemessage.service.SiteMessageStatic;
import com.task.service.IListingDataTask;
import com.trading.service.*;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrtor on 2014/8/18.
 */
@Controller
public class ListingItemController extends BaseAction {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ITradingDataDictionary iTradingDataDictionary;
    @Value("${EBAY.API.URL}")
    private String apiUrl;
    @Autowired
    private ITradingItem iTradingItem;
    @Autowired
    private ITradingListingData iTradingListingData;
    @Autowired
    private IUsercontrollerEbayAccount iUsercontrollerEbayAccount;
    @Autowired
    public ITradingListingAmend iTradingListingAmend;
    @Autowired
    public ITradingTablePrice iTradingTablePrice;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    @Autowired
    private IListingDataTask iListingDataTask;
    @Autowired
    private ListingDataTaskQueryMapper listingDataTaskQueryMapper;

    @RequestMapping("/getListingItemList.do")
    public ModelAndView getListingItemList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        /*String colStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<GetSellerListRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
            "<RequesterCredentials>\n" +
            "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**vVcRVA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**Z2MB0OtmO4JsPFBZPcjclippjnZG4bwpcpXYRXDdc6wEppv5m/WiCvsjyTKWoVXCMxQl2se3U6Vn93oBL6zg8EcR3GCXCC3ZbTpEQ3lBX8avBrME9VHo0RcfcE7oLVtnBAuSffy3Dk5ICUNyU7g57/rHw8d5DnO3JeitpQcTLKAInt+sEZslri3wa4Mx0xgyFW5OF3w8mNK8ib8+57PTHcApnp8xRTAlIVuwW3F/fGbSFVReS07/MulzlFXBoW/ZPLq+L2aLFpn5s+IB5/gB0HoDo5uGzRnALmXxUz8BuwJMrUE29830W7xVSEaYSYsOcJyue6PjJKyZt0rXf8TNHusXCHX240dWllrjMVxS7pEHgKb/FKfd/79PH3rXTFmuexesXS6H1lRmHBBE1iknFwtzzS+UeN22Rd6W+hjSjuOHB33o2gMS5cOdVXHuHyOQ6VJU3bJL/eNDgyB+wz3HhZmz6sF+lmLIRKP82H1QXdlwdGdpVhAhyqnE4FH4qTgPBMxv6c4jRL5BRuyUZDLeJI1WXmaZ4pNMss+MiME7Qu+7bP7S2TZhmValbfW/FvqSrxR9LlHji7iQSsz2m56x5TLjOtkFWjRxmB6C1wzBVtzdILzbvmA/1+9RlMevalW6bg22irusiv7iuD/AnC9pZ0Sju2XK/7WpjVW4/lZyBmRbqHQJPbU/5MU3xrM8pTV8rZmPfQrRh2araaWGIBE5IW3gsTrETpRUQybXd/a107ee61GwXEUqVat1EfznFpIs</eBayAuthToken>\n" +
            "</RequesterCredentials>\n" +
            "<Pagination ComplexType=\"PaginationType\">\n" +
            "\t<EntriesPerPage>10</EntriesPerPage>\n" +
            "\t<PageNumber>1</PageNumber>\n" +
            "</Pagination>\n" +
            "<StartTimeFrom>2014-07-18T16:15:12.000Z</StartTimeFrom>\n" +
            "<StartTimeTo>2014-08-18T16:15:12.000Z</StartTimeTo>\n" +
            "<UserID>testuser_sandpoint</UserID>\n" +
            "<GranularityLevel>Coarse</GranularityLevel>\n" +
            "<DetailLevel>ItemReturnDescription</DetailLevel>\n" +
            "</GetSellerListRequest>​";

        List<Item> li = SamplePaseXml.getItemElememt(this.cosPostXml(colStr,APINameStatic.ListingItemList));
        modelMap.put("li",li);*/
        String flag=request.getParameter("flag");
        if(flag!=null&&!"".equals(flag)){
            modelMap.put("flag",flag);
        }
        String county = request.getParameter("county");
        if(county!=null&&!"".equals(county)){
            modelMap.put("county",county);
        }
        String listingtype = request.getParameter("listingtype");
        if(listingtype!=null&&!"".equals(listingtype)){
            modelMap.put("listingtype",listingtype);
        }
        String ebayaccount = request.getParameter("ebayaccount");
        if(ebayaccount!=null&&!"".equals(ebayaccount)){
            modelMap.put("ebayaccount",ebayaccount);
        }
        String selectType = request.getParameter("selectType");
        if(selectType!=null&&!"".equals(selectType)){
            modelMap.put("selectType",selectType);
        }
        String selectValue = request.getParameter("selectValue");
        if(selectValue!=null&&!"".equals(selectValue)){
            modelMap.put("selectValue",selectValue);
        }
        String folderid = request.getParameter("folderid");
        if(folderid!=null&&!"".equals(folderid)){
            modelMap.put("folderid",folderid);
        }
        return forword("listingitem/listingitemList",modelMap);
    }

    @RequestMapping("/getTablePriceList.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView getTablePriceList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {

        return forword("listingitem/getTablePriceList",modelMap);
    }

    /**
     * 导入模板数据保存
     * @param response
     * @throws Exception
     */
    @RequestMapping("/importTemplateSave.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public void importTemplateSave(@RequestParam("templatename")MultipartFile[] multipartFiles,HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        if(ObjectUtils.isLogicalNull(multipartFiles)){
            AjaxResponse.sendText(response,"nofile");
            return;
        }
        MultipartFile multipartFile = multipartFiles[0];
        InputStream input = multipartFile.getInputStream();
        HSSFWorkbook workbook = new HSSFWorkbook(input);
        HSSFSheet sheet = workbook.getSheetAt(0);

        SessionVO c= SessionCacheSupport.getSessionVO();
        List<TradingTablePrice> littp = new ArrayList<TradingTablePrice>();
        for(int i=1;i<=sheet.getLastRowNum();i++){
            HSSFRow row = sheet.getRow(i);
            double price=row.getCell(2).getNumericCellValue();
            Asserts.assertTrue(NumberUtils.isNumber(price + ""), "在" + (i + 1) + "行第3列，用户输入的价格不是数字，请查看修改！");
            TradingTablePrice ttp = new TradingTablePrice();
            ttp.setSku(row.getCell(0).getStringCellValue());
            ttp.setEbayAccount(row.getCell(1).getStringCellValue());
            ttp.setPrice(price);
            ttp.setCheckFlag("0");
            ttp.setCreateUser(c.getId());
            ttp.setCreateTime(new Date());
            littp.add(ttp);
        }

        this.iTradingTablePrice.saveTablePriceList(littp);

        AjaxResponse.sendText(response, "success");
    }
    /**
     * 导入模板页面跳转
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/importTemplate.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView importTemplate(HttpServletRequest request, HttpServletResponse response, @ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        return forword("listingitem/importTemplate",modelMap);
    }
    /*
   *下载模板downloadTemplate
   */
    @RequestMapping("/downloadTemplate.do")
    public void  downloadTemplate(HttpServletRequest request,HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        String outputFile1= request.getSession().getServletContext().getRealPath("/");
        String outputFile=outputFile1+"template\\template.xls";
        response.setHeader("Content-Disposition","attachment;filename=template.xls");// 组装附件名称和格式
        InputStream fis = new BufferedInputStream(new FileInputStream(outputFile));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
    }

    @RequestMapping("/addTablePrice.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addTablePrice(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        modelMap.put("ebayList",ebayList);
        return forword("listingitem/addTablePrice",modelMap);
    }

    @RequestMapping("/editTablePrice.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editTablePrice(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        modelMap.put("ebayList",ebayList);
        TradingTablePrice ttp  = this.iTradingTablePrice.selectById(Long.parseLong(request.getParameter("id")));
        modelMap.put("ttp",ttp);
        String type = request.getParameter("type");
        if(type!=null&&!"".equals(type)){
            modelMap.put("type",type);
        }
        return forword("listingitem/addTablePrice",modelMap);
    }

    /**
     * 失败日志继续处理
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/continueWork.do")
    @ResponseBody
    public void continueWork(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<TradingDataDictionary> lisite = DataDictionarySupport.getTradingDataDictionaryByType("site");
        String id = request.getParameter("id");
        String endid = request.getParameter("endid");
        TradingListingData tldata = this.iTradingListingData.selectById(Long.parseLong(id));
        TradingListingAmendWithBLOBs tlend = this.iTradingListingAmend.selectById(Long.parseLong(endid));

        AddApiTask addApiTask = new AddApiTask();
        UsercontrollerDevAccountExtend dt = userInfoService.getDevByOrder(new HashMap());
        dt.setApiCallName(APINameStatic.ReviseItem);
        String siteid="";
        for(TradingDataDictionary tdd : lisite){
            if(tldata.getSite().equals(tdd.getValue())){
                siteid=tdd.getName1();
                break;
            }
        }
        dt.setApiSiteid(siteid);
        Map<String, String> resMap = addApiTask.exec(dt, tlend.getCosxml(), apiUrl);
        String res = resMap.get("message");
        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");

        tlend.setCreateTime(new Date());
        tlend.setCreateUser(c.getId());
        System.out.println(":::::::::::"+res);
        if("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)){
            tlend.setId(null);
            tlend.setIsFlag("1");
            this.iTradingListingAmend.saveListingAmend(tlend);
            AjaxSupport.sendSuccessText("message", "操作成功！");
        }else{
            String resStr = "";
            if(res!=null){
                resStr = SamplePaseXml.getSpecifyElementTextAllInOne(res,"Errors","LongMessage");
            }else{
                resStr = "请求失败！";
            }
            this.saveSystemLog(resStr,"继续修改报错",SiteMessageStatic.LISTING_DATA_UPDATE);
            tlend.setId(null);
            tlend.setIsFlag("0");
            this.iTradingListingAmend.saveListingAmend(tlend);
            AjaxSupport.sendFailText("fail","操作失败！");
        }
    }

    public void saveSystemLog(String context,String title,String type){
        SiteMessageService siteMessageService= (SiteMessageService) ApplicationContextUtil.getBean(SiteMessageService.class);
        TaskMessageVO taskMessageVO=new TaskMessageVO();
        taskMessageVO.setMessageContext(context);
        taskMessageVO.setMessageTitle(title);
        taskMessageVO.setMessageType(type);
        taskMessageVO.setMessageFrom("system");
        SessionVO sessionVO=SessionCacheSupport.getSessionVO();
        taskMessageVO.setMessageTo(sessionVO.getId());
        taskMessageVO.setObjClass(null);
        siteMessageService.addSiteMessage(taskMessageVO);
    }
    /**
     * 保存数据
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/delTablePrice.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void delTablePrice(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id = request.getParameter("id");
        TradingTablePrice ttp  = this.iTradingTablePrice.selectById(Long.parseLong(request.getParameter("id")));
        ttp.setCheckFlag("1");
        this.iTradingTablePrice.saveTablePrice(ttp);
        AjaxSupport.sendSuccessText("","操作成功!");
    }

    /**
     * 执行表格调价
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/runPrice.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void runPrice(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UsercontrollerEbayAccount> ebay = this.iUsercontrollerEbayAccount.selectUsercontrollerEbayAccountByUserId(c.getId());
        String ebayAccount = request.getParameter("ebayAccount");
        String token = "";
        String currencyID = "";
        List<TradingDataDictionary> lisite = DataDictionarySupport.getTradingDataDictionaryByType("site");
        String siteid = "0";

        String price = request.getParameter("price");
        String sku = request.getParameter("sku");
        String ebayaccount = request.getParameter("ebayaccount");

        String [] prices = price.split(",");
        String [] skus = sku.split(",");
        String [] ebayaccounts = ebayaccount.split(",");
        String xml = "";
        ReviseItemRequest rir = new ReviseItemRequest();
        rir.setXmlns("urn:ebay:apis:eBLBaseComponents");
        rir.setErrorLanguage("en_US");
        rir.setWarningLevel("High");
        AddApiTask addApiTask = new AddApiTask();
        UsercontrollerDevAccountExtend dt = userInfoService.getDevByOrder(new HashMap());
        dt.setApiCallName(APINameStatic.ReviseItem);
        for(int i=0;i<skus.length;i++){
            List<TradingListingData> litld = this.iTradingListingData.selectByList(skus[i],ebayaccounts[i]);
            for(TradingListingData tld:litld){
                //取得当前数据的token
                for(UsercontrollerEbayAccount eb : ebay){
                    if(eb.getEbayAccount()!=null&&tld.getEbayAccount().equals(eb.getEbayAccount())){
                        token = this.iUsercontrollerEbayAccount.selectById(eb.getId()).getEbayToken();
                        break;
                    }else{
                        if(tld.getEbayAccount().equals(eb.getEbayName())){
                            token = this.iUsercontrollerEbayAccount.selectById(eb.getId()).getEbayToken();
                            break;
                        }
                    }
                }
                //取得站点，及货币ＩＤ
                for(TradingDataDictionary tdd : lisite){
                    if(tld.getSite().equals(tdd.getValue())){
                        siteid=tdd.getName1();
                        currencyID = tdd.getValue1();
                        break;
                    }
                }

                xml="";
                dt.setApiSiteid(siteid);
                Item ite = new Item();
                ite.setItemID(tld.getItemId());
                StartPrice sp = new StartPrice();
                sp.setValue(Double.parseDouble(prices[i]));
                ite.setStartPrice(sp);

                RequesterCredentials rc = new RequesterCredentials();
                rc.seteBayAuthToken(token);
                rir.setRequesterCredentials(rc);
                rir.setItem(ite);
                xml = PojoXmlUtil.pojoToXml(rir);
                xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+xml;
                System.out.println(xml);

                Map<String, String> resMap = addApiTask.exec(dt, xml, apiUrl);
                String res = resMap.get("message");
                String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                TradingListingAmendWithBLOBs tla = new TradingListingAmendWithBLOBs();
                tla.setItem(Long.parseLong(tld.getItemId()));
                tla.setParentId(tld.getId());
                tla.setAmendType("EndItem");
                tla.setContent("商品调价：从"+tld.getPrice()+"调整为"+prices[i] );
                tla.setCreateUser(c.getId());
                tla.setCreateTime(new Date());

                if("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)){
                    tla.setIsFlag("1");
                    this.iTradingListingAmend.saveListingAmend(tla);
                    AjaxSupport.sendSuccessText("message", "操作成功！");
                }else{
                    String resStr = "";
                    if(res!=null){
                        resStr = SamplePaseXml.getSpecifyElementTextAllInOne(res,"Errors","LongMessage");
                    }else{
                        resStr = "请求失败！";
                    }
                    this.saveSystemLog(resStr,"表格调价报错",SiteMessageStatic.LISTING_DATA_UPDATE);
                    tla.setIsFlag("0");
                    this.iTradingListingAmend.saveListingAmend(tla);
                    AjaxSupport.sendFailText("fail","操作失败！");
                }
            }
        }
        AjaxSupport.sendSuccessText("","操作成功!");
    }

    /**
     * 获取用户自定义的文件夹
     * @param request
     * @param modelMap
     * @param commonParmVO
     * @throws Exception
     */
    @RequestMapping("/ajax/selfFolder.do")
    @ResponseBody
    public void selfFolder(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        DataDictionarySupport.removePublicUserConfig(c.getId());
        String folderType = request.getParameter("folderType");
        List<PublicUserConfig> lipuc = DataDictionarySupport.getPublicUserConfigByType(folderType,c.getId());
        List<PublicUserConfig> li = new ArrayList<PublicUserConfig>();
        if(lipuc!=null&&lipuc.size()>1) {
            for (PublicUserConfig puc : lipuc) {
                if (puc.getConfigValue().equals("true")) {
                    li.add(puc);
                }
            }
            AjaxSupport.sendSuccessText("", li);
        }else{
            AjaxSupport.sendSuccessText("", null);
        }
    }


    /**
     * 用户点击同步时，查询
     * @param request
     * @param modelMap
     * @param commonParmVO
     * @throws Exception
     */
    @RequestMapping("/ajax/myEbayAccount.do")
    @ResponseBody
    public void myEbayAccount(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UsercontrollerEbayAccount> liuserebay = this.iUsercontrollerEbayAccount.selectUsercontrollerEbayAccountByUserId(c.getId());
        List<Map> lim = new ArrayList<Map>();
        for(UsercontrollerEbayAccount uea:liuserebay){
            Map mpar = new HashMap();
            mpar.put("ebayAccount",uea.getEbayAccount());

            Map m = new HashMap();
            m.put("ebayAccount",uea.getEbayAccount());
            m.put("ebayName",uea.getEbayAccount());
            m.put("maxDate",this.listingDataTaskQueryMapper.selectByMaxCreateDate(mpar).getCreateDate());
            lim.add(m);
        }
        AjaxSupport.sendSuccessText("", lim);
    }
    /**
     * 刊登商品
     * @param request
     * @throws Exception
     */
    @RequestMapping("/ajax/saveTablePrice.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveTablePrice(HttpServletRequest request,Item item,TradingItem tradingItem,Date timerListing) throws Exception {
        String sku = request.getParameter("sku");
        String price = request.getParameter("price");
        String [] ebayAccounts = request.getParameterValues("ebayAccounts");
        String id = request.getParameter("id");
        SessionVO c= SessionCacheSupport.getSessionVO();

        if(ebayAccounts!=null&&ebayAccounts.length>0){
            for(String ebayaccount:ebayAccounts){
                TradingTablePrice ttp  = new TradingTablePrice();
                if(id!=null&&!"".equals(id)){
                    ttp.setId(Long.parseLong(id));
                }
                ttp.setEbayAccount(ebayaccount);
                ttp.setPrice(Double.parseDouble(price));
                ttp.setSku(sku);
                ttp.setCreateTime(new Date());
                ttp.setCreateUser(c.getId());
                ttp.setCheckFlag("0");
                this.iTradingTablePrice.saveTablePrice(ttp);
            }
        }
        AjaxSupport.sendSuccessText("","操作成功!");
    }


    @RequestMapping("/ajax/ajaxTablePriceList.do")
    @ResponseBody
    public void getAjaxTablePriceList(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        Map map = new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        map.put("userid",c.getId());
        map.put("checkflag","0");
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<TablePriceQuery> paypalli = this.iTradingTablePrice.selectByList(map, page);
        jsonBean.setList(paypalli);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
    }

    @RequestMapping("/getListItemDataAmend.do")
    public ModelAndView getListItemDataAmend(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        String flag=request.getParameter("flag");
        if(flag!=null&&!"".equals(flag)){
            modelMap.put("flag",flag);
        }
        String county = request.getParameter("county");
        if(county!=null&&!"".equals(county)){
            modelMap.put("county",county);
        }
        String listingtype = request.getParameter("listingtype");
        if(listingtype!=null&&!"".equals(listingtype)){
            modelMap.put("listingtype",listingtype);
        }
        String ebayaccount = request.getParameter("ebayaccount");
        if(ebayaccount!=null&&!"".equals(ebayaccount)){
            modelMap.put("ebayaccount",ebayaccount);
        }
        String selectType = request.getParameter("selectType");
        if(selectType!=null&&!"".equals(selectType)){
            modelMap.put("selectType",selectType);
        }
        String selectValue = request.getParameter("selectValue");
        if(selectValue!=null&&!"".equals(selectValue)){
            modelMap.put("selectValue",selectValue);
        }
        String amendType = request.getParameter("amendType");
        if(amendType!=null&&!"".equals(amendType)){
            modelMap.put("amendType",amendType);
        }
        String amendFlag = request.getParameter("amendFlag");
        if(amendFlag!=null&&!"".equals(amendFlag)){
            modelMap.put("amendFlag",amendFlag);
        }
        String parentId = request.getParameter("parentId");
        modelMap.put("parentId",parentId);
        return forword("listingitem/listingdataAmend",modelMap);
    }

    /**
     * 移到到文件夹
     * @param request
     * @param response
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/ajax/shiftListingToFolder.do")
    @ResponseBody
    public void shiftListingToFolder(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String idStr = request.getParameter("idStr");
        String [] ids =idStr.split(",");
        String folderid = request.getParameter("folderid");

        List<TradingListingData> litld = new ArrayList<TradingListingData>();
        for(String id: ids){
            TradingListingData tld = this.iTradingListingData.selectById(Long.parseLong(id));
            tld.setFolderId(folderid);
            litld.add(tld);
        }
        if(litld.size()>0) {
            try {
                this.iTradingListingData.saveTradingListingDataList(litld);
                AjaxSupport.sendSuccessText("","操作成功!");
            } catch (Exception e) {
                e.printStackTrace();
                AjaxSupport.sendSuccessText("","操作失败!");
            }
        }else{
            AjaxSupport.sendSuccessText("","操作失败，你未选择商品，或你选择的商品不存在!");
        }

    }
    /**
     * 添加备注
     * @param request
     * @param response
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/ajax/addRemark.do")
    @ResponseBody
    public void addRemark(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id = request.getParameter("id");
        String [] ids = id.split(",");
        String remark = request.getParameter("remark");
        for(int i=0;i<ids.length;i++) {
            TradingListingData tld = this.iTradingListingData.selectById(Long.parseLong(ids[i]));
            if(tld!=null){
                tld.setRemark(remark);
                this.iTradingListingData.updateTradingListingData(tld);
            }
        }
        AjaxSupport.sendSuccessText("","操作成功!");
    }

    @RequestMapping("/ajax/getListItemDataAmend.do")
    @ResponseBody
    public void getListItemDataAmend(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        Map map = new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        map.put("userid",c.getId());
        String flag = request.getParameter("flag");
        if(flag!=null&&!"".equals(flag)){
            map.put("flag",flag);
        }
        String county = request.getParameter("county");
        if(county!=null&&!"".equals(county)){
            map.put("county",county);
        }
        String listingtype = request.getParameter("listingtype");
        if(listingtype!=null&&!"".equals(listingtype)){
            map.put("listingtype",listingtype);
        }
        String ebayaccount = request.getParameter("ebayaccount");
        if(ebayaccount!=null&&!"".equals(ebayaccount)){
            map.put("ebayaccount",ebayaccount);
        }
        String selectType = request.getParameter("selectType");
        if(selectType!=null&&!"".equals(selectType)){
            map.put("selectType",selectType);
        }
        String selectValue = request.getParameter("selectValue");
        if(selectValue!=null&&!"".equals(selectValue)){
            map.put("selectValue",selectValue);
        }
        String amendType = request.getParameter("amendType");
        if(amendType!=null&&!"".equals(amendType)){
            map.put("amendType",amendType);
        }
        String amendFlag = request.getParameter("amendFlag");
        if(amendFlag!=null&&!"".equals(amendFlag)){
            map.put("amendFlag",amendFlag);
        }
        String parentId = request.getParameter("parentId");
        if(parentId!=null&&!"".equals(parentId)){
            map.put("parentId",parentId);
        }
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<ListingDataAmendQuery> paypalli = this.iTradingListingData.selectAmendData(map, page);
        jsonBean.setList(paypalli);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
    }

    @RequestMapping("/ajax/endItem.do")
    @ResponseBody
    public void endItem(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        //List<PublicUserConfig> ebayList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_PAYPAL, c.getId());
        List<UsercontrollerEbayAccount> ebay = this.iUsercontrollerEbayAccount.selectUsercontrollerEbayAccountByUserId(c.getId());
        String ebayAccount = request.getParameter("ebayAccount");
        String token = "";
        List<TradingDataDictionary> lisite = DataDictionarySupport.getTradingDataDictionaryByType("site");
        String siteid = "0";


        String itemidstr = request.getParameter("itemidStr");
        String reason = request.getParameter("reason");
        String [] itemids = itemidstr.split(",");
        UsercontrollerDevAccountExtend dt = userInfoService.getDevByOrder(new HashMap());

        dt.setApiCallName(APINameStatic.EndItem);
        AddApiTask addApiTask = new AddApiTask();
        for(String itemid:itemids){
            TradingListingAmend tlaold = this.iTradingListingAmend.selectByItemID(itemid,"EndItem");
            if(tlaold!=null){
                continue;
            }
            TradingListingData tld = this.iTradingListingData.selectByItemid(itemid);

            for(UsercontrollerEbayAccount eb : ebay){
                if(eb.getEbayAccount()!=null&&tld.getEbayAccount().equals(eb.getEbayAccount())){
                    token = this.iUsercontrollerEbayAccount.selectById(eb.getId()).getEbayToken();
                    break;
                }else{
                    if(tld.getEbayAccount().equals(eb.getEbayName())){
                        token = this.iUsercontrollerEbayAccount.selectById(eb.getId()).getEbayToken();
                        break;
                    }
                }
            }

            for(TradingDataDictionary tdd : lisite){
                if(tld.getSite().equals(tdd.getValue())){
                    siteid=tdd.getName1();
                }
            }
            dt.setApiSiteid(siteid);
            String xml = this.costXml(itemid,reason,token);
            TradingListingAmendWithBLOBs tla = new TradingListingAmendWithBLOBs();
            tla.setItem(Long.parseLong(tld.getItemId()));
            tla.setParentId(tld.getId());
            tla.setAmendType("EndItem");
            tla.setContent("商品下架，下架原因：" + reason);
            tla.setCreateUser(c.getId());
            tla.setCreateTime(new Date());
            tla.setCosxml(xml);
            Map<String, String> resMap = addApiTask.exec(dt, xml, apiUrl);
            String res = resMap.get("message");
            String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
            if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
                tla.setIsFlag("1");
                tld.setIsFlag("1");
                this.iTradingListingData.updateTradingListingData(tld);
            }else{
                String resStr = "";
                if(res!=null){
                    resStr = SamplePaseXml.getSpecifyElementTextAllInOne(res,"Errors","LongMessage");
                }else{
                    resStr = "请求失败！";
                }
                this.saveSystemLog(resStr,"提前结束商品报错",SiteMessageStatic.LISTING_DATA_UPDATE);
                tla.setIsFlag("0");
            }
            this.iTradingListingData.insertTradingListingAmend(tla);
        }
        AjaxSupport.sendSuccessText("","操作成功!");
    }

    public String costXml(String itemid,String reason,String token){
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<EndItemRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>"+token+"</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "<ItemID ComplexType=\"ItemIDType\">"+itemid+"</ItemID>\n" +
                "<EndingReason EnumType=\"EndReasonCodeType\">"+reason+"</EndingReason>\n" +
                "<Version>893</Version>\n" +
                "</EndItemRequest>​";
        return xml;
    }

    @RequestMapping("/listingdataManager.do")
    public ModelAndView listingdataManager(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        //List<PublicUserConfig> ebayList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_PAYPAL, c.getId());
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        modelMap.put("ebayList",ebayList);
        return forword("listingitem/listingdataManager",modelMap);
    }

    /**
     * 快速编辑
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/quickEdit.do")
    public ModelAndView quickEdit(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        String id = request.getParameter("id");
        String listingType = request.getParameter("listingType");
        String [] ids = id.split(",");
        List<TradingListingData> litld = new ArrayList<TradingListingData>();
        for(int i=0;i<ids.length;i++){
            TradingListingData tld = this.iTradingListingData.selectById(Long.parseLong(ids[i]));
            litld.add(tld);
        }
        modelMap.put("litld",litld);
        modelMap.put("listingType",listingType);
        return forword("listingitem/quickEdit",modelMap);
    }
    @RequestMapping("/ajax/savequickData.do")
    @ResponseBody
    public void savequickData(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        SessionVO c = SessionCacheSupport.getSessionVO();
        String [] ids =request.getParameterValues("ids");
        String listingType = request.getParameter("listingType");
        for(int i=0;i<ids.length;i++){
            String price = request.getParameter("price_"+i);
            String quantity = request.getParameter("quantity_"+i);
            String buyitnowprice = request.getParameter("buyitnowprice_"+i);
            String reserveprice = request.getParameter("reserveprice_"+i);
            String title = request.getParameter("title_"+i);
            String subtitle = request.getParameter("subtitle_"+i);
            String sku = request.getParameter("sku_"+i);
            String id = request.getParameter("id_"+i);
            TradingListingData tld = this.iTradingListingData.selectById(Long.parseLong(id));
            Item item = new Item();
            item.setTitle(title);
            item.setSubTitle(subtitle);
            tld.setTitle(title);
            tld.setSubtitle(subtitle);
            TradingListingAmendWithBLOBs tla = new TradingListingAmendWithBLOBs();
            if("2".equals(listingType)){//多属性
                tla.setAmendType("Title");
                tla.setContent("快速编辑：多属性修改title");
            }else if("Chinese".equals(listingType)){//拍卖
                tla.setAmendType("Title");
                tla.setContent("快速编辑：拍卖修改");
                item.setSKU(sku);
                StartPrice sp = new StartPrice();
                sp.setValue(Double.parseDouble(price));
                item.setStartPrice(sp);
                item.setBuyItNowPrice(Double.parseDouble(buyitnowprice));
                item.setReservePrice(Double.parseDouble(reserveprice));
                item.setQuantity(Integer.parseInt(quantity));
                tld.setPrice(Double.parseDouble(price));
                tld.setBuyitnowprice(Double.parseDouble(buyitnowprice));
                tld.setReserveprice(Double.parseDouble(reserveprice));
                tld.setSku(sku);
                tld.setQuantity(Long.parseLong(quantity));
            }else if("FixedPriceItem".equals(listingType)){//固价
                tla.setAmendType("Title");
                tla.setContent("快速编辑：拍卖修改");
                item.setSKU(sku);
                StartPrice sp = new StartPrice();
                sp.setValue(Double.parseDouble(price));
                item.setStartPrice(sp);
                item.setQuantity(Integer.parseInt(quantity));
                tld.setPrice(Double.parseDouble(price));
                tld.setSku(sku);
                tld.setQuantity(Long.parseLong(quantity));
            }
            item.setItemID(tld.getItemId());
            tla.setParentId(tld.getId());
            tla.setItem(Long.parseLong(tld.getItemId()));
            tla.setCreateUser(c.getId());
            tla.setCreateTime(new Date());
            String xml="";
            ReviseItemRequest rir = new ReviseItemRequest();
            rir.setXmlns("urn:ebay:apis:eBLBaseComponents");
            rir.setErrorLanguage("en_US");
            rir.setWarningLevel("High");
            RequesterCredentials rc = new RequesterCredentials();
            String ebayAccount = tld.getEbayAccount();

            List<UsercontrollerEbayAccount> liuea = this.iUsercontrollerEbayAccount.selectUsercontrollerEbayAccountByUserId(c.getId());
            String token = "";
            for(UsercontrollerEbayAccount uea:liuea){
                if(ebayAccount.equals(uea.getEbayName())){
                    token=this.iUsercontrollerEbayAccount.selectById(uea.getId()).getEbayToken();
                }
            }
            rc.seteBayAuthToken(token);
            rir.setRequesterCredentials(rc);
            rir.setItem(item);
            xml = PojoXmlUtil.pojoToXml(rir);
            xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+xml;
            System.out.println(xml);
            tla.setCosxml(xml);
            String returnString = this.cosPostXml(xml,APINameStatic.ReviseItem);
            System.out.println(returnString);
            String ack = SamplePaseXml.getVFromXmlString(returnString,"Ack");
            if("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)){
                tla.setIsFlag("1");
                this.iTradingListingAmend.saveListingAmend(tla);
                this.iTradingListingData.updateTradingListingData(tld);
            }else{
                String resStr = "";
                if(returnString!=null){
                    resStr = SamplePaseXml.getSpecifyElementTextAllInOne(returnString,"Errors","LongMessage");
                }else{
                    resStr = "请求失败！";
                }
                this.saveSystemLog(resStr,"快速修改报错",SiteMessageStatic.LISTING_DATA_UPDATE);
                tla.setIsFlag("0");
                this.iTradingListingAmend.saveListingAmend(tla);
                Document document= DocumentHelper.parseText(returnString);
                Element rootElt = document.getRootElement();
                Element tl = rootElt.element("Errors");
                String longMessage = tl.elementText("LongMessage");
                if(longMessage==null){
                    longMessage = tl.elementText("ShortMessage");
                }
            }
        }
        AjaxSupport.sendSuccessText("message", "后台操作，请查看操作日志！");
    }


        @RequestMapping("/editListingItem.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editListingItem(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        String itemid = request.getParameter("itemid");
        String [] itemidStr = itemid.split(",");
        TradingListingData tldata = this.iTradingListingData.selectByItemid(itemidStr[0]);
        UsercontrollerEbayAccount uea = this.iUsercontrollerEbayAccount.selectByEbayAccount(tldata.getEbayAccount());
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetItemRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>"+uea.getEbayToken()+"</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "<ItemID>"+itemidStr[0]+"</ItemID>\n" +
                "<DetailLevel>ReturnAll</DetailLevel>\n" +
                "</GetItemRequest>";
        String res = this.cosPostXml(xml,APINameStatic.GetItem);
            String ack = SamplePaseXml.getVFromXmlString(res,"Ack");
            if("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)){
                Item item = null;
                if(res!=null){
                    item = SamplePaseXml.getItem(res);
                }
                modelMap.put("item",item);
                modelMap.put("itemidstr",itemid);
                SessionVO c= SessionCacheSupport.getSessionVO();
                List<PublicUserConfig> paypalList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_PAYPAL, c.getId());
                modelMap.put("paypalList",paypalList);

                List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
                modelMap.put("siteList",lidata);

                List<TradingDataDictionary> acceptList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.RETURNS_ACCEPTED_OPTION);
                modelMap.put("acceptList",acceptList);

                List<TradingDataDictionary> withinList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.RETURNS_WITHIN_OPTION);
                modelMap.put("withinList",withinList);

                List<TradingDataDictionary> refundList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.REFUND_OPTION);
                modelMap.put("refundList",refundList);

                List<TradingDataDictionary> costPaidList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.SHIPPING_COST_PAID);
                modelMap.put("costPaidList",costPaidList);

                List<TradingDataDictionary> lidatas = this.iTradingDataDictionary.selectDictionaryByType("country");
                modelMap.put("countryList",lidatas);
                Long siteid = 0L;
                for(TradingDataDictionary tdd : lidata){
                    if(tdd.getValue().equals(item.getSite())){
                        siteid=tdd.getId();
                        break;
                    }
                }
                modelMap.put("siteid",siteid);
                List<TradingDataDictionary> litype = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SHIPPING_TYPE,siteid);
                List<TradingDataDictionary> li1 = new ArrayList<TradingDataDictionary>();
                List<TradingDataDictionary> li2 = new ArrayList<TradingDataDictionary>();
                List<TradingDataDictionary> li3 = new ArrayList<TradingDataDictionary>();
                List<TradingDataDictionary> li4 = new ArrayList<TradingDataDictionary>();
                List<TradingDataDictionary> li5 = new ArrayList<TradingDataDictionary>();
                for(TradingDataDictionary tdd:litype){
                    if(tdd.getName1().equals("Economy services")){
                        li1.add(tdd);
                    }else if(tdd.getName1().equals("Expedited services")){
                        li2.add(tdd);
                    }else if(tdd.getName1().equals("One-day services")){
                        li3.add(tdd);
                    }else if(tdd.getName1().equals("Other services")){
                        li4.add(tdd);
                    }else if(tdd.getName1().equals("Standard services")){
                        li5.add(tdd);
                    }
                }
                modelMap.put("li1",li1);
                modelMap.put("li2",li2);
                modelMap.put("li3",li3);
                modelMap.put("li4",li4);
                modelMap.put("li5",li5);

                List<TradingDataDictionary> liinter = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SHIPPINGINTER_TYPE);
                List<TradingDataDictionary> inter1 = new ArrayList();
                List<TradingDataDictionary> inter2 = new ArrayList();
                for(TradingDataDictionary tdd:liinter){
                    if(tdd.getName1().equals("Expedited services")){
                        inter1.add(tdd);
                    }else if(tdd.getName1().equals("Other services")){
                        inter2.add(tdd);
                    }
                }
                modelMap.put("inter1",inter1);
                modelMap.put("inter2",inter2);

                List<TradingDataDictionary> lipackage = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SHIPPINGPACKAGE);

                modelMap.put("lipackage",lipackage);

                List<PublicUserConfig> ebayList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_EBAYACCOUNT, c.getId());
                modelMap.put("ebayList",ebayList);
            }else{
                Asserts.assertTrue(false,"查询数据报错！");
            }


            return forword("listingitem/editListingItem",modelMap);
    }


    @RequestMapping("/ajax/updateListingData.do")
    @ResponseBody
    public void updateListingData(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        String type = request.getParameter("type");
        String price = request.getParameter("price");
        String itemId = request.getParameter("itemId");
        String ids = request.getParameter("ids");
        TradingListingData tld = this.iTradingListingData.selectById(Long.parseLong(ids));
        Item item = new Item();
        item.setItemID(itemId);
        TradingListingAmendWithBLOBs tla = new TradingListingAmendWithBLOBs();
        if(type.equals("price")){
            StartPrice sp = new StartPrice();
            sp.setValue(Double.parseDouble(price));
            item.setStartPrice(sp);
            tla.setAmendType("StartPrice");
            tla.setContent("将价格从" + tld.getPrice() + "修改为" + item.getStartPrice().getValue());
            tld.setPrice(item.getStartPrice().getValue());
        }else if(type.equals("quantity")){
            tla.setAmendType("Quantity");
            item.setQuantity(Integer.parseInt(price));
            tla.setContent("将数量从" + tld.getQuantity() + "修改为" + item.getQuantity());

            tld.setQuantity(item.getQuantity().longValue());
        }
        tla.setParentId(tld.getId());
        tla.setItem(Long.parseLong(tld.getItemId()));
        tla.setCreateUser(c.getId());
        tla.setCreateTime(new Date());
        String xml="";
        ReviseItemRequest rir = new ReviseItemRequest();
        rir.setXmlns("urn:ebay:apis:eBLBaseComponents");
        rir.setErrorLanguage("en_US");
        rir.setWarningLevel("High");
        RequesterCredentials rc = new RequesterCredentials();
        //生产
        //rc.seteBayAuthToken("AgAAAA**AQAAAA**aAAAAA**W2xvUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlICkCpSGoA6dj6x9nY+seQ**GNABAA**AAMAAA**Vn4yBA7u+ZDMqwb6Sdip+KaomBablhv7dCVnFt5ksAUd7RjjA4ANJ4TQVoIAQ35NZQzalPoKaGzLBFhURJa2xpJPj/BMSb0ihuql4NDVCUOsPFoWMVPIwQdVQ6dZ29DL66dBcuiRgJsTakDttxgK02lfiBgiEP0YCruAhjIKFzZPSivuvkSqKn2HIFKjJq0VDlCvqaBgYkGm26ITKH9dQj/Ql9jK3BHeWA6GSZ+nR9HPIufHLdNpT4axILEd3Lg2X/d34+QoP46rGb4iwO64AzvOXcF//WE4MuJsTQ4d6qgw6DOajpDBL0PNq1n6HItAylImyPRzfvU8hw8neigieh3CtmjzjJ81bY/swlFQdPlV6zZVE99pegMT0DO9Fms5la8W3MSeoHgWdq4i7AR6GBjlh9W9x8z05I91wOx2wNJb0ETcbwl0YbWxs72K49FYF12CZbXQytfJZNLHi+X9/jFgf4TfdrJgagMhUqP9M6Of3R2POF/4+9j/y7s11M6aWw2oxsJ6VAZQKZXtZ5T6/UfP89VA7M1t68R6f6kVr5hoD5glQa2lIw6bIQR4tubYPTAhg5uPCjWifEwYJoV5VuwAk/WHKEvihNHrYGu3c1SMuJlHatLBx7vSNrFsPFWsmP6Z3I6bBRyjSY57KQwxM3SHJvvbYO8etfU+S1gCXuvFMarCCgxv8MhdDUhA/F6A3QE+KjW91xKz8BQ/UJKBS5kOJF13xqSh+j/zoH6EVmRDLvD0uAW7xsSAiMuwT5Kq");
        //测试
        String ebayAccount = tld.getEbayAccount();

        List<UsercontrollerEbayAccount> liuea = this.iUsercontrollerEbayAccount.selectUsercontrollerEbayAccountByUserId(c.getId());
        String token = "";
        for(UsercontrollerEbayAccount uea:liuea){
            if(ebayAccount.equals(uea.getEbayName())){
                token=this.iUsercontrollerEbayAccount.selectById(uea.getId()).getEbayToken();
            }
        }
        rc.seteBayAuthToken(token);
        rir.setRequesterCredentials(rc);
        rir.setItem(item);
        xml = PojoXmlUtil.pojoToXml(rir);
        xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+xml;
        System.out.println(xml);
        tla.setCosxml(xml);
        String returnString = this.cosPostXml(xml,APINameStatic.ReviseItem);
        System.out.println(returnString);
        String ack = SamplePaseXml.getVFromXmlString(returnString,"Ack");
        if("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)){
            tla.setIsFlag("1");
            this.iTradingListingAmend.saveListingAmend(tla);
            this.iTradingListingData.updateTradingListingData(tld);
            AjaxSupport.sendSuccessText("message", "操作成功！");
        }else{
            tla.setIsFlag("0");
            this.iTradingListingAmend.saveListingAmend(tla);
            Document document= DocumentHelper.parseText(returnString);
            Element rootElt = document.getRootElement();
            Element tl = rootElt.element("Errors");
            String longMessage = tl.elementText("LongMessage");
            if(longMessage==null){
                longMessage = tl.elementText("ShortMessage");
            }

            this.saveSystemLog(longMessage,"修改价格数量报错",SiteMessageStatic.LISTING_DATA_UPDATE);
            AjaxSupport.sendFailText("fail",longMessage);
        }

    }
    @RequestMapping("/ajax/ListingItemList.do")
    @ResponseBody
    public void ListingItemList(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        Map map = new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        map.put("userid",c.getId());
        String flag = request.getParameter("flag");
        if(flag!=null&&!"".equals(flag)){
            map.put("flag",flag);
        }
        String county = request.getParameter("county");
        if(county!=null&&!"".equals(county)){
            map.put("county",county);
        }
        String listingtype = request.getParameter("listingtype");
        if(listingtype!=null&&!"".equals(listingtype)){
            map.put("listingtype",listingtype);
        }
        String ebayaccount = request.getParameter("ebayaccount");
        if(ebayaccount!=null&&!"".equals(ebayaccount)){
            map.put("ebayaccount",ebayaccount);
        }
        String selectType = request.getParameter("selectType");
        if(selectType!=null&&!"".equals(selectType)){
            map.put("selectType",selectType);
        }
        String selectValue = request.getParameter("selectValue");
        if(selectValue!=null&&!"".equals(selectValue)){
            map.put("selectValue",selectValue);
        }
        String folderid = request.getParameter("folderid");
        if(folderid!=null&&!"".equals(folderid)){
            map.put("folderid",folderid);
        }
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<TradingListingData> paypalli = this.iTradingListingData.selectData(map,page);
        jsonBean.setList(paypalli);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);

        /**分页组装*//*
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        jsonBean.setPageCount(jsonBean.getPageCount());
        jsonBean.setPageNum(jsonBean.getPageNum());


        String colStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetSellerListRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**vVcRVA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**Z2MB0OtmO4JsPFBZPcjclippjnZG4bwpcpXYRXDdc6wEppv5m/WiCvsjyTKWoVXCMxQl2se3U6Vn93oBL6zg8EcR3GCXCC3ZbTpEQ3lBX8avBrME9VHo0RcfcE7oLVtnBAuSffy3Dk5ICUNyU7g57/rHw8d5DnO3JeitpQcTLKAInt+sEZslri3wa4Mx0xgyFW5OF3w8mNK8ib8+57PTHcApnp8xRTAlIVuwW3F/fGbSFVReS07/MulzlFXBoW/ZPLq+L2aLFpn5s+IB5/gB0HoDo5uGzRnALmXxUz8BuwJMrUE29830W7xVSEaYSYsOcJyue6PjJKyZt0rXf8TNHusXCHX240dWllrjMVxS7pEHgKb/FKfd/79PH3rXTFmuexesXS6H1lRmHBBE1iknFwtzzS+UeN22Rd6W+hjSjuOHB33o2gMS5cOdVXHuHyOQ6VJU3bJL/eNDgyB+wz3HhZmz6sF+lmLIRKP82H1QXdlwdGdpVhAhyqnE4FH4qTgPBMxv6c4jRL5BRuyUZDLeJI1WXmaZ4pNMss+MiME7Qu+7bP7S2TZhmValbfW/FvqSrxR9LlHji7iQSsz2m56x5TLjOtkFWjRxmB6C1wzBVtzdILzbvmA/1+9RlMevalW6bg22irusiv7iuD/AnC9pZ0Sju2XK/7WpjVW4/lZyBmRbqHQJPbU/5MU3xrM8pTV8rZmPfQrRh2araaWGIBE5IW3gsTrETpRUQybXd/a107ee61GwXEUqVat1EfznFpIs</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "<Pagination ComplexType=\"PaginationType\">\n" +
                "\t<EntriesPerPage>"+jsonBean.getPageCount()+"</EntriesPerPage>\n" +
                "\t<PageNumber>"+jsonBean.getPageNum()+"</PageNumber>\n" +
                "</Pagination>\n" +
                "<StartTimeFrom>2014-06-06T16:15:12.000Z</StartTimeFrom>\n" +
                "<StartTimeTo>2014-09-10T18:15:12.000Z</StartTimeTo>\n" +
                "<UserID>testuser_sandpoint</UserID>\n" +
                "<GranularityLevel>Coarse</GranularityLevel>\n" +
                "<DetailLevel>ItemReturnDescription</DetailLevel>\n" +
                "</GetSellerListRequest>​";
        String res = this.cosPostXml(colStr,APINameStatic.ListingItemList);
        Document document= DocumentHelper.parseText(res);
        Element rootElt = document.getRootElement();
        Element totalElt = rootElt.element("PaginationResult");
        String totalCount = totalElt.elementText("TotalNumberOfEntries");
        jsonBean.setTotal(Integer.parseInt(totalCount));
        List<Item> li = SamplePaseXml.getItemElememt(res);
        jsonBean.setList(li);
        AjaxSupport.sendSuccessText("", jsonBean);*/
    }


    public String cosPostXml(String colStr,String month) throws Exception {
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(1L);
        d.setApiSiteid("0");
        d.setApiCallName(month);
        AddApiTask addApiTask = new AddApiTask();
        Map<String, String> resMap= addApiTask.exec(d, colStr, apiUrl);
        String res=resMap.get("message");
        String ack = SamplePaseXml.getVFromXmlString(res,"Ack");
        if(ack.equals("Success")||"Warning".equalsIgnoreCase(ack)){
            return res;
        }else{
            return res;
        }

    }

    /**
     * 在线修改数据
     * @param request
     * @throws Exception
     */
    @RequestMapping("/saveListingItem.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveListingItem(HttpServletRequest request,Item item,TradingItem tradingItem) throws Exception {
        String [] selectType = request.getParameterValues("selectType");
        String isUpdateFlag = request.getParameter("isUpdateFlag");
        String listingType = request.getParameter("listingType");
        String itemidStr = request.getParameter("ItemID");
        String [] itemid = itemidStr.split(",");
        for(int i=0;i<itemid.length;i++){
            item.setItemID(itemid[i]);
            if("1".equals(isUpdateFlag)){//需要更新范本
                TradingItem tradingItem1=this.iTradingItem.selectByItemId(item.getItemID());
                if(tradingItem1!=null){//更新数据库中的范本
                    this.iTradingItem.updateTradingItem(item,tradingItem1);
                }
            }
            TradingListingData tld = this.iTradingListingData.selectByItemid(item.getItemID());

            Item ite = new Item();
            List litla = new ArrayList();
            ReviseItemRequest rir = new ReviseItemRequest();
            rir.setXmlns("urn:ebay:apis:eBLBaseComponents");
            rir.setErrorLanguage("en_US");
            rir.setWarningLevel("High");
            RequesterCredentials rc = new RequesterCredentials();
            String ebayAccount = tld.getEbayAccount();
            SessionVO c= SessionCacheSupport.getSessionVO();
            List<UsercontrollerEbayAccount> liuea = this.iUsercontrollerEbayAccount.selectUsercontrollerEbayAccountByUserId(c.getId());
            String token = "";
            for(UsercontrollerEbayAccount uea:liuea){
                if(ebayAccount.equals(uea.getEbayName())){
                    token=this.iUsercontrollerEbayAccount.selectById(uea.getId()).getEbayToken();
                }
            }
            rc.seteBayAuthToken(token);
            rir.setRequesterCredentials(rc);
            ite.setItemID(item.getItemID());
            for(String str : selectType){
                TradingListingAmendWithBLOBs tla = new TradingListingAmendWithBLOBs();
                tla.setItem(Long.parseLong(tld.getItemId()));
                tla.setParentId(tld.getId());
                if(str.equals("StartPrice")){//改价格
                    tla.setAmendType("StartPrice");
                    if("FixedPriceItem".equals(listingType)) {
                        ite.setStartPrice(item.getStartPrice());
                        tla.setContent("将价格从" + tld.getPrice() + "修改为" + item.getStartPrice().getValue());
                        tld.setPrice(item.getStartPrice().getValue());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setStartPrice(item.getStartPrice());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if("2".equals(listingType)){
                        tla.setContent("多属性价格调整！");
                        ite.setVariations(item.getVariations());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setVariations(item.getVariations());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if("Chinese".equals(listingType)){
                        tla.setContent("将价格从" + tld.getPrice() + "修改为" + item.getStartPrice().getValue());
                        tld.setPrice(item.getStartPrice().getValue());
                        item.setBuyItNowPrice(item.getStartPrice().getValue());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setBuyItNowPrice(item.getBuyItNowPrice());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }

                }else if(str.equals("Quantity")){//改数量
                    tla.setAmendType("Quantity");
                    tla.setContent("将数量从" + tld.getQuantity() + "修改为" + item.getQuantity());
                    ite.setQuantity(item.getQuantity());
                    tld.setQuantity(item.getQuantity().longValue());
                    Item ites = new Item();
                    ites.setItemID(item.getItemID());
                    ites.setQuantity(item.getQuantity());
                    rir.setItem(ites);
                    tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                }else if(str.equals("PictureDetails")){//改图片
                    tla.setAmendType("PictureDetails");
                    tla.setContent("图片修改");
                    ite.setPictureDetails(item.getPictureDetails());
                    Item ites = new Item();
                    ites.setItemID(item.getItemID());
                    ites.setPictureDetails(item.getPictureDetails());
                    rir.setItem(ites);
                    tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                }else if(str.equals("PayPal")){//改支付方式
                    tla.setAmendType("PayPal");
                    tla.setContent("支付方式修改");
                    ite.setPayPalEmailAddress(item.getPayPalEmailAddress());
                    Item ites = new Item();
                    ites.setItemID(item.getItemID());
                    ites.setPayPalEmailAddress(item.getPayPalEmailAddress());
                    rir.setItem(ites);
                    tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                }else if(str.equals("ReturnPolicy")){//改退货政策
                    tla.setAmendType("ReturnPolicy");
                    tla.setContent("退货政策修改");
                    ite.setReturnPolicy(item.getReturnPolicy());
                    Item ites = new Item();
                    ites.setItemID(item.getItemID());
                    ites.setReturnPolicy(item.getReturnPolicy());
                    rir.setItem(ites);
                    tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                }else if(str.equals("Title")){//改标题　
                    tla.setAmendType("Title");
                    tla.setContent("标题修改为："+item.getTitle());
                    ite.setTitle(item.getTitle());
                    tld.setTitle(item.getTitle());
                    Item ites = new Item();
                    ites.setItemID(item.getItemID());
                    ites.setTitle(item.getTitle());
                    rir.setItem(ites);
                    tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                }else if(str.equals("Buyer")){//改买家要求
                    tla.setAmendType("Buyer");
                    tla.setContent("修改买家要求");
                    ite.setBuyerRequirementDetails(item.getBuyerRequirementDetails());
                    Item ites = new Item();
                    ites.setItemID(item.getItemID());
                    ites.setBuyerRequirementDetails(item.getBuyerRequirementDetails());
                    rir.setItem(ites);
                    tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                }else if(str.equals("SKU")){//改ＳＫＵ
                    tla.setAmendType("SKU");
                    tla.setContent("SKU修改为："+item.getSKU());
                    ite.setSKU(item.getSKU());
                    tld.setSku(item.getSKU());
                    Item ites = new Item();
                    ites.setItemID(item.getItemID());
                    ites.setSKU(item.getSKU());
                    rir.setItem(ites);
                    tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                }else if(str.equals("PrimaryCategory")){//改分类
                    tla.setAmendType("PrimaryCategory");
                    tla.setContent("商品分类修改为:"+item.getPrimaryCategory().getCategoryID());
                    ite.setPrimaryCategory(item.getPrimaryCategory());
                    Item ites = new Item();
                    ites.setItemID(item.getItemID());
                    ites.setPrimaryCategory(item.getPrimaryCategory());
                    rir.setItem(ites);
                    tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                }else if(str.equals("ConditionID")){//改商品状态
                    tla.setAmendType("ConditionID");
                    tla.setContent("修改商品状态");
                    ite.setConditionID(item.getConditionID());
                    Item ites = new Item();
                    ites.setItemID(item.getItemID());
                    ites.setConditionID(item.getConditionID());
                    rir.setItem(ites);
                    tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                }else if(str.equals("Location")){//改运输到的地址
                    tla.setAmendType("Location");
                    ite.setLocation(item.getLocation());
                    Item ites = new Item();
                    ites.setItemID(item.getItemID());
                    ites.setLocation(item.getLocation());
                    rir.setItem(ites);
                    tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                }else if(str.equals("DispatchTimeMax")){//最快处理时间
                    tla.setAmendType("DispatchTimeMax");
                    tla.setContent("修改处理时间");
                    ite.setDispatchTimeMax(item.getDispatchTimeMax());
                    Item ites = new Item();
                    ites.setItemID(item.getItemID());
                    ites.setDispatchTimeMax(item.getDispatchTimeMax());
                    rir.setItem(ites);
                    tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                }else if(str.equals("PrivateListing")){//改是否允许私人买
                    tla.setAmendType("PrivateListing");
                    ite.setPrivateListing(item.getPrivateListing());
                    Item ites = new Item();
                    ites.setItemID(item.getItemID());
                    ites.setPrivateListing(item.getPrivateListing());
                    rir.setItem(ites);
                    tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                }else if(str.equals("ListingDuration")){//改刊登天数
                    tla.setAmendType("ListingDuration");
                    tla.setContent("修改刊登天数为："+item.getListingDuration());
                    ite.setListingDuration(item.getListingDuration());
                    Item ites = new Item();
                    ites.setItemID(item.getItemID());
                    ites.setListingDuration(item.getListingDuration());
                    rir.setItem(ites);
                    tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                }else if(str.equals("Description")){//改商品描述
                    tla.setContent("修改商品描述");
                    tla.setAmendType("Description");
                    ite.setDescription(item.getDescription());
                    Item ites = new Item();
                    ites.setItemID(item.getItemID());
                    ites.setDescription(item.getDescription());
                    rir.setItem(ites);
                    tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                }else if(str.equals("ShippingDetails")){//改运输详情
                    tla.setAmendType("ShippingDetails");
                    tla.setContent("修改运输详情");
                    ShippingDetails sdf = item.getShippingDetails();
                    String nottoLocation = request.getParameter("notLocationValue");
                    if(!ObjectUtils.isLogicalNull(nottoLocation)){
                        String noLocation[] =nottoLocation.split(",");
                        List listr = new ArrayList();
                        for(String nostr : noLocation){
                            listr.add(nostr);
                        }
                        sdf.setExcludeShipToLocation(listr);
                    }
                    ite.setShippingDetails(sdf);
                    Item ites = new Item();
                    ites.setItemID(item.getItemID());
                    ites.setShippingDetails(item.getShippingDetails());
                    rir.setItem(ites);
                    tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                }
                litla.add(tla);
            }

            String xml = "";
            rir.setItem(ite);
            xml = PojoXmlUtil.pojoToXml(rir);
            xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+xml;
            System.out.println(xml);

            String returnString = this.cosPostXml(xml,APINameStatic.ReviseItem);
            String ack = SamplePaseXml.getVFromXmlString(returnString,"Ack");
            if("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)){
                this.saveAmend(litla,"1");
                this.iTradingListingData.updateTradingListingData(tld);

            }else{
                this.saveAmend(litla,"0");
                Document document= DocumentHelper.parseText(returnString);
                Element rootElt = document.getRootElement();
                Element tl = rootElt.element("Errors");
                String longMessage = tl.elementText("LongMessage");
                if(longMessage==null){
                    longMessage = tl.elementText("ShortMessage");
                }

                this.saveSystemLog(longMessage,"在线修改商品报错",SiteMessageStatic.LISTING_DATA_UPDATE);
                AjaxSupport.sendFailText("fail",longMessage);
            }
        }
        AjaxSupport.sendSuccessText("message", "操作成功！");

    }

    public void saveAmend(List<TradingListingAmendWithBLOBs> litlam,String isflag) throws Exception {
        for(TradingListingAmendWithBLOBs tla : litlam){
            ObjectUtils.toInitPojoForInsert(tla);
            tla.setIsFlag(isflag);
            this.iTradingListingData.insertTradingListingAmend(tla);
        }
    }

    /**
     * 同步在线商品
     * @param ebayName
     * @param startTime
     * @param endTime
     * @param pageNumber
     * @param token
     * @return
     */
    public String getCosXml(String ebayName,String startTime,String endTime,int pageNumber,String token){
        String colStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetSellerListRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>"+token+"</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "<Pagination ComplexType=\"PaginationType\">\n" +
                "\t<EntriesPerPage>100</EntriesPerPage>\n" +
                "\t<PageNumber>"+pageNumber+"</PageNumber>\n" +
                "</Pagination>\n" +
                "<StartTimeFrom>"+startTime+"</StartTimeFrom>\n" +
                "<StartTimeTo>"+endTime+"</StartTimeTo>\n" +
                "<UserID>"+ebayName+"</UserID>\n" +
                "<IncludeVariations>true</IncludeVariations><IncludeWatchCount>true</IncludeWatchCount>\n" +
                "<DetailLevel>ReturnAll</DetailLevel>\n" +
                "</GetSellerListRequest>​";
        return colStr;
    }

    /**
     * 同步在线商品
     * @param request
     * @param modelMap
     * @param commonParmVO
     * @throws Exception
     */
    @RequestMapping("/ajax/synListingData.do")
    @ResponseBody
    public void synListingData(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        String [] ebayAccounts = request.getParameter("ebayAccount").split(",");
        SessionVO c= SessionCacheSupport.getSessionVO();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 119);
        String startTo="";
        String startFrom="";
        try {
            Date endDate = dft.parse(dft.format(date.getTime()));
            startTo = DateUtils.DateToString(new Date());
            startFrom = DateUtils.DateToString(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //站点列表
        AddApiTask addApiTask = new AddApiTask();
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        d.setApiCallName(APINameStatic.ListingItemList);
        List<TradingDataDictionary> litdd = DataDictionarySupport.getTradingDataDictionaryByType("site");
        List<UsercontrollerEbayAccount> liusereae = new ArrayList<UsercontrollerEbayAccount>();
        for(String ebayAccount : ebayAccounts){
            liusereae.add(this.iUsercontrollerEbayAccount.selectByEbayAccount(ebayAccount));
        }
        for(UsercontrollerEbayAccount ue : liusereae){
            UsercontrollerEbayAccount ues = this.iUsercontrollerEbayAccount.selectById(ue.getId());
            for(TradingDataDictionary tdd:litdd){
                List<ListingDataTask> lidk = iListingDataTask.selectByflag(tdd.getName1(),ue.getEbayAccount());
                if(lidk!=null&&lidk.size()>0){
                    for(ListingDataTask ldk:lidk){
                        ldk.setTaskFlag("1");
                        ldk.setCreateDate(new Date());
                        iListingDataTask.saveListDataTask(ldk);
                    }
                }
                ListingDataTask ldt= new ListingDataTask();
                ldt.setCreateDate(new Date());
                ldt.setEbayaccount(ue.getEbayAccount());
                ldt.setSite(tdd.getName1());
                ldt.setToken(ue.getEbayToken());
                ldt.setUserid(ue.getUserId());
                ldt.setTaskFlag("2");
                iListingDataTask.saveListDataTask(ldt);

                d.setApiSiteid(tdd.getName1());
                String colStr = this.getCosXml(ues.getEbayAccount(),startFrom,startTo,1,ues.getEbayToken());
                TaskMessageVO taskMessageVO=new TaskMessageVO();
                taskMessageVO.setMessageContext(ues.getEbayName()+"在"+tdd.getName()+"站点同步在线商品");
                taskMessageVO.setMessageTitle("同步在线商品");
                taskMessageVO.setMessageType(SiteMessageStatic.SYN_MESSAGE_LISTING_DATA_TYPE);
                taskMessageVO.setBeanNameType(SiteMessageStatic.SYN_MESSAGE_LISTING_DATA_BEAN);
                taskMessageVO.setMessageFrom("system");
                SessionVO sessionVO=SessionCacheSupport.getSessionVO();
                taskMessageVO.setMessageTo(sessionVO.getId());
                taskMessageVO.setObjClass(new String[]{ues.getEbayAccount(),c.getId()+"",ues.getEbayToken(),tdd.getName1()});
                addApiTask.execDelayReturn(d, colStr, apiUrl, taskMessageVO);
            }
        }
        AjaxSupport.sendSuccessText("message", "操作成功！后台正在同步数据,请稍后查看！");
    }

    /**
     * 保存同步在线商品数据
     * @param cosXml
     * @param ebayAccount
     * @param userid
     * @param pages
     * @param token
     *//*
    public void saveSynListingData(String cosXml,String ebayAccount,Long userid,int pages,String token,String siteid){
        SessionVO c= SessionCacheSupport.getSessionVO();
        UserInfoService userInfoService = (UserInfoService) ApplicationContextUtil.getBean(UserInfoService.class);
        TradingListingDataMapper tldm = (TradingListingDataMapper) ApplicationContextUtil.getBean(TradingListingDataMapper.class);
        CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 119);
        String startTo="";
        String startFrom="";
        try {
            Date endDate = dft.parse(dft.format(date.getTime()));
            startTo = DateUtils.DateToString(new Date());
            startFrom = DateUtils.DateToString(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(userid);
            d.setApiSiteid(siteid);
            d.setApiCallName(APINameStatic.ListingItemList);
            AddApiTask addApiTask = new AddApiTask();
            Map<String, String> resMap= addApiTask.exec(d, cosXml, commPars.apiUrl);
            String res=resMap.get("message");
            System.out.println(res);
            String ack = SamplePaseXml.getVFromXmlString(res,"Ack");
            if(ack.equals("Success")){
                Document document= DocumentHelper.parseText(res);
                Element rootElt = document.getRootElement();
                Element totalElt = rootElt.element("PaginationResult");
                String totalCount = totalElt.elementText("TotalNumberOfEntries");
                String page =  totalElt.elementText("TotalNumberOfPages");
                for(int i=1;i<=Integer.parseInt(page);i++){
                    String colStr = this.getCosXml(ebayAccount,startFrom,startTo,i,token);
                    TaskMessageVO taskMessageVO=new TaskMessageVO();
                    taskMessageVO.setMessageContext("用户点击同步在线商品");
                    taskMessageVO.setMessageTitle("同步在线商品");
                    taskMessageVO.setMessageType(SiteMessageStatic.SYN_MESSAGE_LISTING_DATA_TYPE);
                    taskMessageVO.setBeanNameType(SiteMessageStatic.SYN_MESSAGE_LISTING_DATA_BEAN);
                    taskMessageVO.setMessageFrom("system");
                    SessionVO sessionVO=SessionCacheSupport.getSessionVO();
                    taskMessageVO.setMessageTo(sessionVO.getId());
                    taskMessageVO.setObjClass(new String[]{ebayAccount,c.getId()+"",});
                    addApiTask.execDelayReturn(d, colStr, apiUrl, taskMessageVO);
                }
            }else{
                String resStr = "";
                if(res!=null){
                    resStr = SamplePaseXml.getSpecifyElementTextAllInOne(res,"Errors","LongMessage");
                }else{
                    resStr = "请求失败！";
                }
                this.saveSystemLog(resStr,"同步在线商品报错",SiteMessageStatic.SYN_MESSAGE_LISTING_DATA_TYPE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }*/
}
