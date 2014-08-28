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
            TemplateInitTable=$.dialog({title: '新增模板选项',
                content: 'url:/xsddWeb/addTemplateInitTable.do',
                icon: 'succeed',
                width:1025,
                lock:true
            });
        }
        function editTemplateInitTable(id){
            TemplateInitTable=$.dialog({title: '编辑模板描述',
                content: 'url:/xsddWeb/editTemplateInitTable.do?id='+id,
                icon: 'succeed',
                width:1025,
                lock:true
            });
        }
        function viewTemplateInitTable(id){
            TemplateInitTable=$.dialog({title: '查看模板',
                content: 'url:/xsddWeb/viewTemplateInitTable.do?id='+id,
                icon: 'succeed',
                width:1025,
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
            var htm1="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"editTemplateInitTable('"+json.id+"');\">编辑</a>";
            htm=htm1+"|<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"viewTemplateInitTable('"+json.id+"');\">查看</a>";
            return htm;
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
</head>
<body>
<div style="text-align: right;">
    <input type="button" value="新增模板" onclick="addTemplateInitTable()">
</div>
<div id="templateInitTableListTable"></div>
</body>
</html>
