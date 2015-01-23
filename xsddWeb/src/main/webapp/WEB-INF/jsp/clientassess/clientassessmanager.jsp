<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script>
        $(document).ready(function(){
            onloadTable();
        });
        function onloadTable(){
            $("#clientAssessTable").initTable({
                url:path+"/ajax/loadClientAssessTable.do",
                columnData:[
                    {title:"买家",name:"commentinguser",width:"6%",align:"center"},
                    {title:"评价内容",name:"commenttext",width:"40%",align:"left"},
                    {title:"评价类型",name:"commenttype",width:"6%",align:"center",format:getcommentType},
                    {title:"商品",name:"itemtitle",width:"20%",align:"left",format:getTitle},
                    {title:"评价时间",name:"commenttime",width:"6%",align:"center",format:getDateStr}/*,
                    {title:"价格",name:"itemprice",width:"6%",align:"center"}*/
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        }
        function getcommentType(json){
            var html="";
            if(json.commenttype=="Positive"){
                html="<img src='"+path+"/img/new_one_1.gif'/>"
            }else if(json.commenttype=="Neutral"){
                html="<img src='"+path+"/img/new_one_2.gif'/>"
            }else if(json.commenttype=="Negative"){
                html="<img src='"+path+"/img/new_one_3.gif'/>"
            }
            return html;
        }
        function  refreshTable(){
            var param={};
            param={"commentAmount":$("input[type='hidden'][name='commentAmount']").val(),"commentType":$("input[type='hidden'][name='commentType']").val(),"selecttype":$("select[name='selecttype']").val(),"selectvalue":$("input[type='text'][name='selectvalue']").val()};
            $("#clientAssessTable").selectDataAfterSetParm(param);
        }
        function getTitle(json){
            return "<a target='_blank' tlitle='"+json.itemtitle+"' href='"+serviceItemUrl+json.itemid+"'>"+json.itemtitle+"</a>";
        }
        function getDateStr(json){
            return "<span style='color: #7B7B7B;'>"+json.commenttime.replace(" ","</br>")+"</span>";
        }

        function selectListType(obj){
            var div=$(obj).parent();
            var as=$(div).find("a");
            for(var i=0;i<as.length;i++){
                var span=$(as[i]).find("span");
                $(span[0]).attr("class","newusa_ici_1");
            }
            var span1=$(obj).find("span");
            $(span1[0]).attr("class","newusa_ici");
            $("input[name='commentType']").val($(obj).attr("value"));
            refreshTable();
        }
        function selectAmount1(obj){
            var div=$(obj).parent();
            var as=$(div).find("a");
            for(var i=0;i<as.length;i++){
                var span=$(as[i]).find("span");
                $(span[0]).attr("class","newusa_ici_1");
            }
            var span1=$(obj).find("span");
            $(span1[0]).attr("class","newusa_ici");
            $("input[name='commentAmount']").val($(obj).attr("value"));
            refreshTable();
        }
    </script>
</head>
<body>
<div class="new_all">
    <div class="here">当前位置：首页 > 客户管理 > <b>客户评价</b></div>
    <div class="a_bal"></div>
    <div class="new">
        <div class="new_tab_ls" id="selectModel">
            <dt id=menu1 name="autoAssess" class=new_tab_1 onclick="setAssessTab(this)">客户评价</dt>
        </div>

        <form id="clientAssess">
        <input type="hidden" name="commentType">
        <input type="hidden" name="commentAmount">
        <div class=Contentbox id="Contentbox">
            <div class="new_usa" style="margin-top:20px;">
                <li class="new_usa_list">
                    <span class="newusa_i">评价类型：</span>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value=""><span
                            class="newusa_ici">全部</span></a>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="Positive"><span
                            class="newusa_ici_1">好评</span></a>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="Neutral"><span
                            class="newusa_ici_1">中评</span></a>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="Negative"><span
                            class="newusa_ici_1">差评</span></a>
                </li>
                <li class="new_usa_list">
                    <span class="newusa_i">选择账号：</span>
                    <a href="javascript:void(0)" onclick="selectAmount1(this)" value=""><span
                            class="newusa_ici">全部</span></a>
                    <c:forEach items="${ebays}" var="ebay">
                        <a href="javascript:void(0)" onclick="selectAmount1(this)" value="${ebay.ebayName}"><span
                                class="newusa_ici_1">${ebay.ebayNameCode}</span></a>
                    </c:forEach>
                </li>
                <div class="newsearch">
                    <span class="newusa_i">搜索内容：</span>
                    <%--<a href="javascript:void(0)" onclick="selectAllData(this)" value="">
                        <span class="newusa_ici">全部</span>
                    </a>--%>
                        <span id="sleBG" style="width:82px;background-position: 67px 10px;">
                            <span id="sleHid" style="width: 80px;">
                                <select name="selecttype" class="select" style="color: #737FA7;width: 80px;">
                                    <option selected="selected" value="">选择类型</option>
                                    <option value="ItemID">物品号</option>
                                    <option value="TransactionID">交易号</option>
                                    <option value="OrderLineItemID">定单号</option>
                                    <option value="ItemTitle">物品title</option>
                                </select>
                            </span>
                        </span>
                    <div class="vsearch">
                        <input name="selectvalue" type="text" class="key_1" style="vtical-align:middle;line-height:100%;">
                        <input name="newbut" onclick="refreshTable()" type="button" class="key_2">
                    </div>
                </div>
                <div style="width: 100%;float: left;height: 5px"></div>
                <div id="clientAssessTable"></div>
            </div>
        </div>
        </form>
    </div>

</div>
</body>
</html>
