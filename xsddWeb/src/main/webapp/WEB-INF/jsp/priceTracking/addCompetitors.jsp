<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/12/12
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        $(document).ready(function(){
            var url=path+"/priceTracking/ajax/priceTrackingQueryList.do?";
            $("#priceTrackingQueryListTable").initTable({
                url:url,
                columnData:[
                    /*   {title:"",name:"ch",width:"2%",align:"top",format:makeOption5},*/
                    {title:"图片",name:"picture",width:"5%",align:"left",format:makeOption5,click:makeOption1},
                    {title:"物品号",name:"itemid",width:"5%",align:"left",format:makeOption4,click:makeOption1},
                    {title:"标题",name:"title",width:"50%",align:"left",click:makeOption1},
                    {title:"搜索标题",name:"querytitle",width:"10%",align:"left",click:makeOption1},
                    {title:"卖家",name:"sellerusername",width:"5%",align:"left",click:makeOption1},
                    {title:"价格",name:"currentprice",width:"5%",align:"left",format:makeOption2,click:makeOption1},
                    {title:"运输费",name:"shippingServiceCost",width:"5%",align:"left",format:makeOption3,click:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();

        });
        function makeOption1(json){
            var competitorTable=W.document.getElementById("competitorId");
            var tr1=$(competitorTable).find("tr[id=competitorTr"+json.id+"]");
            if(tr1.length>0){
                alert("该竞争对手已添加");
            }else{
                var tr="<tr name='competitorTr' id='competitorTr"+json.id+"'><td><input type='hidden' name='competitorHidden'value='"+json.id+"'></td><td valign='top'><label  class='control-label' style='line-height: 30px;' >竞争对手:</label></td>" +
                        "<td valign='top' align='left'><label style='line-height: 30px;' >"+json.sellerusername+"</label><a href='javascript:void(0);' style='color: #0000ff;line-height: 30px;' onclick='deleteTr(this)'>移除</a><br/><br/>" +
                        "<table><tr><td style='width: 300px;'>价格始终<select name='competitorPrice"+json.id+"'><option value='0'>竞争对手价格</option><option value='1'>低于竞争对手</option><option value='2'>高于竞争对手</option></select><select name='competitorPriceAdd"+json.id+"'><option >-</option><option  >+</option></select><input name='competitorPriceInput"+json.id+"'  style='width: 50px;margin-left: 5px;margin-right: 5px; '/><select name='competitorPriceSymbol"+json.id+"'><option>$</option><option>%</option></select></td></tr>" +
                        "<tr><td style='width: 300px;'>排名始终<select name='competitorRanking"+json.id+"'><option value='0'>竞争对手排名</option><option value='1'>低于竞争对手</option><option value='2'>高于竞争对手</option></select><select name='competitorRankingAdd"+json.id+"'><option >-</option><option >+</option></select><input name='competitorRankingInput"+json.id+"' style='width: 50px;margin-left: 5px;margin-right: 5px; '/><select name='competitorRankingSymbol"+json.id+"'><option>$</option><option>%</option></select></td></tr></table></td>" +
                        "</tr>";
                $(competitorTable).append(tr);
            }
            W.searchCompetitors.close();
        }

        function makeOption2(json){
            return json.currentprice+json.currencyid;
        }
        function makeOption3(json){
            return json.shippingservicecost+json.shippingcurrencyid;
        }
        function makeOption4(json){
            return "<a href='javascript:void(0);' onclick='lianjieEbay('"+json.itemid+"')'></a>";
        }
        function makeOption5(json){
            return "<img src='"+json.pictureurl+".jpg' style='width: 50px;height:50px;' />"
        }
        function query(){
            var qeuryContent=$("#qeuryContent").val();
            var url=path+"/priceTracking/ajax/priceTrackingQueryList.do?qeuryContent="+qeuryContent;
            $("#priceTrackingQueryListTable").initTable({
                url:url,
                columnData:[
                    /*   {title:"",name:"ch",width:"2%",align:"top",format:makeOption5},*/
                    {title:"物品号",name:"itemid",width:"5%",align:"left"},
                    {title:"标题",name:"title",width:"50%",align:"left"},
                    {title:"搜索标题",name:"querytitle",width:"10%",align:"left"},
                    {title:"最大数量",name:"bidcount",width:"5%",align:"left"},
                    {title:"卖家",name:"sellerusername",width:"5%",align:"left"},
                    {title:"价格",name:"currentprice",width:"5%",align:"left"},
                    {title:"单位",name:"currencyid",width:"5%",align:"left"},
                    {title:"开始时间",name:"starttime",width:"8%",align:"left"},
                    {title:"结束时间",name:"endtime",width:"8%",align:"left"}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        }
        function refreshTable(){
            $("#priceTrackingQueryListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
    </script>
</head>
<body>
<br/><br/>
<div class="new_usa">

    <div class="newsearch">
        <span class="newusa_i" style="width: 75px;">搜索内容：</span>
            <span id="sleBG">
<span id="sleHid">
<select id="selectName" name="type" class="select">
    <option selected="selected" value="title">标题</option>
</select>
</span>
</span>
        <div class="vsearch">
            <input id="qeuryContent" name="conteny" type="text" class="key_1"><input onclick="query();" name="newbut" type="button" class="key_2"></div>
    </div>
    <div class="newds">
        <div class="newsj_left">
        </div><div>
        <div id="newtipi">
        </div></div>
    </div>
</div>
<!--综合结束 -->
<div style="width: 100%;float: left;height: 5px"></div>
<div id="priceTrackingQueryListTable"></div>
<div class="page_newlist">

</div>

<!-- userall -->
<div class="modal fade" id="userall" tabindex="-1" role="dialog" aria-labelledby="userallLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:1200px;">
        <div class="modal-content">
            <div class="modal-body"></div>
        </div>
    </div>
</div>
</body>
</html>
