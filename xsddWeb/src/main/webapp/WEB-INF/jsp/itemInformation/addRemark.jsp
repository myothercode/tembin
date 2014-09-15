<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/10
  Time: 11:56
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
            W.itemInformation.close();
        }
        function submitCommit(){
            var url=path+"/information/ajax/saveremark.do";
            var data=$("#remarkForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.refreshTable();
                        W.itemInformation.close();
                        Base.token();
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
    <form id="remarkForm">
        <input type="hidden" name="id" value="${id}"/>
        标签:&nbsp;<input type="text" name="remark" style="width: 1000px;"/><br/>
        <select name="parentid">
            <option value="0">--请选择--</option>
            <c:forEach items="${parents}" var="parent">
                <option value="${parent.id}">${parent.configName}</option>
            </c:forEach>

        </select>
    </form>
    <input type="button" value="保存" onclick="submitCommit();"/>
    <input type="button" value="关闭" onclick="closedialog();">
</body>
</html>