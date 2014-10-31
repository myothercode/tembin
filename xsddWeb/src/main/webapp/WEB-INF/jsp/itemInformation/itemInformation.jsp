<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/15
  Time: 15:39
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
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.config.js" /> ></script>
    <%--产品信息--%>
    <script type="text/javascript">
        var itemInformation;
        $(document).ready(function(){
            $("#ItemInformationListTable").initTable({
                url:path + "/information/ajax/loadItemInformationList.do?",
                columnData:[
                    {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption4,click:sellectCheckBox},
                    {title:"图片",name:"pictureUrl",width:"8%",align:"left",format:makeOption2,click:sellectCheckBox},
                    {title:"商品/SKU",name:"sku",width:"8%",align:"left",click:sellectCheckBox},
                    {title:"产品名称",name:"name",width:"8%",align:"left",click:sellectCheckBox},
                    {title:"标签",name:"remark",width:"8%",align:"left",click:sellectCheckBox},
                    {title:"分类",name:"typeName",width:"8%",align:"left",click:sellectCheckBox},
                    {title:"状态",name:"pictureUrl",width:"8%",align:"left",format:makeOption3,click:sellectCheckBox},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });
        function sellectCheckBox(json){
            var checkbox=$("input[type=checkbox][name=templateId][value="+json.id+"]");
            if(checkbox[0].checked){
                checkbox[0].checked=false;
            }else{
                checkbox[0].checked=true;
            }
        }
        function refreshTable(){
            $("#ItemInformationListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        function makeOption1(json){
            /*var htm="<div class=\"ui-select\"  style=\"margin-top:1px; width:120px\">" +
                    "<select onchange=\"selectOption("+json.id+",this);\">" +
                    "<option value=\"0\">--请选择--</option>" +
                    "<option value=\"1\">添加标签</option>" +
                    "</select>" +
                    "</div>";
            return htm;*/
            var hs="";
            hs+="<li style=\"height:25px;\" onclick=selectOption("+json.id+",this); value='1' doaction=\"readed\" >快速刊登</li>";
            hs+="<li style=\"height:25px;\" onclick=selectOption("+json.id+",this); value='2' doaction=\"look\" >编辑</li>";
            hs+="<li style=\"height:25px;\" onclick=selectOption("+json.id+",this); value='3' doaction=\"look\" >删除</li>";
            hs+="<li style=\"height:25px;\" onclick=selectOption("+json.id+",this); value='4' doaction=\"look\" >备注</li>";
            var pp={"liString":hs};
            return getULSelect(pp);
        }
        function selectOption(id,obj){
            var value=$(obj).val();
            if(value=='1'){
                editItem(id);
            }
            if(value=='2'){
                updateItemInformation1(id);
            }
            if(value=='3'){
                removeItemInformation1(id);
            }
            if(value=='4'){
                addComment(id);
            }
        }
        function editItem(id){
            var url = path + "/information/editItem.do?id="+id;
            itemInformation=$.dialog({title: '快速刊登',
                content: 'url:'+url,
                icon: 'succeed',
                width:1050,
                height:700,
                lock:true
            });
        }
        function addComment(id){
            var url = path + "/information/addComment.do?id="+id;
            itemInformation=$.dialog({title: '备注',
                content: 'url:'+url,
                icon: 'succeed',
                width:600,
                lock:true
            });
        }
        function makeOption2(json){
            var htm="<img src="+json.pictureUrl+" style=\" width: 50px;height: 50px; \">";
            return htm;
        }
        function makeOption3(json){
            if(json.pictureUrl){
                var htm = "<img src='"+path+"/img/new_yes.png'/>";
                return htm;
            }else{
                var htm = "<img src='"+path+"/img/new_no.png'/>";
                return htm;
            }
        }
        function makeOption4(json){
            var htm = "<input type=\"checkbox\"  name=\"templateId\" value=" + json.id + ">";
            return htm;
        }
        function addItemInformation(){
            var url=path+"/information/addItemInformation.do";
            itemInformation=$.dialog({title: '添加或修改商品信息',
                content: 'url:'+url,
                icon: 'succeed',
                width:1050,
                height:700,
                lock:true
            });
        }
        function removeItemInformation1(id) {
            var url = path + "/information/ajax/removeItemInformation.do?id[0]="+id;
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
        function removeItemInformation(){
            var id=$("input[type='checkbox'][name='templateId']:checked");
            if(id.length>0){
                var str="";
                for(var i=0;i<id.length;i++){
                    if(i!=id.length-1){
                        str+="\"id["+i+"]\":"+$(id[i]).val()+",";
                    }else{
                        str+="\"id["+i+"]\":"+$(id[i]).val();
                    }
                }
                var data1= "{"+str+"}";
                var data= eval('(' + data1 + ')');
                var url=path+"/information/ajax/removeItemInformation.do";
                $().invoke(url,data,
                        [function(m,r){
                            alert(r);
                            refreshTable();
                            Base.token();
                        },
                            function(m,r){
                                alert(r);
                                Base.token();
                            }]
                );
            }else{
                alert("请选择要删除的数据");
            }
        }
        function updateItemInformation(){
            var id=$("input[type='checkbox'][name='templateId']:checked");
            if(id.length==1){
                var url=path+"/information/addItemInformation.do?id="+$(id[0]).val();
                itemInformation=$.dialog({title: '添加或修改商品信息',
                    content: 'url:'+url,
                    icon: 'succeed',
                    width:1050,
                    height:700,
                    lock:true
                });
            }else if(id.length>1){
                alert("请选择单个需要修改的数据");
            }else{
                alert("请选择需要修改的数据");
            }
        }
        function updateItemInformation1(id){
            var url=path+"/information/addItemInformation.do?id="+id;
            itemInformation=$.dialog({title: '添加或修改商品信息',
                content: 'url:'+url,
                icon: 'succeed',
                width:1050,
                height:700,
                lock:true
            });
        }
        function addRemark(id1){
            var id=null;
            var id2="";
            if(!id1){
                 id=$("input[type='checkbox'][name='templateId']:checked");
                for(var i=0;i<id.length;i++){
                    if(i==(id.length-1)){
                       id2= id2+$(id[i]).val();
                    }else{
                        id2= id2+$(id[i]).val()+",";
                    }
                }
            }else{
                 id=$("input[type=checkbox][name=templateId][value="+id1+"]");
                 id2=$(id).val();
            }
            if(id.length>0){
                var url=path+"/information/addRemark.do?id="+id2;
                itemInformation=$.dialog({title: '添加标签',
                    content: 'url:'+url,
                    icon: 'succeed',
                    width:1050,
                    lock:true
                });
            }else{
                alert("请选择需要添加标签的商品");
            }
        }
        function exportItemInformation(){
            var id=$("input[type='checkbox'][name='templateId']:checked");
            if(id.length>0){
                var str="";
                for(var i=0;i<id.length;i++){
                    if(i!=id.length-1){
                        str+="\"id["+i+"]\":"+$(id[i]).val()+",";
                    }else{
                        str+="\"id["+i+"]\":"+$(id[i]).val();
                    }
                }
                var data1= "{"+str+"}";
                var data= eval('(' + data1 + ')');
                var url=path+"/information/exportItemInformation1.do";
                for(var i=0;i<id.length;i++){
                    if(i==0){
                        url=url+"?id["+i+"]="+$(id[i]).val();
                    }else{
                        url=url+"&id["+i+"]="+$(id[i]).val();
                    }
                }
                window.open(url);
            }else{
                alert("请选择要导出的数据");
            }
        }
        function importItemInformation(){
            var url=path+"/information/importItemInformation.do";
            itemInformation=$.dialog({title: '请选择导入的excel文件',
                content: 'url:'+url,
                icon: 'succeed',
                width:1050,
                lock:true
            });
        }
        function submitCommit1(remark,information,itemType,content){
            /*var remark=$("#remarkid").val();
            var information=$("#information").val();
            var itemType=$("#itemTypeid").val();
            var content=$("#content").val();*/
            $("#ItemInformationListTable").initTable({
                url:path + "/information/ajax/loadItemInformationList.do?",
                columnData:[
                    {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption4,click:sellectCheckBox},
                    {title:"图片",name:"pictureUrl",width:"8%",align:"left",format:makeOption2,click:sellectCheckBox},
                    {title:"商品/SKU",name:"sku",width:"8%",align:"left",click:sellectCheckBox},
                    {title:"产品名称",name:"name",width:"8%",align:"left",click:sellectCheckBox},
                    {title:"标签",name:"remark",width:"8%",align:"left",click:sellectCheckBox},
                    {title:"分类",name:"typeName",width:"8%",align:"left",click:sellectCheckBox},
                    {title:"状态",name:"pictureUrl",width:"8%",align:"left",format:makeOption3,click:sellectCheckBox},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            $("#ItemInformationListTable").selectDataAfterSetParm();
            refreshTable2(remark,information,itemType,content);
        }
        function onclickremark(remark,n){
            var arr=$("span[scop=remark]");
            for(var i=0;i<arr.length;i++){
                if(i==n){
                    $(arr[i]).attr("class","newusa_ici");
                    $("#remarkForm").val(remark);
                }else{
                    $(arr[i]).attr("class","newusa_ici_1");
                }
            }
        }
        function onclickinformation(information,n){
            var arr=$("span[scop=information]");
            for(var i=0;i<arr.length;i++){
                if(i==n){
                    $(arr[i]).attr("class","newusa_ici");
                    $("#informationForm").val(information);
                }else{
                    $(arr[i]).attr("class","newusa_ici_1");
                }
            }
        }
        function submitCommit(){

            var itemType=$("#itemTypeid").val();
            var content=$("#content").val();
            var remark=$("#remarkForm").val();
            var information=$("#informationForm").val();
            submitCommit1(remark,information,itemType,content);
        }
        function refreshTable2(remark,information,itemType,content){
            $("#ItemInformationListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0,"remark":remark,"information":information,"itemType":itemType,"content":content});
        }
    </script>
    <%--产品信息分类--%>
    <script type="text/javascript">
        var itemInformationType;
        $(document).ready(function(){
            $("#ItemInformationTypeListTable").initTable({
                url:path + "/informationType/ajax/loadItemInformationTypeList.do?",
                columnData:[
                    {title:"分类名称",name:"typeName",width:"8%",align:"left"},
                    {title:"商品数",name:"countNum",width:"8%",align:"left"},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption5}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:true
            });
            refreshTable1();
        });
        function refreshTable1(){
            $("#ItemInformationTypeListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        function makeOption5(json){
           /* var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"addChildType('"+json.typeId+"');\">添加子分类</a>";*/
          /*  var htm="<div class=\"ui-select\" style=\"margin-top:1px; width:120px\">" +
                    "<select onclick=\"addChildType('"+json.typeId+"');\">" +
                    "<option value=\"1\">添加子分类</option>" +
                    "</select>" +
                    "</div>";
            return htm;*/
            var hs="";
            hs="<li style=\"height:25px;width:75px; \" onclick=addChildType('"+json.typeId+"'); value='1' doaction=\"readed\" >添加子分类</li>";
            var pp={"liString":hs};
            return getULSelect(pp);

        }

        function addType(){
            var url=path+"/informationType/addType.do?";
            itemInformationType=$.dialog({title: '添加商品分类',
                content: 'url:'+url,
                icon: 'succeed',
                width:400,
                lock:true
            });
        }
        function addChildType(id){
            var url=path+"/informationType/addType.do?id="+id;
            itemInformationType=$.dialog({title: '添加子分类',
                content: 'url:'+url,
                icon: 'succeed',
                width:400,
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
    </script>
</head>
<body>
    <div class="new_all" <%--style="width: "--%>>
        <div class="here">当前位置：首页 &gt; 商品管理 &gt; <b>商品模板</b></div>
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

            <div class="new_tab_ls">
                <dt id="menu1" class="new_tab_1" onclick="setTab('menu',1,5)">商品列表</dt>
                <dt id="menu2" class="new_tab_2" onclick="setTab('menu',2,5)">商品分类列表</dt>
            </div>
            <div class="Contentbox">
                <div>
                    <div id="con_menu_1" style="display: block;">
                        <!--综合开始 -->
                        <div class="new_usa" style="margin-top:20px;">
                            <li class="new_usa_list"><span class="newusa_i">按标签看：</span><span class="newusa_ici" scop="remark" onclick="onclickremark(null,0)">全部&nbsp;</span><a href="#"><span class="newusa_ici_1" scop="remark" onclick="onclickremark('null',1)">无标签&nbsp;</span></a><a href="#"><span class="newusa_ici_1">有电池&nbsp;</span></a><a href="#"><span class="newusa_ici_1">无电池&nbsp;</span></a></li>
                            <li class="new_usa_list"><span class="newusa_i">信息状态：</span><span  class="newusa_ici" scop="information" onclick="onclickinformation(null,0)">全部&nbsp;</span><a href="#"><span class="newusa_ici_1" scop="information" onclick="onclickinformation('picture',1)">无图片&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="information" onclick="onclickinformation('custom',2)">无报关信息&nbsp;</span></a><a href="#"><span class="newusa_ici_1" scop="information" onclick="onclickinformation('notAllnull',3)">信息不全&nbsp;</span></a></li>
                            <div class="newsearch">
                                <span class="newusa_i">搜索内容：</span>

<span id="sleBG">
<span id="sleHid">
<select id="itemTypeid" name="itemType" class="select">
    <option value="all">选择类型</option>
    <option value="1">SKU</option>
    <option value="2">商品名称</option>
</select>
</span>
</span>
                                <div class="vsearch">
                                    <input id="content" name="content" type="text" class="key_1"><input name="newbut" type="button" class="key_2" onclick="submitCommit();"></div>
                                    <input type="hidden" id="remarkForm"/>
                                    <input type="hidden" id="informationForm"/>
                            </div>
                            <div class="newds">
                                <div class="newsj_left">
                                    <span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="checkbox" onclick="Allchecked(this);"></span>
                                    <span class="newusa_ici_del" onclick="addItemInformation();">添加商品</span>
                                    <span class="newusa_ici_del" onclick="importItemInformation();">导入商品</span>
                                    <span class="newusa_ici_del" onclick="exportItemInformation();">导出商品</span>
                                    <span class="newusa_ici_del" onclick="addRemark();">添加标签</span>
                                    <span class="newusa_ici_del" onclick="removeItemInformation();">删除商品</span>
                                    <span class="newusa_ici_del" onclick="updateItemInformation();">修改商品分类</span>
                                </div>
                            </div>
                        </div>
                        <div style="width: 100%;float: left;height: 5px"></div>
                        <div id="ItemInformationListTable"></div>
                        <div class="page_newlist">

                        </div>
                        <!--综合结束 -->
                    </div>
                    <div id="con_menu_2" style="display: none;">
                        <div class="new_usa" style="margin-top:20px;">
                            <div class="newds">
                                <div class="tbbay"><a href="#" onclick="addType();">添加分类</a></div>
                            </div>
                        </div>
                        <div style="width: 100%;float: left;height: 5px"></div>
                        <div id="ItemInformationTypeListTable"></div>
                    </div>
                    <!--结束 -->
                </div>
            </div>
        </div>
    </div>
</body>
</html>
