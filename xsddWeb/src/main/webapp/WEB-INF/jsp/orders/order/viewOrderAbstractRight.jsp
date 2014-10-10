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
    <style>
        .table-a table{border:1px solid rgba(0, 0, 0, 0.23)
        }
    </style>
    <script type="text/javascript">
        var OrderSendEvaluateMessage;
        function evaluateBuy(){
            var url=path+'/order/initSendEvaluateMessage.do?transactionid=${order.transactionid}&selleruserid=${order.selleruserid}';
            OrderSendEvaluateMessage=$.dialog({title: '评价',
                content: 'url:'+url,
                icon: 'succeed',
                width:800,
                height:500,
                lock:true
            });
        }
    </script>
</head>
<body>
<div class="table-a">
    <table border="0" cellpadding="0" cellspacing="0" style="width: 390px;" >
        <tr><td>来自卖家:☆<a href="javascript:void()" onclick="evaluateBuy();">给买家评价</a></td></tr>
        <tr><td>来自买家:☆</td></tr>
    </table>
</div><br/>
<div class="table-a">
    <table style="width: 390px;">
        <tr><td>eBay 地址:</td></tr>
        <tr><td>${order.street1}</td></tr>
        <tr><td>${order.cityname}</td></tr>
        <tr><td>${order.phone}</td></tr>
        <tr><td>${order.countryname}</td></tr>
        <tr><td>${order.buyeremail}</td></tr>
    </table>
</div><br/>
<div class="table-a">
    <table style="width: 390px;">
        <tr><td>PayPal 地址:</td></tr>
        <tr><td>${order.street1}</td></tr>
        <tr><td>${order.cityname}</td></tr>
        <tr><td>${order.phone}</td></tr>
        <tr><td>${order.countryname}</td></tr>
        <tr><td>${order.buyeremail}</td></tr>
    </table>
</div><br/>
<div class="table-a" style="background: #b9fcff">
    <table style="width: 390px;">
        <tr><td>发货消息:</td></tr>
        <tr><td>${order.street1}</td></tr>
        <tr><td>${order.cityname}</td></tr>
        <tr><td>${order.phone}</td></tr>
        <tr><td>${order.countryname}</td></tr>
        <tr><td>${order.buyeremail}</td></tr>
        <tr><td>------------------------------------------</td></tr>
        <tr><td>
            <c:if test="${order.status=='Complete'}">
                已付款
            </c:if>
            <c:if test="${order.status=='Incomplete'}">
                未付款
            </c:if>
        </td></tr>
    </table>
</div><br/>
</body>
</html>
