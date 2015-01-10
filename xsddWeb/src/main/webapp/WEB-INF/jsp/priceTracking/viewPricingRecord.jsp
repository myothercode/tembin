<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2015/1/7
  Time: 19:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function(){
            var url=path+"/priceTracking/ajax/priceTrackingRecordQueryList.do?autoPricingId=${autoPricingId}";
            $("#priceTrackingAutoPricingRecordListTable").initTable({
                url:url,
                columnData:[
                    /*   {title:"",name:"ch",width:"2%",align:"top",format:makeOption5},*/
                    {title:"SKU",name:"sku",width:"25%",align:"center"},
                    {title:"调价前的价格",name:"beforeprice",width:"25%",align:"center",format:makeOption2},
                    {title:"调价后的价格",name:"afterprice",width:"25%",align:"center",format:makeOption3},
                    {title:"调价日期",name:"createTime",width:"25%",align:"center"}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });
        function makeOption2(json){
            return json.beforeprice+"$";
        }
        function makeOption3(json){
            return json.afterprice+"$";
        }
        function refreshTable(){
            $("#priceTrackingAutoPricingRecordListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
    </script>
</head>
<body>
<div id="priceTrackingAutoPricingRecordListTable"></div>
</body>
</html>
