<%@ page import="java.util.Date" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/14
  Time: 16:52
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
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/default/easyui.css" />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/icon.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/jquery-easyui/jquery.easyui.min.js" /> ></script>
    <script type="text/javascript">
        var clone=null;
        var api = frameElement.api, W = api.opener;
        var flag=false;
        var s="";
        var r1="";
        $(document).ready(function(){
          /*  var orders=${orders};
            for(var i=0;i<orders.length;i++){
                $("#frameLeft").attr("src",path + "/order/viewOrderAbstractLeft.do?orderId=${orderId}");
                $("#frameRight").attr("src",path + "/order/viewOrderAbstractRight.do?orderId=${orderId}");
                $("#frameDown").attr("src",path + "/order/viewOrderAbstractDown.do?orderId=${orderId}");
            }*/
    /*       *//* $("#frameLeft").attr("src",path + "/order/viewOrderAbstractLeft.do?orderId=${orderId}");
            $("#frameRight").attr("src",path + "/order/viewOrderAbstractRight.do?orderId=${orderId}");
            $("#frameDown").attr("src",path + "/order/viewOrderAbstractDown.do?orderId=${orderId}");*/
            $("#frameBuyHistory").attr("src",path + "/order/viewOrderBuyHistory.do?orderId=${orderId}");
        });
        function dialogClose(){
            W.OrderGetOrders.close();

        }
        var viewsendMessage;
        var viewsendMessage1= W.OrderGetOrders;
        function sendMessage(){
            var url=path+'/order/initOrdersSendMessage.do?orderid=${order.orderid}';
            viewsendMessage=openMyDialog({title: '发送消息',
                content: 'url:'+url,
                icon: 'succeed',
                width:800,
                height:500,
                lock:true
            });
        }
        function boxChange(){
            flag=!flag;
            if(flag){
                $("#tranCheckBox").val("true");
            }else{
                $("#tranCheckBox").val("false");
            }
        }
        function submitCommit(){
            var url=path+"/order/ajax/updateOrder.do";
            var data=$("#tranForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        Base.token;
                        W.refreshTable();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );

        }
        function saveReturnAddress(){
            var returnContacts=$("#returnContacts").val();
            var returnCompany=$("#returnCompany").val();
            var returnCountry=$("#returnCountry").val();
            var returnProvince=$("#returnProvince").val();
            var returnCity=$("#returnCity").val();
            var returnArea=$("#returnArea").val();
            var returnStreet=$("#returnStreet").val();
            var returnPostCode=$("#returnPostCode").val();
            var returnPhone=$("#returnPhone").val();
            var returnEmail=$("#returnEmail").val();
            var url=path+"/order/ajax/saveReturnAddress.do";
            var data={"returnAddress":"1","returnContacts":returnContacts,"returnCompany":returnCompany,
                        "returnCountry":returnCountry,"returnProvince":returnProvince,"returnCity":returnCity,
                        "returnArea":returnArea,"returnStreet":returnStreet,"returnPostCode":returnPostCode,
                        "returnPhone":returnPhone,"returnEmail":returnEmail,"orderId":"${order.orderid}"};
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        Base.token();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
            /*window.location.reload();*/
        }
        function saveReturnAddress1(){

            var returnContacts=$("#returnContacts1").val();
            var returnCompany=$("#returnCompany1").val();
            var returnCountry=$("#returnCountry1").val();
            var returnProvince=$("#returnProvince1").val();
            var returnCity=$("#returnCity1").val();
            var returnArea=$("#returnArea1").val();
            var returnStreet=$("#returnStreet1").val();
            var returnPostCode=$("#returnPostCode1").val();
            var returnPhone=$("#returnPhone1").val();
            var returnEmail=$("#returnEmail1").val();
            var url=path+"/order/ajax/saveReturnAddress.do";
            var data={"returnAddress":"2","returnContacts":returnContacts,"returnCompany":returnCompany,
                "returnCountry":returnCountry,"returnProvince":returnProvince,"returnCity":returnCity,
                "returnArea":returnArea,"returnStreet":returnStreet,"returnPostCode":returnPostCode,
                "returnPhone":returnPhone,"returnEmail":returnEmail,"orderId":"${order.orderid}"};
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        Base.token();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
           /* window.location.reload();*/
        }
        function transportSelect(){
             flag=!flag;
             var returnAddress=s=window.document.getElementById("returnAddress");
             console.debug(returnAddress);
             var returnAddress1="<table><tr><td><b>寄件人地址:</b></td><td><input type='button' value='应用'onclick='saveReturnAddress();'/></td>" +
             "<td><b>退货地址:</b></td><td><input  type='button' value='应用'onclick='saveReturnAddress1();'/></td></tr>" +
             "<tr><td>联系人:</td><td><input value='${addresstype1.contacts}' id='returnContacts' type='text' name='returnContacts' /></td><td>联系人:</td><td><input value='${addresstype2.contacts}' id='returnContacts1' type='text' name='returnContacts1' /></td></tr>" +
             "<tr><td>公司:</td><td><input value='${addresstype1.company}' id='returnCompany' type='text' name='returnCompany' /></td><td>公司:</td><td><input value='${addresstype2.company}' id='returnCompany1' type='text' name='returnCompany1' /></td></tr>" +
             "<tr><td>国家:</td><td><input value='${addresstype1.country}' id='returnCountry' type='text' name='returnCountry' /></td><td>国家:</td><td><input value='${addresstype2.country}' id='returnCountry1' type='text' name='returnCountry1' /></td></tr>" +
             "<tr><td>省:</td><td><input value='${addresstype1.province}' id='returnProvince' type='text' name='returnProvince' /></td>" +
             "<td>省:</td><td><input value='${addresstype2.province}'  id='returnProvince1' type='text' name='returnProvince1' /></td></tr>" +
             "<tr><td>城市:</td><td><input value='${addresstype1.city}' id='returnCity' type='text' name='returnCity' /></td><td>城市:</td><td><input value='${addresstype2.city}' id='returnCity1' type='text' name='returnCity1' /></td></tr>" +
             "<tr><td>区:</td><td><input value='${addresstype1.area}' id='returnArea' type='text' name='returnArea' /></td><td>区:</td><td><input value='${addresstype2.area}' id='returnArea1' type='text' name='returnArea1' /></td></tr>" +
             "<tr><td>街道:</td><td><input value='${addresstype1.street}' id='returnStreet' type='text' name='returnStreet' /></td><td>街道:</td><td><input value='${addresstype2.street}' id='returnStreet1' type='text' name='returnStreet1' /></td></tr>" +
             "<tr><td>邮编:</td><td><input value='${addresstype1.postcode}' id='returnPostCode' type='text'name='returnPostCode' /></td><td>邮编:</td><td><input value='${addresstype2.postcode}' id='returnPostCode1' type='text' name='returnPostCode1' /></td></tr>" +
             "<tr><td>手机:</td><td><input value='${addresstype1.phone}' id='returnPhone' type='text' name='returnPhone' /></td><td>手机:</td><td><input value='${addresstype2.phone}' id='returnPhone1' type='text' name='returnPhone1' /></td></tr>" +
             "<tr><td>电邮:</td><td><input value='${addresstype1.email}' id='returnEmail' type='text' name='returnEmail' /></td><td>电邮:</td><td><input value='${addresstype2.email}' id='returnEmail1' type='text' name='returnEmail1' /></td></tr></table>";
             if(flag){
                 returnAddress.innerHTML=returnAddress1;
             }else{
                 returnAddress.innerHTML="";
             }
         }
        function submitCommit1(){
            if(!$("#sendForm").validationEngine("validate")){
                return;
            }
            var url=path+"/order/apiGetOrdersSendMessage.do?flag=${flag}&messageID=${messageID}&parentMessageID=${parentMessageID}";
            var data=$("#sendForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        var content=$("#textarea").val();
                        var myDate=new Date();
                        var mytime=myDate.toLocaleString();
                        var year=mytime.substring(0,4);
                        var month=mytime.substring(5,7);
                        var day=mytime.substring(8,10);
                        var time1=myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
                        var day1=year+"-"+month+"-"+day+" ";
                        var div="<p class=\"admin\">"+s+"</p>"+
                        "<div class=\"admin_co\">"+
                                "<div class=\"admin_co_1\"></div>"+
                        "<ul>Hi "+r1+".: )<br/>"+content+"";
                        div+="<span>发送于:"+day1+time1+"</span>";
                        /*div+="<span style=\"color: red\">发送失败于:"+day1+time1+"!</span>";*/
                        div+="</ul>"+
                        "<div class=\"admin_co_2\"></div></div>"+
                        "<div class=\"dpan\"></div>";
                        $("#add").append(div);
                        document.getElementById("textarea").innerHTML="";
                        Base.token;
                    },
                        function(m,r){
                            alert(r);
                            var content=$("#textarea").val();
                            var myDate=new Date();
                            var mytime=myDate.toLocaleString();
                            var year=mytime.substring(0,4);
                            var month=mytime.substring(5,7);
                            var day=mytime.substring(8,10);
                            var time1=myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
                            var day1=year+"-"+month+"-"+day+" ";
                            var div="<p class=\"admin\">"+s+"</p>"+
                                    "<div class=\"admin_co\">"+
                                    "<div class=\"admin_co_1\"></div>"+
                                    "<ul>Hi "+r1+".: )<br/>"+content+"";
                           /* div+="<span>发送于:"+day1+time1+"</span>";*/
                            div+="<span style=\"color: red\">发送失败于:"+day1+time1+"!</span>";
                            div+="</ul>"+
                                    "<div class=\"admin_co_2\"></div></div>"+
                                    "<div class=\"dpan\"></div>";
                            $("#add").append(div);
                            Base.token();
                        }],{isConverPage:true}
            );
        }
        function selectSendMessage(){
            var url=path+'/message/selectSendMessage.do?transactionid=${order.transactionid}&seller=${order.selleruserid}&paypal=${paypals[0]}';
            sentmessage = openMyDialog({title: '选择消息模板',
                content:'url:'+url,
                icon: 'succeed',
                width:800,
                height:400,
                parent:api,
                lock:true,
                zIndex:2000
            });
        }
        $(document).ready(function(){
            $("#sendForm").validationEngine();
            var paypal=$("#paypal").val();
            if(paypal!=""){
                var url=path+"/order/ajax/paypalAmount.do?orderId="+paypal;
                $().invoke(url,null,
                        [function(m,r){
                            console.debug(r);
                            var spans=document.getElementsByName("price");
                            var spans1=document.getElementsByName("totalPrice");
                            var spans2=document.getElementsByName("price1");
                            for(var i=0;i< r.length;i++){
                                for(var j=0;j<spans.length;j++){
                                    if(r.length>spans.length){
                                        if(r[j]==""){
                                            spans[j].innerHTML="";
                                        }else{
                                            spans[j].innerHTML=r[j];
                                        }
                                    }else{
                                        if(r[i]==""){
                                            spans[i].innerHTML="";
                                        }else{
                                            spans[i].innerHTML=r[i];
                                        }
                                    }
                                }
                            }
                            if(r.length==0){
                                for(var i=0;i<spans.length;i++){
                                    spans[i].innerHTML="";
                                }
                            }
                            for(var i=0;i<spans.length;i++){
                                if(spans[i].innerHTML!=""){
                                   var price10=parseInt(spans[i].innerHTML);
                                   var price11=parseInt(spans1[i].innerHTML);
                                   var price12=price11-price10;
                                   spans2[i].innerHTML=price12+" USD";
                                   spans[i].innerHTML=spans[i].innerHTML;
                                }else{
                                    spans2[i].innerHTML=spans1[i].innerHTML+" USD";
                                }
                            }
                            for(var i=0;i<spans.length;i++){
                                if(spans[i].innerHTML==""){
                                    $(spans[i]).attr("style","color:red;");
                                    spans[i].innerHTML="获取paypal费用失败";
                                }else{
                                    spans[i].innerHTML=spans[i].innerHTML+" USD";
                                }
                            }
                            Base.token();
                        },
                            function(m,r){
                                alert(r);
                                Base.token();
                            }]
                );
            }

        });
    </script>
    <style>
        .table-a table{border:1px solid rgba(0, 0, 0, 0.23)
        }
        #left { float: left; }
        #right {float: left}
        #center {float: inherit}
        #rightCenter { float:left; }
    </style>
