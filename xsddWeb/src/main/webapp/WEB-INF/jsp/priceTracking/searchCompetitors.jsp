<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/12/31
  Time: 10:59
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
        function query(){
            var qeuryContent=$("#qeuryContent").val();
            if(qeuryContent!=""){
                var url=path+"/priceTracking/ajax/priceTrackingApi.do?qeuryContent="+qeuryContent;
                $("#searchCompetitorsListTable").initTable({
                    url:url,
                    columnData:[
                        /*   {title:"",name:"ch",width:"2%",align:"top",format:makeOption5},*/
                        {title:"",name:"ch",width:"1%",align:"top",format:makeOption1},
                        {title:"图片",name:"picture",width:"5%",align:"left",format:makeOption5},
                        {title:"物品号",name:"itemid",width:"5%",align:"left",format:makeOption4},
                        {title:"标题",name:"title",width:"50%",align:"left"},
                        {title:"卖家",name:"sellerusername",width:"5%",align:"left"},
                        {title:"价格",name:"currentprice",width:"5%",align:"left",format:makeOption2},
                        {title:"运输费",name:"shippingServiceCost",width:"5%",align:"left",format:makeOption3},
                    ],
                    selectDataNow:false,
                    isrowClick:false,
                    showIndex:false,
                    sysParm: {"jsonBean.pageNum": 1, "jsonBean.pageCount": 100}
                });
                refreshTable();
            }
        }
        function refreshTable(){
            $("#searchCompetitorsListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        function makeOption1(json){
            var htm="<div><input type='hidden'id='itemid' value='"+json.itemid+"'>"+
                    "<input type='hidden'id='title' value='"+json.title+"'>"+
                    "<input type='hidden'id='sellerusername' value='"+json.sellerusername+"'>"+
                    "<input type='hidden'id='currentprice' value='"+json.currentprice+"'>"+
                    "<input type='hidden'id='currencyid' value='"+json.currencyid+"'>"+
                    "<input type='hidden'id='bidcount' value='"+json.bidcount+"'>"+
                    "<input type='hidden'id='starttime' value='"+json.starttime+"'>"+
                    "<input type='hidden'id='endtime' value='"+json.endtime+"'>"+
                    "<input type='hidden'id='shippingServiceCost' value='"+json.shippingservicecost+"'>"+
                    "<input type='hidden'id='shippingCurrencyId' value='"+json.shippingcurrencyid+"'>"+
                    "<input type='hidden'id='pictureUrl' value='"+json.pictureurl+"'>"+
                    "<input type='checkbox' value1='"+json.itemid+"' id='checkbox' name='checkbox'/></div>";
            return htm;
        }
        function makeOption2(json){
            return json.currentprice+json.currencyid;
        }
        function makeOption3(json){
            if(json.shippingservicecost==null||json.shippingservicecost==""){
                return "";
            }
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
        function submitCommit(){
            var inputs=$("input[type=checkbox]:checked");
            if(inputs.length>0){
                for(var i=0;i<inputs.length;i++){
                    var div=$(inputs[i]).parent();
                    var itemid=$(div).find("input[id=itemid]").val();
                    var sellerusername=$(div).find("input[id=sellerusername]").val();
                    var currentprice=$(div).find("input[id=currentprice]").val();
                    var currencyid=$(div).find("input[id=currencyid]").val();
                    var title=$(div).find("input[id=title]").val();
                    var bidcount=$(div).find("input[id=bidcount]").val();
                    var starttime=$(div).find("input[id=starttime]").val();
                    var endtime=$(div).find("input[id=endtime]").val();
                    var qeuryContent=$("#qeuryContent").val();
                    var shippingServiceCost=$(div).find("input[id=shippingServiceCost]").val();
                    var shippingCurrencyId=$(div).find("input[id=shippingCurrencyId]").val();
                    var pictureurl=$(div).find("input[id=pictureUrl]").val();
                    var url=path+"/priceTracking/ajax/savepriceTracking.do?itemid="+itemid+"&sellerusername="+sellerusername+"&currentprice="+currentprice+"&currencyid="+currencyid+"&title="+title+"&queryTitle="+qeuryContent+"&bidcount="+bidcount+"&starttime="+starttime+"&endtime="+endtime+"&shippingServiceCost="+shippingServiceCost+"&shippingCurrencyId="+shippingCurrencyId+"&pictureurl="+pictureurl;
                    $().invoke(url,null,
                            [function(m,r){
                                /*if(r["itemid"]==$(inputs[(inputs.length-1)]).attr("value1")){*/
                                    W.refreshTable();
                                    W.priceTracking.close();
                               /* }*/
                                Base.token;
                            },
                                function(m,r){
                                    alert(r);
                                    Base.token();
                                }]
                    );
                }

            }else{
                alert("请选择至少一个选项进行保存");
            }
        }
    </script>
</head>
<body>
<div class="Contentbox">
    <!--综合开始 -->
    <br/><br/><br/>
    <div class="new_usa">

        <div class="newsearch">
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

        </div>
    </div>
    <!--综合结束 -->
    <div style="width: 100%;float: left;height: 5px"></div>
    <div id="searchCompetitorsListTable"></div>
    <div class="page_newlist">

    </div>

    <!-- userall -->
    <div class="modal fade" id="userall" tabindex="-1" role="dialog" aria-labelledby="userallLabel" aria-hidden="true">
        <div class="modal-dialog" style="width:1200px;">
            <div class="modal-content">
                <div class="modal-body">

                </div></div></div></div></div>
<div class="modal-footer" style="text-align: right">
<button type="button" class="net_put" onclick="submitCommit();">保存</button>
</div>
</body>
</html>
