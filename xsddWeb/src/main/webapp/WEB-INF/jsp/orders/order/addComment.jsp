<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/24
  Time: 17:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        function submitCommit(){
            if(!$("#addRemarkForm").validationEngine("validate")){
                return;
            }
            var url=path+"/order/saveComment.do?";
            var data=$("#addRemarkForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.OrderGetOrders.close();
                        /*W.viewsendMessage1.close();*/
                        Base.token;
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function closedialog(){
            W.OrderGetOrders.close();
        }
        $(document).ready(function(){
            $("#addRemarkForm").validationEngine();
        });
    </script>
</head>
<body>
<form id="addRemarkForm">
    <input type="hidden" name="orderid" value="${orderid}"/>
    <br/><br/>&nbsp;备注信息:<input name="comment"  class="validate[required]" id="comment"/><br/>
</form>
<div align="right">
    <input type="button" value="保存" onclick="submitCommit();"/>
    <input type="button" value="关闭" onclick="closedialog();"/>
</div>
</body>
</html>
