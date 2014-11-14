<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/10/13
  Time: 11:59
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
        $(document).ready(function(){
            $("#OrderGetOrdersListTable").initTable({
                url:path + "/order/ajax/loadOrdersList.do?framConten=${content}",
                columnData:[
                    {title:"",name:"ch",width:"2%",align:"top",format:makeOption5},
                    {title:"订单号 / 源订单号",name:"orderid",width:"8%",align:"left",format:makeOption2},
                    {title:"物品 / SKU / 承运商 / 追踪号",name:"orderid",width:"13%",align:"left",format:makeOption3},
                    {title:"站点",name:"itemSite",width:"8%",align:"left"},
                    {title:"售价",name:"itemUrl",width:"8%",align:"left",format:makeOption6},
                    {title:"售出日期",name:"createdtime",width:"8%",align:"left"},
                    {title:"发货日期",name:"shippedtime",width:"8%",align:"left"},
                    {title:"状态",name:"shipped",width:"3%",align:"left",format:makeOption4},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });
        function makeOption2(json){
            var imgurl=path+"/img/error.png";
            var htm1="<img src=\""+imgurl+"\"> <font class=\"red_1\"><strong>"+json.transactionid+"</strong></font><br>";
            var htm="<img src='"+json.pictrue+"' style='width: 50px;hidden=50px;' />";
            return htm1+htm;
        }
        function makeOption3(json){
            var imgurl=path+"/img/";
            var imgurl1=path+"/img/";
            var vas=json.variationspecificsMap;
            var con="";
            for(var i=0;i<vas.length;i++){
                con=con+vas[i]+"<br/>"
            }
            if(json.message==null||json.message==""){
                imgurl1=imgurl1+"f.png";
            }else{
                imgurl1=imgurl1+"add.png";
            }
            /*var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"ebayurl('"+json.itemUrl+"');\">"+json.title+"</a>";*/
            var htm="<span style=\"width:100%; float:left\"><font color=\"#5F93D7\">"+json.buyeruserid+"  </font> ( "+json.buyeremail+" )</span>" +
                    "<span class=\"newbgspan_3\">"+json.shipmenttrackingnumber+"</span>&nbsp;<span class=\"newbgspan_3\">"+json.shippingcarrierused+"</span>" +
                    "<span style=\"width:100%; float:left\"><font color=\"#5F93D7\"><a href='"+json.itemUrl+"' target=\"1\">"+json.title+"</a></font><br>("+json.itemid+")</span>" +
                    "<span style=\"width:100%; float:left; color:#8BB51B\">"+json.sku+"</span>" +
                    "<span style=\"width:100%; float:left\"><font color=\"#5F93D7\">" +con +
                    "</span>" /*+
             "<span style=\"width:100%; float:left\"><font color=\"#5F93D7\">B：</font><img src=\""+imgurl+"f.png\" width=\"14\" height=\"14\"></span>"+
             "<span style=\"width:100%; float:left\"><font color=\"#5F93D7\">S：" +
             "</font><img src=\""+imgurl1+"\" width=\"12\" height=\"12\">"+json.message+"</span>" +
             *//*            "<span class=\"newdf\"></span>" +*//*
             "<span style=\"width:100%; float:left\">" +(json.paypalPaidTime==null?"":json.paypalPaidTime)+"</span>" +*//*Type: [instant],*//*
             "<span style=\"width:100%; float:left; color:#999\">PayPal payment Status: ["+json.status+"],  Amount: [USD "+json.amountpaid+"] received on UTC "+(json.paypalPaymentTime==null?"":json.paypalPaymentTime)+", PayPal transaction ID:"+json.externalTransactionID+" </span>"*/;
            return htm;
        }
        function makeOption4(json){
            var imgurl1=path+"/img/";
            var htm="";
            if(json.flagNotAllComplete){
                htm=htm+"<img src=\""+imgurl1+"money_no.png\" >";
            }else{
                if(json.status=='Incomplete'){
                    htm="<img src=\""+imgurl1+"money.gif \" onmousemove='showInformation();'>"/*"<img src=\""+imgurl1+"money.gif\">"*/;
                    /*"<img onmousemove='showInformation();'>"*/
                }
                if(json.status=='Complete'){
                    htm="<img src=\""+imgurl1+"money.png\" >";
                }
            }
            if(json.shipmenttrackingnumbe!=""&&json.shippingcarrierused!=""){
                htm="<img src=\""+imgurl1+"car.png\" >"
            }
            if(json.feedbackMessage&&json.feedbackMessage!=""){
                htm="<img src=\""+imgurl1+"box.png\" >"
            }
            return htm;
        }
        function makeOption5(json){
            var htm="<input type='checkbox' id='checkbox' name='checkbox' value='"+json.orderid+"' />";
            return htm;
        }
        function makeOption6(json){
            var htm=json.transactionprice+"USD";
            return htm;
        }
        function makeOption1(json){
            /* var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"viewOrder('"+json.orderid+"');\">查看详情</a>";
             var htm1="|<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"modifyOrderStatus('"+json.transactionid+"');\">修改发货状态</a>";*/
            /*var htm="<div class=\"ui-select\" style=\"width:106px\" >" +
             "<select onchange=\"selectOperation('"+json.orderid+"','"+json.transactionid+"','"+json.selleruserid+"',this); \" name=\"ui-select\" style=\"margin-left:-3px;\">" +
             "<option value=\"0\"><a href=\"javascript:void(0)\">--请选择--</a></option>" +
             "<option value=\"1\"><a href=\"javascript:void(0)\">查看详情</a></option>" +
             "<option value=\"2\"><a href=\"javascript:void(0)\">修改发货状态</a></option>" +
             "<option value=\"3\"><a href=\"javascript:void(0)\">发送消息</a></option>" +
             "<option value=\"4\"><a href=\"javascript:void(0)\">退款功能</a></option>" +
             "</select>" +
             "</div>";*/
            var hs="";
            hs="<li onclick=selectOperation('"+json.orderid+"','"+json.transactionid+"','"+json.selleruserid+"',this); value='1' doaction=\"readed\" >查看详情</li>";
            hs+="<li  onclick=selectOperation('"+json.orderid+"','"+json.transactionid+"','"+json.selleruserid+"',this); value='2' doaction=\"look\" >修改状态</li>";
            hs+="<li  onclick=selectOperation('"+json.orderid+"','"+json.transactionid+"','"+json.selleruserid+"',this); value='3' doaction=\"look\" >发送消息</li>";
            hs+="<li  onclick=selectOperation('"+json.orderid+"','"+json.transactionid+"','"+json.selleruserid+"',this); value='4' doaction=\"look\" >退款功能</li>";
            var pp={"liString":hs};
            return getULSelect(pp);
            /* return htm;*/
        }
        function refreshTable1(){
            $("#OrderGetOrdersListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
    </script>
</head>
<body>
    <div id="OrderGetOrdersListTable" ></div>
</body>
</html>
