<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2014/9/22
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/smallFormImport.jsp" %>
<html>
<head>
    <title>添加成员</title>
    <script type="text/javascript" src=<c:url value ="/js/systemabout/user/pop/addSubUser.js" /> ></script>
    <script type="text/javascript">
        var userID="${userID}";
    </script>
</head>
<body>
<div style="width: 400px">
<form class="form-horizontal" id="addSunUserForm">
    <%--<input type="hidden" name="userID" value="${userID}">--%>
    <fieldset>
        <div id="legend" class="">
            <legend class="" id="titleleg">添加成员</legend>
        </div>
        <div class="control-group">
            <!-- Text input-->
            <label class="control-label" for="input01">姓名</label>
            <div class="controls">
                <input type="text" placeholder="" class="input-xlarge " name="name">
                <p class="help-block"></p>
            </div>
        </div>

        <div class="control-group">
            <!-- Text input-->
            <label class="control-label" for="input01">登录名</label>
            <div class="controls">
                <input type="text" placeholder="" class="input-xlarge validate[required,custom[onlyLetterNumber]]" name="loginID">
                <p class="help-block"></p>
            </div>
        </div>

        <div class="control-group">
        <label class="control-label">角色</label>
        <!-- Multiple Checkboxes -->
        <div class="controls" id="roleList">

        </div>
    </div>

        <div class="control-group">
            <label class="control-label">ebay账户</label>
            <!-- Multiple Checkboxes -->
            <div class="controls" id="ebayList">

            </div>
        </div>

        <div class="control-group">
            <!-- Text input-->
            <label class="control-label" for="input01">邮箱</label>
            <div class="controls">
                <input type="text" placeholder="" class="input-xlarge validate[custom[email]]" name="email">
                <p class="help-block"></p>
            </div>
        </div>

        <div class="control-group">
            <!-- Text input-->
            <label class="control-label" for="input01">电话</label>
            <div class="controls">
                <input type="text" placeholder="" class="input-xlarge" name="phone">
                <p class="help-block"></p>
            </div>
        </div>

        <div class="control-group">
            <!-- Text input-->
            <label class="control-label" for="input01">地址</label>
            <div class="controls">
                <input type="text" placeholder="" class="input-xlarge" name="address">
                <p class="help-block"></p>
            </div>
        </div>


    </fieldset>
</form>

        <!-- Button -->
        <div style="text-align:center;">
            <button class="btn btn-primary" onclick="submitf()">确定</button>
        </div>

</div>
</body>
</html>
