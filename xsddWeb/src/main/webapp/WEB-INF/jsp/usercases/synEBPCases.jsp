<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/30
  Time: 14:34
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
        var d=[];
        d={"callBackFunction":xxx,"url":path+"/userCases/apiEBPCasessRequest.do","sellerId":"${userCases.sellerid}","caseId":"${userCases.caseid}","caseType":"${userCases.casetype}"};
        batchPost(d);
    }
    function xxx(opt){
        alert(opt);
        W.userCases.close();
    }
    </script>
</head>
<body>
<table>
    <tr>
       <td>纠纷id:</td>
        <td><input type="text" value="${userCases.caseid}"/></td>
    </tr>
    <tr>
        <td>纠纷类型:</td>
        <td><input type="text" value="${userCases.casetype}"/></td>
    </tr>
    <tr>
        <td>卖家:</td>
        <td><input type="text" value="${userCases.sellerid}"/></td>
    </tr>
</table>
<input type="button" value="同步详情" onclick="submitForm1();"/>
</body>
</html>
