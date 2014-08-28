<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/7
  Time: 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/default/easyui.css" />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/icon.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/jquery-easyui/jquery.easyui.min.js" /> ></script>

</head>
<body>
      <div style="text-align: right">
            <input type="button" value="系统回复" onclick="sendMessage();" />
      </div>
    <c:forEach items="${messages}" var="message">
        ${message.textHtml}
    </c:forEach>
    <script type="text/javascript">
        var sentmessage;
        function sendMessage() {
            var api = frameElement.api, W = api.opener;
            var url=path+"/message/sendMessageGetmymessage.do"
            sentmessage = $.dialog({title: '发送消息',
                content: 'url:'+url+'?messageid=${messages[0].messageid}',
                icon: 'succeed',
                width: 800,
                height: 300,
                lock: true
            });
        }
        $(document).ready(function(){
            var api = frameElement.api, W = api.opener;
            W.refreshTable();
        });
        /* W = api.opener;
         function closeMessage(){
         W.MessageGetmymessage.close();
         }*/
    </script>
</body>
</html>
