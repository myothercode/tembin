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
        $(document).ready(function(){
            $("#itemTable").initTable({
                url:path + "/ajax/ListingItemList.do",
                columnData:[
                    {title:"商品Title",name:"title",width:"8%",align:"left"},
                    {title:"商品站点",name:"site",width:"8%",align:"left"},
                    {title:"商品SKU",name:"SKU",width:"8%",align:"left"},
                    {title:"商品数量",name:"quantity",width:"8%",align:"left"},
                    {title:"操作",name:"Option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:true
            });
            refreshTable();
        });
        /**组装操作选项*/
        function makeOption1(json){
            var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"edit('"+json.itemID+"');\">在线编辑</a>";
            return htm;
        }
        function  refreshTable(){
            $("#itemTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        //在线编辑
        var editPage = "";
        function edit(itemid){
            editPage = $.dialog({title: '编辑在线商品',
                content: 'url:/xsddWeb/editListingItem.do?itemid='+itemid,
                icon: 'succeed',
                width:1000
            });
        }
    </script>
</head>
<body>
<div id="itemTable">
</div>
</body>
</html>
