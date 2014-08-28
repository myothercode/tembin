<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/11
  Time: 17:54
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
            var j=0
            console.debug(boxs);
            /* for(var i=0;i<box.length;i++){
                 if(box[i].attr(""))
             }*/
            for(var i=0;i<boxs.length;i++){
                var box=boxs[i];
               if(box.checked){
                   d[j]={"callBackFunction":xxx,"url":path+"/message/apiGetMyMessagesRequest.do","id":"1","ebayId":box.value};
                   j++;
               }
            }
            batchPost(d);
            /*console.debug($(box[0]).attr("spellcheck"));*/
            /*var d=[{"url":path+"/apiGetMyMessagesRequest.do","id":"1","ebayId":"6"}
                *//*{"callBackFunction":xxx,"url":path+"/apiGetMyMessagesRequest","tt":"x2"},
                {"callBackFunction":xxx,"url":path+"/apiGetMyMessagesRequest","tt":"v3"}*//*
            ];
            batchPost(d);*/
        }

        function xxx(opt){
            alert(opt);
            W.MessageGetmymessage.close();
            W.refreshTable();
        }
    </script>
    
</head>
<body>
<form id="ebayForm">
    <input type="hidden" name="id" value="1">
    <table>
        <tr>
            <td>请选择需要同步的ebay账号:</td>
            <td></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <%--<select name="ebayId">--%>
                    <c:forEach items="${ebays}" var="ebay">
                        <input type="checkbox" scope="checkbox" value="${ebay.id}"/>${ebay.ebayName}<br/>
                        <%--<option value="${ebay.id}">${ebay.ebayName}</option>--%>
                    </c:forEach>
            <%--    </select>--%>
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
