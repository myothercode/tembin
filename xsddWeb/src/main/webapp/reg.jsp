<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册</title>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/css/compiled/layout.css"/> "/>
    <script type="text/javascript" src=<c:url value ="/js/jquery/jquery-1.9.0.min.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/base.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/util.js" /> ></script>

    <script type="text/javascript">
        var _token;
        $(document).ready(function(){
            $(".admin_user_reg ul").css({"background-size":"100% 100%","height":"610px"})
        });

        function doReg(){
            if(!vailForm()){
                return;
            }
            var url="/xsddWeb/doReglogin.do";
            var data=$("#regForm").serialize();
            $().invoke(
                url,
                data,
                    [function(m,r){
                        alert(r);
                        document.location.href="login.jsp";
                    },
                        function(m,r){
                            alert(r);
                        }]
            );
        }

        function vailForm(){
            var Regx = /^[A-Za-z0-9]*$/;
            if(!Regx.test($("#loginName").val())){
                alert("登录名只能是数字和字母!");
                $("#loginName").focus();
                return false;
            }
            var reyx= /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
            if(Regx.test($("#userEmail").val())){
                alert("邮箱格式不正确");
                $("#userEmail").focus();
                return false;
            }
            var p1=$("#pword1").val();
            var p2=$("#pword2").val();
            if(p1!=p2){
                alert("两次输入的密码不一致！");
                return false;
            }
            if(p1==null || p1 ==''){
                alert("请输入密码！")
                return false;
            }

            return true;
        }

    </script>
<style type="text/css">
body {
	background-color: #F2F3F5;
}
</style>


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
        <input name="telPhone" type="text" class="admin_user_input" id="phone" value="电话号码" onfocus="if(this.value=='电话号码'){this.value='';}"  onblur="if(this.value==''){this.value='电话号码';}" />
    </li>
    <li>
        <input name="orgName" type="text" class="admin_user_input" id="orgName" value="公司名" onfocus="if(this.value=='公司名'){this.value='';}"  onblur="if(this.value==''){this.value='公司名';}" />
    </li>
<li>
<input name="userEmail" type="text" class="admin_user_input" id="userEmail" value="请输入你的邮件地址" onfocus="if(this.value=='请输入你的邮件地址'){this.value='';}"  onblur="if(this.value==''){this.value='请输入你的邮件地址';}" />
</li>
<li>
<input title="输入密码" name="userPassword" type="password" class="admin_user_input" id="pword1" value=""  /></li>
<li >
<input title="确认密码" name="vps" type="password" class="admin_user_input" id="pword2" value=""  /></li>

    </form>

<li><input onclick="document.location.href='login.jsp'" type="button" name="button" id="button" value="返回" class="admin_3">
    <input type="button" onclick="doReg()" name="button" id="button1" value="注册" class="admin_4" />
</li>
</ul>


</div>
</body>

</html>
