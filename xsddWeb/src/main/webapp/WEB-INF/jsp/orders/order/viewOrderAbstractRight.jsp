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
    <style>
        .table-a table{border:1px solid rgba(0, 0, 0, 0.23)
        }
    </style>
</head>
<body>
<div class="table-a">
    <table border="0" cellpadding="0" cellspacing="0" style="width: 390px;" >
        <tr><td>来自卖家:☆给买家评价</td></tr>
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
            <c:if test="${order.orderstatus=='Completed'}">
                已付款
            </c:if>
            <c:if test="${order.orderstatus=='Shipped'}">
                已发货
            </c:if>
            <c:if test="${order.orderstatus=='CancelPending'}">
                取消挂起
            </c:if>
            <c:if test="${order.orderstatus!='Completed'}">
                <c:if test="${order.orderstatus!='Shipped'}">
                    <c:if test="${order.orderstatus!='CancelPending'}">
                        未付款
                    </c:if>
                </c:if>
            </c:if>
        </td></tr>
    </table>
</div><br/>
</body>
</html>
