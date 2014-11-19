package com.inventory.controller;

import com.base.sampleapixml.BindAccountAPI;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.httpclient.HttpClientUtil;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.inventory.service.IItemInventory;
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
import java.util.List;

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
        this.iItemInventory.getChuKouYiInventory();
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
        this.iItemInventory.getDeShiFangInventory();
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
        this.iItemInventory.getSiHaiYouInventory();
        AjaxSupport.sendSuccessText("success", "获取成功");
    }

}
