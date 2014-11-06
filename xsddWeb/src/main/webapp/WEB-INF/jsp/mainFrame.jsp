<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2014/9/15
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>

<html>
<head>
    <%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
    <script type="text/javascript" src=<c:url value ="/js/commonPage/mainframe/mainFramePage.js" /> ></script>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/toolTip/css/toolTip.css"/> "/>
    <script type="text/javascript" src=<c:url value ="/js/toolTip/js/toolTip.js" /> ></script>
    <%--<link rel="stylesheet" type="text/css" href="<c:url value ="/js/toolTip/css/jQuery.toolTip.css"/> "/>
    <script type="text/javascript" src=<c:url value ="/js/toolTip/js/jQuery.toolTip.js" /> ></script>--%>


    <title></title>
    <script type="text/javascript">
        $(document).ready(function(){
            getMenu();
            getSystemMessage({"jsonBean.pageNum":1,"jsonBean.pageCount":1000,"readed":0,"strV1":"num"});
            getMessagesByTime();
            setWandH();
            setIndexUrl();
        });
        /**设置主页地址*/
        function setIndexUrl(){
            $("#contentMain").attr("src",path+"/indexInit.do");
        }
        var mainHei_="";
        var mainWidth_="";
        function setWandH(){//设置宽度
            var wi=getCurrPageWH();
            mainWidth_=(parseInt(wi.eWidth)-275);
            mainHei_=(parseInt(wi.eHeight)-70);
            /*alert(wi.eHeight)
            alert(document.documentElement.scrollHeight)*/
            $("#contentMaindiv").css("width",mainWidth_+"px");
            $("#contentMaindiv").css("height",mainHei_+"px");
            //$("#contentMaindiv").css("height","94%");
        }
        function cleanContent(obj){
            var cont= $(obj).val();
            if(cont=="SKU/内容") {
                $(obj).val("");
            }
        }
        function addContent(obj){
            var cont= $(obj).val();
            if(cont==""){
                $(obj).val("SKU/内容");
            }
        }
        function queryOrder(obj){
            var cont= $(obj).val();
            if(cont!=""&&event.keyCode==13){
                $("#queryForm").submit();

            }
        }
    </script>
    <style type="text/css">
        .select_ul {margin:0 5px 0 0;padding:0;list-style-type:none;}
        .select_ul ul li{list-style-type:none;float:left;height:24px;}
        .select_box{float:left;border-bottom:solid 1px #000;color:#444;position:relative;cursor:pointer;width:84px;background: url(../images/select_box_off.gif) no-repeat right center;}
        .selet_open{ display:inline-block;position:absolute;right:0;top:0;width:12px;height:24px;}
        .select_txt{display:inline-block;padding-left:2px;width:50px;line-height:24px;height:22px; cursor:pointer;overflow:hidden; color:#0474f1;}
        .option{width:100px;border:solid 1px #444;position:absolute;top:24px;left:-1px;z-index:2;overflow:hidden;display:none;}
        .option a{display:block;height:26px;line-height:26px;text-align:left;padding:0 10px;width:100%;background:#fff;}
        .option a:hover{background:#ededed;}

        dl { margin:0 auto; padding:0px; float:left;}
        dl dt,dd { margin:0 auto; padding:0px; float:left; height:21px; line-height:21px;}
        dl dt { font-weight:bold;}
        dl dt font { font-weight:normal;}
        dl dt span { float: right; color: #ff0000;}
        dl .noneBold { font-weight:normal;}
    </style>

</head>
<body style=overflow-y:auto;overflow-x:hidden;>
<form id="queryForm" action="/xsddWeb/order/queryOrdersList.do" target="contentMain">
</form>
<!-- navbar -->
<div id="navbar">
<header   class="navbar navbar-inverse" style="position: absolute;width: 100%;" role="banner">
    <div class="navbar-header">
        <button class="navbar-toggle" type="button" data-toggle="collapse" id="menu-toggler">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#"><img src="<c:url value ="/img/logo.png"/> "></a>
    </div>
    <ul class="nav navbar-nav pull-right hidden-xs">
        <li class="hidden-xs hidden-sm">
            <input class="search" type="text" value="SKU/内容" style="color: #808080" onkeydown="queryOrder(this);" onfocus="cleanContent(this);" onblur="addContent(this);"/>
            <%--<div class="vsearch">
                <input id="content6" style="background:#000000; padding: 2px 6px;top: 9px;border-radius: 2px;position: relative;height: 27px; " name="" type="text" class="key_1"><input style="background:#000000;padding: 2px 6px;top: 9px;border-radius: 2px;position: relative;height: 27px; " name="newbut" type="button" class="key_2"></div>
           --%><%-- <input type="text" style="padding: 2px 6px;top: 9px;border-radius: 2px;position: relative;height: 27px; ">--%>
        </li>
        <li   class="notification-dropdown hidden-xs hidden-sm" >
            <a title="ebay消息"  href="javascript:void(0)" id="ebayMessages"  class="trigger " >
                <i title="ebay消息" class="icon-warning-sign tooltips"
                   tooltip="ebay消息"
                   tooltip-type="primary" tooltip-position="bottom"></i>
                <span id="ebayMessagesCount" class="count"></span>
            </a>
            <div class="pop-dialog">
                <div class="pointer right">
                    <div class="arrow"></div>
                    <div class="arrow_border"></div>
                </div>
                <div class="body">
                    <a href="javascript:void(0)" class="close-icon"><i class="icon-remove-sign"></i></a>
                    <div class="notifications" id="notifications">
                        <h3 id="ebaymessageNotice">You have x new notifications</h3>

                        <a href="#" class="item">
                            <i class="icon-download-alt"></i> no message
                            <span class="time"><i class="icon-time"></i> 1 day.</span>
                        </a>
                        <div class="footer">
                            <a href="#" class="logout">View all notifications</a>
                        </div>
                    </div>
                </div>
            </div>
        </li>
        <li title="系统消息" class="notification-dropdown hidden-xs hidden-sm">
            <a href="javascript:void(0)" id="systemMessages" class="trigger">
                <i class="icon-envelope tooltips"
                   tooltip="系统消息"
                   tooltip-type="primary" tooltip-position="bottom"></i>
                <span id="systemMessageCount" class="count"></span>
            </a>
            <div class="pop-dialog">
                <div class="pointer right">
                    <div class="arrow"></div>
                    <div class="arrow_border"></div>
                </div>
                <div class="body">
                    <a href="#" class="close-icon"><i class="icon-remove-sign"></i></a>
                    <div class="messages" id="messages">
                        <a href="javascript:void(0)" class="item">
                            <img src="/xsddWeb/img/contact-img.png" class="display" />
                            <div class="name">no message</div>
                            <div class="msg">
                                no message
                            </div>
                            <span class="time"><i class="icon-time"></i> 13 min.</span>
                        </a>
                    </div>
                </div>
            </div>
        </li>

        <li title="系统配置" class="settings hidden-xs hidden-sm tooltips" tooltip="系统配置"
            tooltip-type="primary" tooltip-position="bottom">
            <a target="contentMain" href="/xsddWeb/systemuser/userConfPage.do" role="button">
                <i class="icon-cog"></i>
            </a>
        </li>
        <li title="注销登录" class="settings hidden-xs hidden-sm tooltips" tooltip="注销登录"
            tooltip-type="primary" tooltip-position="bottom">
            <a onclick="logout()" href="javascript:void(0)" role="button">
                <i class="icon-share-alt"></i>
            </a>
        </li>
    </ul>
</header>
</div>
<!-- end navbar -->

<!-- sidebar -->
<div id="sidebar-nav">
    <ul id="dashboard-menu">

    </ul>
</div>


<div id="contentMaindiv" class="content" style="position: absolute;top: 58px;overflow-x: hidden">
    <iframe width="100%" height="100%" frameborder="0px" id="contentMain" name="contentMain">

    </iframe>
</div>

</body>

</html>
