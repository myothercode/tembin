<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/10
  Time: 10:09
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
        function keymove(objs){
            var data = $('#form').serialize();
            var urll =path+ "/keymove/saveItemToLocation.do";
            $(objs).attr("disabled",true);
            $().invoke(
                    urll,
                    data,
                    [function (m, r) {
                        alert(r);
                        $(objs).attr("disabled",false);
                    },
                        function (m, r) {
                            alert(r);
                            $(objs).attr("disabled",false);
                        }]
            );
        }
    </script>
</head>
<body>
<div style="width: 400px;">
    <form class="form-horizontal" id="form">
        <fieldset>
            <div id="legend" class="">
                <legend class="">一键搬家</legend>
            </div>
            <div class="control-group">
                <!-- Text input-->
                <label class="control-label" for="input01">站点</label>
                <div class="controls">
                    <select name="site" class="input-xlarge">
                        <c:forEach items="${siteList}" var="sites">
                            <option value="${sites.id}">${sites.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <!-- Text input-->
                <label class="control-label" for="input01">ebay账户</label>
                <div class="controls">
                    <c:forEach items="${ebayList}" var="ebay">
                        <em style="color:#48a5f3"><input type="checkbox" name="ebayAccounts" value="${ebay.id}" shortName="${ebay.ebayNameCode}">${ebay.ebayNameCode}</em>
                    </c:forEach>
                </div>
            </div>

        </fieldset>
    </form>

    <div  class="control-group" style="text-align: center;">
        <div class="controls">
            <button class="btn btn-success" onclick="keymove(this)">确定</button>
        </div>
    </div>
</div>
</body>
</html>
