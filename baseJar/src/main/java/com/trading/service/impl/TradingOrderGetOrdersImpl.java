package com.trading.service.impl;

import com.base.database.customtrading.mapper.OrderGetOrdersMapper;
import com.base.database.trading.mapper.TradingOrderGetOrdersMapper;
import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.database.trading.model.TradingOrderGetOrdersExample;
import com.base.domains.querypojos.OrderGetOrdersQuery;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.mybatis.page.Page;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.base.utils.threadpool.TaskPool;
import com.trading.service.ITradingOrderGetOrdersNoTransaction;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * 订单
 * Created by lq on 2014/7/29.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingOrderGetOrdersImpl implements com.trading.service.ITradingOrderGetOrders {
    static Logger logger1 = Logger.getLogger(TradingOrderGetOrdersImpl.class);
    @Autowired
    private TradingOrderGetOrdersMapper tradingOrderGetOrdersMapper;

    @Autowired
    private OrderGetOrdersMapper orderGetOrdersMapper;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    @Autowired
    private ITradingOrderGetOrdersNoTransaction iTradingOrderGetOrdersNoTransaction;
    @Override
    public void saveOrderGetOrders(TradingOrderGetOrders OrderGetOrders) throws Exception {
        /*if(OrderGetOrders.getId()==null){
            ObjectUtils.toInitPojoForInsert(OrderGetOrders);
            tradingOrderGetOrdersMapper.insert(OrderGetOrders);
        }else{
            TradingOrderGetOrders t=tradingOrderGetOrdersMapper.selectByPrimaryKey(OrderGetOrders.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),TradingOrderGetOrdersMapper.class,OrderGetOrders.getId(),"Synchronize");
            tradingOrderGetOrdersMapper.updateByPrimaryKeySelective(OrderGetOrders);
        }*/
        TaskPool.togos.put(OrderGetOrders);
        if ("0".equals(TaskPool.togosBS[0])){
            iTradingOrderGetOrdersNoTransaction.saveOrderGetOrders(OrderGetOrders);
        }

    }

    @Override
    public List<OrderGetOrdersQuery> selectOrderGetOrdersByGroupList(Map map, Page page) {
        return orderGetOrdersMapper.selectOrderGetOrdersByGroupList(map, page);
    }

    @Override
    public List<TradingOrderGetOrders> selectOrderGetOrdersByOrderId(String orderId) {
        TradingOrderGetOrdersExample or=new TradingOrderGetOrdersExample();
        TradingOrderGetOrdersExample.Criteria cr=or.createCriteria();
        cr.andOrderidEqualTo(orderId);
        List<TradingOrderGetOrders> lists=tradingOrderGetOrdersMapper.selectByExample(or);
        return lists;
    }

    @Override
    public List<TradingOrderGetOrders> selectOrderGetOrdersByTransactionId(String TransactionId,String seller) {
        TradingOrderGetOrdersExample or=new TradingOrderGetOrdersExample();
        TradingOrderGetOrdersExample.Criteria cr=or.createCriteria();
        cr.andTransactionidEqualTo(TransactionId);
        cr.andSelleruseridEqualTo(seller);
        List<TradingOrderGetOrders> lists=tradingOrderGetOrdersMapper.selectByExample(or);
        return lists;
    }

    @Override
    public List<TradingOrderGetOrders> selectOrderGetOrdersByPaypalStatus(String status,List<String> ebays) {
        TradingOrderGetOrdersExample example=new TradingOrderGetOrdersExample();
        TradingOrderGetOrdersExample.Criteria cr=example.createCriteria();
        if(status!=null){
            cr.andStatusEqualTo(status);
        }
        cr.andSelleruseridIn(ebays);
        List<TradingOrderGetOrders> list=tradingOrderGetOrdersMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<TradingOrderGetOrders> selectOrderGetOrdersByFolder(String folderId,List<String> ebays) {
        TradingOrderGetOrdersExample example=new TradingOrderGetOrdersExample();
        TradingOrderGetOrdersExample.Criteria cr=example.createCriteria();
        if(folderId!=null){
            cr.andFolderEqualTo(folderId);
        }
        cr.andSelleruseridIn(ebays);
        List<TradingOrderGetOrders> list=tradingOrderGetOrdersMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<TradingOrderGetOrders> selectOrderGetOrdersByBuyerAndItemid(String itemid, String buyer) {
        TradingOrderGetOrdersExample example=new TradingOrderGetOrdersExample();
        TradingOrderGetOrdersExample.Criteria cr=example.createCriteria();
        cr.andBuyeruseridEqualTo(buyer);
        cr.andItemidEqualTo(itemid);
        List<TradingOrderGetOrders> list=tradingOrderGetOrdersMapper.selectByExample(example);
        return list;
    }

    @Override
    public void downloadOrders(List<TradingOrderGetOrders> list, String outputFile, ServletOutputStream outputStream) throws IOException {
        // 创建新的Excel 工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 在Excel 工作簿中建一工作表
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        // 设置单元格格式(文本)
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));

        // 在索引0的位置创建行（第一行）
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell1 = row.createCell(0);// 第一列
        HSSFCell cell2 = row.createCell(1);
        HSSFCell cell3 = row.createCell(2);
        HSSFCell cell4 = row.createCell(3);
        HSSFCell cell5 = row.createCell(4);
        HSSFCell cell6= row.createCell(5);
        HSSFCell cell7 = row.createCell(6);
        HSSFCell cell8 = row.createCell(7);
        HSSFCell cell9 = row.createCell(8);
        HSSFCell cell10 = row.createCell(9);
        HSSFCell cell11 = row.createCell(10);
        HSSFCell cell12= row.createCell(11);
        // 定义单元格为字符串类型
        cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell8.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell9.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell11.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell12.setCellType(HSSFCell.CELL_TYPE_STRING);
        // 在单元格中输入头数据
        cell1.setCellValue("订单号 / 源订单号");
        cell2.setCellValue("买家ebay / 买家邮箱");
        cell3.setCellValue("追踪号 / 运送商");
        cell4.setCellValue("title");
        cell5.setCellValue("itemID");
        cell6.setCellValue("站点");
        cell7.setCellValue("售价");
        cell8.setCellValue("售出日期");
        cell9.setCellValue("发货日期");
        cell10.setCellValue("支付日期");
        cell11.setCellValue("付款状态");
        cell12.setCellValue("发货状态");
        for(int i=0;i<list.size();i++){
            row = sheet.createRow(i+1);
            String[] cells=new String[12];
            cells[0]=list.get(i).getOrderid();
            cells[1]=list.get(i).getBuyeruserid()+"("+list.get(i).getBuyeremail()+")";
            cells[2]=list.get(i).getShipmenttrackingnumber()+"  "+list.get(i).getShippingcarrierused();
            cells[3]=list.get(i).getTitle();
            cells[4]=list.get(i).getItemid();
            cells[5]=list.get(i).getCountry();
            cells[6]=list.get(i).getTransactionprice();
            cells[7]="";
            cells[8]="";
            cells[9]="";
            if(list.get(i).getCreatedtime()!=null){
                cells[7]=list.get(i).getCreatedtime().toString();
            }
            if(list.get(i).getShippedtime()!=null){
                cells[8]=list.get(i).getShippedtime().toString();
            }
            if(list.get(i).getPaidtime()!=null){
                cells[9]=list.get(i).getPaidtime().toString();
            }
            cells[10]=list.get(i).getStatus().toString();
            if(list.get(i).getShipmenttrackingnumber()==null&&list.get(i).getShippingcarrierused()==null){
                cells[11]="未发货";
            }else{
                cells[11]="已发货";
            }
            for(int j=0;j<cells.length;j++){
                HSSFCell cell = row.createCell(j);// 第一列
                // 设置单元格格式
                cell.setCellStyle(cellStyle);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(cells[j]);
            }

        }
        File path=new File(outputFile);
        File paren=path.getParentFile();
        if(!paren.exists()){
            paren.mkdirs();
        }
        if(path.exists()){
            path.delete();
        }
        // 新建一输出文件流
        try {
            FileOutputStream fOut = new FileOutputStream(outputFile);
            // 把相应的Excel 工作簿存盘
            workbook.write(outputStream);
        } catch (Exception e) {
           logger1.error("文件操作错误",e);
        }finally {
            // 操作结束，关闭文件
            outputStream.flush();
            outputStream.close();
        }

    }

    @Override
    public List<OrderGetOrdersQuery> selectOrderGetOrdersBySendPaidMessage() {
        /*TradingOrderGetOrdersExample example=new TradingOrderGetOrdersExample();
        TradingOrderGetOrdersExample.Criteria cr=example.createCriteria();
        cr.andPaypalflagIsNotNull();
        cr.andShippedflagIsNull();
        cr.andSendmessagetimeLessThan(new Date());
        cr.andAutomessageIdNotEqualTo(0L);
        List<TradingOrderGetOrders> list=tradingOrderGetOrdersMapper.selectByExample(example);
        return list;*/
        Map map=new HashMap();
        Page page=new Page();
        page.setCurrentPage(1);
        page.setPageSize(10);
        return orderGetOrdersMapper.selectOrderGetOrdersBySendPaidMessage(map, page);
    }

    @Override
    public List<OrderGetOrdersQuery> selectOrderGetOrdersBySendShipMessage() {
        /*TradingOrderGetOrdersExample example=new TradingOrderGetOrdersExample();
        TradingOrderGetOrdersExample.Criteria cr=example.createCriteria();
        cr.andPaypalflagIsNull();
        cr.andShippedflagIsNotNull();
        cr.andAutomessageIdNotEqualTo(0L);
        cr.andSendmessagetimeLessThan(new Date());
        List<TradingOrderGetOrders> list=tradingOrderGetOrdersMapper.selectByExample(example);
        return list;*/
        Map map=new HashMap();
        Page page=new Page();
        page.setCurrentPage(1);
        page.setPageSize(10);
        return orderGetOrdersMapper.selectOrderGetOrdersBySendShipMessage(map, page);
    }

    @Override
    public List<TradingOrderGetOrders> selectOrderGetOrdersByeBayAccountAndTime1(String ebay, Date start, Date end) {
        TradingOrderGetOrdersExample example=new TradingOrderGetOrdersExample();
        TradingOrderGetOrdersExample.Criteria cr=example.createCriteria();
        cr.andOrderstatusEqualTo("Completed");
        cr.andStatusEqualTo("Complete");
        if(start!=null&&end!=null){
            cr.andPaidtimeBetween(start,end);
        }
        if(ebay!=null){
            cr.andSelleruseridEqualTo(ebay);
        }else{
            Map ebayMap=new HashMap();
            List<UsercontrollerEbayAccountExtend> ebays=systemUserManagerService.queryCurrAllEbay(ebayMap);
            List<String> ebayNames=new ArrayList<String>();
            for(UsercontrollerEbayAccountExtend ebayAccountExtend:ebays){
                ebayNames.add(ebayAccountExtend.getEbayName());
            }
            if(ebayNames!=null&&ebayNames.size()>0) {
                cr.andSelleruseridIn(ebayNames);
            }else{
                return new ArrayList<TradingOrderGetOrders>();
            }
        }
        List<TradingOrderGetOrders> list=tradingOrderGetOrdersMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<OrderGetOrdersQuery> selectOrderGetOrdersByeBayAccountAndTime(String ebay, Date start,Date end) {

        Map map=new HashMap();
        Page page=new Page();
        page.setCurrentPage(1);
        page.setPageSize(10);
        map.put("start",start);
        map.put("end",end);
        List<UsercontrollerEbayAccountExtend> ebays=new ArrayList<UsercontrollerEbayAccountExtend>();
        if(ebay!=null){
            UsercontrollerEbayAccountExtend ebay1=new UsercontrollerEbayAccountExtend();
            ebay1.setEbayName(ebay);
            ebays.add(ebay1);
        }else{
            Map ebayMap=new HashMap();
            ebays=systemUserManagerService.queryCurrAllEbay(ebayMap);
        }
        map.put("ebays",ebays);
        return orderGetOrdersMapper.selectOrderGetOrdersByeBayAccountAndTime(map,page);
    }

    @Override
    public TradingOrderGetOrders selectOrderGetOrdersById(Long id) {
        TradingOrderGetOrdersExample example=new TradingOrderGetOrdersExample();
        TradingOrderGetOrdersExample.Criteria cr=example.createCriteria();
        cr.andIdEqualTo(id);
        List<TradingOrderGetOrders> list=tradingOrderGetOrdersMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }

    @Override
    public void deleteOrderGetOrders(Long id) throws Exception {
        if(id!=null){
            tradingOrderGetOrdersMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public List<OrderGetOrdersQuery> selectOrderGetOrdersByItemFlag() {
        Map map=new HashMap();
        Page page=new Page();
        page.setCurrentPage(1);
        page.setPageSize(20);
        return orderGetOrdersMapper.selectOrderGetOrdersByAccountFlag(map,page);
    }

    @Override
    public List<OrderGetOrdersQuery> selectOrderGetOrdersByAccountFlag() {
        Map map=new HashMap();
        Page page=new Page();
        page.setCurrentPage(1);
        page.setPageSize(20);
        return orderGetOrdersMapper.selectOrderGetOrdersByAccountFlag(map,page);
    }

    @Override
    public List<TradingOrderGetOrders> selectOrderGetOrdersBySellerTrasactionFlag() {
        TradingOrderGetOrdersExample example=new TradingOrderGetOrdersExample();
        TradingOrderGetOrdersExample.Criteria cr=example.createCriteria();
        cr.andSellertrasactionflagEqualTo(0);
        List<TradingOrderGetOrders> list=tradingOrderGetOrdersMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<TradingOrderGetOrders> selectOrderGetOrdersByTrackNumber() {
        TradingOrderGetOrdersExample example=new TradingOrderGetOrdersExample();
        TradingOrderGetOrdersExample.Criteria cr=example.createCriteria();
        cr.andShipmenttrackingnumberIsNotNull();
        cr.andTrackstatusNotEqualTo("4");
        List<TradingOrderGetOrders> list=tradingOrderGetOrdersMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<TradingOrderGetOrders> selectOrderGetOrdersByCreatedDateAndEbayAcount(Date date,String ebayName) {
        TradingOrderGetOrdersExample example=new TradingOrderGetOrdersExample();
        TradingOrderGetOrdersExample.Criteria cr=example.createCriteria();
        cr.andLastmodifiedtimeLessThan(date);
        cr.andSelleruseridEqualTo(ebayName);
        List<TradingOrderGetOrders> list=tradingOrderGetOrdersMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<TradingOrderGetOrders> selectOrderGetOrdersByAutoMessageId() {
        TradingOrderGetOrdersExample example=new TradingOrderGetOrdersExample();
        TradingOrderGetOrdersExample.Criteria cr=example.createCriteria();
        cr.andAutomessageIdNotEqualTo(0L);
        List<TradingOrderGetOrders> list=tradingOrderGetOrdersMapper.selectByExample(example);
        return list;
    }


}
