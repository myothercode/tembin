<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/16
  Time: 11:10
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
        var sendGetmymessage;
        $(document).ready(function(){
            $("#sendMessageTable").initTable({
                url:path + "/sendMessage/ajax/loadSendMessageList.do?status=1",
                columnData:[
                    {title:"模板名称",name:"name",width:"8%",align:"center"},
                    {title:"<a href=\"javascript:void(0)\" onclick=\"orderBy(this,'asc');\">消息类型</a>",name:"content",width:"8%",align:"center",format:makeOption4},
                    {title:"状态",name:"status",width:"8%",align:"center",format:makeOption2},
                    {title:"操作",name:"countNum",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });
        function refreshTable1(url){
            $("#sendMessageTable").initTable({
                url:url,
                columnData:[
                    {title:"模板名称",name:"name",width:"8%",align:"center"},
                    {title:"<a href=\"javascript:void(0)\" onclick=\"orderBy(this,'asc');\">消息类型</a>",name:"content",width:"8%",align:"center",format:makeOption4},
                    {title:"状态",name:"status",width:"8%",align:"center",format:makeOption2},
                    {title:"操作",name:"countNum",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        }
        function orderBy(obj,orderby){
            var htm="";
            if(orderby=="asc"){
               /* htm="&nbsp;<a href=\"javascript:void(0)\" onclick=\"orderBy(this,'desc');\"><img src=\"<c:url value ="/img/desc.png" />\"/></a>";*/
                htm="<a href=\"javascript:void(0)\" onclick=\"orderBy(this,'desc');\">消息类型</a>";
            }
            if(orderby=="desc"){
                 htm="<a href=\"javascript:void(0)\" onclick=\"orderBy(this,'asc');\">消息类型</a>";
            }
            var status=0;
            var checkbox=document.getElementById("checkboxStatus");
            if(checkbox.checked){
                status=0
            }else{
                status=1;
            }
            var url=path + "/sendMessage/ajax/loadSendMessageList.do?status="+status+"&orderby="+orderby;
            $("#sendMessageTable").initTable({
                url:url,
                columnData:[
                    {title:"模板名称",name:"name",width:"8%",align:"center"},
                    {title:htm,name:"content",width:"8%",align:"center",format:makeOption4},
                    {title:"状态",name:"status",width:"8%",align:"center",format:makeOption2},
                    {title:"操作",name:"countNum",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        }
        function refreshTable(){
            $("#sendMessageTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        /**查看发送消息*/
        function makeOption1(json){
            /*var htm="<div class=\"ui-select\" style=\"width:8px\"><a href=\"javascript:void(0)\" onclick=\"deleteSendMessage("+json.transactionid+","+json.messagetype+");\">删除</a></div>";
            return htm;*/
            var hs="";
            hs="<li style=\"height:25px;\" onclick=editMessageTemplate("+json.id+"); value='1' doaction=\"readed\" >编辑</li>";
            if(json.status==0){
                hs+="<li style=\"height:25px;\" onclick=useStatus("+json.id+",'1'); value='1' doaction=\"look\" >启用</li>";
            }else{
                hs+="<li style=\"height:25px;\" onclick=useStatus("+json.id+",'0'); value='1' doaction=\"look\" >禁用</li>";
            }
            hs+="<li style=\"height:25px;\" onclick=deleteSendMessage("+json.id+"); value='1' doaction=\"look\" >删除</li>";
            var pp={"liString":hs};
            return getULSelect(pp);
        }
        function makeOption4(json){
            var htm="";
            if(json.casetype==1){
                htm+="CASE"+"/";
            }
            if(json.autotype==1){
                htm+="自动消息"+"/";
            }
            if(json.messagetype==1){
                htm+="Message"+"/";
            }
            if(htm==""){
                return htm;
            }else{
                return htm.substring(0,htm.length-1);
            }
        }
        function makeOption3(json){
            var htm = "<input type=\"checkbox\"  name=\"templateId\" value=" + json.id + ">";
            return htm;
        }
        function useStatus(id,status){
            var url=path+"/sendMessage/useStatus.do?id="+id+"&status="+status;
            $().invoke(url,null,
                    [function(m,r){
                        alert(r);
                        Base.token();
                        refreshTable();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function deleteSendMessage(id){
            var url=path+"/sendMessage/ajax/removesendMessage.do?id="+id;
            $().invoke(url,null,
                    [function(m,r){
                        alert(r);
                        Base.token();
                        refreshTable();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function makeOption2(json){
            if(json.status==1){
                var htm = "<img src='"+path+"/img/new_yes.png' />";
                return htm;
            }else{
                var htm = "<img src='"+path+"/img/new_no.png' />";
                return htm;
            }
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
        function addMessageTemplate(){
            var url=path+'/sendMessage/addMessageTemplate.do?';
            sendGetmymessage=openMyDialog({title: '添加或编辑',
                content: 'url:'+url,
                icon: 'succeed',
                width:850,
                height:500,
                lock:true
            });
        }
        function editMessageTemplate(id){
            var url=path+'/sendMessage/addMessageTemplate.do?id='+id;
            sendGetmymessage=openMyDialog({title: '添加或编辑',
                content: 'url:'+url,
                icon: 'succeed',
                width:850,
                height:500,
                lock:true
            });
        }
        function viewForbidden(obj){
            var url=path + "/sendMessage/ajax/loadSendMessageList.do?status="+status;
            if(obj.checked){
                url=path + "/sendMessage/ajax/loadSendMessageList.do?status=0";
            }else{
                url=path + "/sendMessage/ajax/loadSendMessageList.do?status=1";
            }
            $("#sendMessageTable").initTable({
                url:url,
                columnData:[
                    {title:"模板名称",name:"name",width:"8%",align:"center"},
                    {title:"<a href=\"javascript:void(0)\" onclick=\"orderBy(this,'asc');\">消息类型</a>",name:"content",width:"8%",align:"center",format:makeOption4},
                    {title:"状态",name:"status",width:"8%",align:"center",format:makeOption2},
                    {title:"操作",name:"countNum",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        }
        function onclickType(count,name){
            var types=$("span[scop=type]");
            for(var i=0;i<types.length;i++){
                if(i==count){
                    $(types[i]).attr("class","newusa_ici");
                    $("#type").val(name);
                }else{
                    $(types[i]).attr("class","newusa_ici_1");
                }
            }
        }
        function selectDays(count,name){
            var days=$("span[scop=days]");

            for(var i=0;i<days.length;i++){
                if(i==count){
                    $(days[i]).attr("class","newusa_ici");
                    $("#time").val(name);
                }else{
                    $(days[i]).attr("class","newusa_ici_1");
                }
            }
        }
        /*function querySelect(){
            var type=$("#type").val();
            var time=$("#time").val();
            var itemType=$("#itemTypeid").val();
            var content2=$("#content2").val();
            $("#sendMessageTable").initTable({
                url:path + "/sendMessage/ajax/loadSendMessageList.do",
                columnData:[
                    {title:"",name:"pictureUrl",width:"2%",align:"center",format:makeOption3},
                    {title:"模板名称",name:"name",width:"8%",align:"center"},
                    {title:"内容",name:"content",width:"8%",align:"center"},
                    {title:"状态",name:"status",width:"8%",align:"center",format:makeOption2},
                    {title:"操作",name:"countNum",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            $("#sendMessageTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0,"type":type,"time":time,"itemType":itemType,"content2":content2});

        }*/

    </script>
</head>
<body>

    <div class="new_all">
        <div class="here">当前位置：首页 &gt; 客户管理 &gt; <b>消息模板</b></div>
        <div class="a_bal"></div>
        <div class="new">
            <div class="Contentbox">
                <div>
                    <!--综合开始 -->
                    <%--<div class="new_usa">
                        <div class="newds">
                            <div class="newsj_left">
                                <span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="checkbox" onclick="Allchecked(this);"></span>
                                <span class="newusa_ici_del">检查eBay费</span>&lt;%&ndash;<div class="page_num">显示20条</div>&ndash;%&gt;
                            </div>
                        </div>
                        <div class="tbbay"><a href="javascript:void(0)">添加模板</a></div>
                    </div>--%>
                   <%-- <div class="new_usa" style="margin-top:20px;">
                        <li class="new_usa_list"><span class="newusa_i">按类型看：</span><a href="javascript:void(0)"><span class="newusa_ici_1" scop="type" onclick="onclickType(0,null)">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="type" onclick="onclickType(1,'caseType')">CASE&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="type" onclick="onclickType(2,'autoType')">自动消息&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="type" onclick="onclickType(3,'messageType')">一般消息&nbsp;</span></a></li>
                        &lt;%&ndash;<li class="new_usa_list"><span class="newusa_i">信息状态：</span><span class="newusa_ici_1" scop="information" onclick="onclickinformation(null,0)">全部&nbsp;</span><a href="javascript:void(0)"><span class="newusa_ici_1" scop="information" onclick="onclickinformation('picture',1)">无图片&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="information" onclick="onclickinformation('custom',2)">无报关信息&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="information" onclick="onclickinformation('notAllnull',3)">信息不全&nbsp;</span></a></li>
                        &ndash;%&gt;
                        <div class="newsearch">
                            <span class="newusa_i">创建时间：</span><a href="javascript:void(0)"><span class="newusa_ici_1" scop="days" onclick="selectDays(0,null);">全部&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="days" onclick="selectDays(1,'1');">今天&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="days" onclick="selectDays(2,'2');">昨天&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="days" onclick="selectDays(3,'7');">7天以内&nbsp;</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" scop="days" onclick="selectDays(4,'30');">30天以内&nbsp;</span></a>
                         </div>
                        <div class="newsearch">
                            <span class="newusa_i">搜索内容：</span>

<span id="sleBG">
<span id="sleHid">
<select id="itemTypeid" name="itemType" class="select">
    <option value="0">选择类型</option>
    <option value="1">模板名称</option>
    <option value="2">内容</option>
</select>
</span>
</span>
                            <div class="vsearch">
                                <input id="content2" name="content2" type="text" class="key_1"><input name="newbut" type="button" class="key_2" onclick="querySelect();"></div>
                            <input type="hidden" id="type">
                            <input type="hidden" id="time">

                        </div>--%>

                    </div>
                    <li class="new_usa_list"><span class="newusa_ii"><input id="checkboxStatus" type="checkbox" value="" onclick="viewForbidden(this);"></span><span <%--class="newusa_ici_2"--%> <%--onclick="viewForbidden();"--%>>显示已停用的模板</span><div class="tbbay"><a data-toggle="modal" href="#myModal" onclick="addMessageTemplate();" class="">增加模板</a></div></li>
                    <div id="sendMessageTable"></div>
                    <!--综合结束 -->
                </div>

            </div>
        </div>
    </div>

</body>

</html>
