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
        var api = frameElement.api, W = api.opener;
        function submitCommit(){
            var val = $("#timerStr").val();
            if(val==""||val==null){
                alert("定时时间必须选择！");
                return ;
            }
            $(W.document).find("input[type='hidden'][name='timerListing']").val(val);
            W.saveData("timeSave","timeSave");
            W.timerPage.close();
        }
    </script>
</head>
<body>
<div style="width: 400px;margin-left: 100px;margin-top: 40px;">
    选择时间：<input name="timerStr" id="timerStr" style="height: 24px;width: 150px" type="text" size="20"
                onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-{%d}'})" class="form-control">
    <input type="button" value="确定" onclick="submitCommit()"/>
</div>
</body>
</html>
