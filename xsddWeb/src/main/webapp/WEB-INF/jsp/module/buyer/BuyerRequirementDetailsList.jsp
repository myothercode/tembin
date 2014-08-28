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
        var buyerRequire;
        function addBuyer(){
            buyerRequire=$.dialog({title: '新增买家要求',
                content: 'url:/xsddWeb/addBuyer.do',
                icon: 'succeed',
                width:500,
                lock:true
            });
        }

        function editBuyer(id){
            buyerRequire=$.dialog({title: '编辑买家要求',
                content: 'url:/xsddWeb/editBuyer.do?id='+id,
                icon: 'succeed',
                width:500,
                lock:true
            });
        }

        $(document).ready(function(){
            $("#buyerRequireTable").initTable({
                url:path + "/ajax/loadBuyerRequirementDetailsList.do",
                columnData:[
                    {title:"名称",name:"name",width:"8%",align:"left"},
                    {title:"站点",name:"siteName",width:"8%",align:"left"},
                    {title:"所有买家购买",name:"buyerFlag",width:"8%",align:"left"},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:true
            });
            refreshTable();
        });
        function refreshTable(){
            $("#buyerRequireTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        /**组装操作选项*/
        function makeOption1(json){
            var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"editBuyer('"+json.id+"');\">编辑</a>";
            return htm;
        }

    </script>
</head>
<body>
<div style="text-align: right;">
    <input type="button" name="add" value="新增" onclick="addBuyer()">
</div>
<div id="buyerRequireTable">

</div>
<%--<div>
    <table width="100%">
        <tr>
            <td>名称</td>
            <td>站点</td>
            <td>所有买家购买</td>
        </tr>
        <c:forEach items="${li}" var="li">
            <tr>
                <td>${li.name}</td>
                <td>
                    ${li.siteName}
                </td>
                <td>${li.buyerFlag}</td>
            </tr>
        </c:forEach>
    </table>
</div>--%>
</body>
</html>
