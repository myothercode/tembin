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
            var url=path+"/priceTracking/ajax/priceTrackingQueryList1.do?";
            $("#priceTrackingListTable").initTable({
                url:url,
                columnData:[
                 /*   {title:"",name:"ch",width:"2%",align:"top",format:makeOption5},*/
                    {title:"SKU",name:"sku",width:"5%",align:"center"},
                    {title:"竞争对手数量",name:"competitorsTotal",width:"20%",align:"center"},
                    {title:"规则",name:"increaseOrDecrease",width:"20%",align:"center",format:makeOption2},
                    {title:"现在价格",name:"newprice",width:"5%",align:"left"},
                    {title:"操作&nbsp;&nbsp;&nbsp;",name:"option1",width:"2%",align:"center",format:makeOption3}
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
            var price="价格";
            if(json.ruleType=="priceLowerType"){
                price+="低于竞争对手,";
            }else{
                price+="高于竞争对手,";
            }
            if(json.increaseOrDecrease=="-"){
                price+="降价";
            }else{
                price+="提价";
            }
            price+=json.inputValue+json.sign;
            return price;
        }
        function makeOption3(json){
            var hs="";
            hs+="<li style=\"height:25px;\" onclick=viewPricingRecord("+json.id+"); doaction=\"readed\" >调价记录</li>";
            hs+="<li style=\"height:25px;\" onclick=deleteAutoPricing("+json.id+"); doaction=\"look\" >删除</li>";
            var pp={"liString":hs};
            return getULSelect(pp);
        }
        function viewPricingRecord(id){
            var url=path+"/priceTracking/viewPricingRecord.do?autoPricingId="+id;
            priceTracking=openMyDialog({title: '查看调价记录',
                content: 'url:'+url,
                icon: 'succeed',
                width:1000,
                height:600,
                lock:true
            });
        }
        function deleteAutoPricing(id){
            var url=path+"/priceTracking/ajax/deleteAutoPricing.do?id="+id;
            $().invoke(url,null,
                    [function(m,r){
                        alert(r);
                        refreshTable();
                        Base.token;
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function lianjieEbay(itemid){
            window.open(serviceItemUrl+itemid+"");
        }
        function query(){
            var qeuryContent=$("#qeuryContent").val();
            $("#priceTrackingListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0,"qeuryContent":qeuryContent});
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
            priceTracking=openMyDialog({title: '指定物品码',
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
    <option selected="selected" value="title">SKU</option>
</select>
</span>
</span>
    <div class="vsearch">
        <input id="qeuryContent" name="conteny" type="text" class="key_1"><input onclick="query();" name="newbut" type="button" class="key_2"></div>
</div>
    <div class="newds">
        <div class="newsj_left" style="position: absolute;top: 95px;right: 100px;z-index: 10000;">
                <img src="<c:url value ="/img/adds.png" />" onclick="autoSetPrice();">
            <%--<span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="checkbox" onclick="Allchecked(this);"></span>--%>
           <%-- <span class="newusa_ici_del" onclick="searchCompetitors();">搜索竞争对手</span>
            <span class="newusa_ici_del" onclick="autoSetPrice();;">自动调价</span>--%>
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