</head>
<body>

<div class="modal-body">
<script type="text/javascript">
    function setvTab(name,cursel,n){
        for(i=1;i<=n;i++){
            var svt=document.getElementById(name+i);
            var con=document.getElementById("new_"+name+"_"+i);
            svt.className=i==cursel?"new_ic_1":"";
            con.style.display=i==cursel?"block":"none";
        }
    }
</script>
<div class="modal-header">
<table width="100%" border="0" style="margin-top:-20px;">
    <tbody><tr>
        <td style="line-height:30px;"><span style=" color:#2395F3; font-size:24px; font-family:Arial, Helvetica, sans-serif">
        <c:if test="${flag=='true'}">
            ${order.buyeruserid}</span> [来自eBay账号：${order.selleruserid} 的买家]
            <input type="hidden" name="orderId" id="paypal" value="${order.orderid}">
        </c:if>
        <c:if test="${flag=='false'}">
            ${message.sender}</span> [来自eBay账号：${message.recipientuserid} 的买家]
            <input type="hidden" name="orderId" id="paypal" value="">
        </c:if>
        </td>
    </tr>
    <tr>
        <td><div class="new_tab">
            <div class="new_tab_left"></div>
            <div class="new_tab_right"></div>
            <c:if test="${flag=='true'}">
                <dt id="svt1" class="new_ic_1" onclick="setvTab('svt',1,4)">订单摘要</dt>
                <dt id="svt2"  onclick="setvTab('svt',2,4)" class="">回复消息</dt>
            </c:if>
            <c:if test="${flag=='false'}">
                <dt id="svt2" class="new_ic_1"  onclick="setvTab('svt',2,4)" class="">回复消息</dt>
            </c:if>
        </div></td>
    </tr>

    </tbody></table>
