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
                        W.refreshTable();
                        W.payPal.close();
                    },
                        function(m,r){
                            alert(r);
                        }]
            );
        }
        $(document).ready(function() {
            jQuery("#payPalForm").validationEngine();
        });
    </script>
</head>
<c:set value="${paypal}" var="paypal" />
<body>
<form id="payPalForm">
    <br/>
    <br/>
    <table>
        <tr>
            <td>名称:</td>
            <td>
                <input type="hidden" name="id" id="id" value="${paypal.id}">
                <input type="text" name="name" id="name" value="${paypal.payName}" class="validate[required]" ></td>
        </tr>
        <tr>
            <td>站点:</td>
            <td>
                <select name="site"  class="validate[required]">
                    <c:forEach items="${siteList}" var="sites">
                        <c:if test="${paypal.site==sites.id}">
                            <option value="${sites.id}" selected="selected">${sites.name}</option>
                        </c:if>
                        <c:if test="${paypal.site!=sites.id}">
                            <option value="${sites.id}">${sites.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <td>paypal账号:</td>
        <td>
            <select name="paypal"  class="validate[required]">
                <c:forEach items="${paypalList}" var="pay">
                    <c:if test="${paypal.paypal==pay.id}">
                        <option value="${pay.id}" selected="selected">${pay.configName}</option>
                    </c:if>
                    <c:if test="${paypal.paypal!=pay.id}">
                        <option value="${pay.id}">${pay.configName}</option>
                    </c:if>
                </c:forEach>
            </select>
        </td>
        </tr>
        </tr>
        <td>付款说明:</td>
        <td>
            <textarea name="paypal_desc" cols="30" rows="5">${paypal.paymentinstructions}</textarea>
            <div><input type="button" value="确定" onclick="submitCommit();"/></div>
        </td>
        </tr>
    </table>
</form>
</body>
</html>
