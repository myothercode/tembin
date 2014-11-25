<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/10/13
  Time: 17:51
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
        function closedialog(){
            W.sendGetmymessage.close();
        }
        function submitCommit(){
            if(!$("#messageForm").validationEngine("validate")){
                return;
            }

            var caseType=$("input[name=caseType]");
            var autoType=$("input[name=autoType]");
            var messageType=$("input[name=messageType]");
            caseType.val("0");
            autoType.val("0");
            messageType.val("0");
            if(caseType[0].checked){
                caseType.val("1");
            }
            if(autoType[0].checked){
                autoType.val("1");
            }
            if(messageType[0].checked){
                messageType.val("1");
            }
            var url=path+"/sendMessage/saveMessageTemplate.do";
            var data=$("#messageForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        Base.token;
                        var url=path + "/sendMessage/ajax/loadSendMessageList.do?status=1";
                        W.refreshTable1(url);
                        W.sendGetmymessage.close();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        $(document).ready(function(){
            $("#messageForm").validationEngine();
        });
        function addcontent(obj,content){
            insertAtCaret(content,obj.value);
        }
        function setCaret(textObj) {
            if (textObj.createTextRange) {
                textObj.caretPos = document.selection.createRange().duplicate();
            }
        }
        function insertAtCaret(textObj, textFeildValue) {
            if (document.all) {
                if (textObj.createTextRange && textObj.caretPos) {
                    var caretPos = textObj.caretPos;
                    caretPos.text = caretPos.text.charAt(caretPos.text.length - 1) == ' ' ? textFeildValue + ' ' : textFeildValue;
                } else {
                    textObj.value = textFeildValue;
                }
            } else {
                if (textObj.setSelectionRange) {
                    var rangeStart = textObj.selectionStart;
                    var rangeEnd = textObj.selectionEnd;
                    var tempStr1 = textObj.value.substring(0, rangeStart);
                    var tempStr2 = textObj.value.substring(rangeEnd);
                    textObj.value = tempStr1 + textFeildValue + tempStr2;
                } else {
                    alert("This version of Mozilla based browser does not support setSelectionRange");
                }
            }
        }
    </script>
</head>
<body>
<div class="modal-body">
    <form class="form-horizontal" role="form" id="messageForm">
        <input type="hidden" name="id" value="${id}"/>
        <div class="form-group">

            <label class="newdt control-label" style="margin-top:9px;">选择类型</label>
            <div class="col-lg-10">
                <div class="newselect span5" style="margin-top:12px; margin-left:8px;width: 750px;">
                    <c:if test="${template.casetype==1}">
                        <input name="caseType" type="checkbox" checked> CASE模板
                    </c:if>
                    <c:if test="${template.casetype!=1}">
                        <input name="caseType" type="checkbox" > CASE模板
                    </c:if>

                    <c:if test="${template.autotype==1}">
                        <input name="autoType" type="checkbox" checked>  自动消息模板
                    </c:if>
                    <c:if test="${template.autotype!=1}">
                        <input name="autoType" type="checkbox" >  自动消息模板
                    </c:if>

                    <c:if test="${template.messagetype==1}">
                        <input name="messageType" type="checkbox" checked >  一般消息模板
                    </c:if>
                    <c:if test="${template.messagetype!=1}">
                        <input name="messageType" type="checkbox" >  一般消息模板
                    </c:if>
                </div>
            </div>
            <label  class="newdt control-label" style="margin-top:9px;">模板名称</label>
            <div class="col-lg-10">
                <div class="newselect" style="margin-top:9px;width: 750px;">
                    <input name="name" class="form-controlsd validate[required]" type="text" value="${template.name}">
                </div>
            </div><br/>
            <label  class="newdt control-label" style="margin-top:9px;">标签</label>
            <div class="col-lg-10">
                <div class="newselect" style="margin-top:9px;width: 750px;">
                    <select onchange="addcontent(this,this.form.content);" class="form-controlsd" style="width: 200px;height: 35px;">
                        <option value="">--选择--</option>
                        <option value="{Buyer_eBay_ID}">Buyer_eBay_ID</option>
                        <option value="{Carrier}">Carrier</option>
                        <option value="{Carrier_TrackingURL}">Carrier_TrackingURL</option>
                        <option value="{eBay_Item#}">eBay_Item#</option>
                        <option value="{eBay_Item_Title}">eBay_Item_Title</option>
                        <option value="{Post_Date}">Post_Date</option>
                        <option value="{Payment_Date}">Payment_Date</option>
                        <option value="{Paypal_Transaction_ID}">Paypal_Transaction_ID</option>
                        <option value="{Purchase_Quantity}">Purchase_Quantity</option>
                        <option value="{Received_Amount}">Received_Amount</option>
                        <option value="{Recipient_Address}">Recipient_Address</option>
                        <option value="{Seller_eBay_ID}">Seller_eBay_ID</option>
                        <option value="{Seller_Email}">Seller_Email</option>
                        <option value="{Today}">Today</option>
                        <option value="{Track_Code}">Track_Code</option>
                    </select>
                </div>
            </div><br/>
            <label  class="newdt control-label" style="margin-top:9px;">模板内容</label>
            <div class="col-lg-10">
                <div class="newselect" style="margin-top:9px;width: 750px;">
                <textarea name="content"  onselect="setCaret(this);" onclick="setCaret(this);" onkeyup="setCaret(this);" id="textarea" class="form-control validate[required]" cols="" rows="4" style="width:600px;height: 200px;">${template.content}</textarea>
                </div>
            </div>
        </div>
    </form>
    <div class="modal-footer">
        <button type="button" class="net_put" onclick="submitCommit();">保存</button>
        <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>
    </div>
</div>
</body>
</html>
