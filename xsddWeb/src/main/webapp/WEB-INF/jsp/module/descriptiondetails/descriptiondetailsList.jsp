<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/30
  Time: 11:33
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script>
        function addDescriptionDetails(){
            $.dialog({title: '新增卖家描述',
                content: 'url:/xsddWeb/addDescriptionDetails.do',
                icon: 'succeed',
                width:1025
            });
        }
        function editDescriptionDetails(id){
            $.dialog({title: '编辑卖家描述',
                content: 'url:/xsddWeb/editDescriptionDetails.do?id='+id,
                icon: 'succeed',
                width:1025
            });
        }
        $(document).ready(function(){
            $("#descriptionDetailsListTable").initTable({
                url:path + "/ajax/loadDescriptionDetailsList.do",
                columnData:[
                    {title:"名称",name:"name",width:"8%",align:"left"},
                    {title:"pay",name:"payInfo",width:"8%",align:"left"},
                    {title:"shipping",name:"shippingInfo",width:"8%",align:"left"},
                    {title:"contact",name:"contactInfo",width:"8%",align:"left"},
                    {title:"Guarantee",name:"guaranteeInfo",width:"8%",align:"left"},
                    {title:"Feedback",name:"feedbackInfo",width:"8%",align:"left"},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:true
            });
            $("#descriptionDetailsListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        });
        /**组装操作选项*/
        function makeOption1(json){
            var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"editDescriptionDetails('"+json.id+"');\">编辑</a>";
            return htm;
        }
    </script>
</head>
<body>
<div style="text-align: right;">
    <input type="button" name="addDescriptionDetails" value="新增" onclick="addDescriptionDetails();">
</div>
<div id="descriptionDetailsListTable"></div>
<%--<div>
    <table width="100%">
        <tr>
            <td>名称</td>
            <td>pay</td>
            <td>shipping</td>
            <td>contact</td>
            <td>Guarantee</td>
            <td>Feedback</td>
        </tr>
        <c:forEach items="${DescriptionDetailsli}" var="li">
            <tr>
                <td>${li.name}</td>
                <td>${li.payInfo}</td>
                <td>${li.shippingInfo}</td>
                <td>${li.contactInfo}</td>
                <td>${li.guaranteeInfo}</td>
                <td>${li.feedbackInfo}</td>
                <td width="3%"><a target="_blank" href="javascript:void(0)" onclick="editDescriptionDetails('${li.id}');">编辑</a></td>
            </tr>
        </c:forEach>
    </table>
</div>--%>
</body>
</html>
