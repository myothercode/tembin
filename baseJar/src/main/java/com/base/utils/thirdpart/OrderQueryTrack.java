package com.base.utils.thirdpart;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.DateUtils;
import com.base.utils.httpclient.HttpClientUtil;
import com.base.utils.scheduleabout.MainTask;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.log4j.Logger;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2014/12/16.
 * 查询订单跟踪号
 */
public class OrderQueryTrack {
    static Logger logger = Logger.getLogger(OrderQueryTrack.class);

    public static List<JSONObject>  queryTrack(List<TradingOrderGetOrders> orders) throws Exception {

        Date lastTime=MainTask.taskRunTime.get("91trackTask_ERROR");//上一次执行报错的时间
        if(lastTime!=null){
            int c= DateUtils.minuteBetween(lastTime, new Date());//现在时间与上次时间相差多少分钟
            if(c<120){//间隔两个小时
                return null;
            }
            MainTask.taskRunTime.remove("91trackTask_ERROR");
        }

        CommAutowiredClass autowiredClass= ApplicationContextUtil.getBean(CommAutowiredClass.class);
        String trackNum="";
        for(TradingOrderGetOrders order:orders){
            trackNum+=order.getShipmenttrackingnumber()+",";
        }
        trackNum=trackNum.substring(0,trackNum.length());
        String token=(URLEncoder.encode(autowiredClass.token91Track));
        String url=autowiredClass.url91Track+"?culture=zh-CN&numbers="+trackNum+"&token="+token;
        /*String url="http://api.91track.com/track?culture=en&numbers="+"RD275816257CN"+"&token="+token;*/
        HttpClient httpClient= HttpClientUtil.getHttpClient();

        String res = HttpClientUtil.get(httpClient, url);

        if(StringUtils.isEmpty(res) || "[]".equalsIgnoreCase(res)){
            if("[]".equalsIgnoreCase(res)){
                logger.error(url+"无效token");
            }
            MainTask.taskRunTime.put("91trackTask_ERROR",new Date());
            return null;
        }
        if(res.startsWith("{")){
            res="["+res+"]";
        }
        List<JSONObject> jsonns=new ArrayList<JSONObject>();
        JSONArray jsonArr = null;
        try {
            jsonArr = JSON.parseArray(res);
            for (Object j : jsonArr){
                jsonns.add((JSONObject) j);
                //System.out.println(((JSONObject)j).get("Number"));
            }
            return jsonns;

        } catch (Exception e) {
            MainTask.taskRunTime.put("91trackTask_ERROR", new Date());
            logger.error(url+":OrderQueryTrack:"+res,e);
            //return jsonns;
            return null;
        }

       /* Map<String,String> jsons = null;
        try {
            jsons = JSON.parseObject(res, HashMap.class);
        } catch (Exception e) {
            MainTask.taskRunTime.put("91trackTask_ERROR",new Date());
            logger.error(url+":OrderQueryTrack:"+res,e);
            return "0";
        }
        if("0".equals(jsons.get("Status")) ){
            MainTask.taskRunTime.put("91trackTask_ERROR",new Date());
            logger.error("OrderQueryTrack:"+jsons.get("Error"));
            return "0";
        }

        String st= "0";
        try {
            st = String.valueOf(jsons.get("Status"));
        } catch (Exception e) {
            logger.error(st,e);
            return "0";
        }

        return  st;*/
    }

