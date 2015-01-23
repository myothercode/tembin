package com.base.utils.itemdescription.impl;

import com.base.database.trading.mapper.TradingItemMapper;
import com.base.database.trading.model.TradingAttrMores;
import com.base.database.trading.model.TradingItemWithBLOBs;
import com.base.database.userinfo.mapper.UsercontrollerUserMapper;
import com.base.database.userinfo.model.SystemLog;
import com.base.database.userinfo.model.UsercontrollerUser;
import com.base.utils.common.EncryptionUtil;
import com.base.utils.common.MyStringUtil;
import com.base.utils.common.SystemLogUtils;
import com.base.utils.common.UUIDUtil;
import com.base.utils.ftpabout.FtpUploadFile;
import com.base.xmlpojo.trading.addproduct.Item;
import com.trading.service.ITradingAttrMores;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by Administrtor on 2015/1/22.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ItemDescriptionImpl implements com.base.utils.itemdescription.IItemDescription {

    static Logger logger = Logger.getLogger(ItemDescriptionImpl.class);
    @Autowired
    private UsercontrollerUserMapper usercontrollerUserMapper;
    @Autowired
    private TradingItemMapper tradingItemMapper;
    @Value("${IMAGE_URL_PREFIX}")
    private String image_url_prefix;
    @Autowired
    private ITradingAttrMores iTradingAttrMores;

    @Override
    public String getPaDescription(String des, TradingItemWithBLOBs tradingItem,Item item){
        try {
            org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(des);
            org.jsoup.select.Elements content = doc.getElementsByAttributeValue("class", "Pa_Box");
            if (content != null && content.size() > 0) {
                for (int i = 0; i < content.size(); i++) {
                    org.jsoup.select.Elements el = content.get(i).getElementsByAttributeValue("class", "Pa_headc");
                    if (el != null && el.size() > 0) {
                        if ("Description".equals(el.get(0).html().toString())) {
                            UsercontrollerUser uu = usercontrollerUserMapper.selectByPrimaryKey(Integer.parseInt(tradingItem.getCreateUser() + ""));
                            org.jsoup.select.Elements imgel = content.get(i).getElementsByTag("img");
                            if (imgel != null && imgel.size() > 0) {
                                for (int j = 0; j < imgel.size(); j++) {
                                    String picUrl = imgel.get(j).attr("src");
                                    if (picUrl.indexOf("?") > 0) {
                                        picUrl = picUrl.substring(0, picUrl.indexOf("?"));
                                    } else {
                                        picUrl = picUrl;
                                    }

                                    URL url = null;
                                    try {
                                        url = new URL(picUrl);
                                    } catch (Exception e) {
                                        logger.error("读取商品描述中的图片信息出错！url:" + picUrl);
                                        continue;
                                    }
                                    //打开链接
                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                    //设置请求方式为"GET"
                                    conn.setRequestMethod("GET");
                                    //超时响应时间为5秒
                                    conn.setConnectTimeout(5 * 1000);
                                    conn.setReadTimeout(5 * 1000);
                                    //通过输入流获取图片数据
                                    InputStream inStream = null;
                                    try {
                                        inStream = conn.getInputStream();
                                    } catch (Exception e) {
                                        logger.error(url + "ebp:", e);
                                        continue;
                                    }
                                    String stuff = MyStringUtil.getExtension(picUrl, "");
                                    String fileName = null;
                                    try {
                                        fileName = FtpUploadFile.ftpUploadFile(inStream, uu.getUserLoginId() + "/" + tradingItem.getSku().toLowerCase(), stuff);
                                    } catch (Exception e) {
                                        inStream.close();
                                        logger.error(url + "", e);
                                        continue;
                                    }
                                    inStream.close();
                                    String picUrls = "";
                                    if (fileName == null) {
                                        picUrls = picUrl;
                                    } else {
                                        picUrls = image_url_prefix + uu.getUserLoginId() + "/" + tradingItem.getSku().toLowerCase() + "/" + fileName;
                                    }
                                    TradingAttrMores tam = new TradingAttrMores();
                                    tam.setParentId(tradingItem.getId());
                                    tam.setParentUuid(tradingItem.getUuid());
                                    tam.setAttrValue("TemplatePicUrl");
                                    tam.setValue(picUrls);
                                    tam.setCreateTime(new Date());
                                    tam.setUuid(UUIDUtil.getUUID());
                                    tam.setAttr1(EncryptionUtil.md5Encrypt(picUrls));
                                    this.iTradingAttrMores.saveAttrMores(tam);
                                }
                            }
                            if (imgel != null && imgel.size() > 0) {
                                for (int j = 0; j < imgel.size(); j++) {
                                    imgel.get(j).remove();
                                }
                            }
                            tradingItem.setDescription(content.get(i).toString());
                            this.tradingItemMapper.updateByPrimaryKeySelective(tradingItem);
                        }else{
                            tradingItem.setDescription("");
                            this.tradingItemMapper.updateByPrimaryKeySelective(tradingItem);
                            saveSystemLog("物品号："+item.getItemID()+";SKU"+item.getSKU(),tradingItem.getCreateUser()+"");
                        }
                    }else{
                        tradingItem.setDescription("");
                        this.tradingItemMapper.updateByPrimaryKeySelective(tradingItem);
                        saveSystemLog("物品号："+item.getItemID()+";SKU"+item.getSKU(),tradingItem.getCreateUser()+"");
                    }
                }
            }else{
                tradingItem.setDescription("");
                this.tradingItemMapper.updateByPrimaryKeySelective(tradingItem);
                saveSystemLog("物品号："+item.getItemID()+";SKU"+item.getSKU(),tradingItem.getCreateUser()+"");
            }
            return "";
        }catch(Exception e){
            return "";
        }
    }

    public void saveSystemLog(String str,String userstr) throws Exception {
        SystemLog sl = new SystemLog();
        sl.setCreatedate(new Date());
        sl.setOperuser(userstr);
        sl.setEventname("ItemDescription");
        sl.setEventdesc(str);
        SystemLogUtils.saveLog(sl);
    }
}
