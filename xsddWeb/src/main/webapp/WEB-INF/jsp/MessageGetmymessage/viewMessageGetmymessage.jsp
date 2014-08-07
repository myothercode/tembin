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
    <script type="text/javascript">
        var sentmessage;
        function sendMessage(){
            sentmessage= $.dialog({title: '发送消息',
                content: 'url:/xsddWeb/sendMessageGetmymessage.do',
                icon: 'succeed',
                width:800,
                height:300,
                lock:true
            });
        }
        W = api.opener;
        function closeMessage(){
            W.MessageGetmymessage.close();
        }
    </script>
</head>
<body>

    <c:forEach items="${messages}" var="message">
        <c:if test="${message.read==1}">
            <div  class="easyui-panel" title="subject:${message.subject}"
                  style="width:1000px;height:150px;padding:10px;background:#fafafa;"
               >
                ${message.sendtoname},您好!<br/>
                &nbsp; &nbsp;${message.textHtml}
            </div>
        </c:if>
        <c:if test="${message.read==0}">
            <div  class="easyui-panel" title="subject:${message.subject}"
                  style="width:1000px;height:150px;padding:10px;background:#fafafa;"
                    >
                    ${message.sendtoname},您好!<br/>
                &nbsp; &nbsp;${message.textHtml}
            </div>
        </c:if>
    </c:forEach>
        <div style="text-align: right">
            <input type="button" value="回复" onclick="sendMessage();" />
      </div>

</body>
</html>
