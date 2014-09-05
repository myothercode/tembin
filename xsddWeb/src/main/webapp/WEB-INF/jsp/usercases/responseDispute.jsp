<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/2
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <style>
        .table-a table{border:1px solid rgba(0, 0, 0, 0.23)
        }
    </style>
    <script type="text/javascript">
        var trFlag=false;
        var api = frameElement.api, W = api.opener;
        function verifyTrack(){
            var tr=document.getElementById("verify");
            var mm="<td>Please verify that this tracking information is correct and click Submit to continue.If it is incorrect,please re-enter your tracking number." +
                    "<br/>What is your tracking number?<br/>" +
                    "<input type='text' name='number'/>" +
                    "<br/>Which carrier did you use?<br/> " +
                    "<input type='text' name=\"carrier\"/><br/>" +
                    "[<a href=\"javascript:viod()\" onclick=''>View your tracking information</a>]" +
                    "<div class=\"table-a\">" +
                    "<table align=\"center\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 400px;\" >" +
                    "<tr><td align=\"center\" style=\"width: 200px;\"><b>Traking #:</b></td><td>-</td></tr>" +
                    "<tr><td align=\"center\"><b>Carrier:</b></td><td>-</td></tr>" +
                    "<tr><td align=\"center\"><b>Status:</b></td><td><input type='text'  name='status'/></td></tr>" +
                    "</table>" +
                    "</div>" +
                    "</td>";
            trFlag=!trFlag;
            if(trFlag){
                tr.innerHTML=mm;
            }else{
                tr.innerHTML="";
            }
        }
        function dialogClose(){
            W.CaseDetails.close();
        }
        var sendfalg=false;
        function sendMessage(){
            sendfalg=!sendfalg;
            var send=document.getElementById("senddispute");
            var text="";
            if(sendfalg){
                send.innerHTML="<textarea name=\"sendmessage\" style=\"width: 500px;height: 200px;\"></textarea>";
            }else{
                send.innerHTML="";
            }
        }
        function submit(){
            var url=path+"/userCases/sendMessage.do?transactionid=${transactionid}";
            var data=$("#responseForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        Base.token();
                        /*W.CaseDetails.close();*/
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function submit1(){
            var url=path+"/userCases/sendMessage2.do?transactionid=${transactionid}";
            var data="";
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        Base.token();
                        /*W.CaseDetails.close();*/
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
    </script>
</head>
<body>
<form id="responseForm">
<div><b>Choose one of the following:</b></div>
<div class="table-a">
    <table border="0" cellpadding="0" cellspacing="0" style="width: 560px;" >
        <tr><td><a href="javascript:viod()" onclick="verifyTrack();"><b>Verify tracking information</b>(Buyer's preference)</a></td></tr>
        <tr id="verify"><%--<td>
            Please verify that this tracking information is correct and click Submit to continue.If it is incorrect,please re-enter your tracking number.<br/>
            What is your tracking number?<br/>
            <input type="text" name="number"/><br/>
            Which carrier did you use?<br/>
            <input type="text" name="carrier"/><br/>
            [<a href="javascript:viod()">View your tracking information</a>]
            <div class="table-a">
                <table align="center"  border="0" cellpadding="0" cellspacing="0" style="width: 400px;" >
                    <tr><td align="center" style="width: 200px;"><b>Traking #:</b></td><td>-</td></tr>
                    <tr><td align="center"><b>Carrier:</b></td><td>-</td></tr>
                    <tr><td align="center"><b>Status:</b></td><td>-</td></tr>
                </table>
            </div>
        </td>--%></tr>
        <tr><td><a href="javascript:viod()" onclick="submit1();"><b>I shipped the item without tracking information</b></a></td></tr>
        <tr><td><a href="javascript:viod()"><b>Issue a full refund</b></a></td></tr>
        <tr><td><a href="javascript:viod()" onclick="sendMessage();"><b>Send a message</b></a>
            <div id="senddispute">
                <%--<textarea name="sendmessage" style="width: 500px;height: 200px;"></textarea>--%>
            </div>
        </td></tr>
    </table>
</div>
</form>
<input type="button" value="Submit" onclick="submit();"/> &nbsp;
<input type="button" value="Cancel" onclick="dialogClose();"/>

</body>
</html>
