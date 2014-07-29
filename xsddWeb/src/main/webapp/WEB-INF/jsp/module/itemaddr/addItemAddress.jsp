<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.WEB-INF/jsp/commonImport.jsp
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
</head>
<c:set value="${itemAddress}" var="item" />
<body>
<form action="/xsddWeb/saveItemAddress.do" method="post">
    <table>
        <tr>
            <td>名称:</td>
            <td>
                <input value="${item.id}" name="id" id="id" type="hidden">
                <input type="text" name="name" id="name" value="${item.name}"></td>
        </tr>
        <tr>
            <td>物品所在地:</td>
            <td><input type="text" name="address" id="address" value="${item.address}"></td>
        </tr>
        <tr>
            <td>国家:</td>
            <td>
                <select name="countryList">
                    <c:forEach items="${countryList}" var="countryList">
                        <c:if test="${item.countryId==countryList.id}">
                            <option value="${countryList.id}" selected="selected">${countryList.name}</option>
                        </c:if>
                        <c:if test="${item.countryId!=countryList.id}">
                            <option value="${countryList.id}">${countryList.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>邮编:</td>
            <td><input type="text" name="postalCode" id="postalCode" value="${item.postalcode}"></td>
        </tr>
    </table>
    <div>
        <div><input type="submit" value="确定"/></div>
    </div>
</form>
</body>
</html>
