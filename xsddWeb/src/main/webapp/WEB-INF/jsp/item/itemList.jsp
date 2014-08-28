<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/2
  Time: 9:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script>
        var returnItem ="";
        function addItem(){
            //window.open("/xsddWeb/addItem.do");
            returnItem=$.dialog({title: '新增商品',
                content: 'url:/xsddWeb/addItem.do',
                icon: 'succeed',
                width:1000
            });
        }

        function editItem(id){
            returnItem=$.dialog({title: '编辑商品',
                content: 'url:/xsddWeb/editItem.do?id='+id,
                icon: 'succeed',
                width:1000
            });
        }

        $(document).ready(function(){
            $("#itemTable").initTable({
                url:path + "/ajax/loadItemList.do",
                columnData:[
                    {title:"商品名称",name:"itemName",width:"8%",align:"left"},
                    {title:"站点",name:"siteName",width:"8%",align:"left"},
                    {title:"标题",name:"title",width:"8%",align:"left"},
                    {title:"数量",name:"quantity",width:"8%",align:"left"},
                    {title:"SKU",name:"sku",width:"8%",align:"left"},
                    {title:"刊登状态",name:"isFlag",width:"8%",align:"left"},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:true
            });
            refreshTable();
        });
        /**组装操作选项*/
        function makeOption1(json){
            var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"editItem('"+json.id+"');\">编辑</a>";
            return htm;
        }
        function  refreshTable(){
            $("#itemTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
    </script>
</head>
<body>
<div>
    <input type="button" value="新增商品" onclick="addItem()">
</div>

<div id="itemTable">

</div>
</body>
</html>
