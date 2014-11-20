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
    </script>
</head>
<body>
<div class="new_all">
    <div class="here">当前位置：首页 > 客户管理 > <b>自动评价</b></div>
    <div class="a_bal"></div>
    <div class="new">
        <h1 style="font-size: 12px;font-weight: bold;">给买家留评价</h1>
        <div class=Contentbox>
            <div style="line-height: 30px;font-size: 12px;border-bottom: solid rgb(226, 220, 220) 1px;padding-left: 3px;">
                <input type="radio" name="selectType" value="1" onclick="savePublicUserConfig(this)"/>&nbsp;&nbsp;不自动评价
            </div>
            <div style="line-height: 30px;font-size: 12px;border-bottom: solid rgb(226, 220, 220) 1px;padding-left: 3px;">
                <input type="radio" name="selectType" value="2" onclick="savePublicUserConfig(this)"/>&nbsp;&nbsp;收到买家的正面评价时
            </div>
            <div style="line-height: 30px;font-size: 12px;border-bottom: solid rgb(226, 220, 220) 1px;padding-left: 3px;">
                <input type="radio" name="selectType" value="3" onclick="savePublicUserConfig(this)"/>&nbsp;&nbsp;发货时
            </div>
            <div style="line-height: 30px;font-size: 12px;border-bottom: solid rgb(226, 220, 220) 1px;padding-left: 3px;">
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
</div>
</body>
</html>
