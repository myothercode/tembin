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
            $("#OrderGetOrdersListTable1").initTable({
                url:path + "/order/ajax/loadOrdersList.do?",
                columnData:[
                    {title:"",name:"ch",width:"2%",align:"top",format:makeOption5},
                    {title:"订单号 / 源订单号",name:"orderid",width:"8%",align:"left",format:makeOption2},
                    {title:"物品 / SKU / 承运商 / 追踪号",name:"orderid",width:"8%",align:"left",format:makeOption3},
                    {title:"站点",name:"country",width:"8%",align:"left"},
                    {title:"售价",name:"itemUrl",width:"8%",align:"left",format:makeOption6},
                    {title:"售出日期",name:"createdtime",width:"8%",align:"left"},
                    {title:"发货日期",name:"shippedtime",width:"8%",align:"left"},
                    {title:"状态",name:"shipped",width:"8%",align:"left",format:makeOption4},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            $("#OrderGetOrdersListTable2").initTable({
                url:path + "/order/ajax/loadOrdersList.do?status=Incomplete",
                columnData:[
                    {title:"",name:"ch",width:"2%",align:"top",format:makeOption5},
                    {title:"订单号 / 源订单号",name:"orderid",width:"8%",align:"left",format:makeOption2},
                    {title:"物品 / SKU / 承运商 / 追踪号",name:"orderid",width:"8%",align:"left",format:makeOption3},
                    {title:"站点",name:"country",width:"8%",align:"left"},
                    {title:"售价",name:"itemUrl",width:"8%",align:"left",format:makeOption6},
                    {title:"售出日期",name:"createdtime",width:"8%",align:"left"},
                    {title:"发货日期",name:"shippedtime",width:"8%",align:"left"},
                    {title:"状态",name:"shipped",width:"8%",align:"left",format:makeOption4},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            $("#OrderGetOrdersListTable3").initTable({
                url:path + "/order/ajax/loadOrdersList.do?status=notAllComplete",
                columnData:[
                    {title:"",name:"ch",width:"2%",align:"top",format:makeOption5},
                    {title:"订单号 / 源订单号",name:"orderid",width:"8%",align:"left",format:makeOption2},
                    {title:"物品 / SKU / 承运商 / 追踪号",name:"orderid",width:"8%",align:"left",format:makeOption3},
                    {title:"站点",name:"country",width:"8%",align:"left"},
                    {title:"售价",name:"itemUrl",width:"8%",align:"left",format:makeOption6},
                    {title:"售出日期",name:"createdtime",width:"8%",align:"left"},
                    {title:"发货日期",name:"shippedtime",width:"8%",align:"left"},
                    {title:"状态",name:"shipped",width:"8%",align:"left",format:makeOption4},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            $("#OrderGetOrdersListTable4").initTable({
                url:path + "/order/ajax/loadOrdersList.do?status=Complete",
                columnData:[
                    {title:"",name:"ch",width:"2%",align:"top",format:makeOption5},
                    {title:"订单号 / 源订单号",name:"orderid",width:"8%",align:"left",format:makeOption2},
                    {title:"物品 / SKU / 承运商 / 追踪号",name:"orderid",width:"8%",align:"left",format:makeOption3},
                    {title:"站点",name:"country",width:"8%",align:"left"},
                    {title:"售价",name:"itemUrl",width:"8%",align:"left",format:makeOption6},
                    {title:"售出日期",name:"createdtime",width:"8%",align:"left"},
                    {title:"发货日期",name:"shippedtime",width:"8%",align:"left"},
                    {title:"状态",name:"shipped",width:"8%",align:"left",format:makeOption4},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable1();
            refreshTable2();
            refreshTable3();
            refreshTable4();
        });

        function refreshTable1(){
            $("#OrderGetOrdersListTable1").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        function refreshTable2(){
            $("#OrderGetOrdersListTable2").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        function refreshTable3(){
            $("#OrderGetOrdersListTable3").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        function refreshTable4(){
            $("#OrderGetOrdersListTable4").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        /**查看消息*/
        function makeOption1(json){
            var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"viewOrder('"+json.orderid+"');\">查看详情</a>";
            var htm1="|<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"modifyOrderStatus('"+json.transactionid+"');\">修改发货状态</a>";
            return htm+htm1;
        }
        function makeOption2(json){
            var imgurl=path+"/img/error.png";
            var htm1="<img src=\""+imgurl+"\"> <font class=\"red_1\"><strong>"+json.orderid+"</strong></font><br>";
            var htm="<img src='"+json.pictrue+"' style='width: 50px;hidden=50px;' />";
            return htm1+htm;
        }
        function makeOption3(json){
            var imgurl=path+"/img/";
            var imgurl1=path+"/img/";
            if(json.message==null||json.message==""){
                imgurl1=imgurl1+"f.png";
            }else{
                imgurl1=imgurl1+"add.png";
            }
            /*var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"ebayurl('"+json.itemUrl+"');\">"+json.title+"</a>";*/
            var htm="<span style=\"width:100%; float:left\"><font color=\"#5F93D7\">"+json.buyeruserid+"  </font> ( "+json.buyeremail+" )</span>" +
            "<span class=\"newbgspan_3\">"+json.shipmenttrackingnumber+"</span>&nbsp;<span class=\"newbgspan_3\">"+json.shippingcarrierused+"</span>" +
            "<span style=\"width:100%; float:left\"><font color=\"#5F93D7\">"+json.title+"</font><br>("+json.itemid+")</span>" +
            "<span style=\"width:100%; float:left; color:#8BB51B\">"+json.sku+"</span>" +
            "<span style=\"width:100%; float:left\"><font color=\"#5F93D7\">Color：</font>Black;</span>" +
            "<span style=\"width:100%; float:left\"><font color=\"#5F93D7\">B：</font><img src=\""+imgurl+"f.png\" width=\"14\" height=\"14\"></span>"+
            "<span style=\"width:100%; float:left\"><font color=\"#5F93D7\">S：" +
            "</font><img src=\""+imgurl1+"\" width=\"12\" height=\"12\">"+json.message+"</span>" +
            "<span class=\"newdf\">I need black color</span>" +
            "<span style=\"width:100%; float:left\">08/25/2014 17:56</span>" +
            "<span style=\"width:100%; float:left; color:#999\">PayPal payment Status: [Completed], Type: [instant], Amount: [USD21.96] received on UTC 8/28/2014 1:55:59 AM, PayPal transaction ID: 5TW</span>";
            return htm;
        }

        function makeOption4(json){
            var imgurl1=path+"/img/";
            var htm="";
            if(json.status=='Incomplete'){
                htm=htm+"<img src=\""+imgurl1+"money.gif \" onmousemove='showInformation();'>"/*"<img src=\""+imgurl1+"money.gif\">"*/;
                /*"<img onmousemove='showInformation();'>"*/
            }
            if(json.status=='Complete'){
                htm=htm+"<img src=\""+imgurl1+"money.png\" >";
            }
            if(json.shipmenttrackingnumbe!=""&&json.shippingcarrierused!=""){
                htm=htm+"<img src=\""+imgurl1+"car.png\" >"
            }
            return htm;
        }
        function makeOption5(json){
            var htm="<input type='checkbox' id='checkbox' name='checkbox' value='"+json.id+"' />";
            return htm;
        }
        function makeOption6(json){
            var htm=json.transactionprice+"USD";
            return htm;
        }
        function ebayurl(url){
            console.debug(url);
            window.location.href=url;
        }
        function submitCommit(){
            $("#OrderGetOrdersListTable").initTable({
                url:path + "/order/ajax/loadOrdersList.do?",
                columnData:[
                    {title:"订单号 / 源订单号",name:"pictrue",width:"8%",align:"left",format:makeOption2},
                    {title:"物品 / SKU / 承运商 / 追踪号",name:"orderid",width:"8%",align:"left"},
                    {title:"站点",name:"country",width:"8%",align:"left"},
                    {title:"售价",name:"itemUrl",width:"8%",align:"left",format:makeOption3},
                    {title:"售出日期",name:"buyeruserid",width:"8%",align:"left"},
                    {title:"发货日期",name:"selleruserid",width:"8%",align:"left"},
                    {title:"状态",name:"shipped",width:"8%",align:"left",format:makeOption4},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
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
        function modifyOrderStatus(id){
            var url=path+'/order/modifyOrderStatus.do?transactionid='+id;
            OrderGetOrders=$.dialog({title: '修改订单状态',
                content: 'url:'+url,
                icon: 'succeed',
                width:1050,
                height:1050,
                lock:true
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
        function query1(){
            var daysQ=$("#daysQ").val();
            var countryQ=$("#countryQ").val();
            var typeQ=$("#typeQ").val();
            var itemType=$("#itemType1").val();
            var content=$("#content1").val();
            var table="#OrderGetOrdersListTable1";
            refreshTable5(table,countryQ,typeQ,daysQ,itemType,content,null);
        }
        function query2(){
            var daysQ=$("#daysQ").val();
            var countryQ=$("#countryQ").val();
            var typeQ=$("#typeQ").val();
            var itemType=$("#itemType2").val();
            var content=$("#content2").val();
            var table="#OrderGetOrdersListTable2";
            var status="Incomplete";
            refreshTable5(table,countryQ,typeQ,daysQ,itemType,content,status);
        }
        function query3(){
            var daysQ=$("#daysQ").val();
            var countryQ=$("#countryQ").val();
            var typeQ=$("#typeQ").val();
            var itemType=$("#itemType3").val();
            var content=$("#content3").val();
            var table="#OrderGetOrdersListTable3";
            refreshTable5(table,countryQ,typeQ,daysQ,itemType,content,null);
        }
        function query4(){
            var daysQ=$("#daysQ").val();
            var countryQ=$("#countryQ").val();
            var typeQ=$("#typeQ").val();
            var itemType=$("#itemType4").val();
            var content=$("#content4").val();
            var table="#OrderGetOrdersListTable4";
            var status="Complete";
            refreshTable5(table,countryQ,typeQ,daysQ,itemType,content,status);
        }
        function refreshTable5(table,countryQ,typeQ,daysQ,itemType,content,status){
            $(table).initTable({
                url:path + "/order/ajax/loadOrdersList.do?",
                columnData:[
                    {title:"",name:"ch",width:"2%",align:"top",format:makeOption5},
                    {title:"订单号 / 源订单号",name:"orderid",width:"8%",align:"left",format:makeOption2},
                    {title:"物品 / SKU / 承运商 / 追踪号",name:"orderid",width:"8%",align:"left",format:makeOption3},
                    {title:"站点",name:"country",width:"8%",align:"left"},
                    {title:"售价",name:"itemUrl",width:"8%",align:"left",format:makeOption6},
                    {title:"售出日期",name:"createdtime",width:"8%",align:"left"},
                    {title:"发货日期",name:"shippedtime",width:"8%",align:"left"},
                    {title:"状态",name:"shipped",width:"8%",align:"left",format:makeOption4},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            $(table).selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0,"countryQ":countryQ,"typeQ":typeQ,"daysQ":daysQ,"itemType":itemType,"content":content,"status":status});
            alert("查询成功");
        }
        function queryTime(n,flag,day){
            var scop="span[scop=queryTime"+n+"]";
            var time=$(scop);
            for(var i=0;i<time.length;i++){
                if(i==(flag-1)){
                    $(time[i]).attr("class","newusa_ici");
               /* <input type="hidden" id="countryQ"/>
                            <input type="hidden" id="typeQ"/>
                            <input type="hidden" id="daysQ"/>*/
                    $("#daysQ").val(day);
                }else{
                    $(time[i]).attr("class","newusa_ici_1");
                }
            }
        }
        function queryAttr(n,flag,day){
            var scop="span[scop=queryAttr"+n+"]";
            var time=$(scop);
            for(var i=0;i<time.length;i++){
                if(i==(flag-1)){
                    $(time[i]).attr("class","newusa_ici");
                    /* <input type="hidden" id="countryQ"/>
                     <input type="hidden" id="typeQ"/>
                     <input type="hidden" id="daysQ"/>*/
                    $("#typeQ").val(day);
                }else{
                    $(time[i]).attr("class","newusa_ici_1");
                }
            }
        }
        function queryCountry(n,flag,day){
            var scop="span[scop=queryCountry"+n+"]";
            var time=$(scop);
            for(var i=0;i<time.length;i++){
                if(i==(flag-1)){
                    $(time[i]).attr("class","newusa_ici");
                    /* <input type="hidden" id="countryQ"/>
                     <input type="hidden" id="typeQ"/>
                     <input type="hidden" id="daysQ"/>*/
                    $("#countryQ").val(day);
                }else{
                    $(time[i]).attr("class","newusa_ici_1");
                }
            }
        }
        function downloadOrders(n){
            var status=null;
            if(n==2){
                status="Incomplete"
            }
            if(n==4){
                status="Complete";
            }
            var url=path+"/order/downloadOrders.do?status="+status;
            window.open(url);
        }
        function addTabRemark(){
            var url=path+"/order/addTabRemark.do";
            OrderGetOrders=$.dialog({title: '新建文件夹',
                content: 'url:'+url,
                icon: 'succeed',
                width:1025,
                lock:true
            });
        }
    </script>
</head>
<body>
<%--订单状态:<select id="orderStatus">
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
<input type="button" value="同步订单" onclick="getBindParm();">--%>

<div class="new_all">
<div class="here">当前位置：首页 &gt; 销售管理 &gt; <b>订单</b></div>
<div class="a_bal"></div>
<div class="new">
<script type="text/javascript">
    function setTab(name,cursel,n){
        for(i=1;i<=n;i++){
            var menu=document.getElementById(name+i);
            var con=document.getElementById("con_"+name+"_"+i);
            menu.className=i==cursel?"new_tab_1":"new_tab_2";
            con.style.display=i==cursel?"block":"none";
        }
    }
</script>
<div id="tabRemark" class="new_tab_ls">
    <dt id="menu1" class="new_tab_2" onclick="setTab('menu',1,4)">近期销售</dt>
    <dt id="menu2" class="new_tab_1" onclick="setTab('menu',2,4)">未付款</dt>
    <dt id="menu3" class="new_tab_2" onclick="setTab('menu',3,4)">未结清付款</dt>
    <dt id="menu4" class="new_tab_2" onclick="setTab('menu',4,4)">已付款</dt>
</div>
<script type="text/javascript">
    function menuFix() {
        var sfEls = document.getElementById("newtipi").getElementsByTagName("li");
        for (var i = 0; i < sfEls.length; i++) {
            sfEls[i].onmouseover = function() {
                this.className += (this.className.length > 0 ? " " : "") + "sfhover";
            }
            sfEls[i].onMouseDown = function() {
                this.className += (this.className.length > 0 ? " " : "") + "sfhover";
            }
            sfEls[i].onMouseUp = function() {
                this.className += (this.className.length > 0 ? " " : "") + "sfhover";
            }
            sfEls[i].onmouseout = function() {
                this.className = this.className.replace(new RegExp("( ?|^)sfhover\\b"),"");
            }
        }
    }
    window.onload = menuFix;
    //--><!]]>
</script>
    <form id="queryForm">
        <input type="hidden" id="countryQ"/>
        <input type="hidden" id="typeQ"/>
        <input type="hidden" id="daysQ"/>
    </form>
<div class="Contentbox">
<div id="con_menu_1" class="hover" style="display: none;">
    <!--综合开始 -->
    <div class="new_usa" style="margin-top:20px;">
        <li class="new_usa_list"><span class="newusa_i">收件人国家：</span><a href="#"><span class="newusa_ici_1" scop="queryCountry1"  onclick="queryCountry(1,1,null);">全部</span></a><a href="#"><span class="newusa_ic_1" scop="queryCountry1"  onclick="queryCountry(1,2,'US');"><img src="../../img/usa_1.png" >美国</span></a><a href="#"><span class="newusa_ic_1" scop="queryCountry1"  onclick="queryCountry(1,3,'UK');"><img src="../../img/usa_2.png">英国</span></a><a href="#"><span class="newusa_ic_1" scop="queryCountry1"  onclick="queryCountry(1,4,'DE');"><img src="../../img/usa_2.png">德国</span></a><a href="#"><span class="newusa_ic_1" scop="queryCountry1"  onclick="queryCountry(1,5,'AU');"><img src="../../img/usa_2.png">澳大利亚</span></a></li>
        <li class="new_usa_list"><span class="newusa_i">刊登类型：</span><a href="#"><span class="newusa_ici_1" scop="queryAttr1"  onclick="queryAttr(1,1,null);">全部</span></a><a href="#"><span class="newusa_ici_1" scop="queryAttr1"  onclick="queryAttr(1,2,'fixation');">固价</span></a><a href="#"><span class="newusa_ici_1" scop="queryAttr1"  onclick="queryAttr(1,3,'auction');">拍卖</span></a><a href="#"><span class="newusa_ici_1" scop="queryAttr1"  onclick="queryAttr(1,4,'multiattribute');">多属性</span></a></li>
        <div class="newsearch">
            <span class="newusa_i">刊登类型：</span><a href="#"><span class="newusa_ici_1" scop="queryTime1" onclick="queryTime(1,1,null)">全部</span></a><a href="#"><span class="newusa_ici_1" scop="queryTime1" onclick="queryTime(1,2,'1')">今天</span></a><a href="#"><span scop="queryTime1" onclick="queryTime(1,3,'2')" class="newusa_ici_1">昨天</span></a><a href="#"><span scop="queryTime1" onclick="queryTime(1,4,'7');" class="newusa_ici_1">7天以内</span></a><a href="#"><span scop="queryTime1" onclick="queryTime(1,5,'30')" class="newusa_ici_1">30天以内</span></a>
<span id="sleBG">
<span id="sleHid">
<select name="type" class="select" id="itemType1">
    <option selected="selected">选择类型</option>
    <option value="1">图书</option>
    <option value="2">音像</option>
</select>
</span>
</span>
            <div class="vsearch">
                <input id="content1" name="" type="text" class="key_1"><input name="newbut" onclick="query1();" type="button" class="key_2"></div>
        </div>
        <div class="newds">
            <div class="newsj_left">

                <span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="checkbox"></span>
                <span class="newusa_ici_del">添加备注</span><span class="newusa_ici_del" onclick="addTabRemark();">新建文件夹</span><span onclick="downloadOrders(1);" class="newusa_ici_del">下载订单</span></div>
            <div class="tbbay"><a data-toggle="modal" href="javascript:#" onclick="getBindParm();" >同步eBay</a></div>
        </div>
    </div>
    <!--综合结束 -->
    <div id="OrderGetOrdersListTable1" ></div>
    <div class="page_newlist">
    </div>
</div>

<div style="display: block;" id="con_menu_2">
    <!--综合开始 -->
    <div class="new_usa" style="margin-top:20px;">
        <li class="new_usa_list"><span class="newusa_i">收件人国家：</span><a href="#"><span class="newusa_ici_1" scop="queryCountry2"  onclick="queryCountry(2,1,null);">全部</span></a><a href="#"><span class="newusa_ic_1" scop="queryCountry2"  onclick="queryCountry(2,2,'US');"><img src="../../img/usa_1.png" >美国</span></a><a href="#"><span class="newusa_ic_1" scop="queryCountry2"  onclick="queryCountry(2,3,'UK');"><img src="../../img/usa_2.png">英国</span></a><a href="#"><span class="newusa_ic_1" scop="queryCountry2"  onclick="queryCountry(2,4,'DE');"><img src="../../img/usa_2.png">德国</span></a><a href="#"><span class="newusa_ic_1" scop="queryCountry2"  onclick="queryCountry(2,5,'AU');"><img src="../../img/usa_2.png">澳大利亚</span></a></li>
        <li class="new_usa_list"><span class="newusa_i">刊登类型：</span><a href="#"><span class="newusa_ici_1" scop="queryAttr2" onclick="queryAttr(2,1,null);">全部</span></a><a href="#"><span class="newusa_ici_1" scop="queryAttr2" onclick="queryAttr(2,2,'fixation');">固价</span></a><a href="#"><span class="newusa_ici_1" scop="queryAttr2" onclick="queryAttr(2,3,'auction');">拍卖</span></a><a href="#"><span class="newusa_ici_1" scop="queryAttr2" onclick="queryAttr(2,4,'multiattribute');">多属性</span></a></li>
        <div class="newsearch">
            <span class="newusa_i">刊登类型：</span><a href="#"><span scop="queryTime2" onclick="queryTime(2,1,null)" class="newusa_ici_1">全部</span></a><a href="#"><span scop="queryTime2" onclick="queryTime(2,2,1)" class="newusa_ici_1">今天</span></a><a href="#"><span scop="queryTime2" onclick="queryTime(2,3,'2')" class="newusa_ici_1">昨天</span></a><a href="#"><span scop="queryTime2" onclick="queryTime(2,4,'7')" class="newusa_ici_1">7天以内</span></a><a href="#"><span scop="queryTime2" onclick="queryTime(2,5,'30')" class="newusa_ici_1">30天以内</span></a>
<span id="sleBG">
<span id="sleHid">
<select name="type" class="select" id="itemType2">
    <option selected="selected">选择类型</option>
    <option value="1">图书</option>
    <option value="2">音像</option>
</select>
</span>
</span>
            <div class="vsearch">
                <input id="content2" name="" type="text" class="key_1"><input name="newbut" onclick="query2();" type="button" class="key_2"></div>
        </div>
        <div class="newds">
            <div class="newsj_left">

                <span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="checkbox"></span>
                <span class="newusa_ici_del">添加备注</span><span class="newusa_ici_del" onclick="addTabRemark();">新建文件夹</span><span onclick="downloadOrders(2);" class="newusa_ici_del">下载订单</span></div>
            <div class="tbbay"><a data-toggle="modal" href="#myModal" onclick="getBindParm();" class="">同步eBay</a></div>
        </div>
    </div>
    <!--综合结束 -->
    <div id="OrderGetOrdersListTable2" ></div>
    <div class="page_newlist">
    </div>

</div>
<div style="display: none;" id="con_menu_3">
    <!--综合开始 -->
    <div class="new_usa" style="margin-top:20px;">
        <li class="new_usa_list"><span class="newusa_i">收件人国家：</span><a href="#"><span class="newusa_ici_1" scop="queryCountry3"  onclick="queryCountry(3,1,null);">全部</span></a><a href="#"><span class="newusa_ic_1" scop="queryCountry3"  onclick="queryCountry(3,2,'US');"><img src="../../img/usa_1.png" >美国</span></a><a href="#"><span class="newusa_ic_1" scop="queryCountry3"  onclick="queryCountry(3,3,'UK');"><img src="../../img/usa_2.png">英国</span></a><a href="#"><span class="newusa_ic_1" scop="queryCountry3"  onclick="queryCountry(3,4,'DE');"><img src="../../img/usa_2.png">德国</span></a><a href="#"><span class="newusa_ic_1" scop="queryCountry3"  onclick="queryCountry(3,5,'AU');"><img src="../../img/usa_2.png">澳大利亚</span></a></li>
        <li class="new_usa_list"><span class="newusa_i">刊登类型：</span><a href="#"><span class="newusa_ici_1" scop="queryAttr3" onclick="queryAttr(3,1,null);">全部</span></a><a href="#"><span class="newusa_ici_1" scop="queryAttr3" onclick="queryAttr(3,2,'fixation');">固价</span></a><a href="#"><span class="newusa_ici_1" scop="queryAttr3" onclick="queryAttr(3,3,'auction');">拍卖</span></a><a href="#"><span class="newusa_ici_1" scop="queryAttr3" onclick="queryAttr(3,4,'multiattribute');">多属性</span></a></li>
        <div class="newsearch">
            <span class="newusa_i">刊登类型：</span><a href="#"><span scop="queryTime3" onclick="queryTime(3,1,null)" class="newusa_ici_1">全部</span></a><a href="#"><span scop="queryTime3" onclick="queryTime(3,2,'1')" class="newusa_ici_1">今天</span></a><a href="#"><span scop="queryTime3" onclick="queryTime(3,3,'2')" class="newusa_ici_1">昨天</span></a><a href="#"><span scop="queryTime3" onclick="queryTime(2,4,'7')" class="newusa_ici_1">7天以内</span></a><a href="#"><span scop="queryTime3" onclick="queryTime(3,5,'30')" class="newusa_ici_1">30天以内</span></a>
<span id="sleBG">
<span id="sleHid">
<select name="type" class="select" id="itemType3">
    <option selected="selected">选择类型</option>
    <option value="1">图书</option>
    <option value="2">音像</option>
</select>
</span>
</span>
            <div class="vsearch">
                <input id="content3" name="" type="text"  class="key_1"><input name="newbut" onclick="query3();" type="button" class="key_2"></div>
        </div>
        <div class="newds">
            <div class="newsj_left">

                <span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="checkbox"></span>
                <span class="newusa_ici_del">添加备注</span><span class="newusa_ici_del" onclick="addTabRemark();">新建文件夹</span><span onclick="downloadOrders(3);" class="newusa_ici_del">下载订单</span></div>
            <div>
                <div id="newtipi">
                </div></div>
            <div class="tbbay"><a data-toggle="modal" href="#myModal" onclick="getBindParm();" class="">同步eBay</a></div>
        </div>
    </div>
    <!--综合结束 -->
    <div id="OrderGetOrdersListTable3" ></div>
    <div class="page_newlist">
    </div>
</div>
<div style="display: none;" id="con_menu_4">
    <!--综合开始 -->
    <div class="new_usa" style="margin-top:20px;">
        <li class="new_usa_list"><span class="newusa_i">收件人国家：</span><a href="#"><span class="newusa_ici_1" scop="queryCountry4"  onclick="queryCountry(4,1,null);">全部</span></a><a href="#"><span class="newusa_ic_!" scop="queryCountry4"  onclick="queryCountry(4,2,'US');"><img src="/img/usa_1.png" >美国</span></a><a href="#"><span class="newusa_ic_1" scop="queryCountry4"  onclick="queryCountry(4,3,'UK');"><img src="../../img/usa_2.png">英国</span></a><a href="#"><span class="newusa_ic_1" scop="queryCountry4"  onclick="queryCountry(4,4,'DE');"><img src="../../img/usa_2.png">德国</span></a><a href="#"><span class="newusa_ic_1" scop="queryCountry4"  onclick="queryCountry(4,5,'AU');"><img src="../../img/usa_2.png">澳大利亚</span></a></li>
        <li class="new_usa_list"><span class="newusa_i">刊登类型：</span><a href="#"><span class="newusa_ici_1" scop="queryAttr4" onclick="queryAttr(4,1,null);">全部</span></a><a href="#"><span class="newusa_ici_1" scop="queryAttr4" onclick="queryAttr(4,2,'fixation');">固价</span></a><a href="#"><span class="newusa_ici_1" scop="queryAttr4" onclick="queryAttr(4,3,'auction');">拍卖</span></a><a href="#"><span class="newusa_ici_1" scop="queryAttr4" onclick="queryAttr(4,4,'multiattribute');">多属性</span></a></li>
        <div class="newsearch">
            <span class="newusa_i">刊登类型：</span><a href="#"><span scop="queryTime4" onclick="queryTime(4,1,null)" class="newusa_ici_1">全部</span></a><a href="#"><span scop="queryTime4" onclick="queryTime(4,2,'1')" class="newusa_ici_1">今天</span></a><a href="#"><span scop="queryTime4" onclick="queryTime(4,3,'2')" class="newusa_ici_1">昨天</span></a><a href="#"><span scop="queryTime4" onclick="queryTime(4,4,'7')" class="newusa_ici_1">7天以内</span></a><a href="#"><span scop="queryTime4" onclick="queryTime(4,5,'30')" class="newusa_ici_1">30天以内</span></a>
<span id="sleBG">
<span id="sleHid">
<select name="type" class="select" id="itemType4">
    <option selected="selected">选择类型</option>
    <option value="1">图书</option>
    <option value="2">音像</option>
</select>
</span>
</span>
            <div class="vsearch">
                <input id="content4" name="" type="text"  class="key_1"><input name="newbut" onclick="query4();" type="button" class="key_2"></div>
        </div>
        <div class="newds">
            <div class="newsj_left">

                <span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="checkbox"></span>
                <span class="newusa_ici_del">添加备注</span><span class="newusa_ici_del" onclick="addTabRemark();">新建文件夹</span><span onclick="downloadOrders(4);" class="newusa_ici_del">下载订单</span></div>
            <div>
                <div id="newtipi">
                </div></div>
            <div class="tbbay"><a data-toggle="modal" href="#myModal" onclick="getBindParm();" class="">同步eBay</a></div>
        </div>
    </div>
    <!--综合结束 -->
    <div id="OrderGetOrdersListTable4" ></div>
    <div class="page_newlist">
    </div>
</div>


</div>
</div></div>

</body>
</html>
