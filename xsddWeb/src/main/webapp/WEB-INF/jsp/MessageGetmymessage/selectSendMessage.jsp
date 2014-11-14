<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/10/22
  Time: 10:08
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
                url:path + "/sendMessage/ajax/loadSendMessageList.do?type=messageType&status=1",
                columnData:[
                    {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption3},
                    {title:"模板名称",name:"name",width:"8%",align:"left"},
                    {title:"内容",name:"content",width:"8%",align:"left"},
                    {title:"状态",name:"status",width:"8%",align:"left",format:makeOption2}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });
        function refreshTable(){
            $("#sendMessageTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        function makeOption3(json){
            var htm = "<input type=\"radio\"  name=\"templateId\" value=" + json.id + " value1="+json.content+"><div style='display: none'><span id='"+json.id+"'>"+json.content+"</span></div>";
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
        function closedialog(){
            W.sentmessage.close();
        }
        function submitCommit(){
            var checkboxs=$("input[type=radio][name=templateId]:checked");
            if(checkboxs.length==1){
                var textarea=W.document.getElementById("textarea");
                var id=$(checkboxs[0]).attr("value");
                var content=document.getElementById(id).innerHTML;
                content=content.replace("{Buyer_eBay_ID}","${order.buyeruserid}");
                content=content.replace("{Carrier}","${order.shippingcarrierused}");
                /*content=content.replace("{Carrier_TrackingURL}","${order.shipmenttrackingnumber}");*/
                content=content.replace("{eBay_Item#}","${order.itemid}");
                content=content.replace("{eBay_Item_Title}","${item.title}");
                /*    content=content.replace("{Post_Date}","${order.buyeruserid}");*/
                content=content.replace("{Payment_Date}","${order.paidtime}");
                content=content.replace("{Paypal_Transaction_ID}","${paypal}");
                content=content.replace("{Purchase_Quantity}","${order.quantitypurchased}");
                content=content.replace("{Received_Amount}","${order.amountpaid}");
                /* content=content.replace("{Recipient_Address}","${order.buyeruserid}");*/
                content=content.replace("{Seller_eBay_ID}","${order.selleruserid}");
                content=content.replace("{Seller_Email}","${order.selleremail}");
                content=content.replace("{Today}","<fmt:formatDate value="${date}" pattern="yyyy-MM-dd HH:mm"/>");
                content=content.replace("{Track_Code}","${order.shipmenttrackingnumber}");
                textarea.innerHTML=content;
                W.sentmessage.close();
            }else{
                alert("请选择一个消息模板");
            }
        }
    </script>
</head>
<body>
<div id="sendMessageTable" ></div>
<div class="modal-footer" style="margin-top:20px; float:left; width:100%;">
    <button type="button" class="btn btn-newco" onclick="submitCommit();">选择</button>
    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closedialog();">关闭</button>
</div>

</body>
</html>
