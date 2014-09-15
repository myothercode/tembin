<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/10
  Time: 10:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
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
                            $(objs).attr("disabled",false);
                        }]
            );
        }
    </script>
</head>
<body>
<form id="form">
<li>
    <dt>站点</dt>
    <div class="ui-select dt5">
        <select name="site">
            <c:forEach items="${siteList}" var="sites">
                <option value="${sites.id}">${sites.name}</option>
            </c:forEach>
        </select>
    </div>
</li>
<li>
    <dt>ebay账户</dt>
    <c:forEach items="${ebayList}" var="ebay">
        <em style="color:#48a5f3"><input type="checkbox" name="ebayAccounts" value="${ebay.id}" shortName="${ebay.ebayNameCode}">${ebay.ebayNameCode}</em>
    </c:forEach>
</li>
<li>
    <dt>刊登开始时间</dt>
    <input name="startFrom"  class="validate[required]" id="startFrom" value="<fmt:formatDate value="${dis.disStarttime}" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
</li>
    <li>
        <dt>刊登结束时间</dt>
        <input name="startTo"  class="validate[required]" id="startTo" value="<fmt:formatDate value="${dis.disStarttime}" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
    </li>
    <input type="button" value="确定" onclick="keymove(this)">
</form>
</body>
</html>
