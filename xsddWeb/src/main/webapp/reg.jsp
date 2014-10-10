<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册</title>
    <script type="text/javascript">

        function doReg(){

        }

    </script>
<style type="text/css">
body {
	background-color: #F2F3F5;
}
</style>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/css/compiled/layout.css"/> "/>
</head>

<body>
<div class="admin_user"><ul></ul></div>
<div class="admin_user_reg">
<ul>
<h1>注册</h1>
    <form id="regForm" method="post">

    <li>
        <input name="userName" type="text" class="admin_user_input" id="name" value="姓名" onfocus="if(this.value=='姓名'){this.value='';}"  onblur="if(this.value==''){this.value='姓名';}" />
    </li>
    <li>
        <input name="userLoginId" type="text" class="admin_user_input" id="loginName" value="登录帐号" onfocus="if(this.value=='登录帐号'){this.value='';}"  onblur="if(this.value==''){this.value='登录帐号';}" />
    </li>
    <li>
        <input name="phone" type="text" class="admin_user_input" id="phone" value="电话号码" onfocus="if(this.value=='电话号码'){this.value='';}"  onblur="if(this.value==''){this.value='电话号码';}" />
    </li>
<li>
<input name="email" type="text" class="admin_user_input" id="search-keyword" value="请输入你的邮件地址" onfocus="if(this.value=='请输入你的邮件地址'){this.value='';}"  onblur="if(this.value==''){this.value='请输入你的邮件地址';}" />
</li>
<li>
<input title="输入密码" name="userPassword" type="password" class="admin_user_input" id="pword1" value=""  /></li>
<li >
<input title="确认密码" name="vps" type="password" class="admin_user_input" id="pword2" value=""  /></li>

    </form>

<li><input onclick="document.location.href='login.jsp'" type="button" name="button" id="button" value="返回" class="admin_3">
    <input type="submit" name="button" id="button1" value="注册" class="admin_4">
</li>
</ul>


</div>
</body>

</html>
