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
       /* var trFlag=false;
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
                        *//**//*W.CaseDetails.close();*//**//*
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
                        *//*W.CaseDetails.close();*//*
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }*/
       var api = frameElement.api, W = api.opener;
        function submit3(){
            var boxs=$("div[class=box]");
            for(var i=0;i<boxs.length;i++){
                var styles=$(boxs[i]).attr("style");
                if(styles=="display: block;"){
                    if(i==0){
                        sendTrackNum();
                    }
                    if(i==1){
                        sendWithoutNum();
                    }
                    if(i==2){
                        sendRefund();
                    }
                    if(i==3){
                        sendMessageForm();
                    }
                }
            }
        }
        function sendTrackNum(){
            if(!$("#sendTrackNum").validationEngine("validate")){
                return;
            }
            var url=path+"/userCases/sendTrackNum.do?";
            var data=$("#sendTrackNum").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        /*W.CaseDetails.close();*/
                        Base.token();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function sendWithoutNum(){
            if(!$("#sendWithoutNum").validationEngine("validate")){
                return;
            }
            var url=path+"/userCases/sendWithoutNum.do?";
            var data=$("#sendWithoutNum").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        /*W.CaseDetails.close();*/
                        Base.token();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function sendRefund(){
            if(!$("#sendRefund").validationEngine("validate")){
                return;
            }
            var url=path+"/userCases/sendRefund.do?";
            var data=$("#sendRefund").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        /*W.CaseDetails.close();*/
                        Base.token();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function sendMessageForm(){
            if(!$("#sendMessageForm").validationEngine("validate")){
                return;
            }
            var url=path+"/userCases/sendMessageForm.do?";
            var data=$("#sendMessageForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        /*W.CaseDetails.close();*/
                        Base.token();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function closedialog(){
            W.CaseDetails.close();
        }
       $(document).ready(function(){
           $("#sendTrackNum").validationEngine();
           $("#sendWithoutNum").validationEngine();
           $("#sendRefund").validationEngine();
           $("#sendMessageForm").validationEngine();
       });
    </script>
</head>
<body>
<div class="modal-body">
<script src="../../js/jquery.min.js"></script>
<script>
    $(function(){
        var _listBox=$('.jq_new');
        var _navItem=$('.jq_new>h4');
        var _boxItem=null, _icoItem=null, _parents=null, _index=null;
        _listBox.each(function(i){
            $(this).find('div.box').eq(0).show();
            $(this).find('h4>span').eq(0).text('-');
        });

        _navItem.each(function(i){
            $(this).click(function(){
                _parents=$(this).parents('.listbox_new');
                _navItem=_parents.find('h4');
                _icoItem=_parents.find('span');
                _boxItem=_parents.find('div.box');
                _index=_navItem.index(this);
                if(_boxItem.eq(_index).is(':visible')){
                    _boxItem.eq(_index).hide().end().not(':eq('+_index+')').first().show();
                    _icoItem.eq(_index).text('+').end().not(':eq('+_index+')').first().text('-');
                }else{
                    _boxItem.eq(_index).show().end().not(':eq('+_index+')').hide();
                    _icoItem.eq(_index).text('-').end().not(':eq('+_index+')').text('+');
                }
            });
        });
    });
</script>
<div id="demo">
<table width="100%" border="0">
<tbody><tr>
    <td width="772">            <div class="modal-header">
        <h4 class="modal-title"><span>Please respond to your buyer by sep 07,2014.</span>Choose one of the following</h4>
    </div></td>
    <td width="9" rowspan="3" valign="top">&nbsp;</td>
    <td rowspan="3" valign="top" style="margin-left:20px; padding-left:15px; padding-top:20px; padding-right:20px; line-height:25px;background:#F4F4F4"><strong>订单号</strong><br>
        ${order.orderid}(已配货)<br>
        <strong>物流跟踪号</strong><br>
        ${order.shipmenttrackingnumber}<br>
        <strong>发货时间：</strong><br>
        <fmt:formatDate value="${order.shippedtime}" pattern="yyyy-MM-dd"/><br>
        <strong>付款时间：</strong><br>
        <fmt:formatDate value="${order.paidtime}" pattern="yyyy-MM-dd"/><br>
        <strong>运输方式：</strong><br>
        ${order.selectedshippingservice}
       <%-- <c:forEach items="${items}" begin="0"  varStatus="status">
            <dt id="menu${status.index+5}" name1="${status.index+5}" name2="${folders[status.index].id}" name="${folders[status.index].configName}" class="new_tab_2" onclick="setTab1('menu',${status.index+5},${status.count+4})">${folders[status.index].configName}</dt>
        </c:forEach>--%>
        <c:forEach items="${items}" begin="0"  varStatus="status">
        <span class="voknet"></span>
        <table width="100%" border="0">
            <tbody>
            <tr>
                <td width="56"><strong>商品${status.index+1}</strong></td>
                <td style="color:#63B300">CNDL(${items[status.index].itemid})</td>
            </tr>
            <tr>
                <td><img src="${pics[status.index]}" width="46" height="46"></td>
                <td style=" color:#5F93D7">${order.title}<br>
                </td>
            </tr>
            </tbody></table>
        </c:forEach>
        <%--<span class="voknet"></span>

        <table width="100%" border="0">
            <tbody><tr>
                <td width="56"><strong>商品2</strong></td>
                <td style="color:#63B300">CNDL</td>
            </tr>
            <tr>
                <td><img src="../../img/no_pic_1.png" width="46" height="46"></td>
                <td style=" color:#5F93D7">标题标题标题标题标题标题标题标题标题标题标题<br>
                    标题标题标题标题标题...</td>
            </tr>
            </tbody></table>--%>
        <div style="font-family:Arial, Helvetica, sans-serif; font-size:12px;">
            <span class="voknet"></span>
            <strong><font color="#5F93D7">The buyer opended a case:</font><font color="#F35F23">${reason}</font></strong><br>
            <fmt:formatDate value="${cases.creationdate}" pattern="yyyy-MM-dd HH:mm"/><br>
            <strong style="color:#000000">The case details:</strong><br>
            <%--The buyer has not receiver the item yet.--%>
            ${content}<br>
            The buyer paid on <fmt:formatDate value="${order.paidtime}" pattern="yyyy-MM-dd HH:mm"/><br>
            The buyer can be contacted at “+7894312424”in order to <br>
            resolve the problem. <br>
            <strong style="color:#000000">Additional information:</strong><br>
            The buyer paid on<fmt:formatDate value="${order.paidtime}" pattern="yyyy-MM-dd HH:mm"/><br>
            <strong style="color:#000000">The case details:</strong><br>
            ${information}<br></div></td>
</tr>
<tr>
    <td width="772"><div class="listbox_new jq_new">
        <h4><span>-</span>
            <table width="90%" border="0"><tbody><tr>
                <td><strong>Verify tracking information ( Buyer’s preference )</strong></td></tr></tbody></table>
        </h4>
        <div class="box" style="display: block;">
            <form id="sendTrackNum">
                <input type="hidden" name="sellerid" value="${cases.sellerid}"/>
                <input type="hidden" name="transactionId" value="${cases.transactionid}"/>
            <table width="100%" border="0">
                <tbody><tr>
                    <td height="32" align="left">Please verity that this tracking information is correct and click Submit to continue.If it is incorrect,please re-enter your </td>
                </tr>
                <tr>
                    <td height="32" align="left">What is your tracking number?</td>
                </tr>
                <tr>
                    <td height="32" align="left"><input name="trackingNum" type="text" class="form-controlsd" value="${order.shipmenttrackingnumber}"></td>
                </tr>
                <tr>
                    <td height="32" align="left">Which carrier did you use?</td>
                </tr>
                <tr>
                    <td height="32" align="left"><input name="carrier" type="text" class="form-controlsd" value="${order.shippingcarrierused}"></td>
                </tr>
                <tr>
                    <td height="32" align="left">[ <font color="#5F93D7">View your tracking information</font> ]
                        <span class="voknet"></span></td>
                </tr>
                <tr>
                    <td height="32" align="left"><table width="100%" border="0">
                        <tbody><tr>
                            <td><strong>Tracking #:</strong></td>
                            <td><strong>Carrier:</strong></td>
                            <td width="118"><strong>Status:</strong></td>
                            <td width="118">&nbsp;</td>
                            <td width="118">&nbsp;</td>
                        </tr>
                        <tr>
                            <td>${order.shipmenttrackingnumber}</td>
                            <td>${order.shippingcarrierused}</td>
                            <td width="118"><img src="<c:url value ="/img/co_1.jpg"/> "></td>
                            <td width="118"><img src="<c:url value ="/img/co_2.jpg"/> "></td>
                            <td width="118"><img src="<c:url value ="/img/co_3.jpg"/> "></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td width="116" style="color:#8DB81F">ACCEPTED</td>
                            <td width="116" style="color:C3C3C3">IN TRANST</td>
                            <td width="116" style="color:C3C3C3">Delivered</td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td colspan="3" style="color:#8DB81F">Departure from outward office of exchange</td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td colspan="3" style="color:#BFBFBF">2014-05-29,09:59:00,Guangdong</td>
                        </tr>
                        </tbody></table></td>
                </tr>
                <tr>
                    <td height="32" align="left">
                        <span class="voknet"></span>
                        <label for="textarea"></label>
                        <textarea name="textarea" id="textarea" cols="100%" rows="5" class="newco validate[required]"><%--Additional comments:(optional)--%></textarea>
                    </td>
                </tr>
                <tr>
                    <td height="22" align="right" style="color:#C6C6C6">1000 chartacters left. No HTML.</td>
                </tr>
                </tbody></table>
            </form>

        </div>
        <h4><span>+</span>
            <table width="90%" border="0"><tbody><tr>
                <td><strong>I shipped the item without tracking information</strong></td></tr></tbody></table>
        </h4>
        <div class="box">
            <form id="sendWithoutNum">
                <input type="hidden" name="sellerid" value="${cases.sellerid}"/>
             <input type="hidden" name="transactionId" value="${cases.transactionid}"/>
            <table width="100%" border="0">
            <tbody><tr>
                <td height="32" align="left"><strong><img src="<c:url value ="/img/new_yes.png"/> " width="22" height="22"> Tracking number entered successfully</strong></td>
            </tr>
            <tr>
                <td height="32" align="left">Please check to see if this tracking information is correct.</td>
            </tr>
            <tr>
                <td height="32" align="left"><textarea name="textarea2" id="textarea2" cols="100%" rows="5" class="newco validate[required]"><%--Additional comments:(optional)--%></textarea></td>
            </tr>
            <tr>
                <td height="32" align="left" style="color:#F00">Or octer the correct tracking delivery conmation number</td>
            </tr>
            <tr>
                <td height="32" align="left">Additonal Commects</td>
            </tr>
            <tr>
                <td height="32" align="left">
                    <label for="textarea"></label>
                    <textarea name="textarea" id="textarea" cols="100%" rows="5" class="newco validate[required]"><%--Additional comments:(optional)--%></textarea>
                </td>
            </tr>
            <tr>
                <td height="22" align="left" style="color:#C6C6C6">2000 No HTML.</td>
            </tr>
            </tbody></table>
            </form>
        </div>
        <h4><span>+</span>
            <table width="90%" border="0"><tbody><tr>
                <td><strong>Issue a full refund</strong></td></tr></tbody></table>
        </h4>
        <div class="box">
            <form id="sendRefund">
                <input type="hidden" name="transactionId" value="${cases.transactionid}"/>
            <table width="100%" border="0">
            <tbody><tr>
                <td height="32" align="left">
                    <input name="fullRefund" type="checkbox" value="1"> Issue a full refund
                    <input name="partialRefund" type="checkbox" value="2">
                    Issue a partial refund
                </td>
            </tr>
            <tr>
                <td height="32" align="left"><input type="text" name="amout" class="form-controlsd" > 填写退款金额</td>
            </tr>
            <tr>
                <td height="32" align="left">You agree to issue your customer a $17.56 refund , which is the purchase price plus original shipping. After the buyer receives the buyer receives the refund, the case will be closed</td>
            </tr>
            <tr>
                <td height="32" align="left" style="color:#5F93D7">The buyer must receive your refund by sep 05,2014.</td>
            </tr>
            <tr>
                <td height="32" align="left">
                    <span class="voknet"></span>
                    <label for="textarea"></label>
                    <textarea name="textarea" id="textarea" cols="100%" rows="5" class="newco validate[required]"><%--Additional comments:(optional)--%></textarea>
                </td>
            </tr>
            <tr>
                <td height="22" align="right" style="color:#C6C6C6">2000 No HTML.</td>
            </tr>
            </tbody></table>
            </form>
        </div>
        <h4><span>+</span>
            <table width="90%" border="0"><tbody><tr>
                <td><strong>send a message</strong></td></tr></tbody></table>
        </h4>
        <div class="box">
            <form id="sendMessageForm">
                <input type="hidden" name="transactionId" value="${cases.transactionid}"/>
            <table width="100%" border="0">
            <tbody><tr>
                <td height="32" align="left" style="color:#5F93D7">Please provide detailed information in your response. All responses may be Viewed by the buyer and eBay Customer Support</td>
            </tr>
            <tr>
                <td height="32" align="left">
                    <label for="textarea"></label>
                    <textarea name="textarea" id="textarea" cols="100%" rows="5" class="newco validate[required]"><%--Additional comments:(optional)--%></textarea>
                </td>
            </tr>
            <tr>
                <td height="22" align="right" style="color:#C6C6C6">2000 No HTML.</td>
            </tr>
            </tbody></table>
            </form>
        </div>
    </div>


    </td>
</tr>
<tr>
    <td width="772"><div class="modal-footer">
        <button type="button" class="btn btn-primary">保存</button>
        <button type="button" class="btn btn-newco" onclick="submit3();">回复</button>
        <button type="button" class="btn btn-default" onclick="closedialog();" data-dismiss="modal">关闭</button>
    </div> </td>
</tr>
</tbody></table>

</div>


<script src="../../js/jquery-latest.js"></script>
<script src="../../js/bootstrap.min.js"></script>
<script src="../../js/theme.js"></script>

</div>
<%--<form id="responseForm">
<div><b>Choose one of the following:</b></div>
<div class="table-a">
    <table border="0" cellpadding="0" cellspacing="0" style="width: 560px;" >
        <tr><td><a href="javascript:viod()" onclick="verifyTrack();"><b>Verify tracking information</b>(Buyer's preference)</a></td></tr>
        <tr id="verify"><td>
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
        </td></tr>
        <tr><td><a href="javascript:viod()" onclick="submit1();"><b>I shipped the item without tracking information</b></a></td></tr>
        <tr><td><a href="javascript:viod()"><b>Issue a full refund</b></a></td></tr>
        <tr><td><a href="javascript:viod()" onclick="sendMessage();"><b>Send a message</b></a>
            <div id="senddispute">
                &lt;%&ndash;<textarea name="sendmessage" style="width: 500px;height: 200px;"></textarea>&ndash;%&gt;
            </div>
        </td></tr>
    </table>
</div>
</form>
<input type="button" value="Submit" onclick="submit();"/> &nbsp;
<input type="button" value="Cancel" onclick="dialogClose();"/>--%>

</body>
</html>
