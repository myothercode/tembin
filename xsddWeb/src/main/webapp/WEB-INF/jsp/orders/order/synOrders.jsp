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
        var boxs=$("input[scope=checkbox]:checked");
     /*   var d=[];
        var j=0;
        for(var i=0;i<boxs.length;i++){
            var box=boxs[i];
            if(box.checked){
                d[j]={"callBackFunction":xxx,"url":path+"/order/apiGetOrdersRequest.do","id":"1","ebayId":box.value};
                j++;
            }
        }
        batchPost(d);*/

        var ebayIds="";
        for(var i=0;i<boxs.length;i++){
            if(i==(boxs.length-1)){
                ebayIds+=boxs[i].value;
            }else{
                ebayIds+=boxs[i].value+",";
            }
        }
        var url=path+"/order/apiGetOrdersRequest.do?ebayIds="+ebayIds;
        $().invoke(url,null,
                [function(m,r){
                    alert(r);
                    W.OrderGetOrders.close();
                    Base.token();
                },
                    function(m,r){
                        alert(r);
                        Base.token();
                    }]
        );
    }

    function xxx(opt){
        alert(opt);
        W.refreshTable1();
        W.OrderGetOrders.close();
    }
    function closedialog(){
        W.OrderGetOrders.close();
    }
    function selectAllcheckBox(obj){
        if(obj.checked){
            var inputs=$(document).find("input[scope=checkbox]");
            for(var i=0;i<inputs.length;i++){
                inputs[i].checked=true;
            }
        }else{
            var inputs=$(document).find("input[scope=checkbox]");
            for(var i=0;i<inputs.length;i++){
                inputs[i].checked=false;
            }
        }
    }
    </script>
</head>
<body>
<div class="modal-body">
    <form id="orderForm" class="form-horizontal" role="form">
        <table>
            <tr>
                <td>请选择需要同步的ebay账号:</td>
                <td></td>
            </tr>
            <tr>
                <td ><input type="checkbox" name="checkbox" scope="allCheckbox" onchange="selectAllcheckBox(this);" > 全选</td>
                <td align="left">上次同步时间</td>
            </tr>
            <c:forEach items="${ebays}" var="ebay" varStatus="status" begin="0">
            <tr>
                <td><input type="checkbox" scope="checkbox" value="${ebay.id}"/>${ebay.ebayName}<br/></td>
                <td><c:if test="${ebayDates[status.index]!=null}">(${ebayDates[status.index]})</c:if></td>
            </tr>
            </c:forEach>
        </table>
    </form>
    <div class="modal-footer">
        <button type="button" class="net_put" onclick="submitForm1();">保存</button>
        <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>
<%--        <button type="button" class="btn btn-primary" onclick="submitForm1();">同步</button>
        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closedialog();">关闭</button>--%>
    </div>
</div>
</body>
</html>
