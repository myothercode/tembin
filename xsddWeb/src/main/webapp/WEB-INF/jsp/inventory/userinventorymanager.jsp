<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/17
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        $(document).ready(function() {
            loadTableData();
        });
        function loadTableData(){
            var clod = [
                    {title:"选择",name:"id",width:"3%",align:"left"},
                    {title:"第三方仓库",name:"dataType",width:"6%",align:"center"},
                    {title:"用户名",name:"userName",width:"8%",align:"center"},
                    {title:"用户key",name:"userKey",width:"5%",align:"center"},
                    {title:"&nbsp;&nbsp;&nbsp;&nbsp;操作",name:"option1",width:"4%",align:"left",format:makeOption1}
                ];
            $("#InventoryList").initTable({
                url:path+"/inventoryset/ajax/loadUserInventoryList.do",
                columnData:clod,
                selectDataNow:false,
                isrowClick:false,
                showIndex:false,
                isrowClick: true,
                rowClickMethod: function (obj,o){

                    if($(event.target).prop("type")=="checkbox"){
                        return;
                    }
                    if($("input[type='checkbox'][name='modelid'][val='"+obj.id+"']").prop("checked")){
                        $("input[type='checkbox'][name='modelid'][val='" + obj.id + "']").prop("checked", false);
                    }else {
                        $("input[type='checkbox'][name='modelid'][val='" + obj.id + "']").prop("checked", true);
                    }
                }
            });
            refreshTable();
        }

        function  refreshTable(){
            var param={};
            $("#InventoryList").selectDataAfterSetParm(param);
        }
        var inventory
        function addInventorySet() {
            inventory = $.dialog({title: '仓库',
                content: 'url:/xsddWeb/inventoryset/addInventorySet.do',
                icon: 'succeed',
                width: 600,
                lock: true
            });
        }

        /**组装操作选项*/
        function makeOption1(json){
            var hs="";
            hs+="<li style='height:25px' onclick=editInventoryselect('"+json.id+"') value='"+json.id+"' doaction=\"look\" >查看</li>";
            hs+="<li style='height:25px' onclick=editInventory('"+json.id+"') value='"+json.id+"' doaction=\"look\" >编辑</li>";
            hs+="<li style='height:25px' onclick=delInventory('" + json.id + "') value='" + json.id + "' doaction=\"look\" >删除</li>";
            var pp={"liString":hs,"marginLeft":"-50px"};
            return getULSelect(pp);
        }

        function editInventoryselect(id){
            inventory=$.dialog({title: '查看仓库',
                content: 'url:/xsddWeb/inventoryset/editInventorySet.do?id='+id+'&type=01',
                icon: 'succeed',
                width:600,
                lock:true
            });
        }

        function editInventory(id){
            inventory=$.dialog({title: '编辑仓库',
                content: 'url:/xsddWeb/inventoryset/editInventorySet.do?id='+id,
                icon: 'succeed',
                width:600,
                lock:true
            });
        }

        function delInventory(id){
            var url=path+"/inventoryset/ajax/delInventorySet.do?id="+id;
            $().invoke(url,{},
                    [function(m,r){
                        alert(r);
                        refreshTable();
                    },
                        function(m,r){
                            alert(r);
                        }]
            );
        }

    </script>
</head>
<body>
<div class="new_all">
    <div class="here">当前位置：首页 > 系统管理 > <b>库存管理</b></div>
    <div class="a_bal"></div>
    <div class="new">
        <div class="new_tab_ls" id="tab">

        </div>
        <div class="tbbay" id="showAddButton" style="background-image:url('');position: absolute;top: 60px;right: 40px;z-index: 10000;">
            <img src="../img/adds.png" onclick="addInventorySet()">
        </div>
        <div class=Contentbox>
            <div style="width: 100%;float: left;height: 5px"></div>
            <div id="InventoryList" >
            </div>
        </div>

    </div>
</div>
</body>
</html>