<c:if test="${flag=='true'}">
<div id="new_svt_1"  style="display: block;">
</c:if>
<c:if test="${flag=='false'}">
<div id="new_svt_1"  style="display: none;">
</c:if>
    <c:forEach items="${orders}" begin="0"  varStatus="status">
    <table width="100%" border="0">
        <tbody><tr>
            <td width="772"></td>
            <td width="9" rowspan="7" valign="top">&nbsp;</td>
            <td rowspan="7" valign="top" style="margin-left:20px; padding-left:15px; padding-top:20px; padding-right:20px; line-height:25px;background:#F4F4F4">
                <table width="100%" border="0">
                    <tbody><tr class="ebay">
                        <td><strong>eBay地址</strong><br>
                            ${orders[status.index].street1}<br>
                            ${orders[status.index].postalcode}<br>
                            ${orders[status.index].countryname}<br>
                            ${orders[status.index].phone}<br>
                            ${orders[status.index].buyeremail}</td>
                    </tr>
                    </tbody></table>
                <span class="voknet"></span>
                <table width="100%" border="0">
                    <tbody>
                    <tr class="Paypal">
                        <td><strong>Paypal 地址</strong><br>
                                ${orders[status.index].street1}<br>
                                ${orders[status.index].postalcode}<br>
                                ${orders[status.index].countryname}<br>
                                ${orders[status.index].phone}<br>
                                ${orders[status.index].buyeremail}</td>
                    </tr>
                    </tbody></table>
                <span class="voknet"></span>
                <table width="100%" border="0">
                    <tbody><tr>
                        <td><strong>发货信息</strong><br>
                                ${orders[status.index].street1}<br>
                                ${orders[status.index].postalcode}<br>
                                ${orders[status.index].countryname}<br>
                                ${orders[status.index].phone}<br>
                                ${orders[status.index].buyeremail}</td>
                    </tr>
                    </tbody></table><span class="moneyok">
                        <c:if test="${orders[status.index].status=='Complete'}">
                            已付款
                        </c:if>
                        <c:if test="${orders[status.index].status=='Incomplete'}">
                            未付款
                        </c:if>
                    </span>
                <span class="voknet"></span>
                <table width="100%" border="0">
                    <tbody><tr>
                        <td><strong>备注</strong><br>
                            ${orders[status.index].title}</td>
                    </tr>
                    </tbody></table>
            </td>
        </tr>
        <tr>
            <td width="772" height="30"><span style="color:#2395F3; font-size:16px; font-family:Arial, Helvetica, sans-serif">1</span>  &nbsp; &nbsp;<strong>销售编号</strong>：53307  &nbsp; &nbsp;eBayx &nbsp; &nbsp;<strong> 销售编号</strong>：53307_5135 &nbsp; &nbsp; <font color="#2395F3">${orders[status.index].buyeruserid} ( ${orders[status.index].buyeremail} )</font></td>
        </tr>
        <tr>
            <td height="40" bgcolor="#F4F4F4" style="font-size:18px; font-family:'微软雅黑', '宋体', Arial">&nbsp;交易信息</td>
        </tr>
        <tr>
            <td><table width="100%" border="0">
                <tbody><tr>
                    <td width="41%" rowspan="10"><img src="${pictures[status.index]}" width="297" height="247"></td>
                    <td width="21%" height="30"><strong>Item No.</strong><br></td>
                    <td width="38%" height="30">${orders[status.index].itemid}</td>
                </tr>
                <tr>
                    <td height="30"><strong>售出日期</strong><br></td>
                    <td width="38%" height="30"><fmt:formatDate value="${orders[status.index].createdtime}" pattern="yyyy-MM-dd HH:mm"/></td>
                </tr>
                <tr>
                    <td height="30"><strong>销售数量</strong><br></td>
                    <td width="38%" height="30">${orders[status.index].quantitypurchased}</td>
                </tr>
                <tr>
                    <td height="30"><strong>售价</strong><br></td>
                    <td width="38%" height="30">${orders[status.index].transactionprice} USD</td>
                </tr>
                <tr>
                    <td height="30"><strong>成交费</strong><br></td>
                    <td width="38%" height="30">${grossdetailamounts[status.index]}USD</td>
                </tr>
                <tr>
                    <td height="30"><strong>SKU</strong><br></td>
                    <td width="38%" height="30">${orders[status.index].sku}</td>
                </tr>
                <tr>
                    <td height="30"><strong>买家选择运输</strong><br></td>
                    <td width="38%" height="30"> ${orders[status.index].selectedshippingservice}</td>
                </tr>
                <tr>
                    <td height="30"><strong>买家选择运输费用</strong><br></td>
                    <td width="38%" height="30">${orders[status.index].selectedshippingservicecost}</td>
                </tr>
                <tr>
                    <td height="30"><strong>保险费</strong><br></td>
                    <td width="38%" height="30">--包括在运输中--</td>
                </tr>
                <tr>
                    <td height="30"><strong>付款状态</strong><br></td>
                    <td width="38%" height="30">
                        <c:if test="${orders[status.index].status=='Complete'}">
                            已付款
                         </c:if>
                        <c:if test="${orders[status.index].status=='Incomplete'}">
                            未付款
                        </c:if></td>
                </tr>
                <tr>
                    <td><font color="#2395F3">${orders[status.index].buyeruserid} ( ${orders[status.index].buyeremail} )</font></td>
                    <td height="30"><strong>付款方式</strong></td>
                    <td height="30">${orders[status.index].paymentmethod}</td>
                </tr>
                </tbody></table></td>
        </tr>
        <tr>
            <td height="40" bgcolor="#F4F4F4" style="font-size:18px; font-family:'微软雅黑', '宋体', Arial">&nbsp;PayPal付款</td>
        </tr>
        <tr>
            <td><table width="100%" border="0">
                <tbody><tr>
                    <td height="30"><strong>PayPal交易号</strong></td>
                    <td height="30"> <strong>付款日期</strong></td>
                    <td width="8%" height="30" align="center"> <strong>状态</strong></td>
                    <td width="15%" height="30"> <strong>总额</strong></td>
                    <td width="15%" height="30"> <strong>PayPal费用</strong></td>
                    <td width="15%" height="30"> <strong>净额</strong></td>
                </tr>
                <c:if test="${orders[status.index].status=='Complete'}">
                <tr>
                    <td height="30">${paypals[status.index]}</td>
                    <td height="30"><fmt:formatDate value="${orders[status.index].paidtime}" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td height="30" align="center">
                    <c:if test="${orders[status.index].status=='Complete'}">
                        <img src="<c:url value ="/img/new_yes.png"/>" width="22" height="22">
                    </c:if>
                    <c:if test="${orders[status.index].status=='Incomplete'}">
                        <img src="<c:url value ="/img/new_no.png"/>" width="22" height="22">
                    </c:if>
                    </td>
                    <td width="15%" height="30"><span name="totalPrice">${orders[status.index].total}</span> USD</td>
                    <td width="15%" height="30"><span name="price"></span></td>
                    <td  width="15%" height="30"><span name="price1"></span></td>
                </tr>
                <c:if test="${orders[status.index].status=='Incomplete'}">
                    <tr>
                        <td height="30"></td>
                        <td height="30"></td>
                        <td height="30" align="center"></td>
                        <td width="15%" height="30"></td>
                        <td width="15%" height="30"></td>
                        <td width="15%" height="30"></td>
                    </tr>
                </c:if>
                </c:if>
                <tr>
                    <td height="30" colspan="6" style="color:#F00"><span class="voknet"></span>PA Note:   PayPal payment Staus:[${orders[status.index].status}],Type:[instant],Amount:[USD${orders[status.index].amountpaid}] recelved on <fmt:formatDate value="${orders[status.index].paidtime}" pattern="yyyy-MM-dd HH:mm"/>,Paypal transaction ID:<br>
                            ${paypals[status.index]}</td>
                </tr>
                </tbody></table></td>
        </tr>
        </tbody></table>
        <hr/>
    </c:forEach>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" onclick="dialogClose();" data-dismiss="modal">关闭</button>
    </div>
