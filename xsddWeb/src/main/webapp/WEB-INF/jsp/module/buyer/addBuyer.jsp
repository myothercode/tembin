<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery-1.9.0.min.js" />"></script>
<html>
<head>
    <title></title>
    <script>
        function checkData(obj){
            if($(obj).parent().find("select").attr("disabled")=="disabled"){
                $(obj).parent().find("select").attr("disabled",false);
            }else{
                $(obj).parent().find("select").attr("disabled",true);
            }
        }

    </script>
</head>
<body>
    <form action="/xsddWeb/saveBuyer.do">
        <table>
            <tr>
                <td>名称:</td>
                <td><input type="text" name="buyName" id="buyName"></td>
            </tr>
            <tr>
                <td>站点:</td>
                <td>
                    <select name="site">
                        <c:forEach items="${siteList}" var="sites">
                            <option value="${sites.id}">${sites.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>买家要求:</td>
                <td>
                    <div><input type="radio" name="buyer_flag" value="1"/> 允许所有买家购买我的物品</div>
                    <div><input type="radio" name="buyer_flag" value="0"/> 不允许以下买家购买我的物品</div>
                    <div style="margin-left: 25px;">
                        <div><input type="checkbox" name="LinkedPayPalAccount" value="0"/>没有 PayPal 账户</div>
                        <div><input type="checkbox" name="ShipToRegistrationCountry" value="0"/>主要运送地址在我的运送范围之外</div>
                        <div><input type="checkbox" name="MaximumUnpaidItemStrikesInfo" onclick="checkData(this)"/>
                            曾收到<select name="Unpaid_count" disabled="disabled">
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                            </select>个弃标个案，在过去<select name="Unpaid_period" disabled="disabled">
                                <option value="30">30</option>
                                <option value="180">180</option>
                                <option value="360">360</option>
                            </select>天
                        </div>
                        <div><input type="checkbox" name="MaximumBuyerPolicyViolations"  onclick="checkData(this)"/>
                            曾收到<select name="Policy_count" disabled="disabled">
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                        </select>个违反政策检举，在过去<select name="Policy_period" disabled="disabled">
                            <option value="30">30</option>
                            <option value="180">180</option>
                        </select>天
                        </div>
                        <div><input type="checkbox" name="MinimumFeedbackScore_flag" onclick="checkData(this)"/>信用指标等于或低于：<select name="MinimumFeedbackScore" disabled="disabled">
                            <option value="-1">-1</option>
                            <option value="-2">-2</option>
                            <option value="-3">-3</option>
                        </select></div>
                        <div><input type="checkbox" name="MaximumItemCount_flag" onclick="checkData(this)"/>在过去10天内曾出价或购买我的物品，已达到我所设定的限制 <select name="MaximumItemCount" disabled="disabled">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10">10</option>
                            <option value="25">25</option>
                            <option value="50">50</option>
                            <option value="75">75</option>
                            <option value="100">100</option>
                        </select></div>
                        <div style="margin-left: 15px;"><input type="checkbox" name="FeedbackScore_falg" onclick="checkData(this)"/>这项限制只适用于买家信用指数等于或低于 <select name="FeedbackScore" disabled="disabled">
                            <option value="5">5</option>
                            <option value="4">4</option>
                            <option value="3">3</option>
                            <option value="2">2</option>
                            <option value="1">1</option>
                            <option value="0">0</option>
                        </select></div>
                    </div>
                    <div><input type="submit" value="确定"/></div>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>