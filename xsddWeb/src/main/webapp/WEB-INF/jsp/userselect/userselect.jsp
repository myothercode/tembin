<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/10
  Time: 10:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>--%>
<%@include file= "/WEB-INF/jsp/smallFormImport.jsp" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/css/compiled/icons.css"/>"/>
    <script>
        var val=0;
        function keymove(objs){
            if(val<100){
                alert("还有任务未执行完，请等会儿搬家！");
                return ;
            }
            var data = $('#form').serialize();
            var urll =path+ "/keymove/saveItemToLocation.do";
            $(objs).attr("disabled",true);
            $().invoke(
                    urll,
                    data,
                    [function (m, r) {
                        alert(r);
                        $(objs).attr("disabled",false);
                    },
                        function (m, r) {
                            alert(r);
                            $(objs).attr("disabled",false);
                        }]
            );
        }

        $(document).ready(function() {
               //初始化进度条，如果已经初始化则会跳过
            updateProgressbarValue();   //调用函数
        });
        function updateProgressbarValue(){
            var urll =path+ "/keymove/keyProgress.do";
            $().invoke(
                    urll,
                    {},
                    [function (m, r) {
                        $("#ebyac").text(r.ebayAccount);
                        var rs = r.limp;
                        if(rs==null||rs==""){
                            $("#mainTable").hide();
                        }else{
                            $("#mainTable").show();
                        }
                        var countNumber = 0;
                        var count = 0;
                        var errorcount=0
                        $("#start").text(formatSeconds(r.startDate));
                        for (var i = 0; i < rs.length; i++) {
                            if (rs[i].taskFlag == "1") {
                                count = rs[i].tjNumber;
                            }else if (rs[i].taskFlag == "2") {
                                errorcount = rs[i].tjNumber;
                            }
                            countNumber += rs[i].tjNumber;
                        }

                        $("#end").text(formatSeconds(parseInt(r.startDate)/(count+errorcount)*(countNumber-count-errorcount)));
                        $("#compcount").text(count+"");
                        $("#errorcount").text(errorcount);
                        $("#waitcount").text(countNumber-count-errorcount);
                        val = (count+errorcount) / countNumber * 100;
                        $("#progress").progressbar({value: val,
                            change:function(){
                                $(".progress-label").text(parseFloat($("#progress").progressbar("value")).toFixed(2)+"%");
                            },
                            complete:function(){
                                $(".progress-label").text("100%");
                            }});
                        if (val < 100) {
                            setTimeout(updateProgressbarValue, 2000);    //使用setTimeout函数延迟调用updateProgressbarValue函数，延迟时间为500毫秒
                        }
                    },
                        function (m, r) {
                            alert(r);
                        }]
            );

        }


        function formatSeconds(value) {
            var theTime = parseInt(value);// 秒
            var theTime1 = 0;// 分
            var theTime2 = 0;// 小时
            if(theTime > 60) {
                theTime1 = parseInt(theTime/60);
                theTime = parseInt(theTime%60);
                if(theTime1 > 60) {
                    theTime2 = parseInt(theTime1/60);
                    theTime1 = parseInt(theTime1%60);
                }
            }
            var result = ""+parseInt(theTime)+"秒";
            if(theTime1 > 0) {
                result = ""+parseInt(theTime1)+"分"+result;
            }
            if(theTime2 > 0) {
                result = ""+parseInt(theTime2)+"时"+result;
            }
            return result;
        }

    </script>
    <style>
        .progress-label{
            float:left;
            margin-left:40%;
            margin-top:3px;
        }
    </style>
</head>
<body>
<div style="width: 100%">
    <form class="form-horizontal" id="form" style="margin-bottom: 2px;">
        <fieldset>
            <div id="legend" class="">
                <legend class="">一键搬家</legend>
            </div>
            <div class="control-group">
                <!-- Text input-->
                <label class="control-label" for="input01">站点</label>
                <div class="controls">
                    <select name="site" class="input-xlarge">
                        <c:forEach items="${siteList}" var="sites">
                            <option value="${sites.id}">${sites.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="control-group"  style="margin-bottom: 5px;">
                <!-- Text input-->
                <label class="control-label" for="input01">ebay账户</label>
                <div class="controls">
                    <c:forEach items="${ebayList}" var="ebay">
                        <em style="color:#48a5f3"><input type="checkbox" name="ebayAccounts" value="${ebay.id}" shortName="${ebay.ebayNameCode}">${ebay.ebayNameCode}</em>
                    </c:forEach>
                </div>
            </div>
        </fieldset>
    </form>

<%--    <div class="modal-footer">
        <button class="new_put" onclick="keymove(this)">确定</button>
    </div>--%>

    <div class="modal-footer" style="padding:0px;background-color:#FFFFFF;text-align: left;padding-left: 160px;border-top: 0px ;padding-bottom:6px;border-bottom: 1px solid #ddd;">
        <button type="button" class="net_put" onclick="keymove(this)">开始搬家</button>
    </div>


    <div id="mainTable">
    <table width="90%">
        <tr style="height: 34px;">
            <td style="text-align: right;">ebay账号：</td>
            <td>
                <span id="ebyac">

                </span>
                <span style="padding-left: 200px;">
                    已用时间：
                </span>
                <span id="start">

                </span>
                <span style="padding-left: 100px;">
                    剩余时间：
                </span>
                <span id="end">

                </span>
            </td>
        </tr>
        <tr>
            <td width="200px;" style="text-align: right;">搬家进度：</td>
            <td>
                <div id="progress" style="">
                    <div class="progress-label"></div>
                </div>
            </td>
        </tr>
        <tr>
            <td></td>
            <td style="text-align: center">
                (<span id="compcount"></span>个商品已搬家，<span id="errorcount"></span>个商品搬家失败，<span id="waitcount"></span>个商品等待搬家)
            </td>
        </tr>
    </table>
    </div>
</div>
</body>
</html>
