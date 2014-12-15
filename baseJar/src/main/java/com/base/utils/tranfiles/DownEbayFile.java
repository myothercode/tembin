package com.base.utils.tranfiles;

import com.base.database.publicd.mapper.PublicDataDictMapper;
import com.base.database.publicd.model.PublicDataDict;
import com.base.database.publicd.model.PublicDataDictExample;
import com.base.database.userinfo.mapper.PublicDataDictCategorySpecificsMapper;
import com.base.database.userinfo.model.PublicDataDictCategorySpecifics;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.DictCollectionsUtil;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;
import org.dom4j.io.SAXReader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2014/12/10.
 * 下载ebay的数据文件，比如属性
 */
public class DownEbayFile {

    /*public static String urll="https://storage.sandbox.ebay.com/FileTransferService";
    private static List<BasicHeader> headers = new ArrayList<BasicHeader>();
    static{
        headers.add(new BasicHeader("CONTENT-TYPE", "Payload Type: XML"));
        headers.add(new BasicHeader("X-EBAY-SOA-OPERATION-NAME", "downloadFile"));
        headers.add(new BasicHeader("X-EBAY-SOA-SECURITY-TOKEN", "AgAAAA**AQAAAA**aAAAAA**vVcRVA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**Z2MB0OtmO4JsPFBZPcjclippjnZG4bwpcpXYRXDdc6wEppv5m/WiCvsjyTKWoVXCMxQl2se3U6Vn93oBL6zg8EcR3GCXCC3ZbTpEQ3lBX8avBrME9VHo0RcfcE7oLVtnBAuSffy3Dk5ICUNyU7g57/rHw8d5DnO3JeitpQcTLKAInt+sEZslri3wa4Mx0xgyFW5OF3w8mNK8ib8+57PTHcApnp8xRTAlIVuwW3F/fGbSFVReS07/MulzlFXBoW/ZPLq+L2aLFpn5s+IB5/gB0HoDo5uGzRnALmXxUz8BuwJMrUE29830W7xVSEaYSYsOcJyue6PjJKyZt0rXf8TNHusXCHX240dWllrjMVxS7pEHgKb/FKfd/79PH3rXTFmuexesXS6H1lRmHBBE1iknFwtzzS+UeN22Rd6W+hjSjuOHB33o2gMS5cOdVXHuHyOQ6VJU3bJL/eNDgyB+wz3HhZmz6sF+lmLIRKP82H1QXdlwdGdpVhAhyqnE4FH4qTgPBMxv6c4jRL5BRuyUZDLeJI1WXmaZ4pNMss+MiME7Qu+7bP7S2TZhmValbfW/FvqSrxR9LlHji7iQSsz2m56x5TLjOtkFWjRxmB6C1wzBVtzdILzbvmA/1+9RlMevalW6bg22irusiv7iuD/AnC9pZ0Sju2XK/7WpjVW4/lZyBmRbqHQJPbU/5MU3xrM8pTV8rZmPfQrRh2araaWGIBE5IW3gsTrETpRUQybXd/a107ee61GwXEUqVat1EfznFpIs"));
        //headers.add(new BasicHeader("X-EBAY-SOA-REQUEST-DATA-FORMAT", "XML"));

    }

    public static void postit() throws Exception {
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<downloadFileRequest xmlns:=\"http://www.ebay.com/marketplace/services\">" +
                "<taskReferenceId>50011849678</taskReferenceId>" +
                "<fileReferenceId>50012089878</fileReferenceId>" +
                "</downloadFileRequest>";
        HttpClient httpClient = HttpClientUtil.getHttpsClient();
        String res= HttpClientUtil.post(httpClient, urll, xml, "UTF-8", headers);
        System.out.println(res);

    }

    public static void main(String[] args) throws Exception {
        postit();
    }*/


    public static void main(String[] args) throws Exception {
        SAXReader reader = new SAXReader();
        reader.addHandler("/GetCategorySpecificsResponse/Recommendations",new MyElementHandler());
        /*reader.setDefaultHandler(new ElementHandler(){
            int i=0;
            public void onEnd(ElementPath ep) {
                Element e = ep.getCurrent(); //获得当前节点
                String nodeName=e.getName();
String v=e.getText();
                if("Recommendations".equalsIgnoreCase(nodeName)){
                    try {

                        System.out.println(e.getName()+":"+i);

                        //List<PublicDataDict> ll = getListForPublicDataDict(e);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                i++;
                e.detach(); //记得从内存中移去
            }
            public void onStart(ElementPath arg0) {
            }
        });*/
        Document document=reader.read(new BufferedInputStream(new FileInputStream(new File("E:\\share\\xml\\0.xml"))));

    }
}
class MyElementHandler implements ElementHandler {
    static Logger logger = Logger.getLogger(MyElementHandler.class);
    public void onStart(ElementPath ep) {}

    public void onEnd(ElementPath ep) {
        Element e = ep.getCurrent(); //获得当前节点
        String nodeName=e.getName();
        String siteId="311";
        String categoryID=e.element("CategoryID").getText();

        List<Element> nrs=e.elements("NameRecommendation");
        if(nrs==null){return;}
        for (Element nr :nrs){
            if(nr==null){continue;}
            String name=nr.element("Name").getText();

            Element ruleE=nr.element("ValidationRules");
            String maxV=(ruleE.element("MaxValues"))==null?"1":(ruleE.element("MaxValues").getText());
            String minV=(ruleE.element("MinValues"))==null?"0":(ruleE.element("MinValues").getText());
            String vSpecifics=(ruleE.element("VariationSpecifics"))==null?"Disabled":(ruleE.element("VariationSpecifics").getText());
            String SelectionMode=(ruleE.element("SelectionMode"))==null?"FreeText":(ruleE.element("SelectionMode").getText());


            List<Element> vrs=nr.elements("ValueRecommendation");

        /*PublicDataDictMapper pm= ApplicationContextUtil.getBean(PublicDataDictMapper.class);
        PublicDataDict p=new PublicDataDict();
        //p.setItemId(categoryID);
        p.setMaxV(maxV);
        p.setMinV(minV);
        p.setSelectionMode(SelectionMode);
        p.setVariationSpecifics(vSpecifics);
        PublicDataDictExample pe=new PublicDataDictExample();
        pe.createCriteria().andItemIdEqualTo(categoryID).andSiteIdEqualTo(siteId);
        pm.updateByExampleSelective(p,pe);//更新目录列表中的数据，添加属性*/
            //List<PublicDataDictCategorySpecifics>  lll=new ArrayList<PublicDataDictCategorySpecifics>();
            PublicDataDictCategorySpecificsMapper pdap=ApplicationContextUtil.getBean(PublicDataDictCategorySpecificsMapper.class);
            for (Element e1:vrs){
                String value=e1.element("Value").getText();
                PublicDataDictCategorySpecifics publicDataDict = new PublicDataDictCategorySpecifics();
                publicDataDict.setItemEnName(value);
                publicDataDict.setItemId(name);
                publicDataDict.setItemParentId(categoryID);
                publicDataDict.setItemType(DictCollectionsUtil.categorySpecifics);
                publicDataDict.setSiteId(siteId);
                publicDataDict.setMaxV(maxV);
                publicDataDict.setMinV(minV);
                publicDataDict.setSelectionMode(SelectionMode);
                publicDataDict.setVariationSpecifics(vSpecifics);
                try {
                    pdap.insert(publicDataDict);
                } catch (Exception e2) {
                    logger.error("",e2);
                    continue;
                }
                //lll.add(publicDataDict);
            }
        }

        e.detach(); //记得从内存中移去
    }


}