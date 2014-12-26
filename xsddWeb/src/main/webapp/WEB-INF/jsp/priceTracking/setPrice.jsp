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
        $(document).ready(function(){
            var url=path+"/priceTracking/ajax/priceTrackingQueryList.do?";
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

        });
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
