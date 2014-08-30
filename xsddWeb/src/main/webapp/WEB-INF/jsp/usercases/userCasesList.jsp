<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/28
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var userCases;
        function getBindParm(){
            var url=path+"/userCases/getUserCases.do";
            userCases=$.dialog({title: '同步订单',
                content: 'url:'+url,
                icon: 'succeed',
                width:1025,
                lock:true
            });
        }
    </script>
</head>
<body>
<input type="button" value="同步纠纷" onclick="getBindParm();">
</body>
</html>
