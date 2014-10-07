<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/19
  Time: 10:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script type="text/javascript">
        function ebayurl(){
            window.top.location.href="http://www.sandbox.ebay.com/itm/${order.itemid}";
        }
    </script>
</head>
<body>
交易信息:
<hr/>
<table>
    <tr>
        <td colspan="2"><a onclick="ebayurl();" href="void()">${order.title}</a></td>
    </tr>
    <tr>
        <td>Item No.:</td>
        <td>${order.itemid}</td>
    </tr>
    <tr>
        <td>售出时间:</td>
        <td><fmt:formatDate value="${order.lastmodifiedtime}" pattern="yyyy-MM-dd HH:mm"/></td>
    </tr>
    <tr>
        <td>销售数量:</td>
        <td>${order.quantitypurchased}</td>
    </tr>
    <tr>
        <td>售价:</td>
        <td>${order.transactionprice}</td>
    </tr>
    <tr>
        <td>成交费:</td>
        <td>${grossdetailamount}</td>
    </tr>
    <tr>
        <td>SKU:</td>
        <td>${order.sku}</td>
    </tr>
         <tr>
            <td>买家选择运输:</td>
            <td>
                ${order.selectedshippingservice}
            </td>
        </tr>
    <tr>
        <td>买家选择运输费用:</td>
        <td>${order.selectedshippingservicecost}</td>
    </tr>
    <tr>
        <td>保险费:</td>
        <td></td>
    </tr>
    <%--<tr><td>${order.quantitypurchased}</td></tr>
    <tr><td>${order.quantitypurchased}</td></tr>
    <tr><td>${order.quantitypurchased}</td></tr>
    <tr><td>${order.quantitypurchased}</td></tr>
    <tr><td>${order.quantitypurchased}</td></tr>--%>
    <tr>
        <td>付款状态:</td>
        <td>
            <c:if test="${order.status=='Complete'}">
                 已付款
            </c:if>
            <c:if test="${order.status=='Incomplete'}">
                  未付款
            </c:if>
        </td>
    </tr>
    <tr>
        <td>付款方式:</td>
        <td>${order.paymentmethod}</td>
    </tr>
</table><br/>
PayPal 付款:
<hr/>
<table style="text-align: center" border="1" cellspacing="0" cellpadding="0">
    <tr>
        <td>PayPal 交易号</td>
        <td>付款日期</td>
        <td>状态</td>
        <td>总额</td>
        <td>PayPal 费用</td>
        <td>净额</td>
    </tr>
    <c:if test="${order.status=='Complete'}">
        <td>${paypal}</td>
        <td><fmt:formatDate value="${order.paidtime}" pattern="yyyy-MM-dd HH:mm"/></td>
        <td>${order.orderstatus}</td>
        <td>${order.total}</td>
        <td>-</td>
        <td>-</td>
    </c:if>
</table><br/>
摘要
<hr/>
<table style="text-align: center" border="1" cellspacing="0" cellpadding="0">
    <tr>
        <td>&nbsp; </td>
        <td>收到付款</td>
        <td>刊登费</td>
        <td>成交费</td>
        <td>PayPal 费用</td>
        <td>P&P</td>
        <td>物品成本</td>
        <td>毛利</td>
    </tr>
    <tr>
        <td>USD</td>
        <td>${order.amountpaid}</td>
        <td>-</td>
        <td>${grossdetailamount}</td>
        <td>-</td>
        <td>-</td>
        <td>${order.actualhandlingcost}</td>
        <td>-</td>
    </tr>


</table>
</body>
</html>
