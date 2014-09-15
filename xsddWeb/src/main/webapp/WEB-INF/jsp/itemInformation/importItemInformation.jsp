<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/12
  Time: 11:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        function upload(obj)
        {
            obj.action =path+"/information/ajax/importInformation.do";
            obj.submit();
        }
        $(document).ready(function(){
            var flag=document.getElementById("flag");
            if("true"==$(flag).val()){
                alert("上传成功");
                W.refreshTable();
                W.itemInformation.close();
            }
        });
    </script>
</head>
<body>
<input id="flag" type="hidden" name="flag" value="${flag}">
<form id="improtForm" action="/information/ajax/importInformation.do" method="post" enctype="multipart/form-data" >
    <input type="file" name="file"/>
    <input type="submit" value="导入" onclick="upload(this.form)">
</form>
</body>
</html>
