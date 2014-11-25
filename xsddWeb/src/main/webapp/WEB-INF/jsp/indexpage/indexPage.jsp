<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2014/11/5
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<html>
<head>
    <title>主页</title>
    <!-- bootstrap -->
    <link href="<c:url value ="/css/bootstrap/bootstrap.css"/>" rel="stylesheet"/>
    <link href="<c:url value ="/css/bootstrap/bootstrap-overrides.css"/>" type="text/css" rel="stylesheet"/>
    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="<c:url value ="/css/compiled/layout.css"/> "/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/css/compiled/elements.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/css/compiled/icons.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/css/basecss/base.css" />"/>
    <!-- libraries -->
    <link href="<c:url value ="/css/lib/font-awesome.css"/>" type="text/css" rel="stylesheet" />
    <!-- this page specific styles -->
    <link rel="stylesheet" href="<c:url value ="/css/compiled/gallery.css"/>" type="text/css" media="screen" />

    <script type="text/javascript" src=<c:url value ="/js/jquery/jquery-1.9.0.min.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/jquery/jquery-migrate-1.2.1.min.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/jquery/jquery.cookie.js" /> ></script>
    <script src="<c:url value ="/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value ="/js/theme.js"/>"></script>
    <script src="<c:url value ="/js/indexpagejs/feedBackReport.js"/>"></script>
    <script src="<c:url value ="/js/indexpagejs/itemReport.js"/>"></script>
    <script type="text/javascript" src=<c:url value ="/js/base.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/util.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/jquery-blockui/jquery.blockUI.min.js" /> ></script>
    <script src="<c:url value ="/js/indexpagejs/jquery-ui-1.10.2.custom.min.js"/>"></script>
    <%--<script src="<c:url value ="/js/indexpagejs/jquery.knob.js"/>"></script>
    <script src="<c:url value ="/js/indexpagejs/jquery.flot.js"/>"></script>
    <script src="<c:url value ="/js/indexpagejs/jquery.flot.stack.js"/>"></script>
    <script src="<c:url value ="/js/indexpagejs/jquery.flot.resize.js"/>"></script>--%>
    <script src="<c:url value ="/js/indexpagejs/highcharts.js"/>"></script>
    <script src="<c:url value ="/js/indexpagejs/exporting.js"/>"></script>
    <script type="text/javascript" src=<c:url value ="/js/table/jquery.table.js" /> ></script>
    <script src="<c:url value ="/js/indexpagejs/indexMain.js"/>"></script>

    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>


    <script type="text/javascript">
        function selectTime(index,date){
            var as1=$("span[name=time]");
            for(var i=0;i<as1.length;i++){
                if(i==index){
                    $(as1[i]).attr("class","newusa_ici");
                    $("#time").val(date);
                }else{
                    $(as1[i]).attr("class","newusa_ici_1");
                }
            }
            var amount=$("#amount").val();
            getCharData(doKnobs,_getOrderCountData,{"time":date,"amount":amount});//单量走势
        }
        function selectday(index,date){
            var as1=$("span[name=day]");
            for(var i=0;i<as1.length;i++){
                if(i==index){
                    $(as1[i]).attr("class","newusa_ici");
                }else{
                    $(as1[i]).attr("class","newusa_ici_1");
                }
            }
            getCharData(doContainer,_getTrenchData,{"day":date});//渠道分布
        }
        function selectAmount(index,date){
            var as1=$("span[name=amount]");
            for(var i=0;i<as1.length;i++){
                if(i==index){
                    $(as1[i]).attr("class","newusa_ici");
                    $("#amount").val(date);
                }else{
                    $(as1[i]).attr("class","newusa_ici_1");

                }
            }
            var time=$("#time").val();
            getCharData(doKnobs,_getOrderCountData,{"time":time,"amount":date});//单量走势
        }
    </script>
