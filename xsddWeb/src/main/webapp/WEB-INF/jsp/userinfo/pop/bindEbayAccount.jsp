<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/6
  Time: 9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/smallFormImport.jsp" %>
<html>
<head>
    <title>绑定账户</title>
    <script type="text/javascript">
        var tokenPageUrl="${tokenPageUrl}";
        var sessid;
        var tokenParm;

        $(document).ready(function(){
            getAllDevSelect();
        });
    </script>
    <script type="text/javascript" src=<c:url value ="/js/systemabout/bindEbayAccount/bindebay.js" /> ></script>
</head>
<body>

选择要绑定的开发帐号
<select id="devSelect">
    <option value="">请选择</option>
</select>

<button onclick="getBindParm()">账户授权</button>
帐号别名
<input type="text" id="bm" onblur="getShortName(this)" />
&nbsp;
别名缩写
<input type="text" id="code" />
<button onclick="fetchToken()">授权已完成?</button>

</body>


</html>
