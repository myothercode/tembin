<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/6
  Time: 16:37
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
        var MessageGetmymessage;

        $(document).ready(function(){
            $("#MessageGetmymessageListTable").initTable({
                url:path + "/message/ajax/loadMessageGetmymessageList.do?",
                columnData:[
                    {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption4},
                    {title:"主题",name:"read",width:"8%",align:"center",format:makeOption2,click:makeOption1},
                    {title:"From > to",name:"sender",width:"8%",align:"center",format:makeOption3,click:makeOption1},
                    {title:"SKU",name:"sku",width:"8%",align:"center",format:makeOption6,click:makeOption1},
                    {title:"修改时间",name:"receivedate",width:"8%",align:"center",format:makeOption7,click:makeOption1},
                    {title:"操作",name:"",width:"8%",align:"center",format:makeOption8}
                    /*,
                    {title:"总数",name:"countNum",width:"8%",align:"left",click:makeOption1}*/
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();

        });
        function refreshTable(){
            $("#MessageGetmymessageListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        function refreshTable1(amount,status,day,type,content,starttime,endtime,messageFrom){
            $("#MessageGetmymessageListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0,"amount":amount,"status":status,"day":day,"type":type,"content":content,"starttime1":starttime,"endtime1":endtime,"messageFrom":messageFrom});

        }
        function refreshTable2(amount,status,day,type,content,starttime,endtime){
            $("#MessageGetmymessageListTable1").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0,"amount":amount,"status":status,"day":day,"type":type,"content":content,"starttime1":starttime,"endtime1":endtime});

        }
        function refreshTable3(amount,status,day,type,content,starttime,endtime){
            $("#MessageGetmymessageListTable2").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0,"amount":amount,"status":status,"day":day,"type":type,"content":content,"starttime1":starttime,"endtime1":endtime});

        }
        /**查看消息*/
        var OrderGetOrders;
        function makeOption1(json,messageid){
            var url=path+"/message/viewMessageGetmymessage.do";
            if(json!=null){
                url='url:'+url+'?messageID='+json.messageid;
            }else{
                url='url:'+url+'?messageID='+messageid;
            }
            OrderGetOrders=openMyDialog({title: '查看消息',

                content: url,
                icon: 'succeed',
                width:1200,
                height:850,
                lock:true
            });

        }
        function makeOption8(json){
            var hs="";
            hs+="<li style=\"height:25px;\" onclick=selectOption("+json.messageid+","+json.id+",this); value='1' doaction=\"readed\" >处理消息</li>";
            if(json.read=="true"){
                hs+="<li style=\"height:25px;\" onclick=selectOption("+json.messageid+","+json.id+",this); value='2' doaction=\"look\" >标记未读</li>";
            }else{
                hs+="<li style=\"height:25px;\" onclick=selectOption("+json.messageid+","+json.id+",this); value='3' doaction=\"look\" >标记已读</li>";
            }
            var pp={"liString":hs};
            return getULSelect(pp);
        }
        function selectOption(messageid,id,obj){
            var value=$(obj).attr("value");
            if(value=='1'){
                makeOption1(null,messageid);
            }
            if(value=='2'){
                markReaded(null,"false",id);
            }
            if(value=='3'){
                markReaded(null,"true",id);
            }
        }
        function makeOption2(json){
            var detail=json.subject;
            if(detail.length>25){
                detail=detail.substring(0,26)+"...<br/>";
            }
            if(json.read=="true"){
                return "<span style=\"color:#999999;\">"+detail+"</span>";
            }else{
                return "<span style=\"color: #0000ff;\">"+detail+"</span>";
            }
        }
        function makeOption3(json){
            if(json.read=="true"){
                return "<span style=\"color:#999999\">"+json.sender+">"+json.recipientuserid+"</span>";
            }else{
                return "<span style=\"color: #0000ff;\">"+json.sender+">"+json.recipientid+"</span>";
            }
        }
        function makeOption6(json){
            if(json.read=="true"){
                return "<span style=\"color:#999999\">"+json.sku+"</span>";
            }else{
                return "<span style=\"color: #0000ff;\">"+json.sku+"</span>";
            }
        }
        function makeOption7(json){
            if(json.read=="true"){
                return "<span style=\"color:#999999\">"+json.receivedate+"</span>";
            }else{
                return "<span style=\"color: #0000ff;\">"+json.receivedate+"</span>";
            }
        }
        function makeOption4(json){
            var htm = "<input type=\"checkbox\" name1='"+json.read+"'  name=\"templateId\" value=" + json.id + ">";
            return htm;
        }
        function makeOption5(json){
            var url=path+"/message/viewMessageAddmymessage.do";
            OrderGetOrders=openMyDialog({title: '查看消息',
                content: 'url:'+url+'?transactionid='+json.transactionid+'&itemid='+json.itemid+'&recipientid='+json.recipientid+'&sender='+json.sender,
                icon: 'succeed',
                width:1200,
                height:850,
                lock:true
            });
        }
        function getBindParm(){
            var url=path+"/message/GetmymessageEbay.do"
            MessageGetmymessage=$.dialog({title: '同步需要的ebay账号',
                content: 'url:'+url,
                icon: 'succeed',
                width:1025,
                lock:true
            });
        }
        function Allchecked(obj){
            var checkboxs=$("input[type=checkbox][name=templateId]");
            if(obj.checked){
                for(var i=0;i<checkboxs.length;i++){
                    checkboxs[i].checked=true;
                }
            }else{
                for(var i=0;i<checkboxs.length;i++){
                    checkboxs[i].checked=false;
                }
            }
        }
        function selectDays(count,name){
            var days=$("span[scop=days]");
            for(var i=0;i<days.length;i++){
                if(i==(count)){
                    $(days[i]).attr("class","newusa_ici");
                    $("#selectDay").val(name);
                    $("#starttime1").val("");
                    $("#endtime1").val("");
                }else{
                    $(days[i]).attr("class","newusa_ici_1");
                }
            }
            if(count!==5){
                document.getElementById("time1").innerHTML="";
                queryMessage();
            }
        }
        function selectDays1(count,name){
            var days=$("span[scop=days1]");
            for(var i=0;i<days.length;i++){
                if(i==(count)){
                    $(days[i]).attr("class","newusa_ici");
                    $("#selectDay1").val(name);
                    $("#starttime2").val("");
                    $("#endtime2").val("");
                }else{
                    $(days[i]).attr("class","newusa_ici_1");
                }
            }
            if(count!==5) {
                document.getElementById("time2").innerHTML="";
                queryMessage1();
            }
        }
        function selectDays2(count,name){
            var days=$("span[scop=days2]");
            for(var i=0;i<days.length;i++){
                if(i==(count)){
                    $(days[i]).attr("class","newusa_ici");
                    $("#selectDay2").val(name);
                    $("#starttime3").val("");
                    $("#endtime3").val("");
                }else{
                    $(days[i]).attr("class","newusa_ici_1");
                }
            }
            if(count!==5) {
                document.getElementById("time3").innerHTML="";
                queryMessage2();
            }
        }
        function selectStatus1(count,name){
            var days=$("span[scop=status]");
            for(var i=0;i<days.length;i++){
                if(i==(count)){
                    $(days[i]).attr("class","newusa_ici");
                    $("#selectStatus").val(name);
                }else{
                    $(days[i]).attr("class","newusa_ici_1");

                }
            }
            queryMessage();
        }
        function selectmessageFrom(count,name){
            var days=$("span[scop=messageFrom]");
            for(var i=0;i<days.length;i++){
                if(i==(count)){
                    $(days[i]).attr("class","newusa_ici");
                    $("#selectmessageFrom").val(name);
                }else{
                    $(days[i]).attr("class","newusa_ici_1");
                }
            }
            queryMessage();
        }
        function selectStatus2(count,name){
            var days=$("span[scop=status2]");
            for(var i=0;i<days.length;i++){
                if(i==(count)){
                    $(days[i]).attr("class","newusa_ici");
                    $("#selectStatus1").val(name);
                }else{
                    $(days[i]).attr("class","newusa_ici_1");
                }
            }
            queryMessage1();
        }
        function selectStatus3(count,name){
            var days=$("span[scop=status3]");
            for(var i=0;i<days.length;i++){
                if(i==(count)){
                    $(days[i]).attr("class","newusa_ici");
                    $("#selectStatus2").val(name);
                }else{
                    $(days[i]).attr("class","newusa_ici_1");
                }
            }
            queryMessage2();
        }
        function selectAmount1(count,name){
            var days=$("span[scop=amount]");
            for(var i=0;i<days.length;i++){
                if(i==(count)){
                    $(days[i]).attr("class","newusa_ici");
                    $("#selectAmount").val(name);
                }else{
                    $(days[i]).attr("class","newusa_ici_1");
                }
            }
            queryMessage();
        }
        function selectAmount2(count,name){
            var days=$("span[scop=amount2]");
            for(var i=0;i<days.length;i++){
                if(i==(count)){
                    $(days[i]).attr("class","newusa_ici");
                    $("#selectAmount1").val(name);
                }else{
                    $(days[i]).attr("class","newusa_ici_1");
                }
            }
            queryMessage1();
        }
        function selectAmount3(count,name){
            var days=$("span[scop=amount3]");
            for(var i=0;i<days.length;i++){
                if(i==(count)){
                    $(days[i]).attr("class","newusa_ici");
                    $("#selectAmount2").val(name);
                }else{
                    $(days[i]).attr("class","newusa_ici_1");
                }
            }
            queryMessage2();
        }
        function queryMessage(){
       /* <input type="hidden" id="selectAmount"/>
                    <input type="hidden" id="selectStatus"/>
                    <input type="hidden" id="selectDay">*/
            var amount=$("#selectAmount").val();
            var status=$("#selectStatus").val();
            var day=$("#selectDay").val();
            var type=$("#typeQuery").val();
            var content=$("#content").val();
            var starttime=$("#starttime1").val();
            var endtime=$("#endtime1").val();
            var messageFrom=$("#selectmessageFrom").val();
            $("#MessageGetmymessageListTable").initTable({
                url:path + "/message/ajax/loadMessageGetmymessageList.do?",
                columnData:[
                    {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption4},
                    {title:"主题",name:"read",width:"8%",align:"center",format:makeOption2,click:makeOption1},
                    {title:"From > to",name:"sender",width:"8%",align:"center",format:makeOption3,click:makeOption1},
                    {title:"SKU",name:"sku",width:"8%",align:"center",format:makeOption6,click:makeOption1},
                    {title:"修改时间",name:"receivedate",width:"8%",align:"center",format:makeOption7,click:makeOption1},
                    {title:"操作",name:"",width:"8%",align:"center",format:makeOption8}/*,
                    {title:"总数",name:"countNum",width:"8%",align:"left",click:makeOption1}*/
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable1(amount,status,day,type,content,starttime,endtime,messageFrom);
        }
        function queryMessage1(){
            var amount=$("#selectAmount1").val();
            var status=$("#selectStatus1").val();
            var day=$("#selectDay1").val();
            var type=$("#typeQuery1").val();
            var content=$("#content1").val();
            var starttime=$("#starttime2").val();
            var endtime=$("#endtime2").val();
            var replied="true";
            $("#MessageGetmymessageListTable1").initTable({
                url:path + "/message/ajax/loadMessageAddmymessageList.do?replied="+replied,
                columnData:[
                   /* {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption4},*/
                    {title:"主题",name:"read",width:"8%",align:"center",format:makeOption2,click:makeOption5},
                    {title:"From > to",name:"sender",width:"8%",align:"center",format:makeOption3,click:makeOption5},
                    {title:"SKU",name:"sku",width:"8%",align:"center",click:makeOption5},
                    {title:"修改时间",name:"createTime",width:"8%",align:"center",click:makeOption5}/*,
                    {title:"总数",name:"countNum",width:"8%",align:"left",click:makeOption5}*/
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable2(amount,status,day,type,content,starttime,endtime);
        }
        function queryMessage2(){
            var amount=$("#selectAmount2").val();
            var status=$("#selectStatus2").val();
            var day=$("#selectDay2").val();
            var type=$("#typeQuery2").val();
            var content=$("#content2").val();
            var starttime=$("#starttime3").val();
            var endtime=$("#endtime3").val();
            var replied="false";
            $("#MessageGetmymessageListTable2").initTable({
                url:path + "/message/ajax/loadMessageAddmymessageList.do?replied="+replied,
                columnData:[
                    /*{title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption4},*/
                    {title:"主题",name:"read",width:"8%",align:"center",format:makeOption2,click:makeOption5},
                    {title:"From > to",name:"sender",width:"8%",align:"center",format:makeOption3,click:makeOption5},
                    {title:"SKU",name:"sku",width:"8%",align:"center",click:makeOption5},
                    {title:"修改时间",name:"createTime",width:"8%",align:"center",click:makeOption5}/*,
                    {title:"总数",name:"countNum",width:"8%",align:"left",click:makeOption5}*/
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable3(amount,status,day,type,content,starttime,endtime);
        }
        function inintTableOne(table,cursel){
            if(cursel==1){
                $(table).initTable({
                    url:path + "/message/ajax/loadMessageGetmymessageList.do?",
                    columnData:[
                        {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption4},
                        {title:"主题",name:"read",width:"8%",align:"center",format:makeOption2,click:makeOption1},
                        {title:"From > to",name:"sender",width:"8%",align:"center",format:makeOption3,click:makeOption1},
                        {title:"SKU",name:"sku",width:"8%",align:"center",format:makeOption6,click:makeOption1},
                        {title:"修改时间",name:"receivedate",width:"8%",align:"center",format:makeOption7,click:makeOption1},
                        {title:"操作",name:"",width:"8%",align:"center",format:makeOption8}/*,
                        {title:"总数",name:"countNum",width:"8%",align:"left",click:makeOption1}*/
                    ],
                    selectDataNow:false,
                    isrowClick:false,
                    showIndex:false
                });
            }else{
                var replied="";
                if(cursel==2){
                    replied="true";
                }else if(cursel==3){
                    replied="false";
                }
                $(table).initTable({
                    url:path + "/message/ajax/loadMessageAddmymessageList.do?replied="+replied,
                    columnData:[
                      /*  {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption4},*/
                        {title:"主题",name:"read",width:"8%",align:"center",format:makeOption2,click:makeOption5},
                        {title:"From > to",name:"sender",width:"8%",align:"center",format:makeOption3,click:makeOption5},
                        {title:"SKU",name:"sku",width:"8%",align:"center",click:makeOption5},
                        {title:"修改时间",name:"createTime",width:"8%",align:"center",click:makeOption5}/*,
                        {title:"总数",name:"countNum",width:"8%",align:"left",click:makeOption5}*/
                    ],
                    selectDataNow:false,
                    isrowClick:false,
                    showIndex:false
                });
            }
            $(table).selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        function definedDays(obj){
                $(obj).val("");
        }
        function definedDays1(obj){
            var value=$(obj).val();
            if(value==""){
                $(obj).val("自定义时间");
            }else{
                $("#selectDay").val(value);
                $(obj).attr("class","newusa_ici");
                $(obj).val(value+"以内");
            }
        }
        function markReaded(value1,value2,id){
            /*"<input type=\"checkbox\"  name=\"templateId\" value=" + json.id + ">";*/
            var inputs= $("input[type=checkbox][name=templateId]:checked");
            var url="";
            if(value1!=null){
                if(inputs.length>0){
                    var value="";
                    for(var i=0;i<inputs.length;i++){
                        if(i==inputs.length) {
                            value+=$(inputs[i]).val();
                        }else{
                            value+=$(inputs[i]).val()+",";
                        }
                    }
                    url = path + "/message/ajax/markReaded.do?value1="+value1+"&value="+value;
                }else{
                    alert("请至少选择一个未读内容!");
                    return;
                }
            }else{
                url = path + "/message/ajax/markReaded.do?value1="+value2+"&value="+id;
            }
            $().invoke(url, null,
                    [function (m, r) {
                        alert(r);
                        refreshTable();
                        Base.token();
                    },
                        function (m, r) {
                            alert(r);
                            Base.token();
                        }]
            );
        }
      function addstartTimeAndEndTime(obj,i){
          var time=$("#time"+i);
          var query="";
          if(i=='1'){
              query="queryMessage";
          }else if(i=='2'){
              query="queryMessage1";
          }else{
              query="queryMessage2";
          }
          var t=$("input[id=starttime"+i+"]");
          if(t.length==0){
              var span="<span style=\"float: left;color: #5F93D7;margin-left:20px;\">从</span><input class=\"form-controlsd \" style=\"float: left;color: #5F93D7;width: 90px;height: 26px;border-color: #d0dde9\" id=\"starttime"+i+"\"  type=\"text\" onfocus=\"WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})\"/>" +
                      "<span style=\"float: left;color: #5F93D7;\">到</span><input class=\"form-controlsd \" style=\"float: left;color: #5F93D7;width: 90px;height: 26px;margin-right: 20px;border-color: #d0dde9\" id=\"endtime"+i+"\"  type=\"text\" onfocus=\"WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})\"/>"+
                      "<input style='border:0;background-color:#ffffff;float: left;color: #5F93D7;height: 26px;' value='确定' onclick='"+query+"();' type='button'>";
              $(time).append(span);
          }
        }
    function queryBySelect(obj){
        if(obj.value=='1'){
            markReaded("true");
        }
        if(obj.value=='2'){
            markReaded("false");
        }
    }
    </script>
</head>
<body>
<%--<div id="MessageGetmymessageListTable" ></div>--%>

<%--<input type="button" value="同步数据" onclick="getBindParm();"/>--%>
    <div class="new_all">
        <div class="here">当前位置：首页 &gt; 客服管理 &gt; <b>messages</b></div>
        <div class="a_bal"></div>
        <div class="new">
            <script type="text/javascript">
                function setTab(name,cursel,n){
                    var table="#MessageGetmymessageListTable";
                    if(cursel!=1){
                        table="#MessageGetmymessageListTable" +(cursel-1);
                    }
                    inintTableOne(table,cursel);
                    for(i=1;i<=n;i++){
                        var menu=document.getElementById(name+i);
                        var con=document.getElementById("con_"+name+"_"+i);
                        menu.className=i==cursel?"new_tab_1":"new_tab_2";
                        con.style.display=i==cursel?"block":"none";
                    }

                }
            </script>
            <div class="new_tab_ls">
                <dt id="menu1" class="new_tab_1" onclick="setTab('menu',1,4)">收件箱</dt>
                <dt id="menu2" class="new_tab_2" onclick="setTab('menu',2,4)">发送成功</dt>
                <dt id="menu3" class="new_tab_2" onclick="setTab('menu',3,4)">发送失败</dt>
               <%-- <dt id="menu4" class="new_tab_2" onclick="setTab('menu',4,4)">发送失败</dt>--%>
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
            <div class="Contentbox">
                <form id="selectForm">
                    <input type="hidden" id="selectAmount" />
                    <input type="hidden" id="selectStatus"/>
                    <input type="hidden" id="selectDay">
                    <input type="hidden" id="selectmessageFrom"/>
                </form>
                <form id="selectForm1">
                    <input type="hidden" id="selectAmount1"/>
                    <input type="hidden" id="selectStatus1"/>
                    <input type="hidden" id="selectDay1">
                </form>
                <form id="selectForm2">
                    <input type="hidden" id="selectAmount2"/>
                    <input type="hidden" id="selectStatus2"/>
                    <input type="hidden" id="selectDay2">
                </form>
                <div id="con_menu_1"  style="display: block;">
                    <!--综合开始 -->
                    <div class="new_usa" style="margin-top:20px;">
                        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">销售状态：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="amount" onclick="selectAmount1(0,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="amount" onclick="selectAmount1(1,'no')">售前&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="amount" onclick="selectAmount1(2,'yes')">售后&nbsp;</span></a></li>
                        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">消息状态：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="status" onclick="selectStatus1(0,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="status"  onclick="selectStatus1(1,'readed');">已读&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="status" onclick="selectStatus1(2,'noRead');">未读&nbsp;</span></a></li>
                        <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">消息来源：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="messageFrom" onclick="selectmessageFrom(0,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="messageFrom"  onclick="selectmessageFrom(1,'ebay');">来自eBay&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="messageFrom" onclick="selectmessageFrom(2,'buyer');">来自买家&nbsp;</span></a></li>
                        <div class="newsearch">
                            <span class="newusa_i" style="width: 75px;">时间：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="days" onclick="selectDays(0,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="days" onclick="selectDays(1,'1');">今天&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="days" onclick="selectDays(2,'2');">昨天&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="days" onclick="selectDays(3,'7');">7天以内&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="days" onclick="selectDays(4,'30');">30天以内&nbsp;</span></a>
                            <%--<span style="float: left;color: #5F93D7;">从</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;border-color: #d0dde9" id="starttime"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>
                            <span style="float: left;color: #5F93D7;">到</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;margin-right: 20px;border-color: #d0dde9" id="endtime"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>--%>
                            <a href="javascript:void(0)" onclick="addstartTimeAndEndTime(this,1)"><span scop="days" onclick="selectDays(5,null);" style="float: left;color: #5F93D7">自定义&nbsp;</span></a><span style="float: left" id="time1"></span>
                        </div><div class="newsearch">
                             <span class="newusa_i" style="width: 75px;">搜索内容：</span>
<span id="sleBG">
<span id="sleHid">
<select id="typeQuery"  name="type" class="select">
    <option value="" selected="selected">选择类型</option>
    <option value="1">发件人</option>
    <option value="2">主题</option>
    <option value="3">SKU</option>

</select>
</span>
</span>
                            <div class="vsearch">
                                <input id="content" name="" type="text" class="key_1"><input onclick="queryMessage();" name="newbut" type="button" class="key_2"></div>
                        </div>
                        <div class="newds">
                            <div class="newsj_left" style="margin-right:15px;padding: 2px;">
                                <span class="newusa_ici_del_in"><input name="checkbox" id="checkbox" type="checkbox" onclick="Allchecked(this);"></span>
                               <%-- <div class="ui-select" style="width:10px">
                                    <select>
                                        <option value="AK">选项</option>
                                        <option value="AK">动作</option>
                                    </select>
                                </div>--%>
                                <div class="numlist">
                                    <div class="ui-select" style="margin-top:1px; width:125px;">
                                        <select onchange="queryBySelect(this)">
                                            <option value="all">全部</option>
                                            <option value="1">标记为已读</option>
                                            <option value="2">标记为未读</option>
                                        </select>
                                    </div>
                                </div>
                                 <%--<span onclick="markReaded()" class="newusa_ici_del">标记为已读</span>--%>
                            </div>
                            <div>
                                <div id="newtipi">
                                   <%-- <li class=""><a href="javascript:void(0)">显示20条</a>
                                        <ul>
                                            <li class=""><a href="javascript:void(0)">自定义显示</a></li>
                                            <li class=""><a href="javascript:void(0)">自定义显示</a></li>
                                            <li class=""><a href="javascript:void(0)">自定义显示</a></li>
                                        </ul>
                                    </li>--%>
                                </div></div>
                            <div class="tbbay"><a data-toggle="modal" href="#userall" onclick="getBindParm();" class="">同步eBay</a></div>
                        </div>
                    </div>
                    <!--综合结束 -->
                    <div style="width: 100%;float: left;height: 5px"></div>
                    <div id="MessageGetmymessageListTable" ></div>
                    <%--<table width="100%" border="0" align="left" cellspacing="0" style="margin-top:20px;">
                        <tbody><tr>
                            <td width="3%" bgcolor="#F7F7F7"></td>
                            <td width="12%" height="30" bgcolor="#F7F7F7"><strong>主题</strong></td>
                            <td height="30" bgcolor="#F7F7F7"><strong>From <img src="../../img/foto.jpg"> to </strong></td>
                            <td width="4%" height="30" align="center" bgcolor="#F7F7F7"><strong style="color:#8BB51B">SKU</strong></td>
                            <td width="14%" align="center" bgcolor="#F7F7F7"><strong>修改时间</strong></td>
                            <td width="10%" align="center" bgcolor="#F7F7F7"><strong>操作</strong></td>
                        </tr>
                        <tr>
                            <td style="color:#5E93D5"><input type="checkbox" name="checkbox" id="checkbox"></td>
                            <td style="color:#5E93D5">Are this better?</td>
                            <td style="color:#5E93D5">alinap.* <img src="../../img/foto.jpg"> containyou</td>
                            <td align="center" style="color:#8BB51B">ZBQ141</td>
                            <td align="center">07/15/2014 09:27</td>
                            <td align="center" valign="top"><div class="ui-select" style="width:106px">
                                <select style="margin-left:-3px;">
                                    <option value="AK">交易详情</option>
                                    <option value="AK">交易详情</option>
                                </select>
                            </div></td>
                        </tr>
                        </tbody></table>--%>
                    <div class="page_newlist">
                        <%--<div>
                            <div id="newtipi">
                                <li><a href="javascript:void(0)">显示20条</a>
                                    <ul>
                                        <li><a href="javascript:void(0)">自定义显示</a></li>
                                        <li><a href="javascript:void(0)">自定义显示</a></li>
                                        <li><a href="javascript:void(0)">自定义显示</a></li>
                                    </ul>
                                </li>
                            </div></div> 共 <span style="color:#F00">3000</span> 条记录 <span style="color:#F00">300</span> 页--%>
                    </div>
                   <%-- <div class="maage_page">
                        <li>&lt;</li>
                        <li class="page_cl">1</li>
                        <li>2</li>
                        <li>3</li>
                        <li>4</li>
                        <dt>&gt;</dt>
                    </div>--%>
                </div>

                <div style="display: none;" id="con_menu_2">
                        <!--综合开始 -->
                        <div class="new_usa" style="margin-top:20px;">
                            <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">销售状态：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="amount2" onclick="selectAmount2(0,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="amount2" onclick="selectAmount2(1,'no')">售前&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="amount2" onclick="selectAmount2(2,'yes')">售后&nbsp;</span></a></li>
                            <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">消息状态：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="status2" onclick="selectStatus2(0,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="status2" onclick="selectStatus2(1,'true');">发送成功&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="status2" onclick="selectStatus2(2,'false');">发送失败&nbsp;</span></a></li>
                            <div class="newsearch">
                                <span class="newusa_i" style="width: 75px;">时间：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="days1" onclick="selectDays1(0,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="days1" onclick="selectDays1(1,'1');">今天&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="days1" onclick="selectDays1(2,'2');">昨天&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="days1" onclick="selectDays1(3,'7');">7天以内&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="days1" onclick="selectDays1(4,'30');">30天以内&nbsp;</span></a>
                                <%--<span style="float: left;color: #5F93D7;">从</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;border-color: #d0dde9" id="starttime1"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>
                                <span style="float: left;color: #5F93D7;">到</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;margin-right: 20px;border-color: #d0dde9" id="endtime1"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>--%>
                                <a href="javascript:void(0)" onclick="addstartTimeAndEndTime(this,2)"><span scop="days1" onclick="selectDays1(5,null);" style="float: left;color: #5F93D7">自定义&nbsp;</span></a><span style="float: left" id="time2"></span>
                            </div><div class="newsearch">
                            <span class="newusa_i" style="width: 75px;">搜索内容：</span>
                                <span id="sleBG">
<span id="sleHid">
<select id="typeQuery1" name="type" class="select">
    <option value=""  selected="selected">选择类型</option>
    <option value="1">发件人</option>
    <option value="2">主题</option>
    <option value="3">SKU</option>
</select>
</span>
</span>
                                <div class="vsearch">
                                    <input id="content1" name="" type="text" class="key_1"><input onclick="queryMessage1();" name="newbut" type="button" class="key_2"></div>
                            </div>
                            <div class="newds">
                                <div class="newsj_left" style="margin-right:15px;">
                                  <%--  <span class="newusa_ici_del_in"><input name="checkbox" id="checkbox" type="checkbox" onclick="Allchecked(this);"></span>
                                    <div class="ui-select" style="width:10px">
                                        <select>
                                            <option value="AK">选项</option>
                                            <option value="AK">动作</option>
                                        </select>
                                    </div>--%>
                                </div>
                                <div>
                                    <div id="newtipi">

                                    </div></div>
                            </div>
                        </div>
                        <!--综合结束 -->
                        <div id="MessageGetmymessageListTable1"></div>

                        <div class="page_newlist">
                    </div>
                </div>
                <div style="display: none;" id="con_menu_3">
                        <!--综合开始 -->
                        <div class="new_usa" style="margin-top:20px;">
                            <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">销售状态：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="amount3" onclick="selectAmount3(0,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="amount3" onclick="selectAmount3(1,'no')">售前&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="amount3" onclick="selectAmount3(2,'yes')">售后&nbsp;</span></a></li>
                            <li class="new_usa_list"><span class="newusa_i" style="width: 75px;">消息状态：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="status3" onclick="selectStatus3(0,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="status3" onclick="selectStatus3(1,'true');">发送成功&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="status3" onclick="selectStatus3(2,'false');">发送失败&nbsp;</span></a></li>
                            <div class="newsearch">
                                <span class="newusa_i" style="width: 75px;">时间：</span><a href="javascript:void(0)"><span class="newusa_ici" scop="days2" onclick="selectDays2(0,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="days2" onclick="selectDays2(1,'1');">今天&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="days2" onclick="selectDays2(2,'2');">昨天&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="days2" onclick="selectDays2(3,'7');">7天以内&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="days2" onclick="selectDays2(4,'30');">30天以内&nbsp;</span></a>
                                <%--<span style="float: left;color: #5F93D7;">从</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;border-color: #d0dde9" id="starttime2"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>
                                <span style="float: left;color: #5F93D7;">到</span><input class="form-controlsd " style="float: left;color: #5F93D7;width: 90px;height: 26px;margin-right: 20px;border-color: #d0dde9" id="endtime2"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>--%>
                                <a href="javascript:void(0)" onclick="addstartTimeAndEndTime(this,3)"><span scop="days2" onclick="selectDays2(5,null);" style="float: left;color: #5F93D7">自定义&nbsp;</span></a><span style="float: left" id="time3"></span>
                            </div><div class="newsearch">
                            <span class="newusa_i" style="width: 75px;">搜索内容：</span>
                                <span id="sleBG">
<span id="sleHid">
<select id="typeQuery2" name="type" class="select">
    <option value=""  selected="selected">选择类型</option>
    <option value="1">发件人</option>
    <option value="2">主题</option>
    <option value="3">SKU</option>
</select>
</span>
</span>
                                <div class="vsearch">
                                    <input id="content2" name="" type="text" class="key_1"><input onclick="queryMessage2();" name="newbut" type="button" class="key_2"></div>
                            </div>
                            <div class="newds">
                                <div class="newsj_left" style="margin-right:15px;">
                                 <%--   <span class="newusa_ici_del_in"><input name="checkbox" id="checkbox" type="checkbox" onclick="Allchecked(this);"></span>
                                    <div class="ui-select" style="width:10px">
                                        <select>
                                            <option value="AK">选项</option>
                                            <option value="AK">动作</option>
                                        </select>
                                    </div>--%>
                                </div>
                                <div>
                                    <div id="newtipi">

                                    </div></div>

                            </div>
                        </div>
                        <!--综合结束 -->
                        <div id="MessageGetmymessageListTable2"></div>
                        <div class="page_newlist">

                        </div>

                    </div>
            </div>
        </div></div>
</body>
</html>
