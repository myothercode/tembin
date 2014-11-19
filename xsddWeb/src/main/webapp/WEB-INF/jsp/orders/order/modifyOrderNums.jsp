<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/11/17
  Time: 16:26
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
        function submitCommit(){
            var url=path+"/order/apiModifyOrdersMessage.do";
            var data=$("#modifyOrderStatus").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.refreshTable1();
                        W.OrderGetOrders.close();
                        Base.token;
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }],{isConverPage:true}
            );
        }
        function closedialog(){
            W.OrderGetOrders.close();
        }
    </script>
</head>
<body>
<div class="modal-body">
    <form id="modifyOrderStatus" class="form-horizontal" role="form">
        <table width="100%" border="0" style="margin-left:20px;">
            <tbody><tr>
                <td width="16%" height="28" align="center">Transactionid</td>
                <td width="16%" height="28" align="center">Itemid</td>
                <td width="16%" height="28" align="center">
                    追踪号
                <%--<div class="newselect" style="margin-top:9px;">
                    <input name="transactionid" value="${order.transactionid}" class="form-controlsd" type="text">--%>
                </div></td>
                <td width="16%" height="28" align="center">货运商</td>
            </tr>
            <c:forEach items="${orders}" var="order" begin="0" varStatus="status">
                <tr>
                    <td height="28" align="left">
                        <div class="newselect" style="margin-top:9px;width: auto;">
                            <input type="hidden" name="selleruserid${status.index}" value="${order.selleruserid}"/>
                            <input type="hidden" name="shippStatus${status.index}" value="true">
                        <input style="width: auto" name="transactionid${status.index}" value="${order.transactionid}" class="form-controlsd" type="text">
                        </div>
                    </td>
                    <td height="28" align="left">
                        <div class="newselect" style="margin-top:9px;width: auto;">
                            <input style="width: auto" name="itemid${status.index}" value="${order.itemid}" class="form-controlsd" type="text">
                        </div>
                    </td>
                    <td height="28" align="left">
                        <div class="newselect" style="margin-top:9px;width: auto;">
                            <input style="width: auto" name="ShipmentTrackingNumber${status.index}" id="ShipmentTrackingNumber${status.index}" class="form-controlsd" type="text">
                        </div>
                    </td>
                    <td height="28" align="left">
                        <div class="newselect" style="margin-top:9px;width: auto;">
                            <input style="width: auto" name="ShippingCarrierUsed${status.index}" id="ShippingCarrierUsed${status.index}" class="form-controlsd" type="text">
                        </div>
                    </td>
                </tr>
            </c:forEach>

           <%-- <tr>
                <td height="28" align="right">追踪号：</td>
                <td height="28"><div class="newselect" style="margin-top:9px;">
                    <input name="ShipmentTrackingNumber" id="ShipmentTrackingNumber" class="form-controlsd" type="text">
                </div></td>
                <td height="28">&nbsp;</td>
            </tr>
            <tr>
                <td height="28" align="right">货运商：</td>
                <td height="28"><div class="newselect" style="margin-top:9px;">
                    <input name="ShippingCarrierUsed" id="ShippingCarrierUsed" class="form-controlsd" type="text">
                </div></td>
                <td height="28">&nbsp;</td>
            </tr>--%>
            <tr>
                <td height="28" align="right">&nbsp;</td>
                <td height="28" style=" padding-top:22px;"><button type="button" class="net_put" onclick="submitCommit();">保存</button><button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button></td>
                <td height="28">&nbsp;</td>
            </tr>
            </tbody></table>

    </form>
</div>
</body>
</html>