</head>
<body>
<div>
<div class="new_all">
<div class="here"><%--当前店铺： EBAY<img src="img/home_logo_user.png" width="160" height="34">--%></div>
<div class="a_bal"></div>
<div class=Contentbox>
<div class="new_index" style="padding-left: 5px">
    <a href="<c:url value ="/userCases/userCasesList.do"/>">
        <li onmouseover="$(this).css('background','#EE6')" onmouseout="$(this).css('background','#EE6E69')"
            style="background:#EE6E69;margin-right:2%;">
        <span>CASE<%--订单代办事项--%></span>
        未处理的( ${caseMessages} )<%--分配失败 ( 3 )--%>
    </li></a>
    <a href="<c:url value ="/message/MessageGetmymessageList.do"/>">
    <li onmouseover="$(this).css('background','#EE6')" onmouseout="$(this).css('background','#48BCFF')"
        style="background:#48BCFF;margin-right:2%;">
        <span>EBAY消息<%--订单代办事项--%></span>
        未处理的( ${ebayMessages} )<%--分配失败 ( 3 )--%>
    </li></a>
    <li onmouseover="$(this).css('background','#EE6')" onmouseout="$(this).css('background','#6ABF0E')"
        style="background:#6ABF0E">
        <span>订单代办事项</span>
        分配失败 ( 3 )
    </li>
</div>
<div class="a_bal"></div>
<div class="new_index_2">
    <table style="width: 100%">
        <tr>
            <td width="49%" style="vertical-align: top;">
                <ul <%--class="pbd"--%> style="width: 100%">
                <h1><img src="img/ebay_index.png" width="40" height="17"> 账户</h1>
                <div style="width: 100%" id="ebay_indexdiv"></div>
                </ul>
            </td>
            <td width="2%"></td>
            <td width="49%" style="vertical-align: top;">
                <ul style="width: 100%">
                    <h1>Paypal </h1>
                    <div id="indexPayPal"></div>
                </ul>
            </td>
        </tr>
        <tr>
            <td style="vertical-align: top;">
                <ul style="width: 100%">
                    <h1>刊登</h1>
                    <div id="itemReportTable"></div>

                </ul>
            </td>
            <td></td>
            <td style="vertical-align: top;">
                <ul style="width: 100%">
                <h1>信用评价</h1>
                <div id="feedBackReportTable"></div>
            </ul>
            </td>
        </tr>
    </table>






<%--<ul &lt;%&ndash;class="pbd"&ndash;%&gt; style="margin-right:2%">
    <h1>刊登</h1>
    <div id="itemReportTable"></div>

</ul>--%>
    <%--<ul >
        <h1>信用评价</h1>
        <div id="feedBackReportTable"></div>
    </ul>--%>
<h1>单量走势</h1>
<div class="a_bal"></div>
    <input type="hidden" id="time"/>
    <input type="hidden" id="amount"/>
<li><span class="newusa_iv">选择账户</span><a href="javascript:void(0)"><span class="newusa_ici" name="amount" onclick="selectAmount(0,null);">全部</span></a>
    <c:forEach items="${ebays}" var="ebay" begin="0" varStatus="status">
        <a href="javascript:void(0)"><span class="newusa_ici_1" name="amount" onclick="selectAmount(${status.index+1},'${ebay.ebayName}');">${ebay.ebayNameCode}<%--TO(eBay)--%></span></a>
    </c:forEach>
</li>
<li><span class="newusa_iv">选择时间</span><a href="javascript:void(0)"><span class="newusa_ici" name="time" onclick="selectTime(0,'15')">15天内</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" name="time" onclick="selectTime(1,'30')">30天内</span></a><a  href="javascript:#" ><span name="time" onclick="selectTime(2,'1')" class="newusa_ici_1">1年内</span></a></li>
<div class="ico_list">
    <div id="statsChart" style="width: 100%; height: 100%; "></div>
</div>
<h1>渠道分布</h1>
<div class="a_bal"></div>
<li><span class="newusa_iv">选择时间</span><a href="javascript:void(0)"><span class="newusa_ici" name="day" onclick="selectday(0,'15')">15天内</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" name="day" onclick="selectday(1,'30')">30天内</span></a><a href="javascript:void(0)"><span class="newusa_ici_1" name="day" onclick="selectday(2,'1')">1年内</span></a></li>

<div class="ico_bin">
    <div id="container" style="min-width: 200px; height: 600px; max-width: 600px; margin: 0 auto"></div>
</div>
</div>
</div>
</div>
</div>
</body>
</html>
