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
    <script type="text/javascript">
        var payPal;
        function addPayPal(){
            payPal=$.dialog({title: '新增付款选项',
                content: 'url:/xsddWeb/addPayPal.do',
                icon: 'succeed',
                width:500,
                lock:true
            });
        }

        function editPayPal(id){
            payPal=$.dialog({title: '编辑付款选项',
                content: 'url:/xsddWeb/editPayPal.do?id='+id,
                icon: 'succeed',
                width:500,
                lock:true
            });
        }

        $(document).ready(function(){
            $("#paypallisttable").initTable({
                url:path + "/ajax/loadPayPalList.do",
                columnData:[
                    {title:"名称",name:"payName",width:"8%",align:"left"},
                    {title:"站点",name:"siteName",width:"8%",align:"left"},
                    {title:"paypal账号",name:"payPalName",width:"8%",align:"left"},
                    {title:"描述",name:"paymentinstructions",width:"8%",align:"left"},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:true
            });
            refreshTable();
        });
        function  refreshTable(){
            $("#paypallisttable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
/**组装操作选项*/
function makeOption1(json){
var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"editPayPal('"+json.id+"');\">编辑</a>";
    return htm;
}

    </script>
</head>
<body>
<div style="text-align: right;">
    <input type="button" name="addPayPal" value="新增" onclick="addPayPal();">
</div>
<div id="paypallisttable">

</div>
<div>
    <%--<table width="100%">
        <tr>
            <td>名称</td>
            <td>站点</td>
            <td>paypal账号</td>
            <td>描述</td>
            <td>操作</td>
        </tr>
        <c:forEach items="${paypalli}" var="li">
            <tr>
                <td>${li.payName}</td>
                <td>
                    ${li.siteName}
                </td>
                <td>${li.payPalName}</td>
                <td>${li.paymentinstructions}</td>
                <td>
                    <a target="_blank" href="javascript:void(0)" onclick="editPayPal('${li.id}');">编辑</a>
                </td>
            </tr>
        </c:forEach>
    </table>--%>
</div>
</body>
</html>
