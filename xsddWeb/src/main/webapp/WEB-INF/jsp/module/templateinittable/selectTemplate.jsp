<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/5
  Time: 10:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <title></title>
    <script type="text/javascript">
        var imageUrlPrefix = '${imageUrlPrefix}';
        $(document).ready(function(){
            $("#templateInitTableListTable_select").initTable({
                url:path + "/ajax/loadTemplateInitTableList.do?",
                columnData:[
                    {title:"",name:"level",width:"8%",align:"left",format:makeOption1}
                ],pageCountArray: [ 9, 18, 36, 72 ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false,
                isrowClick: true,
                rowClickMethod: function (obj,o){
                    $("input[type='radio'][name='templateId'][value='"+obj.id+"']").prop("checked",true);
                },
                afterLoadTable:function(){
                    $("#afterTable").show();
                    $("#afterTable").html("");
                    $("#templateInitTableListTable_select").find("table").hide();
                    var data = $("#templateInitTableListTable_select").data("option").allData;
                    if(data.length>0){
                        var html = "<table border='0' width='100%' align='left' cellspacing='0'>";
                        for(var i=0;i<data.length;i++){
                            if(i%3==0){
                                html+="<tr>";
                            }
                            var str = "<a href='javascript:void(0)' style='padding-top: 30px;color:blue;'  onclick=returnTemplate('"+data[i].id+"','"+data[i].templateViewUrl+"')>选择</a>";

                            html+="<td width='20%' height='340px' style='text-align: -webkit-center;'>" +
                                    "<div style='width: 190px;'><div style='height: 275px;'><img src='"+imageUrlPrefix+data[i].templateViewUrl+"' width='184' height='270' class='newad_pic'></div>" +
                                    "<div style='text-align: center;padding: 5px;'>"+str+"</div><div></td>";
                        }
                        html+="</table>";
                        $("#afterTable").append(html);
                    }
                }
            });
            refreshTable();
            getTemplateType();
        });

        function queryBuP(templateTypeId,obj){
            var p={"strV1":templateTypeId};
            refreshTable(p);

            var oli=obj.parentNode;
            $(oli).siblings().removeClass("onclickd");
            $(oli).addClass("onclickd");

        }

        function returnSelect(json) {
            var htm = "<input type=\"radio\" src='"+json.templateViewUrl+"' name=\"templateId\" value=" + json.id + ">";
            return htm;
        }
        function showBigImage(obj){
            $(obj).hover(function(){

            });
            var x = 10;
            var y = 20;
            $("#template").find("img").attr("src",$(obj).attr("src"));
            $("#template").css({
                "top": ($(obj).offset().top + y) + "px",
                "left": ($(obj).offset().left + x) + "px"
            })
            $("#template").show();
        }
        function hideImage(obj){
            $("#template").hide();
        }
        function showImage(json){
            var html = "<span><input type='hidden' name=''><img onmouseleave='hideImage(this)' onmouseover='showBigImage(this)' src='"+imageUrlPrefix+json.templateViewUrl+"' height='80' width='80' /></span>";
            return html;
        }
        function refreshTable(p){
            $("#afterTable").hide();
            if(p==null){p={}}
            $("#templateInitTableListTable_select").selectDataAfterSetParm(p);
        }
        /**组装操作选项*/
        function makeOption1(json){
            var htm1="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"editTemplateInitTable('"+json.id+"');\">预览</a>";
            return htm1;
        }
        function makeOption2(json){
            var htm=json.tLevel;
            if(htm==1){
                return "用户自定义";
            }else if(htm==0){
                return "预设";
            }
        }
        function returnTemplate(id,src){
            var api = frameElement.api, W = api.opener;
            W.document.getElementById('templateId').value=id;
            W.document.getElementById('templateUrl').src=imageUrlPrefix+src;
            W.showTemPic();
            W.selectTemplates.close();
        }
        function getTemplateType(){
            var url=path + "/ajax/queryTemplateType.do?";
            $().invoke(
                    url,
                    {},
                    function(i,r){
                        for(var i in r){
                            var lihtml="<li class='select2-search-choice1'>";
                            lihtml+="<div onclick='queryBuP("+(r[i]["templateTypeId"])+",this)'>"+(r[i]["templateTypeName"])+"</div>";
                            lihtml+="</li>";
                            $("#s2").append(lihtml);
                            //alert(r[i]["templateTypeId"])
                        }
                    }
            );
        }
    </script>
    <style type="text/css">
        .newad_pic{
            float: left;
            padding: 10px;
            border: 1px solid #D9E4EA;
            border-radius: 10px;
            padding-bottom:25PX;
        }
    </style>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
        .select2-container-multi1 .select2-choices1 {
            height: auto !important;
            height: 1%;
            margin: 0;
            padding: 0 5px 0 0;
            position: relative;
            border: 1px solid #aaa;
            cursor: text;
            overflow: hidden;
            background-color: #fff;

        }


        .select2-container-multi1 .select2-choices1 li {
            float: left;
            list-style: none;

        }


        .select2-container-multi1 .select2-choices1 .select2-search-choice1 {
            padding: 3px 5px 3px 5px;
            margin: 3px 0 3px 5px;
            position: relative;

            line-height: 13px;
            color: #333;
            cursor: default;
            border: 1px solid #aaaaaa;

            border-radius: 3px;
            cursor: pointer;
        }

        .select2-search-choice1:hover{
            border:#35a5e5 5px solid;
            box-shadow: 0 0 5px rgba(81, 203, 238, 1);
        }

        .onclickd{
            border:#35a5e5 5px solid;
            box-shadow: 0 0 5px rgba(81, 203, 238, 1);
        }
    </style>
</head>
<body>
<%--<div style="padding: 10px;">
    <button type="button" onclick="returnTemplate()" class="net_put">确定</button>
</div>--%>
<div class="select2-container1 select2-container-multi1 " id="s2id_e9" style="width: 100%;padding-top: 20px;">
    <ul class="select2-choices1" id="s2">
        <li class="select2-search-choice1">
            <div onclick="queryBuP('all',this)">全部</div>
        </li>
    </ul>
</div>


<div id="afterTable"></div>
<div id="templateInitTableListTable_select" style="padding: 10px;"></div>
<div style="display:none;position:absolute;" id="template">
    <img src="Timg" width="300" height="300"/>
</div>
</body>
</html>
