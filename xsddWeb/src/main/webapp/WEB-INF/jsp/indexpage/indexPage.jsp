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
                }else{
                    $(as1[i]).attr("class","newusa_ici_1");
                }
            }
            getCharData(doKnobs,_getOrderCountData,{"time":date});//单量走势
        }
    </script>
</head>
<body>
<div>
<div class="new_all">
<div class="here">当前店铺： EBAY<%--<img src="img/home_logo_user.png" width="160" height="34">--%></div>
<div class="a_bal"></div>
<div class=Contentbox>
<div class="new_index">
    <a href="<c:url value ="/message/MessageGetmymessageList.do"/>"><li style="background:#EE6E69;margin-right:2%;">
        <span>CASE消息<%--订单代办事项--%></span>
        发送失败( ${caseMessages} )<%--分配失败 ( 3 )--%>
    </li></a>
    <li style="background:#48BCFF;margin-right:2%;">
        <span>EBAY消息<%--订单代办事项--%></span>
        发送失败( ${ebayMessages} )<%--分配失败 ( 3 )--%>
    </li>
    <li style="background:#6ABF0E">
        <span>订单代办事项</span>
        分配失败 ( 3 )
    </li>
</div>
<div class="a_bal"></div>
<div class="new_index_2">
    <ul class="pbd" style="margin-right:2%">
        <h1><img src="img/ebay_index.png" width="40" height="17"> 账户</h1>
        <div style="width: 100%" id="ebay_indexdiv"></div>
    </ul>


<ul>
    <h1>Paypal <div class="ui-select" style="width:110px; margin-top:-5px; line-height:14px;">
        <select>
            <option value="AK">所有帐户</option>
            <option value="AK">所有帐户</option>
        </select>
    </div></h1>
    <div id="indexPayPal"></div>

</ul>

<ul class="pbd" style="margin-right:2%">
    <h1>刊登</h1>
    <div id="itemReportTable"></div>
    <%--<table width="100%" border="0">
        <tr style="font-size:14px;">
            <td bgcolor="#F7F7F7"><strong>（拍卖/固价）</strong></td>
            <td width="12%" align="center" bgcolor="#F7F7F7"><strong>今天</strong></td>
            <td width="12%" align="center" bgcolor="#F7F7F7"><strong> 昨天</strong></td>
            <td width="12%" align="center" bgcolor="#F7F7F7"><strong> 本周</strong></td>
            <td width="12%" align="center" bgcolor="#F7F7F7"><strong> 上周               </strong></td>
            <td width="12%" align="center" bgcolor="#F7F7F7"><strong>本月               </strong></td>
            <td width="12%" align="center" bgcolor="#F7F7F7"><strong>上月</strong></td>
        </tr>
        <tr>
            <td valign="middle">新刊登物品</td>
            <td align="center">14</td>
            <td align="center">330</td>
            <td align="center">853</td>
            <td align="center">85</td>
            <td align="center">8533</td>
            <td align="center">8533</td>
        </tr>
        <tr>
            <td valign="middle">结束的刊登物品</td>
            <td align="center">14</td>
            <td align="center">330</td>
            <td align="center">853</td>
            <td align="center">85</td>
            <td align="center">8533</td>
            <td align="center">8533</td>
        </tr>
        <tr>
            <td valign="middle">结束并卖出的刊登物品</td>
            <td align="center">14</td>
            <td align="center">330</td>
            <td align="center">853</td>
            <td align="center">85</td>
            <td align="center">8533</td>
            <td align="center">8533</td>
        </tr>
        <tr>
            <td valign="middle">销售比</td>
            <td align="center">14</td>
            <td align="center">330</td>
            <td align="center">853</td>
            <td align="center">85</td>
            <td align="center">8533</td>
            <td align="center">8533</td>
        </tr>
        <tr>
            <td valign="middle">刊登费（新刊登）</td>
            <td align="center">14</td>
            <td align="center">330</td>
            <td align="center">853</td>
            <td align="center">85</td>
            <td align="center">8533</td>
            <td align="center">8533</td>
        </tr>
        <tr>
            <td valign="middle">刊登费（结束的刊登）</td>
            <td align="center">14</td>
            <td align="center">330</td>
            <td align="center">853</td>
            <td align="center">85</td>
            <td align="center">8533</td>
            <td align="center">8533</td>
        </tr>
    </table>--%>
</ul>
    <ul >
        <h1>信用评价</h1>
        <div id="feedBackReportTable"></div>
    </ul>
<h1>单量走势</h1>
<div class="a_bal"></div>
<li><span class="newusa_iv">选择账户</span><a href="#"><span class="newusa_ici">全部</span></a><a href="#"><span class="newusa_ici_1">TO(eBay)</span></a><a href="#"><span class="newusa_ici_1">TO(eBay)</span></a></li>
<li><span class="newusa_iv">选择时间</span><a href="javascript:#"><span class="newusa_ici" name="time" onclick="selectTime(0,'15')">15天内</span></a><a href="javascript:#"><span class="newusa_ici_1" name="time" onclick="selectTime(1,'30')">30天内</span></a><a  href="javascript:#" ><span name="time" onclick="selectTime(2,'1')" class="newusa_ici_1">1年内</span></a></li>
<div class="ico_list">
    <div id="statsChart" style="width: 100%; height: 100%; "></div>
</div>
<h1>渠道分布</h1>
<div class="a_bal"></div>
<li><span class="newusa_iv">选择时间</span><span class="newusa_ici">15天内</span><a href="#"><span class="newusa_ici_1">30天内</span></a><a href="#"><span class="newusa_ici_1">30天内</span></a></li>

<div class="ico_bin">
    <div id="container" style="min-width: 200px; height: 600px; max-width: 600px; margin: 0 auto"></div>
</div>
</div>
</div>
</div>
</div>
</body>
</html>
