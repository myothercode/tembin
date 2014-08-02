<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/2
  Time: 9:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script>
        var returnItem ="";
        function addItem(){
            returnItem=$.dialog({title: '新增运送选项',
                content: 'url:/xsddWeb/addItem.do',
                icon: 'succeed',
                width:1000
            });
        }
    </script>
</head>
<body>
<div>
    <input type="button" value="新增商品" onclick="addItem()">
</div>

<div id="itemTable">

</div>
</body>
</html>
