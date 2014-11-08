<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<%--<%@include file= "/WEB-INF/jsp/smallFormImport.jsp" %>--%>
<html>
<head>
    <title></title>
    <script>
        var api = frameElement.api, W = api.opener;
        function submitCommit(){
            if(!jQuery("#payPalForm").validationEngine("validate"))
            {
                return;
            }
            var url=path+"/ajax/savePayPal.do";
            var data=$("#payPalForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r)
                        W.refreshTablepaypal();
                        W.payPal.close();
                    },
                        function(m,r){
                            alert(r);
                        }]
            );
        }
        $(document).ready(function() {
            jQuery("#payPalForm").validationEngine();
            var type = '${type}';
            if(type=="01"){
                $("input").each(function(i,d){
                    $(d).attr("disabled",true);
                });
                $("select").each(function(i,d){
                    $(d).attr("disabled",true);
                });
                $("textarea").attr("disabled",true);
                $("button").hide();
            }
        });
        function winClose(){
            W.payPal.close();
        }
    </script>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
</head>
<c:set value="${paypal}" var="paypal" />
<body>
<div class="modal-header">
    <div class="newtitle">支付方式</div>
</div>
<div class="modal-body">
    <form class="form-horizontal" role="form" id="payPalForm">
        <table width="90%" border="0" style="margin-left:40px;">
            <tr>
                <td width="20%" height="28" align="right">名称：</td>
                <td width="41%" height="28">
                    <div class="newselect" style="margin-top:9px;">
                        <input type="text" name="name" id="name" value="${paypal.payName}"  placeholder="" class="form-controlsd validate[required]">
                        <input type="hidden" name="id" id="id" value="${paypal.id}">
                    </div>
                </td>
                <td width="43%" height="28"></td>
            </tr>
            <tr>
                <td height="28" align="right">站点：</td>
                <td height="28" style=" padding-left:4px;">
                    <div class="col-lg-10">
                        <div class="ui-select" style="margin-top:9px;">
                            <select name="site" width="300px;">
                                <c:forEach items="${siteList}" var="sites">
                                    <c:if test="${paypal.site==sites.id}">
                                        <option value="${sites.id}" selected="selected">${sites.name}</option>
                                    </c:if>
                                    <c:if test="${paypal.site!=sites.id}">
                                        <option value="${sites.id}">${sites.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </td>
                <td height="28">&nbsp;</td>
            </tr>
            <tr>
                <td height="28" align="right">帐号：</td>
                <td height="28" style=" padding-left:4px;">
                    <div class="col-lg-10">
                        <div class="ui-select" style="margin-top:9px;">
                            <select name="paypal" width="300px;">
                                <c:forEach items="${paypalList}" var="pay">
                                    <c:if test="${paypal.paypal==pay.id}">
                                        <option value="${pay.id}" selected="selected">${pay.paypalAccount}</option>
                                    </c:if>
                                    <c:if test="${paypal.paypal!=pay.id}">
                                        <option value="${pay.id}">${pay.paypalAccount}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </td>
                <td height="28">&nbsp;</td>
            </tr>
            <tr>
                <td width="16%" height="28" align="right">说明：</td>
                <td width="41%" height="28">
                    <div class="col-lg-10" style="margin-top:9px;">
                        <textarea name="paypal_desc"  class="form-control" cols="" rows="2" style="width:560px;">${paypal.paymentinstructions}</textarea>
                    </div>
                </td>
                <td width="43%" height="28"></td>
            </tr>
        </table>
    </form>
</div>

<div class="modal-footer">
    <button type="button" class="net_put" onclick="submitCommit()">确定</button>
    <button type="button" class="net_put_1" data-dismiss="modal" onclick="winClose()">关闭</button>
</div>
</body>
</html>
