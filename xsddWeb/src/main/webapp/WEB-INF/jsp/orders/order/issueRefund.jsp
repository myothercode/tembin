<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/10/24
  Time: 9:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <style>
        .table-a table{border:1px solid rgba(0, 0, 0, 0.23)
        }
    </style>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        function closedialog(){
            W.viewsendMessage.close();
        }
        function submit3(){
            var url=path+"/order/sendRefund.do?";
            var data=$("#sendRefund").serialize();
            var a=confirm("确认退款");
            if(a==true){
                $().invoke(url,data,
                        [function(m,r){
                            alert(r);
                            W.viewsendMessage.close();
                            Base.token();
                        },
                            function(m,r){
                                alert(r);
                                Base.token();
                            }]
                );
            }
        }
    </script>
</head>
<body>
<div style="padding: 30px;">
<form id="sendRefund">
    <input type="hidden" name="transactionid" value="${order.transactionid}">
    <input type="hidden" name="selleruserid" value="${order.selleruserid}">
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
    </tbody></table>
</form>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-newco" onclick="submit3();">确定</button>
    <button type="button" class="btn btn-default" onclick="closedialog();" data-dismiss="modal">关闭</button>
</div> </td>
</body>
</html>
