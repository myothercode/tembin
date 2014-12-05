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
        $(document).ready(function () {
            loadAssessData();
            var userConfig = '${userConfig.configValue}';
            $("input[type='radio'][name='selectType'][value='" + userConfig + "']").prop("checked", true);
            var apprange = '${ta.apprange}';
            var setview = '${ta.setview}';
            $("input[type='radio'][name='appRange'][value='"+apprange+"']").prop("checked",true);
            $("input[type='radio'][name='setview'][value='"+setview+"']").prop("checked",true);

        });
        function getContent(json){
            var html="<img src='"+path+"/img/add.png'>&nbsp;&nbsp;<span>"+json.assesscontent+"</span>";
            return html;
        }
        function loadAssessData() {
            $("#assessTable").initTable({
                url: path + "/ajax/loadAssessList.do",
                columnData: [
                    {title: "评价内容", name: "assesscontent", width: "80%", align: "left",format:getContent},
                    {title: "操作", name: "Option1", width: "20%", align: "left", format: dohtml}
                ],
                selectDataNow: false,
                isrowClick: false,
                showIndex: false,
                onlyFirstPage: true
            });
            refreshTable();
        }
        function dohtml(json) {
            var hs="";
            hs += "<li style='height:25px' onclick='editAssessContent(" + json.id + ",this)' value='" + json.id + "' doaction=\"look\" >编辑</li>";
            hs += "<li style='height:25px' onclick='delAssessContent(" + json.id + ",this)' value='" + json.id + "' doaction=\"look\" >删除</li>";
            var pp={"liString":hs};
            return getULSelect(pp);
        }
        function refreshTable() {
            $("#assessTable").selectDataAfterSetParm({"bedDetailVO.deptId": "", "isTrue": 0});
        }
        function savePublicUserConfig(obj) {
            var configValue = $(obj).val();
            var url = path + "/ajax/savePublicUserConfig.do?configValue=" + configValue;
            $().invoke(url, {},
                    [function (m, r) {
                        alert("设置成功！");
                    },
                        function (m, r) {
                            alert(r);
                        }]
            );
        }
        function addAssessContent() {
            var tent = "<div class='textarea'>评价内容：<textarea cols='45' rows='5' id='assesscontent' ></textarea></div>";
            var editPage = openMyDialog({title: '评价内容',
                content: tent,
                icon: 'tips.gif',
                width: 300,
                button: [
                    {
                        name: '确定',
                        callback: function (iwins, enter) {
                            var reason = "";
                            reason = iwins.parent.document.getElementById("assesscontent").value;
                            if (reason == null || reason == "") {
                                alert("请输入评价内容！");
                                return false;
                            }
                            var url = path + "/ajax/addAssessContent.do?content=" + reason;
                            $().invoke(url, {},
                                    [function (m, r) {
                                        if (r == "success") {
                                            refreshTable();
                                        }
                                    },
                                        function (m, r) {
                                            alert(r);
                                        }]
                            );
                        }
                    }
                ]
            });
        }
        function editAssessContent(id, obj) {
            if(obj.tagName=="SPAN"){
                $(obj).parent().parent().parent().prop("id", id);
            }else{
                $(obj).parent().parent().parent().parent().parent().prop("id", id);
            }
            var tent = "<div class='textarea'>评价内容：<textarea cols='45' rows='5' id='assesscontent' >" + $("#" + id).find("td :eq(0)").find("span").html() + "</textarea></div>";
            var editPage = openMyDialog({title: '评价内容',
                content: tent,
                icon: 'tips.gif',
                width: 300,
                button: [
                    {
                        name: '确定',
                        callback: function (iwins, enter) {
                            var reason = "";
                            reason = iwins.parent.document.getElementById("assesscontent").value;
                            if (reason == null || reason == "") {
                                alert("请输入评价内容！");
                                return false;
                            }
                            var url = path + "/ajax/addAssessContent.do?content=" + reason + "&id=" + id;
                            $().invoke(url, {},
                                    [function (m, r) {
                                        if (r == "success") {
                                            refreshTable();
                                        }
                                    },
                                        function (m, r) {
                                            alert(r);
                                        }]
                            );
                        }
                    }
                ]
            });
        }
        function delAssessContent(id, obj) {
            if(obj.tagName=="SPAN"){
                $(obj).parent().parent().parent().prop("id", id);
            }else{
                $(obj).parent().parent().parent().parent().parent().prop("id", id);
            }
            var url = path + "/ajax/delAssessContent.do?id=" + id;
            $().invoke(url, {},
                    [function (m, r) {
                        $("#" + id).hide(1500);
                        $("#" + id).slideUp(1500);
                    },
                        function (m, r) {
                            alert(r);
                        }]
            );
        }

        function setAssessTab(obj){
            var name=$(obj).attr("name");
            $(obj).parent().find("dt").each(function (i, d) {
                if($(d).attr("name")==name){
                    $(d).attr("class","new_tab_1");
                }else{
                    $(d).attr("class","new_tab_2");
                }
            });
            $("div[name='model']").hide();
            $("#"+name).show();
        }

        function onAssessViewSet(obj){

            var url = path + "/ajax/saveAssessViewSet.do?appRange=" + $("input[type='radio'][name='appRange']:checked").val()+"&setView="+$("input[type='radio'][name='setview']:checked").val();
            $().invoke(url, {},
                    [function (m, r) {
                        alert("设置成功！");
                    },
                        function (m, r) {
                            alert(r);
                        }]
            );
        }
    </script>
