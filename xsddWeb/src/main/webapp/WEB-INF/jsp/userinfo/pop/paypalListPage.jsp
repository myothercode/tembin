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
    <title>点击选择paypal帐号列表</title>
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
                    {title:"状态",name:"op",width:"8%",align:"left",format:makePaypalStatus},
                    {title:"选择",name:"op",width:"8%",align:"left",format:makeSelect}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false,
                isrowClick:true,
                sysParm: {"jsonBean.pageNum": 1, "jsonBean.pageCount": 1000},
               // rowClickMethod:trClickEvent,
                onlyFirstPage:true
            });
            $("#paypalList").selectDataAfterSetParm({});
        }
        /**行点击事件*/
        function trClickEvent(json,tr){
            if(json.status!=1){
                alert("当前选择的paypal帐号不可用!");
                return;
            }
            try{
                //W.setPayPalVal(json.id,json.paypalAccount,api.data);
                //W.document.getElementById("paypalAccountId").value=json.id;
                //W.document.getElementById("paypalAccount").value=json.paypalAccount;
            }catch (e){
                console.log(e);
            }

           // W.selectPayPalPage.close();
        }

        function makeSelect(json){
            if(json.status!=1){
                return "";
            }else{
                return "<input type='radio' name='selectpaypalid' value='"+json.id+"'>";
            }
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

        function sureP(){
            var ppid=$("input[type='radio'][name='selectpaypalid']:checked").val();
            var ebayid=api.data;
            var isDefault=$("#xxxx").prop("checked")?1:0;
            if(ppid==null){alert("请选择一个paypal帐号！");return;}
            W.setPayPalVal(ebayid,ppid,isDefault);
            W.selectPayPalPage.close();
        }

    </script>
</head>
<body>
    <div id="paypalList">

    </div>
<div style="height: 25px;width: 100%;bottom: 0px;position: fixed;background-color: lightblue;text-align: right;left: 0px;padding-top: 1px;">

    <div style="margin-right: 5px;">
        <input type="checkbox" id="xxxx" value="1" />始终作为默认&nbsp;&nbsp;&nbsp;<button onclick="sureP()" style="height: 23px">确定选择</button> </div>
</div>
</body>
</html>
