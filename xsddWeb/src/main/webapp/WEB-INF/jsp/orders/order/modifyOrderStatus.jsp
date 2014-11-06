<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/28
  Time: 11:47
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
                        W.refreshTable();
                        W.OrderGetOrders.close();
                        Base.token;
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function changeStatus(){
            var shippStatus=$("#shippStatus").val();
            if(shippStatus=='true'){
                $("#ShipmentTrackingNumber").attr("disabled",false);
                $("#ShippingCarrierUsed").attr("disabled",false);
            }else{
                $("#ShipmentTrackingNumber").attr("disabled",true);
                $("#ShippingCarrierUsed").attr("disabled",true);
            }

        }
        function closedialog(){
            W.OrderGetOrders.close();
        }
    </script>
</head>
<body>


    <div class="modal-body">
        <form id="modifyOrderStatus" class="form-horizontal" role="form">
            <input type="hidden" name="selleruserid" value="${order.selleruserid}"/>
            <input type="hidden" name="shippStatus" value="true">
            <table width="100%" border="0" style="margin-left:20px;">
                <tbody><tr>
                    <td width="16%" height="28" align="right">Transactionid：</td>
                    <td width="41%" height="28"><div class="newselect" style="margin-top:9px;">
                        <input name="transactionid" value="${order.transactionid}" class="form-controlsd" type="text">
                    </div></td>
                    <td width="43%" height="28"></td>
                </tr>
                <tr>
                    <td height="28" align="right">Itemid：</td>
                    <td height="28"><div class="newselect" style="margin-top:9px;">
                        <input name="itemid" value="${order.itemid}" class="form-controlsd" type="text">
                    </div></td>
                    <td height="28">&nbsp;</td>
                </tr>
                <tr>
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
                </tr>
                <tr>
                    <td height="28" align="right">&nbsp;</td>
                    <td height="28" style=" padding-top:22px;"><button type="button" class="net_put" onclick="submitCommit();">保存</button><button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button></td>
                    <td height="28">&nbsp;</td>
                </tr>
                </tbody></table>

        </form>
    </div>

<%--    <form id="modifyOrderStatus">
    <input type="hidden" name="selleruserid" value="${order.selleruserid}"/>
    <table id="statusTable">
        <tr>
            <td>TransactionID:</td>
            <td><input type="text" name="transactionid" value="${order.transactionid}" /></td>
        </tr>
        <tr>
            <td>ItemID:</td>
            <td><input type="text" name="itemid" value="${order.itemid}"/></td>
        </tr>
        <tr>
            <td>修改发货状态:</td>
            <td><select id="shippStatus" name="shippStatus" onchange="changeStatus();">
                <option value="true">true</option>
                <option value="false">false</option>
            </select></td>
        </tr>
        <tr>
            <td>追踪号:</td>
            <td><input type="text" name="ShipmentTrackingNumber" id="ShipmentTrackingNumber"/></td>
        </tr>
        <tr>
            <td>货运商:</td>
            <td><input type="text" name="ShippingCarrierUsed" id="ShippingCarrierUsed"/></td>
        </tr>
    </table>
    <input type="button" value="修改发货状态" onclick="submitCommit();"/>
</form>--%>
</body>
</html>
