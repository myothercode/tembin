<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/29
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>--%>
<%@include file= "/WEB-INF/jsp/smallFormImport.jsp" %>
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
                    W.refreshTable();
                },
                    function(m,r){
                        alert(r);
                        Base.token();
                    }]
        );
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
    });
</script>

<c:set value="${Returnpolicy}" var="Returnpolicy" />
<body>
<div style="width: 400px;">
    <form class="form-horizontal"  id="returnPolicyForm">
        <fieldset>
            <div id="legend" class="">
                <legend class="">退货政策</legend>
            </div>
            <div class="control-group">
                <label class="control-label" for="input01">名称</label>
                <div class="controls">
                    <input type="text" placeholder="" class="input-xlarge validate[required]" name="name" id="name" value="${Returnpolicy.name}">
                    <p class="help-block"></p>
                    <input type="hidden" name="id" id="id" value="${Returnpolicy.id}">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">站点</label>
                <div class="controls">
                    <select name="site"  class="input-xlarge">
                        <c:forEach items="${siteList}" var="sites">
                            <c:if test="${Returnpolicy.site==sites.id}">
                                <option value="${sites.id}" selected="selected">${sites.name}</option>
                            </c:if>
                            <c:if test="${Returnpolicy.site!=sites.id}">
                                <option value="${sites.id}">${sites.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <!-- Select Basic -->
                <label class="control-label">退货政策</label>
                <div class="controls">
                    <select name="returnsacceptedoption"  class="input-xlarge">
                        <c:forEach items="${acceptList}" var="accept">
                            <c:if test="${Returnpolicy.returnsacceptedoption==accept.id}">
                                <option value="${accept.id}" selected="selected">${accept.name}</option>
                            </c:if>
                            <c:if test="${Returnpolicy.returnsacceptedoption!=accept.id}">
                                <option value="${accept.id}">${accept.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <!-- Select Basic -->
                <label class="control-label">退货天数</label>
                <div class="controls">
                    <select name="returnswithinoption" class="input-xlarge">
                        <c:forEach items="${withinList}" var="within">
                            <c:if test="${Returnpolicy.returnswithinoption==within.id}">
                                <option value="${within.id}" selected="selected">${within.name}</option>
                            </c:if>
                            <c:if test="${Returnpolicy.returnswithinoption!=within.id}">
                                <option value="${within.id}">${within.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <!-- Select Basic -->
                <label class="control-label">退款方式</label>
                <div class="controls">
                    <select name="refundoption"  class="input-xlarge">
                        <c:forEach items="${refundList}" var="pay">
                            <c:if test="${Returnpolicy.refundoption==pay.id}">
                                <option value="${pay.id}" selected="selected">${pay.name}</option>
                            </c:if>
                            <c:if test="${Returnpolicy.refundoption!=pay.id}">
                                <option value="${pay.id}">${pay.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <!-- Select Basic -->
                <label class="control-label">退货运费由谁承担</label>
                <div class="controls">
                    <select name="shippingcostpaidbyoption"  class="input-xlarge">
                        <c:forEach items="${costPaidList}" var="pay">
                            <c:if test="${Returnpolicy.shippingcostpaidbyoption==pay.id}">
                                <option value="${pay.id}" selected="selected">${pay.name}</option>
                            </c:if>
                            <c:if test="${Returnpolicy.shippingcostpaidbyoption!=pay.id}">
                                <option value="${pay.id}">${pay.name}</option>
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
                        <textarea type="" class="" name="description" cols="30" rows="5">${Returnpolicy.description}</textarea>
                    </div>
                </div>
            </div>
        </fieldset>
    </form>
    <div class="control-group" style="text-align: center;">
        <div class="controls">
            <button class="btn btn-success" onclick="submitForm();">确定</button>
        </div>
    </div>
</div>

</body>
</html>
