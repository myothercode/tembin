<%--
  Created by IntelliJ IDEA.
  User: wula
  Date: 2014/6/22
  Time: 13:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery-1.9.0.min.js" />"></script>
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
</form>
</body>

</html>