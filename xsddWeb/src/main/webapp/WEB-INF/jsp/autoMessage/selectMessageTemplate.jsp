<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/10/15
  Time: 16:51
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
        var api = frameElement.api, W = api.opener;
        $(document).ready(function(){
            $("#sendMessageTable").initTable({
                url:path + "/sendMessage/ajax/loadSendMessageList.do?status=1",
                columnData:[
                    {title:"模板名称",name:"name",width:"8%",align:"left"},
                    {title:"内容",name:"content",width:"8%",align:"left",format:makeOption4},
                    {title:"状态",name:"status",width:"8%",align:"left",format:makeOption2}
                ],

                selectDataNow:false,
                isrowClick:true,
                rowClickMethod:selectRow,
                showIndex:false
            });
            refreshTable();
        });
        function refreshTable(){
            $("#sendMessageTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        function makeOption4(json){
            var htm="";
            if(json.casetype==1){
                htm+="CASE<br/>";
            }
            if(json.autotype==1){
                htm+="自动消息<br/>";
            }
            if(json.messagetype==1){
                htm+="message<br/>";
            }
            return htm;
        }
        function makeOption2(json){
            if(json.status==1){
                var htm = "<img src='"+path+"/img/new_yes.png' />";
                return htm;
            }else{
                var htm = "<img src='"+path+"/img/new_no.png' />";
                return htm;
            }
        }
       function selectRow(json){
           var subject=W.document.getElementById("subject");
           var messageId= W.document.getElementById("messageId");
           subject.value=json.name;
           messageId.value=json.id;
           W.selectCountry1.close();
       }
        function closedialog(){
            W.selectCountry1.close();
        }
    </script>
</head>
<body>
<div id="sendMessageTable"></div><br/><br/><br/><br/>
<div class="modal-footer" style="text-align: right;width: 750px;">
    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closedialog();">关闭</button>
</div>
</body>
</html>
