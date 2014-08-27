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
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/default/easyui.css" />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/icon.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/jquery-easyui/jquery.easyui.min.js" /> ></script>
    <script type="text/javascript">
        var clone=null;
        var api = frameElement.api, W = api.opener;
        var flag=false;
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
            viewsendMessage=$.dialog({title: '发送消息',
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
<div class="easyui-tabs" border="0" style="width:1020px;">
<%-------------------------------------------------------------------------------------------%>
    <div title="摘要" style="padding:10px" >
        <c:forEach items="${orders}" var="orders1">
        <div class="easyui-panel" style="width:1000px"  title="1|销售编号:|eBay销售编号:|${orders1.selleruserid}">
            <iframe id="frameLeft" src="${rootpath}/order/viewOrderAbstractLeft.do?TransactionId=${orders1.transactionid}"  align="left"  frameborder="0" style="width: 558px;height: 690px;"></iframe>
            <iframe id="frameRight" src="${rootpath}/order/viewOrderAbstractRight.do?TransactionId=${orders1.transactionid}"  align="right"  frameborder="0" style="width: 418px;height:690px;"></iframe>
        </div>
        <div class="easyui-panel"  style="width:1000px" title="最近消息">
            <iframe id="frameDown" src="${rootpath}/order/viewOrderAbstractDown.do?TransactionId=${orders1.transactionid}"   frameborder="0" style="width: 976px;height: 200px;"></iframe>
          <%--  &lt;%&ndash;${addMessage.body}&ndash;%&gt;
            <c:forEach items="${addMessage}" var="addmessage">
                ${addmessage.body}
            </c:forEach>--%>
        </div>
        </c:forEach>
    </div>
    <%-------------------------------------------------------------------------------------------%>
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
                     <%--   <select>
                            <c:forEach>

                            </c:forEach>
                        </select>--%>
                    </td>
                    <td>
                        <%--<select>
                            <c:forEach>

                            </c:forEach>
                        </select>--%>
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
    <%-------------------------------------------------------------------------------------------%>
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
    <%-------------------------------------------------------------------------------------------%>
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
    <%-------------------------------------------------------------------------------------------%>
    <div title="购买历史" style="padding:10px" onclick="BuyHistpry();">
        <iframe id="frameBuyHistory"  frameborder="0" style="width: 976px;height:700px;"></iframe>
    </div>
</div>
<div style="text-align: right">
    <input type="button" value="关闭" onclick="dialogClose();"/>
</div>
</body>
</html>
