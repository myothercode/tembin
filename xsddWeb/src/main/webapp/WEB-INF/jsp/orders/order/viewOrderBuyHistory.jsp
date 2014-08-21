<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/19
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#BuyHistoryListTable").initTable({
                url:path + "/order/ajax/loadBuyHistory.do?orderId=${orderId}",
                columnData:[
                    {title:"图片",name:"pictrue",width:"8%",align:"left",format:makeOption2},
                    {title:"OrderID",name:"orderid",width:"8%",align:"left"},
                    {title:"付款状态",name:"orderstatus",width:"8%",align:"left"},
                    {title:"ebay商品详情",name:"itemUrl",width:"8%",align:"left",format:makeOption3},
                    {title:"买家",name:"buyeruserid",width:"8%",align:"left"},
                    {title:"卖家",name:"selleruserid",width:"8%",align:"left"},
                    {title:"交易总数",name:"countNum",width:"8%",align:"left"}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:true
            });
            refreshTable1();
        });
        /**查看消息*/
        function makeOption1(json){
            var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"viewOrder('"+json.orderid+"');\">查看详情</a>";
            return htm;
        }
        function makeOption2(json){
            var htm="<img src='"+json.pictrue+"' style='width: 50px;hidden=50px;' />";
            return htm;
        }
        function makeOption3(json){
            var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"ebayurl('"+json.itemUrl+"');\">"+json.title+"</a>";
            return htm;
        }
        function refreshTable1(){
            $("#BuyHistoryListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
    </script>
</head>
<body>
<%--<table border="1" cellpadding="0" cellspacing="0">
    <tr>
        <td>订单号 / 源订单号</td>
        <td>物品 / SKU</td>
        <td>卖家</td>
        <td>数量</td>
        <td>价格</td>
        <td>售出日期</td>
        <td>付款日期</td>
        <td>状态</td>
    </tr>
    <c:forEach items="${lists}" var="list">
        <tr>
            <td>${list.orderid}</td>
            <td>
                <tr>
                    <td><img src="${list.pictrue}"/></td>
                    <td><a href="http://www.sandbox.ebay.com/itm/${list.itemUrl}">${list.title}</a><br/>
                        ${lisr.itemid}<br/>${list.sku}
                    </td>
                </tr>
            </td>
            <td>${list.selleruserid}</td>
            <td>${list.quantitypurchased}</td>
            <td>${list.transactionprice}</td>
            <td>${list.lastmodifiedtime}</td>
            <td>${list.paidtime}</td>
            <td>${list.orderstatus}</td>
        </tr>
    </c:forEach>
</table>--%>
<div id="BuyHistoryListTable" ></div>
</body>
</html>
