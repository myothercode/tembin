<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/30
  Time: 16:46
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
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/default/easyui.css" />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/icon.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/jquery-easyui/jquery.easyui.min.js" /> ></script>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        function urlpath(){
            window.top.location.href="${url}";
        }
        function dialogClose(){
            W.userCases.close();
        }
    </script>
</head>
<body>
<div class="easyui-panel"  style="width:1000px;height: 120px;" title="付款状态:${transaction.paymentstatus}" >
地址:${order.street1}<br/>${order.cityname}<br/>${order.postalcode}<br/>${order.countryname}
</div>
<br/>
PayPal 交易号(ID #${transaction.externaltransactionid})<br/>
<div>
    <table border="1" cellspacing="0" cellpadding="0" style="width: 1000px;">
        <tr>
            <td>交易号</td>
            <td>日期</td>
            <td>状态</td>
            <td>总额</td>
            <td>费用金额</td>
            <td>净额</td>
        </tr>
        <tr>
            <td>${transaction.externaltransactionid}</td>
            <td><fmt:formatDate value="${order.paidtime}" pattern="yyyy-MM-dd HH:mm"/></td>
            <td>${transaction.paymentstatus}</td>
            <td>${order.total}</td>
            <td>-</td>
            <td>-</td>
        </tr>
    </table>
</div><br/>
销售编号:-<br/>
<table border="1" cellspacing="0" cellpadding="0" style="width: 1000px;">
    <tr>
        <td>物品号</td>
        <td>详情</td>
        <td>数量</td>
        <td>价格</td>
        <td>金额</td>
    </tr>
    <tr>
        <td><img src="${pic}" style="width: 50px;height: 50px;"></td>
        <td><a href="void()" onclick="urlpath();">${order.title}</a></td>
        <td>${order.quantitypurchased}</td>
        <td>${order.transactionprice}</td>
        <td>${order.amountpaid}</td>
    </tr>
    <tr>
        <td colspan="5" style="text-align: right">总额:${order.total}</td>
    </tr>
</table><br/>
<div >
    <table align="center" style="width: 300px;">
        <tr>
            <td>买家:</td>
            <td>${order.buyeruserid}</td>
        </tr>
        <tr>
            <td>电邮:</td>
            <td>${order.buyeremail}</td>
        </tr>
        <tr>
            <td>接收电邮:</td>
            <td>${order.selleremail}</td>
        </tr>
    </table>
</div>
<hr/>
<div >
    <table align="center" style="width: 300px;">
        <tr>
            <td>总额:</td>
            <td>${order.total}</td>
        </tr>
        <tr>
            <td>费用金额:</td>
            <td>-</td>
        </tr>
        <tr>
            <td>净额:</td>
            <td>-</td>
        </tr>
        <tr>
            <td>日期:</td>
            <td><fmt:formatDate value="${order.paidtime}" pattern="yyyy-MM-dd HH:mm"/></td>
        </tr>
        <tr>
            <td>付款状态:</td>
            <td>${order.status}</td>
        </tr>
    </table>
</div>
<hr/>
<table align="center" style="width: 300px;">
    <tr>
        <td>留言:</td>
        <td></td>
    </tr>

</table>
<div align="right">
    <input type="button" value="确定" onclick="dialogClose();"/>
    &nbsp;
    <input type="button" value="关闭" onclick="dialogClose();"/>
</div>
</body>
</html>
