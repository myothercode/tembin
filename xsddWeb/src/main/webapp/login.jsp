<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/css/compiled/layout.css"/> "/>
    <script type="text/javascript" src=<c:url value ="/js/jquery/jquery-1.9.0.min.js" /> ></script>
    <script type="text/javascript">


        function subLoginForm(){
            if(!ischrom()){
                alert("请使用Chrome浏览器！")
                return;
            }
            $("#loginf").submit();
        }

        //判断是否是googlr
        function ischrom(){
            var isChrome = window.chrome && (window.navigator.userAgent.indexOf("Chrome")>-1);
            return isChrome;
        }

        //跳转到注册页面
        function redirtRegPage(){
            if(!ischrom()){
                alert("请使用Chrome浏览器！")
                return;
            }
            document.location.href='reg.jsp';
        }
        //跳转到找回密码页面
        function findPassWd(){
            if(!ischrom()){
                alert("请使用Chrome浏览器！")
                return;
            }
            document.location.href='findPWD.jsp';
        }

        function changeImg(){
            $("#capImg").hide().attr('src', '/xsddWeb/captchaActionlogin.do?x=' + Math.floor(Math.random()*100) ).fadeIn();
        }

        var errMessage="<%=request.getSession().getAttribute("errMessage_")%>";
        var showCapImage="<%=request.getSession().getAttribute("showCapImage")%>";
        <%
            request.getSession().removeAttribute("errMessage_");
            request.getSession().removeAttribute("showCapImage");
        %>
        $(document).ready(function(){
            if(!ischrom()){
                alert("请使用Chrome浏览器！")
            }
            $(".admin_user_reg ul").css({"background-size":"100% 100%","height":"280px"})
            if(errMessage!=null && errMessage!='' && errMessage!='null'){
                alert(errMessage)
            }
            if(showCapImage!=null && showCapImage!='' && showCapImage != 'null'){
                $("#capli").show();
                changeImg();
                $(".admin_user_reg ul").css({"background-size":"100% 100%","height":"350px"})
            }

        });
    </script>
    <style type="text/css">
        body {
            background-color: #F2F3F5;
        }
    </style>
</head>
<body>
<form id="loginf" action="/xsddWeb/login.do" method="post">
<div class="admin_user"><ul></ul></div>
<div class="admin_user_reg">
    <ul>
        <h1>登录</h1>
        <li>
            <input name="loginId" type="text" class="admin_user_input" id="search-keyword1" value="test"
                   onfocus="if(this.value=='请输入你的帐号'){this.value='';}"  onblur="if(this.value=='')
                   {this.value='请输入你的帐号';}" />
        </li>
        <li>
            <input name="password" type="text" class="admin_user_input" id="search-keyword2" value="123456"
                   onfocus="if(this.value=='请输入密码'){this.value='';}"
                   onblur="if(this.value==''){this.value='请输入密码';}" /></li>

        <li id="capli" style="display: none">
            <div style="float: left"><input name="capcode" style="width: 100px;height: 43px;top: 0px" type="text"/></div>
            <div>&nbsp;&nbsp;验证码:<img id="capImg" onclick="changeImg(this)" src="" alt="点击切换"/> &nbsp;</div>


        </li>

        <li style="text-align: right;margin-top: 0px"><a onclick="findPassWd()" href="javascript:void(0)">忘记密码</a></li>
        <li style="margin-top: -5px">
            <input type="button" onclick="redirtRegPage()" name="button" id="button1" value="注册" class="admin_2">
            <button type="button" onclick="subLoginForm()" name="button" id="button"  class="admin_1">登录 </button>
        </li>
    </ul>
</div>
</form>

</body>
</html>
