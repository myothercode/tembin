<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2014/9/25
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>

<html>
<head>
    <title>帐号绑定</title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script type="text/javascript" src=<c:url value ="/js/systemabout/bindEbayAccount/ebayAccountManager.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/systemabout/bindEbayAccount/paypalAccountManager.js" /> ></script>
</head>
<body>
<div >
<div class="new_all">
    <div class="here">当前位置：首页 > 系统设置 > <b>帐号绑定</b></div>
    <div class="a_bal"></div>
    <div class="new">

        <div class="new_tab_ls">
            <dt id=menu1 class=new_tab_1 onclick="setTab('menu',1,2)">eBay帐号</dt>
            <dt id=menu2 class=new_tab_2 onclick="setTab('menu',2,2)">Paypal帐号</dt>
        </div>
        <div class=Contentbox>
            <div>
                <div id=con_menu_1 class=hover>
                    <div class="new_usa"><div class="tbbay" style="margin-top:20px;"><a data-toggle="modal" onclick="openBindEbayWindow()" href="javascript:void(0)" class="">添加ebay帐号</a></div>
                        <%--<div class="tbbay" style="margin-top:20px;"><a data-toggle="modal" href="#myModal" class="">E邮宝授权</a></div>--%>
                    </div>
                    <div id="ebayManager"></div>
                </div>



                <div style="DISPLAY: none" id=con_menu_2>
                    <div class="new_usa">
                        <div class="tbbay" style="margin-top:20px;">
                            <a onclick="opAddPaypalPage()" data-toggle="modal" href="javascript:void(0)" class="">添加paypal帐号</a></div>
                    </div>
                    <div id="paypalManager"></div>
                </div>
                </form>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>
