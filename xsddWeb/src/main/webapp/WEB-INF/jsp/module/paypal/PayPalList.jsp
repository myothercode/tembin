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
        function addPayPal(){
            $.dialog({title: '新增付款选项',
                content: 'url:/xsddWeb/addPayPal.do',
                icon: 'succeed',
                width:500
            });
        }

        function editPayPal(id){
            $.dialog({title: '编辑付款选项',
                content: 'url:/xsddWeb/editPayPal.do?id='+id,
                icon: 'succeed',
                width:500
            });
        }
    </script>
</head>
<body>
<div style="text-align: right;">
    <input type="button" name="addPayPal" value="新增" onclick="addPayPal();">
</div>
<div>
    <table width="100%">
        <tr>
            <td>名称</td>
            <td>站点</td>
            <td>paypal账号</td>
            <td>描述</td>
            <td>操作</td>
        </tr>
        <c:forEach items="${paypalli}" var="li">
            <tr>
                <td>${li.payName}</td>
                <td>
                    ${li.siteName}
                </td>
                <td>${li.payPalName}</td>
                <td>${li.paymentinstructions}</td>
                <td>
                    <a target="_blank" href="javascript:void(0)" onclick="editPayPal('${li.id}');">编辑</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
