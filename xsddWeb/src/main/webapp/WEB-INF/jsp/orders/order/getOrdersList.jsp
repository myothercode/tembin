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
        var OrderGetOrders;
        $(document).ready(function(){
            $("#OrderGetOrdersListTable").initTable({
                url:path + "/order/ajax/loadOrdersList.do?",
                columnData:[
                    {title:"图片",name:"pictrue",width:"8%",align:"left",format:makeOption2},
                    {title:"OrderID",name:"orderid",width:"8%",align:"left"},
                    {title:"付款状态",name:"orderstatus",width:"8%",align:"left"},
                    {title:"ebay商品详情",name:"itemUrl",width:"8%",align:"left",format:makeOption3},
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
            $("#OrderGetOrdersListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        /**查看消息*/
        function makeOption1(json){
            var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"viewOrder('"+json.orderid+"');\">查看详情</a>";
            return htm;
        }
        function makeOption2(json){
            var htm="<img src='"+json.pictrue+"' style='width: 50px;hidden=50px;' />";
            return htm;
        }
        function makeOption3(json){
            var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"ebayurl('"+json.itemUrl+"');\">"+json.title+"</a>";
            return htm;
        }
        function ebayurl(url){
            console.debug(url);
            window.location.href=url;
        }
        function submitCommit(){
            var orderStatus=$("#orderStatus").val();
            var starttime=$("#starttime").val();
            var status=$("#status").val();
            var endtime=$("#endtime").val();
            $("#OrderGetOrdersListTable").initTable({
                url:path + "/order/ajax/loadOrdersList.do?orderStatus="+orderStatus+"&starttime="+starttime+"&endtime="+endtime+"&status="+status,
                columnData:[
                    {title:"图片",name:"pictrue",width:"8%",align:"left",format:makeOption2},
                    {title:"OrderID",name:"orderid",width:"8%",align:"left"},
                    {title:"付款状态",name:"orderstatus",width:"8%",align:"left"},
                    {title:"ebay商品详情",name:"itemUrl",width:"8%",align:"left",format:makeOption3},
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
            alert("查询完成");
        }
        function viewOrder(id){
            var url=path+'/order/viewOrderGetOrders.do?orderid='+id;
            OrderGetOrders=$.dialog({title: '查看订单详情',
                content: 'url:'+url,
                icon: 'succeed',
                width:1050,
                height:1050
            });
        }
        function getBindParm(){
            var url=path+"/order/GetOrder.do";
            OrderGetOrders=$.dialog({title: '同步订单',
                content: 'url:'+url,
                icon: 'succeed',
                width:1025,
                lock:true
            });
        }
    </script>
</head>
<body>
订单状态:<select id="orderStatus">
            <option value="All">All</option>
            <option value="Active">Active</option>
            <option value="CancelPending">CancelPending</option>
            <option value="Completed">Completed</option>
            <option value="Inactive">Inactive</option>
            <option value="Shipped">Shipped</option>
        </select>
付款状态:<select id="status">
        <option value="All">All</option>
         <option value="Complete">Complete</option>
         <option value="Incomplete">Incomplete</option>
    </select>
&nbsp; 从<input id="starttime"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm'})"/>
到<input id="endtime"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm'})"/>
<input type="button"  value="查询" onclick="submitCommit();" style="right: 10px;"/>
<div id="OrderGetOrdersListTable" ></div>
<input type="button" value="同步订单" onclick="getBindParm();">
</body>
</html>
