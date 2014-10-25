<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2014/9/19
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title>用户账户配置</title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
</head>
<body>
<div class="new_all">
<div class="here">当前位置：首页 > 系统设置 > <b>系统账户</b></div>
<div class="a_bal"></div>
<div class="new">

<div class="new_tab_ls">
    <dt id=menu2 class=new_tab_2 onclick="setTab('menu',2,5)">修改密码</dt>
</div>
<div class=Contentbox>
<div>
<div id=con_menu_1 class=hover>

</div>

<div  id=con_menu_2>
    <form id="changePWDForm">
        <table width="100%" border="0" align="left" cellspacing="0" style="margin-top:20px;">
            <tr>
                <td width="10%" height="40" align="right">原始密码</td>
                <td width="82%" height="40"><input id="oldPWD" name="oldPWD" type="password" class="newps"></td>
            </tr>
            <tr>
                <td width="10%" height="40" align="right" >新密码</td>
                <td height="40"><input id="newPWD" name="newPWD" type="password" class="newps"></td>
            </tr>
            <tr>
                <td width="10%" height="40" align="right">确认密码</td>
                <td height="40"><input id="newPWD2" name="ps" type="password" class="newps"></td>
            </tr>
            <tr>
                <td height="40" align="right">&nbsp;</td>
                <td height="40"><input onclick="changePWDFun()" name="提交" type="button" class="newps_put" value="保 存"></td>
            </tr>
        </table></form>
</div>



<!--结束 -->
</div>
</div>
</div>
</div>
</body>

<script type="text/javascript">
    /**修改密码*/
    function changePWDFun(){
        var oldp=$('#oldPWD').val();
        var newp=$('#newPWD').val();
        var newp2=$('#newPWD2').val();
        if(oldp==null || newp==null || newp2==null){alert("原密码和新密码必须输入");return;}
        if(newp!=newp2){alert("两次输入的新密码不同！");return;}
        $().invoke(
                        path+"/systemuser/changePWD.do",
                {"oldPWD":oldp,"newPWD":newp},
                [function(m,r){
                    alert(r);
                    $("#oldPWD,#newPWD,#newPWD2").val('');
                    Base.token();
                },
                    function(m,r){
                        alert(r)
                        Base.token();
                    }]
        );
    }


    /**tab切换方法*/
    function setTab(name,cursel,n){
        for(i=1;i<=n;i++){
            var menu=document.getElementById(name+i);
            var con=document.getElementById("con_"+name+"_"+i);
            menu.className=i==cursel?"new_tab_1":"new_tab_2";
            con.style.display=i==cursel?"block":"none";
        }
    }

</script>


</html>
