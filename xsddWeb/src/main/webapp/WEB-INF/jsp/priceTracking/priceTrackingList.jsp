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
        function query(){
            var qeuryContent=$("#qeuryContent").val();
            var url=path+"/priceTracking/ajax/priceTrackingApi.do?qeuryContent="+qeuryContent;
            $("#priceTrackingListTable").initTable({
                url:url,
                columnData:[
                 /*   {title:"",name:"ch",width:"2%",align:"top",format:makeOption5},*/
                    {title:"",name:"ch",width:"1%",align:"top",format:makeOption1},
                    {title:"物品号",name:"itemid",width:"5%",align:"left"},
                    {title:"标题",name:"title",width:"50%",align:"left"},
                    {title:"最大数量",name:"bidcount",width:"5%",align:"left"},
                    {title:"卖家",name:"sellerusername",width:"5%",align:"left"},
                    {title:"价格",name:"currentprice",width:"5%",align:"left"},
                    {title:"单位",name:"currencyid",width:"5%",align:"left"},
                    {title:"开始时间",name:"starttime",width:"8%",align:"left"},
                    {title:"结束时间",name:"endtime",width:"8%",align:"left"}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false,
                sysParm: {"jsonBean.pageNum": 1, "jsonBean.pageCount": 100}
            });
            refreshTable();
        }
        function refreshTable(){
            $("#priceTrackingListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
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
                    "<input type='checkbox' id='checkbox' name='checkbox'/></div>";
            return htm;
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
                    var url=path+"/priceTracking/ajax/savepriceTracking.do?itemid="+itemid+"&sellerusername="+sellerusername+"&currentprice="+currentprice+"&currencyid="+currencyid+"&title="+title+"&queryTitle="+qeuryContent+"&bidcount="+bidcount+"&starttime="+starttime+"&endtime="+endtime;
                    var data=$("#addRemarkForm").serialize();
                    $().invoke(url,null,
                            [function(m,r){
                                if(i==(inputs.length-1)){
                                    alert(r);
                                }
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
        var priceTracking;
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
                lock:true
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
        <div class="newsj_left">
        </div><div>
        <div id="newtipi">
        </div></div>
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
    <div class="modal-footer" style="text-align: right">
        <button type="button" class="net_put_1" data-dismiss="modal" onclick="assignItem();">指定跟踪</button>
        <button type="button" class="net_put_1" data-dismiss="modal" onclick="setPrice();">自定调价</button>
        <button type="button" class="net_put_1" data-dismiss="modal" onclick="autoSetPrice();">自动调价</button>
         <button type="button" class="net_put" onclick="submitCommit();">保存</button>
    </div>
</body>
</html>
