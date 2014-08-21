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
        $(document).ready(function(){
            $("#frameLeft").attr("src",path + "/order/viewOrderAbstractLeft.do?orderId=${orderId}");
            $("#frameRight").attr("src",path + "/order/viewOrderAbstractRight.do?orderId=${orderId}");
            $("#frameDown").attr("src",path + "/order/viewOrderAbstractDown.do?orderId=${orderId}");
            $("#frameBuyHistory").attr("src",path + "/order/viewOrderBuyHistory.do?orderId=${orderId}");
           /* var returnAdressTable=$("#returnAdressTable");
             clone=returnAdressTable.clone();
             $("#returnAddress").removeChild(returnAdressTable);*/

        });
        function dialogClose(){
            W.OrderGetOrders.close();
        }
        var flag=false;
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
                        Base.token();
                        W.refreshTable();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );

        }
        /*var flag=fasle;
        function transportSelect(){
         flag=!flag;
         var returnAddress=$("#returnAddress");
         console.debug(returnAddress);

         var returnAddress1="<table><tr><td><b>寄件人地址:</b></td><td><input type='button' value='应用'/></td>" +
         "<td><b>退货地址:</b></td><td><input type='button' value='应用'/></td></tr>" +
         "<tr><td>联系人:</td><td><input type='text' /></td><td>联系人:</td><td><input type='text' /></td></tr>" +
         "<tr><td>公司:</td><td><input type='text' /></td><td>公司:</td><td><input type='text' /></td></tr>" +
         "<tr><td>国家:</td><td><input type='text' /></td><td>国家:</td><td><input type='text' /></td></tr>" +
         "<tr><td>省:</td><td><input type='text' /></td>" +
         "<td>省:</td><td><input type='text' /></td></tr>" +
         "<tr><td>城市:</td><td><input type='text' /></td><td>城市:</td><td><input type='text' /></td></tr>" +
         "<tr><td>区:</td><td><input type='text' /></td><td>区:</td><td><input type='text' /></td></tr>" +
         "<tr><td>街道:</td><td><input type='text' /></td><td>街道:</td><td><input type='text' /></td></tr>" +
         "<tr><td>邮编:</td><td><input type='text' /></td><td>邮编:</td><td><input type='text' /></td></tr>" +
         "<tr><td>手机:</td><td><input type='text' /></td><td>手机:</td><td><input type='text' /></td></tr>" +
         "<tr><td>电邮:</td><td><input type='text' /></td><td>电邮:</td><td><input type='text' /></td></tr></table>";
         if(flag){
         $(returnAddress).innerHTML=returnAddress1;
         }else{
         $(returnAddress).innerHTML="";
         }
         *//* if(){

         }*//*
         *//*var address="";*//*
         }*/
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
        <div class="easyui-panel" style="width:1000px"  title="1|销售编号:|eBay销售编号:|${order.selleruserid}">
            <iframe id="frameLeft" align="left"  frameborder="0" style="width: 558px;height: 690px;"></iframe>
            <iframe id="frameRight" align="right"  frameborder="0" style="width: 418px;height:690px;"></iframe>
        </div>
        <div class="easyui-panel"  style="width:1000px" title="最近消息">
            <iframe id="frameDown"  frameborder="0" style="width: 976px;height: 200px;"></iframe>
        </div>
    </div>
    <%-------------------------------------------------------------------------------------------%>
    <div title="运输" style="padding:10px">
        <form id="tranForm">
            <input type="hidden" name="id" value="${order.orderid}"/>
        <a href="javascipt:void()" onclick="transportSelect()">寄件人地址 \ 退货地址 \ 揽收地址</a>
        <div id="returnAddress">
           <%-- <table id="returnAdressTable">
                <tr><td><b>寄件人地址:</b></td><td><input type='button' value='应用'/></td>
                    <td><b>退货地址:</b></td><td><input type='button' value='应用'/></td>
                </tr>
                <tr><td>联系人:</td><td><input type='text' /></td>
                    <td>联系人:</td><td><input type='text' /></td>
                </tr>
                <tr><td>公司:</td><td><input type='text' /></td>
                    <td>公司:</td><td><input type='text' /></td>
                </tr>
                <tr><td>国家:</td><td><input type='text' /></td>
                    <td>国家:</td><td><input type='text' /></td>
                </tr>
                <tr><td>省:</td><td><input type='text' /></td>
                    <td>省:</td><td><input type='text' /></td>
                </tr>
                <tr><td>城市:</td><td><input type='text' /></td>
                    <td>城市:</td><td><input type='text' /></td>
                </tr>
                <tr><td>区:</td><td><input type='text' /></td>
                    <td>区:</td><td><input type='text' /></td>
                </tr>
                <tr><td>街道:</td><td><input type='text' /></td>
                    <td>街道:</td><td><input type='text' /></td>
                </tr>
                <tr><td>邮编:</td><td><input type='text' /></td>
                    <td>邮编:</td><td><input type='text' /></td>
                </tr>
                <tr><td>手机:</td><td><input type='text' /></td>
                    <td>手机:</td><td><input type='text' /></td>
                </tr>
                <tr><td>电邮:</td><td><input type='text' /></td>
                    <td>电邮:</td><td><input type='text' /></td>
                </tr>
            </table>--%>
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
                <tr><td>${order.itemid}<br/>
                    </td>
                    <td>${order.title}</td>
                    <td>${order.quantitypurchased}</td>
                    <td>重量(kg)</td>
                    <td>${order.transactionprice}$</td>
                    <td>原产地</td>
                    <td>动作</td></tr>
                <c:if test="${order.sku!=null}">
                    <tr><td><input type="text" value="${order.sku}"/><br/>
                    </td>
                        <td><input type="text" value="${order.title}"/></td>
                        <td><input type="text" value="${order.quantitypurchased}"/></td>
                        <td><input type="text" value=""/></td>
                        <td><input type="text" value="${order.transactionprice}"/></td>
                        <td><input type="text" value=""/></td>
                        <td><a href="void()">保存 SKU</a><br/><a href="void()">捆绑产品</a>
                        </td></tr>
                </c:if>
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
                    <td></td>
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
        <c:if test="${order.orderstatus=='Shipped'}">
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
            <c:forEach items="${addMessage}" var="message">
                 <tr>

                    <td>
                        ${message.body}<br/>
                    </td>
                    <td>
                        ${order.selleruserid}->${order.buyeruserid}
                    </td>
                    <td>
                        ${order.itemid}
                    </td>
                    <td>${message.createTime}</td>
                    <td>-</td>

                 </tr>
            </c:forEach>
        </table>
        买家消息:<br/>
        <table style="width: 900px;">
            <tr>
                <td>主题</td>
                <td>From->To</td>
                <td>物品号</td>
                <td>日期</td>
                <td>动作</td>
            </tr>
            <c:forEach items="${messages}" var="message">
                <tr>

                    <td>
                        ${message.subject}<br/>
                    </td>
                    <td>
                        ${order.buyeruserid}->${order.selleruserid}
                    </td>
                    <td>
                        ${order.itemid}
                    </td>
                    <td>${message.receivedate}</td>
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
