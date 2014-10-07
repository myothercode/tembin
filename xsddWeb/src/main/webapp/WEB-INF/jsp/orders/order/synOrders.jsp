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
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
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
                d[j]={"callBackFunction":xxx,"url":path+"/order/apiGetOrdersRequest.do","id":"1","ebayId":box.value};
                j++;
            }
        }
        batchPost(d);
    }

    function xxx(opt){
        alert(opt);
        W.refreshTable();
        W.OrderGetOrders.close();
    }
    function closedialog(){
        W.OrderGetOrders.close();
    }
    </script>
</head>
<body>
<div class="modal-body" style="height: 400px;">
    <form id="orderForm" class="form-horizontal" role="form">
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
        </table>
    </form>
    <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="submitForm1();">同步</button>
        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closedialog();">关闭</button>
    </div>
</div>
</body>
</html>
