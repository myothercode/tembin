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
    <link rel="stylesheet" type="text/css" href="<c:url value ="/css/compiled/icons.css"/>"/>
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
                    <input onchange="pressK(this)" id="paypalAccount" name="paypalAccount" type="text" placeholder="" class="input-xlarge">
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
            <button onclick="submitTheForm()" class="net_put">确定</button>
        </div>
    </div>
    <ul>
    请使用以下步骤授权 PayPal<br>
    登录 www.PayPal.com<br>
    在【我的 PayPal】下点击【用户信息】<br>
    点击【我的销售工具】<br>
    点击【API访问】的【更新】<br>
    在【选项1】里点击【授予 API 许可】或者【添加或编辑 API 许可】<br>
    在【第三方许可用户名】中输入 payment_api1.tembin.com 并点击【查找】<br>
    在可用权限下选择<br>
        <li>&nbsp;【发放特定交易的退款。(如果不想使用退款功能可不选)】</li>
        <li>&nbsp;【获取您的PayPal账户余额。(必选)】</li>
        <li>&nbsp;【获取有关单笔交易的信息】</li>
        <li>&nbsp;【在您的交易中搜索符合特定条件的物品并显示结果】</li>
        <li>&nbsp;点击【新增】</li>
    授权后您还需要在 PushAuction 的【PayPal 账户】中验证该 PayPal 帐户。
    </ul>
</div>

</body>
</html>
