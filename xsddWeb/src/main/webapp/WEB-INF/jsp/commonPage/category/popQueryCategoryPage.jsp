<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/16
  Time: 9:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<script type="text/javascript" src=<c:url value ="/js/commonPage/category/popQueryCategoryPage.js" /> ></script>
<html>
<head>
    <title></title>
    <script>
        var title = '${title}';
    </script>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
</head>
<body>
<div style="height: 32px;"></div>
<div style="">
    <table>
        <tr>
            <td align="right">标题：</td>
            <td align="left"><input type="text" style="border-left-width: 1px;" value="${title}" id="title"class="key_1"><input name="newbut" onclick="getRese()" type="button" class="key_2"></td>
        </tr>
    </table>
</div>
<div id="rese1">

</div>
<div style="text-align: right;margin-top: 10px;">
    <button type="button" class="net_put" onclick="que()">确定</button>
</div>
</body>
</html>
