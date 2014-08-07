<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/6
  Time: 16:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var MessageGetmymessage;

        $(document).ready(function(){
            $("#MessageGetmymessageListTable").initTable({
                url:path + "/ajax/loadMessageGetmymessageList.do?",
                columnData:[
                    {title:"发送的ebay账号",name:"ebayAccountName",width:"8%",align:"left",click:makeOption1},
                    {title:"接受的ebay账号",name:"sendtoname",width:"8%",align:"left",click:makeOption1},
                    {title:"总数",name:"countNum",width:"8%",align:"left",click:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:true
            });
            refreshTable();
        });
        function refreshTable(){
            $("#MessageGetmymessageListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        /**查看消息*/
        function makeOption1(json){
            MessageGetmymessage=$.dialog({title: '查看消息',
                content: 'url:/xsddWeb/viewMessageGetmymessage.do?ebayAccountId='+json.ebayAccountId+'&sendtoname='+json.sendtoname,
                icon: 'succeed',
                width:1025,
                height:500
            });
        }

    </script>
</head>
<body>
<div id="MessageGetmymessageListTable" ></div>
</body>
</html>
