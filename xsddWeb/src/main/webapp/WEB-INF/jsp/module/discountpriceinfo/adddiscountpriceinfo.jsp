<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/29
  Time: 11:45
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
<form>
    <table>
        <tr>
            <td>名称</td>
            <td><input type="text" name="name" id="name" /></td>
        </tr>
        <tr>
            <td>eBay 账户</td>
            <td><input type="text" name="ebayAccount" id="ebayAccount" /></td>
        </tr>
        <tr>
            <td>开始时间</td>
            <td><input name="disStarttime" id="disStarttime" type="text" onfocus="WdatePicker({isShowWeek:true})"/></td>
        </tr>
        <tr>
            <td>结束时间</td>
            <td><input name="disEndtime" id="disEndtime" type="text" onfocus="WdatePicker({isShowWeek:true})"/></td>
        </tr>

    </table>
</form>
</body>
</html>
