<%--
  Created by IntelliJ IDEA.
  User: wula
  Date: 2014/7/27j
  Time: 18:09
  To change this template use File | Settings | File Templates.
  <html xmlns="http://www.w3.org/1999/xhtml">
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>

<script type="text/javascript" src=<c:url value ="/js/jquery/jquery-1.9.0.min.js" /> ></script>
<script typr="text/javascript" src=<c:url value ="/js/base.js" /> ></script>
<script typr="text/javascript" src=<c:url value ="/js/lhgdialog/lhgdialog.min.js" /> ></script>
<script typr="text/javascript" src=<c:url value ="/js/My97DatePicker/WdatePicker.js" /> ></script>


<%
    String rootPath = request.getContextPath();
%>
<script type="text/javascript">
    var path = window["path"] = '<%=rootPath%>';
</script>

