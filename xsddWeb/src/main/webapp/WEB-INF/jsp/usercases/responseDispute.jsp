<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/2
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <style>
        .table-a table{border:1px solid rgba(0, 0, 0, 0.23)
        }
    </style>
</head>
<body>
<div><b>Choose one of the following:</b></div>
<div class="table-a">
    <table border="0" cellpadding="0" cellspacing="0" style="width: 560px;" >
        <tr><td><a href="javascript:viod()"><b>Verify tracking information</b>(Buyer's preference)</a></td></tr>
        <tr id="verify"><td>
            Please verify that this tracking information is correct and click Submit to continue.If it is incorrect,please re-enter your tracking number.<br/>
            What is your tracking number?<br/>
            <input type="text" name="number"/><br/>
            Which carrier did you use?<br/>
            <input type="text" name="carrier"/><br/>
            [<a href="javascript:viod()">View your tracking information</a>]
        </td></tr>
        <tr><td><a href="javascript:viod()"><b>I shipped the item without tracking information</b></a></td></tr>
        <tr><td><a href="javascript:viod()"><b>Issue a full refund</b></a></td></tr>
        <tr><td><a href="javascript:viod()"><b>Send a message</b></a></td></tr>
    </table>
</div>

</body>
</html>
