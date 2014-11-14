<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/5
  Time: 10:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var TemplateInitTable;
        function addTemplateInitTable(){
            TemplateInitTable=openMyDialog({title: '新增模板选项',
                content: 'url:/xsddWeb/addTemplateInitTable.do',
                icon: 'succeed',
                width:1025,
                lock:true
            });
        }
        function editTemplateInitTable(id){
            TemplateInitTable=openMyDialog({title: '编辑模板描述',
                content: 'url:/xsddWeb/editTemplateInitTable.do?id='+id,
                icon: 'succeed',
                width:1025,
                lock:true
            });
        }
        function viewTemplateInitTable(id){
            TemplateInitTable=openMyDialog({title: '查看模板',
                content: 'url:/xsddWeb/viewTemplateInitTable.do?id='+id,
                icon: 'succeed',
                width:1025,
                height:750,
                lock:true
            });
        }

        $(document).ready(function(){
            $("#templateInitTableListTable").initTable({
                url:path + "/ajax/loadTemplateInitTableList.do?",
                columnData:[
                    {title:"模板名字",name:"templateName",width:"8%",align:"left"},
                    {title:"tLevel",name:"level",width:"8%",align:"left",format:makeOption2},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:true
            });
            refreshTable();
        });
        function refreshTable(){
            $("#templateInitTableListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        /**组装操作选项*/
        function makeOption1(json){
            var hs="";
            hs+="<li style='height:25px' onclick=viewTemplateInitTable('"+json.id+"') value='"+json.id+"' doaction=\"look\" >查看</li>";
            hs+="<li style='height:25px' onclick=editTemplateInitTable('"+json.id+"') value='"+json.id+"' doaction=\"look\" >编辑</li>";
            var pp={"liString":hs,"marginLeft":"-50px"};
            return getULSelect(pp);
        }
        function makeOption2(json){
            var htm=json.tLevel;
            if(htm==1){
                return "用户自定义";
            }else if(htm==0){
                return "预设";
            }
        }
    </script>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
</head>
<body>
<div class="new_all">
    <div class="here">当前位置：首页 > 刊登管理 > <b>模板管理</b></div>
    <div class="a_bal"></div>
    <div class="tbbay" style="position: absolute;top: 46px;right: 40px;z-index: 10000;"><a data-toggle="modal" href="javascript:void(0)" class=""  onclick="addTemplateInitTable()">新增模板</a></div>
    <%--<div class="a_bal"></div>--%>
    <div id="cent">
        <div id="templateInitTableListTable"></div>
    </div>
</div>

</body>
</html>
