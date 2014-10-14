<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>找回密码</title>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/css/compiled/layout.css"/> "/>
    <script type="text/javascript" src=<c:url value ="/js/jquery/jquery-1.9.0.min.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/base.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/util.js" /> ></script>

    <script type="text/javascript">
        var _token;
        $(document).ready(function(){
            $(".admin_user_reg ul").css({"background-size":"100% 100%","height":"540px"})
            $("#sureSu").hide();
        });

        function getSafeCode(){
            var ld=$("#loginUserID").val();
            if(ld==null || ld==''){
                alert("请输入帐号！")
                return;
            }
            var url="/xsddWeb/systemuser/getSafeCodelogin.do";
            var data=$("#regForm").serialize();
            $().invoke(
                    url,
                    data,
                    [function(m,r){
                        alert(r);
                        $("#sureSu").show();
                    },
                        function(m,r){
                            alert(r);
                        }]
            );
        };

        function changePassWord(){

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
        <h1>找回密码</h1>
        <form id="regForm" method="post">
            <li style="height: 15px;margin-top: 5px">帐号</li>

            <li>
                <input name="loginUserID" type="text" class="admin_user_input" id="loginUserID" value=""
                        />
            </li>

            <li style="height: 15px;margin-top: 5px">验证码</li>
            <li>
                <input name="safeCode" type="text" class="admin_user_input" id="safeCode" value=""
                        />
            </li>

            <li style="height: 15px;margin-top: 5px">新密码</li>
            <li>
                <input name="pwd1" type="text" class="admin_user_input" id="pwd1" value=""
                        />
            </li>

            <li style="height: 15px;margin-top: 5px">确认新密码</li>
            <li>
                <input name="pwd2" type="text" class="admin_user_input" id="pwd2" value=""
                        />
            </li>

            <li style="margin-top: 5px">
                <span>验证码将会发送至注册邮箱，请将验证码填入</span>
            </li>

        </form>

        <li>
            <input onclick="document.location.href='login.jsp'" type="button" name="button" id="button" value="返回" class="admin_3">
            <input type="button" onclick="getSafeCode()" name="button" id="button1" value="获取验证码" class="admin_4" />
            <input type="button" onclick="doReg()" name="button" id="sureSu" value="确定" class="admin_1" style="margin-left: 18px" />
        </li>
    </ul>


</div>
</body>

</html>
