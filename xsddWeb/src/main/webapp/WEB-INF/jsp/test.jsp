<%--
  Created by IntelliJ IDEA.
  User: wula
  Date: 2014/6/22
  Time: 13:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script>
        function submitForm(obj){
            var name=$(obj).attr("name");
            var map=new Map();
            map.put("buyerId","/xsddWeb/BuyerRequirementDetailsList.do");
            map.put("itemAddr","/xsddWeb/ItemAddressList.do");
            map.put("payPal","/xsddWeb/PayPalList.do");
            map.put("returnpolicy","/xsddWeb/ReturnpolicyList.do");
            map.put("discountPriceInfo","/xsddWeb/discountPriceInfoList.do");
            map.put("descriptionDetails","/xsddWeb/DescriptionDetailsList.do");
            map.put("itemList","/xsddWeb/itemList.do");
            map.put("templateinittable","/xsddWeb/TemplateInitTableList.do");
            map.put("messagegetmymessage",path+"/message/MessageGetmymessageList.do");
            map.put("bindEbayAccount",path+"/user/bindEbayAccount.do");
            map.put("shipping",path+"/shippingDetailsList.do");
            map.put("orders",path+"/order/getOrdersList.do");
            map.put("orderItem",path+"/orderItem/orderItemList.do");
            top.location=map.get(name);

        }
        function abcd(obj){
            var urll=path+"/ajax/saveFeedBackAll.do";
            $().invoke(
                    urll,
                    {},
                    [function(m,r){
                        alert(r)
                    },
                        function(m,r){
                            alert(r)
                        }]

            );
        }
    </script>
</head>
<body>
<form id="form" action="">
    ${ccc}llsss
        <div><input type="button" value="买家要求" name="buyerId" onclick="submitForm(this)"/></div>
        <div><input type="button" value="物品所在地" name="itemAddr" onclick="submitForm(this)"/></div>
        <div><input type="button" value="付款选项" name="payPal" onclick="submitForm(this)"/></div>
        <div><input type="button" value="退还选项" name="returnpolicy" onclick="submitForm(this)"/></div>
        <div><input type="button" value="折扣" name="discountPriceInfo" onclick="submitForm(this)"/></div>
        <div><input type="button" value="运输选项" name="shipping" onclick="submitForm(this)"/></div>
        <div><input type="button" value="卖家描述" name="descriptionDetails" onclick="submitForm(this)"/></div>
        <div><input type="button" value="商品列表" name="itemList" onclick="submitForm(this)"/></div>
        <div><input type="button" value="模板" name="templateinittable" onclick="submitForm(this)"/></div>
        <div><input type="button" value="接受消息" name="messagegetmymessage" onclick="submitForm(this)"/></div>
        <div><input type="button" value="绑定ebay帐号" name="bindEbayAccount" onclick="submitForm(this)"/></div>
        <div><input type="button" value="订单管理" name="orders" onclick="submitForm(this)"/></div>
        <div><input type="button" value="订单商品" name="orderItem" onclick="submitForm(this)"/></div>
        <div><input type="button" value="获取商品反馋嘴" name="abcds" onclick="abcd(this)"/></div>
</form>
</body>
</html>
