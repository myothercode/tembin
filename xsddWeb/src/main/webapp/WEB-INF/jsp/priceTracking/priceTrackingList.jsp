<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/12/11
  Time: 14:54
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
        var priceTracking;
        $(document).ready(function(){
            var url=path+"/priceTracking/ajax/priceTrackingQueryList.do?";
            $("#priceTrackingListTable").initTable({
                url:url,
                columnData:[
                 /*   {title:"",name:"ch",width:"2%",align:"top",format:makeOption5},*/
                    {title:"",name:"ch",width:"1%",align:"top",format:makeOption1},
                    {title:"图片",name:"picture",width:"5%",align:"left",format:makeOption5},
                    {title:"物品号",name:"itemid",width:"5%",align:"left",format:makeOption4},
                    {title:"标题",name:"title",width:"50%",align:"left"},
                    {title:"卖家",name:"sellerusername",width:"5%",align:"left"},
                    {title:"价格",name:"currentprice",width:"5%",align:"left",format:makeOption2},
                    {title:"运输费",name:"shippingServiceCost",width:"5%",align:"left",format:makeOption3}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
         });
        function refreshTable(){
            $("#priceTrackingListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        function makeOption1(json){
            var htm="<input type='checkbox' value='"+json.id+"' id='checkbox' name='checkbox'/>";
            return htm;
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
        function lianjieEbay(itemid){
            window.open(serviceItemUrl+itemid+"");
        }
        function searchCompetitors(){
            var url=path+"/priceTracking/searchCompetitors.do?";
            priceTracking=openMyDialog({title: '竞争对手',
                content: 'url:'+url,
                icon: 'succeed',
                width:1000,
                height:700,
                lock:true
            });
        }

        function setPrice(){
            var url=path+"/priceTracking/setPrice.do?";
            priceTracking=openMyDialog({title: '自定义价格',
                content: 'url:'+url,
                icon: 'succeed',
                width:1000,
                height:600,
                lock:true
            });
        }
        function autoSetPrice(){
            var url=path+"/priceTracking/autoSetPrice.do?";
            priceTracking=openMyDialog({title: '自动调价',
                content: 'url:'+url,
                icon: 'succeed',
                width:600,
                height:400,
                lock:true,
                zIndex:1000
            });
        }
        function assignItem(){
            var url=path+"/priceTracking/assignItem.do?";
            priceTracking=openMyDialog({title: '自动调价',
                content: 'url:'+url,
                icon: 'succeed',
                width:500,
                lock:true
            });
        }
    </script>
</head>
<body>
<div class="new_all">
    <div class="here">当前位置：首页 &gt; 销售管理 &gt; <b>价格跟踪</b></div>
    <div class="a_bal"></div>
<div class="new">
<%--<h2>eBay官方调整E邮宝接口，截至九月底将停止使用原E邮宝，需要您重新授权E邮宝</h2>--%>
<div class="Contentbox">
<!--综合开始 -->
<div class="new_tab_ls">
    <dt id="menu1" class="new_tab_1">价格跟踪</dt>
</div><br/><br/><br/>
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
        <div class="newsj_left" style="margin-left: 9px;">
            <span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="checkbox" onclick="Allchecked(this);"></span>
            <span class="newusa_ici_del" onclick="searchCompetitors();">搜索竞争对手</span>
            <span class="newusa_ici_del" onclick="autoSetPrice();;">自动调价</span>
        </div>
    </div>
</div>
<!--综合结束 -->
<div style="width: 100%;float: left;height: 5px"></div>
<div id="priceTrackingListTable"></div>
<div class="page_newlist">

</div>

<!-- userall -->
<div class="modal fade" id="userall" tabindex="-1" role="dialog" aria-labelledby="userallLabel" aria-hidden="true">
<div class="modal-dialog" style="width:1200px;">
<div class="modal-content">
<div class="modal-body">

</div></div></div></div></div></div>
  <%--  <div class="modal-footer" style="text-align: right">
        <button type="button" class="net_put_1" data-dismiss="modal" onclick="assignItem();">指定跟踪</button>
       &lt;%&ndash; <button type="button" class="net_put_1" data-dismiss="modal" onclick="setPrice();">自定调价</button>&ndash;%&gt;
        &lt;%&ndash;<button type="button" class="net_put_1" data-dismiss="modal" onclick="autoSetPrice();">自动调价</button>&ndash;%&gt;
         <button type="button" class="net_put" onclick="submitCommit();">保存</button>
    </div>--%>
</body>
</html>