</div>
<c:if test="${flag=='true'}">
<div style="display: none;" id="new_svt_2">
</c:if>
<c:if test="${flag=='false'}">
<div style="display: block;" id="new_svt_2">
</c:if>
    <table width="100%" border="0">
        <tbody><tr>
            <td width="772"></td>
            <td width="9" rowspan="5" valign="top">&nbsp;</td>
            <c:if test="${flag=='true'}">
            <td rowspan="5" valign="top" style="margin-left:20px; padding-left:15px; padding-top:20px; padding-right:20px; line-height:25px;background:#F4F4F4"><strong>订单号</strong><br>
                ${order.orderid}(已配货)<br>
                <strong>物流跟踪号</strong><br>
                ${order.shipmenttrackingnumber}<br>
                <strong>发货时间：</strong><br>
                ${order.shippedtime}<br>
                <strong>付款时间：</strong><br>
                ${order.paidtime}<br>
                <strong>运输方式：</strong><br>
                ${order.selectedshippingservice}
                <c:forEach items="${orders}" begin="0"  varStatus="status">
                <span class="voknet"></span>
                <table width="100%" border="0">
                    <tbody><tr>
                        <td width="56"><strong>商品${status.index+1}</strong></td>
                        <td style="color:#63B300">CNDL</td>
                    </tr>
                    <tr>
                        <td><img src="${pictures[status.index]}" width="46" height="46"></td>
                        <td style=" color:#5F93D7">${orders[status.index].title}</td>
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
            </td>
            </c:if>
        </tr>
        <tr>
            <%--<td width="772" align="center" bgcolor="#F6F6F6" style="color:#2395F3">查看更多历史信息...</td>--%>
        </tr>
        <tr>
            <td>
                <div id="add" class="newbook" style="height: 350px;">
                    <p style="text-align: right;">
                        <c:if test="${flag=='true'}">
                            ${order.buyeruserid}&nbsp;
                            <c:if test="${addMessage1!=null}">
                                <fmt:formatDate value="${addMessage1[0].createTime}" pattern="yyyy-MM-dd HH:mm"/>
                            </c:if>

                        </c:if>
                        <c:if test="${flag=='false'}">
                            ${message.sender}&nbsp;<fmt:formatDate value="${addMessage1[0].createTime}" pattern="yyyy-MM-dd HH:mm"/>
                        </c:if>
                    </p>
                <c:forEach items="${addMessage1}" var="addMessage">
                    <c:if test="${addMessage.sender==sender}">
                        <p class="user">${addMessage.recipientid}，您好！</p>
                        <div class="user_co">
                            <div class="user_co_1"></div>
                            <ul>Hi ${addMessage.recipientid}.: )<br/> ${addMessage.body}
                                <span>发送于:<fmt:formatDate value="${addMessage.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                            </ul>
                            <div class="user_co_2"></div>
                        </div>
                        <div class="dpan"></div>
                    </c:if>
                    <c:if test="${addMessage.sender==recipient}">
                        <script type="text/javascript">
                             s="${addMessage.sender}";
                             r1="${addMessage.recipientid}";
                        </script>
                        <p class="admin">${addMessage.sender}</p>

                        <div class="admin_co">
                            <div class="admin_co_1"></div>
                            <ul>Hi ${addMessage.recipientid}.: )<br/>${addMessage.body}
                                <c:if test="${addMessage.replied=='true'}">
                                    <span>发送于:<fmt:formatDate value="${addMessage.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                                </c:if>
                                <c:if test="${addMessage.replied=='false'}">
                                    <span style="color: red">发送失败于:<fmt:formatDate value="${addMessage.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>!</span>
                                </c:if>
                            </ul>
                            <div class="admin_co_2"></div>
                        </div>
                        <div class="dpan"></div>
                    </c:if>
                </c:forEach>
                <%--<div class="newbook">
                <p style="text-align: right;">aasla_karih 2014-08-20 22:38</p>
                <p class="user">containyou，您好！</p>
                <div class="user_co">
                    <div class="user_co_1"></div>
                    <ul>Hi again.: )
                        <span>发送与:2014-08-20 22:38</span>
                    </ul>
                    <div class="user_co_2"></div>
                </div>--%>
                <%--<div class="dpan"></div>
                <p class="admin">携宇科技</p>

                <div class="admin_co">
                    <div class="admin_co_1"></div>
                    <ul>Hi again.: )
                        <span>发送与:2014-08-20 22:38</span>
                    </ul>
                    <div class="admin_co_2"></div>
                </div>
                <p class="user">containyou，您好！</p>
                <div class="user_co">
                    <div class="user_co_1"></div>
                    <ul>Hi again.: )
                        <span>发送与:2014-08-20 22:38</span>
                    </ul>
                    <div class="user_co_2"></div>
                </div>
                <div class="dpan"></div>
                <p class="admin">携宇科技</p>

                <div class="admin_co">
                    <div class="admin_co_1"></div>
                    <ul>Hi again.: )
                        <span>发送与:2014-08-20 22:38</span>
                    </ul>
                    <div class="admin_co_2"></div>
                </div>--%>
            </div></td>
        </tr>
        <tr>
            <td>
                <form id="sendForm">
                    <input type="hidden" name="itemid" value="${order.itemid}">
                    <input type="hidden" name="buyeruserid" value="${order.buyeruserid}">
                    <input type="hidden" name="selleruserid" value="${order.selleruserid}">
                    <input type="hidden" name="transactionid" value="${order.transactionid}">
                    <input type="hidden" name="itemid1" value="${addMessage1[0].itemid}">
                    <input type="hidden" name="buyeruserid1" value="${message.sender}">
                    <input type="hidden" name="selleruserid1" value="${message.recipientuserid}">
                    <input type="hidden" name="subject" value="${addMessage1[0].subject}">
                    <c:if test="${flag=='true'}">
                        <textarea name="body"  id="textarea" style="width:772px;" rows="5"  class="newco_one validate[required]"></textarea>
                    </c:if>
                    <c:if test="${flag=='false'}">
                        <textarea name="body"  id="textarea" style="width:1125px;" rows="5"  class="newco_one1 validate[required]"></textarea>
                    </c:if>

                </form>
                <div class="modal-footer" style="margin-top:20px; float:left; width:100%;">
                <%--<button type="button" class="btn btn-primary">保存</button>--%>
                <button type="button" class="btn btn-primary" onclick="selectSendMessage();">选择模板</button>
                <button type="button" class="btn btn-newco" onclick="submitCommit1();">回复</button>
                <button type="button" class="btn btn-default" onclick="dialogClose();" data-dismiss="modal">关闭</button>
                </div>
            </td>
        </tr>
        <tr>
            <td width="772"> </td>
        </tr>
        </tbody></table>

