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
                    {title:"主题",name:"read",width:"8%",align:"left",format:makeOption2,click:makeOption1},
                    {title:"From > to",name:"sender",width:"8%",align:"left",format:makeOption3,click:makeOption1},
                    {title:"SKU",name:"sendtoname",width:"8%",align:"left",click:makeOption1},
                    {title:"修改时间",name:"receivedate",width:"8%",align:"left",click:makeOption1},
                    {title:"总数",name:"countNum",width:"8%",align:"left",click:makeOption1}
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
        function refreshTable1(amount,status,day,type,content){
            $("#MessageGetmymessageListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0,"amount":amount,"status":status,"day":day,"type":type,"content":content});

        }
        function refreshTable2(amount,status,day,type,content){
            $("#MessageGetmymessageListTable1").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0,"amount":amount,"status":status,"day":day,"type":type,"content":content});

        }
        function refreshTable3(amount,status,day,type,content){
            $("#MessageGetmymessageListTable2").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0,"amount":amount,"status":status,"day":day,"type":type,"content":content});

        }
        /**查看消息*/
        function makeOption1(json){
            var url=path+"/message/viewMessageGetmymessage.do";
            MessageGetmymessage=$.dialog({title: '查看消息',
                content: 'url:'+url+'?messageID='+json.messageid,
                icon: 'succeed',
                width:1025,
                height:500
            });
        }
        function makeOption2(json){
            var detail=json.subject;
            if(detail.length>25){
                detail=detail.substring(0,26)+"...<br/>";
            }
            return "<span style=\"color:#5E93D5\">"+detail+"</span>";
        }
        function makeOption3(json){
            return "<span style=\"color:#5E93D5\">"+json.sender+">"+json.recipientuserid+"</span>";
        }
        function makeOption4(json){
            var htm = "<input type=\"checkbox\"  name=\"templateId\" value=" + json.id + ">";
            return htm;
        }
        function makeOption5(json){
            var url=path+"/message/viewMessageAddmymessage.do";
            MessageGetmymessage=$.dialog({title: '查看消息',
                content: 'url:'+url+'?messageID='+json.messageid,
                icon: 'succeed',
                width:1025,
                height:400
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
                }else{
                    $(days[i]).attr("class","newusa_ici_1");
                }
            }
        }
        function selectDays1(count,name){
            var days=$("span[scop=days1]");
            for(var i=0;i<days.length;i++){
                if(i==(count)){
                    $(days[i]).attr("class","newusa_ici");
                    $("#selectDay1").val(name);
                }else{
                    $(days[i]).attr("class","newusa_ici_1");
                }
            }
        }
        function selectDays2(count,name){
            var days=$("span[scop=days2]");
            for(var i=0;i<days.length;i++){
                if(i==(count)){
                    $(days[i]).attr("class","newusa_ici");
                    $("#selectDay2").val(name);
                }else{
                    $(days[i]).attr("class","newusa_ici_1");
                }
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
            $("#MessageGetmymessageListTable").initTable({
                url:path + "/message/ajax/loadMessageGetmymessageList.do?",
                columnData:[
                    {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption4},
                    {title:"主题",name:"read",width:"8%",align:"left",format:makeOption2,click:makeOption1},
                    {title:"From > to",name:"sender",width:"8%",align:"left",format:makeOption3,click:makeOption1},
                    {title:"SKU",name:"sendtoname",width:"8%",align:"left",click:makeOption1},
                    {title:"修改时间",name:"receivedate",width:"8%",align:"left",click:makeOption1},
                    {title:"总数",name:"countNum",width:"8%",align:"left",click:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable1(amount,status,day,type,content);
            alert("查询成功");
        }
        function queryMessage1(){
            var amount=$("#selectAmount1").val();
            var status=$("#selectStatus1").val();
            var day=$("#selectDay1").val();
            var type=$("#typeQuery").val();
            var content=$("#content").val();
            var replied="true";
            $("#MessageGetmymessageListTable1").initTable({
                url:path + "/message/ajax/loadMessageAddmymessageList.do?replied="+replied,
                columnData:[
                    {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption4},
                    {title:"主题",name:"read",width:"8%",align:"left",format:makeOption2,click:makeOption5},
                    {title:"From > to",name:"sender",width:"8%",align:"left",format:makeOption3,click:makeOption5},
                    {title:"SKU",name:"sender",width:"8%",align:"left",click:makeOption5},
                    {title:"修改时间",name:"createTime",width:"8%",align:"left",click:makeOption5},
                    {title:"总数",name:"countNum",width:"8%",align:"left",click:makeOption5}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable2(amount,status,day,type,content);
            alert("查询成功");
        }
        function queryMessage2(){
            var amount=$("#selectAmount2").val();
            var status=$("#selectStatus2").val();
            var day=$("#selectDay2").val();
            var type=$("#typeQuery").val();
            var content=$("#content").val();
            var replied="false";
            $("#MessageGetmymessageListTable2").initTable({
                url:path + "/message/ajax/loadMessageAddmymessageList.do?replied="+replied,
                columnData:[
                    {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption4},
                    {title:"主题",name:"read",width:"8%",align:"left",format:makeOption2,click:makeOption5},
                    {title:"From > to",name:"sender",width:"8%",align:"left",format:makeOption3,click:makeOption5},
                    {title:"SKU",name:"sender",width:"8%",align:"left",click:makeOption5},
                    {title:"修改时间",name:"createTime",width:"8%",align:"left",click:makeOption5},
                    {title:"总数",name:"countNum",width:"8%",align:"left",click:makeOption5}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable3(amount,status,day,type,content);
            alert("查询成功");
        }
        function inintTableOne(table,cursel){
            if(cursel==1){
                $(table).initTable({
                    url:path + "/message/ajax/loadMessageGetmymessageList.do?",
                    columnData:[
                        {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption4},
                        {title:"主题",name:"subject",width:"8%",align:"left",format:makeOption2,click:makeOption1},
                        {title:"From > to",name:"sender",width:"8%",align:"left",format:makeOption3,click:makeOption1},
                        {title:"SKU",name:"sendtoname",width:"8%",align:"left",click:makeOption1},
                        {title:"修改时间",name:"receivedate",width:"8%",align:"left",click:makeOption1},
                        {title:"总数",name:"countNum",width:"8%",align:"left",click:makeOption1}
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
                        {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption4},
                        {title:"主题",name:"read",width:"8%",align:"left",format:makeOption2,click:makeOption5},
                        {title:"From > to",name:"sender",width:"8%",align:"left",format:makeOption3,click:makeOption5},
                        {title:"SKU",name:"sender",width:"8%",align:"left",click:makeOption5},
                        {title:"修改时间",name:"createTime",width:"8%",align:"left",click:makeOption5},
                        {title:"总数",name:"countNum",width:"8%",align:"left",click:makeOption5}
                    ],
                    selectDataNow:false,
                    isrowClick:false,
                    showIndex:false
                });
            }
            $(table).selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
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
                    <input type="hidden" id="selectAmount"/>
                    <input type="hidden" id="selectStatus"/>
                    <input type="hidden" id="selectDay">
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
                        <li class="new_usa_list"><span class="newusa_i">选择账号：</span><a href="#"><span class="newusa_ici_1" scop="amount" onclick="selectAmount1(0,null);">全部&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="amount" onclick="selectAmount1(1,null)">固价&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="amount" onclick="selectAmount1(2,null)">多属性&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="amount" onclick="selectAmount1(3,null)">多属性&nbsp;</span></a></li>
                        <li class="new_usa_list"><span class="newusa_i">消息状态：</span><a href="#"><span class="newusa_ici_1" scop="status" onclick="selectStatus1(0,null);">全部&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="status"  onclick="selectStatus1(1,'readed');">已读&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="status" onclick="selectStatus1(2,'noRead');">未读&nbsp;</span></a></li>
                        <div class="newsearch">
                            <span class="newusa_i">消息来源：</span><a href="#"><span class="newusa_ici_1" scop="days" onclick="selectDays(0,null);">全部&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="days" onclick="selectDays(1,'1');">今天&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="days" onclick="selectDays(2,'2');">昨天&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="days" onclick="selectDays(3,'7');">7天以内&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="days" onclick="selectDays(4,'30');">30天以内&nbsp;</span></a>
<span id="sleBG">
<span id="sleHid">
<select name="type" class="select">
    <option id="typeQuery" selected="selected">选择类型</option>
    <option value="1">图书</option>
    <option value="2">音像</option>
</select>
</span>
</span>
                            <div class="vsearch">
                                <input id="content" name="" type="text" class="key_1"><input onclick="queryMessage();" name="newbut" type="button" class="key_2"></div>
                        </div>
                        <div class="newds">
                            <div class="newsj_left" style="margin-right:15px;">
                                <span class="newusa_ici_del_in"><input name="checkbox" id="checkbox" type="checkbox" onclick="Allchecked(this);"></span>
                                <div class="ui-select" style="width:10px">
                                    <select>
                                        <option value="AK">选项</option>
                                        <option value="AK">动作</option>
                                    </select>
                                </div>
                            </div>
                            <div>
                                <div id="newtipi">
                                   <%-- <li class=""><a href="#">显示20条</a>
                                        <ul>
                                            <li class=""><a href="#">自定义显示</a></li>
                                            <li class=""><a href="#">自定义显示</a></li>
                                            <li class=""><a href="#">自定义显示</a></li>
                                        </ul>
                                    </li>--%>
                                </div></div>
                            <div class="tbbay"><a data-toggle="modal" href="#userall" onclick="getBindParm();" class="">同步eBay</a></div>
                        </div>
                    </div>
                    <!--综合结束 -->
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
                                <li><a href="#">显示20条</a>
                                    <ul>
                                        <li><a href="#">自定义显示</a></li>
                                        <li><a href="#">自定义显示</a></li>
                                        <li><a href="#">自定义显示</a></li>
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
                            <li class="new_usa_list"><span class="newusa_i">选择账号：</span><a href="#"><span class="newusa_ici_1" scop="amount2" onclick="selectAmount2(0,null);">全部&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="amount2" onclick="selectAmount2(1,null)">固价&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="amount2" onclick="selectAmount2(2,null)">多属性&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="amount2" onclick="selectAmount2(3,null)">多属性&nbsp;</span></a></li>
                            <li class="new_usa_list"><span class="newusa_i">消息状态：</span><a href="#"><span class="newusa_ici_1" scop="status2" onclick="selectStatus2(0,null);">全部&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="status2" onclick="selectStatus2(1,'true');">发送成功&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="status2" onclick="selectStatus2(2,'false');">发送失败&nbsp;</span></a></li>
                            <div class="newsearch">
                                <span class="newusa_i">消息来源：</span><a href="#"><span class="newusa_ici_1" scop="days1" onclick="selectDays1(0,null);">全部&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="days1" onclick="selectDays1(1,'1');">今天&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="days1" onclick="selectDays1(2,'2');">昨天&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="days1" onclick="selectDays1(3,'7');">7天以内&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="days1" onclick="selectDays1(4,'30');">30天以内&nbsp;</span></a>
<span id="sleBG">
<span id="sleHid">
<select name="type" class="select">
    <option id="typeQuery" selected="selected">选择类型</option>
    <option value="1">图书</option>
    <option value="2">音像</option>
</select>
</span>
</span>
                                <div class="vsearch">
                                    <input id="content" name="" type="text" class="key_1"><input onclick="queryMessage1();" name="newbut" type="button" class="key_2"></div>
                            </div>
                            <div class="newds">
                                <div class="newsj_left" style="margin-right:15px;">
                                    <span class="newusa_ici_del_in"><input name="checkbox" id="checkbox" type="checkbox" onclick="Allchecked(this);"></span>
                                    <div class="ui-select" style="width:10px">
                                        <select>
                                            <option value="AK">选项</option>
                                            <option value="AK">动作</option>
                                        </select>
                                    </div>
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
                            <li class="new_usa_list"><span class="newusa_i">选择账号：</span><a href="#"><span class="newusa_ici_1" scop="amount3" onclick="selectAmount3(0,null);">全部&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="amount3" onclick="selectAmount3(1,null)">固价&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="amount3" onclick="selectAmount3(2,null)">多属性&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="amount3" onclick="selectAmount3(3,null)">多属性&nbsp;</span></a></li>
                            <li class="new_usa_list"><span class="newusa_i">消息状态：</span><a href="#"><span class="newusa_ici_1" scop="status3" onclick="selectStatus3(0,null);">全部&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="status3" onclick="selectStatus3(1,'true');">发送成功&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="status3" onclick="selectStatus3(2,'false');">发送失败&nbsp;</span></a></li>
                            <div class="newsearch">
                                <span class="newusa_i">消息来源：</span><a href="#"><span class="newusa_ici_1" scop="days2" onclick="selectDays2(0,null);">全部&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="days2" onclick="selectDays2(1,'1');">今天&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="days2" onclick="selectDays2(2,'2');">昨天&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="days2" onclick="selectDays2(3,'7');">7天以内&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="days2" onclick="selectDays2(4,'30');">30天以内&nbsp;</span></a>
<span id="sleBG">
<span id="sleHid">
<select name="type" class="select">
    <option id="typeQuery" selected="selected">选择类型</option>
    <option value="1">图书</option>
    <option value="2">音像</option>
</select>
</span>
</span>
                                <div class="vsearch">
                                    <input id="content" name="" type="text" class="key_1"><input onclick="queryMessage2();" name="newbut" type="button" class="key_2"></div>
                            </div>
                            <div class="newds">
                                <div class="newsj_left" style="margin-right:15px;">
                                    <span class="newusa_ici_del_in"><input name="checkbox" id="checkbox" type="checkbox" onclick="Allchecked(this);"></span>
                                    <div class="ui-select" style="width:10px">
                                        <select>
                                            <option value="AK">选项</option>
                                            <option value="AK">动作</option>
                                        </select>
                                    </div>
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
