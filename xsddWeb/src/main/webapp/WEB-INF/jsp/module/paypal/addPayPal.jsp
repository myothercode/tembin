<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery-1.9.0.min.js" />"></script>
<html>
<head>
    <title></title>
</head>
<c:set value="${paypal}" var="paypal" />
<body>
<form action="/xsddWeb/savePayPal.do">
    <table>
        <tr>
            <td>名称:</td>
            <td>
                <input type="hidden" name="id" id="id" value="${paypal.id}">
                <input type="text" name="name" id="name" value="${paypal.payName}"></td>
        </tr>
        <tr>
            <td>站点:</td>
            <td>
                <select name="site">
                    <c:forEach items="${siteList}" var="sites">
                        <c:if test="${paypal.site==sites.id}">
                            <option value="${sites.id}" selected="selected">${sites.name}</option>
                        </c:if>
                        <c:if test="${paypal.site!=sites.id}">
                            <option value="${sites.id}">${sites.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <td>paypal账号:</td>
        <td>
            <select name="paypal">
                <c:forEach items="${paypalList}" var="pay">
                    <c:if test="${paypal.paypal==pay.id}">
                        <option value="${pay.id}" selected="selected">${pay.configName}</option>
                    </c:if>
                    <c:if test="${paypal.paypal!=pay.id}">
                        <option value="${pay.id}">${pay.configName}</option>
                    </c:if>
                </c:forEach>
            </select>
        </td>
        </tr>
        </tr>
        <td>付款说明:</td>
        <td>
            <textarea name="paypal_desc" cols="30" rows="5">${paypal.paymentinstructions}</textarea>
            <div><input type="submit" value="确定"/></div>
        </td>
        </tr>
    </table>
</form>
</body>
</html>
