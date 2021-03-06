<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <link href=
          <c:url value="/css/basecss/conter.css"/> type="text/css" rel="stylesheet"/>
    <script>
        function checkData(obj){
            if($(obj).parent().find("select").attr("disabled")=="disabled"){
                $(obj).parent().find("select").attr("disabled",false);
            }else{
                $(obj).parent().find("select").attr("disabled",true);
            }
        }
        var api = frameElement.api, W = api.opener;
        function submitCommit(){
            var url=path+"/ajax/saveBuyer.do";
            var data=$("#buyerRequireForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.refreshTablebuyer();
                        W.buyerRequire.close();
                    },
                        function(m,r){
                            alert(r);
                        }]
            );
        }
        $(document).ready(function () {
            var site = '${buyerRequires.siteCode}';
            $("select[name='site']").find("option[value='" + site + "']").prop("selected", true);
            var buyerFlag='${buyerRequires.buyerFlag}';
            showFlag(buyerFlag);
            $("input[type='radio'][name='buyer_flag'][value='"+buyerFlag+"']").prop("checked",true);
            var linkedpaypalaccount = '${buyerRequires.linkedpaypalaccount}';
            if(linkedpaypalaccount!=null){
                $("input[type='checkbox'][name='LinkedPayPalAccount'][value='"+linkedpaypalaccount+"']").prop("checked",true);
                $("input[type='checkbox'][name='LinkedPayPalAccount'][value='"+linkedpaypalaccount+"']").attr("disabled",false);
            }
            var shiptoregistrationcountry =  '${buyerRequires.shiptoregistrationcountry}';
            if(shiptoregistrationcountry!=null){
                $("input[type='checkbox'][name='ShipToRegistrationCountry'][value='"+shiptoregistrationcountry+"']").prop("checked",true);
                $("input[type='checkbox'][name='ShipToRegistrationCountry'][value='"+shiptoregistrationcountry+"']").attr("disabled",false);
            }
            var policyCount = '${buyerRequires.policyCount}';
            var policyPeriod = '${buyerRequires.policyPeriod}';
            if(policyCount!=null||policyPeriod!=null){
                $("input[type='checkbox'][name='MaximumBuyerPolicyViolations']").prop("checked",true);
                $("input[type='checkbox'][name='MaximumBuyerPolicyViolations']").attr("disabled",false);
                $("select[name='Policy_count']").find("option[value='" + policyCount + "']").prop("selected", true);
                if(policyCount!=null){
                    $("select[name='Policy_count']").attr("disabled",false);
                }
                $("select[name='Policy_period']").find("option[value='" + policyPeriod + "']").prop("selected", true);
                if(policyPeriod!=null){
                    $("select[name='Policy_period']").attr("disabled",false);
                }
            }

            var unpaidCount = '${buyerRequires.policyCount}';
            var unpaidPeriod = '${buyerRequires.policyPeriod}';
            if(unpaidCount!=null||unpaidPeriod!=null){
                $("input[type='checkbox'][name='MaximumUnpaidItemStrikesInfo']").prop("checked",true);
                $("input[type='checkbox'][name='MaximumUnpaidItemStrikesInfo']").attr("disabled",false);
                $("select[name='Unpaid_count']").find("option[value='" + unpaidCount + "']").prop("selected", true);
                if(unpaidCount!=null){
                    $("select[name='Unpaid_count']").attr("disabled",false);
                }
                $("select[name='Unpaid_period']").find("option[value='" + unpaidPeriod + "']").prop("selected", true);
                if(unpaidPeriod!=null){
                    $("select[name='Unpaid_period']").attr("disabled",false);
                }
            }
            var minimumfeedbackscore = '${buyerRequires.minimumfeedbackscore}';
            if(minimumfeedbackscore!=null&&minimumfeedbackscore!=""){
                $("input[type='checkbox'][name='MinimumFeedbackScore_flag']").prop("checked",true);
                $("input[type='checkbox'][name='MinimumFeedbackScore_flag']").attr("disabled",false);
                $("select[name='MinimumFeedbackScore']").find("option[value='" + minimumfeedbackscore + "']").prop("selected", true);
                $("select[name='MinimumFeedbackScore']").attr("disabled",false);
            }
            var maximumitemcount = '${buyerRequires.maximumitemcount}';
            var feedbackscore =  '${buyerRequires.feedbackscore}';
            if(maximumitemcount!=null&&maximumitemcount!=""){
                $("select[name='MaximumItemCount']").find("option[value='" + maximumitemcount + "']").prop("selected", true);
                $("select[name='MaximumItemCount']").attr("disabled",false);

                $("input[type='checkbox'][name='MaximumItemCount_flag']").prop("checked",true);
                $("input[type='checkbox'][name='MaximumItemCount_flag']").attr("disabled",false);
            }

            if(feedbackscore!=null&&feedbackscore!=""){
                $("input[type='checkbox'][name='FeedbackScore_falg']").prop("checked",true);
                $("input[type='checkbox'][name='FeedbackScore_falg']").attr("disabled",false);

                $("select[name='FeedbackScore']").find("option[value='" + feedbackscore + "']").prop("selected", true);
                $("select[name='FeedbackScore']").attr("disabled",false);

                $("input[type='checkbox'][name='MaximumItemCount_flag']").prop("checked",true);
                $("input[type='checkbox'][name='MaximumItemCount_flag']").attr("disabled",false);
            }
            var type = '${type}';
            if(type=="01") {
                $("input[type='text'],[type='radio'],[type='checkbox']").each(function(i,d){
                    $(d).attr("disabled",true);
                });

                $("select").each(function(i,d){
                    $(d).attr("disabled",true);
                });
                $("button").hide();
            }
            $("select[name='site']").selectBoxIt({});
        });
        function showFlag(obj){
            if(obj=="1"){
                $("#buyerShow").hide();
            }else{
                $("#buyerShow").show();
            }
        }
        function winClose(){
            W.buyerRequire.close();
        }
    </script>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
