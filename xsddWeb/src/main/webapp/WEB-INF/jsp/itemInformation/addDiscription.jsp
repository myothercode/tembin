<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/9
  Time: 10:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.config.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.all.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/lang/zh-cn/zh-cn.js" /> ></script>
    <style type="text/css">
        div{
            width:100%;
        }
    </style>
</head>
<body>
<table>
    <tr>
        <td>名称:</td>
        <td><input type="text" value="${itemInformation.name}" id="name" style="width: 400px;"/></td>
    </tr>
    <tr>
        <td colspan="2">
            <script id="myEditor" type="text/plain" style="width:780px;height:500px;">${itemInformation.description}</script>
        </td>
    </tr>
</table>
<div align="right">
 <input type="button" value="保存" onclick="submitCommit();"/>
&nbsp;<input type="button" value="关闭" onclick="closedialog();"/>
</div>
<script type="text/javascript">
    var api = frameElement.api, W = api.opener;
    var ue = UE.getEditor('myEditor');
    function closedialog(){
        W.adddiscription.close();
    }
    function submitCommit(){
        var name=$("#name").val();
        var discription=ue.getContent();
        var table="<table id=\"discriptionTable\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 400px;\"><tr><td width=\"70%\">描述名称</td><td width=\"30%\">动作</td></tr>" +
        "<tr scop=\"tr\"><td>"+name+"<input type=\"hidden\"  name=\"discription\" value=\""+discription+" \"/></td>" +
        "<td><a href=\"javascript:void();\" onclick=\"editDiscription(this);\">编辑</a>&nbsp;<a href=\"javascript:void();\" onclick=\"removeDiscription(this);\">移除</a></td></tr></table>";
        var table1="<tr scop=\"tr\"><td>"+name+"<input type=\"hidden\" name=\"discription\" value=\""+discription+" \"/></td>" +
                "<td><a href=\"javascript:void();\" onclick=\"editDiscription(this);\">编辑</a>&nbsp;<a href=\"javascript:void();\" onclick=\"removeDiscription(this);\">移除</a></td></tr>";
        var discriptionTable=W.document.getElementById("discriptionTable");
        var dis=W.document.getElementById("addDiscription");
        var flag= W.discriptionFlag
        var descriptionName= W.document.getElementById("descriptionName");
        var discription= W.discription;
        if(flag){
            discription.remove();
            $(discriptionTable).append($(table1));
        }else{
            if(discriptionTable==null){
                $(dis).append($(table));
            }else{
                $(discriptionTable).append($(table1));
            }
        }
        W.adddiscription.close();
    }
</script>
</body>
</html>
