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
    <form class="form-horizontal">
        <fieldset>
            <div id="legend" class="">
                <legend class="">添加角色</legend>
            </div>
            <div class="control-group">

                <!-- Text input-->
                <label class="control-label" for="input01">角色名</label>
                <div class="controls">
                    <input type="text" placeholder="" class="input-xlarge">
                    <p class="help-block"></p>
                </div>
            </div>

            <div class="control-group">

                <!-- Text input-->
                <label class="control-label" for="input01">描述</label>
                <div class="controls">
                    <input type="text" placeholder="" class="input-xlarge">
                    <p class="help-block"></p>
                </div>
            </div>



            <div class="control-group">

                <!-- Select Multiple -->
                <label class="control-label">可选权限列表</label>
                <div class="controls" id="canSelectPer">
                    <select class="input-xlarge" multiple="multiple">
                        <option>Enter</option>
                        <option>Your</option>
                        <option>Options</option>
                        <option>Here!</option>
                    </select>
                </div>
            </div>



            <div class="control-group">
                <label class="control-label">已选权限</label>

                <!-- Multiple Checkboxes -->
                <div class="controls">
                    <!-- Inline Checkboxes -->
                    <label class="checkbox inline">
                        <input type="checkbox" value="1">
                        1
                    </label>
                    <label class="checkbox inline">
                        <input type="checkbox" value="2">
                        2
                    </label>
                    <label class="checkbox inline">
                        <input type="checkbox" value="3">
                        3
                    </label>
                </div>

            </div>



            <div class="control-group">
                <label class="control-label"></label>

                <!-- Button -->
                <div class="controls">
                    <button class="btn btn-info">确定</button>
                </div>
            </div>

        </fieldset>
    </form>
</div>
</body>
</html>
