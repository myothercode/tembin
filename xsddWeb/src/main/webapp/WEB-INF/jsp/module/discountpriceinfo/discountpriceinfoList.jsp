<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script>
        var discountPriceInfo;
        function adddiscountpriceinfo(){
            discountPriceInfo= $.dialog({title: '新增折扣选项',
                content: 'url:/xsddWeb/addDiscountPriceInfo.do',
                icon: 'succeed',
                width:400,
                lock:true
            });
        }

        function editdiscountpriceinfo(id){
            discountPriceInfo= $.dialog({title: '编辑折扣选项',
                content: 'url:/xsddWeb/editDiscountPriceInfo.do?id='+id,
                icon: 'succeed',
                width:500,
                lock:true
            });
        }
        $(document).ready(function(){
            $("#discountPriceInfoListTable").initTable({
                url:path + "/ajax/loadDiscountPriceInfoList.do",
                columnData:[
                    {title:"名称",name:"name",width:"8%",align:"left"},
                    {title:"账户名称",name:"ebayName",width:"8%",align:"left"},
                    {title:"开始时间",name:"disStarttime",width:"8%",align:"left"},
                    {title:"结束时间",name:"disEndtime",width:"8%",align:"left"},
                    {title:"降价",name:"madeforoutletcomparisonprice",width:"8%",align:"left"},
                    {title:"是否免运费",name:"isShippingfee",width:"8%",align:"left"},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:true
            });
            refreshTable();
        });
        function refreshTable() {
            $("#discountPriceInfoListTable").selectDataAfterSetParm({"bedDetailVO.deptId": "", "isTrue": 0});
        }
        /**组装操作选项*/
        function makeOption1(json){
            var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"editdiscountpriceinfo('"+json.id+"');\">编辑</a>";
            return htm;
        }
    </script>
</head>
<body>
<div style="text-align: right;">
    <input type="button" name="add" value="新增" onclick="adddiscountpriceinfo()">
</div>
<div id="discountPriceInfoListTable"></div>
<%--<div>
    <table width="100%">
        <tr>
            <td>名称</td>
            <td>账户名称</td>
            <td>开始时间</td>
            <td>结束时间</td>
            <td>折扣</td>
            <td>降价</td>
            <td>是否免运费</td>
            <td>操作</td>
        </tr>
        <c:forEach items="${disli}" var="li">
            <tr>
                <td>${li.name}</td>
                <td>
                    ${li.ebayName}
                </td>
                <td>
                    <fmt:formatDate value="${li.disStarttime}" pattern="yyyy-MM-dd HH:mm"/>
                </td>
                <td>
                    <fmt:formatDate value="${li.disStarttime}" pattern="yyyy-MM-dd HH:mm"/>
                </td>
                <td>
                        ${li.madeforoutletcomparisonprice}
                </td>
                <td>${li.minimumadvertisedprice}</td>
                <td>
                    <c:if test="${li.isShippingfee=='1'}">
                        <c:out value="是"/>
                    </c:if>
                    <c:if test="${li.isShippingfee!='1'}">
                        <c:out value="否"/>
                    </c:if>
                </td>
                <td>
                    <a target="_blank" href="javascript:void(0)" onclick="editdiscountpriceinfo('${li.id}')">编辑</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>--%>
</body>
</html>