</div></div>


</div>
<%--&lt;%&ndash;-----------------------------------------修改前----------------------------------------------&ndash;%&gt;
<div class="easyui-tabs" border="0" style="width:1020px;">
    <div title="摘要" style="padding:10px" >
        <c:forEach items="${orders}" var="orders1">
        <div class="easyui-panel" style="width:1000px"  title="1|销售编号:|eBay销售编号:|${orders1.selleruserid}">
            <iframe id="frameLeft" src="${rootpath}/order/viewOrderAbstractLeft.do?TransactionId=${orders1.transactionid}&selleruserid=${orders1.selleruserid}"  align="left"  frameborder="0" style="width: 558px;height: 690px;"></iframe>
            <iframe id="frameRight" src="${rootpath}/order/viewOrderAbstractRight.do?TransactionId=${orders1.transactionid}&selleruserid=${orders1.selleruserid}"  align="right"  frameborder="0" style="width: 418px;height:690px;"></iframe>
        </div>
        <div class="easyui-panel"  style="width:1000px" title="最近消息">
            <iframe id="frameDown" src="${rootpath}/order/viewOrderAbstractDown.do?TransactionId=${orders1.transactionid}&selleruserid=${orders1.selleruserid}"   frameborder="0" style="width: 976px;height: 200px;"></iframe>
          &lt;%&ndash;  &lt;%&ndash;${addMessage.body}&ndash;%&gt;
            <c:forEach items="${addMessage}" var="addmessage">
                ${addmessage.body}
            </c:forEach>&ndash;%&gt;
        </div>
        </c:forEach>
    </div>
    &lt;%&ndash;---------------------------------------------------------------------------------------&ndash;%&gt;
    <div title="运输" style="padding:10px">
        <form id="tranForm">
            <input type="hidden" name="id" value="${order.orderid}"/>
        <a href="javascipt:void()" onclick="transportSelect()">寄件人地址 \ 退货地址 \ 揽收地址</a>
        <div id="returnAddress">

        </div>
        <hr  style="border:1px dashed #000000; ">
        <div>
            <div style="border: 1px;" id="left">

                <b>收件人地址</b>
                &nbsp;&nbsp;&nbsp;
                <table style="text-align: left">
                    <tr>
                        <td>联系人:</td>
                        <td><input type="text"  value="${order.buyeruserid}"/></td>
                    </tr>
                    <tr>
                        <td>公司:</td>
                        <td><input type="text"  value=""/></td>
                    </tr>
                    <tr>
                        <td>街道:</td>
                        <td><input type="text"  value="${order.street1}"/></td>
                    </tr>
                    <tr>
                        <td>城市:</td>
                        <td><input type="text"  value="${order.cityname}"/></td>
                    </tr>
                    <tr>
                        <td>州/省:</td>
                        <td><input type="text"  value="${order.stateorprovince}"/></td>
                    </tr>
                    <tr>
                        <td>国家:</td>
                        <td><input type="text"  value="${order.cityname}"/></td>
                    </tr>
                    <tr>
                        <td>邮编:</td>
                        <td><input type="text"  value="${order.postalcode}"/></td>
                    </tr>
                    <tr>
                        <td>电话:</td>
                        <td><input type="text"  value="${order.phone}"/></td>
                    </tr>
                    <tr>
                        <td>电邮:</td>
                        <td><input type="text"  value="${order.buyeremail}"/></td>
                    </tr>
                </table>
            </div>
            <div id="center">
                <div id="rightCenter" class="table-a" >
                    <table style="width: 390px;">
                        <tr><td>eBay 地址:</td></tr>
                        <tr><td>${order.street1}</td></tr>
                        <tr><td>${order.cityname}</td></tr>
                        <tr><td>${order.phone}</td></tr>
                        <tr><td>${order.countryname}</td></tr>
                        <tr><td>${order.buyeremail}</td></tr>
                    </table>
                </div>
                <div id="right" class="table-a">
                    <table style="width: 390px;">
                        <tr><td>PayPal 地址:</td></tr>
                        <tr><td>${order.street1}</td></tr>
                        <tr><td>${order.cityname}</td></tr>
                        <tr><td>${order.phone}</td></tr>
                        <tr><td>${order.countryname}</td></tr>
                        <tr><td>${order.buyeremail}</td></tr>
                    </table>
                </div>
            </div>
        </div>
        <br/>
        <div>
            <table style="width: 900px;" border="1" cellpadding="0" cellspacing="0">
                <tr><td>物品号 \ SKU</td>
                    <td>标题</td>
                    <td>数量</td>
                    <td>重量(kg)</td>
                    <td>价值 (美元)</td>
                    <td>原产地</td>
                    <td>动作</td></tr>
                <c:forEach items="${orders}" var="orders1">
                    <tr><td>${orders1.itemid}<br/>
                        </td>
                        <td>${orders1.title}</td>
                        <td>${orders1.quantitypurchased}</td>
                        <td>重量(kg)</td>
                        <td>${orders1.transactionprice}$</td>
                        <td>原产地</td></tr>
                    <c:if test="${orders1.sku!=null}">
                        <tr><td><input type="text" value="${orders1.sku}"/><br/>
                        </td>
                            <td><input type="text" value="${orders1.title}"/></td>
                            <td><input type="text" value="${orders1.quantitypurchased}"/></td>
                            <td><input type="text" value=""/></td>
                            <td><input type="text" value="${orders1.transactionprice}"/></td>
                            <td><input type="text" value=""/></td>
                            </tr>
                    </c:if>
                </c:forEach>
            </table>
        </div><br/>
        <div>
            <table style="width: 900px;" border="1" cellpadding="0" cellspacing="0">
                <tr><td>发货日期</td>
                <td>运费</td>
                <td>承运商</td>
                <td>重量</td>
                <td>追踪号</td>
                <td>动作</td></tr>
                <tr><td><input type="text" name="shippedtime" value="<fmt:formatDate value="${order.shippedtime}" pattern="yyyy-MM-dd HH:mm"/>"  onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm'})" /></td>
                    <td><input type="text" name="freight" value="${order.actualshippingcost}"/>&nbsp;
                     &lt;%&ndash;   <select>
                            <c:forEach>

                            </c:forEach>
                        </select>&ndash;%&gt;
                    </td>
                    <td>
                        &lt;%&ndash;<select>
                            <c:forEach>

                            </c:forEach>
                        </select>&ndash;%&gt;
                    </td>
                    <td></td>
                    <td><input type="text" value="${order.shipmenttrackingnumber}"/></td>
                    <td></td></tr>
            </table>
        </div>
        <br/>
        状态&nbsp;${order.orderstatus}<br/>
        <input type="checkbox" name="tran" value="false" id="tranCheckBox" onclick="boxChange();"/>上门揽收 (勾选上门揽收，表示承运商的工作人员上门揽收包裹，如果不勾选，表示卖家自送)<br/>
        </form>
        <c:if test="${order.orderstatus=='Completed'}">
            <div style="text-align: right">
                <input type="button" value="保存" onclick="submitCommit();"/>
            </div>
        </c:if>

    </div>
    &lt;%&ndash;---------------------------------------------------------------------------------------&ndash;%&gt;
    <div title="发货历史" style="padding:10px">
        <c:if test="${order.orderstatus=='Shipped'||order.orderstatus=='Completed'}">
        <table border="1" cellspacing="0" cellpadding="0" style="width: 1000px;">
            <tr>
                <td>订单号 / 源订单号</td>
                <td>地址</td>
                <td>追踪号</td>
                <td>数量</td>
                <td>状态</td>
                <td>发货日期</td>
            </tr>
            <tr>
                <td>${order.orderid}</td>
                <td>
                    ${order.street1}<br/>${order.cityname}<br/>${order.phone}<br/>${order.countryname}<br/>${order.buyeremail}
                </td>
                <td>${order.quantitypurchased}</td>
                <td>${order.quantitypurchased}</td>
                <td>${order.orderstatus}</td>
                <td>${order.shippedtime}</td>
            </tr>
        </table>
        </c:if>
    </div>
    &lt;%&ndash;---------------------------------------------------------------------------------------&ndash;%&gt;
    <div title="ebay消息" style="padding:10px">
        卖家消息:<br/>
        <table style="width: 900px;">
            <tr>
                <td>主题</td>
                <td>From->To</td>
                <td>物品号</td>
                <td>日期</td>
                <td>动作</td>
            </tr>
            <c:forEach items="${addMessage1}" var="addmessage1">
                 <tr>

                    <td>
                        ${addmessage1.body}<br/>
                    </td>
                    <td>
                       ${addmessage1.sender}到${addmessage1.recipientid}
                    </td>
                    <td>
                       ${addmessage1.itemid}
                    </td>
                    <td><fmt:formatDate value="${addmessage1.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td>-</td>

                 </tr>
            </c:forEach>
        </table>
        <div style="text-align: right">
            <input type="button" value="发送消息" onclick="sendMessage();"/>
        </div>
        买家消息:<br/>
        <table style="width: 900px;">
            <tr>
                <td>主题</td>
                <td>From->To</td>
                <td>物品号</td>
                <td>日期</td>
                <td>动作</td>
            </tr>

            <c:forEach items="${messages1}" var="messages2">
                <tr>

                    <td>
                        ${messages2.subject}<br/>
                    </td>
                    <td>
                        ${order.buyeruserid}->${order.selleruserid}
                    </td>
                    <td>
                        ${order.itemid}
                    </td>
                    <td>${messages2.receivedate}</td>
                    <td>-</td>

                </tr>
            </c:forEach>
        </table>

    </div>
    &lt;%&ndash;---------------------------------------------------------------------------------------&ndash;%&gt;
    <div title="购买历史" style="padding:10px" onclick="BuyHistpry();">
        <iframe id="frameBuyHistory"  frameborder="0" style="width: 976px;height:700px;"></iframe>
    </div>
</div>
<div style="text-align: right">
    <input type="button" value="关闭" onclick="dialogClose();"/>
</div>--%>
</body>
</html>