</head>
<body>
<br>
<%--<div class="newtitle_1" style="margin: 15px;">买家要求</div>--%>
<div style="padding-left: 80px;">
    <form id="buyerRequireForm">
        <input type="hidden" name="id" value="${buyerRequires.id}"/>
        <table>
            <tr>
                <td align="right">名称:</td>
                <td>
                    <div class="newselect">
                        <input type="text" class="form-control" name="buyName" id="buyName" value="${buyerRequires.name}">
                    </div>
                </td>
            </tr>
            <tr>
                <td align="right">站点:</td>
                <td>
                    <select name="site" style="width: 300px;">
                        <c:forEach items="${siteList}" var="sites">
                            <option value="${sites.id}">${sites.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>买家要求:</td>
                <td>
                    <div>&nbsp;<input type="radio" name="buyer_flag" value="1" checked onclick="showFlag('1')"/> 允许所有买家购买我的物品</div>
                    <div>&nbsp;<input type="radio" name="buyer_flag" value="0" onclick="showFlag('0')"/> 不允许以下买家购买我的物品</div>
                    <div id="buyerShow" style="margin-left: 25px;display: none;">
                        <div><input type="checkbox" name="LinkedPayPalAccount" value="0"/>没有 PayPal 账户</div>
                        <div><input type="checkbox" name="ShipToRegistrationCountry" value="0"/>主要运送地址在我的运送范围之外</div>
                        <div><input type="checkbox" name="MaximumUnpaidItemStrikesInfo" onclick="checkData(this)"/>
                            曾收到 <div class="ui-select" style="width:4px;"><select name="Unpaid_count" disabled="disabled" style="width: 100px;">
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                            </select></div>个弃标个案，在过去<div class="ui-select" style="width:4px;"><select  style="width: 100px;" name="Unpaid_period" disabled="disabled">
                                <option value="30">30</option>
                                <option value="180">180</option>
                                <option value="360">360</option>
                            </select></div>天
                        </div>
                        <div><input type="checkbox" name="MaximumBuyerPolicyViolations"  onclick="checkData(this)"/>
                            曾收到<div class="ui-select" style="width:4px;"><select name="Policy_count" disabled="disabled" style="width: 100px;">
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                        </select></div>个违反政策检举，在过去<div class="ui-select" style="width:4px;"><select name="Policy_period" disabled="disabled" style="width: 100px;">
                            <option value="30">30</option>
                            <option value="180">180</option>
                        </select></div>天
                        </div>
                        <div><input type="checkbox" name="MinimumFeedbackScore_flag" onclick="checkData(this)"/>信用指标等于或低于：<div class="ui-select" style="width:4px;"><select  style="width: 100px;" name="MinimumFeedbackScore" disabled="disabled">
                            <option value="-1">-1</option>
                            <option value="-2">-2</option>
                            <option value="-3">-3</option>
                        </select></div></div>
                        <div><input type="checkbox" name="MaximumItemCount_flag" onclick="checkData(this)"/>在过去10天内曾出价或购买我的物品，已达到我所设定的限制 <div class="ui-select" style="width:4px;"><select  style="width: 100px;" name="MaximumItemCount" disabled="disabled">
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
                        </select></div></div>
                        <div style="margin-left: 15px;"><input type="checkbox" name="FeedbackScore_falg" onclick="checkData(this)"/>这项限制只适用于买家信用指数等于或低于 <div class="ui-select" style="width:4px;"><select  style="width: 100px;" name="FeedbackScore" disabled="disabled">
                            <option value="5">5</option>
                            <option value="4">4</option>
                            <option value="3">3</option>
                            <option value="2">2</option>
                            <option value="1">1</option>
                            <option value="0">0</option>
                        </select></div></div>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</div>
<div class="modal-footer" style="margin: 15px;">
    <button type="button" class="net_put" onclick="submitCommit()">确认</button>
    <button type="button" class="net_put_1" data-dismiss="modal" onclick="winClose()">关闭</button>
</div>
</body>
</html>
