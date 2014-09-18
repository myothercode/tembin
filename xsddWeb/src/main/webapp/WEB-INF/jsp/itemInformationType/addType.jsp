<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/10
  Time: 17:00
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
            var url=path+"/informationType/ajax/saveinformationType.do";
            var data=$("#addTypeForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.refreshTable();
                        W.itemInformationType.close();
                        Base.token();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function closedialog(){
            W.itemInformationType.close();
        }
    </script>
</head>
<body>
    <form id="addTypeForm">
        <table align="center">
            <tr>
                <td>分类名称:</td>
                <td><input type="text" name="typeName"/></td>
            </tr>
            <tr>
                <td>上级分类:</td>
                <td>
                    <%--<c:if test="${id!=null}">
                        <select name="parent" disabled>
                    </c:if>
                    <c:if test="${id==null}">--%>
                        <select name="parent">
                    <%--</c:if>--%>
                        <option value="0">无上级分类</option>
                        <c:forEach items="${types}" var="type">
                            <c:if test="${id==type.id}">
                                <option value="${type.id}" selected>${type.configName}</option>
                            </c:if>
                            <c:if test="${id!=type.id}">
                                <option value="${type.id}">${type.configName}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>
    </form>
    <div align="right">
        <input type="button" value="保存" onclick="submitCommit();"/>
        <input type="button" value="关闭" onclick="closedialog();"/>
    </div>
</body>
</html>
