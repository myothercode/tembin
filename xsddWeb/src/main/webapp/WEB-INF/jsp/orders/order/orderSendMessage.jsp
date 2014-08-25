<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/21
  Time: 17:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        function submitCommit(){
            var url=path+"/order/apiGetOrdersSendMessage.do";
            var data=$("#sendForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.viewsendMessage.close();
                        Base.token;
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );

        }
    </script>
</head>
<body>
<form id="sendForm">
<input type="hidden" name="itemid" value="${order.itemid}">
<input type="hidden" name="buyeruserid" value="${order.buyeruserid}">
    <input type="hidden" name="selleruserid" value="${order.selleruserid}">
主题：<input type="text" name="subject" style="width: 700px"><br/>
内容：<textarea id="body" name="body" style="width: 700px;height: 400px;"></textarea>
<input type="button" value="发送" onclick="submitCommit();"/>
</form>
</body>
</html>
