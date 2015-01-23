<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2015/1/20
  Time: 14:37
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
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        function closedialog(){
            W.OrderGetOrders.close();
        }
        function addOrderId(){
            var tr="<tr>" +
                    "<td><input scop='sycOrder' class='form-controlsd validate[required]'/></td>" +
                    "<td><a href='javascript:void(0)' onclick='removeInput(this)'>移除</a></td>" +
                    "</tr>";
            $("#addTable").append(tr);
        }
        function removeInput(obj){
            var tr=$(obj).parent().parent();
            $(tr).remove();
        }
        function submitForm1(){
            var inputs=$("input[scop=sycOrder]");
            var orderIds="";
            for(var i=0;i<inputs.length;i++){
                var orderid=$(inputs[i]).val();
                if(orderid!=""){
                    if(i==(inputs.length-1)){
                        orderIds+=orderid;
                    }else{
                        orderIds+=orderid+","
                    }
                }
            }
            $("#orderIds").val(orderIds);
            var url=path+"/order/ajax/GetOrderTransactions.do?";
            var data=$("#addOrderForm").serialize();
            $().invoke(url,data,
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
    </script>
</head>
<body>
<div class="modal-body">
    <form id="addOrderForm">
        <input name="orderIds" id="orderIds" type="hidden">
    </form>
    <form class="form-horizontal" role="form">
        <table id="addTable" align="center">
            <tr>
                <td style="width: 300px;"><a href="javascript:void(0)" style="margin-left: 10px;" onclick="addOrderId();">添加所需要的订单号</a></td>
                <td style="width: 50px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            </tr>
        </table>
    </form>
    <div class="modal-footer">
        <button type="button" class="net_put" onclick="submitForm1();">保存</button>
        <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>
    </div>
</div>
</body>
</html>
