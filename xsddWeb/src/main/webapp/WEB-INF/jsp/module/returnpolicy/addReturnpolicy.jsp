<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/29
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery-1.9.0.min.js" />"></script>
<html>
<head>
    <title></title>
</head>
<c:set value="${Returnpolicy}" var="Returnpolicy" />
<body>
<form action="/xsddWeb/saveReturnpolicy.do" method="post">
    <table>
        <tr>
            <td>名称:</td>
            <td>
                <input type="hidden" name="id" id="id" value="${Returnpolicy.id}">
                <input type="text" name="name" id="name" value="${Returnpolicy.name}"></td>
        </tr>
        <tr>
            <td>站点:</td>
            <td>
                <select name="site">
                    <c:forEach items="${siteList}" var="sites">
                        <c:if test="${Returnpolicy.site==sites.id}">
                            <option value="${sites.id}" selected="selected">${sites.name}</option>
                        </c:if>
                        <c:if test="${Returnpolicy.site!=sites.id}">
                            <option value="${sites.id}">${sites.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
        <td>退货政策:</td>
        <td>
            <select name="ReturnsAcceptedOption">
                <c:forEach items="${acceptList}" var="accept">
                    <c:if test="${Returnpolicy.returnsacceptedoption==accept.id}">
                        <option value="${accept.id}" selected="selected">${accept.name}</option>
                    </c:if>
                    <c:if test="${Returnpolicy.returnsacceptedoption!=accept.id}">
                        <option value="${accept.id}">${accept.name}</option>
                    </c:if>
                </c:forEach>
            </select>
        </td>
        </tr>
        <tr>
            <td>退货天数:</td>
            <td>
                <select name="ReturnsWithinOption">
                    <c:forEach items="${withinList}" var="within">
                        <c:if test="${Returnpolicy.returnswithinoption==within.id}">
                            <option value="${within.id}" selected="selected">${within.name}</option>
                        </c:if>
                        <c:if test="${Returnpolicy.returnswithinoption!=within.id}">
                            <option value="${within.id}">${within.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>退款方式:</td>
            <td>
                <select name="RefundOption">
                    <c:forEach items="${refundList}" var="pay">
                        <c:if test="${Returnpolicy.refundoption==pay.id}">
                            <option value="${pay.id}" selected="selected">${pay.name}</option>
                        </c:if>
                        <c:if test="${Returnpolicy.refundoption!=pay.id}">
                            <option value="${pay.id}">${pay.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>退货运费由谁承担:</td>
            <td>
                <select name="ShippingCostPaidByOption">
                    <c:forEach items="${costPaidList}" var="pay">
                        <c:if test="${Returnpolicy.shippingcostpaidbyoption==pay.id}">
                            <option value="${pay.id}" selected="selected">${pay.name}</option>
                        </c:if>
                        <c:if test="${Returnpolicy.shippingcostpaidbyoption!=pay.id}">
                            <option value="${pay.id}">${pay.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <td>付款说明:</td>
        <td>
            <textarea name="Returnpolicy_desc" cols="30" rows="5">${Returnpolicy.description}</textarea>
            <div><input type="submit" value="确定" /></div>
        </td>
        </tr>
    </table>
</form>
</body>
</html>
