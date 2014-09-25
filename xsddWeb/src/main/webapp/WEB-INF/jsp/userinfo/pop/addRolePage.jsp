<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2014/9/24
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/smallFormImport.jsp" %>
<html>
<head>
    <title>添加角色</title>
    <script type="text/javascript" src=<c:url value ="/js/systemabout/user/pop/addRolePage.js" /> ></script>
    <script type="text/javascript">
        var roleId="${roleId}";
    </script>
</head>
<body>
<div style="width: 400px">
    <form class="form-horizontal" id="roleForm">
        <fieldset>
            <div id="legend" class="">
                <legend class="">添加角色</legend>
            </div>
            <div class="control-group">

                <!-- Text input-->
                <label class="control-label" for="input01">角色名</label>
                <div class="controls">
                    <input id="roleName" name="role.roleName" type="text" placeholder="" class="input-xlarge">
                    <p class="help-block"></p>
                </div>
            </div>

            <div class="control-group">

                <!-- Text input-->
                <label class="control-label" for="input01">描述</label>
                <div class="controls">
                    <input id="roleDesc" name="role.roleDesc" type="text" placeholder="" class="input-xlarge">
                    <p class="help-block"></p>
                </div>
            </div>



            <div class="control-group">

                <!-- Select Multiple -->
                <label class="control-label">可选权限列表</label>
                <div class="controls" id="canSelectPer">

                </div>
            </div>



            <div class="control-group">
                <label class="control-label">已选权限</label>

                <!-- Multiple Checkboxes -->
                <div class="controls" id="alreadChecked">

                </div>

            </div>



            <div class="control-group">
                <label class="control-label"></label>

                <!-- Button -->

            </div>

        </fieldset>
    </form>
    <div class="controls" style="text-align: center">
        <button class="btn btn-info" onclick="submitRole()">确定</button>
    </div>
</div>
</body>
</html>
