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
                    {title: "选项", name: "option1", width: "8%", align: "left", format: returnSelect},
                    {title:"模板",name:"option2",width:"8%",align:"left",format:showImage},
                    {title:"模板名字",name:"templateName",width:"8%",align:"left"},
                    {title:"tLevel",name:"level",width:"8%",align:"left",format:makeOption2},
                    {title:"预览",name:"level",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });
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
        function refreshTable(){
            $("#templateInitTableListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
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
        function returnTemplate(){
            var api = frameElement.api, W = api.opener;
            W.document.getElementById('templateId').value=$("input[type='radio'][name='templateId']:checked").val();
            W.document.getElementById('templateUrl').src=imageUrlPrefix+$("input[type='radio'][name='templateId']:checked").attr("src");
            W.selectTemplates.close();
        }
    </script>
</head>
<body>
<div>
    <input type="button" value="确定" onclick="returnTemplate()">
</div>
<div id="templateInitTableListTable"></div>
<div style="display:none;position:absolute;" id="template">
    <img src="Timg" width="300" height="300"/>
</div>
</body>
</html>
