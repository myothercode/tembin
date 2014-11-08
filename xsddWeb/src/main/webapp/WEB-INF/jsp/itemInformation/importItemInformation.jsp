<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/12
  Time: 11:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        function upload(obj)
        {
            obj.action =path+"/information/ajax/importInformation.do";
            obj.submit();
        }
        $(document).ready(function(){
            var flag=document.getElementById("flag");
            if("true"==$(flag).val()){
                alert("上传成功");
                W.refreshTable();
                W.itemInformation.close();
            }
        });
    </script>
</head>
<body>
<br/><br/>
<a href="<c:url value ="/upload/upload.xls"/> "><font color="blue">请先下载excel模板</font></a>
<input id="flag" type="hidden" name="flag" value="${flag}">
<form id="improtForm" action="/information/ajax/importInformation.do" method="post" enctype="multipart/form-data" >
    <input type="file" name="file"/>
    <input type="submit" value="导入" onclick="upload(this.form)">
</form>
<%--<div class="modal-body">
    <form class="form-horizontal" role="form">
        <table width="100%" border="0" style="margin-left:40px;">
            <tbody><tr>
                <td width="16%" height="28" align="right"></td>
                <td width="41%" height="28"><input type="button" value="选择文件" size="30" onclick="f.click()" class="net_put_2">
                    <input type="file" id="f" onchange="this.form.submit()" name="f" style="position:absolute; filter:alpha(opacity=0); opacity:0; width:30px; " size="1"> 未选择任何文件 <button type="button" class="net_put">导入</button></td>
                <td width="43%" height="28"></td>
            </tr>
            <tr>
                <td height="28" align="right">&nbsp;</td>
                <td height="28" style=" padding-top:22px;"><button type="button" class="net_put">保存</button><button type="button" class="net_put_1" data-dismiss="modal">关闭</button></td>
                <td height="28">&nbsp;</td>
            </tr>
            </tbody></table>

    </form></div>--%>
</body>
</html>
