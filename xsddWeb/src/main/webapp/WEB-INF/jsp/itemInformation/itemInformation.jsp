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
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.config.js" /> ></script>
    <%--产品信息--%>
    <script type="text/javascript">
        var itemInformation;
        $(document).ready(function(){
            $("#ItemInformationListTable").initTable({
                url:path + "/information/ajax/loadItemInformationList.do?",
                columnData:[
                    {title:"",name:"pictureUrl",width:"8%",align:"left",format:makeOption4},
                    {title:"图片",name:"pictureUrl",width:"8%",align:"left",format:makeOption2},
                    {title:"商品 / SKU",name:"sku",width:"8%",align:"left"},
                    {title:"商品名称",name:"name",width:"8%",align:"left"},
                    {title:"标签",name:"remark",width:"8%",align:"left"},
                    {title:"分类",name:"typeName",width:"8%",align:"left"},
                    {title:"状态",name:"pictureUrl",width:"8%",align:"left",format:makeOption3},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });
        function refreshTable(){
            $("#ItemInformationListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        function makeOption1(json){
            var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"printSku('"+json.id+"');\">打印SKU</a>";
            return htm;
        }
        function makeOption2(json){
            var htm="<img src="+json.pictureUrl+" style=\" width: 50px;height: 50px; \">";
            return htm;
        }
        function makeOption3(json){
            if(json.pictureUrl){
                var htm = "<img src='"+path+"/img/new_yes.png' />";
                return htm;
            }else{
                var htm = "<img src='"+path+"/img/new_no.png' />";
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
                width:1050
            });
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
                console.debug(data);
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
                    width:1050
                });
            }else if(id.length>1){
                alert("请选择单个需要修改的数据");
            }else{
                alert("请选择需要修改的数据");
            }
        }
        function addRemark(){
            var id=$("input[type='checkbox'][name='templateId']:checked");
            if(id.length==1){
                var url=path+"/information/addRemark.do?id="+$(id).val();
                itemInformation=$.dialog({title: '添加标签',
                    content: 'url:'+url,
                    icon: 'succeed',
                    width:1050
                });
            }else if(id.length>1){
                alert("请选择单个需要添加标签的商品");
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
                width:1050
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
                    {title:"",name:"pictureUrl",width:"8%",align:"left",format:makeOption4},
                    {title:"图片",name:"pictureUrl",width:"8%",align:"left",format:makeOption2},
                    {title:"商品 / SKU",name:"sku",width:"8%",align:"left"},
                    {title:"商品名称",name:"name",width:"8%",align:"left"},
                    {title:"标签",name:"remark",width:"8%",align:"left"},
                    {title:"分类",name:"typeName",width:"8%",align:"left"},
                    {title:"状态",name:"pictureUrl",width:"8%",align:"left",format:makeOption3},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            $("#ItemInformationListTable").selectDataAfterSetParm();
            refreshTable2(remark,information,itemType,content);
        }
        function submitCommit(){
            var itemType=$("#itemTypeid").val();
            var content=$("#content").val();
            submitCommit1(null,null,itemType,content);
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
            var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"addChildType('"+json.typeId+"');\">添加子分类</a>";
            return htm;
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
    </script>
</head>
<body>
<div class="content">
    <div class="new_all">
        <div class="here">当前位置：首页 &gt; 刊登管理 &gt; <b>账户管理</b></div>
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
            <h2>eBay官方调整E邮宝接口，截至九月底将停止使用原E邮宝，需要您重新授权E邮宝</h2>
            <div class="new_tab_ls">
                <dt id="menu1" class="new_tab_2" onclick="setTab('menu',1,5)">商品列表</dt>
                <dt id="menu2" class="new_tab_1" onclick="setTab('menu',2,5)">商品分类列表</dt>
            </div>
            <div class="Contentbox">
                <div>
                    <div id="con_menu_1" class="hover" style="display: none;">
                        <!--综合开始 -->
                        <div class="new_usa" style="margin-top:20px;">
                            <li class="new_usa_list"><span class="newusa_i">按标签看：全部</span><a href="#"><span class="newusa_ici_1" onclick="submitCommit1('null',null,null,null)">无标签</span></a><a href="#"><span class="newusa_ici_1">有电池</span></a><a href="#"><span class="newusa_ici_1">无电池</span></a></li>
                            <li class="new_usa_list"><span class="newusa_i">信息状态：全部</span><a href="#"><span class="newusa_ici_1" onclick="submitCommit1(null,'picture',null,null)">无图片</span></a><a href="#"><span class="newusa_ici_1" onclick="submitCommit1(null,'custom',null,null)">无报关信息</span></a><a href="#"><span class="newusa_ici_1" onclick="submitCommit1(null,'notAll',null,null)">信息不全</span></a></li>
                            <div class="newsearch">
                                <span class="newusa_i">搜索内容：全部</span>
<span id="sleBG">
<span id="sleHid">
<select id="itemTypeid" name="itemType" class="select">
    <option value="all">选择类型</option>
    <c:forEach items="${types}" var="type">
        <option value="${type.id}">${type.configName}</option>
    </c:forEach>
</select>
</span>
</span>
                                <div class="vsearch">
                                    <input id="content" name="content" type="text" class="key_1"><input name="newbut" type="button" class="key_2" onclick="submitCommit();"></div>
                            </div>
                            <div class="newds">
                                <div class="newsj_left">
                                    <span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="checkbox"></span>
                                    <span class="newusa_ici_del" onclick="addItemInformation();">添加商品</span>
                                    <span class="newusa_ici_del" onclick="importItemInformation();">导入商品</span>
                                    <span class="newusa_ici_del" onclick="exportItemInformation();">导出商品</span>
                                    <span class="newusa_ici_del" onclick="addRemark();">添加标签</span>
                                    <span class="newusa_ici_del" onclick="removeItemInformation();">删除商品</span>
                                    <span class="newusa_ici_del" onclick="updateItemInformation();">修改商品分类</span>
                                </div>
                            </div>
                        </div>
                        <div id="ItemInformationListTable"></div>
                        <!--综合结束 -->
                    </div>
                    <div id="con_menu_2">
                        <div class="new_usa" style="margin-top:20px;">
                            <div class="newds">
                                <div class="tbbay"><a href="#" onclick="addType();">添加分类</a></div>
                            </div>
                        </div>
                        <div id="ItemInformationTypeListTable"></div>
                    </div>
                    <!--结束 -->
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
