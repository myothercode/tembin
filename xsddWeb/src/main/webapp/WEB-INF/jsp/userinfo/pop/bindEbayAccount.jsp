<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/6
  Time: 9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/smallFormImport.jsp" %>
<html>
<head>
    <title>绑定账户</title>
    <script type="text/javascript">
        var tokenPageUrl="${tokenPageUrl}";
        var sessid;
        var tokenParm;
        var ebayId="${ebayId}";

    </script>
    <script type="text/javascript" src=<c:url value ="/js/systemabout/bindEbayAccount/bindebay.js" /> ></script>
</head>
<body>

<div style="width: 400px">

    <form class="form-horizontal">
        <fieldset>
            <div id="legend" class="">
                <legend id="pageTitle" class="">ebay账户绑定</legend>
            </div>


            <div class="control-group">

                <!-- Text input-->
                <label class="control-label" for="input01">ebay账户名</label>
                <div class="controls">
                    <input id="bm" value="${ebay.ebayName}" onblur="getShortName(this)" type="text" placeholder="" class="input-xlarge">
                    <p class="help-block"></p>
                </div>
            </div>

            <div class="control-group">
                <!-- Text input-->
                <label class="control-label" for="input01">简写代码</label>
                <div class="controls">
                    <input id="code" value="${ebay.ebayNameCode}" type="text" placeholder="" class="input-xlarge">
                    <p class="help-block"></p>
                </div>
            </div>

            <div class="control-group" style="display: none">

                <!-- Appended input-->
                <label class="control-label">绑定paypal帐号</label>
                <div class="controls">
                    <div class="input-append">
                        <input id="paypalAccount" value="${ebay.paypalName}" type="text" placeholder="" class="span2">
                        <input id="paypalAccountId" value="${ebay.paypalAccountId}" type="hidden" placeholder="" class="input-xlarge">
                        <span onclick="selectPaypalWindow()" class="add-on" style="cursor: pointer">选择</span>
                    </div>
                    <p class="help-block"></p>
                </div>

            </div>



        </fieldset>
    </form>

    <div id="action1" class="control-group" style="text-align: center;">
        <label class="control-label"></label>
        <!-- Button -->
        <div class="controls">
            <button onclick="getBindParm()" class="btn btn-info">账户授权</button>
        </div>
    </div>

    <div id="action2" class="control-group" style="text-align: center;display: none">
        <label class="control-label"></label>
        <!-- Button -->
        <div class="controls">
            <button class="btn btn-info" onclick="fetchToken()">确定授权完成</button>
        </div>
    </div>

    <div id="action3" class="control-group" style="text-align: center;display: none">
        <label class="control-label"></label>
        <!-- Button -->
        <div class="controls">
            <button class="btn btn-info" onclick="fetchToken('edit')">确定修改</button>
        </div>
    </div>

</div>



<%--选择要绑定的开发帐号
<select id="devSelect">
    <option value="">请选择</option>
</select>

<button onclick="getBindParm()">账户授权</button>
帐号别名
<input type="text" id="bm" onblur="getShortName(this)" />
&nbsp;
别名缩写
<input type="text" id="code" />
<button onclick="fetchToken()">授权已完成?</button>--%>

</body>


</html>
