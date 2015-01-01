<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/12/28
  Time: 11:39
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
            W.inventorySale.close();
        }
        function submitCommit(){
            if(!$("#ShiHaiYouForm").validationEngine("validate")){
                return;
            }
            var url=path+"/inventorySale/ajax/saveShiHaiYou.do";
            var data=$("#ShiHaiYouForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.inventorySale.close();
                        Base.token();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        $(document).ready(function(){
            $("#ShiHaiYouForm").validationEngine();
        });
    </script>
</head>
<body>
<form id="ShiHaiYouForm">
    <br/><br/>
    <input type="hidden" name="id" id="id" value="${inventory.id}">
    <input type="hidden" name="sku" value="${inventory.sku}">
    <table align="center">
        <tr>
            <td>SKU:</td>
            <td><input type="text" disabled class="form-controlsd"  value="${inventory.sku}"/></td>
        </tr>
        <tr>
            <td>可用库存:</td>
            <td><input type="text" class="form-controlsd" disabled   value="${inventory.quantity}"/></td>
        </tr>
        <tr>
            <td>库存预警值:</td>
            <td><input type="text" class="form-controlsd validate[required]"  name="warnming" /></td>
        </tr>
        <tr>
            <td>提价比列:</td>
            <td><input type="text" class="form-controlsd validate[required]" name="asd"/></td>
        </tr>
    </table>
    <%--&nbsp;&nbsp;&nbsp;备注:<input type="text" class="form-controlsd validate[required]" name="comment" value="${information.comment}"/>--%>
</form>
<div class="modal-footer" align="right">
    <button type="button" class="net_put" onclick="submitCommit();">保存</button>
    <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>
</div>
</body>
</html>