    public static List<JSONObject>  queryTrack1(List<TradingOrderGetOrders> orders) throws Exception {

        CommAutowiredClass autowiredClass = ApplicationContextUtil.getBean(CommAutowiredClass.class);
        String trackNum = "";
        for (TradingOrderGetOrders order : orders) {
            trackNum += order.getShipmenttrackingnumber() + ",";
        }
        trackNum = trackNum.substring(0, trackNum.length());
        String token = (URLEncoder.encode(autowiredClass.token91Track));
        String url = autowiredClass.url91Track + "?culture=zh-CN&numbers=" + trackNum + "&token=" + token;
        /*String url="http://api.91track.com/track?culture=en&numbers="+"RD275816257CN"+"&token="+token;*/
        HttpClient httpClient = HttpClientUtil.getHttpClient();

        String res = HttpClientUtil.get(httpClient, url);

        if (StringUtils.isEmpty(res) || "[]".equalsIgnoreCase(res)) {
            if ("[]".equalsIgnoreCase(res)) {
                logger.error(url + "无效token");
            }
            MainTask.taskRunTime.put("91trackTask_ERROR", new Date());
            return null;
        }
        if (res.startsWith("{")) {
            res = "[" + res + "]";
        }
        List<JSONObject> jsonns = new ArrayList<JSONObject>();
        JSONArray jsonArr = null;
        try {
            jsonArr = JSON.parseArray(res);
            for (Object j : jsonArr) {
                jsonns.add((JSONObject) j);
                //System.out.println(((JSONObject)j).get("Number"));
            }
            return jsonns;

        } catch (Exception e) {
            MainTask.taskRunTime.put("91trackTask_ERROR", new Date());
            logger.error(url + ":OrderQueryTrack:" + res, e);
            //return jsonns;
            return null;
        }
    }
    public static void main(String[] args) throws Exception {
String res="[{\"Number\":\"9400110200964001883099\",\"Status\":4,\"NotFoundReason\":0,\"DeliveryDate\":\"2014-12-11 16:40:00\",\"Duration\":\"5天5小时\",\"Orginal\":\"美国\",\"OrginalWebSite\":null,\"Destination\":\"美国\",\"DestinationWebSite\":null,\"SupportedLanguages\":[\"zh-cn\",\"en-us\"],\"OriginTracking\":{\"Carrier\":\"美国邮政\",\"Language\":null,\"Events\":[{\"Time\":\"2014-12-11 16:40:00\",\"Location\":null,\"Description\":\"Your item was delivered in or at the mailbox at 4:40 pm on December 11, 2014 in LAS VEGAS, NV 89104.\"},{\"Time\":\"2014-12-11 07:03:00\",\"Location\":null,\"Description\":\"Out for Delivery, December 11, 2014, 7:03 am, LAS VEGAS, NV 89104\"},{\"Time\":\"2014-12-11 06:53:00\",\"Location\":null,\"Description\":\"Sorting Complete, December 11, 2014, 6:53 am, LAS VEGAS, NV 89104\"},{\"Time\":\"2014-12-11 02:06:00\",\"Location\":null,\"Description\":\"Arrived at Post Office, December 11, 2014, 2:06 am, LAS VEGAS, NV 89104\"},{\"Time\":\"2014-12-10 17:56:00\",\"Location\":null,\"Description\":\"Arrived at USPS Facility, December 10, 2014, 5:56 pm, LAS VEGAS, NV 89120\"},{\"Time\":\"2014-12-09 05:55:00\",\"Location\":null,\"Description\":\"Departed USPS Facility, December 9, 2014, 5:55 am, GOLETA, CA 93199\"},{\"Time\":\"2014-12-09 05:24:00\",\"Location\":null,\"Description\":\"Arrived at USPS Origin Facility, December 9, 2014, 5:24 am, GOLETA, CA 93199\"},{\"Time\":\"2014-12-09 04:09:00\",\"Location\":null,\"Description\":\"Accepted at USPS Origin Sort Facility, December 9, 2014, 4:09 am, OXNARD, CA 93030\"},{\"Time\":\"2014-12-06 11:19:00\",\"Location\":null,\"Description\":\"Shipping Label Created, December 6, 2014, 11:19 am, OXNARD, CA 93030\"}]},\"DestTracking\":{\"Carrier\":\"美国邮政\",\"Language\":null,\"Events\":[{\"Time\":\"2014-12-11 16:40:00\",\"Location\":null,\"Description\":\"Your item was delivered in or at the mailbox at 4:40 pm on December 11, 2014 in LAS VEGAS, NV 89104.\"},{\"Time\":\"2014-12-11 07:03:00\",\"Location\":null,\"Description\":\"Out for Delivery, December 11, 2014, 7:03 am, LAS VEGAS, NV 89104\"},{\"Time\":\"2014-12-11 06:53:00\",\"Location\":null,\"Description\":\"Sorting Complete, December 11, 2014, 6:53 am, LAS VEGAS, NV 89104\"},{\"Time\":\"2014-12-11 02:06:00\",\"Location\":null,\"Description\":\"Arrived at Post Office, December 11, 2014, 2:06 am, LAS VEGAS, NV 89104\"},{\"Time\":\"2014-12-10 17:56:00\",\"Location\":null,\"Description\":\"Arrived at USPS Facility, December 10, 2014, 5:56 pm, LAS VEGAS, NV 89120\"},{\"Time\":\"2014-12-09 05:55:00\",\"Location\":null,\"Description\":\"Departed USPS Facility, December 9, 2014, 5:55 am, GOLETA, CA 93199\"},{\"Time\":\"2014-12-09 05:24:00\",\"Location\":null,\"Description\":\"Arrived at USPS Origin Facility, December 9, 2014, 5:24 am, GOLETA, CA 93199\"},{\"Time\":\"2014-12-09 04:09:00\",\"Location\":null,\"Description\":\"Accepted at USPS Origin Sort Facility, December 9, 2014, 4:09 am, OXNARD, CA 93030\"},{\"Time\":\"2014-12-06 11:19:00\",\"Location\":null,\"Description\":\"Shipping Label Created, December 6, 2014, 11:19 am, OXNARD, CA 93030\"}]},\"Extra\":null}]";
        JSONArray jsonArr = JSON.parseArray(res);
        //List<JSONArray> ll= Arrays.asList(jsonArr);
        for (Object j : jsonArr){

            System.out.println(((JSONObject)j).get("Number"));
        }
        String s="";

        /*TradingOrderGetOrders tradingOrderGetOrders=new TradingOrderGetOrders();
        tradingOrderGetOrders.setShipmenttrackingnumber("LK274578216CN");
        queryTrack(tradingOrderGetOrders);*/
    }
}
