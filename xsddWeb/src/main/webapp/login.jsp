<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>登录</title>
    <script type="text/javascript">
        <%
            String rootPath = request.getContextPath();
        %>
        //var path ='<%=rootPath%>';
        var errMessage="<%=request.getSession().getAttribute("errMessage_")%>";
        var showCapImage="<%=request.getSession().getAttribute("showCapImage")%>";
        <%
            request.getSession().removeAttribute("errMessage_");
            request.getSession().removeAttribute("showCapImage");
        %>
    </script>
    <script type="text/javascript" src=<c:url value ="/js/crypjs/core-min.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/crypjs/aes.js" /> ></script>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/css/compiled/layout.css"/> "/>
    <script type="text/javascript" src=<c:url value ="/js/jquery/jquery-1.9.0.min.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/jquery/jquery.cookie.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/loginPage/login.js" /> ></script>

    <%--<link rel="stylesheet" type="text/css" href="<c:url value ="/css/bootstrap2/bootstrap.min.css" />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/css/bootstrap2/bootstrap-responsive.min.css" />"/>
    <script type="text/javascript" src=<c:url value ="/css/bootstrap2/bootstrap.min.js" /> ></script>--%>

    <style type="text/css">
        body {
            background-color: #F2F3F5;
        }
    </style>
</head>
<body>
<form  id="loginf" action="/xsddWeb/login.do" method="post">
<div class="admin_user"><ul></ul></div>
<div class="admin_user_reg">
    <ul>
        <h1>登录</h1>
        <li style="margin-bottom:20px">
            <input id="loginId" name="loginId" type="text" class="admin_user_input aemail_1" id="search-keyword" value="请输入你的邮件地址" onfocus="if(this.value=='请输入你的邮件地址'){this.value='';}"  onblur="if(this.value==''){this.value='请输入你的邮件地址';}" />
        </li>
        <%--<li>
            <input id="loginId" name="loginId" type="text" class="admin_user_input" id="search-keyword1" value=""
                   style="padding:2px 2px 2px 40px;height: 36px;width: 286px;background-repeat: no-repeat;background-position: 0px center;background-image: url(/xsddWeb/img/admin_user_unameinput.png)"
                   &lt;%&ndash;onfocus="if(this.value=='请输入你的邮箱'){this.value='';}"  onblur="if(this.value=='')
                   {this.value='请输入你的邮箱';}"&ndash;%&gt; />
        </li>--%>
        <li style="margin-bottom:10px">
            <input name="password" type="password"  class="admin_user_input aemail_2" id="password"  /></li>
        <li>
        <%--<li>
            <input name="password" type="password" class="admin_user_input" id="password" value=""
                   style="padding:2px 2px 2px 40px;height: 36px;width: 286px;background-repeat: no-repeat;background-position: 0px center;background-image: url(/xsddWeb/img/admin_user_upassinput.png)"
                   &lt;%&ndash;onfocus="if(this.value=='请输入密码'){this.value='';}"&ndash;%&gt;
                   &lt;%&ndash;onblur="if(this.value==''){this.value='请输入密码';}"&ndash;%&gt; /></li>--%>

        <li id="capli" style="display: none">
            <div style="float: left"><input name="capcode" style="width: 100px;height: 43px;top: 0px" type="text"/></div>
            <div>&nbsp;&nbsp;验证码:<img id="capImg" onclick="changeImg(this)" src="" alt="点击切换"/> &nbsp;</div>


        </li>

        <li>
            <table width="325" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td><input onclick="jzcheck(this)"  type="checkbox" id="jzw"/><label for="jzw"  style="color:#526F91;margin-top:-22px; line-height:12px;">记住我</label></td>
                    <td><input onclick="subLoginForm()" type="button" name="button" id="button" value="登录" class="admin_but"style="float:right"></td>
                </tr>
            </table>
        </li>
        <li class="nbb"  style=" height:32px;"></li>
        <li style=" height:32px;">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="50%" align="center"><a href="javascript:findPassWd()">忘记密码？</a></td>
                    <td width="50%" align="center"><a href="javascript:redirtRegPage()">注册</a></td>
                </tr>
            </table>
            <a href="javascript:void(0)"></a> <a href="javascript:void(0)"></a></li>
        <%--<li style="text-align: right;margin-top: 0px;">
            <input onclick="jzcheck(this)" type="checkbox" id="jzw" /><label for="jzw">记住密码</label>&nbsp;
            <button style="margin-top: 10px;margin-right: 10px" type="button" onclick="subLoginForm()" name="button" id="button"  class="admin_1">登录 </button>
        </li>--%>
        <%--<li style="margin-top: -5px">
            <a style="float: left;margin-top: 20px;margin-left: 7px;cursor: pointer" onclick="findPassWd()" href="javascript:void(0)" >忘记密码</a>
            <a style="float: right;margin-top: 20px;margin-right: 8px;cursor: pointer" onclick="redirtRegPage()" href="javascript:void(0)">注册</a>
            &lt;%&ndash;<input type="button" onclick="redirtRegPage()" name="button" id="button1" value="注册" class="admin_2">&ndash;%&gt;

        </li>--%>
    </ul>
</div><div class="foot_new"></div>
</div>
</form>

</body>
</html>
