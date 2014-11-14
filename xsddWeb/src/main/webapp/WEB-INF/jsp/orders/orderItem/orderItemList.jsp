<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/13
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var orderItem;

        /*$(document).ready(function(){
            $("#orderItemListTable").initTable({
                url:path + "/order/ajax/loadOrdersList.do?",
                columnData:[
                    {title:"OrderID",name:"orderid",width:"8%",align:"left"},
                    {title:"付款状态",name:"orderstatus",width:"8%",align:"left"},
                    {title:"买家",name:"buyeruserid",width:"8%",align:"left"},
                    {title:"卖家",name:"selleruserid",width:"8%",align:"left"},
                    {title:"交易总数",name:"countNum",width:"8%",align:"left"},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:true
            });
            refreshTable();
        });
        function refreshTable(){
            $("#orderItemListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        *//**查看消息*//*
        function makeOption1(json){
            var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"viewOrder('"+json.orderid+"');\">查看详情</a>";
            return htm;
        }
        function viewOrder(id){
            var url=path+'/order/vieworderItem.do?orderid='+id;
            orderItem=openMyDialog({title: '查看订单详情',
                content: 'url:'+url,
                icon: 'succeed',
                width:1000,
                height:500,
                lock:true
            });
        }*/
        function getBindParm(){
            var url=path+"/orderItem/GetOrder.do";
            orderItem=openMyDialog({title: '同步订单商品',
                content: 'url:'+url,
                icon: 'succeed',
                width:1025,
                lock:true
            });
        }
    </script>
</head>
<body>
<div id="orderItemListTable" ></div>
<input type="button" value="同步订单商品" onclick="getBindParm();">
</body>
</html>
