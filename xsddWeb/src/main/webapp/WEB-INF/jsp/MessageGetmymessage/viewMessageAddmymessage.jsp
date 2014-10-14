<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/10/13
  Time: 9:23
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
        function closedialog(){
            W.MessageGetmymessage.close();
        }
    </script>
</head>
<body>
    <div class="newbook">
        <c:forEach items="${addMessages}" var="addMessage">
            <p class="user">${addMessage.recipientid}，您好！</p>
            <div class="user_co">
                <ul>Hi ${addMessage.recipientid}.: )<br/> ${addMessage.body}
                    <span>发送与:<fmt:formatDate value="${addMessage.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                </ul>
                <div class="user_co_2"></div>
            </div>
            <div class="dpan"></div>
        </c:forEach>
    </div>
    <div class="modal-footer">
        <input  class="btn btn-default" type="button" onclick="closedialog();" value="关闭" data-dismiss="modal">
    </div>

</body>
</html>