</head>
<body>
<div class="new_all">
    <div class="here">当前位置：首页 > 客户管理 > <b>评价管理</b></div>
    <div class="a_bal"></div>
    <div class="new">
        <div class="new_tab_ls" id="selectModel">
            <dt id=menu1 name="autoAssess" class=new_tab_1 onclick="setAssessTab(this)">自动评价</dt>
            <dt id=menu2 name="setAssessRe" class=new_tab_2 onclick="setAssessTab(this)">商品评价关联</dt>
        </div>
    </div>
    <div class="new" id="autoAssess" name="model">
        <h1 style="font-size: 12px;font-weight: bold;">给买家留评价</h1>
        <div class=Contentbox>
            <div style="line-height: 30px;font-size: 12px;border-bottom: solid rgb(226, 220, 220) 1px;padding-left: 19px;">
                <input type="radio" name="selectType" value="1" onclick="savePublicUserConfig(this)"/>&nbsp;&nbsp;不自动评价
            </div>
            <div style="line-height: 30px;font-size: 12px;border-bottom: solid rgb(226, 220, 220) 1px;padding-left: 19px;">
                <input type="radio" name="selectType" value="2" onclick="savePublicUserConfig(this)"/>&nbsp;&nbsp;收到买家的正面评价时
            </div>
            <div style="line-height: 30px;font-size: 12px;border-bottom: solid rgb(226, 220, 220) 1px;padding-left: 19px;">
                <input type="radio" name="selectType" value="3" onclick="savePublicUserConfig(this)"/>&nbsp;&nbsp;发货时
            </div>
            <div style="line-height: 30px;font-size: 12px;border-bottom: solid rgb(226, 220, 220) 1px;padding-left: 19px;">
                <input type="radio" name="selectType" value="4" onclick="savePublicUserConfig(this)"/>&nbsp;&nbsp;买家购买物品时
            </div>
            <h6 style="margin: 20px;">系统将在以下正评中随即抽取一条留给买家</h6>
            <div id="assessTable">

            </div>
            <div style="margin: 10px;font-size: 12px;">
                <a style="color: #5897fb" href="javascript:void(0)" onclick="addAssessContent()">添加</a>
            </div>
        </div>
    </div>
    <div class="new" id="setAssessRe" style="display: none"  name="model">
        <h1 style="font-size: 12px;font-weight: bold;">关联评价应用范围</h1>
        <div class=Contentbox>
            <div style="line-height: 30px;font-size: 12px;border-bottom: solid rgb(226, 220, 220) 1px;padding-left: 19px;">
                <input type="radio" name="appRange" value="1" onclick="onAssessViewSet(this)"/>&nbsp;&nbsp;应用到所有刊登
            </div>
            <div style="line-height: 30px;font-size: 12px;border-bottom: solid rgb(226, 220, 220) 1px;padding-left: 19px;">
                <input type="radio" name="appRange" value="2" onclick="onAssessViewSet(this)"/>&nbsp;&nbsp;自主选择Linting关联
            </div>
        </div>
        <h1 style="font-size: 12px;font-weight: bold;">评价显示设置</h1>
        <div class=Contentbox>
            <div style="line-height: 30px;font-size: 12px;border-bottom: solid rgb(226, 220, 220) 1px;padding-left: 19px;">
                <input type="radio" name="setview" value="1" onclick="onAssessViewSet(this)"/>&nbsp;&nbsp;只显示好评
            </div>
            <div style="line-height: 30px;font-size: 12px;border-bottom: solid rgb(226, 220, 220) 1px;padding-left: 19px;">
                <input type="radio" name="setview" value="2" onclick="onAssessViewSet(this)"/>&nbsp;&nbsp;显示所有评价（包括中评、差评）
            </div>
        </div>
    </div>
</div>
</body>
</html>
