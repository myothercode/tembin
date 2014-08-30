<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/13
  Time: 16:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src=<c:url value ="/js/batchAjaxUtil.js" /> ></script>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        function submitForm1(){
            var boxs=$("input[scope=checkbox]");
            var d=[];
            var j=0;
            for(var i=0;i<boxs.length;i++){
                var box=boxs[i];
                if(box.checked){
                    d[j]={"callBackFunction":xxx,"url":path+"/userCases/apiGetuserCasessRequest.do","id":"1","ebayId":box.value};
                    j++;
                }
            }
            batchPost(d);
        }

        function xxx(opt){
            alert(opt);
            W.refreshTable();
            W.userCases.close();
        }
    </script>
</head>
<body>
<form id="userCasesForm">
    <table>
        <tr>
            <td>请选择需要同步的ebay账号:</td>
            <td></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <c:forEach items="${ebays}" var="ebay">
                    <input type="checkbox" scope="checkbox" value="${ebay.id}"/>${ebay.ebayName}<br/>
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="button" value="同步" onclick="submitForm1();"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
