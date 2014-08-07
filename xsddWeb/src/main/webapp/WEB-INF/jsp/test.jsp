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
            if($(obj).attr("name")=="buyerId"){
                $("#form").attr("action","/xsddWeb/BuyerRequirementDetailsList.do");
            }else if ($(obj).attr("name")=="itemAddr"){
                $("#form").attr("action","/xsddWeb/ItemAddressList.do");
            }else if ($(obj).attr("name")=="payPal"){
                $("#form").attr("action","/xsddWeb/PayPalList.do");
            }else if ($(obj).attr("name")=="returnpolicy") {
                $("#form").attr("action", "/xsddWeb/ReturnpolicyList.do");
            }else if ($(obj).attr("name")=="discountPriceInfo"){
                $("#form").attr("action", "/xsddWeb/discountPriceInfoList.do");
            }else if ($(obj).attr("name")=="descriptionDetails"){
                $("#form").attr("action", "/xsddWeb/DescriptionDetailsList.do");
            }else if ($(obj).attr("name")=="shipping"){
                $("#form").attr("action", "/xsddWeb/shippingDetailsList.do");
            }else if ($(obj).attr("name")=="itemList"){
                $("#form").attr("action", "/xsddWeb/itemList.do");
            }else if ($(obj).attr("name")=="templateinittable"){
                $("#form").attr("action", "/xsddWeb/TemplateInitTableList.do");
            }else if ($(obj).attr("name")=="messagegetmymessage"){
                $("#form").attr("action", "/xsddWeb/MessageGetmymessageList.do");
            }
            $("#form").submit();
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
</form>
</body>
</html>
