<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script>


    </script>
</head>
<body>
<div class="newds">
    <div class="tbbay"><a data-toggle="modal" href="#myModal" class=""  onclick="addItemAddress()">新增</a></div>
</div>
<div id="ItemAddressListTable"></div>

<%--<div>
    <table width="100%">
        <tr>
            <td>名称</td>
            <td>地址</td>
            <td>国家</td>
            <td>邮编</td>
            <td>操作</td>
        </tr>
        <c:forEach items="${li}" var="li">
            <tr>
                <td>${li.name}</td>
                <td>
                    ${li.address}
                </td>
                <td>${li.countryName}</td>
                <td>${li.postalcode}</td>
                <td>
                    <a target="_blank" href="javascript:void(0)" onclick="editItemAddress('${li.id}')">编辑</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>--%>
</body>
</html>
