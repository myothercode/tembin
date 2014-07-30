<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script>

    </script>
</head>
<body>
<div>
选取一个或多个选项，您可以不运送到某个国家或者整个地区
</div>
<div>
    <div>Domestic</div>
    <div>
        <c:forEach var="data" items="${lidata}">
            <c:if test="${data.name1=='Domestic'}">
                <input type="checkbox" name="location" value="${data.value}">${data.name}
            </c:if>
        </c:forEach>
    </div>
</div>
<div>
    <div>International</div>
    <div>
        <c:forEach var="data" items="${lidata}">
            <c:if test="${data.name1=='International'}">
                <input type="checkbox" name="location" value="${data.value}">${data.name}
                [<a>
                显示所有国家
                </a>]
            </c:if>
        </c:forEach>
    </div>
</div>
<div>
    <div>Additional Locations</div>
    <div>
        <c:forEach var="data" items="${lidata}">
            <c:if test="${data.name1=='Additional Locations'}">
                <input type="checkbox" name="location" value="${data.value}">${data.name}
            </c:if>
        </c:forEach>
    </div>
</div>
</body>
</html>
