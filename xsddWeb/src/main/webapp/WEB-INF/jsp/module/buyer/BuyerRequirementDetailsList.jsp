<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script>
        function addBuyer(){
            $.dialog({title: '新增买家要求',
                content: 'url:/xsddWeb/addBuyer.do',
                icon: 'succeed',
                width:500
            });
        }

        function editBuyer(){
            $.dialog({title: '编辑买家要求',
                content: 'url:/xsddWeb/editBuyer.do',
                icon: 'succeed',
                width:500
            });
        }

    </script>
</head>
<body>
<div style="text-align: right;">
    <input type="button" name="add" value="新增" onclick="addBuyer()">
</div>
<div>
    <table width="100%">
        <tr>
            <td>名称</td>
            <td>站点</td>
            <td>所有买家购买</td>
        </tr>
        <c:forEach items="${li}" var="li">
            <tr>
                <td>${li.name}</td>
                <td>
                    ${li.siteName}
                </td>
                <td>${li.buyerFlag}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
