package com.inventory.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.base.aboutpaypal.paypalutils.PaypalxmlUtil;
import com.base.database.customtrading.mapper.ItemInventoryQueryMapper;
import com.base.database.inventory.mapper.ItemInventoryMapper;
import com.base.database.inventory.mapper.ShihaiyouInventoryMapper;
import com.base.database.inventory.model.ItemInventory;
import com.base.database.inventory.model.ItemInventoryExample;
import com.base.database.inventory.model.ShihaiyouInventory;
import com.base.database.inventory.model.ShihaiyouInventoryExample;
import com.base.domains.querypojos.ItemInventoryQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.DateUtils;
import com.base.utils.httpclient.HttpClientUtil;
import com.inventory.service.ItemInventoryStatic;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.xpath.DefaultXPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by Administrtor on 2014/11/17.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ItemInventoryImpl implements com.inventory.service.IItemInventory {
    static Logger logger = Logger.getLogger(ItemInventoryImpl.class);

    @Autowired
    private ItemInventoryMapper itemInventoryMapper;
    @Autowired
    private ShihaiyouInventoryMapper shihaiyouInventoryMapper;
    @Autowired
    private ItemInventoryQueryMapper itemInventoryQueryMapper;

    @Value("${CHUKOUYI_URL}")
    private String chukouyi_url;
    @Value("${CHUKOUYI_TOKEN}")
    private String chukouyi_token;
    @Value("${CHUKOUYI_USER_KEY}")
    private String chukouyi_user_key;
    @Value("${DESHIFANG_URL}")
    private String deshifang_url;
    @Value("${DESHIFANG_CUSTOMERID}")
    private String deshifang_customerid;
    @Value("${DESHIFANG_TODEN}")
    private String deshifang_toden;

    @Override
    public void saveItemInventory(ItemInventory itemInventory){
        if(itemInventory.getId()==null){
            this.itemInventoryMapper.insertSelective(itemInventory);
        }else{
            this.itemInventoryMapper.updateByPrimaryKeySelective(itemInventory);
        }
    }

    public List<ItemInventory> selectBySku(String sku,String storageno,String warehouse){
        ItemInventoryExample iie = new ItemInventoryExample();
        iie.createCriteria().andSkuEqualTo(sku).andStorageNoEqualTo(storageno).andWarehouseEqualTo(warehouse).andCreateDateBetween(DateUtils.turnToDateStart(new Date()),DateUtils.turnToDateEnd(new Date()));
        return this.itemInventoryMapper.selectByExample(iie);
    }

    @Override
    public void saveListItemInventory(List<ItemInventory> liii){
        for(ItemInventory ii : liii){
            List<ItemInventory> li = this.selectBySku(ii.getSku(),ii.getStorageNo(),ii.getWarehouse());
            if(ii!=null&&li.size()>0){
                ii.setId(li.get(0).getId());
            }
            this.saveItemInventory(ii);
        }
    }

    @Override
    public void getChuKouYiInventory(){
        HttpClient httpClient= HttpClientUtil.getHttpClient();
        String abcd="";
        try {
            String posurl = chukouyi_url+ ItemInventoryStatic.LIST_PRODUCT_STOCK+"?token="+chukouyi_token+"&user_key="+chukouyi_user_key+"&start_index=1&count=100";
            String res = HttpClientUtil.get(httpClient, posurl);
            Map jsons = JSON.parseObject(res, HashMap.class);
            Map body = JSON.parseObject(jsons.get("body").toString(),HashMap.class);
            JSONArray dt = JSON.parseArray(body.get("stock_detail").toString());
            List<ItemInventory> liii = new ArrayList<ItemInventory>();
            String total_count = body.get("total_count")+"";
            int page=1;
            //分页查询
            if(Integer.parseInt(total_count)%100>0){
                page = Integer.parseInt(total_count)/100 + 1;
            }else{
                page = Integer.parseInt(total_count)/100;
            }

            for(int j=1;j<=page;j++){
                posurl = chukouyi_url+ ItemInventoryStatic.LIST_PRODUCT_STOCK+"?token="+chukouyi_token+"&user_key="+chukouyi_user_key+"&start_index="+((j-1)*100+1)+"&count=100";
                res = HttpClientUtil.get(httpClient, posurl);
                abcd=res;
                try {
                    jsons = JSON.parseObject(res, HashMap.class);
                    body = JSON.parseObject(jsons.get("body").toString(), HashMap.class);
                    dt = JSON.parseArray(body.get("stock_detail").toString());
                }catch(Exception e){
                    logger.error("itemInventory:"+res,e);
                    continue;
                }
                for(int i=0;i<dt.size();i++){
                    Map stock_detail = JSON.parseObject(dt.get(i).toString(), HashMap.class);
                    ItemInventory ii = new ItemInventory();
                    ii.setDataSource("1");
                    ii.setSku(stock_detail.get("sku") + "");
                    ii.setStorageNo(stock_detail.get("storage_no") + "");
                    ii.setWarehouse(stock_detail.get("warehouse") + "");
                    ii.setAvailStock(Integer.parseInt(stock_detail.get("avail_stock") + ""));
                    ii.setStock(Integer.parseInt(stock_detail.get("stock") + ""));
                    ii.setStockShipping(Integer.parseInt(stock_detail.get("stock_shipping") + ""));
                    ii.setCreateDate(new Date());
                    liii.add(ii);
                }
            }
            this.saveListItemInventory(liii);
        } catch (Exception e) {
            logger.error("itemInvent:",e);
        }
    }

    @Override
    public void getSiHaiYouInventory(){
        HttpClient httpClient= HttpClientUtil.getHttpClient();
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <QueryInventory xmlns=\"http://www.4haiyou.com/\">\n" +
                "      <userName>demo1@4haiyou.com</userName>\n" +
                "      <password>"+this.md5("11111111auctivision")+"</password>\n" +
                "    </QueryInventory>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>";
        BasicHeader head1 =  new BasicHeader("SOAPAction","http://www.4haiyou.com/QueryInventory");
        BasicHeader head2 = new BasicHeader("Host","system.4haiyou.com");
        BasicHeader head3=new BasicHeader("Content-Type","text/xml; charset=utf-8");
        List<BasicHeader> list=new ArrayList<BasicHeader>();
        list.add(head1);
        list.add(head2);
        list.add(head3);
        String res="";
        try {
            res = HttpClientUtil.post(httpClient,"http://system.4haiyou.com/Merchant/invetoryapi.asmx",xml,"utf-8",list);
            Element el = PaypalxmlUtil.getSpecElement(res, "Body", "QueryInventoryResponse", "QueryInventoryResult");
            String ack = el.element("ACK").getText();
            if("Success".equals(ack)){
                Element els = el.element("Inventory");
                Iterator<Element> ite = els.elementIterator("InventoryEntity");
                List<ShihaiyouInventory> li = new ArrayList<ShihaiyouInventory>();
                while (ite.hasNext()){
                    Element invent = ite.next();
                    ShihaiyouInventory si = new ShihaiyouInventory();
                    si.setShiId(Long.parseLong(invent.element("Id").getText()));
                    si.setProductid(Long.parseLong(invent.element("ProductId").getText()));
                    si.setProductname(invent.element("ProductName") == null ? null : invent.element("ProductName").getText());
                    si.setIsdeleted(invent.element("IsDeleted").equals("false") ? "1" : "0");
                    si.setCreateddatetime(DateUtils.returnDate(invent.element("CreatedDatetime").getText()));
                    si.setModifieddatetime(DateUtils.returnDate(invent.element("ModifiedDatetime").getText()));
                    si.setModifiedby(invent.element("ModifiedBy")==null?null:invent.element("ModifiedBy").getText());
                    si.setQuantity(Integer.parseInt(invent.element("Quantity").getText()));
                    si.setStatus(invent.element("Status").getText());
                    si.setAgencyid(Long.parseLong(invent.element("AgencyId").getText()));
                    si.setAgencyname(invent.element("AgencyName")==null?null:invent.element("AgencyName").getText());
                    si.setUnit(invent.element("Unit")==null?null:invent.element("Unit").getText());
                    si.setSku(invent.element("Sku").getText());
                    si.setCreateDate(new Date());
                    li.add(si);
                }
                this.saveShiHaiYouList(li);
            }

        } catch (Exception e) {
            logger.error(res+":",e);
        }
    }
    @Override
    public List<ShihaiyouInventory> selectBySkuShiHaiYou(String sku,Long productId,String status){
        ShihaiyouInventoryExample sie = new ShihaiyouInventoryExample();
        sie.createCriteria().andSkuEqualTo(sku).andProductidEqualTo(productId).andStatusEqualTo(status).andCreateDateBetween(DateUtils.turnToDateStart(new Date()),DateUtils.turnToDateEnd(new Date()));
        return this.shihaiyouInventoryMapper.selectByExample(sie);
    }
    @Override
    public void saveShiHaiYouList(List<ShihaiyouInventory> li){
        for(ShihaiyouInventory si : li){
            List<ShihaiyouInventory> lis = this.selectBySkuShiHaiYou(si.getSku(),si.getProductid(),si.getStatus());
            if(lis!=null&&lis.size()>0){
                si.setId(lis.get(0).getId());
            }
            this.saveShiHaiYouData(si);
        }
    }

    @Override
    public void saveShiHaiYouData(ShihaiyouInventory shihaiyouInventory){
        if(shihaiyouInventory.getId()==null){
            this.shihaiyouInventoryMapper.insertSelective(shihaiyouInventory);
        }else{
            this.shihaiyouInventoryMapper.updateByPrimaryKeySelective(shihaiyouInventory);
        }
    }



    /**
     * md5加密
     * @param s
     * @return
     */
    public static String md5(String s) {
        if (s != null) {
            String md5_str = "";
            try {
                MessageDigest m = MessageDigest.getInstance("MD5");
                m.update(s.getBytes(), 0, s.length());
                md5_str = new BigInteger(1, m.digest()).toString(16);
                // Log.i("MD5", md5_str);
                if (md5_str.length() < 32) {
                    md5_str = "0".concat(md5_str);
                }
            } catch (NoSuchAlgorithmException e) {
                logger.error("md5..",e);
            }
            return md5_str;
        }
        return "";
    }

    @Override
    public void getDeShiFangInventory(){
        HttpClient httpClient= HttpClientUtil.getHttpClient();
        String res="";
        try {

            BasicHeader head1 =  new BasicHeader("Content-Type","application/json");
            List<BasicHeader> list=new ArrayList<BasicHeader>();
            list.add(head1);
            res = HttpClientUtil.post(httpClient,deshifang_url+ItemInventoryStatic.DESHIFANG_GETINVENTORY+"?format=json&customerId="+deshifang_customerid+"&token="+deshifang_toden+"&language=en_US","{}","utf-8",list);
            Map jsons = JSON.parseObject(res, HashMap.class);
            JSONArray dt = JSON.parseArray(jsons.get("data").toString());
            List<ItemInventory> liii = new ArrayList<ItemInventory>();
            for(int i=0;i<dt.size();i++){
                Map stock_detail = JSON.parseObject(dt.get(i).toString(), HashMap.class);
                ItemInventory ii = new ItemInventory();
                ii.setDataSource("2");
                ii.setSku(stock_detail.get("sku") + "");
                ii.setStorageNo(stock_detail.get("skuId") + "");
                ii.setWarehouse(stock_detail.get("warehouseCode") + "");
                ii.setAvailStock(Integer.parseInt(stock_detail.get("actualQuantity") + ""));
                ii.setStock(Integer.parseInt(stock_detail.get("availableQuantity") + ""));
                ii.setStockShipping(Integer.parseInt(stock_detail.get("shippingQuantity") + ""));
                ii.setStockPending(Integer.parseInt(stock_detail.get("pendingQuantity")+""));
                ii.setCreateDate(new Date());
                liii.add(ii);
            }
            this.saveListItemInventory(liii);
        } catch (Exception e) {
            logger.error("itemInvent11" + res,e);
        }
    }

    @Override
    public List<ItemInventoryQuery> selectBySku(Map map,Page page){
        return this.itemInventoryQueryMapper.selectItemInventoryList(map,page);
    }

}
