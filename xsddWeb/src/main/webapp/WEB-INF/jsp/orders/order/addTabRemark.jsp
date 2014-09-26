<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/23
  Time: 15:42
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
        function submitCommit(){
            var url=path+"/order/saveTabremark.do?";
            var data=$("#addRemarkForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        W.location.reload();
                        W.selectTabRemark.close();
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
            W.selectTabRemark.close();
        }
    </script>
</head>
<body>
<form id="addRemarkForm">
    &nbsp;文件夹名称:<input name="tabName" id="tabName"/><br/>
</form>
<div align="right">
    <input type="button" value="保存" onclick="submitCommit();"/>
    <input type="button" value="关闭" onclick="closedialog();"/>
</div>
</body>
</html>
