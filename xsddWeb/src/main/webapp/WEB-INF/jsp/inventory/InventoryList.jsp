<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/17
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        function siHaiYouAddOrder(){
            var url=path+"/inventory/ajax/siHaiYouAddOrder.do";
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
<input type="button" value="四海邮addOrder" onclick="siHaiYouAddOrder();">
</body>
</html>
