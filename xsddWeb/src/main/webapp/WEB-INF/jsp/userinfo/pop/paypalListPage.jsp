<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2014/9/29
  Time: 17:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/smallFormImport.jsp" %>
<html>
<head>
    <title>选择paypal帐号列表</title>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        $(document).ready(function(){
            queryPaypalList();
        });
        function queryPaypalList(){
            $("#paypalList").initTable({
                url:path + "/paypal/queryPaypalList.do",
                columnData:[
                    {title:"paypal帐号",name:"paypalAccount",width:"8%",align:"left"},
                    {title:"email",name:"email",width:"8%",align:"left"},
                    {title:"状态",name:"op",width:"8%",align:"left",format:makePaypalStatus}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false,
                isrowClick:true,
                rowClickMethod:trClickEvent,
                onlyFirstPage:false
            });
            $("#paypalList").selectDataAfterSetParm({});
        }
        /**行点击事件*/
        function trClickEvent(json,tr){
            if(json.status!=1){
                alert("当前选择的paypal帐号不可用!");
                return;
            }
            W.document.getElementById("paypalAccountId").value=json.id;
            W.document.getElementById("paypalAccount").value=json.paypalAccount;
            W.selectPayPalPage.close();
        }
        /**状态*/
        function makePaypalStatus(json){
            var imgurlpr=path+"/img/";
            if(json.status==1 || json.status=='1'){
                imgurlpr+="new_yes.png";
            }else if(json.status==0 || json.status=='0'){
                imgurlpr+="new_no.png";
            }else{
                imgurlpr+="";
            }
            return "<img src='"+imgurlpr+"' />";
        }
    </script>
</head>
<body>
    <div id="paypalList">

    </div>
</body>
</html>
