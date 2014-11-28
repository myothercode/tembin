package com.publicd.service.impl;

import com.base.database.customtrading.mapper.ItemInformationMapper;
import com.base.database.publicd.mapper.PublicItemInformationMapper;
import com.base.database.publicd.model.*;
import com.base.domains.querypojos.ItemInformationQuery;
import com.base.mybatis.page.Page;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.publicd.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 退货政策
 * Created by lq on 2014/7/29.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PublicItemInformationImpl implements com.publicd.service.IPublicItemInformation {

    @Autowired
    private PublicItemInformationMapper PublicItemInformationMapper;
    @Autowired
    private ItemInformationMapper ItemInformationMapper;

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


    @Override
    public void saveItemInformation(PublicItemInformation ItemInformation) throws Exception {
        if(ItemInformation.getId()==null){
            ObjectUtils.toInitPojoForInsert(ItemInformation);
            PublicItemInformationMapper.insertSelective(ItemInformation);
        }else{
            PublicItemInformation t=PublicItemInformationMapper.selectByPrimaryKey(ItemInformation.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),PublicItemInformationMapper.class,ItemInformation.getId(),"Synchronize");
            PublicItemInformationMapper.updateByPrimaryKeySelective(ItemInformation);
        }
    }
    @Override
    public List<ItemInformationQuery> selectItemInformation(Map map, Page page) {
        return ItemInformationMapper.selectItemInformation(map,page);
    }

    @Override
    public void deleteItemInformation(Long id) throws Exception {
        if(id!=null){
            PublicItemInformationMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public PublicItemInformation selectItemInformationByid(Long id) {
        PublicItemInformationExample example=new PublicItemInformationExample();
        PublicItemInformationExample.Criteria cr=example.createCriteria();
        cr.andIdEqualTo(id);
        List<PublicItemInformation> list=PublicItemInformationMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }

    @Override
    public List<ItemInformationQuery> selectItemInformationByType(Map map, Page page) {
        return ItemInformationMapper.selectItemInformationByType(map,page);
    }

    @Override
    public PublicItemInformation selectItemInformationBySKU(String sku) {
        PublicItemInformationExample example=new PublicItemInformationExample();
        PublicItemInformationExample.Criteria cr=example.createCriteria();
        cr.andSkuEqualTo(sku);
        List<PublicItemInformation> list=PublicItemInformationMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }

    @Override
    public void exportItemInformation(List<PublicItemInformation> list,String outputFile,ServletOutputStream outputStream) throws Exception {
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
        // 定义单元格为字符串类型
        cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell8.setCellType(HSSFCell.CELL_TYPE_STRING);
        // 在单元格中输入头数据
        cell1.setCellValue("商品名称");
        cell2.setCellValue("商品SKU");
        cell3.setCellValue("长");
        cell4.setCellValue("宽");
        cell5.setCellValue("高");
        cell6.setCellValue("重量");
        cell7.setCellValue("销售价");
        cell8.setCellValue("描述");
        for(int i=0;i<list.size();i++){
            row = sheet.createRow(i+1);
            String[] cells=new String[8];
            cells[0]=list.get(i).getName();
            cells[1]=list.get(i).getSku();
            if(list.get(i).getInventoryId()!=null){
                PublicItemInventory inventory=iPublicItemInventory.selectItemInventoryByid(list.get(i).getInventoryId());
                cells[2]=inventory.getLength()+"";
                cells[3]=inventory.getWidth()+"";
                cells[4]=inventory.getHeight()+"";
            }else{
                cells[2]="";
                cells[3]="";
                cells[4]="";
            }
            if(list.get(i).getCustomId()!=null){
                PublicItemCustom custom=iPublicItemCustom.selectItemCustomByid(list.get(i).getCustomId());
                cells[5]=custom.getWeight()+"";
            }else{
                cells[5]="";
            }
            if(list.get(i).getSupplierId()!=null){
                PublicItemSupplier supplier=iPublicItemSupplier.selectItemSupplierByid(list.get(i).getSupplierId());
                cells[6]=supplier.getPrice()+"";
            }else{
                cells[6]="";
            }
            if(StringUtils.isNotBlank(list.get(i).getDescription())){
                cells[7]=list.get(i).getDescription();
            }else{
                cells[7]="";
            }
            for(int j=0;j<cells.length;j++){
                HSSFCell cell = row.createCell(j);// 第一列
                // 设置单元格格式
                cell.setCellStyle(cellStyle);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(cells[j]);
            }
        }
       /* File path=new File(outputFile);
        File paren=path.getParentFile();
        if(!paren.exists()){
            paren.mkdirs();
        }
        if(path.exists()){
            path.delete();
        }
        // 新建一输出文件流
        FileOutputStream fOut = new FileOutputStream(outputFile);*/
        // 把相应的Excel 工作簿存盘
        workbook.write(outputStream);
        // 操作结束，关闭文件
        outputStream.flush();
        outputStream.close();
    }

    @Override
    public void importItemInformation(File file) throws Exception {
        InputStream inputStream=new FileInputStream(file);
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        int total = workbook.getNumberOfSheets();
        for(int i=0;i<total;i++){
            HSSFSheet sheet = workbook.getSheetAt(i);
            for(int j=1;j<=sheet.getLastRowNum();j++){
                PublicItemInformation itemInformation=new PublicItemInformation();
                HSSFRow row=sheet.getRow(j);
                String name="";
                String sku="";
                String length="";
                String width="";
                String height="";
                String weight="";
                String price="";
                String discription="";
                if(row.getCell(0)!=null){
                   name=row.getCell(0).toString();
                }
                if(row.getCell(1)!=null){
                   sku=row.getCell(1).toString();
                }
                if(row.getCell(2)!=null){
                    length=row.getCell(2).toString();
                }
                if(row.getCell(3)!=null){
                    width=row.getCell(3).toString();
                }
                if(row.getCell(4)!=null){
                    height=row.getCell(4).toString();
                }
                if(row.getCell(5)!=null){
                    weight=row.getCell(5).toString();
                }
                if(row.getCell(6)!=null){
                    price=row.getCell(6).toString();
                }
                if(row.getCell(7)!=null){
                    discription=row.getCell(7).toString();
                }
                /*String inventory=row.getCell(6).toString();
                String custom=row.getCell(7).toString();*/
                PublicItemInformation itemInformation1=selectItemInformationBySKU(sku);
                if(itemInformation1!=null){
                    continue;
                }else{
                    if(StringUtils.isNotBlank(name)){
                        itemInformation.setName(name);
                    }
                    if(StringUtils.isNotBlank(sku)){
                        itemInformation.setSku(sku);
                    }
                    if(StringUtils.isNotBlank(length)||StringUtils.isNotBlank(width)||StringUtils.isNotBlank(height)){
                        PublicItemInventory inventory=new PublicItemInventory();
                        if(StringUtils.isNotBlank(length)){
                            inventory.setLength(Double.valueOf(length));
                        }
                        if(StringUtils.isNotBlank(width)){
                            inventory.setLength(Double.valueOf(width));
                        }
                        if(StringUtils.isNotBlank(height)){
                            inventory.setLength(Double.valueOf(height));
                        }
                        iPublicItemInventory.saveItemInventory(inventory);
                        itemInformation.setInventoryId(inventory.getId());
                    }
                    if(StringUtils.isNotBlank(weight)){
                        PublicItemCustom custom=new PublicItemCustom();
                        custom.setWeight(Double.valueOf(weight));
                        iPublicItemCustom.saveItemCustom(custom);
                        itemInformation.setCustomId(custom.getId());
                    }
                    if(StringUtils.isNotBlank(price)){
                        PublicItemSupplier supplier=new PublicItemSupplier();
                        supplier.setPrice(Double.valueOf(price));
                        iPublicItemSupplier.saveItemSupplier(supplier);
                        itemInformation.setSupplierId(supplier.getId());
                    }
                    if(StringUtils.isNotBlank(discription)){
                      /*  discription.replaceAll("  ","<br/>");*/
                        itemInformation.setDescription(discription);
                    }
                    saveItemInformation(itemInformation);
                }

               /* for(int x=0;x<8;x++){

                }*/
            }
        }
    }

    @Override
    public List<PublicItemInformation> selectItemInformationByTypeIsNull() {
        PublicItemInformationExample example=new PublicItemInformationExample();
        PublicItemInformationExample.Criteria cr=example.createCriteria();
        cr.andTypeflagEqualTo(0);
        List<PublicItemInformation> list=PublicItemInformationMapper.selectByExample(example);
        return list;
    }
}
