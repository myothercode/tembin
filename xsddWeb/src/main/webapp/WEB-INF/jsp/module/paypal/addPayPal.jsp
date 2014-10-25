<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>--%>
<%@include file= "/WEB-INF/jsp/smallFormImport.jsp" %>
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
    </script>
</head>
<c:set value="${paypal}" var="paypal" />
<body>
<div style="width: 400px;">
<form class="form-horizontal"  id="payPalForm">
    <fieldset>
        <div id="legend" class="">
            <legend class="">支付方式</legend>
        </div>
        <div class="control-group">
            <!-- Text input-->
            <label class="control-label" for="input01">名称</label>
            <div class="controls">
                <input type="text" name="name" id="name" value="${paypal.payName}"  placeholder="" class="input-xlarge validate[required]">
                <p class="help-block"></p>
                <input type="hidden" name="id" id="id" value="${paypal.id}">
            </div>
        </div>
        <div class="control-group">
            <!-- Select Basic -->
            <label class="control-label">站点</label>
            <div class="controls">
                <select name="site"  class="input-xlarge validate[required]">
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
        <div class="control-group">
            <!-- Select Basic -->
            <label class="control-label">paypal账号</label>
            <div class="controls">
                <select name="paypal"  class="input-xlarge validate[required]">
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
        <div class="control-group">
            <!-- Textarea -->
            <label class="control-label">付款说明</label>
            <div class="controls">
                <div class="textarea">
                    <textarea type="" class="" name="paypal_desc" cols="30" rows="5">${paypal.paymentinstructions}</textarea>
                </div>
            </div>
        </div>
    </fieldset>
</form>
    <div class="control-group" style="text-align: center;">
        <div class="controls">
            <button class="btn btn-success" onclick="submitCommit()">确定</button>
        </div>
    </div>
</div>
</body>
</html>
