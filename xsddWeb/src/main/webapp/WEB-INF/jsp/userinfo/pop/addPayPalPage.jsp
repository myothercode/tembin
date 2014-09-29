<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2014/9/29
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/smallFormImport.jsp" %>
<html>
<head>
    <title>添加paypal帐号</title>
    <script type="text/javascript" src=<c:url value ="/js/systemabout/bindEbayAccount/bindPayPal.js" /> ></script>
</head>
<body>
<div style="width: 400px">
    <form class="form-horizontal" id="paypalForm">
        <fieldset>
            <div id="legend" class="">
                <legend class="">添加paypal帐号</legend>
            </div>


            <div class="control-group">

                <!-- Text input-->
                <label class="control-label" for="input01">paypal帐号</label>
                <div class="controls">
                    <input id="paypalAccount" name="paypalAccount" type="text" placeholder="" class="input-xlarge">
                    <p class="help-block"></p>
                </div>
            </div>

            <div class="control-group">

                <!-- Text input-->
                <label class="control-label" for="input01">email</label>
                <div class="controls">
                    <input id="email" name="email" type="text" placeholder="" class="input-xlarge">
                    <p class="help-block"></p>
                </div>
            </div>

        </fieldset>
    </form>
    <div style="text-align: center" class="control-group">
        <label class="control-label"></label>

        <!-- Button -->
        <div class="controls">
            <button onclick="submitTheForm()" class="btn btn-info">确定</button>
        </div>
    </div>
</div>
</body>
</html>
