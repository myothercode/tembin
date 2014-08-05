<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script>
        var returnShipping ="";
        function addshippingDetails(){
            returnShipping=$.dialog({title: '新增运送选项',
                content: 'url:/xsddWeb/addshippingDetails.do',
                icon: 'succeed',
                width:1000
            });
        }

        function editshippingDetails(id){
            returnShipping=$.dialog({title: '编辑运送选项',
                content: 'url:/xsddWeb/editshippingDetails.do?id='+id,
                icon: 'succeed',
                width:1000
            });
        }
        $(document).ready(function(){
            $("#shippingDetailsList").initTable({
                url:path + "/ajax/loadShippingDetailsList.do",
                columnData:[
                    {title:"名称",name:"shippingName",width:"8%",align:"left"},
                    {title:"站点",name:"siteName",width:"8%",align:"left"},
                    {title:"ebay账号",name:"ebayName",width:"8%",align:"left"},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:true
            });
            refreshTable();
        });

        function refreshTable(){
            $("#shippingDetailsList").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        /**组装操作选项*/
        function makeOption1(json){
            var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"editshippingDetails('"+json.id+"');\">编辑</a>";
            return htm;
        }
    </script>
</head>
<body>
<div style="text-align: right;">
    <input type="button" name="add" value="新增" onclick="addshippingDetails()">
</div>
<div id="shippingDetailsList">

</div>
</body>
</html>
