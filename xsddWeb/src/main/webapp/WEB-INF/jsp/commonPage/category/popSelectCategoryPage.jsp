<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/16
  Time: 9:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<script type="text/javascript" src=<c:url value ="/js/commonPage/category/popSelectCategoryPage.js" /> ></script>
<html>
<head>
    <title></title>
    <script>
        var title = '${title}';
    </script>
</head>
<body>
<button name="search" onclick="showDiv(this)">选择分类</button>
<button name="rese" onclick="showDiv(this)">相似分类</button>
<div id="search">

</div>
<div id="rese1" style="display: none;">

</div>


<div style="width: 600px" id="choose">
<div id="mainDiv"  style="width: 580px;height: 400px"></div>
<div id="menuPath" style="float: left;width: 550px;overflow: hidden"></div>
</div>
<div>
    <input type="button" value="确定" onclick="que()">
</div>
</body>
</html>
