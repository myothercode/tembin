package com.base.utils.scheduleabout.commontask;

import com.base.database.keymove.mapper.KeyMoveListMapper;
import com.base.database.keymove.model.KeyMoveList;
import com.base.database.keymove.model.KeyMoveListExample;
import com.base.database.trading.mapper.TradingListingDataMapper;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.base.xmlpojo.trading.addproduct.Item;
import com.trading.service.ITradingItem;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/29.
 * 定时刊登任务
 */
public class ListingItemDataTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(ListingItemDataTaskRun.class);


    public String getCosXml(String ebayName,String startTime,String endTime,int pageNumber){
        String colStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetSellerListRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**vVcRVA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**Z2MB0OtmO4JsPFBZPcjclippjnZG4bwpcpXYRXDdc6wEppv5m/WiCvsjyTKWoVXCMxQl2se3U6Vn93oBL6zg8EcR3GCXCC3ZbTpEQ3lBX8avBrME9VHo0RcfcE7oLVtnBAuSffy3Dk5ICUNyU7g57/rHw8d5DnO3JeitpQcTLKAInt+sEZslri3wa4Mx0xgyFW5OF3w8mNK8ib8+57PTHcApnp8xRTAlIVuwW3F/fGbSFVReS07/MulzlFXBoW/ZPLq+L2aLFpn5s+IB5/gB0HoDo5uGzRnALmXxUz8BuwJMrUE29830W7xVSEaYSYsOcJyue6PjJKyZt0rXf8TNHusXCHX240dWllrjMVxS7pEHgKb/FKfd/79PH3rXTFmuexesXS6H1lRmHBBE1iknFwtzzS+UeN22Rd6W+hjSjuOHB33o2gMS5cOdVXHuHyOQ6VJU3bJL/eNDgyB+wz3HhZmz6sF+lmLIRKP82H1QXdlwdGdpVhAhyqnE4FH4qTgPBMxv6c4jRL5BRuyUZDLeJI1WXmaZ4pNMss+MiME7Qu+7bP7S2TZhmValbfW/FvqSrxR9LlHji7iQSsz2m56x5TLjOtkFWjRxmB6C1wzBVtzdILzbvmA/1+9RlMevalW6bg22irusiv7iuD/AnC9pZ0Sju2XK/7WpjVW4/lZyBmRbqHQJPbU/5MU3xrM8pTV8rZmPfQrRh2araaWGIBE5IW3gsTrETpRUQybXd/a107ee61GwXEUqVat1EfznFpIs</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "<Pagination ComplexType=\"PaginationType\">\n" +
                "\t<EntriesPerPage>100</EntriesPerPage>\n" +
                "\t<PageNumber>"+pageNumber+"</PageNumber>\n" +
                "</Pagination>\n" +
                "<StartTimeFrom>"+startTime+"</StartTimeFrom>\n" +
                "<StartTimeTo>"+endTime+"</StartTimeTo>\n" +
                "<UserID>"+ebayName+"</UserID>\n" +
                "<IncludeWatchCount>true</IncludeWatchCount>\n" +
                "<DetailLevel>ReturnAll</DetailLevel>\n" +
                "</GetSellerListRequest>​";
        return colStr;
    }

    public void saveListingData(String cosXml,String ebayAccount,Long userid,int pages){
        UserInfoService userInfoService = (UserInfoService) ApplicationContextUtil.getBean(UserInfoService.class);
        TradingListingDataMapper tldm = (TradingListingDataMapper) ApplicationContextUtil.getBean(TradingListingDataMapper.class);
        try {
            UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(userid);
            d.setApiSiteid("0");
            d.setApiCallName(APINameStatic.ListingItemList);
            AddApiTask addApiTask = new AddApiTask();
            Map<String, String> resMap= addApiTask.exec(d, cosXml, "https://api.sandbox.ebay.com/ws/api.dll");
            String res=resMap.get("message");
            String ack = SamplePaseXml.getVFromXmlString(res,"Ack");
            if(ack.equals("Success")){
                Document document= DocumentHelper.parseText(res);
                Element rootElt = document.getRootElement();
                Element totalElt = rootElt.element("PaginationResult");
                String totalCount = totalElt.elementText("TotalNumberOfEntries");
                String page =  totalElt.elementText("TotalNumberOfPages");
                List<TradingListingData> litld = SamplePaseXml.getItemListElememt(res,ebayAccount);
                for(TradingListingData td : litld){
                    td.setEbayAccount(ebayAccount);
                    TradingListingDataExample tlde  = new TradingListingDataExample();
                    tlde.createCriteria().andItemIdEqualTo(td.getItemId());
                    List<TradingListingData> litl = tldm.selectByExample(tlde);
                    if(litl!=null&&litl.size()>0){
                        TradingListingData oldtd = litl.get(0);
                        td.setId(oldtd.getId());
                        tldm.updateByPrimaryKeySelective(td);
                    }else{
                        tldm.insertSelective(td);
                    }
                }
                if(pages!=Integer.parseInt(page)){
                    if(pages==1&&Integer.parseInt(totalCount)>100){
                        pages=2;
                    }
                    for(int i=pages;i<=Integer.parseInt(page);i++){
                        String colStr = this.getCosXml(ebayAccount,"2014-06-06T16:15:12.000Z","2014-09-23T18:15:12.000Z",i);
                        this.saveListingData(colStr,ebayAccount,userid,i);
                    }
                }



            }else{

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    @Override
    public void run() {
        UsercontrollerEbayAccountMapper ueam = (UsercontrollerEbayAccountMapper) ApplicationContextUtil.getBean(UsercontrollerEbayAccountMapper.class);
        UsercontrollerEbayAccountExample ueame = new UsercontrollerEbayAccountExample();
        ueame.createCriteria();
        List<UsercontrollerEbayAccount> liue = ueam.selectByExample(ueame);
        for(int i =0 ;i<liue.size();i++) {
            UsercontrollerEbayAccount ue = liue.get(i);
            String colStr = this.getCosXml(ue.getEbayName(),"2014-06-06T16:15:12.000Z","2014-09-23T18:15:12.000Z",1);
            this.saveListingData(colStr,ue.getEbayName(),ue.getUserId(),1);
        }
    }

    /**只从集合记录取多少条*/
    private List<TradingTimerListingWithBLOBs> filterLimitList(List<TradingTimerListingWithBLOBs> tlist){

        List<TradingTimerListingWithBLOBs> x=new ArrayList<TradingTimerListingWithBLOBs>();
        for (int i = 0;i<20;i++){
            x.add(tlist.get(i));
        }
        return x;
    }

    public ListingItemDataTaskRun(){
    }

    @Override
    public String getScheduledType() {
        return MainTask.LISTING_DATA;
    }
}
