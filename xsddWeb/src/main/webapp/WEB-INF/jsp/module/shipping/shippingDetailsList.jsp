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
    <script>
        function addshippingDetails(){
            $.dialog({title: '新增运送选项',
                content: 'url:/xsddWeb/addshippingDetails.do',
                icon: 'succeed',
                width:1000
            });
        }

        function editshippingDetails(id){
            $.dialog({title: '编辑运送选项',
                content: 'url:/xsddWeb/editshippingDetails.do?id='+id,
                icon: 'succeed',
                width:500
            });
        }
    </script>
</head>
<body>
<div style="text-align: right;">
    <input type="button" name="add" value="新增" onclick="addshippingDetails()">
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
        <c:forEach items="${disli}" var="li">
            <tr>
                <td>${li.name}</td>
                <td>
                    ${li.ebayName}
                </td>
                <td>
                    <fmt:formatDate value="${li.disStarttime}" pattern="yyyy-MM-dd HH:mm"/>
                </td>
                <td>
                    <fmt:formatDate value="${li.disStarttime}" pattern="yyyy-MM-dd HH:mm"/>
                </td>
                <td>
                        ${li.madeforoutletcomparisonprice}
                </td>
                <td>${li.minimumadvertisedprice}</td>
                <td>
                    <c:if test="${li.isShippingfee=='1'}">
                        <c:out value="是"/>
                    </c:if>
                    <c:if test="${li.isShippingfee!='1'}">
                        <c:out value="否"/>
                    </c:if>
                </td>
                <td>
                    <a target="_blank" href="javascript:void(0)" onclick="editdiscountpriceinfo('${li.id}')">编辑</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
