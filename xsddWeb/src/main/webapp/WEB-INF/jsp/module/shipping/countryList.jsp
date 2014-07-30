<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/30
  Time: 17:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script>
        function isSub(){

            alert($(document).html());
        }
    </script>
</head>
<body>
<div>
    <div>
        <c:forEach var="data" items="${lidata}" varStatus="da">
                <c:if test="${da.index%3==0}">
                    </br>
                </c:if>
                <span style="text-align:left;display:-moz-inline-box; display:inline-block; width:250px;">
                    <input type="checkbox" name="location" value="${data.value}" value1="${data.name}">${data.name}
                </span>
        </c:forEach>
    </div>
</div>
<div>
    <input type="button" onclick="isSub()" value="确定">
</div>
</body>
</html>
