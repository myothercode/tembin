package com.inventory.controller;

import com.base.database.inventory.model.UserInventorySet;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.CommonParmVO;
import com.base.domains.querypojos.ItemInventoryQuery;
import com.base.mybatis.page.Page;
import com.base.sampleapixml.BindAccountAPI;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.httpclient.HttpClientUtil;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.inventory.service.IItemInventory;
import com.inventory.service.IUserInventorySet;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;
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
 * Created by Administrtor on 2014/9/17.
 */
@Controller
@RequestMapping("inventory")
public class InventoryController extends BaseAction {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private IItemInventory iItemInventory;
    @Autowired
    private IUserInventorySet iUserInventorySet;
    @RequestMapping("/InventoryList.do")
    public ModelAndView MessageGetmymessageList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return forword("inventory/InventoryList",modelMap);
    }
    @RequestMapping("/ajax/siHaiYouAddOrder.do")
    /**回复消息*/
    public void siHaiYouAddOrder(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        /*HttpClient httpClient= HttpClientUtil.getHttpClient();
        String xml= BindAccountAPI.getSiHaiYouAddOrder();
        BasicHeader head1 =  new BasicHeader("SOAPAction","http://www.4haiyou.com/AddOrder");
        BasicHeader head2 = new BasicHeader("Host","sandbox.4haiyou.com");
        BasicHeader head3=new BasicHeader("Content-Type","text/xml; charset=utf-8");
        List<BasicHeader> list=new ArrayList<BasicHeader>();
        list.add(head1);
        list.add(head2);
        list.add(head3);
        *//*HttpPost post=new HttpPost();
        for(BasicHeader l:list){
            post.addHeader(l);
        }
        HttpResponse response1 =httpClient.execute(post);*//*
        String res = HttpClientUtil.post(httpClient,"http://sandbox.4haiyou.com/Merchant/Order.asmx",xml,"utf-8",list);*/

        //验证
        /*HttpClient httpClient= HttpClientUtil.getHttpClient();
        String xml= BindAccountAPI.getChuKouYi();
        BasicHeader head1 =  new BasicHeader("SOAPAction","http://www.chukou1.com/VerifyUser");
        BasicHeader head2 = new BasicHeader("Host","demo.chukou1.cn");
        BasicHeader head3=new BasicHeader("Content-Type","text/xml; charset=utf-8");
        List<BasicHeader> list=new ArrayList<BasicHeader>();
        list.add(head1);
        list.add(head2);
        list.add(head3);
        String res = HttpClientUtil.post(httpClient,"http://demo.chukou1.cn/client/ws/v2.1/ck1.asmx",xml,"utf-8",list);*/
        //获取产品库存
        HttpClient httpClient= HttpClientUtil.getHttpClient();
        String xml= BindAccountAPI.getChuKouYi();
        //BasicHeader head1 =  new BasicHeader("SOAPAction","http://www.chukou1.com/ProductGetStock");
        /*BasicHeader head1 =  new BasicHeader("SOAPAction","http://demo.chukou1.cn/v3/Product/stock/list-product-stock");
        BasicHeader head2 = new BasicHeader("Host","demo.chukou1.cn");
        BasicHeader head3=new BasicHeader("Content-Type","text/xml; charset=utf-8");
        List<BasicHeader> list=new ArrayList<BasicHeader>();
        list.add(head1);
        list.add(head2);
        list.add(head3);*/
        String res = HttpClientUtil.get(httpClient,"http://demo.chukou1.cn/v3/Product/stock/list-product-stock","token=887E99B5F89BB18BEA12B204B620D236&user_key=wr5qjqh4gj&start_index=1&count=100");
        AjaxSupport.sendSuccessText("success", "获取成功");
    }

    /**
     * 得到出口易库存数据
     * @param request
     * @param response
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/ajax/getChuKouYiData.do")
    public void getChuKouYiData(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UserInventorySet> lius = this.iUserInventorySet.selectByOrgIdList(c.getOrgId());
        try{
            if(lius!=null){
                for(UserInventorySet us:lius){
                    if("出口易".equals(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(us.getDataType())).getName())) {
                        this.iItemInventory.getChuKouYiInventory(us.getUserName(), us.getUserKey(), c.getOrgId() + "");
                    }
                }
            }
        }catch(Exception e){
            AjaxSupport.sendFailText("fail","获取库存信息失败！");
        }
        AjaxSupport.sendSuccessText("success", "获取成功");

    }

    /**
     * 得到递四方库存数据
     * @param request
     * @param response
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/ajax/getDeShiFangData.do")
    public void getDeShiFangData(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UserInventorySet> lius = this.iUserInventorySet.selectByOrgIdList(c.getOrgId());
        try{
            if(lius!=null){
                for(UserInventorySet us:lius){
                    if("第四方".equals(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(us.getDataType())).getName())) {
                        this.iItemInventory.getDeShiFangInventory(us.getUserName(), us.getUserKey(), c.getOrgId() + "");
                    }
                }
            }
        }catch(Exception e){
            AjaxSupport.sendFailText("fail","获取库存信息失败！");
        }
        AjaxSupport.sendSuccessText("success", "获取成功");
    }

    /**
     * 得到四海邮库存数据
     * @param request
     * @param response
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/ajax/getShiHaiYouData.do")
    public void getShiHaiYouData(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UserInventorySet> lius = this.iUserInventorySet.selectByOrgIdList(c.getOrgId());
        try{
            if(lius!=null){
                for(UserInventorySet us:lius){
                    if("四海邮".equals(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(us.getDataType())).getName())) {
                        this.iItemInventory.getSiHaiYouInventory(us.getUserName(), us.getUserKey(), c.getOrgId() + "");
                    }
                }
            }
        }catch(Exception e){
            AjaxSupport.sendFailText("fail","获取库存信息失败！");
        }
        AjaxSupport.sendSuccessText("success", "获取成功");
    }

    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadInventorySkuList.do")
    @ResponseBody
    public void loadInventorySkuList(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String content=request.getParameter("content");
        if(!StringUtils.isNotBlank(content)){
            content=null;
        }
        Map m = new HashMap();
        /**分页组装*/
        Map map=new HashMap();
        map.put("content",content);
        Page page=new Page();
        page.setCurrentPage(1);
        page.setPageSize(10);
        List<ItemInventoryQuery> lists= this.iItemInventory.selectBySku(map,page);
        AjaxSupport.sendSuccessText("", lists);
    }
}
