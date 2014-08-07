<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/7
  Time: 17:02
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
        function cancelButton(){
            W.sentmessage.close();
        }
        function submitCommit(){
            var url=path+"/ajax/saveMessageGetmymessage.do";
            var data=$("#sendMessageForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        Base.token();
                        W.sentmessage.close();
                        W.refreshTable();
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
<form id="sendMessageForm">
    标题:<input type="text" name="sendHeader" style="width: 300px;"/>
    <br/>
    内容:<textarea name="content" style="width: 700px;height:200px;"></textarea>
<hr/>
    <div style="text-align: right">
        <input type="button" value="取消" onclick="cancelButton();"/>|
        <input type="button" value="确定" onclick="submitCommit();"/>
    </div>
</form>
</body>
</html>
