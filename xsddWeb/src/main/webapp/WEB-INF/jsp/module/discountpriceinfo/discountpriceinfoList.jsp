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
        function adddiscountpriceinfo(){
            $.dialog({title: '新增折扣选项',
                content: 'url:/xsddWeb/addDiscountPriceInfo.do',
                icon: 'succeed',
                width:500
            });
        }

        function editdiscountpriceinfo(id){
            $.dialog({title: '编辑折扣选项',
                content: 'url:/xsddWeb/editDiscountPriceInfo.do?id='+id,
                icon: 'succeed',
                width:500
            });
        }
    </script>
</head>
<body>
<div style="text-align: right;">
    <input type="button" name="add" value="新增" onclick="adddiscountpriceinfo()">
</div>
<div>
    <table width="100%">
        <tr>
            <td>名称</td>
            <td>账户名称</td>
            <td>开始时间</td>
            <td>结束时间</td>
            <td>折扣</td>
            <td>降价</td>
            <td>是否免运费</td>
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
</div>
</body>
</html>
