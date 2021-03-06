<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/14
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
</head>
<body>
    <c:forEach items="${lists}" var="list">
        <table>
            <tr>
                <td>订单编号:</td>
                <td>${list.orderid}</td>
            </tr>
            <tr>
                <td>商品编号:</td>
                <td>${list.itemid}</td>
            </tr>
            <tr>
                <td>订单状态:</td>
                <td>${list.orderstatus},
                    <c:if test="${list.orderstatus=='Completed'}">
                        已付款
                    </c:if>
                    <c:if test="${list.orderstatus=='Shipped'}">
                        已发货
                    </c:if>
                    <c:if test="${list.orderstatus!='Shipped'&&list.orderstatus!='Completed'}">
                        未付款
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>ebay付款状态:</td>
                <td>${list.ebaypaymentstatus}</td>
            </tr>
            <tr>
                <td>最后修改时间:</td>
                <td>${list.lastmodifiedtime}</td>
            </tr>
            <tr>
                <td>支付方式:</td>
                <td>${list.paymentmethod}</td>
            </tr>
            <tr>
                <td>支付状态:</td>
                <td>${list.status}</td>
            </tr>
             <tr>
                <td>运输是否包括税收:</td>
                <td>${list.shippingincludedintax}</td>
             </tr>
             <tr>
                <td>航运服务:</td>
                <td>${list.shippingservice}</td>
             </tr>
             <tr>
                <td>航运服务优先级:</td>
                <td>${list.shippingservicepriority}</td>
             </tr>
            <tr>
                <td>加快服务:</td>
                <td>${list.expeditedservice}</td>
            </tr>
            <tr>
                <td>销售经理销售记录数:</td>
                <td>${list.sellingmanagersalesrecordnumber}</td>
            </tr>
            <tr>
                <td>签收人:</td>
                <td>${list.name}</td>
            </tr>
            <tr>
                <td>地址1:</td>
                <td>${list.street1}</td>
            </tr>
            <tr>
                <td>城市:</td>
                <td>${list.cityname}</td>
            </tr>
            <tr>
                <td>州或省:</td>
                <td>${list.stateorprovince}</td>
            </tr>
            <tr>
                <td>国家:</td>
                <td>${list.country}</td>
            </tr>
        </table>
    </c:forEach>
</body>
</html>
