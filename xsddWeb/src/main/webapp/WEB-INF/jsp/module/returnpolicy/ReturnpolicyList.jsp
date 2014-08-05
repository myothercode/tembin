<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/29
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var returnPolicy;
        function addReturnpolicy(){
            returnPolicy=$.dialog({title: '新增退款选项',
                content: 'url:/xsddWeb/addReturnpolicy.do',
                icon: 'succeed',
                width:500,
                lock:true
            });
        }
        function editReturnpolicy(id){
            returnPolicy=$.dialog({title: '编辑退款选项',
                content: 'url:/xsddWeb/editReturnpolicy.do?id='+id,
                icon: 'succeed',
                width:500,
                lock:true
            });
        }

        $(document).ready(function(){
            $("#returnPolicyListTable").initTable({
                url:path + "/ajax/loadReturnpolicyList.do?",
                columnData:[
                    {title:"名称",name:"name",width:"8%",align:"left"},
                    {title:"站点",name:"siteName",width:"8%",align:"left"},
                    {title:"退货政策",name:"returnsAcceptedOptionName",width:"8%",align:"left"},
                    {title:"退货天数",name:"returnsWithinOptionName",width:"8%",align:"left"},
                    {title:"退款方式",name:"refundOptionName",width:"8%",align:"left"},
                    {title:"退货运费由谁负担",name:"shippingCostPaidByOptionName",width:"8%",align:"left"},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:true
            });
            refreshTable();
        });
        function refreshTable(){
            $("#returnPolicyListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        /**组装操作选项*/
        function makeOption1(json){
            var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"editReturnpolicy('"+json.id+"');\">编辑</a>";
            return htm;
        }
    </script>
</head>
<body>
<div style="text-align: right;">
    <input type="button" name="addReturnpolicy" value="新增" onclick="addReturnpolicy();">
</div>
<div id="returnPolicyListTable"></div>
</body>
</html>
