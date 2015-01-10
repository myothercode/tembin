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
    <title></title>
    <script type="text/javascript">
        var imageUrlPrefix = '${imageUrlPrefix}';

        $(document).ready(function(){
            $("#templateInitTableListTable").initTable({
                url:path + "/ajax/loadTemplateInitTableList.do?",
                columnData:[
                    {title:"",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:true,
                afterLoadTable:function(){
                    $("#afterTable").show();
                    $("#afterTable").html("");
                    $("#templateInitTableListTable").find("table").hide();
                    var data = $("#templateInitTableListTable").data("option").allData;
                    if(data.length>0){
                        var html = "<table border='0' width='100%' align='left' cellspacing='0'>";
                        for(var i=0;i<data.length;i++){
                            if(i%5==0){
                                html+="<tr>";
                            }
                            var str = "<a href='javascript:void(0)' style='padding-top: 30px;color:blue;'  onclick=viewTemplateInitTable('"+data[i].id+"')>查看</a>";
                            if(data[i].tLevel==1) {
                                str+="&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)' onclick=editTemplateInitTable('"+data[i].id+"')>编辑</a>";
                            }
                            html+="<td width='20%' height='340px'>" +
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

        function makeImgUrl(json){
            return "<img src="+imageUrlPrefix+json.templateViewUrl+" style='width:100px;height:150px;' />";
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

        function queryBuP(templateTypeId,obj){
            var p={"strV1":templateTypeId};
            refreshTable(p);

            var oli=obj.parentNode;
            $(oli).siblings().removeClass("onclickd");
            $(oli).addClass("onclickd");

        }


        var TemplateInitTable;
        function addTemplateInitTable(){
            TemplateInitTable=openMyDialog({title: '新增模板选项',
                content: 'url:/xsddWeb/addTemplateInitTable.do',
                icon: 'succeed',
                width:1025,
                lock:true
            });
        }
        function editTemplateInitTable(id){
            TemplateInitTable=openMyDialog({title: '编辑模板描述',
                content: 'url:/xsddWeb/editTemplateInitTable.do?id='+id,
                icon: 'succeed',
                width:1025,
                lock:true
            });
        }
        function viewTemplateInitTable(id){
            TemplateInitTable=openMyDialog({title: '查看模板',
                content: 'url:/xsddWeb/viewTemplateInitTable.do?id='+id,
                icon: 'succeed',
                width:1025,
                height:750,
                lock:true
            });
        }


        function refreshTable(p){
            $("#afterTable").hide();
            if(p==null){p={}}
            $("#templateInitTableListTable").selectDataAfterSetParm(p);
        }
        /**组装操作选项*/
        function makeOption1(json){
            var hs="";
            hs+="<li style='height:25px' onclick=viewTemplateInitTable('"+json.id+"') value='"+json.id+"' doaction=\"look\" >查看</li>";
            if(json.tLevel==1){
                hs+="<li style='height:25px' onclick=editTemplateInitTable('"+json.id+"') value='"+json.id+"' doaction=\"look\" >编辑</li>";
            }

            var pp={"liString":hs,"marginLeft":"-50px"};
            return getULSelect(pp);
        }
        function makeOption2(json){
            var htm=json.tLevel;
            if(htm==1){
                return "用户自定义";
            }else if(htm==0){
                return "预设";
            }
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
</head>
<body>
<div class="new_all">
    <div class="here">当前位置：首页 > 刊登管理 > <b>模板管理</b></div>
    <div class="a_bal"></div>
    <div class="tbbay" style="position: absolute;top: 28px;right: 40px;z-index: 10000;"><a data-toggle="modal" href="javascript:void(0)" class=""  onclick="addTemplateInitTable()">新增模板</a></div>
    <%--<div class="a_bal"></div>--%>
    <div class="select2-container1 select2-container-multi1 " id="s2id_e9" style="width: 90%;">
        <ul class="select2-choices1" id="s2">
            <li class="select2-search-choice1">
                <div onclick="queryBuP('all',this)">全部</div>
            </li>
        </ul>
    </div>

    <div id="cent">
        <div id="afterTable"></div>
        <div id="templateInitTableListTable"></div>
    </div>
</div>

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
</body>
</html>
