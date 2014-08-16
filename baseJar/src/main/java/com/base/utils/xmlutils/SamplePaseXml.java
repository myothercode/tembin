package com.base.utils.xmlutils;

import com.base.database.publicd.model.PublicDataDict;
import com.base.database.trading.model.TradingFeedBackDetail;
import com.base.utils.common.DateUtils;
import com.base.utils.common.DictCollectionsUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/7.
 * 解析结构比较简单的xml
 */
public class SamplePaseXml {
    public static String getVFromXmlString(String xml,String nodeName) throws Exception {
        //ByteArrayInputStream is = new ByteArrayInputStream(res.getBytes());//文件
        Document document= DocumentHelper.parseText(xml);
        Element rootElt = document.getRootElement();
        //Iterator iter = rootElt.elementIterator("SessionID");
        Element e =  rootElt.element(nodeName);
        if(e==null){return null;}
        return e.getTextTrim();
    }

    /**
     * 得到反馈信息列表
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static List<TradingFeedBackDetail> getFeedBackListElement(String xml) throws DocumentException {
        List<TradingFeedBackDetail> lifb = new ArrayList();
        Document document= DocumentHelper.parseText(xml);
        Element rootElt = document.getRootElement();
        Element recommend = rootElt.element("FeedbackDetailArray");
        Iterator<Element> iter = recommend.elementIterator("FeedbackDetail");
        while (iter.hasNext()){
            Element element = iter.next();
            TradingFeedBackDetail tfbd = new TradingFeedBackDetail();
            tfbd.setCommentinguser(element.elementText("CommentingUser"));
            tfbd.setCommentinguserscore(Long.parseLong(element.elementText("CommentingUserScore")));
            tfbd.setCommenttext(StringEscapeUtils.escapeXml(element.element("CommentText").getStringValue()));
            tfbd.setCommenttime(DateUtils.returnDate(element.elementText("CommentTime")));
            tfbd.setCommenttype(element.elementText("CommentType"));
            tfbd.setItemid(element.elementText("ItemID"));
            tfbd.setRole(element.elementText("Role"));
            tfbd.setFeedbackid(element.elementText("FeedbackID"));
            tfbd.setTransactionid(element.elementText("TransactionID"));
            tfbd.setOrderlineitemid(element.elementText("OrderLineItemID"));
            tfbd.setItemtitle(element.elementText("ItemTitle"));
            if(element.elementText("ItemPrice")!=null) {
                tfbd.setItemprice(Double.parseDouble(element.elementText("ItemPrice")));
            }
            lifb.add(tfbd);
        }
        return lifb;
    }

    /**将商品目录属性api返回的xml解析为List<PublicDataDict>*/
    public static List<PublicDataDict> getListForPublicDataDict(String xml) throws Exception {
        List<PublicDataDict> publicDataDictList = new ArrayList<PublicDataDict>();
        Document document= DocumentHelper.parseText(xml);
        Element rootElt = document.getRootElement();
        Element recommend = rootElt.element("Recommendations");

        Element cad= recommend.element("CategoryID");
        String categoryID=cad.getTextTrim();

        Iterator<Element> iter = recommend.elementIterator("NameRecommendation");
        while (iter.hasNext()){

            Element element = iter.next();
            String itemId=element.element("Name").getText();
            Iterator<Element> valueIter = element.elementIterator("ValueRecommendation");
            while (valueIter.hasNext()){
                Element element1 = valueIter.next();
                PublicDataDict publicDataDict = new PublicDataDict();
                publicDataDict.setItemId(itemId);
                publicDataDict.setItemParentId(categoryID);
                String val=element1.element("Value").getText();
                publicDataDict.setItemEnName(val);
                publicDataDict.setItemType(DictCollectionsUtil.categorySpecifics);
                publicDataDictList.add(publicDataDict);
            }
        }
        if(publicDataDictList.isEmpty()){
            PublicDataDict publicDataDict = new PublicDataDict();
            publicDataDict.setItemId(DictCollectionsUtil.categorySpecifics+categoryID);
            publicDataDict.setItemParentId(categoryID);
            publicDataDict.setItemEnName("noval");
            publicDataDict.setItemType(DictCollectionsUtil.categorySpecifics);
            publicDataDictList.add(publicDataDict);
        }
       return publicDataDictList;
    }

    /**
     * 获取根需要解析的元素
     */
    public static Element getApiElement(String xml,String nodeName) throws  Exception{
        Document document= DocumentHelper.parseText(xml);
        Element rootElt = document.getRootElement();
        Element root=  rootElt.element(nodeName);
        return root;
    }
    /**
     * 获取指定元素下面的某层指定元素内容
     */
    public static String getSpecifyElementText(Element root,String... firstElement) throws  Exception{
        String text=null;
        String[] nodes=firstElement;
        int length=nodes.length;
        for(int i=0;i<length;i++){
            String node=nodes[i];
            if(i==(length-1)){
                Element last=root.element(node);
                if(last!=null){
                    text=last.getTextTrim();
                }
            }else{
                root=root.element(node);
            }
        }
        return text;
    }
}
