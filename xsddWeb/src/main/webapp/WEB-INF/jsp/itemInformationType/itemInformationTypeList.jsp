<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/10
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var itemInformationType;
        $(document).ready(function(){
            $("#ItemInformationTypeListTable").initTable({
                url:path + "/informationType/ajax/loadItemInformationTypeList.do?",
                columnData:[
                    {title:"分类名称",name:"typeName",width:"8%",align:"left"},
                    {title:"商品数",name:"countNum",width:"8%",align:"left"},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:true
            });
            refreshTable();
        });
        function refreshTable(){
            $("#ItemInformationTypeListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        function makeOption1(json){
         /*   var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"addChildType('"+json.typeId+"');\">添加子分类</a>";*/
            var htm="<div class=\"ui-select\" style=\"margin-top:1px; width:120px\">" +
            "<select onclick=\"addChildType('"+json.typeId+"');\">" +
            "<option value=\"1\">添加子分类</option>" +
            "</select>" +
            "</div>";
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
<div align="right">
    <input type="button" value="添加分类" onclick="addType();"/>
</div>
<div id="ItemInformationTypeListTable"></div>
</body>
</html>
