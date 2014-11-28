<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/29
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<%--<%@include file= "/WEB-INF/jsp/smallFormImport.jsp" %>--%>
<html>
<head>
    <title></title>
</head>

<script type="text/javascript">
    var api = frameElement.api, W = api.opener;
    function submitForm(){
        if(!$("#returnPolicyForm").validationEngine("validate")){
            return;
        }
        var url=path+"/ajax/saveReturnpolicy.do";
        var data=$("#returnPolicyForm").serialize();
        $().invoke(url,data,
                [function(m,r){
                    alert(r);
                    W.returnPolicy.close();
                    W.refreshTablereturnpolicy();
                },
                    function(m,r){
                        alert(r);
                        Base.token();
                    }]
        );
    }
    function winClose(){
        W.returnPolicy.close();
    }
    $(document).ready(function () {
        $("#returnPolicyForm").validationEngine();
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
        $("select").selectBoxIt({});
    });
</script>
<style type="text/css">
    body {
        background-color: #ffffff;
    }
</style>
<link href=
      <c:url value="/css/basecss/conter.css"/> type="text/css" rel="stylesheet"/>
<c:set value="${Returnpolicy}" var="Returnpolicy" />
<body>
<%--<div class="modal-header">
    <div class="newtitle">退款政策</div>
</div>--%>
<div class="modal-body">
<form class="form-horizontal" role="form" id="returnPolicyForm">
<table width="90%" border="0" style="margin-left:40px;">
    <tr>
        <td width="27%" height="28" align="right">名称：</td>
        <td width="73%" height="28"><div class="newselect">
            <input type="text" placeholder="" class="form-control validate[required]" name="name" id="name" value="${Returnpolicy.name}">
            <input type="hidden" name="id" id="id" value="${Returnpolicy.id}">
        </div></td>
    </tr>
    <tr>
        <td height="28" align="right">站点：</td>
        <td height="28">
            <select name="site" style="width: 300px;">
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
        <td height="28" align="right">退货政策：</td>
        <td height="28">
            <select name="returnsacceptedoption" style="width:300px;">
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
        <td height="28" align="right">退货天数：</td>
        <td height="28">
            <select name="returnswithinoption" style="width: 300px;">
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
        <td height="28" align="right">退款方式：</td>
        <td height="28">
            <select name="refundoption" style="width: 300px;">
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
        <td height="28" align="right">退货费用由谁承担：</td>
        <td height="28">
            <select name="shippingcostpaidbyoption" style="width: 300px;">
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

    <tr>
        <td width="27%" height="28" align="right">付款说明：</td>
        <td width="73%" height="28"><div class="col-lg-10">
            <textarea class="form-control"  name="description" cols="" rows="2" style="width:350px;">${Returnpolicy.description}</textarea>
        </div></td>
    </tr>
    <tr >
        <td height="28" align="right"></td>
        <td height="28" style=" padding-top:22px;">
            <button type="button" class="net_put" onclick="submitForm();">确定</button>
            <button type="button" class="net_put_1" data-dismiss="modal" onclick="winClose()">关闭</button>
        </td>
    </tr>
</table>
</form>
</div>
</body>
</html>
