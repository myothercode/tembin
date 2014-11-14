<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/11/14
  Time: 11:21
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
        $(document).ready(function() {
            $("#viewSystemListTable").initTable({
                url: path + "/order/ajax/loadSystemList.do?",
                columnData: [
                    {title: "描述", name: "eventdesc", width: "8%", align: "center"},
                    {title: "UserName", name: "operuser", width: "8%", align: "center"},
                    {title: "操作时间", name: "createdate", width: "8%", align: "center" +
                            ""}
                ],
                selectDataNow: false,
                isrowClick: false,
                showIndex: false
            });
            refreshTable();
        });
        function refreshTable(){
            $("#viewSystemListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
    </script>
</head>
<body>
<div id="viewSystemListTable"></div>
</body>
</html>
